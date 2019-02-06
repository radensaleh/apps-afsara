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
import android.widget.Toast;

import com.example.rdsaleh.adpl_rs.api.ListSpesialis;
import com.example.rdsaleh.adpl_rs.javaClass.Dokter;
import com.example.rdsaleh.adpl_rs.javaClass.Spesialis;
import com.example.rdsaleh.adpl_rs.retroServer.RetrofitClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataDokterActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressDialog pd;

    Context mContext;

    private AlertDialog.Builder dialog;
    private LayoutInflater inflater;
    private View dialogView;

    private TextView txtDate, txtnamaSpesialis;
    private Button btnDate;
    private RadioGroup RG;
    private EditText et_idDokter, et_nama, et_alamat, et_noHP, et_pswd;
    private int mYear, mMonth, mDay;
    private String jk;
    private Spinner spSpesialis;

    private Dokter dokter = new Dokter();
    private Spesialis spesialis = new Spesialis();

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

                dialog = new AlertDialog.Builder(DataDokterActivity.this);
                inflater = getLayoutInflater();
                dialogView = inflater.inflate(R.layout.activity_add_dokter, null);
                dialog.setView(dialogView);
                dialog.setCancelable(false);
                dialog.setTitle("Add Data");

                btnDate = dialogView.findViewById(R.id.btnDate);
                btnDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get Current Date
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);


                        DatePickerDialog datePickerDialog = new DatePickerDialog(DataDokterActivity.this,
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

                txtDate = dialogView.findViewById(R.id.tvDate);
                txtDate.setText("date");

                RG = dialogView.findViewById(R.id.RG);
                RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if(checkedId == R.id.rb_l){
                            RadioButton rb = dialogView.findViewById(checkedId);
                            jk = (String) rb.getText();

                        }else if(checkedId == R.id.rb_p){
                            RadioButton rb = dialogView.findViewById(checkedId);
                            jk = (String) rb.getText();

                        }
                    }
                });

                et_idDokter = dialogView.findViewById(R.id.et_idDokter);
                et_pswd     = dialogView.findViewById(R.id.et_password);
                et_nama     = dialogView.findViewById(R.id.et_nama);
                et_alamat   = dialogView.findViewById(R.id.et_alamat);
                et_noHP     = dialogView.findViewById(R.id.et_noHP);
                spSpesialis = dialogView.findViewById(R.id.spinnerSpesialis);
                txtnamaSpesialis = dialogView.findViewById(R.id.txtnamaSpesialis);

                getSpesialis();

                dialog.setNegativeButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(TextUtils.isEmpty(et_idDokter.getText())){
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
                        }else if(TextUtils.isEmpty(et_pswd.getText())){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("Password Kosong")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }else if(txtnamaSpesialis.getText() == "Pilih ID Spesialis"){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("ID Spesialis Kosong")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        } else if(TextUtils.isEmpty(et_nama.getText())){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("Nama Kosong")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }else if(jk == null){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("Jenis Kelamin Kosong")
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
                                    .setMessage("Tanggal Lahir Kosong")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }else if(TextUtils.isEmpty(et_alamat.getText())){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("Alamat Kosong")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }else if(TextUtils.isEmpty(et_noHP.getText())){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("Nomer HP Kosong")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }
                        else{

                            String id_dokter = et_idDokter.getText().toString().trim();
                            String password  = et_pswd.getText().toString().trim();
                            String id_spesialis = spSpesialis.getSelectedItem().toString().trim();
                            String nama      = et_nama.getText().toString().trim();
                            String alamat    = et_alamat.getText().toString().trim();
                            String no_hp     = et_noHP.getText().toString().trim();
                            String date      = txtDate.getText().toString().trim();
                            int status       = 3;

                            dokter.PrevSpesialis(spesialis);
                            dokter.addDokter(id_dokter,password,id_spesialis,nama,jk,date,alamat,no_hp,status,pd,mContext);

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
        setContentView(R.layout.activity_data_dokter);

        recyclerView = findViewById(R.id.recyclerDokter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mContext = this;
        pd = new ProgressDialog(mContext);

        dokter.showDokter(recyclerView, mContext);

//        Bundle extras = getIntent().getExtras();
//        String id_admin = extras.getString("id_admin");
//        Toast.makeText(mContext, id_admin, Toast.LENGTH_LONG).show();

    }

    public void getSpesialis(){
        Call<List<ListSpesialis>> call = RetrofitClient
                .getInstance().baseAPI()
                .getSpesialis();

        call.enqueue(new Callback<List<ListSpesialis>>() {
            @Override
            public void onResponse(Call<List<ListSpesialis>> call, Response<List<ListSpesialis>> response) {
                if(response.isSuccessful()){

                    final List<ListSpesialis> allSpesialis = response.body();
                    List<String> listSpinner = new ArrayList<>();
                    listSpinner.add(0, "ID Spesialis");

                    for(int i = 0; i < allSpesialis.size(); i++){
                        listSpinner.add(allSpesialis.get(i).getId_spesialis());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spSpesialis.setAdapter(adapter);

                    spSpesialis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(parent.getItemAtPosition(position).equals("ID Spesialis")){
                                txtnamaSpesialis.setText("Pilih ID Spesialis");
                            }else{

                                for(int i = 0; i < allSpesialis.size(); i++){
                                    if(position == i+1){
                                        txtnamaSpesialis.setText("Spesialis " + allSpesialis.get(i).getNama_spesialis());
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
            public void onFailure(Call<List<ListSpesialis>> call, Throwable t) {

            }
        });
    }
}
