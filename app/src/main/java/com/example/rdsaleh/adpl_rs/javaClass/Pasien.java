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
import com.example.rdsaleh.adpl_rs.DataPasienActivity;
import com.example.rdsaleh.adpl_rs.R;
import com.example.rdsaleh.adpl_rs.adapter.AdapterPasien;
import com.example.rdsaleh.adpl_rs.api.ListPasien;
import com.example.rdsaleh.adpl_rs.api.Response;
import com.example.rdsaleh.adpl_rs.retroServer.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Pasien extends Person{

    private String id_pasien;

    private List<ListPasien> pasienList;
    private AdapterPasien adapterPasien;

    private RekamMedis rm = new RekamMedis();

    public String getId_pasien() {
        return id_pasien;
    }

    public void setId_pasien(String id_pasien) {
        this.id_pasien = id_pasien;
    }

    public void addPasien(String id_pasien, String password, String nama, String jk, String tgl_lahir, String alamat, String no_hp, int status, final ProgressDialog pd, final Context mContext){
        this.setId_pasien(id_pasien);
        this.setPassword(password);
        this.setNama(nama);
        this.setJk(jk);
        this.setTgl_lahir(tgl_lahir);
        this.setAlamat(alamat);
        this.setNo_hp(no_hp);
        this.setStatus(status);

        pd.setIcon(R.drawable.pasien);
        pd.setTitle("Add Pasien");
        pd.setMessage("Please waiting . . .");
        pd.show();

        Runnable pr = new Runnable() {
            @Override
            public void run() {

                Call<Response> call = RetrofitClient
                        .getInstance()
                        .baseAPI()
                        .addPasien(getId_pasien(),getPassword(),getNama(),getJk(),getTgl_lahir(),getAlamat(),getNo_hp(),getStatus());

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
                                    .setMessage("Pasien " + getNama() + " Berhasil ditambahkan")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            String id_rm = "RM" + getId_pasien();
                                            rm.addRM(id_rm, getId_pasien());

                                            Bundle extras = ((DataPasienActivity) mContext).getIntent().getExtras();
                                            String id_admin = extras.getString("id_admin");

                                            Intent i = new Intent(mContext, DataPasienActivity.class);
                                            i.putExtra("id_admin", id_admin);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            ((DataPasienActivity) mContext).startActivityForResult(i, 0);
                                            ((DataPasienActivity) mContext).overridePendingTransition(0,0);
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

    public void showPasien(final RecyclerView recyclerView, final Context mContext){
        Call<List<ListPasien>> listPasien = RetrofitClient
                .getInstance()
                .baseAPI()
                .getAllPasien();

        listPasien.enqueue(new Callback<List<ListPasien>>() {
            @Override
            public void onResponse(Call<List<ListPasien>> call, retrofit2.Response<List<ListPasien>> response) {
                pasienList = response.body();
                adapterPasien = new AdapterPasien(pasienList, mContext);
                recyclerView.setAdapter(adapterPasien);
                adapterPasien.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ListPasien>> call, Throwable t) {

            }
        });
    }

    public void deletePasien(final Context mContext, final String id_pasien){
        new AlertDialog.Builder(mContext)
                .setIcon(R.drawable.failed)
                .setTitle("Delete")
                .setMessage("Anda Yakin Ingin Mendelete Data " + id_pasien + " ? ")
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
                        .deletePasien(id_pasien);

                call.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        String error   = response.body().getErrorRes();
                        String message = response.body().getMessageRes();

                        if(error.equals("0")){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.success)
                                    .setTitle("Success")
                                    .setMessage("Data " + id_pasien + " Berhasil Dihapus")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            Bundle extras = ((DataPasienActivity) mContext).getIntent().getExtras();
                                            String id_admin = extras.getString("id_admin");

                                            Intent i = new Intent(mContext, DataPasienActivity.class);
                                            i.putExtra("id_admin", id_admin);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            ((DataPasienActivity) mContext).startActivityForResult(i, 0);
                                            ((DataPasienActivity) mContext).overridePendingTransition(0,0);
                                            mContext.startActivity(i);

                                        }
                                    }).show();
                        }else if(error.equals("1")){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("Data " + id_pasien + " Gagal Dihapus")
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
