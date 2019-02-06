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
import com.example.rdsaleh.adpl_rs.api.ListPendaftaran;
import com.example.rdsaleh.adpl_rs.javaClass.Pendaftaran;

import java.util.List;

public class AdapterPendaftaran extends RecyclerView.Adapter<AdapterPendaftaran.MyViewHolder> {

    private List<ListPendaftaran> pendaftaranList;
    private Context mContext;

    public AdapterPendaftaran(List<ListPendaftaran> pendaftaranList, Context mContext) {
        this.pendaftaranList = pendaftaranList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_pendaftaran, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tv_idPendaftaran.setText(pendaftaranList.get(i).getId_pendaftaran());
        myViewHolder.tv_idPasien.setText(pendaftaranList.get(i).getId_pasien());
        myViewHolder.tv_date.setText(pendaftaranList.get(i).getTgl_daftar());
        myViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pendaftaran p = new Pendaftaran();
                p.deletePendaftaran(mContext, pendaftaranList.get(i).getId_pendaftaran());
            }
        });
    }

    @Override
    public int getItemCount() {
        return pendaftaranList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_idPendaftaran, tv_idPasien, tv_date;
        Button btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_idPendaftaran = itemView.findViewById(R.id.tv_idPendaftaran);
            tv_idPasien      = itemView.findViewById(R.id.tv_idPasien);
            tv_date          = itemView.findViewById(R.id.tv_date);
            btnDelete        = itemView.findViewById(R.id.btnDelete);

        }
    }
}
