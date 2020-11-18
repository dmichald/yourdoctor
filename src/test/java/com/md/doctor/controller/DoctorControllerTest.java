package com.md.doctor.controller;

import com.md.doctor.dto.doctor.UpdateDoctorNameAndSurnameDto;
import com.md.doctor.service.doctor.DoctorService;
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

import static com.md.doctor.TestResource.objectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@DisplayName("DoctorController should ")
class DoctorControllerTest {
    @MockBean
    private DoctorService doctorService;
    private MockMvc mockMvc;
    private DoctorController doctorController;

    @BeforeEach
    void setUp() {
        doctorController = new DoctorController(doctorService);
        mockMvc = MockMvcBuilders.standaloneSetup(doctorController).build();
    }

    @Test
    void updateDoctor() throws Exception {
        //given
        UpdateDoctorNameAndSurnameDto update = new UpdateDoctorNameAndSurnameDto("New", "Doc");
        String jsonReq = objectMapper().writeValueAsString(update);

        //when
        ResultActions actions = mockMvc.perform(put("/doctors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonReq));

        //then
        actions.andExpect(status().isOk());

    }

    @Test
    void addSpecializationToDoctor() throws Exception {

        //when
        ResultActions actions = mockMvc.perform(put("/doctors/1/specializations")
                .param("specializationId", "1")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isOk());
    }

    @Test
    void removeSpecializationToDoctor() throws Exception {
        //when
        ResultActions actions = mockMvc.perform(delete("/doctors/1/specializations")
                .param("specializationId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isOk());
    }
}