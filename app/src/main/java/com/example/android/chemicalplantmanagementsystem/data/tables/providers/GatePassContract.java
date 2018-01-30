package com.example.android.chemicalplantmanagementsystem.data.tables.providers;

import android.provider.BaseColumns;

/**
 * Created by Rashid Saleem on 30-Jan-18.
 */

public class GatePassContract {

    public GatePassContract() {

    }

    public static final class GatePassEntry implements BaseColumns {

        // Table Name
        public static final String TABLE_NAME = "gate_passes";

        /**
         * Type: INTEGER
         */
        public static final String _ID = "id";

        /**
         * Type: TEXT
         */
        public static final String COLUMN_PERSON_NAME = "person_name";


        /**
         * Type: TEXT
         */
        public static final String COLUMN_CONTACT_PHONE = "contact_phone";


        /**
         * Type: TEXT
         */
        public static final String COLUMN_DESTINATION = "destination";


        /**
         * Type: TEXT
         */
        public static final String COLUMN_REMARKS = "remarks";








    }
}
