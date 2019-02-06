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
import com.example.rdsaleh.adpl_rs.api.ListRuangan;
import com.example.rdsaleh.adpl_rs.javaClass.Ruangan;

import java.util.List;


public class AdapterRuangan extends RecyclerView.Adapter<AdapterRuangan.MyViewHolder> {

    private List<ListRuangan> listRuangan;
    private Context mContext;

    public AdapterRuangan(List<ListRuangan> listRuangan, Context mContext) {
        this.listRuangan = listRuangan;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_ruangan, viewGroup , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.id_ruangan.setText(listRuangan.get(i).getId_ruangan());
        myViewHolder.nama_ruangan.setText(listRuangan.get(i).getNama_ruangan());
        myViewHolder.id_kelas.setText(listRuangan.get(i).getId_kelas());
        myViewHolder.status.setText(listRuangan.get(i).getStatus());
        myViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ruangan ruangan = new Ruangan();
                ruangan.deleteRuangan(mContext, listRuangan.get(i).getId_ruangan());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listRuangan.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id_ruangan, nama_ruangan, id_kelas, status;
        Button btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id_ruangan   = itemView.findViewById(R.id.tv_idRuangan);
            nama_ruangan = itemView.findViewById(R.id.tv_namaRuangan);
            id_kelas     = itemView.findViewById(R.id.tv_idKelas);
            status       = itemView.findViewById(R.id.tv_status);
            btnDelete    = itemView.findViewById(R.id.btnDelete);
        }
    }
}
