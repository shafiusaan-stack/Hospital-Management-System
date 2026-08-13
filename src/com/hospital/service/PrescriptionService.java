package com.hospital.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.hospital.models.Prescription;
import com.hospital.storage.PrescriptionStorage;
import com.hospital.util.IdGenerator;

public class PrescriptionService {
    private final List<Prescription> prescriptions;

    public PrescriptionService() {
        this.prescriptions = new ArrayList<>(PrescriptionStorage.loadPrescriptions());
    }

    public void addPrescription(String patientId, String doctorId, String medicineName, String dosage, String frequency, String duration) {
        String id = IdGenerator.generate("PR");
        prescriptions.add(new Prescription(id, patientId, doctorId, medicineName, dosage, frequency, duration, LocalDate.now()));
        save();
    }

    public List<Prescription> getHistory(String patientId) {
        return prescriptions.stream().filter(p -> p.getPatientId().equalsIgnoreCase(patientId)).collect(Collectors.toList());
    }

    public void save() {
        PrescriptionStorage.savePrescriptions(prescriptions);
    }
}
