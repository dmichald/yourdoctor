package com.md.doctor.repository;

import com.md.doctor.models.Specialization;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("SpecializationRepo should")
class SpecializationRepoIntegrationTest {
    @Autowired
    SpecializationRepo specializationRepo;

    @Test
    @DisplayName(" correct save entity")
    void shouldSaveEntity() {

        //given
        Specialization specialization = Specialization.builder().name("Test").build();
        Long EXPECTED_ID = 8L; // file data.sql contains 7 specializations

        //when
        specializationRepo.save(specialization);

        //then
        Specialization fromDb = specializationRepo.getOne(EXPECTED_ID);
        assertEquals(specialization, fromDb);
    }

    @Test
    @DisplayName("return entity by given id")
    void getEntityById() {
        //given
        String EXPECTED_SPECIALIZATION_NAME = "Psychiatra";
        Long ID = 1L;

        //when
        Specialization fromDb = specializationRepo
                .findById(1L)
                .orElseThrow(RuntimeException::new);

        //then
        assertEquals(fromDb.getName(), EXPECTED_SPECIALIZATION_NAME);

    }
}