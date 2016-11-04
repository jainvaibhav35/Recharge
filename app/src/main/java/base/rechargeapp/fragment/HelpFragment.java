package base.rechargeapp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import base.rechargeapp.activity.MainActivity;
import base.rechargeapp.R;
import base.rechargeapp.utils.CustomTextView;

/**
 * Created by lin on 13/10/16.
 */

public class HelpFragment extends Fragment implements View.OnClickListener{

    private View mView ;
    private Activity mActivity ;
    private LinearLayout mCall , mEmail ;
    CustomTextView mCallIcon , mEmailIcon ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.help, null);
        mActivity = getActivity() ;

        initViews();
        setListner();

        setTypeface();

        return mView;
    }

    private void setListner() {
        mCall.setOnClickListener(this);
        mEmail.setOnClickListener(this);
    }

    private void initViews() {

        mCall = (LinearLayout) mView.findViewById(R.id.ll_call);
        mEmail = (LinearLayout) mView.findViewById(R.id.ll_email);
        mCallIcon = (CustomTextView) mView.findViewById(R.id.call_icon);
        mEmailIcon = (CustomTextView) mView.findViewById(R.id.email_icon);
    }

    private void setTypeface() {
        Typeface font = Typeface.createFromAsset(mActivity.getAssets(), "fonts/"+ mActivity.getString(R.string.icon_font));
        mCallIcon.setTypeface(font);
        mEmailIcon.setTypeface(font);
    }

    @Override
    public void onClick(View mView) {

        switch (mView.getId()){

            case R.id.ll_call:
                callUs();
                break;

            case R.id.ll_email:
                emailUs();
                break;
        }

    }


    private void emailUs(){

            String uriText = "mailto:xyz@abc.com"
                    + "?subject="
                    + Uri.encode("Undostres Android Feedback")
                    + "&body=" + Uri.encode("");

            Uri uri = Uri.parse(uriText);

            Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
            sendIntent.setData(uri);
            startActivity(Intent.createChooser(sendIntent, "Send email"));

        }

    private void callUs(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+mActivity.getString(R.string.contact_number)));
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        // For setting toolbar title
        ((MainActivity) mActivity).setTitle(mActivity.getString(R.string.help));
    }




}
