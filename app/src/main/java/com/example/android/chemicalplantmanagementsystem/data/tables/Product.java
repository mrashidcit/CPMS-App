package com.example.android.chemicalplantmanagementsystem.data.tables;

import java.sql.Timestamp;

/**
 * Created by Rashid Saleem on 19-Jan-18.
 */

public class Product {

    // Data Members of the Object
    private int id;
    private String productCode;
    private String name;
    private int deleteStatus;
    private String description;
    private int branchId;
    private int departmentId;
    private int companyId;
    private int userId;
    private int unitId;
    private Timestamp createdAt;

    // Pivot Table Columns
    private int pivotGatePassId;
    private int pivotProductId;
    private int pivotQuantity;


    // Default Constructor
    public Product() {
    }

    public Product(int pivotProductId, int pivotQuantity) {
        this.pivotProductId = pivotProductId;
        this.pivotQuantity = pivotQuantity;
    }

    /**
     * Constructor
     *
     * @param id
     * @param productCode
     * @param name
     * @param deleteStatus
     * @param description
     */
    public Product(int id, String productCode, String name, int deleteStatus, String description) {
        this.id = id;
        this.productCode = productCode;
        this.name = name;
        this.deleteStatus = deleteStatus;
        this.description = description;
    }

    public Product(int id, String productCode, String name, int deleteStatus, String description, int userId, int pivotGatePassId, int pivotProductId, int pivotQuantity) {
        this.id = id;
        this.productCode = productCode;
        this.name = name;
        this.deleteStatus = deleteStatus;
        this.description = description;
        this.userId = userId;
        this.pivotGatePassId = pivotGatePassId;
        this.pivotProductId = pivotProductId;
        this.pivotQuantity = pivotQuantity;
    }

    /**
     * Constructor
     * @param id
     * @param productCode
     * @param name
     * @param deleteStatus
     * @param description
     * @param userId
     */
    public Product(int id, String productCode, String name, int deleteStatus, String description, int userId) {
        this.id = id;
        this.productCode = productCode;
        this.name = name;
        this.deleteStatus = deleteStatus;
        this.description = description;
        this.userId = userId;
    }


    /**
     * Constructor
     * @param productCode
     * @param name
     * @param deleteStatus
     * @param description
     */
    public Product(String productCode, String name, int deleteStatus, String description) {
        this.productCode = productCode;
        this.name = name;
        this.deleteStatus = deleteStatus;
        this.description = description;
    }

    // Setter Methods


    public void setId(int id) {
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

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    // Pivot Columns methods

    public void setPivotGatePassId(int pivotGatePassId) {
        this.pivotGatePassId = pivotGatePassId;
    }

    public void setPivotProductId(int pivotProductId) {
        this.pivotProductId = pivotProductId;
    }

    public void setPivotQuantity(int pivotQuantity) {
        this.pivotQuantity = pivotQuantity;
    }


    // Getter Methods


    public int getId() {
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

    public int getUnitId() {
        return unitId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    // Pivot Column Getter Methods

    public int getPivotGatePassId() {
        return pivotGatePassId;
    }

    public int getPivotProductId() {
        return pivotProductId;
    }

    public int getPivotQuantity() {
        return pivotQuantity;
    }
}
