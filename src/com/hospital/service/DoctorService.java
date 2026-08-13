package com.hospital.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hospital.models.Doctor;
import com.hospital.storage.DoctorStorage;
import com.hospital.util.IdGenerator;

public class DoctorService {
    private final List<Doctor> doctors;

    public DoctorService() {
        this.doctors = new ArrayList<>(DoctorStorage.loadDoctors());
    }

    public void addDoctor(String fullName, String specialization) {
        String doctorId = IdGenerator.generate("DOC");
        doctors.add(new Doctor(doctorId, fullName, specialization));
        save();
    }

    public List<Doctor> getAllDoctors() {
        return new ArrayList<>(doctors);
    }

    public Optional<Doctor> findById(String doctorId) {
        return doctors.stream().filter(d -> d.getDoctorId().equalsIgnoreCase(doctorId)).findFirst();
    }

    public List<Doctor> searchByName(String name) {
        return doctors.stream()
                .filter(d -> d.getFullName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Doctor> sortByName() {
        return doctors.stream()
                .sorted(Comparator.comparing(Doctor::getFullName))
                .collect(Collectors.toList());
    }

    public void deleteDoctor(String doctorId) {
        doctors.removeIf(d -> d.getDoctorId().equalsIgnoreCase(doctorId));
        save();
    }

    public void save() {
        DoctorStorage.saveDoctors(doctors);
    }
}
