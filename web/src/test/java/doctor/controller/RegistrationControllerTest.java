package doctor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import doctor.dto.security.UserDto;
import doctor.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static doctor.TestResource.PASSWORD;
import static doctor.TestResource.USERNAME;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class RegistrationControllerTest {
    @MockBean
    private UserService userService;

    private MockMvc mockMvc;
    private RegistrationController registrationController;

    @BeforeEach
    void setUp() {
        registrationController = new RegistrationController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
    }

    @Test
    void registerNewUserAccount() throws Exception {
        //given
        UserDto userDto = UserDto.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .matchingPassword(PASSWORD)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonReq = objectMapper.writeValueAsString(userDto);
        //when

        ResultActions actions = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonReq)

        );

        //then
        actions.andExpect(status().isCreated());
    }
}
