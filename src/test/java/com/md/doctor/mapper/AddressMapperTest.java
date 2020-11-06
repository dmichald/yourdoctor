package com.md.doctor.mapper;

import com.md.doctor.dto.AddressDto;
import com.md.doctor.models.Address;
import org.hibernate.annotations.NamedQuery;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

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
        AddressDto addressDto = new AddressDto(ID,NAME,SURNAME,STREET,CODE,CITY);

        //when
        Address address = addressMapper.mapAddressDtoToAddress(addressDto);

        //then
        assertAll(
                () -> assertEquals(NAME, address.getName()),
                () -> assertEquals(SURNAME, address.getSurname()),
                () -> assertEquals(STREET, address.getStreet()),
                () -> assertEquals(CODE, address.getCode()),
                () -> assertEquals(CITY, address.getCity())
        );
    }

    @Test
    void mapAddressToAddressDto() {
        //given
        Address address = new Address(ID, NAME,SURNAME,STREET,CODE,CITY);

        //when
        AddressDto addressDto = addressMapper.mapAddressToAddressDto(address);

        //then
        assertAll(
                () -> assertEquals(NAME, addressDto.getName()),
                () -> assertEquals(SURNAME, addressDto.getSurname()),
                () -> assertEquals(STREET, addressDto.getStreet()),
                () -> assertEquals(CODE, addressDto.getCode()),
                () -> assertEquals(CITY, addressDto.getCity())
        );
    }
}