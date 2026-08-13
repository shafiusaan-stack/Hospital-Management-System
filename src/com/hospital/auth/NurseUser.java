package com.hospital.auth;

public class NurseUser extends User {
    private static final long serialVersionUID = 1L;

    public NurseUser(String userId, String username, String passwordHash) {
        super(userId, username, passwordHash, Role.NURSE);
    }
}
