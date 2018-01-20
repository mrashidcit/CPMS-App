package com.example.android.chemicalplantmanagementsystem.data.tables;

import java.util.ArrayList;

/**
 * Created by Rashid Saleem on 13-Jan-18.
 */

public class GatePass {

    private long id;
    private String personName;
    private String itemName;
    private double quantity;
    private String destination;
    private String contactPhone;
    private String remarks;

    private ArrayList<Product> products;
    private ArrayList<Material> materials;

    /* Constructor */

    public GatePass() {
    }

    public GatePass(long id, String personName, String itemName, double quantity, String destination) {
        this.id = id;
        this.personName = personName;
        this.itemName = itemName;
        this.quantity = quantity;
        this.destination = destination;
    }

    public GatePass(String personName, String itemName, double quantity, String destination, String contactPhone, String remarks) {
        this.personName = personName;
        this.itemName = itemName;
        this.quantity = quantity;
        this.destination = destination;
        this.contactPhone = contactPhone;
        this.remarks = remarks;
    }


    // Setter Methods


    public void setId(long id) {
        this.id = id;
    }

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

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void setMaterials(ArrayList<Material> materials) {
        this.materials = materials;
    }

    // Getter Methods

    public long getId() {
        return id;
    }

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

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Material> getMaterials() {
        return materials;
    }
}
