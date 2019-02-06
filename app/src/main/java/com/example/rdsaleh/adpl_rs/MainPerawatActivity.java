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
import com.example.rdsaleh.adpl_rs.api.ListPerawat;
import com.example.rdsaleh.adpl_rs.retroServer.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPerawatActivity extends AppCompatActivity {

    Context mContext;
    private CardView cvInfo, cvJadwal;

    private AlertDialog.Builder dialog;
    private LayoutInflater inflater;
    private View dialogView;

    private TextView tv_idPerawat, tv_namaPerawat, tv_jk, tv_date, tv_alamat, tv_nohp;

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
                Intent i = new Intent(MainPerawatActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        }).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_perawat);

        mContext = this;

        cvInfo = findViewById(R.id.cvInfoPerawat);
        cvJadwal = findViewById(R.id.cvJadwal);

        cvJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id_perawat;
                Bundle extras = getIntent().getExtras();

                id_perawat = extras.getString("id_perawat");

                Intent i = new Intent(mContext, JadwalJagaActivity.class);
                i.putExtra("id_perawat", id_perawat);
                startActivity(i);
            }
        });

        cvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new AlertDialog.Builder(mContext);
                inflater = getLayoutInflater();
                dialogView = inflater.inflate(R.layout.info_perawat, null);
                dialog.setView(dialogView);
                dialog.setIcon(R.drawable.perawat);
                dialog.setCancelable(false);
                dialog.setTitle("My Info");

                tv_idPerawat   = dialogView.findViewById(R.id.tv_idPerawat);
                tv_namaPerawat = dialogView.findViewById(R.id.tv_namaPerawat);
                tv_jk         = dialogView.findViewById(R.id.tv_jk);
                tv_date       = dialogView.findViewById(R.id.tv_date);
                tv_alamat     = dialogView.findViewById(R.id.tv_alamat);
                tv_nohp       = dialogView.findViewById(R.id.tv_noHP);

                String id_perawat;
                Bundle extras = getIntent().getExtras();

                id_perawat = extras.getString("id_perawat");

                Call<ListPerawat> call = RetrofitClient
                        .getInstance()
                        .baseAPI()
                        .getPerawat(id_perawat);

                call.enqueue(new Callback<ListPerawat>() {
                    @Override
                    public void onResponse(Call<ListPerawat> call, Response<ListPerawat> response) {
                        tv_idPerawat.setText(response.body().getId_perawat());
                        tv_namaPerawat.setText(response.body().getNama());
                        tv_jk.setText(response.body().getJk());
                        tv_date.setText(response.body().getTgl_lahir());
                        tv_alamat.setText(response.body().getAlamat());
                        tv_nohp.setText(response.body().getNo_hp());
                    }

                    @Override
                    public void onFailure(Call<ListPerawat> call, Throwable t) {

                    }
                });

                dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                
            }
        });
    }
}
