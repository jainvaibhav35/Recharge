package base.rechargeapp.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import base.rechargeapp.R;

/**
 * Created by lin on 27/10/16.
 */

public class TermsAndConditionActivity extends AppCompatActivity{

    private TextView titleText ;
    private WebView termsWebView ;
    private ProgressBar mProgressBar ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_condition);

        initView();
        setUpData();
    }

    private void initView() {
        titleText = (TextView) findViewById(R.id.toolbar_title);
        termsWebView = (WebView) findViewById(R.id.terms_and_condition);
        mProgressBar  = (ProgressBar) findViewById(R.id.progress_bar);
        termsWebView.setWebViewClient(new MyWebViewClient());
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void setUpData() {

        titleText.setText(getString(R.string.terms_condition));
        //intentUrl = "http://id.bookmyshow.com/terms-and-conditions";
        termsWebView.loadUrl("https://www.google.co.in/intl/en/policies/terms/regional.html");
        WebSettings webSettings = termsWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }


    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mProgressBar.setVisibility(View.GONE);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
