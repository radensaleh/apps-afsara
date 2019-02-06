package com.example.rdsaleh.adpl_rs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rdsaleh.adpl_rs.javaClass.Login;

public class IndexActivity extends AppCompatActivity {

    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        pd = new ProgressDialog(this);

        Runnable pr = new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(IndexActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        };

        Handler handler = new Handler();
        handler.postDelayed(pr, 3000);

    }
}
