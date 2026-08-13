package com.hospital.models;

import java.io.Serializable;

public class Department implements Serializable {
    private static final long serialVersionUID = 1L;

    private String departmentId;
    private String departmentName;
    private String description;
    private String headOfDepartment;
    private int numberOfStaff;

    public Department(String departmentId, String departmentName, String description, String headOfDepartment, int numberOfStaff) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.description = description;
        this.headOfDepartment = headOfDepartment;
        this.numberOfStaff = numberOfStaff;
    }

    public String getDepartmentId() { return departmentId; }
    public String getDepartmentName() { return departmentName; }
    public String getDescription() { return description; }
    public String getHeadOfDepartment() { return headOfDepartment; }
    public int getNumberOfStaff() { return numberOfStaff; }

    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    public void setDescription(String description) { this.description = description; }
    public void setHeadOfDepartment(String headOfDepartment) { this.headOfDepartment = headOfDepartment; }
    public void setNumberOfStaff(int numberOfStaff) { this.numberOfStaff = numberOfStaff; }
}
