package com.example.rdsaleh.adpl_rs.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.rdsaleh.adpl_rs.DataPeriksaDokterActivity;
import com.example.rdsaleh.adpl_rs.R;
import com.example.rdsaleh.adpl_rs.api.ListPeriksa;
import com.example.rdsaleh.adpl_rs.javaClass.Dokter;
import com.example.rdsaleh.adpl_rs.javaClass.Periksa;
import com.example.rdsaleh.adpl_rs.javaClass.RekamMedis;

import java.util.List;

public class AdapterPeriksaDokter extends RecyclerView.Adapter<AdapterPeriksaDokter.MyViewHolder> {

    private List<ListPeriksa> periksaList;
    private Context mContext;

    private AlertDialog.Builder dialog;
    private LayoutInflater inflater;
    private View dialogView;

    private EditText et_idPeriksa, et_idRM, et_idDokter, et_diagnosa, et_tglPeriksa;
    private RadioGroup RG_statusRawat, RG_statusPeriksa;
    private RadioButton rb1,rb2;

    private String status_periksa, status_rawat;
    private ProgressDialog pd;

    public AdapterPeriksaDokter(List<ListPeriksa> periksaList, Context mContext) {
        this.periksaList = periksaList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_periksa_dokter, viewGroup, false);
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
        myViewHolder.btnPeriksa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new AlertDialog.Builder(mContext);
                inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                dialogView = inflater.inflate(R.layout.activity_dokter_periksa_pasien, null);
                dialog.setView(dialogView);
                dialog.setCancelable(false);
                dialog.setTitle("Periksa Pasien");

                et_idDokter    = dialogView.findViewById(R.id.et_idDokter);
                et_idPeriksa   = dialogView.findViewById(R.id.et_idPeriksa);
                et_idRM        = dialogView.findViewById(R.id.et_idRM);
                et_diagnosa    = dialogView.findViewById(R.id.et_diagnosa);
                et_tglPeriksa  = dialogView.findViewById(R.id.et_tglPeriksa);
                RG_statusRawat = dialogView.findViewById(R.id.RG_StatusRawat);
                RG_statusPeriksa = dialogView.findViewById(R.id.RG_StatusPeriksa);

                et_idPeriksa.setText(periksaList.get(i).getId_periksa());
                et_idPeriksa.setEnabled(false);

                et_idRM.setText(periksaList.get(i).getId_rm());
                et_idRM.setEnabled(false);

                et_idDokter.setText(periksaList.get(i).getId_dokter());
                et_idDokter.setEnabled(false);

                et_tglPeriksa.setText(periksaList.get(i).getTgl_periksa());
                et_tglPeriksa.setEnabled(false);

                RG_statusRawat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if(checkedId == R.id.rb_jalan){
                            RadioButton rb = dialogView.findViewById(checkedId);
                            status_rawat = (String) rb.getText();
                        }else if(checkedId == R.id.rb_inap){
                            RadioButton rb = dialogView.findViewById(checkedId);
                            status_rawat = (String) rb.getText();

                        }
                    }
                });


                rb1 = dialogView.findViewById(R.id.rb_sudah);
                rb2 = dialogView.findViewById(R.id.rb_belum);

                RG_statusPeriksa.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if(checkedId == R.id.rb_sudah){
                            status_periksa = (String) rb1.getText();
                        }else if(checkedId == R.id.rb_belum){
                            status_periksa = (String) rb2.getText();

                        }
                    }
                });


                String getSttsPeriksa = periksaList.get(i).getStatus_periksa();

                if(getSttsPeriksa.equals("Sudah Diperiksa")){
                    rb1.setChecked(true);
                }else if(getSttsPeriksa.equals("Belum Diperiksa")){
                    rb2.setChecked(true);
                }

                dialog.setNegativeButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(TextUtils.isEmpty(et_diagnosa.getText())){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("Diagnosa Kosong")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }else if(status_rawat == null){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("Status Rawat Kosong")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }else if(status_periksa == null){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("Status Periksa Kosong")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }else{
                            String id_periksa  = et_idPeriksa.getText().toString();
                            String id_rm       = et_idRM.getText().toString();
                            String id_dokter   = et_idDokter.getText().toString();
                            String diagnosa    = et_diagnosa.getText().toString();
                            String tgl_periksa = et_tglPeriksa.getText().toString();

                            pd = new ProgressDialog(mContext);
                            Periksa periksa = new Periksa();
                            RekamMedis RM = new RekamMedis();
                            Dokter dokter = new Dokter();

                            periksa.PrevRM(RM);
                            periksa.PrevDokter(dokter);
                            periksa.updatePeriksaDokter(id_periksa,id_rm,id_dokter,diagnosa,tgl_periksa,status_rawat,status_periksa,pd,mContext);

                        }
                    }
                }).setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return periksaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_idPeriksa, tv_idRM, tv_idDokter, tv_diagnosa, tv_tglPeriksa, tv_statusRawat, tv_statusPeriksa;
        Button btnPeriksa;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_idPeriksa = itemView.findViewById(R.id.tv_idPeriksa);
            tv_idRM      = itemView.findViewById(R.id.tv_idRm);
            tv_idDokter  = itemView.findViewById(R.id.tv_idDokter);
            tv_diagnosa  = itemView.findViewById(R.id.tv_diagnosa);
            tv_tglPeriksa= itemView.findViewById(R.id.tv_tglPeriksa);
            tv_statusRawat= itemView.findViewById(R.id.tv_sttsRawat);
            tv_statusPeriksa= itemView.findViewById(R.id.tv_sttsPeriksa);
            btnPeriksa = itemView.findViewById(R.id.btnPeriksa);

        }
    }
}
