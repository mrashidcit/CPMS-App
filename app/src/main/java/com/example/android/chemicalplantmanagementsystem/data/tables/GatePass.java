package com.example.android.chemicalplantmanagementsystem.data.tables;

/**
 * Created by Rashid Saleem on 13-Jan-18.
 */

public class GatePass {

    private String personName;
    private String itemName;
    private double quantity;
    private String destination;
    private String contactPhone;
    private String remarks;


    // Constructor
    public GatePass(String personName, String itemName, double quantity, String destination, String contactPhone, String remarks) {
        this.personName = personName;
        this.itemName = itemName;
        this.quantity = quantity;
        this.destination = destination;
        this.contactPhone = contactPhone;
        this.remarks = remarks;
    }


    // Setter Methods
    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    // Getter Methods
    public String getPersonName() {
        return personName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getItemName() {
        return itemName;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getDestination() {
        return destination;
    }
}
