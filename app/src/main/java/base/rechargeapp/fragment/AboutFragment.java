package base.rechargeapp.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import base.rechargeapp.activity.MainActivity;
import base.rechargeapp.R;
import base.rechargeapp.utils.ApplicationSnackbar;
import base.rechargeapp.utils.Utils;

/**
 * Created by lin on 13/10/16.
 */

public class AboutFragment extends Fragment {

    private View mView ;
    private Activity mActivity ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.about, null);
        mActivity = getActivity() ;

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // For setting toolbar title
        ((MainActivity) mActivity).setTitle(mActivity.getString(R.string.about));
    }

}
