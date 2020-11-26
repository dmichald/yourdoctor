package com.md.doctor.controller;

import com.md.doctor.dto.doctor.AddDoctorDto;
import com.md.doctor.dto.doctor.GetDoctorDto;
import com.md.doctor.dto.doctor.UpdateDoctorNameAndSurnameDto;
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

    @GetMapping("/{id}")
    public GetDoctorDto getDoctor(@PathVariable @NotNull Long id) {
        return doctorService.getDoctorById(id);
    }

    @PutMapping("/{id}")
    void updateDoctor(@PathVariable Long id, @RequestBody UpdateDoctorNameAndSurnameDto doctor) {
        doctorService.updateDoctorNameAndSurname(id, doctor);
    }

    @PutMapping("/{doctorId}/specializations")
    void addSpecializationToDoctor(@PathVariable Long doctorId, @RequestParam Long specializationId) {
        doctorService.addSpecialization(specializationId, doctorId);
    }

    @DeleteMapping("/{doctorId}/specializations")
    void removeSpecializationToDoctor(@PathVariable Long doctorId, @RequestParam Long specializationId) {
        doctorService.removeSpecialization(specializationId, doctorId);
    }

}
