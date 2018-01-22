package com.example.android.chemicalplantmanagementsystem;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.chemicalplantmanagementsystem.data.tables.Material;
import com.example.android.chemicalplantmanagementsystem.data.tables.Product;
import com.example.android.chemicalplantmanagementsystem.generategatepass.MaterialAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GenerateGatePassActivity extends AppCompatActivity {


    private static final String LOG_TAG = "MainActivity";
    private EditText mPersonNameView;
    private EditText mContactPhoneView;
    private EditText mDestinationView;
    private EditText mRemarksView;
    private Spinner mMaterialSpinnerView;
    private EditText mMaterialQtyView;
    private Button mAddMaterialButtonView;
    private ListView mMaterialListView;
    private ListView mProductListView;

    private Button mSaveButtonView;


    // Data Objects
    private ArrayList<Product> mProductList;
    private ArrayList<Material> mMaterialList;

    private ArrayAdapter<String> mMaterialAdapter;
    private ArrayAdapter<String> mProductAdapter;

    // Material and Products passed in this GatePass
    private HashMap<Integer, Integer> mCurrentGatePassMaterial;
    private HashMap<Integer, Integer> mCurrentGatePassProduct;

    private MaterialAdapter mCurrentGatePassMaterialAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_gate_pass);

        mPersonNameView = (EditText) findViewById(R.id.et_persone_name);
        mContactPhoneView = (EditText) findViewById(R.id.et_contact_phone);
        mDestinationView = (EditText) findViewById(R.id.et_destination);
        mRemarksView = (EditText) findViewById(R.id.et_remarks);
        mSaveButtonView = (Button) findViewById(R.id.btn_save);
        mMaterialSpinnerView = (Spinner) findViewById(R.id.sp_material);
        mMaterialQtyView = (EditText) findViewById(R.id.et_material_qty);
        mAddMaterialButtonView = (Button) findViewById(R.id.btn_add_material);
        mMaterialListView = (ListView) findViewById(R.id.list_material);
        mProductListView = (ListView) findViewById(R.id.list_products);

        // Initializing Product List and Adapters
        mProductList = new ArrayList<Product>();
        mMaterialList = new ArrayList<Material>();
        mProductAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        mMaterialAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        mCurrentGatePassMaterial = new HashMap<Integer, Integer>();
        mCurrentGatePassMaterial.put(1, 23);
        mCurrentGatePassMaterialAdapter = new MaterialAdapter(this, mCurrentGatePassMaterial);

        mMaterialListView.setAdapter(mCurrentGatePassMaterialAdapter);


        // Setting Listener for the buttons
        mSaveButtonView.setOnClickListener(mSaveClickListener);
        mAddMaterialButtonView.setOnClickListener(mAddMaterialClickListener);

        generateFakeData();
        mMaterialSpinnerView.setAdapter(mMaterialAdapter);

    }
    /**
     * Click Listener for Save Button
     */
    private View.OnClickListener mSaveClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String personName = mPersonNameView.getText().toString();
            String contactPhone = mContactPhoneView.getText().toString();
            String destination = mDestinationView.getText().toString();
            String remarks = mRemarksView.getText().toString();


            Log.v(LOG_TAG,"(personName, contactPhone, destination, remarks ) = ( " +
                    personName + " , " + contactPhone + " , " + destination + " , " + remarks
                    + " )"
            );



        }
    };

    /**
     * Click Listener for Add Material
     */
    private View.OnClickListener mAddMaterialClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int index = mMaterialSpinnerView.getSelectedItemPosition();
            int qty = Integer.parseInt(mMaterialQtyView.getText().toString());
            Material currentMaterial = mMaterialList.get(index);

            mCurrentGatePassMaterial.put(currentMaterial.getId(), qty);

            Log.v(LOG_TAG,  "(material, qty) = ("  + currentMaterial.getName() +
                    " , " + mCurrentGatePassMaterial.get(currentMaterial.getId()) + " )"

            );
            Log.v(LOG_TAG, "MapSize = " + mCurrentGatePassMaterial.size());

            mMaterialListView.setAdapter(mCurrentGatePassMaterialAdapter);


        }
    };

    /**
     * Click Listener to Add Product
     */
    private View.OnClickListener mAddProductClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


//            Product currentProduct = mProductList.get()
        }
    };



    public void generateFakeData() {
        // Fake Products
        mProductList.add(new Product(12, "ad333", "Mineral Water", 0, "Pure May be Fresh Water"));
        mProductList.add(new Product(13, "ad333", "Fast Food", 0, "Pure May be Fresh Water"));
        mProductList.add(new Product(14, "ad333", "Fresh Air", 0, "Pure May be Fresh Water"));
        mProductList.add(new Product(15, "ad333", "Nothing", 0, "Pure May be Fresh Water"));
        mProductList.add(new Product(16, "ad333", "Something", 0, "Pure May be Fresh Water"));


        // Fake Material
        mMaterialList.add(new Material(12, "ed33", "ab", 0, "for water purfication"));
        mMaterialList.add(new Material(13, "ed32", "sand", 0, "for water purfication"));
        mMaterialList.add(new Material(14, "ed39", "Oxegyn", 0, "for water purfication"));


        for (int i = 0; i <  mMaterialList.size(); i++) {
            mMaterialAdapter.add(mMaterialList.get(i).getName().toString());
        }

//        mProductAdapter.addAll(mProductList);


    }

    public void saveGatePass() {

    }


    public void addMaterial() {


    }

}
