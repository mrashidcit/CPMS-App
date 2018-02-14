package com.example.android.chemicalplantmanagementsystem.data.tables;

import android.util.Log;

import com.example.android.chemicalplantmanagementsystem.data.tables.providers.ProductionContract;

import java.io.Serializable;

/**
 * Created by Rashid Saleem on 15-Jan-18.
 */

public class Production implements Serializable {
    private int id; // Primary Key
    private String productionCode;
    private String productionName;
    private int productionStatus;
    private String description;
    private int branchId;
    private int departmentId;
    private int companyId;
    private int userId;

    /**
     *
     * @param productionName
     * @param productionCode
     * @param productionStatus
\     */
    public Production(String productionCode, String productionName, int productionStatus) {
        this.productionName = productionName;
        this.productionCode = productionCode;
        this.productionStatus = productionStatus;

    }

    /**
     *
     * @param id
     * @param productionName
     * @param productionCode
     * @param productionStatus
     * @param description
     */
    public Production(int id,  String productionName, String productionCode, int productionStatus, String description) {
        this.id = id;
        this.productionCode = productionCode;
        this.productionName = productionName;
        this.productionStatus = productionStatus;
        this.description = description;
    }

    // Setter Methods
    public void setId(int id) {
        this.id = id;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }

    public void setProductionCode(String productionCode) {
        this.productionCode = productionCode;
    }

    public void setProductionStatus(int productionStatus) {
        this.productionStatus = productionStatus;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Getter Methods

    public int getId() {
        return id;
    }

    public String getProductionName() {
        return productionName;
    }

    public String getProductionCode() {
        return productionCode;
    }

    public int getProductionStatus() {
        return productionStatus;
    }

    public String getDescription() {
        return description;
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

    public int getUserId() {
        return userId;
    }


    public static final String getStatusMessage(int status) {

        String msg;

        if (status == ProductionContract.ProductionEntry.PENDING_STATUS) {
            msg = "Pending";
        } else if (status == ProductionContract.ProductionEntry.APPROVED_STATUS) {
            msg = "Approved";
        } else if (status == ProductionContract.ProductionEntry.COMPLETED_STATUS) {
            msg = "Completed";
        } else {
            msg = "Unknown";
        }

        return msg;
    }

}
