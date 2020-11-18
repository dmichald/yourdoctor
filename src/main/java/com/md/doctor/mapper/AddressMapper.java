package com.md.doctor.mapper;

import com.md.doctor.dto.address.AddressDto;
import com.md.doctor.models.Address;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {
    Address mapAddressDtoToAddress(AddressDto addressDto);

    AddressDto mapAddressToAddressDto(Address address);
}
