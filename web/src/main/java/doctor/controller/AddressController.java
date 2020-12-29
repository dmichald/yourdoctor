package doctor.controller;

import doctor.dto.address.AddressDto;
import doctor.service.address.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PutMapping("/offices/{officeId}/address/{addressId}")
    void updateAddress(@PathVariable Long officeId, @PathVariable Long addressId, @RequestBody AddressDto addressDto) {
        addressService.updateAddress(addressId, addressDto);
    }
}
