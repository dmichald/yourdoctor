package com.md.doctor.controller;

import com.md.doctor.dto.specilalization.SpecializationDto;
import com.md.doctor.dto.specilalization.SpecializationListDto;
import com.md.doctor.repository.SpecializationRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/specializations")
public class SpecializationController {
    private SpecializationRepo repository;

    public SpecializationController(SpecializationRepo repository) {
        this.repository = repository;
    }


    @GetMapping
    public SpecializationListDto getAllSpecializations() {
        List<SpecializationDto> specializations = repository.findAll().stream()
                .map(SpecializationDto::new)
                .collect(Collectors.toList());
        return new SpecializationListDto(specializations);
    }
}
