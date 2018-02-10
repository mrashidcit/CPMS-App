package com.example.android.chemicalplantmanagementsystem.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.chemicalplantmanagementsystem.Api;
import com.example.android.chemicalplantmanagementsystem.R;
import com.example.android.chemicalplantmanagementsystem.data.tables.GatePass;
import com.example.android.chemicalplantmanagementsystem.data.tables.Material;
import com.example.android.chemicalplantmanagementsystem.data.tables.Product;
import com.example.android.chemicalplantmanagementsystem.data.tables.User;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.GatePassContract.GatePassEntry;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.MaterialContract.MaterialEntry;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.ProductContract.ProductEntry;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.UserContract;
import com.example.android.chemicalplantmanagementsystem.loaders.GatePassEditorLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class GatePassDetailFragment extends Fragment
                                implements LoaderManager.LoaderCallbacks{

    private static final String LOG_TAG = GatePassDetailFragment.class.getSimpleName();
    private static final int GATEPASS_DETAIL_LOADER_ID = 2001;

    private LinearLayout mProductLinearLayoutView;
    private LinearLayout mMaterialLinearLayoutView;
    private ViewGroup mContainerViewGroup;

    // GatePass Values View
    private TextView mPersonNameView;
    private TextView mContactPhoneView;
    private TextView mDestinationView;
    private TextView mRemarksView;

    private LoaderManager mLoaderManager;

    private GatePass mGatePass;
    private HashMap<Integer, Product> mProductHashMap;
    private HashMap<Integer, Material> mMaterialHashMap;
    private ArrayList<Product> mProductArrayList;
    private ArrayList<Material> mMaterialArrayList;


    public GatePassDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gate_pass_detail, container, false);

        // Initialize DataMembers
        mProductHashMap = new HashMap<Integer, Product>();
        mMaterialHashMap = new HashMap<Integer, Material>();
        mProductArrayList = new ArrayList<Product>();
        mMaterialArrayList = new ArrayList<Material>();

        // Getting GatePass Info. from Previous Fragment
        mGatePass = (GatePass) getArguments().getSerializable("gate_pass");
        mLoaderManager = getLoaderManager();

//        Log.v(LOG_TAG, "gatePass: " + mGatePass.getPersonName());

        mContainerViewGroup = container;
        mPersonNameView = (TextView) view.findViewById(R.id.tv_person_name);
        mContactPhoneView = (TextView) view.findViewById(R.id.tv_contact_phone);
        mDestinationView = (TextView) view.findViewById(R.id.tv_destination);
        mRemarksView = (TextView) view.findViewById(R.id.tv_remarks);
        mProductLinearLayoutView = (LinearLayout) view.findViewById(R.id.ll_product_list);
        mMaterialLinearLayoutView = (LinearLayout) view.findViewById(R.id.ll_material_list);

        // Showing GatePass Info. in Views
        mPersonNameView.setText(mGatePass.getPersonName());
        mPersonNameView.setTag(mGatePass.getId());
        mContactPhoneView.setText(mGatePass.getContactPhone());
        mDestinationView.setText(mGatePass.getDestination());
        mRemarksView.setText(mGatePass.getRemarks());


//        extractGatePassDetailFromJSONString();


        // Starting Loader
        ConnectivityManager connMgr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            if (mLoaderManager.getLoader(GATEPASS_DETAIL_LOADER_ID) != null) {
                mLoaderManager.destroyLoader(GATEPASS_DETAIL_LOADER_ID);
            }

            mLoaderManager.initLoader(GATEPASS_DETAIL_LOADER_ID, new Bundle(), this);

        } else {
            Toast.makeText(getContext(), "Unable to Connect to Network!" , Toast.LENGTH_LONG).show();
            Log.v(LOG_TAG, "Unable to Connect to Network!");
        }




        return  view;
    }


    // Add Product Item to the List
    private void addProductItemToList(Product product) {
//        Product product = new Product(1, "keji3387", "Water", "clean", 2);

        // Add Product Item in List
        LinearLayout productItemView = null;
        productItemView = (LinearLayout) getLayoutInflater().inflate(R.layout.gate_pass_products_list_item, mContainerViewGroup, false);
        TextView productNameView = (TextView) productItemView.getChildAt(0);
        TextView productQtyView = (TextView) productItemView.getChildAt(1);

        productNameView.setText(product.getName());
        productQtyView.setText(product.getPivotQuantity() + "");
        mProductLinearLayoutView.addView(productItemView);

    }


    // Add Material Item to the Materials List on View
    private void addMaterialItemToList(Material material) {
//        Material material = new Material(1, "3987lksjdif", "Dummy", "good", 2);

        // Add Material Item in List
        LinearLayout materialItemView = null;
        materialItemView = (LinearLayout) getLayoutInflater().inflate(R.layout.gate_pass_products_list_item, mContainerViewGroup, false);
        TextView materialNameView = (TextView) materialItemView.getChildAt(0);
        TextView materialQtyView = (TextView) materialItemView.getChildAt(1);
        materialNameView.setText(material.getName());
        materialQtyView.setText(material.getPivotQuantity() + "");
        mMaterialLinearLayoutView.addView(materialItemView);
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        Log.v(LOG_TAG, "onCreateLoader()");

        int requestCode = Api.CODE_GET_REQUEST;
        String token = User.getToken(getContext());
        int gatePassId = mGatePass.getId();

        if (Api.CODE_GET_REQUEST == requestCode) {

            args.putInt(GatePassEntry._ID, gatePassId);
            args.putInt(Api.REQUEST_CODE, requestCode);
            args.putString(UserContract.COLUMN_TOKEN, token);

            String urlString = Api.GATE_PASS_URL + "/" + gatePassId;

            return new GatePassEditorLoader(getContext(), urlString, args);

        }


        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        Log.v(LOG_TAG, "onLoadFinished()");

        if (data == null) {
            Log.v(LOG_TAG, "data null in onLoadFinished()");
            return;
        }
        String outputJsonString = data.toString();

        extractGatePassDetailFromJSONString(outputJsonString);


    }

    @Override
    public void onLoaderReset(Loader loader) {

    }


    // Extracting GatePass Data from JsonString
    public void extractGatePassDetailFromJSONString(String jsonResponseString) {

//        String jsonResponseString = "{\"gatepass\":{\"id\":1,\"person_name\":\"altaf driver\",\"contact_phone\":\"12345678901\",\"destination\":\"department1store\",\"remarks\":\"default remarks\",\"created_at\":\"2018-01-19 14:44:34\",\"updated_at\":\"2018-01-30 22:41:28\"},\"products\":[{\"id\":1,\"product_code\":\"HPfQjt3NIr\",\"name\":\"product1\",\"delete_status\":1,\"description\":\"product1 default\",\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"user_id\":1,\"unit_id\":1,\"created_at\":\"2018-01-19 14:44:28\",\"updated_at\":\"2018-01-19 14:44:28\",\"pivot\":{\"gate_id\":1,\"product_id\":1,\"quantity\":4,\"created_at\":\"2018-01-30 22:16:49\",\"updated_at\":\"2018-01-30 22:21:57\"}},{\"id\":2,\"product_code\":\"eJYZ6ZYsqnb\",\"name\":\"Mineral Water\",\"delete_status\":1,\"description\":\"sfe sdfg esdfe fse\",\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"user_id\":1,\"unit_id\":1,\"created_at\":\"2018-01-30 21:57:38\",\"updated_at\":\"2018-01-30 21:57:38\",\"pivot\":{\"gate_id\":1,\"product_id\":2,\"quantity\":6,\"created_at\":\"2018-01-30 22:21:57\",\"updated_at\":\"2018-01-30 22:21:57\"}}],\"materials\":[{\"id\":1,\"material_code\":\"qMnKffk7wW\",\"name\":\"material1\",\"delete_status\":1,\"description\":\"material1 default\",\"user_id\":1,\"unit_id\":1,\"branch_id\":1,\"department_id\":1,\"company_id\":1,\"created_at\":\"2018-01-19 14:44:28\",\"updated_at\":\"2018-01-19 14:44:28\",\"pivot\":{\"gate_id\":1,\"material_id\":1,\"quantity\":4,\"created_at\":\"2018-01-30 22:16:59\",\"updated_at\":\"2018-01-30 22:21:57\"}}]}\n";


        JSONObject root = null;
        JSONArray productJSONArray = null;
        JSONObject productJson = null;
        JSONArray materialJSONArray = null;
        JSONObject materialJson = null;

        try {
            root = new JSONObject(jsonResponseString);
            productJSONArray = root.getJSONArray(ProductEntry.TABLE_NAME);
            materialJSONArray = root.getJSONArray(MaterialEntry.TABLE_NAME);


            // Getting product data
            for (int i =0; i < productJSONArray.length(); i++){
                productJson = productJSONArray.getJSONObject(i);

                int productId = productJson.getInt(ProductEntry._ID);
                String productCode = productJson.getString(ProductEntry.COLUMN_PRODUCT_CODE);
                String productName = productJson.getString(ProductEntry.COLUMN_NAME);
                String productDescription = productJson.getString(ProductEntry.COLUMN_DESCRIPTION);
                int productQty = productJson.getJSONObject("pivot").getInt(ProductEntry.COLUMN_PIVOT_QTY);

                Product product = new Product(productId, productCode, productName, productDescription, productQty);
                addProductItemToList(product); // Add Product Item to List

                mProductArrayList.add(product);
            }


            // Extracting Material Data from JsonArray
            for (int i =0; i < materialJSONArray.length(); i++) {
                materialJson = materialJSONArray.getJSONObject(i);

                int id = materialJson.getInt(MaterialEntry._ID);
                String materialCode = materialJson.getString(MaterialEntry.COLUMN_MATERIAL_CODE);
                String name = materialJson.getString(MaterialEntry.COLUMN_NAME);
                String description = materialJson.getString(MaterialEntry.COLUMN_DESCRIPTION);
                int qty = materialJson.getJSONObject("pivot").getInt(MaterialEntry.COLUMN_PIVOT_QUANTITY);

                Material material = new Material(id, materialCode, name, description, qty);
                addMaterialItemToList(material); // add Material Item to List

                mMaterialArrayList.add(material);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
