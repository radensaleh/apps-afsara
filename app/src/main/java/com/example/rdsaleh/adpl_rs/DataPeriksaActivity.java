package com.example.rdsaleh.adpl_rs;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rdsaleh.adpl_rs.api.ListDokter;
import com.example.rdsaleh.adpl_rs.api.ListRM;
import com.example.rdsaleh.adpl_rs.javaClass.Dokter;
import com.example.rdsaleh.adpl_rs.javaClass.Periksa;
import com.example.rdsaleh.adpl_rs.javaClass.RekamMedis;
import com.example.rdsaleh.adpl_rs.retroServer.RetrofitClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataPeriksaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressDialog pd;

    Context mContext;

    private EditText et_idPeriksa;
    private Spinner spRM, spDokter;
    private TextView txtDate, txtidDokter, txtidRM;
    private Button btnDate;
    private int mYear, mMonth, mDay;
    private RadioGroup RG;
    private String status_periksa;

    private AlertDialog.Builder dialog;
    private LayoutInflater inflater;
    private View dialogView;

    private Periksa periksa = new Periksa();
    private RekamMedis rm = new RekamMedis();
    private Dokter dokter = new Dokter();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();

        switch (id){
            case R.id.action_add:
                dialog = new AlertDialog.Builder(DataPeriksaActivity.this);
                inflater = getLayoutInflater();
                dialogView = inflater.inflate(R.layout.activity_add_periksa, null);
                dialog.setView(dialogView);
                dialog.setCancelable(false);
                dialog.setTitle("Add Data");

                et_idPeriksa = dialogView.findViewById(R.id.et_idPeriksa);
                spRM = dialogView.findViewById(R.id.spinnerRM);
                spDokter = dialogView.findViewById(R.id.spinnerDokter);
                txtDate = dialogView.findViewById(R.id.tvDate);
                btnDate = dialogView.findViewById(R.id.btnDate);

                txtidDokter = dialogView.findViewById(R.id.txtidDokter);
                txtidRM     = dialogView.findViewById(R.id.txtidRM);

                getDokter();
                getRM();

                RG = dialogView.findViewById(R.id.RG_StatusPeriksa);
                RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if(checkedId == R.id.rb_sudah){
                            RadioButton rb = dialogView.findViewById(checkedId);
                            status_periksa = (String) rb.getText();

                        }else if(checkedId == R.id.rb_belum){
                            RadioButton rb = dialogView.findViewById(checkedId);
                            status_periksa = (String) rb.getText();

                        }
                    }
                });

                btnDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get Current Date
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);


                        DatePickerDialog datePickerDialog = new DatePickerDialog(DataPeriksaActivity.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {

                                        txtDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });

                txtDate.setText("date");


                dialog.setNegativeButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(TextUtils.isEmpty(et_idPeriksa.getText())){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("ID Periksa Kosong")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }else if(txtidRM.getText() == "Pilih ID Rekam Medis"){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("ID Rekam Medis Kosong")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }else if(txtidDokter.getText() == "Pilih ID Dokter"){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("ID Dokter Kosong")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }else if(status_periksa == null){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("Status Periksa Kosong")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }else{
                            String id_periksa = et_idPeriksa.getText().toString();
                            String id_rm      = spRM.getSelectedItem().toString();
                            String id_dokter  = spDokter.getSelectedItem().toString();
                            String tgl_periksa = txtDate.getText().toString();

                            periksa.PrevDokter(dokter);
                            periksa.PrevRM(rm);

                            periksa.addPeriksa(id_periksa,id_rm,id_dokter,tgl_periksa,status_periksa,pd,mContext);

                        }
                    }
                }).setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

                return true;
                default:
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_periksa);

        recyclerView = findViewById(R.id.recyclerPeriksa);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mContext = this;
        pd = new ProgressDialog(mContext);

        periksa.showPeriksa(recyclerView, mContext);

    }

    public void getDokter(){
        Call<List<ListDokter>> call = RetrofitClient
                .getInstance()
                .baseAPI()
                .getAllDokter();

        call.enqueue(new Callback<List<ListDokter>>() {
            @Override
            public void onResponse(Call<List<ListDokter>> call, Response<List<ListDokter>> response) {
                if(response.isSuccessful()){
                    final List<ListDokter> dokterList = response.body();
                    List<String> listSpinner = new ArrayList<>();
                    listSpinner.add(0, "ID Dokter");

                    for(int i= 0; i< dokterList.size(); i++){
                        listSpinner.add(dokterList.get(i).getId_dokter());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spDokter.setAdapter(adapter);

                    spDokter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(parent.getItemAtPosition(position).equals("ID Dokter")){
                                txtidDokter.setText("Pilih ID Dokter");
                            }else{
                                for(int i=0; i<dokterList.size(); i++){
                                    if(position == i+1){
                                        txtidDokter.setText("Dokter " + dokterList.get(i).getNama());
                                    }
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<List<ListDokter>> call, Throwable t) {

            }
        });
    }

    public void getRM(){
        Call<List<ListRM>> call = RetrofitClient
                .getInstance()
                .baseAPI()
                .getAllRM();

        call.enqueue(new Callback<List<ListRM>>() {
            @Override
            public void onResponse(Call<List<ListRM>> call, Response<List<ListRM>> response) {
                if(response.isSuccessful()){
                    final List<ListRM> rmList = response.body();
                    final List<String> listSpinner = new ArrayList<>();
                    listSpinner.add(0, "ID Rekam Medis");

                    for(int i=0; i<rmList.size(); i++){
                        listSpinner.add(rmList.get(i).getId_rm());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spRM.setAdapter(adapter);

                    spRM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(parent.getItemAtPosition(position).equals("ID Rekam Medis")){
                                txtidRM.setText("Pilih ID Rekam Medis");
                            }else{
                                for(int i=0; i < rmList.size(); i++){
                                    if(position == i+1){
                                        txtidRM.setText("ID " + rmList.get(i).getId_rm());
                                    }
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<List<ListRM>> call, Throwable t) {

            }
        });
    }
}
