package com.example.android.chemicalplantmanagementsystem.data.tables;

/**
 * Created by Rashid Saleem on 15-Jan-18.
 */

public class Production {
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

     */
    public Production(String productionCode, String productionName, int productionStatus) {
        this.productionName = productionName;
        this.productionCode = productionCode;
        this.productionStatus = productionStatus;

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
}
