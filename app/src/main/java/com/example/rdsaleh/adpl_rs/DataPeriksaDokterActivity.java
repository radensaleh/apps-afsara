package com.example.rdsaleh.adpl_rs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.rdsaleh.adpl_rs.adapter.AdapterPeriksaDokter;
import com.example.rdsaleh.adpl_rs.api.ListPeriksa;
import com.example.rdsaleh.adpl_rs.retroServer.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataPeriksaDokterActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressDialog pd;

    Context mContext;

    private List<ListPeriksa> periksaList;
    private AdapterPeriksaDokter adapterPeriksaDokter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_periksa_dokter);

        recyclerView = findViewById(R.id.recyclerPeriksaDokter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mContext = this;
        pd = new ProgressDialog(mContext);

        String id_dokter;
        Bundle extras = getIntent().getExtras();

        id_dokter = extras.getString("id_dokter");

        Call<List<ListPeriksa>> call = RetrofitClient
                .getInstance()
                .baseAPI()
                .getPeriksaDokter(id_dokter);

        call.enqueue(new Callback<List<ListPeriksa>>() {
            @Override
            public void onResponse(Call<List<ListPeriksa>> call, Response<List<ListPeriksa>> response) {
                periksaList = response.body();
                adapterPeriksaDokter = new AdapterPeriksaDokter(periksaList,mContext);
                recyclerView.setAdapter(adapterPeriksaDokter);
                adapterPeriksaDokter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ListPeriksa>> call, Throwable t) {

            }
        });

    }

}
