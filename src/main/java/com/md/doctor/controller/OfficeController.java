package com.md.doctor.controller;

import com.md.doctor.dto.GetOfficeDto;
import com.md.doctor.service.office.OfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class OfficeController {
    private final OfficeService officeService;


    @GetMapping("/offices/specializations/{specializationId}")
    Page<GetOfficeDto> getOfficesBySpecializationId(@PathVariable Long specializationId, Pageable pageable) {
        return officeService.getOfficesByDoctorSpecializationId(specializationId, pageable);
    }
}
