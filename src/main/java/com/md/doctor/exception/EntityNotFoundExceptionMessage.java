package com.md.doctor.exception;

public class EntityNotFoundExceptionMessage {
    private EntityNotFoundExceptionMessage() {
    }

    public static String ADDRESS_NOT_FOUND(Long id) {
        return "ADDRESS NOT FOUND. ID: " + id;
    }

    public static String DOCTOR_NOT_FOUND(Long id) {
        return "DOCTOR NOT FOUND. ID: " + id;
    }
    public static String OFFICE_NOT_FOUND(Long id) {
        return "OFFICE NOT FOUND. ID: " + id;
    }
    public static String RESERVATION_NOT_FOUND(Long id) {
        return "RESERVATION NOT FOUND. ID: " + id;
    }
    public static String CONTACT_NOT_FOUND(Long id) {
        return "CONTACT NOT FOUND. ID: " + id;
    }
    public static String SPECIALIZATION_NOT_FOUND(Long id) {
        return "SPECIALIZATION NOT FOUND. ID: " + id;
    }
    public static String USER_NOT_FOUND(Long id) {
        return "USER NOT FOUND. ID: " + id;
    }

}
