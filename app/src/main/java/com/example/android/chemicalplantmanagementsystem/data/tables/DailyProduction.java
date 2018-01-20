package com.example.android.chemicalplantmanagementsystem.data.tables;

/**
 * Created by Rashid Saleem on 20-Jan-18.
 */

public class DailyProduction {

    private long id;
    private int productId;
    private int produced;
    private int dispatches;
    private int saleReturn;
    private int received;
    private int branchId;
    private int departmentId;
    private int companyId;

    // Default Contructor
    public DailyProduction() {
    }

    /**
     *
     * @param productId
     * @param produced
     * @param dispatches
     * @param saleReturn
     * @param received
     */
    public DailyProduction(int productId, int produced, int dispatches, int saleReturn, int received) {
        this.productId = productId;
        this.produced = produced;
        this.dispatches = dispatches;
        this.saleReturn = saleReturn;
        this.received = received;
    }


    // Setter Methods

    public void setId(long id) {
        this.id = id;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProduced(int produced) {
        this.produced = produced;
    }

    public void setDispatches(int dispatches) {
        this.dispatches = dispatches;
    }

    public void setSaleReturn(int saleReturn) {
        this.saleReturn = saleReturn;
    }

    public void setReceived(int received) {
        this.received = received;
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

    public long getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public int getProduced() {
        return produced;
    }

    public int getDispatches() {
        return dispatches;
    }

    public int getSaleReturn() {
        return saleReturn;
    }

    public int getReceived() {
        return received;
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
