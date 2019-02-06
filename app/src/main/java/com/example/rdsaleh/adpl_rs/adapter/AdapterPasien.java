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
import com.example.rdsaleh.adpl_rs.api.ListPasien;
import com.example.rdsaleh.adpl_rs.javaClass.Pasien;

import java.util.List;

public class AdapterPasien extends RecyclerView.Adapter<AdapterPasien.MyViewHolder>{

    private List<ListPasien> pasienList;
    private Context mContext;

    public AdapterPasien(List<ListPasien> pasienList, Context mContext) {
        this.pasienList = pasienList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_pasien, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.id_pasien.setText(pasienList.get(i).getId_pasien());
        myViewHolder.nama_pasien.setText(pasienList.get(i).getNama());
        myViewHolder.jk.setText(pasienList.get(i).getJk());
        myViewHolder.tgl_lahir.setText(pasienList.get(i).getTgl_lahir());
        myViewHolder.alamat.setText(pasienList.get(i).getAlamat());
        myViewHolder.no_hp.setText(pasienList.get(i).getNo_hp());
        myViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pasien pasien = new Pasien();
                pasien.deletePasien(mContext, pasienList.get(i).getId_pasien());
            }
        });
    }

    @Override
    public int getItemCount() {
        return pasienList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id_pasien, nama_pasien, jk, tgl_lahir, alamat, no_hp;
        Button btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id_pasien   = itemView.findViewById(R.id.tv_idPasien);
            nama_pasien = itemView.findViewById(R.id.tv_namaPasien);
            jk          = itemView.findViewById(R.id.tv_jk);
            tgl_lahir   = itemView.findViewById(R.id.tv_date);
            alamat      = itemView.findViewById(R.id.tv_alamat);
            no_hp       = itemView.findViewById(R.id.tv_noHP);
            btnDelete   = itemView.findViewById(R.id.btnDelete);

        }
    }
}
