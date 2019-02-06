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
import com.example.rdsaleh.adpl_rs.api.ListKelas;
import com.example.rdsaleh.adpl_rs.javaClass.Kelas;

import java.util.List;


public class AdapterKelas extends RecyclerView.Adapter<AdapterKelas.MyViewHolder>{

    private List<ListKelas> listKelas;
    private Context mContext;

    public AdapterKelas(List<ListKelas> listKelas, Context mContext) {
        this.listKelas = listKelas;
        this.mContext  = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_kelas, viewGroup , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        myViewHolder.id_kelas.setText(listKelas.get(i).getId_kelas());
        myViewHolder.biaya.setText(String.valueOf(listKelas.get(i).getBiaya()));
        myViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              Kelas kelas = new Kelas();
              kelas.deleteKelas(mContext, listKelas.get(i).getId_kelas());


            }
        });
    }

    @Override
    public int getItemCount() {
        return listKelas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id_kelas, biaya;
        Button btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id_kelas  = itemView.findViewById(R.id.tv_idKelas);
            biaya     = itemView.findViewById(R.id.tv_biaya);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
