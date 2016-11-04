//package base.rechargeapp.fragment;
//
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.AppCompatActivity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.RelativeLayout;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//
//import java.util.ArrayList;
//
//import base.rechargeapp.activity.R;
//import base.rechargeapp.appInterface.DialogInterface;
//import base.rechargeapp.beans.UserBean;
//import base.rechargeapp.database.firebaseDB;
//import base.rechargeapp.utils.AppDialog;
//import base.rechargeapp.utils.ApplicationSnackbar;
//
///**
// * Created by lin on 7/7/16.
// */
//
//public class FireBaseFragment extends Fragment implements FirebaseAuth.AuthStateListener, View.OnClickListener {
//
//    private View mView;
//    private Button buttonUpdate;
//    private FirebaseAuth firebaseAuth;
//    private RelativeLayout main_layout;
//    private firebaseDB firebaseDB;
//    private int DialogId = 200;
//
//    private DialogInterface dialogInterface = new DialogInterface() {
//        @Override
//        public void clickResponse(int Id, String response) {
//            callUpdate();
//        }
//    };
//
//    private void callUpdate() {
//        firebaseDB.updateDataInDB();
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        mView = inflater.inflate(R.layout.firebase, container, false);
//        main_layout = (RelativeLayout) mView.findViewById(R.id.ll_main);
//        buttonUpdate = (Button) mView.findViewById(R.id.btn_update);
//
//        buttonUpdate.setOnClickListener(this);
//
//        // Getting Firebase Auth
//        firebaseAuth = FirebaseAuth.getInstance();
//
//
//        firebaseDB = new firebaseDB();
//
//        return mView;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//
//        // For setting toolbar title
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("FireBase");
//
//        /**
//         *  Used to signIn as I have a created a useer with same credentials as passed
//         */
//
//        firebaseAuth.signInWithEmailAndPassword("jainvaibhav35@gmail.com", "vaibhav@123" +
//                "").addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task != null && !task.isSuccessful() && getActivity() != null) {
//
//                }
//
////                    ApplicationSnackbar.showSnackBar(main_layout, "Failed");
////                Toast.makeText(getActivity(), getActivity().getString(R.string.failed), Toast.LENGTH_SHORT);
//            }
//        });
//
//        /**
//         * Used to signIn as I have a created a useer with same credentials as passed
//         */
//
//        /*
//        firebaseAuth.createUserWithEmailAndPassword("jainvaibhav35@gmail.com", "vaibhav@123").addOnCompleteListener(new OnCompleteListener() {
//            @Override
//            public void onComplete(@NonNull Task task) {
//                if (task.isSuccessful())
//                    ApplicationSnackbar.showSnackBar(main_layout, getString(R.string.success_created));
//                else
//                    ApplicationSnackbar.showSnackBar(main_layout, getString(R.string.failed));
//
//            }
//    });
//
//    */
//    }
//
//    /**
//     * When FireBaseAuthentication takes place then this method will be called
//     * to get the current User
//     *
//     * @param firebaseAuth
//     */
//
//    @Override
//    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//
//        FirebaseUser user = firebaseAuth.getCurrentUser();
//        // get User Email
//        if (user != null && main_layout != null
//                ) {
//            user.getEmail();
//            // Show Email in Welcome
//            if (main_layout != null)
//                ApplicationSnackbar.showSnackBar(main_layout, "Welcome " + user.getEmail() + " ID " + user.getUid());
//
//
//            if (false)
//                firebaseDB.insertDataInDB();
//            else {
//                ArrayList<UserBean> arrData = new ArrayList<>();
//                arrData.add(new UserBean(12, "Vaibhav", "Jain" +
//                        ""));
//                arrData.add(new UserBean(15, "Gurwinder", "Singh" +
//                        "")
//                );
//                firebaseDB.insertArraylistDataInDB(arrData);
//            }
//
//            UserBean userBean = firebaseDB.readData();
//
//        }
//    }
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Add authListner
//        firebaseAuth.addAuthStateListener(this);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        // Remove Auth Listner
//        firebaseAuth.removeAuthStateListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId()) {
//            case R.id.btn_update:
//                showDialog();
//                break;
//
//        }
//
//    }
//
//    private void showDialog() {
//
//        /**
//         * This is used as proof that when we create new Interface(){ }
//         * then it willl create a class and implements that interface in that class
//         */
//
//        AppDialog.showDialog(getActivity(), DialogId, getString(R.string.want_to_update), getString(R.string.want_to_update), "Yes", "No", true, false, dialogInterface);
//    }
//}
