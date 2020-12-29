package doctor.mapper;

import doctor.dto.address.AddressDto;
import doctor.models.Address;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {
    Address mapAddressDtoToAddress(AddressDto addressDto);

    AddressDto mapAddressToAddressDto(Address address);
}
