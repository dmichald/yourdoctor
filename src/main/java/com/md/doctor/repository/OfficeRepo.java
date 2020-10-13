package com.md.doctor.repository;

import com.md.doctor.models.Office;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficeRepo extends JpaRepository<Office, Long> {
}
