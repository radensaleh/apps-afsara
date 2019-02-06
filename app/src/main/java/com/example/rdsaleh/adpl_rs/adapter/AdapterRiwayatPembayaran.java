package com.example.rdsaleh.adpl_rs.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rdsaleh.adpl_rs.R;
import com.example.rdsaleh.adpl_rs.api.ListPembayaran;

import java.util.List;

public class AdapterRiwayatPembayaran extends RecyclerView.Adapter<AdapterRiwayatPembayaran.MyViewHolder> {

    private List<ListPembayaran> pembayaranList;
    private Context mContext;

    public AdapterRiwayatPembayaran(List<ListPembayaran> pembayaranList, Context mContext) {
        this.pembayaranList = pembayaranList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_riwayat_pembayaran, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tvIdPembayaran.setText(pembayaranList.get(i).getId_pembayaran());
        myViewHolder.tvIdPasien.setText(pembayaranList.get(i).getId_pasien());
        myViewHolder.tvDate.setText(pembayaranList.get(i).getTgl_pembayaran());
        myViewHolder.tvJmlBiayaDaftar.setText(String.valueOf(pembayaranList.get(i).getJml_biaya_daftar()));
        myViewHolder.tvJmlBiayaInap.setText(String.valueOf(pembayaranList.get(i).getJml_biaya_inap()));
        myViewHolder.tvTotal.setText(String.valueOf(pembayaranList.get(i).getJml_biaya_daftar() + pembayaranList.get(i).getJml_biaya_inap()));
    }

    @Override
    public int getItemCount() {
        return pembayaranList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvIdPembayaran, tvIdPasien, tvDate,tvJmlBiayaDaftar, tvJmlBiayaInap, tvTotal;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIdPembayaran = itemView.findViewById(R.id.tv_idPembayaran);
            tvIdPasien     = itemView.findViewById(R.id.tv_idPasien);
            tvDate         = itemView.findViewById(R.id.tv_date);
            tvJmlBiayaDaftar = itemView.findViewById(R.id.tv_jmlbiayadaftar);
            tvJmlBiayaInap   = itemView.findViewById(R.id.tv_jmlbiayainap);
            tvTotal   = itemView.findViewById(R.id.tv_totalbiaya);

        }
    }
}
