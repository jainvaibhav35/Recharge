//package base.rechargeapp.socialMediaIntegration;
//
///**
// * Created by lin on 12/7/16.
// */
//
//import android.content.Context;
//import android.os.AsyncTask;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//
//import com.google.android.gms.auth.GoogleAuthException;
//import com.google.android.gms.auth.GoogleAuthUtil;
//import com.google.android.gms.auth.UserRecoverableAuthException;
//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//
//import java.io.IOException;
//
//
//
///**
// * This class is used to get the login from
// * Google Plus .
// * <p>
// * Donot forget to include google-services.json file after
// * registering Project on Google Developer Console.
// */
//
//
//public class GPlus_Login {
//
//    private static String TAG = GPlus_Login.class.getSimpleName();
//    GoogleApiClient.OnConnectionFailedListener cfl = new GoogleApiClient.OnConnectionFailedListener() {
//        @Override
//        public void onConnectionFailed(ConnectionResult connectionResult) {
//
//        }
//    };
//    private GoogleApiClient mGoogleApiClient;
//    private Context mContext;
//
//    public GoogleApiClient AuthenticateGplus(AppCompatActivity mActivity) {
//
//        // Configure sign-in to request the user's ID, email address, and basic
//        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
//
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//
//
//        // Build a GoogleApiClient with access to the Google Sign-In API and the
//        // options specified by gso.
//
//        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
//                .enableAutoManage((mActivity)
//                        , cfl)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
//
//
//        return mGoogleApiClient;
//
//    }
//
//    public GoogleSignInAccount handleSignInResult(GoogleSignInResult result) {
//        GoogleSignInAccount acct = null;
//        Log.d("handleSignInResult", "handleSignInResult:" + result.isSuccess());
//        if (result.isSuccess()) {
//            // Signed in successfully, show authenticated UI.
//            acct = result.getSignInAccount();
//        } else {
//        }
//        return acct;
//    }
//
//    /**
//     * This is used to get access token from
//     * the account.
//     */
//
//    public static class RetrieveTokenTask extends AsyncTask<String, String, String> {
//
//        private Context mContext;
//        private Fragment mFragment;
//
//        private GoogleSignInResult googleSignInResult;
//
//        public RetrieveTokenTask(Context mContext, Fragment mFragment, GoogleSignInResult result) {
//            this.mContext = mContext;
//            this.mFragment = mFragment;
//            googleSignInResult = result;
//        }
//
//
//        @Override
//
//        protected String doInBackground(String... params) {
//            String accountName = googleSignInResult.getSignInAccount().getEmail();
//            String scopes = "oauth2:profile email";
//            String token = null;
//            try {
//                token = GoogleAuthUtil.getToken(mContext, accountName, scopes);
//            } catch (IOException e) {
//                Log.e(TAG, e.getMessage());
//            } catch (UserRecoverableAuthException e) {
////                startActivityForResult(e.getIntent(), 100);
//            } catch (GoogleAuthException e) {
//                Log.e(TAG, e.getMessage());
//            }
//            return token;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
////            ((SocialMediaFragment) mFragment).getGplusToken(s, googleSignInResult);
//        }
//    }
//
//}