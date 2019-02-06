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
import com.example.rdsaleh.adpl_rs.DataSpesialisActivity;
import com.example.rdsaleh.adpl_rs.R;
import com.example.rdsaleh.adpl_rs.adapter.AdapterSpesialis;
import com.example.rdsaleh.adpl_rs.api.ListSpesialis;
import com.example.rdsaleh.adpl_rs.api.Response;
import com.example.rdsaleh.adpl_rs.retroServer.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Spesialis {

    private String id_spesialis;
    private String nama_spesialis;
    private int biaya;

    private List<ListSpesialis> spesialisList;
    private AdapterSpesialis adapterSpesialis;

    public int getBiaya() {
        return biaya;
    }

    public void setBiaya(int biaya) {
        this.biaya = biaya;
    }

    public String getId_spesialis() {
        return id_spesialis;
    }

    public void setId_spesialis(String id_spesialis) {
        this.id_spesialis = id_spesialis;
    }

    public String getNama_spesialis() {
        return nama_spesialis;
    }

    public void setNama_spesialis(String nama_spesialis) {
        this.nama_spesialis = nama_spesialis;
    }

    public void addSpesialis(final String id_spesialis, final String nama_spesialis, final int biaya, final ProgressDialog pd, final Context mContext){
        this.setId_spesialis(id_spesialis);
        this.setNama_spesialis(nama_spesialis);
        this.setBiaya(biaya);

        pd.setIcon(R.drawable.spesialis);
        pd.setTitle("Add Spesialis");
        pd.setMessage("Please Waiting. . .");
        pd.setCancelable(false);
        pd.show();

        Runnable pr = new Runnable() {
            @Override
            public void run() {



                Call<Response> call = RetrofitClient
                        .getInstance()
                        .baseAPI()
                        .addSpesialis(getId_spesialis(),getNama_spesialis(),getBiaya());

                call.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<com.example.rdsaleh.adpl_rs.api.Response> call, retrofit2.Response<com.example.rdsaleh.adpl_rs.api.Response> response) {
                        String error   = response.body().getErrorRes();
                        String message = response.body().getMessageRes();

                        if(error.equals("0")){
                            pd.dismiss();
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.success)
                                    .setTitle("Success")
                                    .setMessage("Spesialis " + getNama_spesialis() + " Berhasil ditambahkan")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Bundle extras = ((DataSpesialisActivity) mContext).getIntent().getExtras();
                                            String id_admin = extras.getString("id_admin");

                                            Intent i = new Intent(mContext, DataSpesialisActivity.class);
                                            i.putExtra("id_admin", id_admin);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            ((DataSpesialisActivity) mContext).startActivityForResult(i, 0);
                                            ((DataSpesialisActivity) mContext).overridePendingTransition(0,0);
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
                        Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG);
                    }
                });
            }
        };

        Handler pdCancel = new Handler();
        pdCancel.postDelayed(pr, 3000);
    }

    public void showSpesialis(final RecyclerView recyclerView, final Context mContext){
        Call<List<ListSpesialis>> listSpesialis = RetrofitClient
                .getInstance()
                .baseAPI()
                .getSpesialis();

        listSpesialis.enqueue(new Callback<List<ListSpesialis>>() {
            @Override
            public void onResponse(Call<List<ListSpesialis>> call, retrofit2.Response<List<ListSpesialis>> response) {
                spesialisList = response.body();
                adapterSpesialis = new AdapterSpesialis(spesialisList, mContext);
                recyclerView.setAdapter(adapterSpesialis);
                adapterSpesialis.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ListSpesialis>> call, Throwable t) {

            }
        });
    }

    public void deleteSpesialis(final Context mContext, final String id_spesialis){
        new AlertDialog.Builder(mContext)
                .setIcon(R.drawable.failed)
                .setTitle("Delete")
                .setMessage("Anda Yakin Ingin Mendelete Data " + id_spesialis + " ? ")
                .setCancelable(false)
                .setPositiveButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Call<Response> call = RetrofitClient
                        .getInstance()
                        .baseAPI()
                        .deleteSpesialis(id_spesialis);

                call.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        String error   = response.body().getErrorRes();
                        String message = response.body().getMessageRes();

                        if(error.equals("0")){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.success)
                                    .setTitle("Success")
                                    .setMessage("Data " + id_spesialis + " Berhasil Dihapus")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            Bundle extras = ((DataSpesialisActivity) mContext).getIntent().getExtras();
                                            String id_admin = extras.getString("id_admin");

                                            Intent i = new Intent(mContext, DataSpesialisActivity.class);
                                            i.putExtra("id_admin", id_admin);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            ((DataSpesialisActivity) mContext).startActivityForResult(i, 0);
                                            ((DataSpesialisActivity) mContext).overridePendingTransition(0,0);
                                            mContext.startActivity(i);

                                        }
                                    }).show();
                        }else if(error.equals("1")){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("Data " + id_spesialis + " Gagal Dihapus")
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
