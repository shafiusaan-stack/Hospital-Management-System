package com.hospital.storage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.hospital.models.Prescription;

public class PrescriptionStorage {
    private static final String FILE = "data" + File.separator + "prescriptions.ser";

    public static void savePrescriptions(List<Prescription> prescriptions) {
        FileStorage.ensureDataDir();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))) {
            oos.writeObject(prescriptions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Prescription> loadPrescriptions() {
        File f = new File(FILE);
        if (!f.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            return (List<Prescription>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
