package com.hospital.backup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Instant;

import com.hospital.storage.FileStorage;

public class BackupManager {
    private static final String BACKUP_DIR = "backups";

    public BackupManager() {
        File b = new File(BACKUP_DIR);
        if (!b.exists()) b.mkdirs();
    }

    public void performBackup() {
        FileStorage.ensureDataDir();
        String dest = BACKUP_DIR + File.separator + "backup-" + Instant.now().toEpochMilli();
        File d = new File(dest);
        d.mkdirs();
        try {
            copyDirectory(new File("data"), d);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyDirectory(File src, File dest) throws IOException {
        if (!src.exists()) return;
        Files.walk(src.toPath()).forEach(source -> {
            Path relative = src.toPath().relativize(source);
            Path target = dest.toPath().resolve(relative);
            try {
                if (source.toFile().isDirectory()) {
                    Files.createDirectories(target);
                } else {
                    Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
