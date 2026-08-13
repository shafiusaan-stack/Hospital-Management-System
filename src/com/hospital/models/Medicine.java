package com.hospital.models;

import java.io.Serializable;
import java.time.LocalDate;

public class Medicine implements Serializable {
    private static final long serialVersionUID = 1L;

    private String medicineId;
    private String medicineName;
    private String category;
    private int quantityAvailable;
    private double unitPrice;
    private LocalDate expiryDate;
    private String supplier;

    public Medicine(String medicineId, String medicineName, String category, int quantityAvailable, double unitPrice, LocalDate expiryDate, String supplier) {
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.category = category;
        this.quantityAvailable = quantityAvailable;
        this.unitPrice = unitPrice;
        this.expiryDate = expiryDate;
        this.supplier = supplier;
    }

    public String getMedicineId() { return medicineId; }
    public String getMedicineName() { return medicineName; }
    public String getCategory() { return category; }
    public int getQuantityAvailable() { return quantityAvailable; }
    public double getUnitPrice() { return unitPrice; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public String getSupplier() { return supplier; }

    public void setQuantityAvailable(int quantityAvailable) { this.quantityAvailable = quantityAvailable; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    public void setSupplier(String supplier) { this.supplier = supplier; }
}
