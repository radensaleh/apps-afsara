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
import com.example.rdsaleh.adpl_rs.api.ListPeriksa;
import com.example.rdsaleh.adpl_rs.javaClass.Periksa;

import java.util.List;

public class AdapterPeriksa extends RecyclerView.Adapter<AdapterPeriksa.MyViewHolder> {

    private List<ListPeriksa> periksaList;
    private Context mContext;

    public AdapterPeriksa(List<ListPeriksa> periksaList, Context mContext) {
        this.periksaList = periksaList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_periksa, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tv_idPeriksa.setText(periksaList.get(i).getId_periksa());
        myViewHolder.tv_idRM.setText(periksaList.get(i).getId_rm());
        myViewHolder.tv_idDokter.setText(periksaList.get(i).getId_dokter());
        myViewHolder.tv_diagnosa.setText(periksaList.get(i).getDiagnosa());
        myViewHolder.tv_tglPeriksa.setText(periksaList.get(i).getTgl_periksa());
        myViewHolder.tv_statusRawat.setText(periksaList.get(i).getStatus_rawat());
        myViewHolder.tv_statusPeriksa.setText(periksaList.get(i).getStatus_periksa());
        myViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Periksa periksa = new Periksa();
                periksa.deletePeriksa(mContext,periksaList.get(i).getId_periksa());
            }
        });
    }

    @Override
    public int getItemCount() {
        return periksaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_idPeriksa, tv_idRM, tv_idDokter, tv_diagnosa, tv_tglPeriksa, tv_statusRawat, tv_statusPeriksa;
        Button btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_idPeriksa = itemView.findViewById(R.id.tv_idPeriksa);
            tv_idRM      = itemView.findViewById(R.id.tv_idRm);
            tv_idDokter  = itemView.findViewById(R.id.tv_idDokter);
            tv_diagnosa  = itemView.findViewById(R.id.tv_diagnosa);
            tv_tglPeriksa= itemView.findViewById(R.id.tv_tglPeriksa);
            tv_statusRawat= itemView.findViewById(R.id.tv_sttsRawat);
            tv_statusPeriksa= itemView.findViewById(R.id.tv_sttsPeriksa);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
