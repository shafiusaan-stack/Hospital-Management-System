package com.hospital.models;

import java.io.Serializable;

public class Doctor implements Serializable {
    private static final long serialVersionUID = 1L;

    private String doctorId;
    private String fullName;
    private String specialization;

    public Doctor(String doctorId, String fullName, String specialization) {
        this.doctorId = doctorId;
        this.fullName = fullName;
        this.specialization = specialization;
    }

    public String getDoctorId() { return doctorId; }
    public String getFullName() { return fullName; }
}
