package com.example.rdsaleh.adpl_rs;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.rdsaleh.adpl_rs.adapter.AdapterRiwayatPeriksa;
import com.example.rdsaleh.adpl_rs.api.ListPeriksa;
import com.example.rdsaleh.adpl_rs.retroServer.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatPeriksaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressDialog pd;

    Context mContext;

    private List<ListPeriksa> periksaList;
    private AdapterRiwayatPeriksa adapterRiwayatPeriksa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_periksa);

        recyclerView = findViewById(R.id.recyclerRiwayatPeriksa);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mContext = this;
        pd = new ProgressDialog(mContext);

        String id_pasien;
        Bundle extras = getIntent().getExtras();

        id_pasien = extras.getString("id_pasien");

        String id_rm = "RM" + id_pasien;

        Call<List<ListPeriksa>> call = RetrofitClient
                .getInstance()
                .baseAPI()
                .getPeriksa(id_rm);

        call.enqueue(new Callback<List<ListPeriksa>>() {
            @Override
            public void onResponse(Call<List<ListPeriksa>> call, Response<List<ListPeriksa>> response) {
                periksaList = response.body();
                adapterRiwayatPeriksa = new AdapterRiwayatPeriksa(periksaList,mContext);
                recyclerView.setAdapter(adapterRiwayatPeriksa);
                adapterRiwayatPeriksa.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ListPeriksa>> call, Throwable t) {

            }
        });
    }
}
