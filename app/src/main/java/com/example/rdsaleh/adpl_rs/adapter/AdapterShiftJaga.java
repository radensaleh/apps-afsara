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
import com.example.rdsaleh.adpl_rs.api.ListShiftJaga;
import com.example.rdsaleh.adpl_rs.javaClass.ShiftJaga;

import java.util.List;

public class AdapterShiftJaga extends RecyclerView.Adapter<AdapterShiftJaga.MyViewHolder> {

    private Context mContext;
    private List<ListShiftJaga> shiftJagaList;

    public AdapterShiftJaga(Context mContext, List<ListShiftJaga> shiftJagaList) {
        this.mContext = mContext;
        this.shiftJagaList = shiftJagaList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_shiftjaga, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.id_jaga.setText(shiftJagaList.get(i).getId_jaga());
        myViewHolder.id_ruangan.setText(shiftJagaList.get(i).getId_ruangan());
        myViewHolder.id_perawat.setText(shiftJagaList.get(i).getId_perawat());
        myViewHolder.tgl_jaga.setText(shiftJagaList.get(i).getTgl_jaga());
        myViewHolder.shift.setText(shiftJagaList.get(i).getShift());
        myViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShiftJaga shiftJaga = new ShiftJaga();
                shiftJaga.deleteJaga(mContext, shiftJagaList.get(i).getId_jaga(), shiftJagaList.get(i).getId_ruangan());
            }
        });
    }

    @Override
    public int getItemCount() {
        return shiftJagaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id_jaga, id_ruangan, id_perawat, tgl_jaga, shift;
        Button btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id_jaga    = itemView.findViewById(R.id.tv_idJaga);
            id_ruangan = itemView.findViewById(R.id.tv_idRuangan);
            id_perawat = itemView.findViewById(R.id.tv_idPerawat);
            tgl_jaga   = itemView.findViewById(R.id.tv_tglJaga);
            shift      = itemView.findViewById(R.id.tv_shifJaga);
            btnDelete  = itemView.findViewById(R.id.btnDelete);
        }
    }
}
