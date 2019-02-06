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
import com.example.rdsaleh.adpl_rs.DataShiftjagaActivity;
import com.example.rdsaleh.adpl_rs.R;
import com.example.rdsaleh.adpl_rs.adapter.AdapterJadwalJaga;
import com.example.rdsaleh.adpl_rs.adapter.AdapterShiftJaga;
import com.example.rdsaleh.adpl_rs.api.ListShiftJaga;
import com.example.rdsaleh.adpl_rs.api.Response;
import com.example.rdsaleh.adpl_rs.retroServer.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ShiftJaga {

    private String id_jaga, tgl_jaga, shiftJaga;
    private Perawat perawat;
    private Ruangan ruangan;
    private AdapterShiftJaga adapterShiftJaga;
    private AdapterJadwalJaga adapterJadwalJaga;
    private List<ListShiftJaga> shiftJagaList;

    public String getId_jaga() {
        return id_jaga;
    }

    public void setId_jaga(String id_jaga) {
        this.id_jaga = id_jaga;
    }

    public String getTgl_jaga() {
        return tgl_jaga;
    }

    public void setTgl_jaga(String tgl_jaga) {
        this.tgl_jaga = tgl_jaga;
    }

    public String getShiftJaga() {
        return shiftJaga;
    }

    public void setShiftJaga(String shiftJaga) {
        this.shiftJaga = shiftJaga;
    }

    public void addShiftJaga(final String id_jaga, final String id_ruangan, final String id_perawat, final String tgl_jaga, final String shift, final ProgressDialog pd, final Context mContext){
        this.setId_jaga(id_jaga);
        this.ruangan.setId_ruangan(id_ruangan);
        this.perawat.setId_perawat(id_perawat);
        this.setTgl_jaga(tgl_jaga);
        this.setShiftJaga(shift);

        pd.setIcon(R.drawable.perawat_jaga);
        pd.setTitle("Add Shift Jaga");
        pd.setMessage("Please Waiting. . .");
        pd.setCancelable(false);
        pd.show();

        Runnable pr = new Runnable() {
            @Override
            public void run() {

                Call<Response> call = RetrofitClient
                        .getInstance()
                        .baseAPI()
                        .addShiftJaga(getId_jaga(), ruangan.getId_ruangan(),perawat.getId_perawat(),getTgl_jaga(),getShiftJaga());

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
                                    .setMessage("ID Jaga " + getId_jaga() + " Berhasil ditambahkan")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            Call<Response> call = RetrofitClient
                                                    .getInstance().baseAPI().updateRuangan(ruangan.getId_ruangan(), "Penuh");

                                            call.enqueue(new Callback<Response>() {
                                                @Override
                                                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                                                }

                                                @Override
                                                public void onFailure(Call<Response> call, Throwable t) {

                                                }
                                            });

                                            Bundle extras = ((DataShiftjagaActivity) mContext).getIntent().getExtras();
                                            String id_admin = extras.getString("id_admin");

                                            Intent i = new Intent(mContext, DataShiftjagaActivity.class);
                                            i.putExtra("id_admin", id_admin);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            ((DataShiftjagaActivity) mContext).startActivityForResult(i, 0);
                                            ((DataShiftjagaActivity) mContext).overridePendingTransition(0,0);
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

    public void showJaga(final RecyclerView recyclerView, final Context mContext){
        Call<List<ListShiftJaga>> listJaga = RetrofitClient
                .getInstance().baseAPI()
                .getAllShift();

        listJaga.enqueue(new Callback<List<ListShiftJaga>>() {
            @Override
            public void onResponse(Call<List<ListShiftJaga>> call, retrofit2.Response<List<ListShiftJaga>> response) {
                shiftJagaList = response.body();
                adapterShiftJaga = new AdapterShiftJaga(mContext, shiftJagaList);
                recyclerView.setAdapter(adapterShiftJaga);
                adapterShiftJaga.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ListShiftJaga>> call, Throwable t) {

            }
        });
    }

    public void JadwalJagaPasien(final RecyclerView recyclerView, final Context mContext, final String id_perawat){
        Call<List<ListShiftJaga>> listJadwal = RetrofitClient
                .getInstance()
                .baseAPI()
                .getJadwalJaga(id_perawat);

        listJadwal.enqueue(new Callback<List<ListShiftJaga>>() {
            @Override
            public void onResponse(Call<List<ListShiftJaga>> call, retrofit2.Response<List<ListShiftJaga>> response) {
                shiftJagaList = response.body();
                adapterJadwalJaga = new AdapterJadwalJaga(mContext, shiftJagaList);
                recyclerView.setAdapter(adapterJadwalJaga);
                adapterJadwalJaga.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ListShiftJaga>> call, Throwable t) {

            }
        });
    }

    public void deleteJaga(final Context mContext, final String id_jaga, final String id_ruangan){
        new AlertDialog.Builder(mContext)
                .setIcon(R.drawable.failed)
                .setTitle("Delete")
                .setMessage("Anda Yakin Ingin Mendelete Data " + id_jaga + " ? ")
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
                            .deleteShiftJaga(id_jaga, id_ruangan);

                    call.enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            String error   = response.body().getErrorRes();
                            String message = response.body().getMessageRes();

                            if(error.equals("0")){
                                new AlertDialog.Builder(mContext)
                                        .setIcon(R.drawable.success)
                                        .setTitle("Success")
                                        .setMessage("Data " + id_jaga + " Berhasil Dihapus")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                Bundle extras = ((DataShiftjagaActivity) mContext).getIntent().getExtras();
                                                String id_admin = extras.getString("id_admin");

                                                Intent i = new Intent(mContext, DataShiftjagaActivity.class);
                                                i.putExtra("id_admin", id_admin);
                                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                ((DataShiftjagaActivity) mContext).startActivityForResult(i, 0);
                                                ((DataShiftjagaActivity) mContext).overridePendingTransition(0,0);
                                                mContext.startActivity(i);

                                            }
                                        }).show();
                            }else if(error.equals("1")){
                                new AlertDialog.Builder(mContext)
                                        .setIcon(R.drawable.failed)
                                        .setTitle("Failed")
                                        .setMessage("Data " + id_jaga + " Gagal Dihapus")
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

    public void PrevRuangan(Ruangan r){
        this.ruangan = r;
    }

    public void PrevPerawat(Perawat p){
        this.perawat = p;
    }

}
