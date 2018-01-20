package com.example.android.chemicalplantmanagementsystem.data.tables;

import java.util.ArrayList;

/**
 * Created by Rashid Saleem on 20-Jan-18.
 */

public class Material {

    private int id;
    private String materialCode;
    private String name;
    private int deleteStatus;
    private String description;
    private int userId;
    private int unitId;
    private int branchId;
    private int departmentId;
    private int companyId;



    // Contructor

    public Material() {
    }

    public Material(int id, String materialCode, String name, int deleteStatus, String description) {
        this.id = id;
        this.materialCode = materialCode;
        this.name = name;
        this.deleteStatus = deleteStatus;
        this.description = description;
    }

    // Setter Methods


    public void setId(int id) {
        this.id = id;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
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


    // Getter Methods


    public int getId() {
        return id;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public String getName() {
        return name;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public String getDescription() {
        return description;
    }

    public int getUserId() {
        return userId;
    }

    public int getUnitId() {
        return unitId;
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
}
