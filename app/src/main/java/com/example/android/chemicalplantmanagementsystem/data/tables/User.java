package com.example.android.chemicalplantmanagementsystem.data.tables;

/**
 * Created by Rashid Saleem on 25-Jan-18.
 */

public class User {
    private int id;
    private String name;
    private String password;
    private int active;
    private String avatar;
    private int branchId;
    private int departmentId;
    private int companyId;
    private int deleteStatus;

    // Default Contructor
    public User() {

    }

    public User(int id, String name, String password, int active, String avatar, int branchId, int departmentId, int companyId, int deleteStatus) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.active = active;
        this.avatar = avatar;
        this.branchId = branchId;
        this.departmentId = departmentId;
        this.companyId = companyId;
        this.deleteStatus = deleteStatus;
    }

    // Setter Methods


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    // Getter Methods
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getActive() {
        return active;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getBranchId() {
        return branchId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }
}
