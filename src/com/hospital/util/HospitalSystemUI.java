package com.hospital.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.hospital.auth.AuthenticationManager;
import com.hospital.auth.Role;
import com.hospital.auth.User;
import com.hospital.logger.SystemLogger;
import com.hospital.service.AppointmentService;
import com.hospital.service.BillingService;
import com.hospital.service.DepartmentService;
import com.hospital.service.DoctorService;
import com.hospital.service.MedicalRecordService;
import com.hospital.service.PatientService;
import com.hospital.service.PharmacyService;
import com.hospital.service.PrescriptionService;
import com.hospital.service.ReportService;

public class HospitalSystemUI {
    private final Scanner sc;

    public HospitalSystemUI(Scanner sc) {
        this.sc = sc;
    }

    public void displayWelcome() {
        System.out.println("====================================");
        System.out.println("   Welcome to Hospital Management   ");
        System.out.println("====================================");
    }

    public int displayLoginMenu() {
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Choice: ");
        String line = sc.nextLine().trim();
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void displayExitMessage() {
        System.out.println("Thank you for using the Hospital Management System.");
    }

    public void displayError(String msg) {
        System.err.println(msg);
    }

    public boolean displayDashboard(
            User user,
            AuthenticationManager authManager,
            PatientService patientService,
            DoctorService doctorService,
            DepartmentService departmentService,
            AppointmentService appointmentService,
            MedicalRecordService medicalRecordService,
            PrescriptionService prescriptionService,
            PharmacyService pharmacyService,
            BillingService billingService,
            SystemLogger logger) {

        System.out.println();
        System.out.println("Logged in as: " + user.getUsername() + " (" + user.getRole() + ")");
        System.out.println("1. View Dashboard Summary");
        System.out.println("2. Patient Management");
        System.out.println("3. Doctor Management");
        System.out.println("4. Department Management");
        System.out.println("5. Appointment Management");
        System.out.println("6. Medical Record Management");
        System.out.println("7. Prescription Management");
        System.out.println("8. Pharmacy Management");
        System.out.println("9. Billing Management");
        if (user.getRole() == Role.ADMIN) {
            System.out.println("10. User Management");
            System.out.println("11. Logout");
        } else {
            System.out.println("10. Logout");
        }
        System.out.print("Choice: ");
        String line = sc.nextLine().trim();

        switch (line) {
            case "1":
                if (user.getRole() == Role.ADMIN) {
                    printReportSummary(patientService, doctorService, appointmentService, billingService, departmentService, pharmacyService);
                } else {
                    displayRoleSummary(user, patientService, doctorService, appointmentService, billingService);
                }
                return true;
            case "2":
                managePatients(patientService);
                return true;
            case "3":
                manageDoctors(doctorService);
                return true;
            case "4":
                manageDepartments(departmentService);
                return true;
            case "5":
                manageAppointments(appointmentService);
                return true;
            case "6":
                manageMedicalRecords(medicalRecordService);
                return true;
            case "7":
                managePrescriptions(prescriptionService);
                return true;
            case "8":
                managePharmacy(pharmacyService);
                return true;
            case "9":
                manageBilling(billingService);
                return true;
            case "10":
                if (user.getRole() == Role.ADMIN) {
                    handleUserManagement(authManager);
                } else {
                    return false;
                }
                return true;
            case "11":
                if (user.getRole() == Role.ADMIN) {
                    return false;
                }
                System.out.println("Unknown option.");
                return true;
            default:
                System.out.println("Unknown option.");
                return true;
        }
    }

    private void printReportSummary(PatientService patientService, DoctorService doctorService,
                                   AppointmentService appointmentService, BillingService billingService,
                                   DepartmentService departmentService, PharmacyService pharmacyService) {
        ReportService reportService = new ReportService();
        System.out.println("\n--- Hospital Reports ---");
        System.out.println("Total Patients: " + reportService.countPatients(patientService.getAllPatients()));
        System.out.println("Total Doctors: " + reportService.countDoctors(doctorService.getAllDoctors()));
        System.out.println("Total Appointments: " + reportService.countAppointments(appointmentService.getAllAppointments()));
        System.out.println("Daily Revenue: " + reportService.totalRevenue(billingService.sortByAmount()));
        Map<String, Long> deptStats = reportService.departmentStats(departmentService.getAllDepartments());
        System.out.println("Department Statistics:");
        deptStats.forEach((k, v) -> System.out.println("  - " + k + ": " + v));
        System.out.println("Low Stock Medicines:");
        reportService.lowStockMedicines(pharmacyService.getAllMedicines()).forEach(m ->
                System.out.println("  - " + m.getMedicineName() + " (" + m.getQuantityAvailable() + ")"));
    }

    private void managePatients(PatientService patientService) {
        while (true) {
            System.out.println("\n-- Patient Management --");
            System.out.println("1. Add Patient");
            System.out.println("2. View Patients");
            System.out.println("3. Search Patients");
            System.out.println("4. Delete Patient");
            System.out.println("5. Back");
            System.out.print("Choice: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.print("Full Name: ");
                    String name = sc.nextLine();
                    if (name == null || name.trim().isEmpty()) {
                        System.out.println("Full name cannot be empty.");
                        break;
                    }
                    System.out.print("Gender: ");
                    String gender = sc.nextLine();
                    System.out.print("Date of Birth (yyyy-mm-dd): ");
                    try {
                        patientService.addPatient(name.trim(), gender.trim(), LocalDate.parse(sc.nextLine()));
                        System.out.println("Patient added.");
                    } catch (Exception e) {
                        System.out.println("Invalid date format.");
                    }
                    break;
                case "2":
                    patientService.getAllPatients().forEach(p -> System.out.println("- " + p.getPatientId() + " | " + p.getFullName()));
                    break;
                case "3":
                    System.out.print("Search by name: ");
                    patientService.searchByName(sc.nextLine()).forEach(p -> System.out.println("- " + p.getPatientId() + " | " + p.getFullName()));
                    break;
                case "4":
                    System.out.print("Patient ID to delete: ");
                    patientService.deletePatient(sc.nextLine());
                    System.out.println("Patient deleted if found.");
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void manageDoctors(DoctorService doctorService) {
        while (true) {
            System.out.println("\n-- Doctor Management --");
            System.out.println("1. Add Doctor");
            System.out.println("2. View Doctors");
            System.out.println("3. Search Doctors");
            System.out.println("4. Delete Doctor");
            System.out.println("5. Back");
            System.out.print("Choice: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.print("Doctor Name: ");
                    String name = sc.nextLine();
                    if (name == null || name.trim().isEmpty()) {
                        System.out.println("Doctor name cannot be empty.");
                        break;
                    }
                    System.out.print("Specialization: ");
                    String specialization = sc.nextLine();
                    doctorService.addDoctor(name.trim(), specialization.trim());
                    System.out.println("Doctor added.");
                    break;
                case "2":
                    doctorService.getAllDoctors().forEach(d -> System.out.println("- " + d.getDoctorId() + " | " + d.getFullName() + " | " + d.getFullName()));
                    break;
                case "3":
                    System.out.print("Search by name: ");
                    doctorService.searchByName(sc.nextLine()).forEach(d -> System.out.println("- " + d.getDoctorId() + " | " + d.getFullName()));
                    break;
                case "4":
                    System.out.print("Doctor ID to delete: ");
                    doctorService.deleteDoctor(sc.nextLine());
                    System.out.println("Doctor deleted if found.");
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void manageDepartments(DepartmentService departmentService) {
        while (true) {
            System.out.println("\n-- Department Management --");
            System.out.println("1. Add Department");
            System.out.println("2. View Departments");
            System.out.println("3. Search Departments");
            System.out.println("4. Back");
            System.out.print("Choice: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.print("Department Name: ");
                    String name = sc.nextLine();
                    if (name == null || name.trim().isEmpty()) {
                        System.out.println("Department name cannot be empty.");
                        break;
                    }
                    System.out.print("Description: ");
                    String desc = sc.nextLine();
                    System.out.print("Head of Department: ");
                    String head = sc.nextLine();
                    System.out.print("Number of Staff: ");
                    try {
                        int staff = Integer.parseInt(sc.nextLine());
                        departmentService.addDepartment(name.trim(), desc.trim(), head.trim(), staff);
                        System.out.println("Department added.");
                    } catch (Exception e) {
                        System.out.println("Invalid staff count.");
                    }
                    break;
                case "2":
                    departmentService.getAllDepartments().forEach(d -> System.out.println("- " + d.getDepartmentId() + " | " + d.getDepartmentName()));
                    break;
                case "3":
                    System.out.print("Search by department name: ");
                    departmentService.searchByName(sc.nextLine()).forEach(d -> System.out.println("- " + d.getDepartmentId() + " | " + d.getDepartmentName()));
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void manageAppointments(AppointmentService appointmentService) {
        while (true) {
            System.out.println("\n-- Appointment Management --");
            System.out.println("1. Schedule Appointment");
            System.out.println("2. View Appointments");
            System.out.println("3. Cancel Appointment");
            System.out.println("4. Back");
            System.out.print("Choice: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.print("Patient ID: ");
                    String patientId = sc.nextLine();
                    System.out.print("Doctor ID: ");
                    String doctorId = sc.nextLine();
                    System.out.print("Department: ");
                    String dept = sc.nextLine();
                    System.out.print("Date (yyyy-mm-dd): ");
                    String date = sc.nextLine();
                    System.out.print("Time (HH:mm): ");
                    String time = sc.nextLine();
                    System.out.print("Reason: ");
                    String reason = sc.nextLine();
                    try {
                        appointmentService.scheduleAppointment(patientId, doctorId, dept, LocalDate.parse(date), LocalTime.parse(time), reason);
                        System.out.println("Appointment scheduled.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "2":
                    appointmentService.getAllAppointments().forEach(a -> System.out.println("- " + a.getAppointmentId() + " | " + a.getPatientId() + " | " + a.getDoctorId() + " | " + a.getAppointmentStatus()));
                    break;
                case "3":
                    System.out.print("Appointment ID: ");
                    appointmentService.cancelAppointment(sc.nextLine());
                    System.out.println("Appointment cancelled if found.");
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void manageMedicalRecords(MedicalRecordService medicalRecordService) {
        while (true) {
            System.out.println("\n-- Medical Record Management --");
            System.out.println("1. Add Medical Record");
            System.out.println("2. View Records");
            System.out.println("3. Back");
            System.out.print("Choice: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.print("Patient ID: ");
                    String patientId = sc.nextLine();
                    System.out.print("Doctor ID: ");
                    String doctorId = sc.nextLine();
                    System.out.print("Symptoms: ");
                    String symptoms = sc.nextLine();
                    System.out.print("Diagnosis: ");
                    String diagnosis = sc.nextLine();
                    System.out.print("Treatment: ");
                    String treatment = sc.nextLine();
                    System.out.print("Notes: ");
                    String notes = sc.nextLine();
                    medicalRecordService.addRecord(patientId, doctorId, symptoms, diagnosis, treatment, notes);
                    System.out.println("Medical record added.");
                    break;
                case "2":
                    medicalRecordService.getAll().forEach(r -> System.out.println("- " + r.getRecordId() + " | " + r.getPatientId() + " | " + r.getDiagnosis()));
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void managePrescriptions(PrescriptionService prescriptionService) {
        while (true) {
            System.out.println("\n-- Prescription Management --");
            System.out.println("1. Add Prescription");
            System.out.println("2. View Patient History");
            System.out.println("3. Back");
            System.out.print("Choice: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.print("Patient ID: ");
                    String patientId = sc.nextLine();
                    System.out.print("Doctor ID: ");
                    String doctorId = sc.nextLine();
                    System.out.print("Medicine Name: ");
                    String medicine = sc.nextLine();
                    System.out.print("Dosage: ");
                    String dosage = sc.nextLine();
                    System.out.print("Frequency: ");
                    String frequency = sc.nextLine();
                    System.out.print("Duration: ");
                    String duration = sc.nextLine();
                    prescriptionService.addPrescription(patientId, doctorId, medicine, dosage, frequency, duration);
                    System.out.println("Prescription added.");
                    break;
                case "2":
                    System.out.print("Patient ID: ");
                    prescriptionService.getHistory(sc.nextLine()).forEach(p -> System.out.println("- " + p.getPrescriptionId() + " | " + p.getMedicineName() + " | " + p.getDosage()));
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void managePharmacy(PharmacyService pharmacyService) {
        while (true) {
            System.out.println("\n-- Pharmacy Management --");
            System.out.println("1. Add Medicine");
            System.out.println("2. View Medicines");
            System.out.println("3. Dispense Medicine");
            System.out.println("4. Back");
            System.out.print("Choice: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.print("Medicine Name: ");
                    String name = sc.nextLine();
                    System.out.print("Category: ");
                    String category = sc.nextLine();
                    System.out.print("Quantity: ");
                    int qty = Integer.parseInt(sc.nextLine());
                    System.out.print("Unit Price: ");
                    double price = Double.parseDouble(sc.nextLine());
                    System.out.print("Expiry Date (yyyy-mm-dd): ");
                    LocalDate expiry = LocalDate.parse(sc.nextLine());
                    System.out.print("Supplier: ");
                    String supplier = sc.nextLine();
                    pharmacyService.addMedicine(name, category, qty, price, expiry, supplier);
                    System.out.println("Medicine added.");
                    break;
                case "2":
                    pharmacyService.getAllMedicines().forEach(m -> System.out.println("- " + m.getMedicineId() + " | " + m.getMedicineName() + " | qty=" + m.getQuantityAvailable()));
                    break;
                case "3":
                    System.out.print("Medicine ID: ");
                    String medId = sc.nextLine();
                    System.out.print("Quantity: ");
                    int dispenseQty = Integer.parseInt(sc.nextLine());
                    try {
                        pharmacyService.dispenseMedicine(medId, dispenseQty);
                        System.out.println("Medicine dispensed.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void manageBilling(BillingService billingService) {
        while (true) {
            System.out.println("\n-- Billing Management --");
            System.out.println("1. Generate Bill");
            System.out.println("2. View Bills");
            System.out.println("3. Record Payment");
            System.out.println("4. Back");
            System.out.print("Choice: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.print("Patient ID: ");
                    String patientId = sc.nextLine();
                    System.out.print("Service Description: ");
                    String service = sc.nextLine();
                    System.out.print("Amount: ");
                    double amount = Double.parseDouble(sc.nextLine());
                    System.out.print("Payment Method: ");
                    String method = sc.nextLine();
                    billingService.generateBill(patientId, service, amount, method);
                    System.out.println("Bill generated.");
                    break;
                case "2":
                    billingService.sortByAmount().forEach(b -> System.out.println("- " + b.getBillId() + " | " + b.getPatientId() + " | " + b.getAmount() + " | " + b.getPaymentStatus()));
                    break;
                case "3":
                    System.out.print("Bill ID: ");
                    String billId = sc.nextLine();
                    System.out.print("Status (PAID/PENDING): ");
                    billingService.recordPayment(billId, sc.nextLine().toUpperCase());
                    System.out.println("Payment recorded.");
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void handleUserManagement(AuthenticationManager authManager) {
        while (true) {
            System.out.println("\n-- User Management --");
            System.out.println("1. List Users");
            System.out.println("2. Create User");
            System.out.println("3. Back");
            System.out.print("Choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    listUsers(authManager.listUsers());
                    break;
                case "2":
                    createUser(authManager);
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid user-management option.");
                    break;
            }
        }
    }

    private void listUsers(List<User> users) {
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        System.out.println("\nUsers:");
        users.forEach(u -> System.out.println("- " + u.getUserId() + " | " + u.getUsername() + " | " + u.getRole()));
    }

    private void createUser(AuthenticationManager authManager) {
        System.out.print("Username: ");
        String username = sc.nextLine().trim();
        System.out.print("Password: ");
        String password = sc.nextLine().trim();
        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username and password cannot be blank.");
            return;
        }
        System.out.print("Role (ADMIN/DOCTOR/NURSE/RECEPTIONIST): ");
        String roleInput = sc.nextLine().trim().toUpperCase();
        try {
            Role role = Role.valueOf(roleInput);
            authManager.createUser(username, password, role);
            System.out.println("User created successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid role. Please choose one of: ADMIN, DOCTOR, NURSE, RECEPTIONIST");
        }
    }

    private void displayRoleSummary(User user, PatientService patientService, DoctorService doctorService, AppointmentService appointmentService, BillingService billingService) {
        System.out.println("--- Dashboard Summary ---");
        System.out.println("Total Patients: " + patientService.getAllPatients().size());
        System.out.println("Total Doctors: " + doctorService.getAllDoctors().size());
        System.out.println("Total Appointments: " + appointmentService.getAllAppointments().size());
        System.out.println("Total Bills: " + billingService.sortByAmount().size());
        switch (user.getRole()) {
            case ADMIN:
                System.out.println("Admin Dashboard: Total Patients: " + patientService.getAllPatients().size() + " | Total Doctors: " + doctorService.getAllDoctors().size());
                break;
            case DOCTOR:
                System.out.println("Doctor Dashboard: Today's Appointments: " + appointmentService.getAllAppointments().size());
                break;
            case NURSE:
                System.out.println("Nurse Dashboard: Assigned Patients: " + patientService.getAllPatients().size());
                break;
            case RECEPTIONIST:
                System.out.println("Receptionist Dashboard: Today's Appointments: " + appointmentService.getAllAppointments().size());
                break;
        }
    }
}
