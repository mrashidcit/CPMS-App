package com.example.android.chemicalplantmanagementsystem.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.chemicalplantmanagementsystem.Api;
import com.example.android.chemicalplantmanagementsystem.R;
import com.example.android.chemicalplantmanagementsystem.data.tables.GatePass;
import com.example.android.chemicalplantmanagementsystem.data.tables.GenerateData;
import com.example.android.chemicalplantmanagementsystem.data.tables.Material;
import com.example.android.chemicalplantmanagementsystem.data.tables.Product;
import com.example.android.chemicalplantmanagementsystem.data.tables.User;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.GatePassContract.GatePassEntry;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.MaterialContract.MaterialEntry;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.ProductContract;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.ProductContract.ProductEntry;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.UserContract;
import com.example.android.chemicalplantmanagementsystem.loaders.GatePassEditorLoader;
import com.example.android.chemicalplantmanagementsystem.loaders.GatePassLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class GatePassEditorFragment extends Fragment
                    implements  android.support.v4.app.LoaderManager.LoaderCallbacks{

    public static final String LOG_TAG = GatePassEditorFragment.class.getSimpleName();
    private static final int GATEPASS_EDITOR_LOADER_ID = 1002;
    private static final int GATEPASS_LOAD_PRODUCT_MATERIALS_LOADER_ID = 1002;
    private static final java.lang.String REQUEST_CODE = "request_code";

    // Controls
    private EditText mPersonNameTextView;
    private EditText mContactPhoneTextView;
    private EditText mDestinationTextView;
    private EditText mRemarksTextView;
    private Button mSaveButtonView;
    private Button mRefreshButtonView;
    private LinearLayout mProductContainerView;
    private LinearLayout mMaterialContainerView;
    private ViewGroup containerViewGroup;
    private Spinner mProductSpinner;
    private Spinner mMaterialSpinner;
    private Button mAddProductButtonView;
    private Button mAddMaterialButtonView;

    private HashMap<Integer, Product> mAllProductHashMap; // All Products Comming from Database
    private Integer[] mProductKeys;
    private ArrayList<String> mProductNamesArrayList; // Used for Spinner
    private ArrayAdapter<String> mProductNamesArrayAdapter; // For spinner
    private Button mDelProductButtonView;
    private Button mDelMaterialButtonView;

    private HashMap<Integer, Material> mAllMaterialHashMap; // All Materials Comming from Database
    private Integer[] mMaterialKeys;
    private ArrayList<String> mMaterialNamesArrayList; // For Spinner
    private ArrayAdapter<String> mMaterialNamesArrayAdapter; // for Spinner

    private JSONArray mProductsJsonArray;
    private JSONArray mMaterialJsonArray;

    private GatePass mGatePass;
    private Context mContext;
    private android.support.v4.app.LoaderManager mLoaderManager;
    private int mRequestCode;

    // Products and Materials in the List of this GatePass
    private HashMap<Integer, Product> mProductHashMap;
    private HashMap<Integer, Material> mMaterialHashMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_gate_pass_editor, container, false);

//        mGatePass = (GatePass) getArguments().getSerializable("gate_pass");
        mGatePass = new GatePass(3, "Ali Usman", "03033329738", "Lahore", "Sincere");
        mContext = getContext();
        mLoaderManager = getLoaderManager();



        // Setting Controls
        mPersonNameTextView = (EditText) v.findViewById(R.id.et_persone_name);
        mContactPhoneTextView = (EditText)  v.findViewById(R.id.et_contact_phone);
        mDestinationTextView = (EditText) v.findViewById(R.id.et_destination);
        mRemarksTextView = (EditText) v.findViewById(R.id.et_remarks);
        mSaveButtonView = (Button) v.findViewById(R.id.btn_save);
        mRefreshButtonView = (Button) v.findViewById(R.id.btn_refresh);
        mProductContainerView = (LinearLayout) v.findViewById(R.id.products_container);
        mMaterialContainerView = (LinearLayout) v.findViewById(R.id.materials_container);
        mProductSpinner = (Spinner) v.findViewById(R.id.sp_product);
        mMaterialSpinner = (Spinner) v.findViewById(R.id.sp_material);
        mAddProductButtonView = (Button) v.findViewById(R.id.btn_add_product);
        mAddMaterialButtonView = (Button) v.findViewById(R.id.btn_add_material);

        containerViewGroup = container;

        // Initializin Data Members
        mMaterialHashMap = new HashMap<Integer, Material>();
        mProductHashMap = new HashMap<Integer, Product>();
        mAllProductHashMap = new HashMap<Integer, Product>();
        mAllMaterialHashMap = new HashMap<Integer, Material>();

        // All data members related to Product Spinner
//        mAllProductHashMap = GenerateData.fakeProducts();
//        mProductKeys = mAllProductHashMap.keySet().toArray(new Integer[mAllProductHashMap.size()]);
//        mProductNamesArrayList = GenerateData.getProductNamesArrayList(mAllProductHashMap);
//        mProductNamesArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item);
//        mProductNamesArrayAdapter.addAll(mProductNamesArrayList);
//        mProductSpinner.setAdapter(mProductNamesArrayAdapter);

        // All data members related to Material Spinner
//        mAllMaterialHashMap = GenerateData.fakeMaterials();
//        mMaterialKeys = mAllMaterialHashMap.keySet().toArray(new Integer[mAllMaterialHashMap.size()]);
//        mMaterialNamesArrayList = GenerateData.getMaterialNamesArrayList(mAllMaterialHashMap);
//        mMaterialNamesArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item);
//        mMaterialNamesArrayAdapter.addAll(mMaterialNamesArrayList);
//        mMaterialSpinner.setAdapter(mMaterialNamesArrayAdapter);


        // Setting Click Listeners
        mSaveButtonView.setOnClickListener(mSaveButtonClicListener);
        mRefreshButtonView.setOnClickListener(mRefreshButtonClickListener);
        mAddProductButtonView.setOnClickListener(mAddProductClickListener);
        mAddMaterialButtonView.setOnClickListener(mAddMaterialClickListener);


        // Entering Dummy data in EditeText Views
//        mPersonNameTextView.setText("Rashid Saleem");
//        mContactPhoneTextView.setText("09873987383");
//        mDestinationTextView.setText("Islamabad");
//        mRemarksTextView.setText("good service");



        HashMap<Integer, GatePass> gatePassHashMap = new HashMap<Integer, GatePass>();
        gatePassHashMap.put(mGatePass.getId(), mGatePass);

//        fetchGatePassDetail();

//        displayProducts();


        // Adding data automatically Product and Material List
//        addProduct();
//        mProductSpinner.setSelection(1);
//        addProduct();
//        mProductSpinner.setSelection(2);
//        addProduct();

//        addMaterial();
//        mMaterialSpinner.setSelection(1);
//        addMaterial();
//        mMaterialSpinner.setSelection(2);
//        addMaterial();

        // Called on Save Button Clicked
//        saveGatePass();

//        clearAllViews();

        Log.v(LOG_TAG, "onCreateView()");
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(LOG_TAG, "onStart()");
        getProductsAndMaterials();
//        extractProductAndMaterialFromJson();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    // Extract Product and Materials Items From Network
//    private void ProductAndMaterialFromJson() {
    private void extractProductAndMaterialFromJson(String jsonString) {

//        String jsonString = "{\"products\":[{\"id\":1,\"product_code\":\"HPfQjt3NIr\",\"name\":\"product1\",\"delete_status\":1,\"description\":\"product1 default\",\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"user_id\":1,\"unit_id\":1,\"created_at\":\"2018-01-19 14:44:28\",\"updated_at\":\"2018-01-19 14:44:28\"},{\"id\":2,\"product_code\":\"eJYZ6ZYsqnb\",\"name\":\"Mineral Water\",\"delete_status\":1,\"description\":\"sfe sdfg esdfe fse\",\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"user_id\":1,\"unit_id\":1,\"created_at\":\"2018-01-30 21:57:38\",\"updated_at\":\"2018-01-30 21:57:38\"},{\"id\":3,\"product_code\":\"DwPQbJjtWxj\",\"name\":\"Air Fresher\",\"delete_status\":1,\"description\":\"sdf sdf efs esfd\",\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"user_id\":1,\"unit_id\":1,\"created_at\":\"2018-01-30 21:58:41\",\"updated_at\":\"2018-01-30 21:58:41\"}],\"materials\":[{\"id\":1,\"material_code\":\"qMnKffk7wW\",\"name\":\"material1\",\"delete_status\":1,\"description\":\"material1 default\",\"user_id\":1,\"unit_id\":1,\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"created_at\":\"2018-01-19 14:44:28\",\"updated_at\":\"2018-01-19 14:44:28\"},{\"id\":2,\"material_code\":\"ksXfVlCgprL\",\"name\":\"O2\",\"delete_status\":1,\"description\":\"Oxegyn\",\"user_id\":1,\"unit_id\":1,\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"created_at\":\"2018-01-30 21:48:20\",\"updated_at\":\"2018-01-30 21:48:20\"},{\"id\":3,\"material_code\":\"NzuCKzqAGbD\",\"name\":\"Dark Water\",\"delete_status\":1,\"description\":\"kdi kjeijkl flkjikje\",\"user_id\":1,\"unit_id\":1,\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"created_at\":\"2018-01-30 21:55:10\",\"updated_at\":\"2018-01-30 21:56:19\"}]}";

        JSONObject root = null;
        JSONArray productsJsonArray = null;
        JSONArray materialsJsonArray = null;
        try {
            root = new JSONObject(jsonString);

            productsJsonArray = root.getJSONArray(ProductEntry.TABLE_NAME);

            int id ;
            String name;
            JSONObject productObject = null;

            for (int i =0; i < productsJsonArray.length(); i++) {

                productObject = productsJsonArray.getJSONObject(i);

                id = productObject.getInt(ProductEntry._ID);
                name = productObject.getString(ProductEntry.COLUMN_NAME);

                Product product = new Product(id, name);
                mAllProductHashMap.put(id, product);

                Log.v(LOG_TAG, "id , name : " + product.getId() + " , " + product.getName());
            }

            materialsJsonArray = root.getJSONArray(MaterialEntry.TABLE_NAME);

            JSONObject materialObject = null;
            for (int i = 0; i < materialsJsonArray.length(); i++) {
                materialObject = materialsJsonArray.getJSONObject(i);
                id = materialObject.getInt(MaterialEntry._ID);
                name = materialObject.getString(MaterialEntry.COLUMN_NAME);

                Material material = new Material(id, name);
                mAllMaterialHashMap.put(id, material);

//                Log.v(LOG_TAG, "id , name: " + material.getId() + " , " + material.getName());
            }

            // All data members related to Product Spinner
            mProductKeys = mAllProductHashMap.keySet().toArray(new Integer[mAllProductHashMap.size()]);
            mProductNamesArrayList = GenerateData.getProductNamesArrayList(mAllProductHashMap);
            mProductNamesArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item);
            mProductNamesArrayAdapter.addAll(mProductNamesArrayList);
            mProductSpinner.setAdapter(mProductNamesArrayAdapter);

            // All data members related to Material Spinner
            mMaterialKeys = mAllMaterialHashMap.keySet().toArray(new Integer[mAllMaterialHashMap.size()]);
            mMaterialNamesArrayList = GenerateData.getMaterialNamesArrayList(mAllMaterialHashMap);
            mMaterialNamesArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item);
            mMaterialNamesArrayAdapter.addAll(mMaterialNamesArrayList);
            mMaterialSpinner.setAdapter(mMaterialNamesArrayAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // Clear All Fields Called After Saving the GatePass to Database
    private void clearAllViews() {
        // Clear All Fields After Saving GatePass Data
        mPersonNameTextView.setText("");
        mContactPhoneTextView.setText("");
        mDestinationTextView.setText("");
        mRemarksTextView.setText("");
        // Removing Product Items from Product Container
        int totalProductItems = mProductContainerView.getChildCount();
        for (int i = 2; i < totalProductItems; i++) {

            if (mProductContainerView.getChildAt(2) != null) {
                mProductContainerView.removeViewAt(2);
            }

        }
        // Removing Material Items from Product Container
        int totalMaterialItems = mMaterialContainerView.getChildCount();
        for (int i = 2; i < totalProductItems; i++) {

            if (mMaterialContainerView.getChildAt(2) != null){
                mMaterialContainerView.removeViewAt(2);
            }

        }
    }

    // Add Product to List
    private void addProduct() {

        LinearLayout productItemView = null;

        productItemView = (LinearLayout) getLayoutInflater().inflate(R.layout.product_item, containerViewGroup, false);

        TextView productNameView = (TextView) productItemView.findViewById(R.id.tv_product_name);
        EditText productQtyView = (EditText) productItemView.findViewById(R.id.et_product_qty);
        mDelProductButtonView = (Button) productItemView.findViewById(R.id.btn_delete_product_item);


        int position = mProductSpinner.getSelectedItemPosition();

        Product product = mAllProductHashMap.get(mProductKeys[position]);
        String name = product.getName();
        int productId = product.getId();

        // Checking is that product already exist in the list
        for (int i = 2; i < mProductContainerView.getChildCount(); i++) {

            LinearLayout productItem = (LinearLayout) mProductContainerView.getChildAt(i);

            TextView productName = (TextView) productItem.getChildAt(0);

            int id = Integer.parseInt(productName.getTag().toString());

            if (productId == id) {

                Toast.makeText(getContext(), "Product Already added in the List!", Toast.LENGTH_SHORT).show();

                return;
            }

        }

        productNameView.setText(name);
        productNameView.setTag(productId);
        productItemView.setTag(productId);
        productQtyView.setText(1 + "");
        mDelProductButtonView.setOnClickListener(mDelProductButtonClickListener);

        mProductContainerView.addView(productItemView);


    }

    // Addd Material Item into List
    private void addMaterial() {

        int position = mMaterialSpinner.getSelectedItemPosition();

        int materialId = mMaterialKeys[position];

        for (int i =2; i < mMaterialContainerView.getChildCount(); i++) {

            LinearLayout materialItem = (LinearLayout) mMaterialContainerView.getChildAt(i);

            int id = Integer.parseInt(materialItem.getTag().toString());

            if (id == materialId) {

                Toast.makeText(getContext(), "Material Already Exist in the List!", Toast.LENGTH_SHORT).show();

                return;

            } // end if

        } // end for loop

        LinearLayout materialItemView = (LinearLayout) getLayoutInflater().inflate(R.layout.material_item, containerViewGroup, false);

        TextView materialNameView = (TextView) materialItemView.findViewById(R.id.tv_material_name);
        EditText materialQtyView = (EditText) materialItemView.findViewById(R.id.et_material_qty);
        mDelMaterialButtonView = (Button) materialItemView.findViewById(R.id.btn_delete_material_item);

        String materialName = mAllMaterialHashMap.get(materialId).getName();
        int materialQty = 1;

        materialItemView.setTag(materialId);
        materialNameView.setText(materialName);
        materialQtyView.setText(materialQty + "");
        mDelMaterialButtonView.setOnClickListener(mDelMaterialButtonClickListener);

        mMaterialContainerView.addView(materialItemView);

    }

    // called when gate Pass info is loaded
    private void displayProducts(){

        LinearLayout productItemView = null;

        Integer[] mKeys = mProductHashMap.keySet().toArray(new Integer[ mProductHashMap.size() ]);
        for (int i = 0; i < mProductHashMap.size(); i++) {
            productItemView = (LinearLayout) getLayoutInflater().inflate(R.layout.product_item, containerViewGroup, false );

            Product product = mProductHashMap.get(mKeys[i]);

            TextView productNameView = (TextView) productItemView.findViewWithTag("tv_product_name");
            EditText productQtyView = (EditText) productItemView.findViewWithTag("et_product_qty");
            Button delButtonView = (Button) productItemView.findViewWithTag("btn_del");

            productNameView.setText(product.getName());
            productQtyView.setText(product.getPivotQuantity() + "");

            mProductContainerView.addView(productItemView);
        }
    }

    // Call when gate Pass info is loaded
    private void displayMaterials() {

        Log.v(LOG_TAG, "Total Materials: " + mMaterialHashMap.size());

        LinearLayout materialItemView = null;

        Integer[] mKeys = mMaterialHashMap.keySet().toArray(new Integer[mMaterialHashMap.size()]);
        for (int i = 0; i < mKeys.length; i++) {
            materialItemView = (LinearLayout) getLayoutInflater().inflate(R.layout.material_item, containerViewGroup, false);

            Material material = mMaterialHashMap.get(mKeys[i]);

            TextView materialNameView = (TextView) materialItemView.findViewById(R.id.tv_material_name);
            EditText materialQtyView = (EditText) materialItemView.findViewById(R.id.et_material_qty);
            Button delButtonView = (Button) materialItemView.findViewById(R.id.btn_delete_material_item);

            materialNameView.setText(material.getName());
            materialQtyView.setText(material.getPivotQuantity() + "");
            delButtonView.setOnClickListener(mDelMaterialButtonClickListener);

            mMaterialContainerView.addView(materialItemView);

        }
    }

    private void toggleSaveButtonClickable() {

        if(mSaveButtonView.isClickable() == true) {
            mSaveButtonView.setClickable(false); //
            mSaveButtonView.setBackgroundColor(Color.WHITE);
        } else {
            mSaveButtonView.setClickable(true);
            mSaveButtonView.setBackgroundColor(Color.GRAY);
        }


    }

    // Click Listeners Implementation
    private View.OnClickListener mSaveButtonClicListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            saveGatePass(); // Saving


        }
    };


    private View.OnClickListener mRefreshButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            fetchGatePassDetail();
        }
    };

    // Add Product Click Listener
    private View.OnClickListener mAddProductClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            addProduct();

        }
    };

    // Add Material Click Listener
    private View.OnClickListener mAddMaterialClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addMaterial();
        }
    };

    // Delete Product item button Listener from List
    private View.OnClickListener mDelProductButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Getting Button Parent
            LinearLayout productItemView = (LinearLayout) v.getParent();
            // Getting productItemView Parent
            LinearLayout parentView = (LinearLayout) productItemView.getParent();

            parentView.removeView(productItemView);

        }
    };

    // Delete Material Item Button Listener from List
    private View.OnClickListener mDelMaterialButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Getting Button Parent
            LinearLayout materialItemView = (LinearLayout) v.getParent();
            // Getting materialItemView Parent
            LinearLayout parentView = (LinearLayout) materialItemView.getParent();

            parentView.removeView(materialItemView);
        }
    };



    // Update GatePass
    private void updateGatePass() {
        mGatePass.setPersonName(mPersonNameTextView.getText().toString());
        mGatePass.setContactPhone(mContactPhoneTextView.getText().toString());
        mGatePass.setDestination(mDestinationTextView.getText().toString());
        mGatePass.setRemarks(mRemarksTextView.getText().toString());

        // extract Products List
        ArrayList<Product> productArrayList = new ArrayList<Product>();
        ArrayList<Material> materialArrayList = new ArrayList<Material>();

        int productId, materialId, qty;
        // Products Info.
        for (int i = 2; i < mProductContainerView.getChildCount(); i++) {

            LinearLayout productItemView = (LinearLayout) mProductContainerView.getChildAt(i);
            EditText productQtyView = (EditText) productItemView.findViewById(R.id.et_product_qty);

            productId = Integer.parseInt(productItemView.getTag().toString());
            qty = Integer.parseInt(productQtyView.getText().toString());

            productArrayList.add(new Product(mGatePass.getId(), productId, qty));

            Log.v(LOG_TAG, "product: ( " + productId + " , " + qty + " )");

        }

        // Material Info.
        for (int i = 2; i < mMaterialContainerView.getChildCount(); i++) {

            LinearLayout materialItemView = (LinearLayout) mMaterialContainerView.getChildAt(i);
            EditText materialQtyView = (EditText) materialItemView.findViewById(R.id.et_material_qty);

            materialId = Integer.parseInt(materialItemView.getTag().toString());
            qty = Integer.parseInt(materialQtyView.getText().toString());

            materialArrayList.add(new Material(mGatePass.getId(), materialId, qty));

            Log.v(LOG_TAG, "material: ( " + materialId + " , " + qty + " )");
        }




//        ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//
//        if (networkInfo != null && networkInfo.isConnected()) {
//
//
//            if (mLoaderManager.getLoader(GATEPASS_EDITOR_LOADER_ID) != null) {
//                mLoaderManager.destroyLoader(GATEPASS_EDITOR_LOADER_ID);
//
//            }
//
//            mLoaderManager.initLoader(GATEPASS_EDITOR_LOADER_ID, null, this);
//
//
//        } else {
//
//            Toast.makeText(mContext, "Unable to Connect to Network!" , Toast.LENGTH_LONG).show();
//            Log.v(LOG_TAG, "Unable to Connect to Network!");
//        }

    }


    // Get Product and Material Items
    private void getProductsAndMaterials() {

        mRequestCode = Api.CODE_GET_REQUEST;
        ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Bundle args = new Bundle();
            args.putString("url", Api.GATE_PASS_CREATE_URL);
            mLoaderManager.initLoader(GATEPASS_LOAD_PRODUCT_MATERIALS_LOADER_ID, args, this);

        } else {

            Toast.makeText(mContext, "Unable to Connect to Network!" , Toast.LENGTH_LONG).show();
            Log.v(LOG_TAG, "Unable to Connect to Network!");
        }

    }


    // Save GatePass
    private void saveGatePass() {

        // Sanity Check to All Fields
        if (mPersonNameTextView.getText().toString().isEmpty() || mContactPhoneTextView.getText().toString().isEmpty() ||
                mDestinationTextView.getText().toString().isEmpty() || mRemarksTextView.getText().toString().isEmpty() ){
            Toast.makeText(getContext(), "Fill all fields!", Toast.LENGTH_LONG).show();
            return;
        } else if (mProductContainerView.getChildCount() <= 2 && mMaterialContainerView.getChildCount() <= 2) {
            Toast.makeText(getContext(), "Add at least on Product/Material in the List!", Toast.LENGTH_LONG).show();
            return;
        }

        toggleSaveButtonClickable();

        mGatePass.setPersonName(mPersonNameTextView.getText().toString());
        mGatePass.setContactPhone(mContactPhoneTextView.getText().toString());
        mGatePass.setDestination(mDestinationTextView.getText().toString());
        mGatePass.setRemarks(mRemarksTextView.getText().toString());

        // Extracting Products Info.
        for (int i =2; i < mProductContainerView.getChildCount(); i++) {

            LinearLayout productItemView = (LinearLayout) mProductContainerView.getChildAt(i);

            EditText qtyView = (EditText) productItemView.getChildAt(1);

            int gatePassId = mGatePass.getId();
            int productId = Integer.parseInt(productItemView.getTag().toString());
            int qty = Integer.parseInt(qtyView.getText().toString());
            mProductHashMap.put(productId, new Product(gatePassId, productId, qty));

        }

        // Extracting Material Info.
        for (int i = 2; i < mMaterialContainerView.getChildCount(); i++) {

            LinearLayout materialItemView = (LinearLayout) mMaterialContainerView.getChildAt(i);

            EditText qtyView = (EditText) materialItemView.getChildAt(1);

            int gatePassId = mGatePass.getId();
            int materialId = Integer.parseInt(materialItemView.getTag().toString());
            int qty = Integer.parseInt(qtyView.getText().toString());

            mMaterialHashMap.put(materialId, new Material(gatePassId, materialId, qty));

        }

        mRequestCode = Api.CODE_POST_REQUEST;
        ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {


            mLoaderManager.initLoader(GATEPASS_EDITOR_LOADER_ID, new Bundle(), this);

        } else {

            Toast.makeText(mContext, "Unable to Connect to Network!" , Toast.LENGTH_LONG).show();
            Log.v(LOG_TAG, "Unable to Connect to Network!");
        }
    }


    private void showGatePassLog() {
        Log.v(LOG_TAG, "GatePass = ( " + mGatePass.getId() + " , " + mGatePass.getPersonName() + " , " +
                mGatePass.getContactPhone() + " , " + mGatePass.getDestination() + " , " + mGatePass.getRemarks() + " )"

        );
    }

    private void fetchGatePassDetail() {

        mRequestCode = Api.CODE_GET_REQUEST;

        ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            Bundle bundle = new Bundle();
            bundle.putInt(REQUEST_CODE, mRequestCode);

            if (mLoaderManager.getLoader(GATEPASS_EDITOR_LOADER_ID) != null) {
                mLoaderManager.destroyLoader(GATEPASS_EDITOR_LOADER_ID);

            }

            mLoaderManager.initLoader(GATEPASS_EDITOR_LOADER_ID, bundle, this);


        } else {

            Toast.makeText(mContext, "Unable to Connect to Network!" , Toast.LENGTH_LONG).show();
            Log.v(LOG_TAG, "Unable to Connect to Network!");
        }

    }

    @Override
    public android.support.v4.content.Loader onCreateLoader(int id, Bundle args) {

        if (Api.CODE_GET_REQUEST == mRequestCode) {

            args.putInt(GatePassEntry._ID, mGatePass.getId());
            args.putString(UserContract.COLUMN_TOKEN, User.getToken(mContext));
            args.putInt(Api.REQUEST_CODE, mRequestCode);

            return new GatePassEditorLoader(mContext, args.getString("url"), args);

        } else if (Api.CODE_POST_REQUEST == mRequestCode) {

            args.putInt(GatePassEntry._ID, mGatePass.getId());
            args.putString(UserContract.COLUMN_TOKEN, User.getToken(mContext));
            args.putInt(Api.REQUEST_CODE, mRequestCode);

            Log.v(LOG_TAG, "onCreateLoader()");
            return new GatePassEditorLoader(mContext, mGatePass, mProductHashMap, mMaterialHashMap, Api.GATE_PASS_URL, args);

        }

        HashMap<Integer, GatePass> gatePassHashMap = new HashMap<Integer, GatePass>();

        Log.v(LOG_TAG, "onCreateLoader()");

        gatePassHashMap.put(mGatePass.getId(), mGatePass);

        return new GatePassEditorLoader(mContext, Api.GATE_PASS_URL, args);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader loader, Object data) {

        Log.v(LOG_TAG, "onLoadFinished()");

        if (data == null) {
            Log.v(LOG_TAG, "data is null onLoadFinished()");
            Toast.makeText(getContext(), "Unable to get data from Server!", Toast.LENGTH_LONG).show();
            toggleSaveButtonClickable();
            return;
        } else if (data.toString().isEmpty()) {
            Log.v(LOG_TAG, "Error: SocketTimeoutException");
            Toast.makeText(getContext(), "Error: SocketTimeoutException!", Toast.LENGTH_LONG).show();
            toggleSaveButtonClickable();
            return;
        }

        String jsonString = data.toString();

//        Log.v(LOG_TAG, "responseString: " + jsonString);

        JSONObject jsonObject = null;
        try {

            jsonObject = new JSONObject(jsonString);

            // If jsonString has Products Array It means we want Products and Materials
            // data to show in spinners
            if (jsonObject.optJSONArray(ProductEntry.TABLE_NAME) != null) {

                extractProductAndMaterialFromJson(jsonString);

            } else if (jsonObject.optString("status") !=  null) {
                Toast.makeText(getContext(), "GatePass Successfully Saved!", Toast.LENGTH_LONG).show();

                Log.v(LOG_TAG, "status , msg : " + jsonObject.getString("status") + " , " + jsonObject.getString("msg"));
                toggleSaveButtonClickable(); // Enable Clicking on Save Button
                clearAllViews(); // Clear All Fields after saving Data to the Database
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        getLoaderManager().destroyLoader(GATEPASS_EDITOR_LOADER_ID);

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader loader) {

    }


}
