package com.hospital.auth;

public class DoctorUser extends User {
    private static final long serialVersionUID = 1L;

    public DoctorUser(String userId, String username, String passwordHash) {
        super(userId, username, passwordHash, Role.DOCTOR);
    }
}
