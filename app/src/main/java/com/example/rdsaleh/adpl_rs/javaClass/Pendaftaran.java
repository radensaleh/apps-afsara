package com.example.rdsaleh.adpl_rs.javaClass;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;

import com.example.rdsaleh.adpl_rs.DataPendaftaranActivity;
import com.example.rdsaleh.adpl_rs.DataRawatActivity;
import com.example.rdsaleh.adpl_rs.R;
import com.example.rdsaleh.adpl_rs.adapter.AdapterPendaftaran;
import com.example.rdsaleh.adpl_rs.api.ListPendaftaran;
import com.example.rdsaleh.adpl_rs.api.Response;
import com.example.rdsaleh.adpl_rs.retroServer.RetrofitClient;

import java.util.List;
import java.util.logging.Handler;

import retrofit2.Call;
import retrofit2.Callback;

public class Pendaftaran {

    private String id_pendaftaran;
    private Pasien p;
    private Admin adm;
    private String tgl_daftar;

    private List<ListPendaftaran> pendaftaranList;
    private AdapterPendaftaran adapterPendaftaran;

    public String getId_pendaftaran() {
        return id_pendaftaran;
    }

    public void setId_pendaftaran(String id_pendaftaran) {
        this.id_pendaftaran = id_pendaftaran;
    }

    public String getTgl_daftar() {
        return tgl_daftar;
    }

    public void setTgl_daftar(String tgl_daftar) {
        this.tgl_daftar = tgl_daftar;
    }

    public void PrevPasien(Pasien p){
        this.p = p;
    }

    public void PrevAdmin(Admin adm){
        this.adm = adm;
    }

    public void addPendaftaran(String id_pendaftaran, String id_Pasien, String id_admin, String date, final ProgressDialog pd, final Context mContext){
        this.setId_pendaftaran(id_pendaftaran);
        this.p.setId_pasien(id_Pasien);
        this.adm.setId_admin(id_admin);
        this.setTgl_daftar(date);

        pd.setIcon(R.drawable.daftar);
        pd.setTitle("Add Pendaftaran Pasien");
        pd.setMessage("Please waiting . . .");
        pd.setCancelable(false);
        pd.show();

        Runnable r = new Runnable() {
            @Override
            public void run() {
                Call<Response> call = RetrofitClient
                        .getInstance()
                        .baseAPI()
                        .addPendaftaran(getId_pendaftaran(), p.getId_pasien(), adm.getId_admin(), getTgl_daftar());

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
                                    .setMessage("Pendaftaran " + getId_pendaftaran() + " Berhasil ditambahkan")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Bundle extras = ((DataPendaftaranActivity) mContext).getIntent().getExtras();
                                            String id_admin = extras.getString("id_admin");

                                            Intent i = new Intent(mContext, DataPendaftaranActivity.class);
                                            i.putExtra("id_admin", id_admin);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            ((DataPendaftaranActivity) mContext).startActivityForResult(i, 0);
                                            ((DataPendaftaranActivity) mContext).overridePendingTransition(0,0);
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

        android.os.Handler hander = new android.os.Handler();
        hander.postDelayed(r, 3000);

    }

    public void showPendaftaran(final RecyclerView rv, final Context mContext){
        Call<List<ListPendaftaran>> listPendaftaran = RetrofitClient
                .getInstance().baseAPI().getAllPendaftaran();

        listPendaftaran.enqueue(new Callback<List<ListPendaftaran>>() {
            @Override
            public void onResponse(Call<List<ListPendaftaran>> call, retrofit2.Response<List<ListPendaftaran>> response) {
                pendaftaranList = response.body();
                adapterPendaftaran = new AdapterPendaftaran(pendaftaranList,mContext);
                rv.setAdapter(adapterPendaftaran);
                adapterPendaftaran.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ListPendaftaran>> call, Throwable t) {

            }
        });
    }

    public void deletePendaftaran(final Context mContext, final String id_pendaftaran){
        new AlertDialog.Builder(mContext)
                .setIcon(R.drawable.failed)
                .setTitle("Delete")
                .setMessage("Anda Yakin Ingin Mendelete Data " + id_pendaftaran + " ? ")
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
                            .deletePendaftaran(id_pendaftaran);

                    call.enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            String error   = response.body().getErrorRes();
                            String message = response.body().getMessageRes();

                            if(error.equals("0")){
                                new AlertDialog.Builder(mContext)
                                        .setIcon(R.drawable.success)
                                        .setTitle("Success")
                                        .setMessage("Data " + id_pendaftaran + " Berhasil Dihapus")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                Bundle extras = ((DataPendaftaranActivity) mContext).getIntent().getExtras();
                                                String id_admin = extras.getString("id_admin");

                                                Intent i = new Intent(mContext, DataPendaftaranActivity.class);
                                                i.putExtra("id_admin", id_admin);
                                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                ((DataPendaftaranActivity) mContext).startActivityForResult(i, 0);
                                                ((DataPendaftaranActivity) mContext).overridePendingTransition(0,0);
                                                mContext.startActivity(i);

                                            }
                                        }).show();
                            }else if(error.equals("1")){
                                new AlertDialog.Builder(mContext)
                                        .setIcon(R.drawable.failed)
                                        .setTitle("Failed")
                                        .setMessage("Data " + id_pendaftaran + " Gagal Dihapus")
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
