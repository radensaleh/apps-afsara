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
import com.example.rdsaleh.adpl_rs.api.ListRawatInap;
import com.example.rdsaleh.adpl_rs.javaClass.RawatInap;

import java.util.List;

public class AdapterRawatInap extends RecyclerView.Adapter<AdapterRawatInap.MyViewHolder> {

    private List<ListRawatInap> rawatInapList;
    private Context mContext;

    public AdapterRawatInap(List<ListRawatInap> rawatInapList, Context mContext) {
        this.rawatInapList = rawatInapList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_rawatinap, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.id_rawat.setText(rawatInapList.get(i).getId_rawat());
        myViewHolder.id_jaga.setText(rawatInapList.get(i).getId_jaga());
        myViewHolder.id_pasien.setText(rawatInapList.get(i).getId_pasien());
        myViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RawatInap rawatInap = new RawatInap();
                rawatInap.deleteRawat(mContext, rawatInapList.get(i).getId_rawat());
            }
        });
    }

    @Override
    public int getItemCount() {
        return rawatInapList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id_rawat, id_jaga, id_pasien, tgl_masuk;
        Button btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id_rawat = itemView.findViewById(R.id.tv_idRawat);
            id_jaga  = itemView.findViewById(R.id.tv_idJaga);
            id_pasien= itemView.findViewById(R.id.tv_idPasien);
            tgl_masuk= itemView.findViewById(R.id.tv_date);
            btnDelete= itemView.findViewById(R.id.btnDelete);

        }
    }
}
