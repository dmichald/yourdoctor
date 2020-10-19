package com.md.doctor.repository;

import com.md.doctor.models.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface DoctorRepo extends PagingAndSortingRepository<Doctor, Long> {


    @Query("SELECT  d FROM Doctor d JOIN d.specializations s WHERE s.id = :categoryId")
    Page<Doctor> findBySpecializationId(@Param("categoryId") Long categoryId, Pageable pageable);

    Page<Doctor> findAllByNameContainingOrSurnameContaining(String name, String surname, Pageable pageable);

}
