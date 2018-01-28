package com.example.android.chemicalplantmanagementsystem;

import android.accessibilityservice.FingerprintGestureController;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.sax.RootElement;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ViewUtils;
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

import com.example.android.chemicalplantmanagementsystem.data.tables.Product;

import org.w3c.dom.Text;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class NewProductionFragment extends Fragment {


    private static final String LOG_TAG = NewProductionFragment.class.getSimpleName();
    private Spinner mSpinner;
    private Context mContext;
    LinearLayout mProductsListLinearlayout;
    private Button mAddMoreButtonView;



    private HashMap<Integer, Product> mProductsHashMap;
    private Integer[] mProductKeys;
    ArrayAdapter<String> mProductAdapterForSpinner;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_new_production, container, false);

        // Initialize Data Members
        mProductsHashMap = new HashMap<Integer, Product>();
        mProductAdapterForSpinner = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item);

        mSpinner = view.findViewById(R.id.sp_products);
        mContext = this.getContext();
        mProductsListLinearlayout = view.findViewById(R.id.ll_products_list);
        mAddMoreButtonView = (Button) view.findViewById(R.id.btn_add_more);

//        mProductAdapterForSpinner = ArrayAdapter.createFromResource(mContext, R.array.planets_array, android.R.layout.simple_spinner_dropdown_item);

        // Getting Data
        generateFakeData();

        // Apply the adapter to the spinner
        mSpinner.setAdapter(mProductAdapterForSpinner);

        // Setting Click Listeners
        mAddMoreButtonView.setOnClickListener(mAddMoreClickListener);

        mAddMoreButtonView.performClick();
//        mAddMoreButtonView.performClick();

        return view;
    }


    // Click Listeners for Buttons
    private View.OnClickListener mAddMoreClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {



            View view = LayoutInflater.from(mContext).inflate(R.layout.product_item, null);

            int productId  = mProductKeys[mSpinner.getSelectedItemPosition()];

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
            productQtyEditTextView.setText(productId + "");


//            Log.v(LOG_TAG, "tv = " + productNameTextView.getText().toString());

            mProductsListLinearlayout.addView(view);

            delButtonView.setOnClickListener(mDelButtonClickListener);

//            delButtonView.performClick();

//            Log.v(LOG_TAG, "delButtonView = " + delButtonView.getText().toString());


        }
    };

    private View.OnClickListener mDelButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            LinearLayout parent = (LinearLayout) v.getParent();

            parent.setVisibility(View);


        }
    };



    public void generateFakeData() {




        // Fake Products
        mProductsHashMap.put(12, new Product(12, "ad333", "Mineral Water", 0, "Pure May be Fresh Water"));
        mProductsHashMap.put(13, new Product(13, "ad333", "Fast Food", 0, "Pure May be Fresh Water"));
        mProductsHashMap.put(14, new Product(14, "ad333", "Fresh Air", 0, "Pure May be Fresh Water"));
        mProductsHashMap.put(15, new Product(15, "ad333", "Nothing", 0, "Pure May be Fresh Water"));
        mProductsHashMap.put(16, new Product(16, "ad333", "Something", 0, "Pure May be Fresh Water"));


        mProductKeys = mProductsHashMap.keySet().toArray(new Integer[mProductsHashMap.size()]);

        Log.v(LOG_TAG, "mProductKeys = " + mProductKeys.length );

        for (int i = 0; i < mProductKeys.length; i++) {
            mProductAdapterForSpinner.add(mProductsHashMap.get(mProductKeys[i]).getName());

        }

        

    }




}
