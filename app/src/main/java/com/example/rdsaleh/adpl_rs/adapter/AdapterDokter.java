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
import com.example.rdsaleh.adpl_rs.api.ListDokter;
import com.example.rdsaleh.adpl_rs.javaClass.Dokter;

import java.util.List;

public class AdapterDokter extends RecyclerView.Adapter<AdapterDokter.MyViewHolder> {

    private List<ListDokter> dokterList;
    private Context mContext;

    public AdapterDokter(List<ListDokter> dokterList, Context mContext) {
        this.dokterList = dokterList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_dokter, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.id_dokter.setText(dokterList.get(i).getId_dokter());
        myViewHolder.id_spesialis.setText(dokterList.get(i).getId_spesialis());
        myViewHolder.nama_dokter.setText(dokterList.get(i).getNama());
        myViewHolder.jk.setText(dokterList.get(i).getJk());
        myViewHolder.tgl_lahir.setText(dokterList.get(i).getTgl_lahir());
        myViewHolder.alamat.setText(dokterList.get(i).getAlamat());
        myViewHolder.no_hp.setText(dokterList.get(i).getNo_hp());
        myViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dokter dokter = new Dokter();
                dokter.deleteDokter(mContext, dokterList.get(i).getId_dokter());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dokterList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id_dokter, id_spesialis, nama_dokter, jk, tgl_lahir, alamat, no_hp;
        Button btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id_dokter   = itemView.findViewById(R.id.tv_idDokter);
            id_spesialis= itemView.findViewById(R.id.tv_idSpesialis);
            nama_dokter = itemView.findViewById(R.id.tv_namaDokter);
            jk          = itemView.findViewById(R.id.tv_jk);
            tgl_lahir   = itemView.findViewById(R.id.tv_date);
            alamat      = itemView.findViewById(R.id.tv_alamat);
            no_hp       = itemView.findViewById(R.id.tv_noHP);
            btnDelete   = itemView.findViewById(R.id.btnDelete);

        }
    }
}
