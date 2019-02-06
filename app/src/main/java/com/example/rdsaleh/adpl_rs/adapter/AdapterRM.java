package com.example.rdsaleh.adpl_rs.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rdsaleh.adpl_rs.R;
import com.example.rdsaleh.adpl_rs.api.ListRM;

import java.util.List;

public class AdapterRM extends RecyclerView.Adapter<AdapterRM.MyViewHolder> {

    private List<ListRM> rmList;
    private Context mContext;

    public AdapterRM(List<ListRM> rmList, Context mContext) {
        this.rmList = rmList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_rm, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.id_rm.setText(rmList.get(i).getId_rm());
        myViewHolder.id_pasien.setText(rmList.get(i).getId_pasien());
    }

    @Override
    public int getItemCount() {
        return rmList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id_rm, id_pasien;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id_rm     = itemView.findViewById(R.id.tv_idRm);
            id_pasien = itemView.findViewById(R.id.tv_idPasien);

        }
    }
}
