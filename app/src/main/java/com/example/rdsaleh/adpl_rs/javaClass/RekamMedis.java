package com.example.rdsaleh.adpl_rs.javaClass;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;

import com.example.rdsaleh.adpl_rs.DataRmActivity;
import com.example.rdsaleh.adpl_rs.R;
import com.example.rdsaleh.adpl_rs.adapter.AdapterRM;
import com.example.rdsaleh.adpl_rs.api.ListRM;
import com.example.rdsaleh.adpl_rs.api.Response;
import com.example.rdsaleh.adpl_rs.retroServer.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class RekamMedis {

    private String id_rm;

    private List<ListRM> rmList;
    private AdapterRM adapterRM;

    public String getId_rm() {
        return id_rm;
    }

    public void setId_rm(String id_rm) {
        this.id_rm = id_rm;
    }

    public void addRM(String id_rm, String id_pasien){
        this.setId_rm(id_rm);

        Call<Response> call = RetrofitClient
                        .getInstance()
                        .baseAPI()
                        .addRM(getId_rm(),id_pasien);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }

//    public void addRM(String id_rm, String id_pasien, final ProgressDialog pd, final Context mContext){
//        this.setId_rm(id_rm);
//        this.pasien.setId_pasien(id_pasien);
//
//        pd.setIcon(R.drawable.rekam_medis);
//        pd.setTitle("Add Rekam Medis");
//        pd.setMessage("Please Waiting. . .");
//        pd.setCancelable(false);
//        pd.show();
//
//        Runnable pr = new Runnable() {
//            @Override
//            public void run() {
//
//                Call<Response> call = RetrofitClient
//                        .getInstance()
//                        .baseAPI()
//                        .addRM(getId_rm(),pasien.getId_pasien());
//
//                call.enqueue(new Callback<Response>() {
//                    @Override
//                    public void onResponse(Call<com.example.rdsaleh.adpl_rs.api.Response> call, retrofit2.Response<Response> response) {
//                        String error   = response.body().getErrorRes();
//                        String message = response.body().getMessageRes();
//
//                        if(error.equals("0")){
//                            pd.dismiss();
//                            new AlertDialog.Builder(mContext)
//                                    .setIcon(R.drawable.success)
//                                    .setTitle("Success")
//                                    .setMessage("Rekam Medis " + getId_rm() + " Berhasil ditambahkan")
//                                    .setCancelable(false)
//                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            Bundle extras = ((DataRmActivity) mContext).getIntent().getExtras();
//                                            String id_admin = extras.getString("id_admin");
//
//                                            Intent i = new Intent(mContext, DataRmActivity.class);
//                                            i.putExtra("id_admin", id_admin);
//                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                            ((DataRmActivity) mContext).startActivityForResult(i, 0);
//                                            ((DataRmActivity) mContext).overridePendingTransition(0,0);
//                                            mContext.startActivity(i);
//                                        }
//                                    }).show();
//                        }else if(error.equals("1")){
//                            pd.dismiss();
//                            new AlertDialog.Builder(mContext)
//                                    .setIcon(R.drawable.failed)
//                                    .setTitle("Failed")
//                                    .setMessage(message)
//                                    .setCancelable(false)
//                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//
//                                        }
//                                    }).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<com.example.rdsaleh.adpl_rs.api.Response> call, Throwable t) {
//
//                    }
//                });
//
//            }
//        };
//
//        Handler handler = new Handler();
//        handler.postDelayed(pr, 3000);
//
//    }

    public void showRM(final RecyclerView recyclerView, final Context mContext){
        Call<List<ListRM>> listRm = RetrofitClient
                .getInstance()
                .baseAPI()
                .getAllRM();

        listRm.enqueue(new Callback<List<ListRM>>() {
            @Override
            public void onResponse(Call<List<ListRM>> call, retrofit2.Response<List<ListRM>> response) {
                rmList = response.body();
                adapterRM = new AdapterRM(rmList, mContext);
                recyclerView.setAdapter(adapterRM);
                adapterRM.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ListRM>> call, Throwable t) {

            }
        });
    }

}
