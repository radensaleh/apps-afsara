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
import com.example.rdsaleh.adpl_rs.DataPerawatActivity;
import com.example.rdsaleh.adpl_rs.R;
import com.example.rdsaleh.adpl_rs.adapter.AdapterPerawat;
import com.example.rdsaleh.adpl_rs.api.ListPerawat;
import com.example.rdsaleh.adpl_rs.api.Response;
import com.example.rdsaleh.adpl_rs.retroServer.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Perawat extends Person {

    private String id_perawat;

    private List<ListPerawat> perawatList;
    private AdapterPerawat adapterPerawat;

    public String getId_perawat() {
        return id_perawat;
    }

    public void setId_perawat(String id_perawat) {
        this.id_perawat = id_perawat;
    }

    public void addPerawat(String id_perawat, String password, String nama, String jk, String tgl_lahir, String alamat, String no_hp, int status, final ProgressDialog pd, final Context mContext){
        this.setId_perawat(id_perawat);
        this.setPassword(password);
        this.setNama(nama);
        this.setJk(jk);
        this.setTgl_lahir(tgl_lahir);
        this.setAlamat(alamat);
        this.setNo_hp(no_hp);
        this.setStatus(status);

        pd.setIcon(R.drawable.perawat);
        pd.setTitle("Add Perawat");
        pd.setMessage("Please waiting . . .");
        pd.setCancelable(false);
        pd.show();

        Runnable pr = new Runnable() {
            @Override
            public void run() {

                Call<Response> call = RetrofitClient
                        .getInstance()
                        .baseAPI()
                        .addPerawat(getId_perawat(),getPassword(),getNama(),getJk(),getTgl_lahir(),getAlamat(),getNo_hp(), getStatus());

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
                                    .setMessage("Perawat " + getNama() + " Berhasil ditambahkan")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Bundle extras = ((DataPerawatActivity) mContext).getIntent().getExtras();
                                            String id_admin = extras.getString("id_admin");

                                            Intent i = new Intent(mContext, DataPerawatActivity.class);
                                            i.putExtra("id_admin", id_admin);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            ((DataPerawatActivity) mContext).startActivityForResult(i, 0);
                                            ((DataPerawatActivity) mContext).overridePendingTransition(0,0);
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

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(pr, 3000);
    }

    public void showPerawat(final RecyclerView recyclerView, final Context mContext){
        Call<List<ListPerawat>> listPerawat = RetrofitClient
                .getInstance()
                .baseAPI()
                .getAllPerawat();

        listPerawat.enqueue(new Callback<List<ListPerawat>>() {
            @Override
            public void onResponse(Call<List<ListPerawat>> call, retrofit2.Response<List<ListPerawat>> response) {
                perawatList = response.body();
                adapterPerawat = new AdapterPerawat(perawatList, mContext);
                recyclerView.setAdapter(adapterPerawat);
                adapterPerawat.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ListPerawat>> call, Throwable t) {

            }
        });
    }

    public void deletePerawat(final Context mContext, final String id_perawat){
        new AlertDialog.Builder(mContext)
                .setIcon(R.drawable.failed)
                .setTitle("Delete")
                .setMessage("Anda Yakin Ingin Mendelete Data " + id_perawat + " ? ")
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
                        .deletePerawat(id_perawat);

                call.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        String error   = response.body().getErrorRes();
                        String message = response.body().getMessageRes();

                        if(error.equals("0")){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.success)
                                    .setTitle("Success")
                                    .setMessage("Data " + id_perawat + " Berhasil Dihapus")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            Bundle extras = ((DataPerawatActivity) mContext).getIntent().getExtras();
                                            String id_admin = extras.getString("id_admin");

                                            Intent i = new Intent(mContext, DataPerawatActivity.class);
                                            i.putExtra("id_admin", id_admin);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            ((DataPerawatActivity) mContext).startActivityForResult(i, 0);
                                            ((DataPerawatActivity) mContext).overridePendingTransition(0,0);
                                            mContext.startActivity(i);

                                        }
                                    }).show();
                        }else if(error.equals("1")){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("Data " + id_perawat + " Gagal Dihapus")
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
