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
import com.example.rdsaleh.adpl_rs.api.ListShiftJaga;
import com.example.rdsaleh.adpl_rs.javaClass.Admin;
import com.example.rdsaleh.adpl_rs.javaClass.Pasien;
import com.example.rdsaleh.adpl_rs.javaClass.RawatInap;
import com.example.rdsaleh.adpl_rs.javaClass.ShiftJaga;
import com.example.rdsaleh.adpl_rs.retroServer.RetrofitClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRawatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressDialog pd;

    private Button btnDate;
    private EditText et_idRawat;
    private Spinner spJaga, spPasien;
    private TextView txtIdJaga, txtnamaPasien, txtDate;
    private int mYear, mMonth, mDay;

    Context mContext;

    private AlertDialog.Builder dialog;
    private LayoutInflater inflater;
    private View dialogView;

    private RawatInap rawatInap = new RawatInap();
    private ShiftJaga shiftJaga = new ShiftJaga();
    private Pasien pasien       = new Pasien();
    private Admin admin         = new Admin();

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

                dialog = new AlertDialog.Builder(DataRawatActivity.this);
                inflater = getLayoutInflater();
                dialogView = inflater.inflate(R.layout.activity_add_rawat_inap, null);
                dialog.setView(dialogView);
                dialog.setCancelable(false);
                dialog.setTitle("Add Data");


                et_idRawat = dialogView.findViewById(R.id.et_idRawat);
                spJaga     = dialogView.findViewById(R.id.spinnerJaga);
                spPasien   = dialogView.findViewById(R.id.spinnerPasien);
                txtIdJaga  = dialogView.findViewById(R.id.txtShiftJaga);
                txtDate    = dialogView.findViewById(R.id.tvDate);
                btnDate    = dialogView.findViewById(R.id.btnDate);
                txtnamaPasien = dialogView.findViewById(R.id.txtnamaPasien);

                txtDate.setText("date");

                getJaga();
                getPasien();

                btnDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get Current Date
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);


                        DatePickerDialog datePickerDialog = new DatePickerDialog(DataRawatActivity.this,
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

                dialog.setNegativeButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(TextUtils.isEmpty(et_idRawat.getText())){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("ID Rawat Kosong")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }else if(txtIdJaga.getText() == "Pilih ID Jaga"){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("ID Jaga Kosong")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }else if(txtnamaPasien.getText() == "Pilih ID Pasien"){
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
                        }else if(txtDate.getText() == "date"){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("Tanggal Masuk Kosong")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }else{

                            String id_rawat  = et_idRawat.getText().toString().trim();
                            String id_jaga   = spJaga.getSelectedItem().toString().trim();
                            String id_pasien = spPasien.getSelectedItem().toString().trim();
                            String date      = txtDate.getText().toString().trim();
                            String id_admin;

                            Bundle extras = getIntent().getExtras();
                            id_admin = extras.getString("id_admin");

                            rawatInap.PrevJaga(shiftJaga);
                            rawatInap.PrevPasien(pasien);
                            rawatInap.PrevAdmin(admin);
                            rawatInap.addRawat(id_rawat,id_jaga,id_pasien,id_admin,date,pd,mContext);

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
        setContentView(R.layout.activity_data_rawat);

        recyclerView = findViewById(R.id.recyclerRawat);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mContext = this;
        pd = new ProgressDialog(mContext);

        rawatInap.showRawatInap(recyclerView, mContext);

//        Bundle extras = getIntent().getExtras();
//        String id_admin = extras.getString("id_admin");
//        Toast.makeText(mContext, id_admin, Toast.LENGTH_LONG).show();

    }

    public void getJaga(){
        Call<List<ListShiftJaga>> call = RetrofitClient
                .getInstance()
                .baseAPI()
                .getAllShift();

        call.enqueue(new Callback<List<ListShiftJaga>>() {
            @Override
            public void onResponse(Call<List<ListShiftJaga>> call, Response<List<ListShiftJaga>> response) {
                if(response.isSuccessful()){

                    final List<ListShiftJaga> allShiftJaga = response.body();
                    List<String> listSpinner = new ArrayList<>();
                    listSpinner.add(0, "ID Jaga");

                    for(int i = 0; i < allShiftJaga.size(); i++){
                        listSpinner.add(allShiftJaga.get(i).getId_jaga());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spJaga.setAdapter(adapter);

                    spJaga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(parent.getItemAtPosition(position).equals("ID Jaga")){
                                txtIdJaga.setText("Pilih ID Jaga");
                            }else{

                                for(int i = 0; i < allShiftJaga.size(); i++){
                                    if(position == i+1){
                                        txtIdJaga.setText("ID Jaga " + allShiftJaga.get(i).getId_jaga());
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
            public void onFailure(Call<List<ListShiftJaga>> call, Throwable t) {

            }
        });
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
                                txtnamaPasien.setText("Pilih ID Pasien");
                            }else{

                                for(int i = 0; i < allPasien.size(); i++){
                                    if(position == i+1){
                                        txtnamaPasien.setText("Pasien " + allPasien.get(i).getNama());
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
