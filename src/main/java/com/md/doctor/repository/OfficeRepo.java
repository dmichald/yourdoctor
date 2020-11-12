package com.md.doctor.repository;

import com.md.doctor.models.Office;
import com.md.doctor.models.Specialization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfficeRepo extends JpaRepository<Office, Long> {

    Page<Office> findAllByDoctor_Specializations(Specialization specialization, Pageable pageable);

    @Query(value = "SELECT DISTINCT a.city FROM Office o  JOIN o.address a ON o.address.id = a.id")
    List<String> getCities();
}
