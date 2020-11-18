package com.md.doctor.controller;

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

import java.sql.Date;
import java.util.List;

import static com.md.doctor.TestResource.objectMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    @DisplayName(" return available reservations hours in given day")
    void getAvailableHoursTest() throws Exception {
        //given
        List<String> times = List.of("09:00:00", "10:00:00", "12:00:00");
        ListHoursWrapper listHoursWrapper = new ListHoursWrapper(times);
        String expectedResponse = objectMapper().writeValueAsString(listHoursWrapper);
        Date date = Date.valueOf("1997-03-10");

        //when
        when(reservationService.getFreeReservationsHours(date, 1L)).thenReturn(times);
        ResultActions result = mockMvc.perform(get("/offices/1/available-hours")
                .param("date", date.toString())
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk());
        String response = result.andReturn().getResponse().getContentAsString();
        assertEquals(expectedResponse, response);
    }
}