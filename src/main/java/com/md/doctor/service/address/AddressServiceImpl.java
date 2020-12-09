package com.md.doctor.service.address;

import com.md.doctor.dto.address.AddressDto;
import com.md.doctor.exception.EntityNotFoundException;
import com.md.doctor.mapper.AddressMapper;
import com.md.doctor.models.Address;
import com.md.doctor.repository.AddressRepo;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {
    private AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);
    private final AddressRepo addressRepository;

    public AddressServiceImpl(AddressRepo addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public void saveAddress(AddressDto addressDto) {
        addressRepository.save(addressMapper.mapAddressDtoToAddress(addressDto));
        log.debug("Save address with id: " + addressDto.getId());
    }

    @Override
    public AddressDto getAddressById(Long id) {
        return addressRepository.findById(id)
                .map(addressMapper::mapAddressToAddressDto)
                .orElseThrow(() -> new EntityNotFoundException("ADDRESS WITH GIVEN ID NOT EXIST: ID " + id));
    }

    @Transactional
    @Override
    public void updateAddress(Long addressId, AddressDto addressDto) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("ADDRESS NOT FOUND. ID: " + addressId));

        BeanUtils.copyProperties(addressDto, address);
        log.debug("Updated address with id: " + address.getId());
    }
}
