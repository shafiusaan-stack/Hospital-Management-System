package com.hospital.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.hospital.models.MedicalRecord;
import com.hospital.storage.MedicalRecordStorage;
import com.hospital.util.IdGenerator;

public class MedicalRecordService {
    private final List<MedicalRecord> records;

    public MedicalRecordService() {
        this.records = new ArrayList<>(MedicalRecordStorage.loadMedicalRecords());
    }

    public void addRecord(String patientId, String doctorId, String symptoms, String diagnosis, String treatmentGiven, String notes) {
        String recordId = IdGenerator.generate("REC");
        records.add(new MedicalRecord(recordId, patientId, doctorId, symptoms, diagnosis, treatmentGiven, notes, LocalDate.now()));
        save();
    }

    public List<MedicalRecord> getRecordsForPatient(String patientId) {
        return records.stream().filter(r -> r.getPatientId().equalsIgnoreCase(patientId)).collect(Collectors.toList());
    }

    public List<MedicalRecord> getAll() {
        return new ArrayList<>(records);
    }

    public void save() {
        MedicalRecordStorage.saveMedicalRecords(records);
    }
}
