package com.hospital.util;

public class InputValidator {
    public static boolean isNonEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }
}
