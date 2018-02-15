package com.example.android.chemicalplantmanagementsystem.data.tables.providers;

import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by Rashid Saleem on 25-Jan-18.
 */

public class ProductionContract {


    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private ProductionContract() { }

    public static final class ProductionEntry implements BaseColumns {

        // Table Name
        public final static String TABLE_NAME = "productions";

        // Columns Names

        /**
         * Type: INTEGER
         */
        public final static String _ID = "id";

        /**
         * Type: TEXT
         */
        public final static String COLUMN_NAME = "name";

        /**
         * Type: TEXT
         */
        public final static String COLUMN_PRODUCTION_CODE = "production_code";


        /**
         * Type: INTEGER
         */
        public final static String COLUMN_STATUS = "status";

        /**
         * Type: INTEGER
         */
        public final static String COLUMN_DELETE_STATUS = "delete_status";

        /**
         * Type: TEXT
         */
        public final static String COLUMN_DESCRIPTION = "description";

        /**
         * Type: INTEGER
         */
        public final static String COLUMN_BRANCH_ID = "branch_id";

        /**
         * Type: INTEGER
         */
        public final static String COLUMN_DEPARTMENT_ID = "department_id";

        /**
         * Type: INTEGER
         */
        public final static String COLUMN_COMPANY_ID = "company_id";

        /**
         * Type: INTEGER
         */
        public final static String COLUMN_USER_ID = "user_id";

        /**
         * Type: TIMESTAMP
         */
        public final static String COLUMN_CREATE_AT = "create_at";

        /**
         * Type: TIMESTAMP
         */
        public final static String COLUMN_UPDATED_AT = "updated_at";

        /**
         * Type: INTEGER
         */
        public final static String PIVOT_PRODUCTION_ID = "production_id";

        /**
         * Type: INTEGER
         */
        public final static String PIVOT_PRODUCT_ID = "product_id";

        /**
         * Type: INTEGER
         */
        public final static String PIVOT_QUANTITY = "quantity";


        public final static int PENDING_STATUS = 1;
        public final static int APPROVED_STATUS = 3;
        public final static int COMPLETED_STATUS = 4;





        public final static String generateProductionCode(){
            String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            String productionCode = "";

            for (int i = 0; i < 11; i++) {
                productionCode += possible.charAt((int) Math.floor(Math.random() * possible.length()));
            }
//            Log.v(LOG_TAG, "ProductionCode = " + productionCode);

            return productionCode;
        }


    } // end ProductionEntry

    public final static String PENDING_PRODUCTION = "pending";
    public final static String APPRVOED_PRODUCTION = "approved";
    public final static String COMPLETED_PRODUCTION = "completed";



}
