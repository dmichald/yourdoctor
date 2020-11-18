package com.md.doctor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.md.doctor.TestResource;
import com.md.doctor.service.reservation.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.md.doctor.TestResource.objectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@DisplayName("ReservationController should ")
class ReservationControllerTest {
    @MockBean
    private ReservationService reservationService;
    private MockMvc mockMvc;
    private ReservationController reservationController;

    @BeforeEach
    void setUp() {
        reservationController = new ReservationController(reservationService);
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
    }

    @DisplayName("successful add reservation")
    @Test
    void addReservation() throws Exception {
        //given

        String jsonRequest = objectMapper().writeValueAsString(TestResource.RESERVATION_DTO);

        //when
        ResultActions actions = mockMvc.perform(post("/offices/1/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));

        //then
        actions.andDo(MockMvcResultHandlers.print());
        actions.andExpect(status().isOk());
    }
}