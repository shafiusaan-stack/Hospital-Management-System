package com.hospital;

import com.hospital.auth.AuthenticationManager;
import com.hospital.auth.User;
import com.hospital.util.InputValidator;
import com.hospital.util.HospitalSystemUI;
import com.hospital.backup.BackupManager;
import com.hospital.logger.SystemLogger;

/**
 * Main entry point for the Hospital Management System
 */
public class Main {
    private static AuthenticationManager authManager;
    private static HospitalSystemUI ui;
    private static SystemLogger logger;
    private static BackupManager backupManager;

    public static void main(String[] args) {
        try {
            // Initialize system components
            initializeSystem();
            
            // Display welcome screen
            ui.displayWelcome();
            
            // Start automatic backup thread
            startBackupThread();
            
            // Main application loop
            runApplicationLoop();
            
        } catch (Exception e) {
            logger.logError("System Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void initializeSystem() {
        logger = SystemLogger.getInstance();
        authManager = new AuthenticationManager();
        ui = new HospitalSystemUI();
        backupManager = new BackupManager();
        
        logger.logInfo("Hospital Management System Initialized");
    }

    private static void startBackupThread() {
        Thread backupThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3600000); // Backup every hour
                    backupManager.performBackup();
                    logger.logInfo("Automatic backup completed");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        backupThread.setDaemon(true);
        backupThread.start();
    }

    private static void runApplicationLoop() {
        boolean isLoggedIn = false;
        User currentUser = null;

        while (true) {
            try {
                if (!isLoggedIn) {
                    // Display login menu
                    int choice = ui.displayLoginMenu();
                    
                    if (choice == 1) {
                        // Login
                        currentUser = authManager.login();
                        if (currentUser != null) {
                            isLoggedIn = true;
                            logger.logInfo("User logged in: " + currentUser.getUsername());
                        }
                    } else if (choice == 2) {
                        // Exit
                        ui.displayExitMessage();
                        logger.logInfo("System shutdown");
                        break;
                    } else {
                        ui.displayError("Invalid choice. Please try again.");
                    }
                } else {
                    // Display dashboard based on user role
                    isLoggedIn = ui.displayDashboard(currentUser);
                    
                    if (!isLoggedIn) {
                        currentUser = null;
                        logger.logInfo("User logged out");
                    }
                }
            } catch (Exception e) {
                logger.logError("Application Error: " + e.getMessage());
                ui.displayError("An error occurred. Please try again.");
            }
        }
    }
}
