package com.md.doctor.exception;

import com.md.doctor.controller.OfficeController;
import com.md.doctor.service.office.OfficeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.DispatcherServlet;

import java.lang.reflect.Field;

import static com.md.doctor.TestResource.objectMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@DisplayName("ExceptionHandler should ")
class RestExceptionHandlerTest {
    @MockBean
    private OfficeService officeService;
    private OfficeController officeController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        officeController = new OfficeController(officeService);
        mockMvc = MockMvcBuilders.standaloneSetup(officeController).setControllerAdvice(new RestExceptionHandler()).build();
    }

    @DisplayName("handle any error")
    @Test
    void handleAnyErrorTest() throws Exception {
        //when
        when(officeService.getById(1L)).thenThrow(RuntimeException.class);

        ResultActions actions = mockMvc.perform(get("/offices/1"));

        //then
        actions.andExpect(status().isInternalServerError());
    }

    @DisplayName("handle EntityNotFoundException")
    @Test
    void handleEntityNotFoundException() throws Exception {
        //when
        when(officeService.getById(1L)).thenThrow(EntityNotFoundException.class);

        ResultActions actions = mockMvc.perform(get("/offices/1"));

        //then
        actions.andExpect(status().isNotFound());

        //and
        String response = actions.andReturn().getResponse().getContentAsString();
        ErrorResponse errorResponse = objectMapper().readValue(response, ErrorResponse.class);

        assertEquals("ENTITY NOT FOUND", errorResponse.getMessage());

    }

    @Test
    @DisplayName("should return method not allowed when is used wrong method")
    void handleMethodNotSupportedTest() throws Exception {
        //when
        when(officeService.getById(1L)).thenThrow(EntityNotFoundException.class);

        ResultActions actions = mockMvc.perform(patch("/offices/1"));

        //then
        actions.andExpect(status().isMethodNotAllowed());

        //and
        String response = actions.andReturn().getResponse().getContentAsString();
        ErrorResponse errorResponse = objectMapper().readValue(response, ErrorResponse.class);

        assertEquals("METHOD NOT ALLOWED", errorResponse.getMessage());
    }

    @Test
    @DisplayName(" return NOT_FOUND when no handler found")
    void shouldReturnNotFoundWhenNoHandlerFound() throws Exception {
        //given
        throwExceptionIfNoHandlerFound();

        //when
        ResultActions actions = mockMvc.perform(get("/foo"))
                .andExpect(status().isNotFound());

        //and
        String response = actions.andReturn().getResponse().getContentAsString();
        ErrorResponse errorResponse = objectMapper().readValue(response, ErrorResponse.class);

        assertEquals("NO HANDLER FOUND", errorResponse.getMessage());
    }

    @Test
    @DisplayName(" should return BAD_REQUEST when argument is wrong type")
    void shouldReturnBadRequestWhenArgumentIsWrongType() throws Exception {
        String wrongTypeArgument = "foo";
        //when
        when(officeService.getById(1L)).thenThrow(EntityNotFoundException.class);

        ResultActions actions = mockMvc.perform(get("/offices/" + wrongTypeArgument));

        //then
        actions.andExpect(status().isBadRequest());

        //and
        String response = actions.andReturn().getResponse().getContentAsString();
        ErrorResponse errorResponse = objectMapper().readValue(response, ErrorResponse.class);

        assertEquals("WRONG ARGUMENT TYPE", errorResponse.getMessage());
    }


    private void throwExceptionIfNoHandlerFound() throws NoSuchFieldException, IllegalAccessException {
        final Field field = MockMvc.class.getDeclaredField("servlet");
        field.setAccessible(true);
        final DispatcherServlet servlet = (DispatcherServlet) field.get(mockMvc);
        servlet.setThrowExceptionIfNoHandlerFound(true);
    }
}
