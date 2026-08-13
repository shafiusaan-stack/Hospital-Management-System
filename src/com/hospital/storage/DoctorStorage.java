package com.hospital.storage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.hospital.models.Doctor;

public class DoctorStorage {
    private static final String DATA_DIR = "data";
    private static final String DOCTORS_FILE = DATA_DIR + File.separator + "doctors.ser";

    public static void saveDoctors(List<Doctor> doctors) {
        FileStorage.ensureDataDir();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DOCTORS_FILE))) {
            oos.writeObject(doctors);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Doctor> loadDoctors() {
        File f = new File(DOCTORS_FILE);
        if (!f.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            return (List<Doctor>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
