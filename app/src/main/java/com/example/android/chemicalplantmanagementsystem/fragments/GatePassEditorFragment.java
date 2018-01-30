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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.chemicalplantmanagementsystem.Api;
import com.example.android.chemicalplantmanagementsystem.R;
import com.example.android.chemicalplantmanagementsystem.data.tables.GatePass;
import com.example.android.chemicalplantmanagementsystem.loaders.GatePassLoader;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class GatePassEditorFragment extends Fragment
                    implements android.support.v4.app.LoaderManager.LoaderCallbacks{

    private static final String LOG_TAG = GatePassEditorFragment.class.getSimpleName();
    private static final int GATEPASS_EDITOR_LOADER_ID = 1002;

    // Controls
    private EditText mPersonNameTextView;
    private EditText mContactPhoneTextView;
    private EditText mDestinationTextView;
    private EditText mRemarksTextView;
    private Button mSaveButtonView;

    private GatePass mGatePass;
    private Context mContext;
    android.support.v4.app.LoaderManager mLoaderManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_gate_pass_editor, container, false);

        mGatePass = (GatePass) getArguments().getSerializable("gate_pass");
        mContext = getContext();

        // Setting Controls
        mPersonNameTextView = (EditText) v.findViewById(R.id.et_persone_name);
        mContactPhoneTextView = (EditText)  v.findViewById(R.id.et_contact_phone);
        mDestinationTextView = (EditText) v.findViewById(R.id.et_destination);
        mRemarksTextView = (EditText) v.findViewById(R.id.et_remarks);
        mSaveButtonView = (Button) v.findViewById(R.id.btn_save);

        // Setting Click Listeners
        mSaveButtonView.setOnClickListener(mSaveButtonClicListener);


        // Setting Values
        mPersonNameTextView.setText(mGatePass.getPersonName());
        mContactPhoneTextView.setText(mGatePass.getContactPhone());
        mDestinationTextView.setText(mGatePass.getDestination());
        mRemarksTextView.setText(mGatePass.getRemarks());

        mLoaderManager = getLoaderManager();

//        mSaveButtonView.performClick();

        HashMap<Integer, GatePass> gatePassHashMap = new HashMap<Integer, GatePass>();
        gatePassHashMap.put(mGatePass.getId(), mGatePass);

        // Fuction to convert GatePass to JsonObject

//        Log.v(LOG_TAG, "gatePass = " + mGatePass.getId() + " , " + mGatePass.getPersonName());


        return v;
    }


    private View.OnClickListener mSaveButtonClicListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            saveGatePass(); // Saving

//            showGatePassLog();

        }
    };


    private void saveGatePass() {
        mGatePass.setPersonName(mPersonNameTextView.getText().toString());
        mGatePass.setContactPhone(mContactPhoneTextView.getText().toString());
        mGatePass.setDestination(mDestinationTextView.getText().toString());
        mGatePass.setRemarks(mRemarksTextView.getText().toString());

        ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {


            if (mLoaderManager.getLoader(GATEPASS_EDITOR_LOADER_ID) != null) {
                mLoaderManager.destroyLoader(GATEPASS_EDITOR_LOADER_ID);

            }

            mLoaderManager.initLoader(GATEPASS_EDITOR_LOADER_ID, null, this);


        } else {

            Toast.makeText(mContext, "Unable to Connect!" , Toast.LENGTH_LONG).show();

        }
    }




    private void showGatePassLog() {
        Log.v(LOG_TAG, "GatePass = ( " + mGatePass.getId() + " , " + mGatePass.getPersonName() + " , " +
                mGatePass.getContactPhone() + " , " + mGatePass.getDestination() + " , " + mGatePass.getRemarks() + " )"

        );
    }


    @Override
    public android.support.v4.content.Loader onCreateLoader(int id, Bundle args) {

        HashMap<Integer, GatePass> gatePassHashMap = new HashMap<Integer, GatePass>();

        Log.v(LOG_TAG, "onCreateLoader()");

        gatePassHashMap.put(mGatePass.getId(), mGatePass);

        return new GatePassLoader(mContext, Api.GATE_PASS_URL, gatePassHashMap, Api.CODE_PUT_REQUEST);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader loader, Object data) {

        if (data == null) {
            Toast.makeText(mContext, "Error in Saving!", Toast.LENGTH_SHORT).show();

            return;
        }


        HashMap<Integer, GatePass> gatePassHashMap = (HashMap<Integer, GatePass>) data;

        if (gatePassHashMap.size() > 0) {

            Toast.makeText(mContext, "Updated Successfully!", Toast.LENGTH_SHORT).show();

//            Log.v(LOG_TAG, "Updated Successfuly!");
        }


    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader loader) {

    }
}
