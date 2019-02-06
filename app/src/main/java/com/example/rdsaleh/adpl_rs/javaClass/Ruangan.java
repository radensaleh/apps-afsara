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
import com.example.rdsaleh.adpl_rs.DataRuanganActivity;
import com.example.rdsaleh.adpl_rs.R;
import com.example.rdsaleh.adpl_rs.adapter.AdapterRuangan;
import com.example.rdsaleh.adpl_rs.api.ListRuangan;
import com.example.rdsaleh.adpl_rs.api.Response;
import com.example.rdsaleh.adpl_rs.retroServer.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Ruangan {

    private String id_ruangan;
    private String nama_ruangan;
    private Kelas kelas;
    private String status;

    private List<ListRuangan> ruanganList;
    private AdapterRuangan adapterRuangan;

    public String getId_ruangan() {
        return id_ruangan;
    }

    public void setId_ruangan(String id_ruangan) {
        this.id_ruangan = id_ruangan;
    }

    public String getNama_ruangan() {
        return nama_ruangan;
    }

    public void setNama_ruangan(String nama_ruangan) {
        this.nama_ruangan = nama_ruangan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addRuangan(final String id_ruangan, final String nama_ruangan, final String id_kelas, final String status, final ProgressDialog pd, final Context mContext){
        this.setId_ruangan(id_ruangan);
        this.setNama_ruangan(nama_ruangan);
        this.kelas.setId_kelas(id_kelas);
        this.setStatus(status);

        pd.setIcon(R.drawable.ruangan);
        pd.setTitle("Add Ruangan");
        pd.setMessage("Please Waiting. . .");
        pd.setCancelable(false);
        pd.show();

        Runnable pr = new Runnable() {
            @Override
            public void run() {


                Call<Response> call = RetrofitClient
                        .getInstance()
                        .baseAPI()
                        .addRuangan(getId_ruangan(),getNama_ruangan(),kelas.getId_kelas(),getStatus());

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
                                    .setMessage("Ruangan " + getNama_ruangan() + " Berhasil ditambahkan")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Bundle extras = ((DataRuanganActivity) mContext).getIntent().getExtras();
                                            String id_admin = extras.getString("id_admin");

                                            Intent i = new Intent(mContext, DataRuanganActivity.class);
                                            i.putExtra("id_admin", id_admin);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            ((DataRuanganActivity) mContext).startActivityForResult(i, 0);
                                            ((DataRuanganActivity) mContext).overridePendingTransition(0,0);
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

        Handler handler = new Handler();
        handler.postDelayed(pr, 3000);
    }

    public void showRuangan(final RecyclerView recyclerView, final Context mContext){
        Call<List<ListRuangan>> listRuangan = RetrofitClient
                .getInstance()
                .baseAPI()
                .getAllRuangan();

        listRuangan.enqueue(new Callback<List<ListRuangan>>() {
            @Override
            public void onResponse(Call<List<ListRuangan>> call, retrofit2.Response<List<ListRuangan>> response) {
                ruanganList = response.body();
                adapterRuangan = new AdapterRuangan(ruanganList, mContext);
                recyclerView.setAdapter(adapterRuangan);
                adapterRuangan.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ListRuangan>> call, Throwable t) {

            }
        });
    }

    public void deleteRuangan(final Context mContext, final String id_ruangan){
        new AlertDialog.Builder(mContext)
                .setIcon(R.drawable.failed)
                .setTitle("Delete")
                .setMessage("Anda Yakin Ingin Mendelete Data " + id_ruangan + " ? ")
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
                        .deleteRuangan(id_ruangan);

                call.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        String error   = response.body().getErrorRes();
                        String message = response.body().getMessageRes();

                        if(error.equals("0")){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.success)
                                    .setTitle("Success")
                                    .setMessage("Data " + id_ruangan + " Berhasil Dihapus")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            Bundle extras = ((DataRuanganActivity) mContext).getIntent().getExtras();
                                            String id_admin = extras.getString("id_admin");

                                            Intent i = new Intent(mContext, DataRuanganActivity.class);
                                            i.putExtra("id_admin", id_admin);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            ((DataRuanganActivity) mContext).startActivityForResult(i, 0);
                                            ((DataRuanganActivity) mContext).overridePendingTransition(0,0);
                                            mContext.startActivity(i);

                                        }
                                    }).show();
                        }else if(error.equals("1")){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("Data " + id_ruangan + " Gagal Dihapus")
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

    public void prevKelas(Kelas k){
        this.kelas = k;
    }
}
