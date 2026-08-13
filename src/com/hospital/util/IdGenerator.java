package com.hospital.util;

import java.time.Instant;

public class IdGenerator {
    public static String generate(String prefix) {
        return prefix + "-" + Instant.now().toEpochMilli();
    }
}
