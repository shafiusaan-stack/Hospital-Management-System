package com.hospital.storage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.hospital.models.Bill;

public class BillStorage {
    private static final String FILE = "data" + File.separator + "bills.ser";

    public static void saveBills(List<Bill> bills) {
        FileStorage.ensureDataDir();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))) {
            oos.writeObject(bills);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Bill> loadBills() {
        File f = new File(FILE);
        if (!f.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            return (List<Bill>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
