package base.rechargeapp.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import base.rechargeapp.R;
import base.rechargeapp.database.sqllite.CRUD_OperationOfDB;
import base.rechargeapp.database.sqllite.ContractClass;

/**
 * This fragment is used to connect different types of social media with involving their codes in this
 * if have only calling and callback codes of particular social media
 * <p>
 * <p>
 * Created by lin on 13/7/16.
 */

public class DbOperatoinFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private Button insert, fetch, update, delete;
    private TextView retrivedData;
    private EditText insertValue;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.db_operation_fragment, container, false);

        insert = (Button) mView.findViewById(R.id.insert);
        fetch = (Button) mView.findViewById(R.id.fetch);
        retrivedData = (TextView) mView.findViewById(R.id.data);
        insertValue = (EditText) mView.findViewById(R.id.name);
        update = (Button) mView.findViewById(R.id.update);
        delete = (Button) mView.findViewById(R.id.delete);
        setListners();

        return mView;
    }

    private void setListners() {

        insert.setOnClickListener(this);
        fetch.setOnClickListener(this);
        update.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("DB Operation" +
                "");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.fetch:
                getDataFromDB();
                break;
            case R.id.insert:
                insertDatainTable();
                break;

            case R.id.update:
                updateDb();
                break;

            case R.id.delete:
                deleteRow();
                break;
        }

    }

    /**
     * Method to delete values from DB
     */
    private void deleteRow() {
        // Define 'where' part of query.
        String selection = ContractClass.FeedEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {"vv"};

        CRUD_OperationOfDB mDatabase = new CRUD_OperationOfDB(getActivity());
        Toast.makeText(getActivity(), mDatabase.deleteFromDB(ContractClass.FeedEntry.TABLE_NAME, selection, selectionArgs) + " Row deleted", Toast.LENGTH_SHORT).show();


    }

    /**
     * Method to update DB
     */
    private void updateDb() {

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(ContractClass.FeedEntry.COLUMN_NAME_ENTRY_ID, "vv" +
                "");

        // Which row to update, based on the ID
        String selection = ContractClass.FeedEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {"hi"};

        CRUD_OperationOfDB mDatabase = new CRUD_OperationOfDB(getActivity());
        Toast.makeText(getActivity(), mDatabase.UpdateDB(ContractClass.FeedEntry.TABLE_NAME, selection, selectionArgs, values) + " Row Updated", Toast.LENGTH_SHORT).show();
    }


    /**
     * Method to insert values in DB
     */
    private void insertDatainTable() {

        ContentValues values = new ContentValues();
        values.put(ContractClass.FeedEntry.COLUMN_NAME_ENTRY_ID, insertValue.getText().toString());

        CRUD_OperationOfDB mDatabase = new CRUD_OperationOfDB(getActivity());

        if (mDatabase.InsertIntoDB(ContractClass.FeedEntry.TABLE_NAME, values) >= 0) {
            Toast.makeText(getActivity(), "Successfully Saved", Toast.LENGTH_SHORT).show();
            insertValue.setText("");
        }
    }

    /**
     * Method to fetch data from the db
     */
    private void getDataFromDB() {
        try {
            String AllData = "";

            CRUD_OperationOfDB mDatabase = new CRUD_OperationOfDB(getActivity());
            Cursor cursor = mDatabase.fetchData(ContractClass.FeedEntry.TABLE_NAME, null, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();
                while (cursor.moveToNext()) {
                    AllData += cursor.getString(cursor.getColumnIndexOrThrow(ContractClass.FeedEntry.COLUMN_NAME_ENTRY_ID)) + " , ";
                }
            }

            retrivedData.setText(AllData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
