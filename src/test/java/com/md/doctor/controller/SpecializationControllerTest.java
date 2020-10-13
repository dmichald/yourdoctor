package com.md.doctor.controller;

import com.md.doctor.models.Specialization;
import com.md.doctor.repository.SpecializationRepo;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SpecializationControllerTest {
    @Mock
    private SpecializationRepo repository;

    @InjectMocks
    private SpecializationController specializationController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(specializationController)
                .build();
    }

    @Test
    void whenGetRequest_ShouldReturnListOfSpecializations() throws Exception {
        //given
        List<Specialization> list = Lists.newArrayList(
                Specialization.builder().name("Test").build(),
                Specialization.builder().name("Test2").build()
        );

        //when
        when(repository.findAll()).thenReturn(list);

        //then
        mockMvc.perform(get("/specializations").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.specializations", hasSize(2)));

    }
}