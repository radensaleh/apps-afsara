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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rdsaleh.adpl_rs.api.ListPasien;
import com.example.rdsaleh.adpl_rs.javaClass.Admin;
import com.example.rdsaleh.adpl_rs.javaClass.Pasien;
import com.example.rdsaleh.adpl_rs.javaClass.Pendaftaran;
import com.example.rdsaleh.adpl_rs.retroServer.RetrofitClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataPendaftaranActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressDialog pd;

    Context mContext;

    private AlertDialog.Builder dialog;
    private LayoutInflater inflater;
    private View dialogView;

    private EditText et_idPendaftaran;
    private Spinner spPasien;
    private TextView txtnamapasien, txtDate;
    private Button btnDate;
    private int mYear, mMonth, mDay;

    private Pasien pasien = new Pasien();
    private Admin admin   = new Admin();
    private Pendaftaran p = new Pendaftaran();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();

        switch (id){
            case R.id.action_add:
                dialog = new AlertDialog.Builder(DataPendaftaranActivity.this);
                inflater = getLayoutInflater();
                dialogView = inflater.inflate(R.layout.activity_pendaftaran, null);
                dialog.setView(dialogView);
                dialog.setCancelable(false);
                dialog.setTitle("Add Data");

                et_idPendaftaran = dialogView.findViewById(R.id.et_idPendaftaran);
                spPasien = dialogView.findViewById(R.id.spinnerPasien);
                txtnamapasien = dialogView.findViewById(R.id.txtnamaPasien);
                txtDate = dialogView.findViewById(R.id.tvDate);
                btnDate = dialogView.findViewById(R.id.btnDate);

                btnDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get Current Date
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);


                        DatePickerDialog datePickerDialog = new DatePickerDialog(DataPendaftaranActivity.this,
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

                getPasien();

                txtDate.setText("date");
                dialog.setNegativeButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(TextUtils.isEmpty(et_idPendaftaran.getText())){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("ID Pendaftaran Kosong")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }else if(txtnamapasien.getText() == "Pilih ID Pasien") {
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("ID Pasien Kosong")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }else if(txtDate.getText() == "date") {
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("Tanggal Daftar Kosong")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }else{
                            p.PrevAdmin(admin);
                            p.PrevPasien(pasien);

                            String id_pendaftaran = et_idPendaftaran.getText().toString();
                            String id_pasien      = spPasien.getSelectedItem().toString();
                            String date = txtDate.getText().toString();
                            String id_admin;

                            Bundle extras = getIntent().getExtras();
                            id_admin = extras.getString("id_admin");

                            p.addPendaftaran(id_pendaftaran,id_pasien, id_admin, date, pd, mContext);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pendaftaran);

        recyclerView = findViewById(R.id.recyclerPendaftaran);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mContext = this;
        pd = new ProgressDialog(mContext);

        p.showPendaftaran(recyclerView, mContext);

//        Bundle extras = getIntent().getExtras();
//        String id_admin = extras.getString("id_admin");
//        Toast.makeText(mContext, id_admin, Toast.LENGTH_LONG).show();

    }

    public void getPasien(){
        Call<List<ListPasien>> call = RetrofitClient
                .getInstance()
                .baseAPI()
                .getAllPasien();

        call.enqueue(new Callback<List<ListPasien>>() {
            @Override
            public void onResponse(Call<List<ListPasien>> call, Response<List<ListPasien>> response) {
                if(response.isSuccessful()){

                    final List<ListPasien> allPasien = response.body();
                    List<String> listSpinner = new ArrayList<>();
                    listSpinner.add(0, "ID Pasien");

                    for(int i = 0; i < allPasien.size(); i++){
                        listSpinner.add(allPasien.get(i).getId_pasien());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spPasien.setAdapter(adapter);

                    spPasien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(parent.getItemAtPosition(position).equals("ID Pasien")){
                                txtnamapasien.setText("Pilih ID Pasien");
                            }else{

                                for(int i = 0; i < allPasien.size(); i++){
                                    if(position == i+1){
                                        txtnamapasien.setText("Pasien " + allPasien.get(i).getNama());
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
            public void onFailure(Call<List<ListPasien>> call, Throwable t) {

            }
        });
    }
}
