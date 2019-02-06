package com.example.rdsaleh.adpl_rs;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.rdsaleh.adpl_rs.javaClass.ShiftJaga;

public class JadwalJagaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    Context mContext;

    private ShiftJaga shiftJaga = new ShiftJaga();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_jaga);

        recyclerView = findViewById(R.id.recyclerJadwalJaga);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mContext = this;

        String id_perawat;
        Bundle extras = getIntent().getExtras();

        id_perawat = extras.getString("id_perawat");

        shiftJaga.JadwalJagaPasien(recyclerView,mContext,id_perawat);

    }
}
