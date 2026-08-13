package com.hospital.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.hospital.storage.FileStorage;
import com.hospital.util.IdGenerator;
import com.hospital.util.InputValidator;
import com.hospital.util.PasswordUtils;

public class AuthenticationManager {
    private final List<User> users;
    private final Scanner scanner;

    public AuthenticationManager(Scanner scanner) {
        this.scanner = scanner;
        FileStorage.ensureDataDir();
        users = new ArrayList<>(FileStorage.loadUsers());
        if (users.isEmpty()) {
            createDefaultAdmin();
        }
    }

    private void createDefaultAdmin() {
        String id = IdGenerator.generate("USR");
        users.add(new Admin(id, "admin", PasswordUtils.hash("admin123")));
        saveUsers();
    }

    public void createUser(String username, String password, Role role) {
        if (!InputValidator.isNonEmpty(username) || !InputValidator.isNonEmpty(password)) {
            throw new IllegalArgumentException("Username and password cannot be empty.");
        }

        boolean exists = users.stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(username.trim()));
        if (exists) {
            throw new IllegalArgumentException("Username already exists.");
        }

        String id = IdGenerator.generate("USR");
        User user;
        switch (role) {
            case ADMIN:
                user = new Admin(id, username.trim(), PasswordUtils.hash(password));
                break;
            case DOCTOR:
                user = new DoctorUser(id, username.trim(), PasswordUtils.hash(password));
                break;
            case NURSE:
                user = new NurseUser(id, username.trim(), PasswordUtils.hash(password));
                break;
            case RECEPTIONIST:
                user = new ReceptionistUser(id, username.trim(), PasswordUtils.hash(password));
                break;
            default:
                throw new IllegalArgumentException("Unsupported role: " + role);
        }

        users.add(user);
        saveUsers();
    }

    public List<User> listUsers() {
        return new ArrayList<>(users);
    }

    public User login() {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        Optional<User> opt = users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst();

        if (opt.isPresent()) {
            User u = opt.get();
            if (u.getPasswordHash().equals(PasswordUtils.hash(password))) {
                return u;
            }
            System.out.println("Invalid credentials.");
        } else {
            System.out.println("User not found.");
        }
        return null;
    }

    public void saveUsers() {
        FileStorage.saveUsers(users);
    }
}
