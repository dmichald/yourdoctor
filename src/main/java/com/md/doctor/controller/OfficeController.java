package com.md.doctor.controller;

import com.md.doctor.dto.office.GetOfficeDto;
import com.md.doctor.dto.office.OfficeDetails;
import com.md.doctor.service.office.OfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class OfficeController {
    private final OfficeService officeService;


    @GetMapping("/offices/specializations/{specializationId}")
    Page<GetOfficeDto> getOfficesBySpecializationId(@PathVariable Long specializationId, Pageable pageable) {
        return officeService.getOfficesByDoctorSpecializationId(specializationId, pageable);
    }

    @GetMapping("/offices")
    Page<GetOfficeDto> filterOffices(@RequestParam(required = false) String name, @RequestParam(required = false) String city, @RequestParam(required = false) Long specializationId, Pageable pageable) {
        return officeService.findByNameOrSurnameAndCityAndSpecialization(name, city, specializationId, pageable);
    }

    @GetMapping("/offices/{id}")
    OfficeDetails getOfficeById(@PathVariable Long id) {
        return officeService.getById(id);
    }
}
