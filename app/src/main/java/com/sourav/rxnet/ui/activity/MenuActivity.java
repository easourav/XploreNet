package com.sourav.rxnet.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sourav.rxnet.R;
import com.sourav.rxnet.adapter.RouterTitleAdapter;
import com.sourav.rxnet.utils.Common;
import com.sourav.rxnet.webapi.ApiService;
import com.sourav.rxnet.webapi.RetrofitClient;
import com.sourav.rxnet.webapi.response.RouterTitle;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity {
    ApiService apiService;
    ProgressBar loderGV;
    RecyclerView recyclerView;
    String onOpen, id, title;
    LinearLayout configureLayout;
    TextView pageHeaderTitle;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        loderGV = findViewById(R.id.loderGif);
        recyclerView = findViewById(R.id.rvAllMainMenu);
        configureLayout = findViewById(R.id.configureLayout);
        pageHeaderTitle = findViewById(R.id.pageHeaderTitle);
        backBtn = findViewById(R.id.backBtn);

        apiService = RetrofitClient.getClient().create(ApiService.class);
        onOpen = getIntent().getStringExtra("onOpen");
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        getRouterTitle();
        pageHeaderTitle.setText("Router Setup");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getRouterTitle() {
        Call<ArrayList<RouterTitle>> routerTitleCall = apiService.getRouterTitle();
        routerTitleCall.enqueue(new Callback<ArrayList<RouterTitle>>() {
            @Override
            public void onResponse(Call<ArrayList<RouterTitle>> call, Response<ArrayList<RouterTitle>> response) {
                if (response.isSuccessful()){
                    configureLayout.setVisibility(View.VISIBLE);
                    final ArrayList<RouterTitle> routerTitles = response.body();
                    recyclerView.setLayoutManager(new LinearLayoutManager(MenuActivity.this));
                    RouterTitleAdapter routerTitleAdapter = new RouterTitleAdapter(routerTitles);
                    recyclerView.setAdapter(routerTitleAdapter);
                }else {
                    Toast.makeText(MenuActivity.this, "error: "+response.code(), Toast.LENGTH_SHORT).show();
                }loderGV.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<RouterTitle>> call, Throwable t) {
                Common.onSNACK(configureLayout, "Something went wrong, try again !!");
                loderGV.setVisibility(View.GONE);
            }
        });
    }

    public void setConfigureVedio(View view) {
        Intent intent = new Intent(MenuActivity.this, WebViewActivity.class);
        intent.putExtra("url", "https://www.youtube.com/watch?v=pgFC-stUrzk");
        startActivity(intent);
    }
}