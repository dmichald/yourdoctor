package com.md.doctor.service.address;

import com.md.doctor.dto.address.AddressDto;

public interface AddressService {
    void saveAddress(AddressDto addressDto);

    AddressDto getAddressById(Long id);

    void updateAddress(Long addressId, AddressDto addressDto);
}
