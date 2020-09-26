package com.sourav.rxnet.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sourav.rxnet.R;
import com.sourav.rxnet.ui.activity.DetailsActivity;
import com.sourav.rxnet.webapi.response.RouterTitle;

import java.util.ArrayList;


public class RouterTitleAdapter extends RecyclerView.Adapter<RouterTitleAdapter.ViewHolder> {
    ArrayList<RouterTitle> routerTitleArrayList;

    public RouterTitleAdapter(ArrayList<RouterTitle> routerTitleArrayList) {
        this.routerTitleArrayList = routerTitleArrayList;
    }

    @NonNull
    @Override
    public RouterTitleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_menu_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RouterTitleAdapter.ViewHolder holder, int position) {
        final RouterTitle routerTitle = routerTitleArrayList.get(position);
        holder.mainMenuTv.setText(routerTitle.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailsActivity.class);
                intent.putExtra("id", routerTitle.getId());
                intent.putExtra("title", routerTitle.getTitle());
                intent.putExtra("onOpen", "router");
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return routerTitleArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mainMenuTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainMenuTv = itemView.findViewById(R.id.tvMainMenu);
        }
    }
}
