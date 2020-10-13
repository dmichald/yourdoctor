package com.md.doctor.repository;

import com.md.doctor.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepo extends JpaRepository<Patient, Long> {
}
