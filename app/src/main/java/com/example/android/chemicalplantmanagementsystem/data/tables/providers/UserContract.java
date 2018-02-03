package com.example.android.chemicalplantmanagementsystem.data.tables.providers;

import android.provider.BaseColumns;

/**
 * Created by Rashid Saleem on 25-Jan-18.
 */

public class UserContract {

    private UserContract() { }


    /**
     * Inner class that defines constant values for the User database table.
     * Each entry in the table represents a single User
     */
    public static final class UserEntry implements BaseColumns {

        public final static String TABLE_NAME = "users";

        /**
         * Unique ID number
         * Type: INTEGER
         */
        public final static String _ID = "id";

        /**
         * Name of the user
         * Type: TEXT
         */
        public final static String COLUMN_USER_NAME = "name";

        /**
         * Email of the user
         * Type: TEXT
         */
        public final static String COLUMN_USER_EMAIL = "email";

        /**
         * Password of the user
         * Type: TEXT
         */
        public final static String COLUMN_USER_PASSWORD = "password";

        /**
         * Active state of the user
         * Type: INTEGER
         */
        public final static String COLUMN_USER_ACTIVE = "active";

        /**
         * Type: TEXT
         */
        public final static String COLUMN_USER_AVATAR = "avatar";


        /**
         * Type: INTEGER
         */
        public final static String COLUMN_USER_BRANCH_ID = "branch_id";

        /**
         * Type: INTEGER
         */
        public final static String COLUMN_USER_DEPARTMENT_ID = "department_id";

        /**
         * Type: INTEGER
         */
        public final static String COLUMN_USER_COMPANY_ID = "company_id";

        /**
         * Delete_Status of user in case of user account is Deleted by Admin
         * Type: INTEGER
         */
        public final static String COLUMN_USER_DELETE_STATUS = "delete_status";

        /**
         * Type: timestamp
         */
        public final static String COLUMN_USER_CREATED_AT = "created_at";

        /**
         * Type: timestamp
         */
        public final static String COLUMN_USER_UPDATED_AT = "updated_at";


    }

    // Other Variables
    public final static String COLUMN_TOKEN = "token";






}












