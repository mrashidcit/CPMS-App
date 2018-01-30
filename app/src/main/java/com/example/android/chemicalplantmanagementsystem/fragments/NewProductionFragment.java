package com.example.android.chemicalplantmanagementsystem.fragments;

import android.content.Context;
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

import com.example.android.chemicalplantmanagementsystem.R;
import com.example.android.chemicalplantmanagementsystem.data.tables.Product;
import com.example.android.chemicalplantmanagementsystem.data.tables.User;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.ProductContract.ProductEntry;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.ProductionContract.ProductionEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class NewProductionFragment extends Fragment {


    private static final String LOG_TAG = NewProductionFragment.class.getSimpleName();
    private TextView mProductionName;
    private TextView mProductionDescription;
    private Spinner mSpinner;
    private Context mContext;
    LinearLayout mProductsListLinearlayout;
    private Button mAddMoreButtonView;
    private Button mSaveButtonView;


    private JSONObject mNewProductionJson;


    private HashMap<Integer, Product> mProductsHashMap;
    private Integer[] mProductKeys;
    ArrayAdapter<String> mProductAdapterForSpinner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_production, container, false);

        // Initialize Data Members
        mProductsHashMap = new HashMap<Integer, Product>();
        mProductAdapterForSpinner = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item);
        mNewProductionJson = new JSONObject();

        mProductionName = (TextView) view.findViewById(R.id.tv_production_name);
        mProductionDescription = (TextView) view.findViewById(R.id.tv_production_description);
        mSpinner = view.findViewById(R.id.sp_products);
        mContext = this.getContext();
        mProductsListLinearlayout = view.findViewById(R.id.ll_products_list);
        mAddMoreButtonView = (Button) view.findViewById(R.id.btn_add_more);
        mSaveButtonView = (Button) view.findViewById(R.id.btn_save);

//        mProductAdapterForSpinner = ArrayAdapter.createFromResource(mContext, R.array.planets_array, android.R.layout.simple_spinner_dropdown_item);

        // Getting Data
        generateFakeData();

        // Apply the adapter to the spinner
        mSpinner.setAdapter(mProductAdapterForSpinner);

        // Setting Click Listeners
        mAddMoreButtonView.setOnClickListener(mAddMoreClickListener);
        mSaveButtonView.setOnClickListener(mSaveButtonClickListener);

        mAddMoreButtonView.performClick();
        mSpinner.setSelection(1);
        mAddMoreButtonView.performClick();
        mSpinner.setSelection(2);
        mAddMoreButtonView.performClick();
        mSpinner.setSelection(3);
        mAddMoreButtonView.performClick();

        mProductionName.setText("Marker");
        mProductionDescription.setText("Board Marker only used for whiteboards.");

//        saveProduction();
        mSaveButtonView.performClick(); // Auto Click when Fragment Fully Loaded and Save

        return view;
    }


    // Click Listeners for Buttons

    // Add More Button Listener
    private View.OnClickListener mAddMoreClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            View view = LayoutInflater.from(mContext).inflate(R.layout.product_item, null);

            int productId = mProductKeys[mSpinner.getSelectedItemPosition()];

            // If Product Already exist in the list then don't add again
            if (mProductsListLinearlayout.findViewById(productId) != null) {
                Log.v(LOG_TAG, "onClick() return;");
                return;
            }

            String productName = mProductsHashMap.get(productId).getName();

            LinearLayout llProductItem = view.findViewWithTag("product_item");
            TextView productNameTextView = view.findViewWithTag("tv_product_name");
            EditText productQtyEditTextView = view.findViewWithTag("et_product_qty");
            Button delButtonView = view.findViewWithTag("del");

            // ProductId is Id of LinearLayout and Tag of Delete Button
//            llProductItem.setId(View.generateViewId());

            llProductItem.setId(productId);
            delButtonView.setTag(productId);

            productNameTextView.setText(productName);
//            productQtyEditTextView.setText(productId + "");


//            Log.v(LOG_TAG, "tv = " + productNameTextView.getText().toString());

            mProductsListLinearlayout.addView(view);

            delButtonView.setOnClickListener(mDelButtonClickListener);

        }
    };

    // Delete Product Item Listener
    private View.OnClickListener mDelButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            LinearLayout parent = (LinearLayout) v.getParent();

            mProductsListLinearlayout.removeView(parent);

            Log.v(LOG_TAG, "Total Child = " + mProductsListLinearlayout.getChildCount());

        }
    };


    // Save Production Button Listener
    private View.OnClickListener mSaveButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            saveProduction();

            Toast.makeText(getContext(), "Saved SuccessFully!", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * Showing log of the products used in the selected production
     */
    public JSONArray getProductsJSONArray() {
        JSONObject product = new JSONObject();

        JSONArray productsArray = new JSONArray();


        for (int i = 0; i < mProductsListLinearlayout.getChildCount(); i++) {
            product = new JSONObject();
            LinearLayout item = (LinearLayout) mProductsListLinearlayout.getChildAt(i);

            TextView productNameTextView = (TextView) item.getChildAt(0);
            EditText productQtyEditTextView = (EditText) item.getChildAt(1);

            int productId = item.getId();
            String productName = productNameTextView.getText().toString();
            int qty = Integer.valueOf(productQtyEditTextView.getText().toString());

            try {
                product.put(ProductEntry._ID, productId);
                product.put(ProductEntry.QTY, qty);

                productsArray.put(i, product);

            } catch (JSONException e) {
                e.printStackTrace();
            }


//            Log.v(LOG_TAG, "(productId, productName, qty) = ( " + productId + " , " +
//                    productName + " , " + qty + " )"
//            );
        }

        return productsArray;
    }


    public void generateFakeData() {

        // Fake Products
        mProductsHashMap.put(12, new Product(12, "ad333", "Mineral Water", 0, "Pure May be Fresh Water"));
        mProductsHashMap.put(13, new Product(13, "ad333", "Fast Food", 0, "Pure May be Fresh Water"));
        mProductsHashMap.put(14, new Product(14, "ad333", "Fresh Air", 0, "Pure May be Fresh Water"));
        mProductsHashMap.put(15, new Product(15, "ad333", "Nothing", 0, "Pure May be Fresh Water"));
        mProductsHashMap.put(16, new Product(16, "ad333", "Something", 0, "Pure May be Fresh Water"));


        mProductKeys = mProductsHashMap.keySet().toArray(new Integer[mProductsHashMap.size()]);

        Log.v(LOG_TAG, "mProductKeys = " + mProductKeys.length);

        for (int i = 0; i < mProductKeys.length; i++) {
            mProductAdapterForSpinner.add(mProductsHashMap.get(mProductKeys[i]).getName());

        }


    }


    public void saveProduction() {

        User user = new User(getContext());

        String name = mProductionName.getText().toString();
        String productionCode = ProductionEntry.generateProductionCode();
        int status = 0;
        int deleteStatus = 0;
        String description = mProductionDescription.getText().toString();
        int branchId = user.getBranchId();
        int departmentId = user.getDepartmentId();
        int companyId = user.getCompanyId();
        int userId = user.getId();

        JSONArray productsJSONArray = getProductsJSONArray();


        try {
            mNewProductionJson.put(ProductionEntry.COLUMN_NAME, name);
            mNewProductionJson.put(ProductionEntry.COLUMN_PRODUCTION_CODE, productionCode);
            mNewProductionJson.put(ProductionEntry.COLUMN_DESCRIPTION, description);
            mNewProductionJson.put(ProductionEntry.COLUMN_STATUS, status);
            mNewProductionJson.put(ProductionEntry.COLUMN_DELETE_STATUS, deleteStatus);
            mNewProductionJson.put(ProductionEntry.COLUMN_BRANCH_ID, branchId);
            mNewProductionJson.put(ProductionEntry.COLUMN_DEPARTMENT_ID, departmentId);
            mNewProductionJson.put(ProductionEntry.COLUMN_USER_ID, userId);
            mNewProductionJson.put("products", productsJSONArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.v(LOG_TAG, "json = " + mNewProductionJson.toString());


    }


}
