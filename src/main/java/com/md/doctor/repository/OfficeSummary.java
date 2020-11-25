package com.md.doctor.repository;

public interface OfficeSummary {
    String getId();

    String getName();

    String getSurname();

    String getSpecialization();

    default String getFullName() {
        return getName() + " " + getSurname();
    }
}
