//package baseproject.rechargeapp.fragment;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.login.LoginManager;
//import com.facebook.login.LoginResult;
//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import java.util.Arrays;
//import baseproject.rechargeapp.activity.R;
//import baseproject.rechargeapp.socialMediaIntegration.GPlus_Login;
//import baseproject.rechargeapp.utils.AppConstant;
//
///**
// * This fragment is used to connect differnt types of social media with invovling their codes in this
// * if have only calling and callback codes of particular social media
// * <p>
// * <p>
// * Created by lin on 13/7/16.
// */
//
//public class SocialMediaFragment extends Fragment implements View.OnClickListener {
//
//    // Login Result call back of facebook
//
//    private View mView;
//    private Button gPlus, Facebook, Twitter, LinkedIn;
//    private TextView retrivedData;
//    FacebookCallback<LoginResult> facebookLoginResult = new FacebookCallback<LoginResult>() {
//        @Override
//        public void onSuccess(LoginResult loginResult) {
//            Log.e("success", "success " + loginResult.getAccessToken().toString());
//            retrivedData.setText("Facebook Login " + " \n " + "User ID" +
//                    " : " + loginResult.getAccessToken().getUserId().toString() + "\n" + "Token" +
//                    " : " + loginResult.getAccessToken().getToken().toString());
//        }
//
//        @Override
//        public void onCancel() {
//            Log.e("cancel", "cancel");
//        }
//
//        @Override
//        public void onError(FacebookException error) {
//            Log.e("failure", "failure");
//        }
//    };
//    // Used for GPLus Integration
//    private GoogleApiClient mGoogleApiClient;
//    private GPlus_Login gPlus_login;
//    private GoogleSignInAccount account;
//
//    // Used for fb Integration
//    private CallbackManager callbackmanager;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        mView = inflater.inflate(R.layout.socialfragment, container, false);
//
//        gPlus = (Button) mView.findViewById(R.id.gplus);
//        Facebook = (Button) mView.findViewById(R.id.facebook);
//        Twitter = (Button) mView.findViewById(R.id.twitter);
//        LinkedIn = (Button) mView.findViewById(R.id.linkedin);
//
//        retrivedData = (TextView) mView.findViewById(R.id.retrivedData);
//
//        setListners();
//        return mView;
//    }
//
//    // Method to set Listners
//    private void setListners() {
//
//        gPlus.setOnClickListener(this);
//        Facebook.setOnClickListener(this);
//        Twitter.setOnClickListener(this);
//        LinkedIn.setOnClickListener(this);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Social" +
//                "");
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId()) {
//
//            case R.id.gplus:
//                gPlus_login = new GPlus_Login();
//                mGoogleApiClient = gPlus_login.AuthenticateGplus(((AppCompatActivity) getActivity()));
//                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//                startActivityForResult(signInIntent, AppConstant.GPlusReqCode);
//                break;
//            case R.id.facebook:
//                callfbLogin();
//                break;
//            case R.id.twitter:
//                break;
//            case R.id.linkedin:
//                break;
//        }
//
//    }
//
//    /**
//     * Method to be called for login with facebook
//     */
//    private void callfbLogin() {
//        callbackmanager = CallbackManager.Factory.create();
//        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends"));
//        LoginManager.getInstance().registerCallback(callbackmanager, facebookLoginResult);
//    }
//
//    // Getting callback after interacting with social media
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == AppConstant.GPlusReqCode && resultCode == getActivity().RESULT_OK) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            account = gPlus_login.handleSignInResult(result);
//            new GPlus_Login.RetrieveTokenTask(getActivity(), SocialMediaFragment.this, result).execute();
//        } else {
//            // This will call onActivity Result of callbackmanager
//            callbackmanager.onActivityResult(requestCode, resultCode, data);
//        }
//
//    }
//
//    // Method to get gPLUS token
//    public void getGplusToken(String token, GoogleSignInResult result) {
//        retrivedData.setText("Social Login " + " \n " + "Token : " + token + "\n" + "Email : " + result.getSignInAccount().getEmail());
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//    }
//
//}
