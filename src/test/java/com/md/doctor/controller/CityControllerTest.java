package com.md.doctor.controller;

import com.md.doctor.service.office.OfficeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@DisplayName("CityController should")
class CityControllerTest {
    @MockBean
    private OfficeService officeService;

    private MockMvc mockMvc;
    private CityController cityController;

    @BeforeEach
    void setUp() {
        cityController = new CityController(officeService);
        mockMvc = MockMvcBuilders.standaloneSetup(cityController).build();
    }

    @DisplayName("return all cities where offices are")
    @Test
    void getCities() throws Exception {
        //given
        List<String> cities = List.of("Zbąszynek", "New York");

        //when
        when(officeService.getCities()).thenReturn(cities);
        ResultActions actions = mockMvc.perform(get("/offices/cities")
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.cities", hasSize(2)))
                .andExpect(jsonPath("$.cities", hasItem(cities.get(0))));
    }
}