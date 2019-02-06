package com.example.rdsaleh.adpl_rs.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.rdsaleh.adpl_rs.R;
import com.example.rdsaleh.adpl_rs.api.ListSpesialis;
import com.example.rdsaleh.adpl_rs.javaClass.Spesialis;

import java.util.ArrayList;
import java.util.List;

public class AdapterSpesialis extends RecyclerView.Adapter<AdapterSpesialis.MyViewHolder> {

    private List<ListSpesialis> spesialisList;
    private Context mContext;

    public AdapterSpesialis(List<ListSpesialis> spesialisList, Context mContext) {
        this.spesialisList = spesialisList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public AdapterSpesialis.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_spesialis, viewGroup , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSpesialis.MyViewHolder myViewHolder, final int i) {
        myViewHolder.id_spesialis.setText(spesialisList.get(i).getId_spesialis());
        myViewHolder.nama_spesialis.setText(spesialisList.get(i).getNama_spesialis());
        myViewHolder.biaya.setText(String.valueOf(spesialisList.get(i).getBiaya()));
        myViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spesialis sp = new Spesialis();
                sp.deleteSpesialis(mContext, spesialisList.get(i).getId_spesialis());
            }
        });
    }

    @Override
    public int getItemCount() {
        return spesialisList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id_spesialis, nama_spesialis, biaya;
        Button btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id_spesialis   = itemView.findViewById(R.id.tv_idSpesialis);
            nama_spesialis = itemView.findViewById(R.id.tv_namaSpesialis);
            biaya          = itemView.findViewById(R.id.tv_biaya);
            btnDelete      = itemView.findViewById(R.id.btnDelete);

        }
    }

}
