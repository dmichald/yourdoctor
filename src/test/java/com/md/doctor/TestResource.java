package com.md.doctor;

import com.md.doctor.dto.*;
import com.md.doctor.models.*;
import com.md.doctor.models.security.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.sql.Time;
import java.util.Collections;
import java.util.UUID;


public class TestResource {
    public static final Pageable PAGEABLE = PageRequest.of(0, 5);
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
    public static final Date DATE = Date.valueOf("2022-01-01");
    public static final Time START_TIME = Time.valueOf("09:30:00");
    public static final Time END_TIME = Time.valueOf("09:45:00");


    public static final AddressDto ADDRESS_DTO = new AddressDto(ID, NAME, SURNAME, STREET, CODE, CITY);
    public static final ContactDto CONTACT_DTO = new ContactDto(ID, TEL_NUMBER, EMAIL);
    public static final OfficeDto OFFICE_DTO = new OfficeDto(ID, DOCTOR_DTO, CONTACT_DTO,ADDRESS_DTO, null, new User());
    public static final Office OFFICE = new Office(ID, DOCTOR, CONTACT,ADDRESS, null, new User());
    public static final Reservation RESERVATION = new Reservation(ID, OFFICE, PATIENT, DATE, START_TIME, END_TIME, DATE, false);
    public static final ReservationDto RESERVATION_DTO = new ReservationDto(ID, OFFICE_DTO, new PatientDto(), DATE, START_TIME, END_TIME, false);
    public static final User USER = new User();
    public static final String TOKEN = UUID.randomUUID().toString();


}
