package com.hospital.models;

import java.io.Serializable;
import java.time.LocalDate;

public class Prescription implements Serializable {
    private static final long serialVersionUID = 1L;

    private String prescriptionId;
    private String patientId;
    private String doctorId;
    private String medicineName;
    private String dosage;
    private String frequency;
    private String duration;
    private LocalDate prescriptionDate;

    public Prescription(String prescriptionId, String patientId, String doctorId, String medicineName, String dosage, String frequency, String duration, LocalDate prescriptionDate) {
        this.prescriptionId = prescriptionId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.duration = duration;
        this.prescriptionDate = prescriptionDate;
    }

    public String getPrescriptionId() { return prescriptionId; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public String getMedicineName() { return medicineName; }
    public String getDosage() { return dosage; }
    public String getFrequency() { return frequency; }
    public String getDuration() { return duration; }
    public LocalDate getPrescriptionDate() { return prescriptionDate; }
}
