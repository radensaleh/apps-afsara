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

import com.example.rdsaleh.adpl_rs.api.ListPerawat;
import com.example.rdsaleh.adpl_rs.api.ListRuangan;
import com.example.rdsaleh.adpl_rs.javaClass.Perawat;
import com.example.rdsaleh.adpl_rs.javaClass.Ruangan;
import com.example.rdsaleh.adpl_rs.javaClass.ShiftJaga;
import com.example.rdsaleh.adpl_rs.retroServer.RetrofitClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataShiftjagaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    Context mContext;

    private AlertDialog.Builder dialog;
    private LayoutInflater inflater;
    private View dialogView;

    private EditText et_idJaga;
    private Spinner spRuangan, spPerawat;
    private RadioGroup RG;
    private TextView txtnamaruangan, txtnamaperawat, txtDate;
    private String shift = null;
    private Button btnDate;
    private int mYear, mMonth, mDay;

    private ProgressDialog pd;

    private ShiftJaga shiftJaga = new ShiftJaga();
    private Ruangan ruangan     = new Ruangan();
    private Perawat perawat     = new Perawat();

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

                dialog = new AlertDialog.Builder(DataShiftjagaActivity.this);
                inflater = getLayoutInflater();
                dialogView = inflater.inflate(R.layout.activity_shift_jaga, null);
                dialog.setView(dialogView);
                dialog.setCancelable(false);
                dialog.setTitle("Add Data");

                et_idJaga = dialogView.findViewById(R.id.et_idJaga);
                spPerawat = dialogView.findViewById(R.id.spinnerPerawat);
                spRuangan = dialogView.findViewById(R.id.spinnerRuangan);
                RG        = dialogView.findViewById(R.id.RG);
                txtnamaperawat = dialogView.findViewById(R.id.txtnamaPerawat);
                txtnamaruangan = dialogView.findViewById(R.id.txtnamaRuangan);

                btnDate = dialogView.findViewById(R.id.btnDate);
                btnDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get Current Date
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);


                        DatePickerDialog datePickerDialog = new DatePickerDialog(DataShiftjagaActivity.this,
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

                getPerawat();
                getRuangan();

                RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if(checkedId == R.id.rb_pagi){
                            RadioButton rb = dialogView.findViewById(checkedId);
                            shift = (String) rb.getText();

                        }else if(checkedId == R.id.rb_siang){
                            RadioButton rb = dialogView.findViewById(checkedId);
                            shift = (String) rb.getText();

                        }else if(checkedId == R.id.rb_malam){
                            RadioButton rb = dialogView.findViewById(checkedId);
                            shift = (String) rb.getText();

                        }
                    }
                });

                dialog.setNegativeButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(TextUtils.isEmpty(et_idJaga.getText())){
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
                        }else if(txtnamaruangan.getText() == "Pilih ID Ruangan"){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("ID Ruangan Kosong")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }else if(txtnamaperawat.getText() == "Pilih ID Perawat"){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("ID Perawat Kosong")
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
                                    .setMessage("Tanggal Jaga Kosong")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        } else if(shift == null){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("Shift Kosong")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }else{
                            String id_jaga    = et_idJaga.getText().toString().trim();
                            String id_perawat = spPerawat.getSelectedItem().toString().trim();
                            String id_ruangan = spRuangan.getSelectedItem().toString().trim();
                            String date      = txtDate.getText().toString().trim();

                            shiftJaga.PrevRuangan(ruangan);
                            shiftJaga.PrevPerawat(perawat);
                            shiftJaga.addShiftJaga(id_jaga,id_ruangan,id_perawat,date,shift, pd, mContext);

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
        setContentView(R.layout.activity_data_shiftjaga);

        recyclerView = findViewById(R.id.recyclerShiftJaga);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mContext = this;
        pd = new ProgressDialog(mContext);

        shiftJaga.showJaga(recyclerView, mContext);

//        Bundle extras = getIntent().getExtras();
//        String id_admin = extras.getString("id_admin");
//        Toast.makeText(mContext, id_admin, Toast.LENGTH_LONG).show();

    }

    public void getPerawat(){
        Call<List<ListPerawat>> call = RetrofitClient
                .getInstance()
                .baseAPI()
                .getAllPerawat();

        call.enqueue(new Callback<List<ListPerawat>>() {
            @Override
            public void onResponse(Call<List<ListPerawat>> call, Response<List<ListPerawat>> response) {
                if(response.isSuccessful()){

                    final List<ListPerawat> allPerawat = response.body();
                    List<String> listSpinner = new ArrayList<>();
                    listSpinner.add(0, "ID Perawat");

                    for(int i = 0; i < allPerawat.size(); i++){
                        listSpinner.add(allPerawat.get(i).getId_perawat());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spPerawat.setAdapter(adapter);

                    spPerawat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(parent.getItemAtPosition(position).equals("ID Perawat")){
                                txtnamaperawat.setText("Pilih ID Perawat");
                            }else{

                                for(int i = 0; i < allPerawat.size(); i++){
                                    if(position == i+1){
                                        txtnamaperawat.setText("Perawat " + allPerawat.get(i).getNama());
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
            public void onFailure(Call<List<ListPerawat>> call, Throwable t) {

            }
        });

    }

    public void getRuangan(){
        Call<List<ListRuangan>> call = RetrofitClient
                .getInstance()
                .baseAPI()
                .getAllRuangan();

        call.enqueue(new Callback<List<ListRuangan>>() {
            @Override
            public void onResponse(Call<List<ListRuangan>> call, Response<List<ListRuangan>> response) {
                if(response.isSuccessful()){

                    final List<ListRuangan> allRuangan = response.body();
                    List<String> listSpinner = new ArrayList<>();
                    listSpinner.add(0, "ID Ruangan");

                    for(int i = 0; i < allRuangan.size(); i++){
                        listSpinner.add(allRuangan.get(i).getId_ruangan());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spRuangan.setAdapter(adapter);

                    spRuangan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(parent.getItemAtPosition(position).equals("ID Ruangan")){
                                txtnamaruangan.setText("Pilih ID Ruangan");
                            }else{

                                for(int i = 0; i < allRuangan.size(); i++){
                                    if(position == i+1){
                                        txtnamaruangan.setText("Ruangan " + allRuangan.get(i).getNama_ruangan());
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
            public void onFailure(Call<List<ListRuangan>> call, Throwable t) {

            }
        });
    }

}
