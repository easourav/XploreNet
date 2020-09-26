package com.sourav.rxnet.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sourav.rxnet.R;
import com.sourav.rxnet.adapter.HelpLineAdapter;
import com.sourav.rxnet.webapi.ApiService;
import com.sourav.rxnet.webapi.RetrofitClient;
import com.sourav.rxnet.webapi.response.HelpLine;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelpLineActivity extends AppCompatActivity {
    ImageView backBtn;
    TextView pageHeaderTitle, emptyText;
    ApiService apiService;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_line);

        apiService = RetrofitClient.getClient().create(ApiService.class);

        backBtn = findViewById(R.id.backBtn);
        pageHeaderTitle = findViewById(R.id.pageHeaderTitle);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerHotLine);
        emptyText = findViewById(R.id.errorMessage);
        recyclerView.setLayoutManager(new LinearLayoutManager(HelpLineActivity.this));

        pageHeaderTitle.setText("Hot Line");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getHelpLine();
    }

    private void getHelpLine() {
        Call<ArrayList<HelpLine>> helpLineCall = apiService.getHelpLine();
        helpLineCall.enqueue(new Callback<ArrayList<HelpLine>>() {
            @Override
            public void onResponse(Call<ArrayList<HelpLine>> call, Response<ArrayList<HelpLine>> response) {
                if (response.isSuccessful()){
                    ArrayList<HelpLine> helpLineArrayList = response.body();
                    HelpLineAdapter helpLineAdapter = new HelpLineAdapter(helpLineArrayList, HelpLineActivity.this);
                    recyclerView.setAdapter(helpLineAdapter);
                }else {
                    emptyText.setVisibility(View.VISIBLE);
                    emptyText.setText("Try again later");
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<HelpLine>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                emptyText.setVisibility(View.VISIBLE);
                emptyText.setText("Try again later");
            }
        });
    }
}