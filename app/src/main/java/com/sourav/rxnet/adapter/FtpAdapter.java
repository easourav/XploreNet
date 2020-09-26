package com.sourav.rxnet.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sourav.rxnet.R;
import com.sourav.rxnet.ui.activity.WebViewActivity;
import com.sourav.rxnet.webapi.response.FTP;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FtpAdapter extends RecyclerView.Adapter<FtpAdapter.ViewHolder> {
    ArrayList<FTP> ftpArrayList;
    Context mContext;

    public FtpAdapter(@NotNull ArrayList<FTP> ftpList, FragmentActivity activity) {
        this.ftpArrayList = ftpList;
        this.mContext = activity;
    }

    @NonNull
    @Override
    public FtpAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ftp, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FtpAdapter.ViewHolder holder, int position) {
        final FTP ftp = ftpArrayList.get(position);
        holder.textTv.setText(ftp.getTitle());
        final String url = ftp.getContent();
        String imgUrl = "https://rsnetbd.ispms.net/bannerImage/"+ftp.getBannerImage();
        Glide.with(mContext)
                .load(imgUrl)
                .into(holder.icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("url", url);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ftpArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTv;
        ImageView icon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTv = itemView.findViewById(R.id.item_text);
            icon = itemView.findViewById(R.id.item_image);
        }
    }
}
