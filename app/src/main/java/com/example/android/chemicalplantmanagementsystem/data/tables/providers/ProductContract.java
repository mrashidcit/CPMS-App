package com.example.android.chemicalplantmanagementsystem.data.tables.providers;

import android.provider.BaseColumns;

/**
 * Created by Rashid Saleem on 29-Jan-18.
 */

public class ProductContract {

    private ProductContract() { }

    public static final class ProductEntry implements BaseColumns {

        // Table Name
        public final static String TABLE_NAME  = "products";

        // Columns Names

        /**
         * Type: INT
         */
        public final static String _ID  = "id";

        /**
         * Type: TEXT UNIQUE
         */
        public final static String COLUMN_PRODUCT_CODE  = "product_code";

        /**
         * Type: TEXT
         */
        public final static String COLUMN_NAME  = "name";

        /**
         * Type: INTEGER
         */
        public final static String COLUMN_DELETE_STATUS  = "delete_status";

        /**
         * Type: TEXT
         */
        public final static String COLUMN_DESCRIPTION  = "description";

        /**
         * Type: INTEGER
         */
        public final static String COLUMN_USER_ID  = "user_id";





        // Pivot Table Columns

        /**
         * Type: INTEGER
         */
        public final static String COLUMN_PIVOT_GATE_PASS_ID  = "gate_id";

        /**
         * Type: INTEGER
         */
        public final static String COLUMN_PIVOT_PRODUCT_ID  = "product_id";

        /**
         * Type: INTEGER
         */
        public final static String COLUMN_PIVOT_QTY  = "quantity";













        public final static String QTY = "qty";

    }

}
