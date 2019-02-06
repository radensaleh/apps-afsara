package com.example.rdsaleh.adpl_rs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.rdsaleh.adpl_rs.api.Response;
import com.example.rdsaleh.adpl_rs.javaClass.Login;
import com.example.rdsaleh.adpl_rs.retroServer.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {

    private EditText et_id, et_password;
    private ProgressDialog pd;
    private Button btnAdd;
    private Spinner sp;
    private int status = 0;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        pd = new ProgressDialog(mContext);

        et_id       = findViewById(R.id.et_id);
        et_password = findViewById(R.id.et_password);
        btnAdd      = findViewById(R.id.btn_Login);
        sp          = findViewById(R.id.spinnerStatus);

        List<String> listSpinner = new ArrayList<>();
        listSpinner.add(0, "-- Pilih --");
        listSpinner.add(1, "Admin");
        listSpinner.add(2, "Pasien");
        listSpinner.add(3, "Dokter");
        listSpinner.add(4, "Perawat");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, listSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    status = 0;
                }else if(position == 1){
                    status = 1;
                }else if(position == 2){
                    status = 2;
                }else if(position == 3){
                    status = 3;
                }else if(position == 4){
                    status = 4;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_id.getText())){
                    et_id.setError("ID Kosong");
                }else if(TextUtils.isEmpty(et_password.getText())){
                    et_password.setError("Password Kosong");
                }else if(status == 0){
                    new AlertDialog.Builder(mContext)
                            .setIcon(R.drawable.failed)
                            .setTitle("Error")
                            .setMessage("Harap Memilih Status")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                } else{
                    login();
                }
            }
        });
    }

    public void login(){
        pd.setIcon(R.drawable.login);
        pd.setTitle("Login");
        pd.setMessage("Please Waiting. . .");
        pd.setCancelable(false);
        pd.show();

        Runnable pr = new Runnable() {
            @Override
            public void run() {
                final String id = et_id.getText().toString().trim();
                String pw = et_password.getText().toString();

                Login login = new Login();
                login.doLogin(id,pw,status);

                Call<Response> call = RetrofitClient
                        .getInstance()
                        .baseAPI()
                        .login(login.getIdLogin(), login.getPasswordLogin(),login.getStatusLogin());

                call.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        String error   = response.body().getErrorRes();
                        String message = response.body().getMessageRes();

                        if(error.equals("0")){
                            pd.dismiss();
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.failed)
                                    .setTitle("Failed")
                                    .setMessage(message)
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }else if(error.equals("1")){
                            pd.dismiss();
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.success)
                                    .setTitle("Success")
                                    .setMessage(message)
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent i = new Intent(mContext, MainActivity.class);
                                            i.putExtra("id_admin", id);
                                            startActivity(i);
                                        }
                                    }).show();
                        }else if(error.equals("2")){
                            pd.dismiss();
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.success)
                                    .setTitle("Success")
                                    .setMessage(message)
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent i = new Intent(mContext, MainPasienActivity.class);
                                            i.putExtra("id_pasien", id);
                                            startActivity(i);
                                        }
                                    }).show();
                        }else if(error.equals("3")){
                            pd.dismiss();
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.success)
                                    .setTitle("Success")
                                    .setMessage(message)
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent i = new Intent(mContext, MainDokterActivity.class);
                                            i.putExtra("id_dokter", id);
                                            startActivity(i);
                                        }
                                    }).show();
                        }else if(error.equals("4")){
                            pd.dismiss();
                            new AlertDialog.Builder(mContext)
                                    .setIcon(R.drawable.success)
                                    .setTitle("Success")
                                    .setMessage(message)
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent i = new Intent(mContext, MainPerawatActivity.class);
                                            i.putExtra("id_perawat", id);
                                            startActivity(i);
                                        }
                                    }).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        pd.dismiss();
                        Log.e("ERRRORRRR : " , t.getMessage());
                    }
                });

            }
        };

        Handler handler = new Handler();
        handler.postDelayed(pr, 3000);


    }
}
