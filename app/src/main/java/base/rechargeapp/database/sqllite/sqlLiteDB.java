package base.rechargeapp.database.sqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Vaibhav Jain on 27/7/16.
 * <p>
 * This singleton class create table in Sqlite DB
 */


public class sqlLiteDB extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =

            "CREATE TABLE " + ContractClass.FeedEntry.TABLE_NAME + " (" +
                    ContractClass.FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ContractClass.FeedEntry.VENDER_ID + TEXT_TYPE + COMMA_SEP +
                    ContractClass.FeedEntry.TXN_ID + TEXT_TYPE + COMMA_SEP +
                    ContractClass.FeedEntry.MOBILE_NUMBER + TEXT_TYPE + COMMA_SEP +
                    ContractClass.FeedEntry.OPERATOR + TEXT_TYPE + COMMA_SEP +
                    ContractClass.FeedEntry.AMOUNT + TEXT_TYPE + COMMA_SEP +
                    ContractClass.FeedEntry.DATE + TEXT_TYPE + COMMA_SEP +
                    ContractClass.FeedEntry.TIME + TEXT_TYPE + COMMA_SEP +
                    ContractClass.FeedEntry.STATUS + TEXT_TYPE + COMMA_SEP +
                    ContractClass.FeedEntry.TIME_IN_MILLI + TEXT_TYPE + COMMA_SEP +
                    ContractClass.FeedEntry.IMAGE_URL + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContractClass.FeedEntry.TABLE_NAME;

    private static sqlLiteDB objSqlLiteDB;


    private sqlLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Through this we will create only pne instance of sqllite
    public static sqlLiteDB getInstance(Context mContext) {
        if (objSqlLiteDB == null)
            objSqlLiteDB = new sqlLiteDB(mContext);

        return objSqlLiteDB;
    }


    // This is Overrided method called once , it checks whther table exist in DB if not then create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    /**
     * This is Overrided method called when any version of DB is changed
     *
     * @param db         -- db
     * @param oldVersion -- Old version of the DB
     * @param newVersion -- New Version of the DB
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
        db.execSQL(SQL_DELETE_ENTRIES);
    }


}
