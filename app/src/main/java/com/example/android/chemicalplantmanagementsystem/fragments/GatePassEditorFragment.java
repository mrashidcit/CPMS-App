package com.example.android.chemicalplantmanagementsystem.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
                    implements android.support.v4.app.LoaderManager.LoaderCallbacks{

    public static final String LOG_TAG = GatePassEditorFragment.class.getSimpleName();
    private static final int GATEPASS_EDITOR_LOADER_ID = 1002;
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

    private HashMap<Integer, Product> mAllProductHashMap;
    private Integer[] mProductKeys;
    private ArrayList<String> mProductNamesArrayList;
    private ArrayAdapter<String> mProductNamesArrayAdapter;
    private Button mDelProductButtonView;
    private Button mDelMaterialButtonView;

    private HashMap<Integer, Material> mAllMaterialHashMap;
    private Integer[] mMaterialKeys;
    private ArrayList<String> mMaterialNamesArrayList;
    private ArrayAdapter<String> mMaterialNamesArrayAdapter;

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

        // All data members related to Product Spinner
        mAllProductHashMap = GenerateData.fakeProducts();
        mProductKeys = mAllProductHashMap.keySet().toArray(new Integer[mAllProductHashMap.size()]);
        mProductNamesArrayList = GenerateData.getProductNamesArrayList(mAllProductHashMap);
        mProductNamesArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item);
        mProductNamesArrayAdapter.addAll(mProductNamesArrayList);
        mProductSpinner.setAdapter(mProductNamesArrayAdapter);

        // All data members related to Material Spinner
        mAllMaterialHashMap = GenerateData.fakeMaterials();
        mMaterialKeys = mAllMaterialHashMap.keySet().toArray(new Integer[mAllMaterialHashMap.size()]);
        mMaterialNamesArrayList = GenerateData.getMaterialNamesArrayList(mAllMaterialHashMap);
        mMaterialNamesArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item);
        mMaterialNamesArrayAdapter.addAll(mMaterialNamesArrayList);
        mMaterialSpinner.setAdapter(mMaterialNamesArrayAdapter);

        // Setting Click Listeners
        mSaveButtonView.setOnClickListener(mSaveButtonClicListener);
        mRefreshButtonView.setOnClickListener(mRefreshButtonClickListener);
        mAddProductButtonView.setOnClickListener(mAddProductClickListener);
        mAddMaterialButtonView.setOnClickListener(mAddMaterialClickListener);


        // Setting Values
        mPersonNameTextView.setText(mGatePass.getPersonName());
        mContactPhoneTextView.setText(mGatePass.getContactPhone());
        mDestinationTextView.setText(mGatePass.getDestination());
        mRemarksTextView.setText(mGatePass.getRemarks());

        mLoaderManager = getLoaderManager();

//        mSaveButtonView.performClick();

        HashMap<Integer, GatePass> gatePassHashMap = new HashMap<Integer, GatePass>();
        gatePassHashMap.put(mGatePass.getId(), mGatePass);

//        fetchGatePassDetail();

//        displayProducts();


        // Adding data automatically

        addProduct();
        mProductSpinner.setSelection(1);
        addProduct();
        mProductSpinner.setSelection(2);
        addProduct();


        addMaterial();
        mMaterialSpinner.setSelection(1);
        addMaterial();
        mMaterialSpinner.setSelection(2);
        addMaterial();

        // Called on Save Button Clicked
//        saveGatePass();

        return v;
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

    // Click Listeners Implementation
    private View.OnClickListener mSaveButtonClicListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            saveGatePass(); // Saving

//            showGatePassLog();
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



    // Save GatePass
    private void saveGatePass() {
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

            if (mLoaderManager.getLoader(GATEPASS_EDITOR_LOADER_ID) != null) {
                mLoaderManager.destroyLoader(GATEPASS_EDITOR_LOADER_ID);

            }

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

            return new GatePassEditorLoader(mContext, Api.GATE_PASS_URL, args);

        } else if (Api.CODE_POST_REQUEST == mRequestCode) {

            args.putInt(GatePassEntry._ID, mGatePass.getId());
            args.putString(UserContract.COLUMN_TOKEN, User.getToken(mContext));

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

//        String jsonString = "{\"status\":\"success\",\"msg\":\"Successfully Created!\"}";

        String jsonString = data.toString();

        Log.v(LOG_TAG, jsonString);

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);

            Log.v(LOG_TAG, "status: " + jsonObject.getString("status"));
            Log.v(LOG_TAG, "msg: " + jsonObject.getString("msg"));


        } catch (JSONException e) {
            e.printStackTrace();
        }




//        if (data == null) {
//            Toast.makeText(mContext, "Error in connectivity!", Toast.LENGTH_SHORT).show();
//
//            return;
//        }


//        JSONObject root;
//        JSONObject gatePassJson;
//        JSONObject productJson;
//        JSONObject materialJson;
//        JSONObject pivotJson;
//        try {
//            root = new JSONObject(data.toString());
//
//            // currently not using this becuase gatepass data already exists
//            gatePassJson = root.getJSONObject("gatepass");
//
//            JSONArray productsArray = root.getJSONArray("products");
//
//
//            for (int i = 0; i < productsArray.length(); i++) {
//                productJson = productsArray.getJSONObject(i);
//
//                int id = productJson.getInt(ProductEntry._ID);
//                String productCode = productJson.getString(ProductEntry.COLUMN_PRODUCT_CODE);
//                String name = productJson.getString(ProductEntry.COLUMN_NAME);
//                int deleteStatus = productJson.getInt(ProductEntry.COLUMN_DELETE_STATUS);
//                String description = productJson.getString(ProductEntry.COLUMN_DESCRIPTION);
//                int userId = productJson.getInt(ProductEntry.COLUMN_USER_ID);
//
//                pivotJson = productJson.getJSONObject("pivot");
//
//                // Pivot Table Columns
//                int pivotGatePassId = pivotJson.getInt(ProductEntry.COLUMN_PIVOT_GATE_PASS_ID);
//                int pivotProductId = pivotJson.getInt(ProductEntry.COLUMN_PIVOT_PRODUCT_ID);
//                int pivotQuantity = pivotJson.getInt(ProductEntry.COLUMN_PIVOT_QTY);
//
//                Product product = new Product(id, productCode, name, deleteStatus, description, userId, pivotGatePassId, pivotGatePassId, pivotQuantity);
//
//                mProductHashMap.put(id, product);
//
//            }
//
//            JSONArray materialsArray = root.getJSONArray("materials");
//
//            for (int i = 0; i < materialsArray.length(); i++) {
//                materialJson = materialsArray.getJSONObject(i);
//
//                int id = materialJson.getInt(MaterialEntry._ID);
//                String materialCode = materialJson.getString(MaterialEntry.COLUMN_MATERIAL_CODE);
//                String name = materialJson.getString(MaterialEntry.COLUMN_NAME);
//                int deleteStatus = materialJson.getInt(MaterialEntry.COLUMN_DELETE_STATUS);
//                String description = materialJson.getString(MaterialEntry.COLUMN_DESCRIPTION);
//                int userId = materialJson.getInt(MaterialEntry.COLUMN_USER_ID);
//
//                pivotJson = materialJson.getJSONObject("pivot");
//                int pivotGatePassId = pivotJson.getInt(MaterialEntry.COLUMN_PIVOT_GATE_PASS_ID);
//                int pivotMaterialId = pivotJson.getInt(MaterialEntry.COLUMN_PIVOT_MATERIAL_ID);
//                int pivotQuantity = pivotJson.getInt(MaterialEntry.COLUMN_PIVOT_QUANTITY);
//
//                Material material = new Material(id, materialCode, name, deleteStatus, description, userId, pivotGatePassId, pivotMaterialId, pivotQuantity);
//
//                mMaterialHashMap.put(id, material);
//
//            }
//
//
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        displayProducts();
//        displayMaterials();

//        Log.v(LOG_TAG, "response: " + data.toString());


//        HashMap<Integer, GatePass> gatePassHashMap = (HashMap<Integer, GatePass>) data;
//
//        if (gatePassHashMap.size() > 0) {
//
//            Toast.makeText(mContext, "Updated Successfully!", Toast.LENGTH_SHORT).show();
//
////            Log.v(LOG_TAG, "Updated Successfuly!");
//        }


    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader loader) {

    }
}
