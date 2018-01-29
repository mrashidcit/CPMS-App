package com.example.android.chemicalplantmanagementsystem.data.tables;

import android.util.Log;

import com.example.android.chemicalplantmanagementsystem.network.QueryUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Rashid Saleem on 13-Jan-18.
 */

public class GatePass {

    private static final String LOG_TAG = GatePass.class.getSimpleName();
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


    // Fetch GatePasses from Network
    public static final String fetchGatePasses(String requestUrl) {
        // Create URL object
        URL url = QueryUtils.createUrl(requestUrl);
        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = QueryUtils.makeHttpRequest(url, "");

            Log.v(LOG_TAG, "jsonResponse = " + jsonResponse);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return jsonResponse;

    }


}

