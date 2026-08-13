package com.hospital.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hospital.models.Department;
import com.hospital.storage.DepartmentStorage;
import com.hospital.util.IdGenerator;

public class DepartmentService {
    private final List<Department> departments;

    public DepartmentService() {
        this.departments = new ArrayList<>(DepartmentStorage.loadDepartments());
    }

    public void addDepartment(String name, String description, String head, int staffCount) {
        String id = IdGenerator.generate("DEP");
        departments.add(new Department(id, name, description, head, staffCount));
        save();
    }

    public List<Department> getAllDepartments() {
        return new ArrayList<>(departments);
    }

    public Optional<Department> findByName(String name) {
        return departments.stream().filter(d -> d.getDepartmentName().equalsIgnoreCase(name)).findFirst();
    }

    public List<Department> searchByName(String name) {
        return departments.stream()
                .filter(d -> d.getDepartmentName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void save() {
        DepartmentStorage.saveDepartments(departments);
    }
}
