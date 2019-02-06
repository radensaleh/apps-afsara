package com.example.rdsaleh.adpl_rs.javaClass;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;

import com.example.rdsaleh.adpl_rs.DataDokterActivity;
import com.example.rdsaleh.adpl_rs.DataPasienActivity;
import com.example.rdsaleh.adpl_rs.R;
import com.example.rdsaleh.adpl_rs.adapter.AdapterDokter;
import com.example.rdsaleh.adpl_rs.api.ListDokter;
import com.example.rdsaleh.adpl_rs.api.Response;
import com.example.rdsaleh.adpl_rs.retroServer.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Dokter extends Person {

    private String id_dokter;
    private Spesialis spesialis;

    private List<ListDokter> dokterList;
    private AdapterDokter adapterDokter;


    public String getId_dokter() {
        return id_dokter;
    }

    public void setId_dokter(String id_dokter) {
        this.id_dokter = id_dokter;
    }


    public void addDokter(String id_dokter, String password, String id_spesialis, String nama, String jk, String tgl_lahir, String alamat, String no_hp, int status, final ProgressDialog pd, final Context mContext){
        this.setId_dokter(id_dokter);
        this.setPassword(password);
        this.spesialis.setId_spesialis(id_spesialis);
        this.setNama(nama);
        this.setJk(jk);
        this.setTgl_lahir(tgl_lahir);
        this.setAlamat(alamat);
        this.setNo_hp(no_hp);
        this.setStatus(status);

        pd.setIcon(R.drawable.doktor);
        pd.setTitle("Add Dokter");
        pd.setMessage("Please waiting . . .");
        pd.setCancelable(false);
        pd.show();

        Runnable pr = new Runnable() {
            @Override
            public void run() {

                Call<Response> call = RetrofitClient
                        .getInstance()
                        .baseAPI()
                        .addDokter(getId_dokter(),getPassword(),spesialis.getId_spesialis(),getNama(),getJk(),getTgl_lahir(),getAlamat(),getNo_hp(), getStatus());
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
                                    .setMessage("Dokter " + getNama() + " Berhasil ditambahkan")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Bundle extras = ((DataDokterActivity) mContext).getIntent().getExtras();
                                            String id_admin = extras.getString("id_admin");

                                            Intent i = new Intent(mContext, DataDokterActivity.class);
                                            i.putExtra("id_admin", id_admin);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            ((DataDokterActivity) mContext).startActivityForResult(i, 0);
                                            ((DataDokterActivity) mContext).overridePendingTransition(0,0);
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

        Handler pdCancel = new Handler();
        pdCancel.postDelayed(pr, 3000);
    }

    public void showDokter(final RecyclerView recyclerView, final Context mContext){
        Call<List<ListDokter>> listDokter = RetrofitClient
                .getInstance()
                .baseAPI()
                .getAllDokter();

        listDokter.enqueue(new Callback<List<ListDokter>>() {
            @Override
            public void onResponse(Call<List<ListDokter>> call, retrofit2.Response<List<ListDokter>> response) {
                dokterList = response.body();
                adapterDokter = new AdapterDokter(dokterList, mContext);
                recyclerView.setAdapter(adapterDokter);
                adapterDokter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ListDokter>> call, Throwable t) {

            }
        });
    }

    public void deleteDokter(final Context mContext, final String id_dokter){
        new AlertDialog.Builder(mContext)
                .setIcon(R.drawable.failed)
                .setTitle("Delete")
                .setMessage("Anda Yakin Ingin Mendelete Data " + id_dokter + " ? ")
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
                        .deleteDokter(id_dokter);

                call.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        String error   = response.body().getErrorRes();
                        String message = response.body().getMessageRes();

                        if(error.equals("0")){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.success)
                                    .setTitle("Success")
                                    .setMessage("Data " + id_dokter + " Berhasil Dihapus")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            Bundle extras = ((DataDokterActivity) mContext).getIntent().getExtras();
                                            String id_admin = extras.getString("id_admin");

                                            Intent i = new Intent(mContext, DataDokterActivity.class);
                                            i.putExtra("id_admin", id_admin);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            ((DataDokterActivity) mContext).startActivityForResult(i, 0);
                                            ((DataDokterActivity) mContext).overridePendingTransition(0,0);
                                            mContext.startActivity(i);

                                        }
                                    }).show();
                        }else if(error.equals("1")){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("Data " + id_dokter + " Gagal Dihapus")
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

    public void PrevSpesialis(Spesialis sp){
        this.spesialis = sp;
    }

}
