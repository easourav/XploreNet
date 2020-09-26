package com.sourav.rxnet.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sourav.rxnet.R;
import com.sourav.rxnet.ui.activity.HelpLineActivity;
import com.sourav.rxnet.webapi.response.HelpLine;

import java.util.ArrayList;

public class HelpLineAdapter extends RecyclerView.Adapter<HelpLineAdapter.ViewHolder> {
    ArrayList<HelpLine> helpLineArrayList;
    Context mContext;
    int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    public HelpLineAdapter(ArrayList<HelpLine> helpLineArrayList) {
        this.helpLineArrayList = helpLineArrayList;
    }

    public HelpLineAdapter(ArrayList<HelpLine> helpLineArrayList, HelpLineActivity helpLineActivity) {
        this.helpLineArrayList = helpLineArrayList;
        this.mContext = helpLineActivity;
    }

    @NonNull
    @Override
    public HelpLineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_line, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpLineAdapter.ViewHolder holder, int position) {
        final HelpLine helpLine = helpLineArrayList.get(position);
        holder.numberTv.setText(helpLine.getNumber());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);

                }else{
                    //String num = "09611722833";
                    String num = helpLine.getNumber().trim();
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + num));
                    mContext.startActivity(callIntent);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return helpLineArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView numberTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            numberTv = itemView.findViewById(R.id.hot_lineTv);
        }
    }
}
