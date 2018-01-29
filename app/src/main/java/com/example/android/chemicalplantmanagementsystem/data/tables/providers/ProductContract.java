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






        public final static String QTY = "qty";

    }

}
