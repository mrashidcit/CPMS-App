package com.example.android.chemicalplantmanagementsystem.generategatepass;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.chemicalplantmanagementsystem.R;
import com.example.android.chemicalplantmanagementsystem.data.tables.Product;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rashid Saleem on 22-Jan-18.
 */

public class ProductAdapter extends BaseAdapter {

    private static final String LOG_TAG = ProductAdapter.class.getSimpleName();
    private HashMap<Integer, Integer> mData;
    private Integer[] mKeys;
    private Context mContext;
    private HashMap<Integer, Product> mProductHashMap;


    public ProductAdapter(Context context, HashMap<Integer, Integer> data, ArrayList<Product> productArrayList) {
        mProductHashMap = new HashMap<Integer, Product>();

        mContext = context;
        mData = data;

        for(int i =0; i < productArrayList.size(); i++) {
            Product product = productArrayList.get(i);

            mProductHashMap.put(product.getId(), product);
        }


        mKeys = mData.keySet().toArray(new Integer[mData.size()]);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(mKeys[position]);
    }

    @Override
    public long getItemId(int position) {
        return mKeys[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.generategatepass_product_list_item, parent, false);
        }
        // Extracting Values
        final Integer key = mKeys[position];
        Integer qty = mData.get(key);
        Product currentProduct = mProductHashMap.get(key);

        TextView productNameView = (TextView) convertView.findViewById(R.id.tv_product_name);
        TextView productQtyView = (TextView) convertView.findViewById(R.id.tv_product_qty);
        Button productDelButtonView = (Button) convertView.findViewById(R.id.btn_delete_product_item);

        productNameView.setText(currentProduct.getName());
        productQtyView.setText(qty + "");

        productDelButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.remove(key);

                notifyDataSetChanged();
            }
        });

//        Log.v(LOG_TAG, "(product, qty) = ( " + currentProduct.getName() + " , " +
//                    qty + " )"
//        );

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

        mKeys = mData.keySet().toArray(new Integer[mData.size()]);
    }
}
