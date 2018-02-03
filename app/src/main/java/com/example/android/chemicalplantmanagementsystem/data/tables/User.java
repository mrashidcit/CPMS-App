package com.example.android.chemicalplantmanagementsystem.data.tables;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.android.chemicalplantmanagementsystem.R;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.UserContract;
import com.example.android.chemicalplantmanagementsystem.data.tables.providers.UserContract.UserEntry;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rashid Saleem on 25-Jan-18.
 */

public class User {
    private static final String LOG_TAG = User.class.getSimpleName() ;

    public static final String TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjE2YzUzYzQ0OTU0MWM1ZjczOTk3MTE1ODVhYTUzNzdjZjQ1ZWE2YzM2NGM0NWJlN2UzOGYyMGVjMzE4ZTY4MmI1MjcwNjRhOTQ5ODIxNWU2In0.eyJhdWQiOiIxIiwianRpIjoiMTZjNTNjNDQ5NTQxYzVmNzM5OTcxMTU4NWFhNTM3N2NmNDVlYTZjMzY0YzQ1YmU3ZTM4ZjIwZWMzMThlNjgyYjUyNzA2NGE5NDk4MjE1ZTYiLCJpYXQiOjE1MTY4NjIzOTMsIm5iZiI6MTUxNjg2MjM5MywiZXhwIjoxNTQ4Mzk4MzkzLCJzdWIiOiIxIiwic2NvcGVzIjpbIioiXX0.mJluATN1Z3kJKwvD6w6tvckylmQLm0UG7VTe2NIUtaMdSu6ff2hBo7m0EXw2fiFXqCP2k5dy3HuueLf3dYIzSv6KCR_--hZyLolo3G463bx_O63lQxtLZXSUxv2Y1abHKzm-DdRTEJo2WLhDo93viICwNAlGjawIkIdjvBD27nA38L08KKr2p_m8ddqvAnCrqNB_F9uEVrM3lD5FqYgpytymiE6jPXexDzJV4CsK5P6aq00pvIrA1-an6yxMGLiHJ8yO5NSNGjDUu_AC2IDladzPZBhbxhfXmFLmQvTTBTwxO7stQ7dxTT02NChSLXKWbC-X3JAnlMFQ0ZIKYh7xwbX5_ow3yUuj5vX7kHZARkThrbtEkfRl8GXhELbI6saN6G6PMzwSAjkCJBQI6TyVsL-sF_tgr4FOBH_RCed8T35iuJzwgCXgfUV254ZkKmeAqLiGyN0BcydEypdy1vfvLb5rPbSVcOr9ErrALxDBdkAI6l4sJiO2SZAJp_WuODtRsSob2VZSb9Ha6H9d2UvJcxpGBseFboCLweX329Aua3AtrqyOJmJ-dxIigvBxYv63vWrwOJ65nWTpNIjKv1jnCnup3gm7v3dXFE2jAq48gpin8VRztTYpBFKW323c8fsowsPm7uKx-dAKUTf_1RxXft0gfy71TswaxMVNROJwJHw";




    private int id;
    private String name;
    private String password;
    private int active;
    private String avatar;
    private int branchId;
    private int departmentId;
    private int companyId;
    private int deleteStatus;

    private Context mContext;
    private SharedPreferences mSharedPref;

    // Default Contructor
    public User(Context context) {
        mContext = context;
        mSharedPref = context.getSharedPreferences(UserEntry.TABLE_NAME, 0);


    }

    public User(int id, String name, String password, int active, String avatar, int branchId, int departmentId, int companyId, int deleteStatus) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.active = active;
        this.avatar = avatar;
        this.branchId = branchId;
        this.departmentId = departmentId;
        this.companyId = companyId;
        this.deleteStatus = deleteStatus;
    }

    // Setter Methods


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    // Getter Methods
    public int getId() {
        return mSharedPref.getInt(UserEntry._ID, 0);
    }

    public String getName() {
        return mSharedPref.getString(UserEntry.COLUMN_USER_NAME, "");
    }

    public String getPassword() {
        return password;
    }

    public int getActive() {
        return mSharedPref.getInt(UserEntry.COLUMN_USER_ACTIVE, 0);
    }

    public String getAvatar() {
        return avatar;
    }

    public int getBranchId() {
        return mSharedPref.getInt(UserEntry.COLUMN_USER_BRANCH_ID, 0);
    }

    public int getDepartmentId() {
        return mSharedPref.getInt(UserEntry.COLUMN_USER_DEPARTMENT_ID, 0);
    }

    public int getCompanyId() {
        return mSharedPref.getInt(UserEntry.COLUMN_USER_COMPANY_ID, 0);
    }

    public int getDeleteStatus() {
        return mSharedPref.getInt(UserEntry.COLUMN_USER_DELETE_STATUS, 0);
    }

    public String getToken() {
        String access_token = mSharedPref.getString(mContext.getString(R.string.pref_access_token_key), "");

        return  "Bearer " + access_token;
    }

    public static final String getToken(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(UserEntry.TABLE_NAME, 0);

        String access_token = sharedPref.getString(context.getString(R.string.pref_access_token_key), "");

        return  "Bearer " + access_token;
    }

    public static JSONObject readUserFromJson(String userInfoJSONString, Context context) throws JSONException {
        JSONObject parent = new JSONObject(userInfoJSONString.toString());
//        JSONObject userInfoJSON = parent;
        JSONObject userInfoJSON = parent.getJSONObject("user");

        // UserInfo Variables
//                        int id = userInfoJSON.getInt(UserEntry._ID);
        int id = userInfoJSON.getInt(UserContract.UserEntry._ID);
        String name = userInfoJSON.getString(UserContract.UserEntry.COLUMN_USER_NAME);
        String email = userInfoJSON.getString(UserContract.UserEntry.COLUMN_USER_EMAIL);
        int active  = userInfoJSON.getInt(UserContract.UserEntry.COLUMN_USER_ACTIVE);
        String avatar  = userInfoJSON.getString(UserContract.UserEntry.COLUMN_USER_AVATAR);
        int branchId = userInfoJSON.getInt(UserContract.UserEntry.COLUMN_USER_BRANCH_ID);
        int departmentId = userInfoJSON.getInt(UserContract.UserEntry.COLUMN_USER_DEPARTMENT_ID);
        int companyId = userInfoJSON.getInt(UserContract.UserEntry.COLUMN_USER_COMPANY_ID);
        int deleteStatus = userInfoJSON.getInt(UserContract.UserEntry.COLUMN_USER_DELETE_STATUS);
        String createdAt = userInfoJSON.getString(UserContract.UserEntry.COLUMN_USER_CREATED_AT);
        String updatedAt = userInfoJSON.getString(UserContract.UserEntry.COLUMN_USER_UPDATED_AT);

        SharedPreferences userInfoPref =  context.getSharedPreferences(UserContract.UserEntry.TABLE_NAME, 0);
        SharedPreferences.Editor prefEdit = userInfoPref.edit();
        prefEdit.putInt(UserContract.UserEntry._ID, id);
        prefEdit.putString(UserContract.UserEntry.COLUMN_USER_NAME, name);
        prefEdit.putString(UserContract.UserEntry.COLUMN_USER_EMAIL, email);
        prefEdit.putInt(UserContract.UserEntry.COLUMN_USER_ACTIVE, active);
        prefEdit.putString(UserContract.UserEntry.COLUMN_USER_AVATAR, avatar);
        prefEdit.putInt(UserContract.UserEntry.COLUMN_USER_BRANCH_ID, branchId);
        prefEdit.putInt(UserContract.UserEntry.COLUMN_USER_DEPARTMENT_ID, departmentId);
        prefEdit.putInt(UserContract.UserEntry.COLUMN_USER_COMPANY_ID, companyId);
        prefEdit.putInt(UserContract.UserEntry.COLUMN_USER_DEPARTMENT_ID, deleteStatus);
        prefEdit.putString(UserContract.UserEntry.COLUMN_USER_CREATED_AT, createdAt);
        prefEdit.putString(UserContract.UserEntry.COLUMN_USER_UPDATED_AT, updatedAt);
        prefEdit.commit();

        return userInfoJSON;
    }

}
