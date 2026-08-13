package com.hospital.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hospital.models.Patient;
import com.hospital.storage.PatientStorage;
import com.hospital.util.IdGenerator;

public class PatientService {
    private final List<Patient> patients;

    public PatientService() {
        this.patients = new ArrayList<>(PatientStorage.loadPatients());
    }

    public void addPatient(String fullName, String gender, LocalDate dob) {
        String patientId = IdGenerator.generate("PAT");
        patients.add(new Patient(patientId, fullName, gender, dob));
        save();
    }

    public List<Patient> getAllPatients() {
        return new ArrayList<>(patients);
    }

    public Optional<Patient> findById(String patientId) {
        return patients.stream().filter(p -> p.getPatientId().equalsIgnoreCase(patientId)).findFirst();
    }

    public List<Patient> searchByName(String name) {
        return patients.stream()
                .filter(p -> p.getFullName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Patient> sortByName() {
        return patients.stream()
                .sorted(Comparator.comparing(Patient::getFullName))
                .collect(Collectors.toList());
    }

    public void deletePatient(String patientId) {
        patients.removeIf(p -> p.getPatientId().equalsIgnoreCase(patientId));
        save();
    }

    public void save() {
        PatientStorage.savePatients(patients);
    }
}
