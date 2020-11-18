package com.md.doctor.controller;

import com.md.doctor.service.address.AddressService;
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

import static com.md.doctor.TestResource.ADDRESS_DTO;
import static com.md.doctor.TestResource.objectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@DisplayName("AddressController should ")
class AddressControllerTest {

    @MockBean
    private AddressService addressService;
    private MockMvc mockMvc;
    private AddressController addressController;

    @BeforeEach
    void setUp() {
        addressController = new AddressController((addressService));
        mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();
    }

    @Test
    @DisplayName("successful update address")
    void updateAddress() throws Exception {
        //given
        String request = objectMapper().writeValueAsString(ADDRESS_DTO);

        ResultActions action = mockMvc.perform(put("/offices/1/address/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request));

        action.andExpect(status().isOk());
    }
}