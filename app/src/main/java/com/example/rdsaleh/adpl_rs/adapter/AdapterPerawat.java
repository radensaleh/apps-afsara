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
import com.example.rdsaleh.adpl_rs.api.ListPerawat;
import com.example.rdsaleh.adpl_rs.javaClass.Perawat;

import java.util.List;

public class AdapterPerawat extends RecyclerView.Adapter<AdapterPerawat.MyViewHolder> {

    private List<ListPerawat> perawatList;
    private Context mContext;

    public AdapterPerawat(List<ListPerawat> perawatList, Context mContext) {
        this.perawatList = perawatList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_perawat, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.id_perawat.setText(perawatList.get(i).getId_perawat());
        myViewHolder.nama_perawat.setText(perawatList.get(i).getNama());
        myViewHolder.jk.setText(perawatList.get(i).getJk());
        myViewHolder.tgl_lahir.setText(perawatList.get(i).getTgl_lahir());
        myViewHolder.alamat.setText(perawatList.get(i).getAlamat());
        myViewHolder.no_hp.setText(perawatList.get(i).getNo_hp());
        myViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Perawat perawat = new Perawat();
                perawat.deletePerawat(mContext, perawatList.get(i).getId_perawat());
            }
        });
    }

    @Override
    public int getItemCount() {
        return perawatList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id_perawat, nama_perawat, jk, tgl_lahir, alamat, no_hp;
        Button btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id_perawat   = itemView.findViewById(R.id.tv_idPerawat);
            nama_perawat = itemView.findViewById(R.id.tv_namaPerawat);
            jk           = itemView.findViewById(R.id.tv_jk);
            tgl_lahir    = itemView.findViewById(R.id.tv_date);
            alamat       = itemView.findViewById(R.id.tv_alamat);
            no_hp        = itemView.findViewById(R.id.tv_noHP);
            btnDelete    = itemView.findViewById(R.id.btnDelete);

        }
    }
}
