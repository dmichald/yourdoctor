package com.md.doctor.mapper;

import com.md.doctor.dto.doctordto.AddDoctorDto;
import com.md.doctor.dto.doctordto.GetDoctorDto;
import com.md.doctor.models.Doctor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("DoctorMapper should")
class DoctorMapperTest {
    private static final Long ID = 1L;
    private static final String NAME = "Jan";
    private static final String SURNAME = "Kowal";
    private final DoctorMapper doctorMapper = DoctorMapper.INSTANCE;


    @Test
    @DisplayName("correct map Doctor to GetDoctorDto")
    void mapToGetDoctorDto() {
        //given
        Doctor doctor = new Doctor(ID, NAME, SURNAME, Collections.emptySet());

        //when
        GetDoctorDto dto = doctorMapper.mapToGetDoctorDto(doctor);

        //then
        assertAll(
                () -> assertEquals(ID, dto.getId()),
                () -> assertEquals(NAME, dto.getName()),
                () -> assertEquals(SURNAME, dto.getSurname()),
                () -> assertEquals(Collections.emptySet(), dto.getSpecializations())
        );
    }

/*    @Test
    @DisplayName("correct map AddDoctorDto to Doctor")
    void mapAddDoctorDtotoDoctor() {

        //given
        AddDoctorDto addDoctorDto = new AddDoctorDto(NAME, SURNAME, Collections.emptySet());

        //when
        Doctor doctor = doctorMapper.mapAddDoctorDtoToDoctor(addDoctorDto);

        //then
        assertAll(
                () -> assertEquals(NAME, doctor.getName()),
                () -> assertEquals(SURNAME, doctor.getSurname()),
                () -> assertEquals(Collections.emptySet(), doctor.getSpecializations())
        );

    }*/
}