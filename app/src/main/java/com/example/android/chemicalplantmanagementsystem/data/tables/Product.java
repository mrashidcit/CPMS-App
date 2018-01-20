package com.example.android.chemicalplantmanagementsystem.data.tables;

import android.os.health.TimerStat;

import java.sql.Timestamp;

/**
 * Created by Rashid Saleem on 19-Jan-18.
 */

public class Product {

    // Data Members of the Object
    private long id;
    private String productCode;
    private String name;
    private int deleteStatus;
    private String description;
    private long branchId;
    private long departmentId;
    private long companyId;
    private long userId;
    private long unitId;
    private Timestamp createdAt;



    public Product(long id, String productCode, String name, int deleteStatus, String description) {
        this.id = id;
        this.productCode = productCode;
        this.name = name;
        this.deleteStatus = deleteStatus;
        this.description = description;
    }

    public Product(String productCode, String name, int deleteStatus, String description) {
        this.productCode = productCode;
        this.name = name;
        this.deleteStatus = deleteStatus;
        this.description = description;
    }

    // Setter Methods


    public void setId(long id) {
        this.id = id;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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

    public void setBranchId(long branchId) {
        this.branchId = branchId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    // Getter Methods


    public long getId() {
        return id;
    }

    public String getProductCode() {
        return productCode;
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

    public long getBranchId() {
        return branchId;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public long getCompanyId() {
        return companyId;
    }

    public long getUserId() {
        return userId;
    }

    public long getUnitId() {
        return unitId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}
