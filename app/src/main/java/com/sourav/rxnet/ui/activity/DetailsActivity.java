package com.sourav.rxnet.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.sourav.rxnet.R;
import com.sourav.rxnet.webapi.ApiService;
import com.sourav.rxnet.webapi.RetrofitClient;
import com.sourav.rxnet.webapi.response.BillingInfo;
import com.sourav.rxnet.webapi.response.RouterInfo;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {
    ApiService apiService;
    String id, titlee, img;
    String data = "";
    ImageView headerImageView, backBtn, emptyIv;
    ImageView menuImg;
    PhotoView zoomImg;
    String onOpen;
    TextView contentTv, pageHeaderTitle;
    GifImageView progressBar;
    ConstraintLayout zoomLayout;
    LinearLayout constraintLayout;
    boolean zoomLayoutIsVisible = false;
    boolean isData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        apiService = RetrofitClient.getClient().create(ApiService.class);

        progressBar = findViewById(R.id.progressBar);
        menuImg = findViewById(R.id.menuImg);
        zoomImg = findViewById(R.id.zoomImg);
        emptyIv = findViewById(R.id.emptyIv);
        headerImageView = findViewById(R.id.headerImageView);
        backBtn = findViewById(R.id.backBtn);
        contentTv = findViewById(R.id.contentTv);
        pageHeaderTitle = findViewById(R.id.pageHeaderTitle);
        zoomLayout = findViewById(R.id.zoomLayout);
        constraintLayout = findViewById(R.id.constraintLayout);

        progressBar.setVisibility(View.VISIBLE);

        id = getIntent().getStringExtra("id");
        titlee = getIntent().getStringExtra("title");
        onOpen = getIntent().getStringExtra("onOpen");
        pageHeaderTitle.setText(titlee);

        if (onOpen != null && onOpen.equalsIgnoreCase("router")){
            getRouterInfo();
        }else {
            getBillingInfo();
        }



        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                constraintLayout.setVisibility(View.GONE);
                zoomLayout.setVisibility(View.VISIBLE);
                zoomLayoutIsVisible = true;
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getBillingInfo() {
        Call<ArrayList<BillingInfo>> billingInfoCall = apiService.getBillingInfo();
        billingInfoCall.enqueue(new Callback<ArrayList<BillingInfo>>() {
            @Override
            public void onResponse(Call<ArrayList<BillingInfo>> call, Response<ArrayList<BillingInfo>> response) {
                List<BillingInfo> billingInfos = response.body();
                if (response.isSuccessful()){
                    if (!id.isEmpty()){
                        for (int i=0; i<billingInfos.size(); i++){
                            BillingInfo billingInfo = billingInfos.get(i);
                            if (billingInfo.getId().equals(id)){
                                data = billingInfo.getContent();
                                img = billingInfo.getImage();

                                final String imgUrl = "https://rsnetbd.ispms.net/bill_image/"+img;
                                Glide.with(getApplication())
                                        .load(imgUrl)
                                        .into(menuImg);

                                Glide.with(getApplication())
                                        .load(imgUrl)
                                        .into(zoomImg);

                                if (!data.isEmpty()){
                                    setData(titlee, data);
                                    menuImg.setVisibility(View.VISIBLE);
                                    headerImageView.setVisibility(View.VISIBLE);
                                    isData = true;
                                }else {
                                    emptyIv.setVisibility(View.VISIBLE);
                                }
                            }else {

                                //pageHeaderTitle.setText("Data not found");
                            }

                        }

                    }
                }else {
                    Toast.makeText(DetailsActivity.this, "Try again later", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<BillingInfo>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                emptyIv.setVisibility(View.VISIBLE);
                Toast.makeText(DetailsActivity.this, "Try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getRouterInfo() {
        Call<ArrayList<RouterInfo>> routerInfoCall = apiService.getRouterInfo();
        routerInfoCall.enqueue(new Callback<ArrayList<RouterInfo>>() {
            @Override
            public void onResponse(Call<ArrayList<RouterInfo>> call, Response<ArrayList<RouterInfo>> response) {
                List<RouterInfo> routerInfos = response.body();
                if (response.isSuccessful()){
                    if (!id.isEmpty()){
                        for (int i=0; i<routerInfos.size(); i++){
                            RouterInfo routerInfo = routerInfos.get(i);
                            if (routerInfo.getId().equals(id)){
                                data = routerInfo.getContent();
                                img = routerInfo.getImage();
                                headerImageView.setVisibility(View.VISIBLE);
                                final String imgUrl = "https://dhakatech.ispms.net/router_image/"+img;
                                Glide.with(getApplication())
                                        .load(imgUrl)
                                        .into(menuImg);

                                Glide.with(getApplication())
                                        .load(imgUrl)
                                        .into(zoomImg);
                                menuImg.setVisibility(View.VISIBLE);

                                if (!data.isEmpty()){
                                    setData(titlee, data);
                                }
                            }else {
                                // pageHeaderTitle.setText("তথ্য খুজে পাওয়া যায়নি");
                            }

                        }

                    }
                }else {
                    emptyIv.setVisibility(View.VISIBLE);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<RouterInfo>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                emptyIv.setVisibility(View.VISIBLE);
                Toast.makeText(DetailsActivity.this, "Try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData(String titlee, String data) {
        pageHeaderTitle.setText(titlee);
        Spanned spanned = HtmlCompat.fromHtml(data, HtmlCompat.FROM_HTML_MODE_COMPACT);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            contentTv.setText(Html.fromHtml(String.valueOf(spanned),Html.FROM_HTML_MODE_COMPACT));
        } else {
            contentTv.setText(Html.fromHtml(String.valueOf(spanned)));
        }
    }

    @Override
    public void onBackPressed() {

        FragmentManager fm = getSupportFragmentManager();
        if (zoomLayoutIsVisible){
            constraintLayout.setVisibility(View.VISIBLE);
            zoomLayout.setVisibility(View.GONE);
            zoomLayoutIsVisible = false;
        }else {
            super.onBackPressed();
        }
    }
}