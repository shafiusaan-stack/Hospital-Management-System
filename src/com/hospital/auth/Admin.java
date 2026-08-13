package com.hospital.auth;

public class Admin extends User {
    private static final long serialVersionUID = 1L;

    public Admin(String userId, String username, String passwordHash) {
        super(userId, username, passwordHash, Role.ADMIN);
    }
}
