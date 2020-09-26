package com.sourav.rxnet.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sourav.rxnet.R;

import pl.droidsonroids.gif.GifImageView;

public class WebViewActivity extends AppCompatActivity {
    String url;
    ProgressBar progressBar;

    private WebView webView;

    ImageView backBtn;
    GifImageView gifLoder;
    TextView titleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        url = getIntent().getStringExtra("url");

        backBtn = findViewById(R.id.backBtn);
        progressBar = findViewById(R.id.progressBar);
        titleTv = findViewById(R.id.pageHeaderTitle);
        titleTv.setText("Loading please wait...");
        progressBar.setMax(100);


        gifLoder = findViewById(R.id.gifLoder);
        webView = findViewById(R.id.torent_webview);


        webView.setWebViewClient(new Browserr());
        webView.setWebChromeClient(new MyWebClient());
        webView.getSettings().setJavaScriptEnabled(true);

        //mWebChromeClient = new MyWebClient();
        //webView.setWebChromeClient(MyWebClient);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setSaveFormData(true);

        gifLoder.setVisibility(ProgressBar.VISIBLE);
        if (savedInstanceState == null){
            webView.post(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl(url);
                }
            });
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titleTv.getText().equals("Plex")){
                    finish();
                }else {
                    onBackPressed();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (titleTv.getText().equals("Plex")){
            finish();
        }else {
            if(webView.canGoBack()) {
                webView.goBack();
            }else {
                finish();
            }
        }

    }//End Back button press for exit...

    public class MyWebClient extends WebChromeClient {
        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setProgress(newProgress);
            if (newProgress <30){
                gifLoder.setVisibility(ProgressBar.VISIBLE);
            }
            if (newProgress == 30){
                gifLoder.setVisibility(ProgressBar.GONE);
            }
            if (newProgress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {
                progressBar.setVisibility(ProgressBar.VISIBLE);

            }
            if (newProgress == 100) {
                progressBar.setVisibility(ProgressBar.GONE);
                gifLoder.setVisibility(ProgressBar.GONE);

            }
        }


        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            titleTv.setText(title);
        }

        public MyWebClient() {}

        public Bitmap getDefaultVideoPoster()
        {
            if (WebViewActivity.this == null) {
                return null;
            }
            return BitmapFactory.decodeResource(WebViewActivity.this.getApplicationContext().getResources(), 2130837573);
        }

        @SuppressLint("SourceLockedOrientationActivity")
        public void onHideCustomView()
        {
            ((FrameLayout)WebViewActivity.this.getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            WebViewActivity.this.getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            WebViewActivity.this.setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }

        @SuppressLint("SourceLockedOrientationActivity")
        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = WebViewActivity.this.getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = WebViewActivity.this.getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)WebViewActivity.this.getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            WebViewActivity.this.getWindow().getDecorView().setSystemUiVisibility(3846);
        }
    }

    class Browserr extends WebViewClient {
        Browserr() {}

        public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
        {
            paramWebView.loadUrl(paramString);

            return true;
        }


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState )
    {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        webView.restoreState(savedInstanceState);
    }

}