package com.hospital.storage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.hospital.models.Appointment;

public class AppointmentStorage {
    private static final String FILE = "data" + File.separator + "appointments.ser";

    public static void saveAppointments(List<Appointment> appointments) {
        FileStorage.ensureDataDir();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))) {
            oos.writeObject(appointments);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Appointment> loadAppointments() {
        File f = new File(FILE);
        if (!f.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            return (List<Appointment>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
