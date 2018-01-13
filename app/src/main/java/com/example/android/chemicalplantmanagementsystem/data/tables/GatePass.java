package com.example.android.chemicalplantmanagementsystem.data.tables;

/**
 * Created by Rashid Saleem on 13-Jan-18.
 */

public class GatePass {

    private String personName;
    private String contactPhone;
    private String items;
    private double quantity;
    private String remarks;

    // Constructor
    public GatePass(String personName, String contactPhone, String items, double quantity, String remarks) {
        this.personName = personName;
        this.contactPhone = contactPhone;
        this.items = items;
        this.quantity = quantity;
        this.remarks = remarks;
    }

    // Setter Methods
    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    // Getter Methods
    public String getPersonName() {
        return personName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getItems() {
        return items;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getRemarks() {
        return remarks;
    }

}
