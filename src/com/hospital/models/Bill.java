package com.hospital.models;

import java.io.Serializable;
import java.time.LocalDate;

public class Bill implements Serializable {
    private static final long serialVersionUID = 1L;

    private String billId;
    private String patientId;
    private String serviceDescription;
    private double amount;
    private LocalDate paymentDate;
    private String paymentMethod;
    private String paymentStatus;

    public Bill(String billId, String patientId, String serviceDescription, double amount, LocalDate paymentDate, String paymentMethod, String paymentStatus) {
        this.billId = billId;
        this.patientId = patientId;
        this.serviceDescription = serviceDescription;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }

    public String getBillId() { return billId; }
    public String getPatientId() { return patientId; }
    public String getServiceDescription() { return serviceDescription; }
    public double getAmount() { return amount; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getPaymentStatus() { return paymentStatus; }

    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    public void setAmount(double amount) { this.amount = amount; }
}
