package base.rechargeapp.database.sqllite;

import android.provider.BaseColumns;

/**
 * Created by lin on 27/7/16.
 */

public class ContractClass {

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "transaction_history";
        public static final String VENDER_ID = "vender_id";
        public static final String TXN_ID = "txn_id";
        public static final String MOBILE_NUMBER = "mobile_number";
        public static final String OPERATOR = "operator";
        public static final String AMOUNT = "amount";
        public static final String DATE = "date";
        public static final String TIME = "time";
        public static final String STATUS = "status";
        public static final String COLUMN_NAME_ENTRY_ID = "name";
        public static final String TIME_IN_MILLI = "time_in_milis";
        public static final String IMAGE_URL = "image_url";
    }
}
