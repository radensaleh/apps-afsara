package com.example.rdsaleh.adpl_rs.javaClass;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;

import com.example.rdsaleh.adpl_rs.DataKelasActivity;
import com.example.rdsaleh.adpl_rs.DataRawatActivity;
import com.example.rdsaleh.adpl_rs.R;
import com.example.rdsaleh.adpl_rs.adapter.AdapterRawatInap;
import com.example.rdsaleh.adpl_rs.api.ListRawatInap;
import com.example.rdsaleh.adpl_rs.api.Response;
import com.example.rdsaleh.adpl_rs.retroServer.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class RawatInap {

    private String id_rawat, tgl_masuk;
    private ShiftJaga jaga;
    private Pasien pasien;
    private Admin admin;

    private List<ListRawatInap> rawatInapList;
    private AdapterRawatInap adapterRawatInap;

    public String getId_rawat() {
        return id_rawat;
    }

    public void setId_rawat(String id_rawat) {
        this.id_rawat = id_rawat;
    }

    public String getTgl_masuk() {
        return tgl_masuk;
    }

    public void setTgl_masuk(String tgl_masuk) {
        this.tgl_masuk = tgl_masuk;
    }

    public void addRawat(String id_rawat, String id_jaga, String id_pasien, String id_admin, String tgl_masuk, final ProgressDialog pd, final Context mContext){
        this.setId_rawat(id_rawat);
        this.jaga.setId_jaga(id_jaga);
        this.pasien.setId_pasien(id_pasien);
        this.admin.setId_admin(id_admin);
        this.setTgl_masuk(tgl_masuk);

        pd.setIcon(R.drawable.rawatinap);
        pd.setTitle("Add Rawat Inap");
        pd.setMessage("Please waiting . . .");
        pd.setCancelable(false);
        pd.show();

        Runnable pr = new Runnable() {
            @Override
            public void run() {

                Call<Response> call = RetrofitClient
                        .getInstance()
                        .baseAPI()
                        .addRawat(getId_rawat(),jaga.getId_jaga(),pasien.getId_pasien(),admin.getId_admin(),getTgl_masuk());

                call.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<com.example.rdsaleh.adpl_rs.api.Response> call, retrofit2.Response<Response> response) {
                        String error   = response.body().getErrorRes();
                        String message = response.body().getMessageRes();

                        if(error.equals("0")){
                            pd.dismiss();
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.success)
                                    .setTitle("Success")
                                    .setMessage("Rawat Inap " + getId_rawat() + " Berhasil ditambahkan")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Bundle extras = ((DataRawatActivity) mContext).getIntent().getExtras();
                                            String id_admin = extras.getString("id_admin");

                                            Intent i = new Intent(mContext, DataRawatActivity.class);
                                            i.putExtra("id_admin", id_admin);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            ((DataRawatActivity) mContext).startActivityForResult(i, 0);
                                            ((DataRawatActivity) mContext).overridePendingTransition(0,0);
                                            mContext.startActivity(i);
                                        }
                                    }).show();
                        }else if(error.equals("1")){
                            pd.dismiss();
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage(message)
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<com.example.rdsaleh.adpl_rs.api.Response> call, Throwable t) {

                    }
                });
            }
        };

        Handler hander = new Handler();
        hander.postDelayed(pr, 3000);
    }

    public void showRawatInap(final RecyclerView recyclerView, final Context mContext){
        Call<List<ListRawatInap>> listRawat = RetrofitClient
                .getInstance()
                .baseAPI()
                .getAllRawat();

        listRawat.enqueue(new Callback<List<ListRawatInap>>() {
            @Override
            public void onResponse(Call<List<ListRawatInap>> call, retrofit2.Response<List<ListRawatInap>> response) {
                rawatInapList = response.body();
                adapterRawatInap = new AdapterRawatInap(rawatInapList, mContext);
                recyclerView.setAdapter(adapterRawatInap);
                adapterRawatInap.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ListRawatInap>> call, Throwable t) {

            }
        });
    }

    public void deleteRawat(final Context mContext, final String id_rawat){
        new AlertDialog.Builder(mContext)
                .setIcon(R.drawable.failed)
                .setTitle("Delete")
                .setMessage("Anda Yakin Ingin Mendelete Data " + id_rawat + " ? ")
                .setCancelable(false)
                .setPositiveButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    Call<Response> call = RetrofitClient
                            .getInstance()
                            .baseAPI()
                            .deleteRawatInap(id_rawat);

                    call.enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            String error   = response.body().getErrorRes();
                            String message = response.body().getMessageRes();

                            if(error.equals("0")){
                                new AlertDialog.Builder(mContext)
                                        .setIcon(R.drawable.success)
                                        .setTitle("Success")
                                        .setMessage("Data " + id_rawat + " Berhasil Dihapus")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                Bundle extras = ((DataRawatActivity) mContext).getIntent().getExtras();
                                                String id_admin = extras.getString("id_admin");

                                                Intent i = new Intent(mContext, DataRawatActivity.class);
                                                i.putExtra("id_admin", id_admin);
                                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                ((DataRawatActivity) mContext).startActivityForResult(i, 0);
                                                ((DataRawatActivity) mContext).overridePendingTransition(0,0);
                                                mContext.startActivity(i);

                                            }
                                        }).show();
                            }else if(error.equals("1")){
                                new AlertDialog.Builder(mContext)
                                        .setIcon(R.drawable.failed)
                                        .setTitle("Failed")
                                        .setMessage("Data " + id_rawat + " Gagal Dihapus")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {

                        }
                    });
            }
        }).show();
    }

    public void PrevJaga(ShiftJaga sj){
        this.jaga = sj;
    }

    public void PrevPasien(Pasien p){
        this.pasien = p;
    }

    public void PrevAdmin(Admin adm){
        this.admin = adm;
    }
}
