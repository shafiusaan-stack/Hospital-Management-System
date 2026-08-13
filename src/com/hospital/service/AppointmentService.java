package com.hospital.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.hospital.models.Appointment;
import com.hospital.storage.AppointmentStorage;
import com.hospital.util.IdGenerator;

public class AppointmentService {
    private final List<Appointment> appointments;

    public AppointmentService() {
        this.appointments = new ArrayList<>(AppointmentStorage.loadAppointments());
    }

    public void scheduleAppointment(String patientId, String doctorId, String department, LocalDate date, LocalTime time, String reason) {
        boolean conflict = appointments.stream().anyMatch(a ->
                a.getDoctorId().equalsIgnoreCase(doctorId)
                        && a.getAppointmentDate().equals(date)
                        && a.getAppointmentTime().equals(time)
                        && !"CANCELLED".equalsIgnoreCase(a.getAppointmentStatus()));
        if (conflict) {
            throw new IllegalArgumentException("Doctor already has an appointment at that time.");
        }
        String id = IdGenerator.generate("APT");
        appointments.add(new Appointment(id, patientId, doctorId, department, date, time, reason, "SCHEDULED"));
        save();
    }

    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments);
    }

    public List<Appointment> sortByDate() {
        return appointments.stream()
                .sorted(Comparator.comparing(Appointment::getAppointmentDate).thenComparing(Appointment::getAppointmentTime))
                .collect(Collectors.toList());
    }

    public void cancelAppointment(String appointmentId) {
        appointments.stream().filter(a -> a.getAppointmentId().equalsIgnoreCase(appointmentId)).findFirst()
                .ifPresent(a -> a.setAppointmentStatus("CANCELLED"));
        save();
    }

    public void save() {
        AppointmentStorage.saveAppointments(appointments);
    }
}
