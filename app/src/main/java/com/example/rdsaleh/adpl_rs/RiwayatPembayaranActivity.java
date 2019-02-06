package com.example.rdsaleh.adpl_rs;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.rdsaleh.adpl_rs.adapter.AdapterPembayaran;
import com.example.rdsaleh.adpl_rs.adapter.AdapterRiwayatPembayaran;
import com.example.rdsaleh.adpl_rs.api.ListPembayaran;
import com.example.rdsaleh.adpl_rs.retroServer.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatPembayaranActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressDialog pd;

    Context mContext;

    private List<ListPembayaran> pembayaranList;
    private AdapterRiwayatPembayaran adapterRiwayatPembayaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_pembayaran);

        recyclerView = findViewById(R.id.recyclerRiwayatPembayaran);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mContext = this;

        String id_pasien;
        Bundle extras = getIntent().getExtras();

        id_pasien = extras.getString("id_pasien");

        Call<List<ListPembayaran>> call = RetrofitClient
                .getInstance()
                .baseAPI()
                .getPembayaran(id_pasien);

        call.enqueue(new Callback<List<ListPembayaran>>() {
            @Override
            public void onResponse(Call<List<ListPembayaran>> call, Response<List<ListPembayaran>> response) {
                pembayaranList = response.body();
                adapterRiwayatPembayaran = new AdapterRiwayatPembayaran(pembayaranList, mContext);
                recyclerView.setAdapter(adapterRiwayatPembayaran);
                adapterRiwayatPembayaran.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ListPembayaran>> call, Throwable t) {

            }
        });
    }
}
