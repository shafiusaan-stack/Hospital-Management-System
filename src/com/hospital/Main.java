package com.hospital;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import com.hospital.auth.AuthenticationManager;
import com.hospital.auth.User;
import com.hospital.backup.BackupManager;
import com.hospital.logger.SystemLogger;
import com.hospital.service.AppointmentService;
import com.hospital.service.BillingService;
import com.hospital.service.DepartmentService;
import com.hospital.service.DoctorService;
import com.hospital.service.MedicalRecordService;
import com.hospital.service.PatientService;
import com.hospital.service.PharmacyService;
import com.hospital.service.PrescriptionService;
import com.hospital.util.HospitalSystemUI;

/**
 * Main entry point for the Hospital Management System
 */
public class Main {
    private static AuthenticationManager authManager;
    private static HospitalSystemUI ui;
    private static SystemLogger logger;
    private static BackupManager backupManager;
    private static PatientService patientService;
    private static DoctorService doctorService;
    private static DepartmentService departmentService;
    private static AppointmentService appointmentService;
    private static MedicalRecordService medicalRecordService;
    private static PrescriptionService prescriptionService;
    private static PharmacyService pharmacyService;
    private static BillingService billingService;
    private static final Scanner SCANNER = new Scanner(System.in);

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
        authManager = new AuthenticationManager(SCANNER);
        ui = new HospitalSystemUI(SCANNER);
        backupManager = new BackupManager();
        patientService = new PatientService();
        doctorService = new DoctorService();
        departmentService = new DepartmentService();
        appointmentService = new AppointmentService();
        medicalRecordService = new MedicalRecordService();
        prescriptionService = new PrescriptionService();
        pharmacyService = new PharmacyService();
        billingService = new BillingService();
        
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
                    isLoggedIn = ui.displayDashboard(
                            currentUser,
                            authManager,
                            patientService,
                            doctorService,
                            departmentService,
                            appointmentService,
                            medicalRecordService,
                            prescriptionService,
                            pharmacyService,
                            billingService,
                            logger);
                    
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
