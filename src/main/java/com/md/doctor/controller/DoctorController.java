package com.md.doctor.controller;

import com.md.doctor.dto.AddDoctorDto;
import com.md.doctor.dto.GetDoctorDto;
import com.md.doctor.service.doctor.DoctorService;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public void addDoctor(@RequestBody AddDoctorDto doctorDto) {
        doctorService.saveDoctor(doctorDto);
    }

    @GetMapping("{id}")
    public GetDoctorDto getDoctor(@PathVariable @NotNull Long id) {
        return doctorService.getDoctorById(id);
    }

}
