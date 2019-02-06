package com.example.rdsaleh.adpl_rs;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.rdsaleh.adpl_rs.adapter.AdapterRiwayatPendaftaran;
import com.example.rdsaleh.adpl_rs.api.ListPendaftaran;
import com.example.rdsaleh.adpl_rs.retroServer.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatPendaftaranActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressDialog pd;

    Context mContext;

    private List<ListPendaftaran> pendaftaranList;
    private AdapterRiwayatPendaftaran adapterRiwayatPendaftaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_pendaftaran);

        recyclerView = findViewById(R.id.recyclerRiwayatPendaftaran);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mContext = this;
        pd = new ProgressDialog(mContext);

        String id_pasien;
        Bundle extras = getIntent().getExtras();

        id_pasien = extras.getString("id_pasien");

        Call<List<ListPendaftaran>> call = RetrofitClient
                .getInstance()
                .baseAPI()
                .getPendaftaran(id_pasien);

        call.enqueue(new Callback<List<ListPendaftaran>>() {
            @Override
            public void onResponse(Call<List<ListPendaftaran>> call, Response<List<ListPendaftaran>> response) {
                pendaftaranList = response.body();
                adapterRiwayatPendaftaran = new AdapterRiwayatPendaftaran(pendaftaranList, mContext);
                recyclerView.setAdapter(adapterRiwayatPendaftaran);
                adapterRiwayatPendaftaran.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ListPendaftaran>> call, Throwable t) {

            }
        });

    }
}
