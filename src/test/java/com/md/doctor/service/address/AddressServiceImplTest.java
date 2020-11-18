package com.md.doctor.service.address;

import com.md.doctor.dto.address.AddressDto;
import com.md.doctor.exception.EntityNotFoundException;
import com.md.doctor.models.Address;
import com.md.doctor.repository.AddressRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.md.doctor.TestResource.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DisplayName("AddressService should")
@ExtendWith(SpringExtension.class)
class AddressServiceImplTest {
    @MockBean
    private AddressRepo addressRepository;
    private AddressService addressService;

    @BeforeEach
    void setUp() {
        addressService = new AddressServiceImpl(addressRepository);
    }

    @Test
    @DisplayName("save address")
    void saveAddress() {
        Assertions.assertDoesNotThrow(() -> addressService.saveAddress(ADDRESS_DTO));
    }

    @Test
    @DisplayName("get address by id")
    void getAddressById() {
        //given
        Optional<Address> optionalAddress = Optional.of(ADDRESS);

        //when
        when(addressRepository.findById(ID)).thenReturn(optionalAddress);
        AddressDto result = addressService.getAddressById(ID);

        //then
        assertNotNull(result);
    }

    @Test
    @DisplayName("should throw EntityNotFoundException when Address not exist")
    void getAddressById_shouldThrowException_WhenAddressNotExist() {
        //when
        when(addressRepository.findById(ID)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> addressService.getAddressById(ID));

    }

    @Test
    @DisplayName("should throw EntityNotFoundException during updating address if address non exist")
    void updateAddress_shouldThrowException_WhenAddressNotExist() {
        //when
        when(addressRepository.findById(ID)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> addressService.updateAddress(ID,ADDRESS_DTO));

    }

    @Test
    @DisplayName("not throw any exceptions when updating address is correct")
    void updateAddress() {
        when(addressRepository.findById(ID)).thenReturn(Optional.of(ADDRESS));
        Assertions.assertDoesNotThrow(() -> addressService.updateAddress(ID, ADDRESS_DTO));
    }

}