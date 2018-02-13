package com.example.android.chemicalplantmanagementsystem.data.tables.providers;

import android.provider.BaseColumns;

import com.example.android.chemicalplantmanagementsystem.data.tables.DailyProduction;

/**
 * Created by Rashid Saleem on 13-Feb-18.
 */

public class DailyProductionContract {

    private DailyProductionContract() {

    }


    public static final class DailyProductionEntry implements BaseColumns {

        public static final String TABLE_NAME = "daily_production";

        /**
         * Type: INTEGER
         */
        public static final String _ID = "id";

        /**
         * Type: INTEGER
         */
        public static final String COLUMN_PRODUCT_ID = "product_id";

        /**
         * Type: INTEGER
         */
        public static final String COLUMN_PRODUCED = "produced";

        /**
         * Type: INTEGER
         */
        public static final String COLUMN_DISPATCHES = "dispatches";

        /**
         * Type: INTEGER
         */
        public static final String COLUMN_SALE_RETURN = "sale_return";

        /**
         * Type: INTEGER
         */
        public static final String COLUMN_RECEIVED = "received";





    }

}
