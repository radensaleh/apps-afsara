package com.example.rdsaleh.adpl_rs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.rdsaleh.adpl_rs.api.ListDokter;
import com.example.rdsaleh.adpl_rs.api.ListPasien;
import com.example.rdsaleh.adpl_rs.retroServer.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainDokterActivity extends AppCompatActivity {

    Context mContext;
    private CardView cvInfo, cvPeriksa;

    private AlertDialog.Builder dialog;
    private LayoutInflater inflater;
    private View dialogView;

    private TextView tv_idDokter, tv_idSpesialis, tv_namaDokter, tv_jk, tv_date, tv_alamat, tv_nohp;

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(mContext)
                .setTitle("Alert")
                .setMessage("Anda Ingin Logout ? ")
                .setCancelable(false)
                .setIcon(R.drawable.ic_warning_logout_24dp)
                .setPositiveButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(MainDokterActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        }).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dokter);

        mContext = this;

        cvInfo = findViewById(R.id.cvInfoDokter);
        cvPeriksa = findViewById(R.id.cvPeriksa);

        cvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new AlertDialog.Builder(mContext);
                inflater = getLayoutInflater();
                dialogView = inflater.inflate(R.layout.info_dokter, null);
                dialog.setView(dialogView);
                dialog.setIcon(R.drawable.doktor);
                dialog.setCancelable(false);
                dialog.setTitle("My Info");

                tv_idDokter   = dialogView.findViewById(R.id.tv_idDokter);
                tv_idSpesialis= dialogView.findViewById(R.id.tv_idSpesialis);
                tv_namaDokter = dialogView.findViewById(R.id.tv_namaDokter);
                tv_jk         = dialogView.findViewById(R.id.tv_jk);
                tv_date       = dialogView.findViewById(R.id.tv_date);
                tv_alamat     = dialogView.findViewById(R.id.tv_alamat);
                tv_nohp       = dialogView.findViewById(R.id.tv_noHP);

                String id_dokter;
                Bundle extras = getIntent().getExtras();

                id_dokter = extras.getString("id_dokter");

                Call<ListDokter> call = RetrofitClient
                        .getInstance()
                        .baseAPI()
                        .getDokter(id_dokter);

                call.enqueue(new Callback<ListDokter>() {
                    @Override
                    public void onResponse(Call<ListDokter> call, Response<ListDokter> response) {
                        tv_idDokter.setText(response.body().getId_dokter());
                        tv_idSpesialis.setText(response.body().getId_spesialis());
                        tv_namaDokter.setText(response.body().getNama());
                        tv_jk.setText(response.body().getJk());
                        tv_date.setText(response.body().getTgl_lahir());
                        tv_alamat.setText(response.body().getAlamat());
                        tv_nohp.setText(response.body().getNo_hp());
                    }

                    @Override
                    public void onFailure(Call<ListDokter> call, Throwable t) {

                    }
                });

                dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

            }
        });

        cvPeriksa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_dokter;
                Bundle extras = getIntent().getExtras();

                id_dokter = extras.getString("id_dokter");

                Intent i = new Intent(mContext, DataPeriksaDokterActivity.class);
                i.putExtra("id_dokter", id_dokter);
                startActivity(i);
            }
        });

    }
}
