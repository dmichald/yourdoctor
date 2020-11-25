package com.md.doctor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.md.doctor.dto.address.AddDoctorDto;
import com.md.doctor.dto.address.AddressDto;
import com.md.doctor.dto.contact.ContactDto;
import com.md.doctor.dto.doctor.GetDoctorDto;
import com.md.doctor.dto.office.OfficeDto;
import com.md.doctor.dto.patient.PatientDto;
import com.md.doctor.dto.reseravtion.ReservationDto;
import com.md.doctor.models.*;
import com.md.doctor.models.security.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;


public class TestResource {
    public static final Pageable PAGEABLE = PageRequest.of(0, 5);
    public static final String USERNAME = "Username";
    public static final String PASSWORD = "Password";
    public static final Long ID = 1L;
    public static final String NAME = "Jan";
    public static final String SURNAME = "Kowal";
    public static final String STREET = "ul. 3 Maja";
    public static final String CODE = "33-232";
    public static final String CITY = "Warszawa";
    public static final String TEL_NUMBER = "878235623";
    public static final String EMAIL = "michal@gmail.com";
    public static final Doctor DOCTOR = new Doctor(ID, NAME, SURNAME, null);

    public static final Page<Doctor> DOCTOR_PAGE = new PageImpl<>(Collections.singletonList(DOCTOR), PAGEABLE, 3);
    public static final GetDoctorDto DOCTOR_DTO = new GetDoctorDto(ID, NAME, SURNAME, null);
    public static final AddDoctorDto ADD_DOCTOR_DTO = new AddDoctorDto(NAME, SURNAME, null);
    public static final Address ADDRESS = new Address(ID, NAME, SURNAME, STREET, CODE, CITY);


    public static final Page<GetDoctorDto> EXPECTED_PAGE = new PageImpl<>(Collections.singletonList(DOCTOR_DTO), PAGEABLE, 3);
    public static final Patient PATIENT = new Patient(ID, EMAIL, TEL_NUMBER, ADDRESS, new Reservation());
    public static final Contact CONTACT = new Contact(ID, TEL_NUMBER, EMAIL);
    public static final LocalDate DATE = LocalDate.now().plusYears(1);
    @JsonFormat(pattern = "MM:hh")
    public static final LocalTime START_TIME = LocalTime.now().plusMinutes(5);
    @JsonFormat(pattern = "MM:hh")
    public static final LocalTime RESERVATION_H = LocalTime.of(9,0);

    @JsonFormat(pattern = "MM:hh")
    public static final LocalTime START_WORK_TIME = LocalTime.of(8, 0);

    @JsonFormat(pattern = "MM:hh")
    public static final LocalTime END_WORK_TIME = LocalTime.of(16, 0);
    public static final int PRICE = 100;
    public static final int VISIT_DURATION = 30;



    public static final AddressDto ADDRESS_DTO = new AddressDto(ID, NAME, SURNAME, STREET, CODE, CITY);
    public static final PatientDto PATIENT_DTO = new PatientDto(ID,EMAIL,TEL_NUMBER,ADDRESS_DTO);
    public static final ContactDto CONTACT_DTO = new ContactDto(ID, TEL_NUMBER, EMAIL);
    public static final OfficeDto OFFICE_DTO = new OfficeDto(ID, DOCTOR_DTO, CONTACT_DTO, ADDRESS_DTO, new HashSet<>(), new User(),PRICE, START_WORK_TIME, END_WORK_TIME, VISIT_DURATION);
    public static final Office OFFICE = new Office(ID, DOCTOR, CONTACT, ADDRESS, new HashSet<>(), new User(), PRICE, Time.valueOf(START_WORK_TIME), Time.valueOf(END_WORK_TIME), VISIT_DURATION);
    public static final Reservation RESERVATION = new Reservation(ID, OFFICE, PATIENT, Date.valueOf(DATE), Time.valueOf(RESERVATION_H), Date.valueOf(DATE), false);
    public static final ReservationDto RESERVATION_DTO = new ReservationDto(ID, PATIENT_DTO, DATE, RESERVATION_H, false);
    public static final User USER = new User();
    public static final String TOKEN = UUID.randomUUID().toString();

    public static final Specialization SPECIALIZATION = new Specialization(ID, "TESTSPEC",new HashSet<>());

    public static ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
    }


}
