package com.example.android.chemicalplantmanagementsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.chemicalplantmanagementsystem.data.tables.GatePass;

import java.util.ArrayList;

public class GatePassActivity extends AppCompatActivity {

    private final static String LOG_TAG = GatePassActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gate_pass);

        ArrayList<GatePass> gatePasses = new ArrayList<GatePass>();
        gatePasses.add(new GatePass("Ali", "03038789873", "hello", 3, "lkjdij kjdijff"));
        gatePasses.add(new GatePass("Ali", "03038789873", "hello", 3, "lkjdij kjdijff"));
        gatePasses.add(new GatePass("Ali", "03038789873", "hello", 3, "lkjdij kjdijff"));
        gatePasses.add(new GatePass("Ali", "03038789873", "hello", 3, "lkjdij kjdijff"));
        gatePasses.add(new GatePass("Ali", "03038789873", "hello", 3, "lkjdij kjdijff"));


        for(int i= 0; i <= gatePasses.size(); i++) {
            Log.v(LOG_TAG, gatePasses.get(i).getPersonName());
        }

    }
}
