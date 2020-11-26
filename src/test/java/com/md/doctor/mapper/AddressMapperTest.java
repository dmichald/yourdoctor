package com.md.doctor.mapper;

import com.md.doctor.dto.address.AddressDto;
import com.md.doctor.models.Address;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressMapperTest {
    private static Long ID = 1L;
    private static String NAME = "Jan";
    private static String SURNAME = "Kowal";
    private static String STREET = "ul. 3 Maja";
    private static String CODE = "33-333";
    private static String CITY = "Kraków";

    private final AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);

    @Test
    void mapAddressDtoToAddress() {
        //given
        AddressDto addressDto = new AddressDto(ID, STREET, CODE, CITY);

        //when
        Address address = addressMapper.mapAddressDtoToAddress(addressDto);

        //then
        assertAll(
                () -> assertEquals(STREET, address.getStreet()),
                () -> assertEquals(CODE, address.getCode()),
                () -> assertEquals(CITY, address.getCity())
        );
    }

    @Test
    void mapAddressToAddressDto() {
        //given
        Address address = new Address(ID, STREET, CODE, CITY);

        //when
        AddressDto addressDto = addressMapper.mapAddressToAddressDto(address);

        //then
        assertAll(
                () -> assertEquals(STREET, addressDto.getStreet()),
                () -> assertEquals(CODE, addressDto.getCode()),
                () -> assertEquals(CITY, addressDto.getCity())
        );
    }
}
