package com.example.rdsaleh.adpl_rs.javaClass;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;

import com.example.rdsaleh.adpl_rs.DataPembayaranActivity;
import com.example.rdsaleh.adpl_rs.DataPendaftaranActivity;
import com.example.rdsaleh.adpl_rs.R;
import com.example.rdsaleh.adpl_rs.adapter.AdapterPembayaran;
import com.example.rdsaleh.adpl_rs.api.ListPembayaran;
import com.example.rdsaleh.adpl_rs.api.Response;
import com.example.rdsaleh.adpl_rs.retroServer.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Pembayaran {

    private String id_pembayaran, tgl_bayar;
    private  int jml_biaya_daftar, jml_biaya_inap;
    private Pasien pasien;
    private Admin adm;

    private List<ListPembayaran> pembayaranList;
    private AdapterPembayaran adapterPembayaran;

    public String getId_pembayaran() {
        return id_pembayaran;
    }

    public void setId_pembayaran(String id_pembayaran) {
        this.id_pembayaran = id_pembayaran;
    }

    public String getTgl_bayar() {
        return tgl_bayar;
    }

    public void setTgl_bayar(String tgl_bayar) {
        this.tgl_bayar = tgl_bayar;
    }

    public int getJml_biaya_daftar() {
        return jml_biaya_daftar;
    }

    public void setJml_biaya_daftar(int jml_biaya_daftar) {
        this.jml_biaya_daftar = jml_biaya_daftar;
    }

    public int getJml_biaya_inap() {
        return jml_biaya_inap;
    }

    public void setJml_biaya_inap(int jml_biaya_inap) {
        this.jml_biaya_inap = jml_biaya_inap;
    }

    public void PrevPasien(Pasien p){
        this.pasien = p;
    }

    public void PrevAdmin(Admin adm){
        this.adm = adm;
    }

    public void addPembayaran(String id_pembayaran, String id_admin, String id_pasien, String tgl_bayar, int jml_biaya_daftar, int jml_biaya_inap, final ProgressDialog pd, final Context mContext){
        this.setId_pembayaran(id_pembayaran);
        this.adm.setId_admin(id_admin);
        this.pasien.setId_pasien(id_pasien);
        this.setTgl_bayar(tgl_bayar);
        this.setJml_biaya_daftar(jml_biaya_daftar);
        this.setJml_biaya_inap(jml_biaya_inap);

        pd.setIcon(R.drawable.daftar);
        pd.setTitle("Add Pembayaran Pasien");
        pd.setMessage("Please waiting . . .");
        pd.setCancelable(false);
        pd.show();

        Runnable pr = new Runnable() {
            @Override
            public void run() {
                Call<Response> call = RetrofitClient
                        .getInstance()
                        .baseAPI()
                        .addPembayaran(getId_pembayaran(),adm.getId_admin(),pasien.getId_pasien(),getTgl_bayar(),getJml_biaya_daftar(),getJml_biaya_inap());

                call.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        String error   = response.body().getErrorRes();
                        String message = response.body().getMessageRes();

                        if(error.equals("0")){
                            pd.dismiss();
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.success)
                                    .setTitle("Success")
                                    .setMessage("Pembayaran " + getId_pembayaran() + " Berhasil ditambahkan")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Bundle extras = ((DataPembayaranActivity) mContext).getIntent().getExtras();
                                            String id_admin = extras.getString("id_admin");

                                            Intent i = new Intent(mContext, DataPembayaranActivity.class);
                                            i.putExtra("id_admin", id_admin);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            ((DataPembayaranActivity) mContext).startActivityForResult(i, 0);
                                            ((DataPembayaranActivity) mContext).overridePendingTransition(0,0);
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
                    public void onFailure(Call<Response> call, Throwable t) {

                    }
                });
            }
        };

        Handler handler = new Handler();
        handler.postDelayed(pr, 3000);


    }

    public void showPembayaran(final RecyclerView rv, final Context mContext){
        Call<List<ListPembayaran>> listPembayaran = RetrofitClient
                .getInstance()
                .baseAPI()
                .getAllPembayaran();

        listPembayaran.enqueue(new Callback<List<ListPembayaran>>() {
            @Override
            public void onResponse(Call<List<ListPembayaran>> call, retrofit2.Response<List<ListPembayaran>> response) {
                pembayaranList = response.body();
                adapterPembayaran = new AdapterPembayaran(pembayaranList, mContext);
                rv.setAdapter(adapterPembayaran);
                adapterPembayaran.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ListPembayaran>> call, Throwable t) {

            }
        });
    }

    public void deletePembayaran(final Context mContext, final String id_pembayaran){
        new AlertDialog.Builder(mContext)
                .setIcon(R.drawable.failed)
                .setTitle("Delete")
                .setMessage("Anda Yakin Ingin Mendelete Data " + id_pembayaran + " ? ")
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
                            .deletePembayaran(id_pembayaran);

                    call.enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            String error   = response.body().getErrorRes();
                            String message = response.body().getMessageRes();

                            if(error.equals("0")){
                                new AlertDialog.Builder(mContext)
                                        .setIcon(R.drawable.success)
                                        .setTitle("Success")
                                        .setMessage("Data " + id_pembayaran + " Berhasil Dihapus")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                Bundle extras = ((DataPembayaranActivity) mContext).getIntent().getExtras();
                                                String id_admin = extras.getString("id_admin");

                                                Intent i = new Intent(mContext, DataPembayaranActivity.class);
                                                i.putExtra("id_admin", id_admin);
                                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                ((DataPembayaranActivity) mContext).startActivityForResult(i, 0);
                                                ((DataPembayaranActivity) mContext).overridePendingTransition(0,0);
                                                mContext.startActivity(i);

                                            }
                                        }).show();
                            }else if(error.equals("1")){
                                new AlertDialog.Builder(mContext)
                                        .setIcon(R.drawable.failed)
                                        .setTitle("Failed")
                                        .setMessage("Data " + id_pembayaran + " Gagal Dihapus")
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
