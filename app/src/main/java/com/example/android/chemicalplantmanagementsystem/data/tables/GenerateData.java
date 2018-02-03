package com.example.android.chemicalplantmanagementsystem.data.tables;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rashid Saleem on 30-Jan-18.
 */

public class GenerateData {

    private GenerateData() {

    }


    public static final HashMap<Integer, Product> fakeProducts() {

        HashMap<Integer, Product> productHashMap = new HashMap<Integer, Product>();

        productHashMap.put(1 ,new Product(1, "HPfQjt3NIr", "product1", 1, "product1 default"));
        productHashMap.put(2 ,new Product(2, "eJYZ6ZYsqnb", "Mineral Water", 1, "sfe sdfg esdfe fse"));
        productHashMap.put(3 ,new Product(3, "DwPQbJjtWxj", "Air Fresher", 1, "sdf sdf efs esfd"));

        return productHashMap;

    }


    public static final ArrayList<Product> getProductsArrayList() {

        ArrayList<Product> productArrayList = new ArrayList<Product>();

        productArrayList.add(new Product(1, "HPfQjt3NIr", "product1", 1, "product1 default"));
        productArrayList.add(new Product(2, "eJYZ6ZYsqnb", "Mineral Water", 1, "sfe sdfg esdfe fse"));
        productArrayList.add(new Product(3, "DwPQbJjtWxj", "Air Fresher", 1, "sdf sdf efs esfd"));

        return productArrayList;

    }




    // Return ProductsName ArrayList Mostly used for Spinner
    public static final ArrayList<String> getProductNamesArrayList(HashMap<Integer, Product> productHashMap) {

        ArrayList<String> productNamesArrayList = new ArrayList<String>();

        Integer[] mKeys = productHashMap.keySet().toArray(new Integer[productHashMap.size()]);

        for(int i =0; i < mKeys.length; i++) {

            productNamesArrayList.add(productHashMap.get(mKeys[i]).getName());

        }

        return productNamesArrayList;

    }


    // Return MaterialName ArrayList Mostly used for Spinner
    public static final ArrayList<String> getMaterialNamesArrayList(HashMap<Integer, Material> materialHashMap) {

        ArrayList<String> materialNamesArrayList = new ArrayList<String>();

        Integer[] mKeys = materialHashMap.keySet().toArray(new Integer[materialHashMap.size()]);

        for(int i =0; i < mKeys.length; i++) {

            materialNamesArrayList.add(materialHashMap.get(mKeys[i]).getName());
        }

        return materialNamesArrayList;

    }

    // Return HashMap of Materials
    public static final HashMap<Integer, Material> fakeMaterials() {

        HashMap<Integer, Material> materialHashMap = new HashMap<Integer, Material>();

        materialHashMap.put(1 , new Material(1, "ed33", "material1", 0, "for water purfication"));
        materialHashMap.put(2 , new Material(2, "ed32", "O2", 0, "for water purfication"));
        materialHashMap.put(3 , new Material(3, "ed39", "Dark Water", 0, "for water purfication"));

        return materialHashMap;
    }

}
