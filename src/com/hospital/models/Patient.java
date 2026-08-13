package com.hospital.models;

import java.io.Serializable;
import java.time.LocalDate;

public class Patient implements Serializable {
    private static final long serialVersionUID = 1L;

    private String patientId;
    private String fullName;
    private String gender;
    private LocalDate dob;

    public Patient(String patientId, String fullName, String gender, LocalDate dob) {
        this.patientId = patientId;
        this.fullName = fullName;
        this.gender = gender;
        this.dob = dob;
    }

    public String getPatientId() { return patientId; }
    public String getFullName() { return fullName; }
}
