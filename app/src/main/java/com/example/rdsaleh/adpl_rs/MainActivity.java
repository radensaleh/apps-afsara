package com.example.rdsaleh.adpl_rs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.zip.Inflater;


public class MainActivity extends AppCompatActivity {

    private CardView cvPasien, cvSpesialis, cvDokter, cvPerawat, cvKelas, cvRuangan, cvRM, cvShiftjaga, cvRawat, cvPendaftaran, cvPembayaran, cvPeriksa;
    private AlertDialog.Builder dialog;
    private LayoutInflater inflater;
    Context mContext;

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(mContext)
                .setTitle("Alert")
                .setMessage("Anda Ingin Logout ? ")
                .setCancelable(false)
                .setIcon(R.drawable.ic_warning_logout_24dp)
                .setPositiveButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        }).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        String idLogin;
        Bundle extras = getIntent().getExtras();

        idLogin = extras.getString("id_admin");

        switch (id) {
            case R.id.action_info:
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(R.drawable.admin)
                        .setTitle("Info")
                        .setMessage("Hai " + idLogin + ", ini adalah Dashboard Admin. " +
                                "Selamat Beraktivitas")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                return true;
            case R.id.action_logout:
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Logout")
                        .setMessage("Anda Yakin Ingin Logout ? ")
                        .setCancelable(false)
                        .setIcon(R.drawable.exit)
                        .setPositiveButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);
                    }
                }).show();
                return true;
            default:
        }

                return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        cvPasien    = findViewById(R.id.cvPasien);
        cvSpesialis = findViewById(R.id.cvSpesialis);
        cvDokter    = findViewById(R.id.cvDokter);
        cvPerawat   = findViewById(R.id.cvPerawat);
        cvKelas     = findViewById(R.id.cvKelas);
        cvRuangan   = findViewById(R.id.cvRuangan);
        cvRM        = findViewById(R.id.cvRM);
        cvShiftjaga = findViewById(R.id.cvJaga);
        cvRawat     = findViewById(R.id.cvRawatInap);
        cvPendaftaran = findViewById(R.id.cvDaftar);
        cvPembayaran  = findViewById(R.id.cvPembayaran);
        cvPeriksa   = findViewById(R.id.cvPeriksa);

        cvPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_admin;
                Bundle extras = getIntent().getExtras();

                id_admin = extras.getString("id_admin");

                Intent i = new Intent(MainActivity.this, DataPasienActivity.class);
                i.putExtra("id_admin", id_admin);
                startActivity(i);
            }
        });

        cvSpesialis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_admin;
                Bundle extras = getIntent().getExtras();

                id_admin = extras.getString("id_admin");

                Intent i = new Intent(MainActivity.this, DataSpesialisActivity.class);
                i.putExtra("id_admin", id_admin);
                startActivity(i);
            }
        });

        cvDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_admin;
                Bundle extras = getIntent().getExtras();

                id_admin = extras.getString("id_admin");

                Intent i = new Intent(MainActivity.this, DataDokterActivity.class);
                i.putExtra("id_admin", id_admin);
                startActivity(i);
            }
        });

        cvPerawat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_admin;
                Bundle extras = getIntent().getExtras();

                id_admin = extras.getString("id_admin");

                Intent i = new Intent(MainActivity.this, DataPerawatActivity.class);
                i.putExtra("id_admin", id_admin);
                startActivity(i);
            }
        });

        cvKelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_admin;
                Bundle extras = getIntent().getExtras();

                id_admin = extras.getString("id_admin");

                Intent i = new Intent(MainActivity.this, DataKelasActivity.class);
                i.putExtra("id_admin", id_admin);
                startActivity(i);
            }
        });

        cvRuangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_admin;
                Bundle extras = getIntent().getExtras();

                id_admin = extras.getString("id_admin");

                Intent i = new Intent(MainActivity.this, DataRuanganActivity.class);
                i.putExtra("id_admin", id_admin);
                startActivity(i);
            }
        });

        cvRM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_admin;
                Bundle extras = getIntent().getExtras();

                id_admin = extras.getString("id_admin");

                Intent i = new Intent(MainActivity.this, DataRmActivity.class);
                i.putExtra("id_admin", id_admin);
                startActivity(i);
            }
        });

        cvShiftjaga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_admin;
                Bundle extras = getIntent().getExtras();

                id_admin = extras.getString("id_admin");

                Intent i = new Intent(MainActivity.this, DataShiftjagaActivity.class);
                i.putExtra("id_admin", id_admin);
                startActivity(i);
            }
        });

        cvRawat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_admin;
                Bundle extras = getIntent().getExtras();

                id_admin = extras.getString("id_admin");

                Intent i = new Intent(MainActivity.this, DataRawatActivity.class);
                i.putExtra("id_admin", id_admin);
                startActivity(i);
            }
        });

        cvPendaftaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_admin;
                Bundle extras = getIntent().getExtras();

                id_admin = extras.getString("id_admin");

                Intent i = new Intent(MainActivity.this, DataPendaftaranActivity.class);
                i.putExtra("id_admin", id_admin);
                startActivity(i);
            }
        });

        cvPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_admin;
                Bundle extras = getIntent().getExtras();

                id_admin = extras.getString("id_admin");

                Intent i = new Intent(MainActivity.this, DataPembayaranActivity.class);
                i.putExtra("id_admin", id_admin);
                startActivity(i);
            }
        });

        cvPeriksa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_admin;
                Bundle extras = getIntent().getExtras();

                id_admin = extras.getString("id_admin");

                Intent i = new Intent(MainActivity.this, DataPeriksaActivity.class);
                i.putExtra("id_admin", id_admin);
                startActivity(i);
            }
        });

    }
}
