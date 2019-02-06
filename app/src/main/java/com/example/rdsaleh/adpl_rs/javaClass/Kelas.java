package com.example.rdsaleh.adpl_rs.javaClass;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.rdsaleh.adpl_rs.DataKelasActivity;
import com.example.rdsaleh.adpl_rs.R;
import com.example.rdsaleh.adpl_rs.adapter.AdapterKelas;
import com.example.rdsaleh.adpl_rs.api.ListKelas;
import com.example.rdsaleh.adpl_rs.api.Response;
import com.example.rdsaleh.adpl_rs.retroServer.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Kelas {

    private String id_kelas;
    private int biaya;

    private List<ListKelas> kelasList;
    private AdapterKelas adapterKelas;

    public String getId_kelas() {
        return id_kelas;
    }

    public void setId_kelas(String id_kelas) {
        this.id_kelas = id_kelas;
    }

    public int getBiaya() {
        return biaya;
    }

    public void setBiaya(int biaya) {
        this.biaya = biaya;
    }

    public void addKelas(String id_kelas, int biaya, final Context mContext, final ProgressDialog pd){
        this.setId_kelas(id_kelas);
        this.setBiaya(biaya);

        pd.setIcon(R.drawable.kelas);
        pd.setTitle("Add Kelas");
        pd.setMessage("Please Waiting. . .");
        pd.setCancelable(false);
        pd.show();

        Runnable pr = new Runnable() {
            @Override
            public void run() {

                Call<com.example.rdsaleh.adpl_rs.api.Response> call = RetrofitClient
                        .getInstance()
                        .baseAPI()
                        .addKelas(getId_kelas(),getBiaya());

                call.enqueue(new Callback<com.example.rdsaleh.adpl_rs.api.Response>() {
                    @Override
                    public void onResponse(Call<com.example.rdsaleh.adpl_rs.api.Response> call, retrofit2.Response<com.example.rdsaleh.adpl_rs.api.Response> response) {
                        String error   = response.body().getErrorRes();
                        String message = response.body().getMessageRes();

                        if(error.equals("0")){
                            pd.dismiss();
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.success)
                                    .setTitle("Success")
                                    .setMessage("Kelas " + getId_kelas() + " Berhasil ditambahkan")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Bundle extras = ((DataKelasActivity) mContext).getIntent().getExtras();
                                            String id_admin = extras.getString("id_admin");

                                            Intent i = new Intent(mContext, DataKelasActivity.class);
                                            i.putExtra("id_admin", id_admin);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            ((DataKelasActivity) mContext).startActivityForResult(i, 0);
                                            ((DataKelasActivity) mContext).overridePendingTransition(0,0);
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
                        pd.dismiss();
                        Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        };

        Handler handler = new Handler();
        handler.postDelayed(pr, 3000);
    }

    public void showKelas(final RecyclerView recyclerView, final Context mContext){
        Call<List<ListKelas>> listKelas = RetrofitClient
                .getInstance()
                .baseAPI()
                .getAllKelas();

        listKelas.enqueue(new Callback<List<ListKelas>>() {
            @Override
            public void onResponse(Call<List<ListKelas>> call, retrofit2.Response<List<ListKelas>> response) {
                kelasList = response.body();
                adapterKelas = new AdapterKelas(kelasList, mContext);
                recyclerView.setAdapter(adapterKelas);
                adapterKelas.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ListKelas>> call, Throwable t) {

            }
        });
    }

    public void deleteKelas(final Context mContext, final String id_kelas){
        new AlertDialog.Builder(mContext)
                .setIcon(R.drawable.failed)
                .setTitle("Delete")
                .setMessage("Anda Yakin Ingin Mendelete Data " + id_kelas + " ? ")
                .setCancelable(false)
                .setPositiveButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Call<Response> call = RetrofitClient
                        .getInstance().baseAPI()
                        .deleteKelas(id_kelas);

                call.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        String error   = response.body().getErrorRes();
                        String message = response.body().getMessageRes();

                        if(error.equals("0")){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.success)
                                    .setTitle("Success")
                                    .setMessage("Data " + id_kelas + " Berhasil Dihapus")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            Bundle extras = ((DataKelasActivity) mContext).getIntent().getExtras();
                                            String id_admin = extras.getString("id_admin");

                                            Intent i = new Intent(mContext, DataKelasActivity.class);
                                            i.putExtra("id_admin", id_admin);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            ((DataKelasActivity) mContext).startActivityForResult(i, 0);
                                            ((DataKelasActivity) mContext).overridePendingTransition(0,0);
                                            mContext.startActivity(i);

                                        }
                                    }).show();
                        }else if(error.equals("1")){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("Data " + id_kelas + " Gagal Dihapus")
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


}
