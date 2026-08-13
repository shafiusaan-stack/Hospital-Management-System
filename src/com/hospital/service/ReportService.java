package com.hospital.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.hospital.models.Appointment;
import com.hospital.models.Bill;
import com.hospital.models.Medicine;
import com.hospital.models.Patient;

public class ReportService {
    public long countPatients(List<Patient> patients) {
        return patients.size();
    }

    public long countAppointments(List<Appointment> appointments) {
        return appointments.size();
    }

    public long countDoctors(List<com.hospital.models.Doctor> doctors) {
        return doctors.size();
    }

    public double totalRevenue(List<Bill> bills) {
        return bills.stream().filter(b -> "PAID".equalsIgnoreCase(b.getPaymentStatus())).mapToDouble(Bill::getAmount).sum();
    }

    public Map<String, Long> departmentStats(List<com.hospital.models.Department> departments) {
        return departments.stream().collect(Collectors.groupingBy(com.hospital.models.Department::getDepartmentName, Collectors.counting()));
    }

    public Map<String, Long> stockByMedicine(List<Medicine> medicines) {
        return medicines.stream().collect(Collectors.groupingBy(Medicine::getMedicineName, Collectors.summingLong(m -> m.getQuantityAvailable())));
    }

    public double monthlyRevenue(List<Bill> bills, int year, int month) {
        return bills.stream()
                .filter(b -> b.getPaymentDate().getYear() == year && b.getPaymentDate().getMonthValue() == month)
                .filter(b -> "PAID".equalsIgnoreCase(b.getPaymentStatus()))
                .mapToDouble(Bill::getAmount)
                .sum();
    }

    public List<Medicine> lowStockMedicines(List<Medicine> medicines) {
        return medicines.stream().filter(m -> m.getQuantityAvailable() <= 10).sorted(Comparator.comparingInt(Medicine::getQuantityAvailable)).collect(Collectors.toList());
    }

    public List<Appointment> upcomingAppointments(List<Appointment> appointments, LocalDate date) {
        return appointments.stream().filter(a -> a.getAppointmentDate().isAfter(date) || a.getAppointmentDate().isEqual(date)).collect(Collectors.toList());
    }
}
