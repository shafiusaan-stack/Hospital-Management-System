package com.hospital.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.hospital.models.Bill;
import com.hospital.storage.BillStorage;
import com.hospital.util.IdGenerator;

public class BillingService {
    private final List<Bill> bills;

    public BillingService() {
        this.bills = new ArrayList<>(BillStorage.loadBills());
    }

    public void generateBill(String patientId, String serviceDescription, double amount, String paymentMethod) {
        String billId = IdGenerator.generate("BILL");
        bills.add(new Bill(billId, patientId, serviceDescription, amount, LocalDate.now(), paymentMethod, "PENDING"));
        save();
    }

    public List<Bill> getBillsForPatient(String patientId) {
        return bills.stream().filter(b -> b.getPatientId().equalsIgnoreCase(patientId)).collect(Collectors.toList());
    }

    public List<Bill> sortByAmount() {
        return bills.stream().sorted(Comparator.comparingDouble(Bill::getAmount).reversed()).collect(Collectors.toList());
    }

    public void recordPayment(String billId, String status) {
        bills.stream().filter(b -> b.getBillId().equalsIgnoreCase(billId)).findFirst().ifPresent(b -> b.setPaymentStatus(status));
        save();
    }

    public void save() {
        BillStorage.saveBills(bills);
    }
}
