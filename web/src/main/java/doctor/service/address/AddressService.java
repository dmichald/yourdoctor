package doctor.service.address;


import doctor.dto.address.AddressDto;

public interface AddressService {
    void saveAddress(AddressDto addressDto);

    AddressDto getAddressById(Long id);

    void updateAddress(Long addressId, AddressDto addressDto);
}
