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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rdsaleh.adpl_rs.javaClass.Perawat;

import java.util.Calendar;

public class DataPerawatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressDialog pd;

    Context mContext;

    private AlertDialog.Builder dialog;
    private LayoutInflater inflater;
    private View dialogView;

    private TextView txtDate;
    private Button btnDate;
    private RadioGroup RG;
    private EditText et_idPerawat, et_nama, et_alamat, et_noHP, et_pswd;
    private int mYear, mMonth, mDay;
    private String jk;

    private Perawat perawat = new Perawat();

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

                dialog = new AlertDialog.Builder(DataPerawatActivity.this);
                inflater = getLayoutInflater();
                dialogView = inflater.inflate(R.layout.activity_add_perawat, null);
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


                        DatePickerDialog datePickerDialog = new DatePickerDialog(DataPerawatActivity.this,
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

                et_idPerawat = dialogView.findViewById(R.id.et_idPerawat);
                et_pswd      = dialogView.findViewById(R.id.et_password);
                et_nama      = dialogView.findViewById(R.id.et_nama);
                et_alamat    = dialogView.findViewById(R.id.et_alamat);
                et_noHP      = dialogView.findViewById(R.id.et_noHP);

                dialog.setNegativeButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(TextUtils.isEmpty(et_idPerawat.getText())){
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
                        }else if(TextUtils.isEmpty(et_nama.getText())){
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

                            String id_perawat= et_idPerawat.getText().toString().trim();
                            String password  = et_pswd.getText().toString().trim();
                            String nama      = et_nama.getText().toString().trim();
                            String alamat    = et_alamat.getText().toString().trim();
                            String no_hp     = et_noHP.getText().toString().trim();
                            String date      = txtDate.getText().toString().trim();
                            int status       = 4;

                            perawat.addPerawat(id_perawat,password,nama,jk,date,alamat,no_hp,status,pd,mContext);

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
        setContentView(R.layout.activity_data_perawat);

        recyclerView = findViewById(R.id.recyclerPerawat);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mContext = this;
        pd = new ProgressDialog(mContext);

        perawat.showPerawat(recyclerView,mContext);
//
//        Bundle extras = getIntent().getExtras();
//        String id_admin = extras.getString("id_admin");
//        Toast.makeText(mContext, id_admin, Toast.LENGTH_LONG).show();

    }

}
