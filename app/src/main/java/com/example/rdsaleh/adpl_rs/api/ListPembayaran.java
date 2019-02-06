package com.example.rdsaleh.adpl_rs.api;

public class ListPembayaran {

    private String id_pembayaran, id_admin, id_pasien, tgl_pembayaran;

    private int jml_biaya_daftar, jml_biaya_inap;

    public String getId_pembayaran() {
        return id_pembayaran;
    }

    public String getId_admin() {
        return id_admin;
    }

    public String getId_pasien() {
        return id_pasien;
    }

    public String getTgl_pembayaran() {
        return tgl_pembayaran;
    }

    public int getJml_biaya_daftar() {
        return jml_biaya_daftar;
    }

    public int getJml_biaya_inap() {
        return jml_biaya_inap;
    }
}
