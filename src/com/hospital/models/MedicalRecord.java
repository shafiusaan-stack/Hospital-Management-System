package com.hospital.models;

import java.io.Serializable;
import java.time.LocalDate;

public class MedicalRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private String recordId;
    private String patientId;
    private String doctorId;
    private String symptoms;
    private String diagnosis;
    private String treatmentGiven;
    private String medicalNotes;
    private LocalDate dateCreated;

    public MedicalRecord(String recordId, String patientId, String doctorId, String symptoms, String diagnosis, String treatmentGiven, String medicalNotes, LocalDate dateCreated) {
        this.recordId = recordId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.treatmentGiven = treatmentGiven;
        this.medicalNotes = medicalNotes;
        this.dateCreated = dateCreated;
    }

    public String getRecordId() { return recordId; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public String getSymptoms() { return symptoms; }
    public String getDiagnosis() { return diagnosis; }
    public String getTreatmentGiven() { return treatmentGiven; }
    public String getMedicalNotes() { return medicalNotes; }
    public LocalDate getDateCreated() { return dateCreated; }

    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public void setTreatmentGiven(String treatmentGiven) { this.treatmentGiven = treatmentGiven; }
    public void setMedicalNotes(String medicalNotes) { this.medicalNotes = medicalNotes; }
}
