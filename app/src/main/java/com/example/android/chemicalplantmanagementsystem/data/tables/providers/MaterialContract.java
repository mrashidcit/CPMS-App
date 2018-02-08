package com.example.android.chemicalplantmanagementsystem.data.tables.providers;

import android.provider.BaseColumns;

/**
 * Created by Rashid Saleem on 31-Jan-18.
 */

public class MaterialContract {

    private MaterialContract() {
    }

    public static final class MaterialEntry implements BaseColumns {


        public static final String TABLE_NAME = "materials";

        /**
         * Type: INTEGER
         */
        public static final String _ID = "id";

        /**
         * Type: TEXT
         */
        public static final String COLUMN_MATERIAL_CODE = "material_code";

        /**
         * Type: TEXT
         */
        public static final String COLUMN_NAME = "name";

        /**
         * Type: Boolean
         */
        public static final String COLUMN_DELETE_STATUS = "delete_status";

        /**
         * Type: TEXT
         */
        public static final String COLUMN_DESCRIPTION = "description";

        /**
         * Type: INTEGER
         */
        public static final String COLUMN_USER_ID = "user_id";


        // Pivot Table Columns
        /**
         * Type: INTEGER
         */
        public static final String COLUMN_PIVOT_GATE_PASS_ID = "gate_id";

        /**
         * Type: INTEGER
         */
        public static final String COLUMN_PIVOT_MATERIAL_ID = "material_id";

        /**
         * Type: INTEGER
         */
        public static final String COLUMN_PIVOT_QUANTITY = "quantity";










    }
}
