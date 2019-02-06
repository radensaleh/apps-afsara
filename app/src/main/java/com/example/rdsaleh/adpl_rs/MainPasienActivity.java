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

import com.example.rdsaleh.adpl_rs.api.ListPasien;
import com.example.rdsaleh.adpl_rs.retroServer.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPasienActivity extends AppCompatActivity {

    Context mContext;
    private CardView cvInfo, cvRiwayatPeriksa, cvRiwayatPendaftaran, cvRiwayatPembayaran;

    private AlertDialog.Builder dialog;
    private LayoutInflater inflater;
    private View dialogView;

    private TextView tv_idPasien, tv_namaPasien, tv_jk, tv_date, tv_alamat, tv_nohp;

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
                Intent i = new Intent(MainPasienActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        }).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pasien);

        mContext = this;

        cvInfo = findViewById(R.id.cvInfoPasien);
        cvRiwayatPeriksa = findViewById(R.id.cvPeriksa);
        cvRiwayatPendaftaran = findViewById(R.id.cvPendaftaran);
        cvRiwayatPembayaran  = findViewById(R.id.cvPembayaran);

        cvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new AlertDialog.Builder(mContext);
                inflater = getLayoutInflater();
                dialogView = inflater.inflate(R.layout.info_pasien, null);
                dialog.setView(dialogView);
                dialog.setIcon(R.drawable.infopasien);
                dialog.setCancelable(false);
                dialog.setTitle("My Info");

                tv_idPasien   = dialogView.findViewById(R.id.tv_idPasien);
                tv_namaPasien = dialogView.findViewById(R.id.tv_namaPasien);
                tv_jk         = dialogView.findViewById(R.id.tv_jk);
                tv_date       = dialogView.findViewById(R.id.tv_date);
                tv_alamat     = dialogView.findViewById(R.id.tv_alamat);
                tv_nohp       = dialogView.findViewById(R.id.tv_noHP);

                String id_pasien;
                Bundle extras = getIntent().getExtras();

                id_pasien = extras.getString("id_pasien");

                Call<ListPasien> call = RetrofitClient
                        .getInstance()
                        .baseAPI()
                        .getPasien(id_pasien);

                call.enqueue(new Callback<ListPasien>() {
                    @Override
                    public void onResponse(Call<ListPasien> call, Response<ListPasien> response) {
                        tv_idPasien.setText(response.body().getId_pasien());
                        tv_namaPasien.setText(response.body().getNama());
                        tv_jk.setText(response.body().getJk());
                        tv_date.setText(response.body().getTgl_lahir());
                        tv_alamat.setText(response.body().getAlamat());
                        tv_nohp.setText(response.body().getNo_hp());
                    }

                    @Override
                    public void onFailure(Call<ListPasien> call, Throwable t) {

                    }
                });

                dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });

        cvRiwayatPeriksa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_pasien;
                Bundle extras = getIntent().getExtras();

                id_pasien = extras.getString("id_pasien");

                Intent i = new Intent(mContext, RiwayatPeriksaActivity.class);
                i.putExtra("id_pasien", id_pasien);
                startActivity(i);
            }
        });

        cvRiwayatPendaftaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_pasien;
                Bundle extras = getIntent().getExtras();

                id_pasien = extras.getString("id_pasien");

                Intent i = new Intent(mContext, RiwayatPendaftaranActivity.class);
                i.putExtra("id_pasien", id_pasien);
                startActivity(i);
            }
        });

        cvRiwayatPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_pasien;
                Bundle extras = getIntent().getExtras();

                id_pasien = extras.getString("id_pasien");

                Intent i = new Intent(mContext, RiwayatPembayaranActivity.class);
                i.putExtra("id_pasien", id_pasien);
                startActivity(i);
            }
        });

    }
}
