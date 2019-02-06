package com.example.rdsaleh.adpl_rs;

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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rdsaleh.adpl_rs.api.ListKelas;
import com.example.rdsaleh.adpl_rs.javaClass.Kelas;
import com.example.rdsaleh.adpl_rs.javaClass.Ruangan;
import com.example.rdsaleh.adpl_rs.retroServer.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRuanganActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressDialog pd;

    Context mContext;

    private AlertDialog.Builder dialog;
    private LayoutInflater inflater;
    private View dialogView;

    EditText et_idRuangan;
    EditText et_namaRuangan;
    TextView txtKelas;
    RadioGroup RG;
    Spinner sp;
    String status = null;

    private Ruangan ruangan = new Ruangan();
    private Kelas kelas = new Kelas();

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

                dialog = new AlertDialog.Builder(DataRuanganActivity.this);
                inflater = getLayoutInflater();
                dialogView = inflater.inflate(R.layout.activity_add_ruangan, null);
                dialog.setView(dialogView);
                dialog.setCancelable(false);
                dialog.setTitle("Add Data");

                et_idRuangan   = dialogView.findViewById(R.id.et_idRuangan);
                et_namaRuangan = dialogView.findViewById(R.id.et_namaRuangan);

                RG = dialogView.findViewById(R.id.RG);
                sp = dialogView.findViewById(R.id.spinnerKelas);

                txtKelas = dialogView.findViewById(R.id.txtidkelas);

                getKelas();

                RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if(checkedId == R.id.rb_full){
                            RadioButton rb = dialogView.findViewById(checkedId);
                            status = (String) rb.getText();

                        }else if(checkedId == R.id.rb_empty){
                            RadioButton rb = dialogView.findViewById(checkedId);
                            status = (String) rb.getText();

                        }
                    }
                });

                dialog.setNegativeButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(TextUtils.isEmpty(et_idRuangan.getText())){
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
                        }else if(TextUtils.isEmpty(et_namaRuangan.getText())){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("Nama Ruangan Kosong")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }else if(txtKelas.getText() == "Pilih ID Kelas"){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("ID Kelas Kosong")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }else if(status == null){
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage("Status Ruangan Kosong")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }else{
                            String id_ruangan   = et_idRuangan.getText().toString().trim();
                            String nama_ruangan = et_namaRuangan.getText().toString().trim();
                            String id_kelas     = sp.getSelectedItem().toString().trim();

                            ruangan.prevKelas(kelas);
                            ruangan.addRuangan(id_ruangan,nama_ruangan,id_kelas,status,pd,mContext);

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
        setContentView(R.layout.activity_data_ruangan);

        recyclerView = findViewById(R.id.recyclerRuangan);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mContext = this;
        pd = new ProgressDialog(mContext);

        ruangan.showRuangan(recyclerView,mContext);

//        Bundle extras = getIntent().getExtras();
//        String id_admin = extras.getString("id_admin");
//        Toast.makeText(mContext, id_admin, Toast.LENGTH_LONG).show();

    }


    public void getKelas(){
        Call<List<ListKelas>> listKelas = RetrofitClient
                .getInstance()
                .baseAPI()
                .getAllKelas();

        listKelas.enqueue(new Callback<List<ListKelas>>() {
            @Override
            public void onResponse(Call<List<ListKelas>> call, Response<List<ListKelas>> response) {
                if(response.isSuccessful()){

                    final List<ListKelas> allKelas = response.body();
                    List<String> listKelas = new ArrayList<>();
                    listKelas.add(0, "ID Kelas");

                    for(int i = 0; i < allKelas.size(); i++){
                        listKelas.add(allKelas.get(i).getId_kelas());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, listKelas);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    sp.setAdapter(adapter);

                    sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(parent.getItemAtPosition(position).equals("ID Kelas")){
                                txtKelas.setText("Pilih ID Kelas");
                            }else{

                                for(int i = 0; i < allKelas.size(); i++){
                                    if(position == i+1){
                                        txtKelas.setText("Kelas " + allKelas.get(i).getId_kelas());
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
            public void onFailure(Call<List<ListKelas>> call, Throwable t) {

            }
        });

    }

}
