package com.md.doctor.repository;

import com.md.doctor.models.Office;
import com.md.doctor.models.Specialization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OfficeRepo extends JpaRepository<Office, Long> {
    Page<Office> findAllByDoctor_Specializations_Id(Specialization specialization, Pageable pageable);
}
