//package base.rechargeapp.database;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//
//import base.rechargeapp.appInterface.InterfaceClass;
//import base.rechargeapp.beans.UserBean;
//import base.rechargeapp.utils.AppDialog;
//
///**
// * Created by lin on 7/7/16.
// */
//
///**
// * Please Navigate to below Url
// * <p>
// * https://console.firebase.google.com/project/baseproject-c21e1/database/data/
// * <p>
// * to see Db online for this project
// */
//
//
//public class firebaseDB extends InterfaceClass implements ValueEventListener {
//
//    InterfaceClass objInterfaceClass;
//    private FirebaseDatabase firebaseDB;
//    private DatabaseReference firebaseREF;
//    private UserBean userBean;
//
//    /**
//     * Method to create a instance of firebase Database and firebase References
//     */
//    private void initDBAndGetReference() {
//        firebaseDB = FirebaseDatabase.getInstance();
//        firebaseREF = firebaseDB.getReference("user" +
//                "");
//
//    }
//
//    /**
//     * Method to insert data in DB
//     */
//    public void insertDataInDB() {
//
//        initDBAndGetReference();
//
//        UserBean bean = new UserBean(15, "Vaibhav", "Jain");
//
//        // Inserting the Values
//        firebaseREF.child("User").setValue(bean);
//    }
//
//    /**
//     * Method to read data from Existing DB
//     *
//     * @return
//     */
//
//    public UserBean readData() {
//
//        initDBAndGetReference();
//        readFromDB();
//        if (userBean != null)
//            return userBean;
//        else
//            return null;
//    }
//
//    private void readFromDB() {
//
//        firebaseREF.addValueEventListener(this);
//    }
//
//
//    @Override
//    public void onDataChange(DataSnapshot dataSnapshot) {
//        try {
//
//            // Way to parse a arraylist of Objects
//            if (dataSnapshot.child("User").getChildrenCount() > 1) {
//
//                Iterator it = dataSnapshot.child("User").getChildren().iterator();
//                while (it.hasNext()) {
//                    DataSnapshot userBean = (DataSnapshot) it.next();
//                    UserBean ub = (UserBean) userBean.getValue(UserBean.class);
//                    ub.getId();
//
//                }
//
//            } else {
//                // Way to get a child data
//                userBean = dataSnapshot.child("User").getValue(UserBean.class);
//            }
//
////            String Name = (String) dataSnapshot.getChildren().iterator().next().child("fname").getValue();
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onCancelled(DatabaseError databaseError) {
//
//    }
//
//    /**
//     * Add a arraylist
//     *
//     * @param arrData - Arraylist of Object Type to be inserted
//     */
//    public void insertArraylistDataInDB(ArrayList<UserBean> arrData) {
//
//        initDBAndGetReference();
//
//
//        // Inserting the Values
//        firebaseREF.child("User").setValue(arrData);
//
//    }
//
//    public void updateDataInDB() {
//
//        objInterfaceClass = new InterfaceClass();
//        AppDialog.showDialog(100, "Title", "Description" +
//                "", "ok", "No", false, false, objInterfaceClass);
//
//    }
//}
