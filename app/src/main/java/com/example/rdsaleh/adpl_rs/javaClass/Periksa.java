package com.example.rdsaleh.adpl_rs.javaClass;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.rdsaleh.adpl_rs.DataDokterActivity;
import com.example.rdsaleh.adpl_rs.DataPasienActivity;
import com.example.rdsaleh.adpl_rs.DataPeriksaActivity;
import com.example.rdsaleh.adpl_rs.DataPeriksaDokterActivity;
import com.example.rdsaleh.adpl_rs.R;
import com.example.rdsaleh.adpl_rs.adapter.AdapterPeriksa;
import com.example.rdsaleh.adpl_rs.api.ListPeriksa;
import com.example.rdsaleh.adpl_rs.api.Response;
import com.example.rdsaleh.adpl_rs.retroServer.RetrofitClient;

import java.util.List;
import java.util.logging.Handler;

import retrofit2.Call;
import retrofit2.Callback;

public class Periksa {

    private String id_periksa, diagnosa, tgl_periksa, status_rawat, status_periksa;
    private RekamMedis rm;
    private Dokter dokter;

    private AdapterPeriksa adapterPeriksa;
    private List<ListPeriksa> periksaList;


    public String getId_periksa() {
        return id_periksa;
    }

    public void setId_periksa(String id_periksa) {
        this.id_periksa = id_periksa;
    }

    public String getDiagnosa() {
        return diagnosa;
    }

    public void setDiagnosa(String diagnosa) {
        this.diagnosa = diagnosa;
    }

    public String getTgl_periksa() {
        return tgl_periksa;
    }

    public void setTgl_periksa(String tgl_periksa) {
        this.tgl_periksa = tgl_periksa;
    }

    public String getStatus_rawat() {
        return status_rawat;
    }

    public void setStatus_rawat(String status_rawat) {
        this.status_rawat = status_rawat;
    }

    public String getStatus_periksa() {
        return status_periksa;
    }

    public void setStatus_periksa(String status_periksa) {
        this.status_periksa = status_periksa;
    }

    public void PrevRM(RekamMedis rm){
        this.rm = rm;
    }

    public void PrevDokter(Dokter dokter){
        this.dokter = dokter;
    }

    public void addPeriksa(String id_periksa, String id_rm, String id_dokter, String tgl_periksa, String status_periksa, final ProgressDialog pd, final Context mContext){
        this.setId_periksa(id_periksa);
        this.rm.setId_rm(id_rm);
        this.dokter.setId_dokter(id_dokter);
        this.setTgl_periksa(tgl_periksa);
        this.setStatus_periksa(status_periksa);

        pd.setIcon(R.drawable.periksa);
        pd.setTitle("Add Periksa");
        pd.setMessage("Please waiting . . .");
        pd.setCancelable(false);
        pd.show();

        Runnable pr = new Runnable() {
            @Override
            public void run() {
                Call<Response> call = RetrofitClient
                        .getInstance()
                        .baseAPI()
                        .addPeriksa(getId_periksa(), rm.getId_rm(), dokter.getId_dokter(),getTgl_periksa(), getStatus_periksa());

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
                                    .setMessage("Periksa " + getId_periksa() + " Berhasil ditambahkan")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Bundle extras = ((DataPeriksaActivity) mContext).getIntent().getExtras();
                                            String id_admin = extras.getString("id_admin");

                                            Intent i = new Intent(mContext, DataPeriksaActivity.class);
                                            i.putExtra("id_admin", id_admin);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            ((DataPeriksaActivity) mContext).startActivityForResult(i, 0);
                                            ((DataPeriksaActivity) mContext).overridePendingTransition(0,0);
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

        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(pr, 3000);


    }

    public void showPeriksa(final RecyclerView recyclerView, final Context mContext){
        Call<List<ListPeriksa>> call = RetrofitClient
                .getInstance()
                .baseAPI()
                .getAllPeriksa();

        call.enqueue(new Callback<List<ListPeriksa>>() {
            @Override
            public void onResponse(Call<List<ListPeriksa>> call, retrofit2.Response<List<ListPeriksa>> response) {
                periksaList = response.body();
                adapterPeriksa = new AdapterPeriksa(periksaList, mContext);
                recyclerView.setAdapter(adapterPeriksa);
                adapterPeriksa.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ListPeriksa>> call, Throwable t) {

            }
        });
    }

    public void deletePeriksa(final Context mContext, final String id_periksa){
        new AlertDialog.Builder(mContext)
                .setIcon(R.drawable.failed)
                .setTitle("Delete")
                .setMessage("Anda Yakin Ingin Mendelete Data " + id_periksa + " ? ")
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
                        .deletePeriksa(id_periksa);

                call.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        String error   = response.body().getErrorRes();
                        String message = response.body().getMessageRes();

                        if(error.equals("0")){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.success)
                                    .setTitle("Success")
                                    .setMessage("Data " + id_periksa + " Berhasil Dihapus")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            Bundle extras = ((DataPeriksaActivity) mContext).getIntent().getExtras();
                                            String id_admin = extras.getString("id_admin");

                                            Intent i = new Intent(mContext, DataPeriksaActivity.class);
                                            i.putExtra("id_admin", id_admin);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            ((DataPeriksaActivity) mContext).startActivityForResult(i, 0);
                                            ((DataPeriksaActivity) mContext).overridePendingTransition(0,0);
                                            mContext.startActivity(i);

                                        }
                                    }).show();
                        }else if(error.equals("1")){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("Data " + id_periksa + " Gagal Dihapus")
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

    public void updatePeriksaDokter(String id_periksa, String id_rm, String id_dokter, String diagnosa, String tgl_periksa, String status_rawat, String status_periksa, final ProgressDialog pd, final Context mContext){
        this.setId_periksa(id_periksa);
        this.rm.setId_rm(id_rm);
        this.dokter.setId_dokter(id_dokter);
        this.setDiagnosa(diagnosa);
        this.setTgl_periksa(tgl_periksa);
        this.setStatus_rawat(status_rawat);
        this.setStatus_periksa(status_periksa);

        pd.setIcon(R.drawable.periksa);
        pd.setTitle("Save Periksa");
        pd.setMessage("Please waiting . . .");
        pd.setCancelable(false);
        pd.show();

        Runnable pr = new Runnable() {
            @Override
            public void run() {
                Call<Response> call = RetrofitClient
                        .getInstance()
                        .baseAPI()
                        .updatePeriksaDokter(getId_periksa(),rm.getId_rm(),dokter.getId_dokter(),getDiagnosa(),getTgl_periksa(),getStatus_rawat(),getStatus_periksa());

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
                                    .setMessage("Periksa " + getId_periksa() + " Berhasil di Update")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Bundle extras = ((DataPeriksaDokterActivity) mContext).getIntent().getExtras();
                                            String id_dokter = extras.getString("id_dokter");

                                            Intent i = new Intent(mContext, DataPeriksaDokterActivity.class);
                                            i.putExtra("id_dokter", id_dokter);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            ((DataPeriksaDokterActivity) mContext).startActivityForResult(i, 0);
                                            ((DataPeriksaDokterActivity) mContext).overridePendingTransition(0,0);
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

        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(pr, 3000);

    }

}
