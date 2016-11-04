package base.rechargeapp.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import base.rechargeapp.R;

/**
 * Created by Vaibhav Jain on 10/10/16.
 */

public class CustomTextView extends TextView {

    private Context mContext ;

    public CustomTextView(Context context) {
        super(context);
        mContext = context ;
        initializeFonts();
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context ;
        initializeFonts();
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context ;
        initializeFonts();
    }

    private void initializeFonts() {
        Typeface customTypeface = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/"+ mContext.getString(R.string.icon_font));
        setTypeface(customTypeface);
    }
}
