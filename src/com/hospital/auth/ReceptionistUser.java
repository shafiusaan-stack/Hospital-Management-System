package com.hospital.auth;

public class ReceptionistUser extends User {
    private static final long serialVersionUID = 1L;

    public ReceptionistUser(String userId, String username, String passwordHash) {
        super(userId, username, passwordHash, Role.RECEPTIONIST);
    }
}
