# Hospital Management System

A Java console-based hospital management system that stores all data in local files without a database.

## Features
- User authentication with role-based access
- Patient, doctor, and department management
- Appointment scheduling and conflict checks
- Medical records, prescriptions, and pharmacy stock control
- Billing and payment processing
- Search, sorting, reports, and backup support
- Logging and file persistence

## Run
```bash
cd /workspaces/Hospital-Management-System
javac -d out $(find src -name "*.java")
java -cp out com.hospital.Main
```

## Default login
- Username: admin
- Password: admin123

## Notes
- All data is stored in the `data` folder.
- Logs are written to the `logs` folder.
- Automatic backup copies are stored in the `backups` folder.
