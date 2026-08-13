package com.hospital.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.hospital.models.Medicine;
import com.hospital.storage.MedicineStorage;
import com.hospital.util.IdGenerator;

public class PharmacyService {
    private final List<Medicine> medicines;

    public PharmacyService() {
        this.medicines = new ArrayList<>(MedicineStorage.loadMedicines());
    }

    public void addMedicine(String medicineName, String category, int quantity, double unitPrice, LocalDate expiryDate, String supplier) {
        String id = IdGenerator.generate("MED");
        medicines.add(new Medicine(id, medicineName, category, quantity, unitPrice, expiryDate, supplier));
        save();
    }

    public List<Medicine> getAllMedicines() {
        return new ArrayList<>(medicines);
    }

    public List<Medicine> searchByName(String name) {
        return medicines.stream().filter(m -> m.getMedicineName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
    }

    public List<Medicine> sortByQuantity() {
        return medicines.stream().sorted(Comparator.comparingInt(Medicine::getQuantityAvailable).reversed()).collect(Collectors.toList());
    }

    public void dispenseMedicine(String medicineId, int quantity) {
        medicines.stream().filter(m -> m.getMedicineId().equalsIgnoreCase(medicineId)).findFirst().ifPresent(m -> {
            if (m.getExpiryDate().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Expired medicine cannot be dispensed.");
            }
            if (m.getQuantityAvailable() < quantity) {
                throw new IllegalArgumentException("Insufficient stock.");
            }
            m.setQuantityAvailable(m.getQuantityAvailable() - quantity);
            save();
        });
    }

    public void save() {
        MedicineStorage.saveMedicines(medicines);
    }
}
