package com.example.rdsaleh.adpl_rs.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseAPI {

    @FormUrlEncoded
    @POST("addPasien")
    Call<Response> addPasien(
            @Field("id_pasien") String id_pasien,
            @Field("password") String password,
            @Field("nama") String nama,
            @Field("jk") String jk,
            @Field("tgl_lahir") String tgl_lahir,
            @Field("alamat") String alamat,
            @Field("no_hp") String no_hp,
            @Field("status") int status
    );

    @FormUrlEncoded
    @POST("addSpesialis")
    Call<Response> addSpesialis(
            @Field("id_spesialis") String id_spesialis,
            @Field("nama_spesialis") String nama_spesialis,
            @Field("biaya") int biaya
    );

    @GET("getSpesialis")
    Call<List<ListSpesialis>> getSpesialis();

    @FormUrlEncoded
    @POST("addDokter")
    Call<Response> addDokter(
            @Field("id_dokter") String id_dokter,
            @Field("password") String password,
            @Field("id_spesialis") String id_spesialis,
            @Field("nama") String nama,
            @Field("jk") String jk,
            @Field("tgl_lahir") String tgl_lahir,
            @Field("alamat") String alamat,
            @Field("no_hp") String no_hp,
            @Field("status") int status
    );

    @FormUrlEncoded
    @POST("addPerawat")
    Call<Response> addPerawat(
            @Field("id_perawat") String id_perawat,
            @Field("password") String password,
            @Field("nama") String nama,
            @Field("jk") String jk,
            @Field("tgl_lahir") String tgl_lahir,
            @Field("alamat") String alamat,
            @Field("no_hp") String no_hp,
            @Field("status") int status
    );

    @FormUrlEncoded
    @POST("addPendaftaran")
    Call<Response> addPendaftaran(
            @Field("id_pendaftaran") String id_pendaftaran,
            @Field("id_pasien") String id_pasien,
            @Field("id_admin") String id_admin,
            @Field("tgl_daftar") String tgl_daftar

    );

    @FormUrlEncoded
    @POST("addPeriksa")
    Call<Response> addPeriksa(
            @Field("id_periksa") String id_periksa,
            @Field("id_rm") String id_rm,
            @Field("id_dokter") String id_dokter,
            @Field("tgl_periksa") String tgl_periksa,
            @Field("status_periksa") String status_periksa
    );

    @GET("getPeriksa")
    Call<List<ListPeriksa>> getAllPeriksa();

    @GET("getIdPeriksa/{id_rm}")
    Call<List<ListPeriksa>> getPeriksa(
            @Path("id_rm") String id_rm
    );

    @FormUrlEncoded
    @POST("addPembayaran")
    Call<Response> addPembayaran(
            @Field("id_pembayaran") String id_pembayaran,
            @Field("id_admin") String id_admin,
            @Field("id_pasien") String id_pasien,
            @Field("tgl_pembayaran") String tgl_pembayaran,
            @Field("jml_biaya_daftar") int jml_biaya_daftar,
            @Field("jml_biaya_inap") int jml_biaya_inap

    );

    @FormUrlEncoded
    @POST("addKelas")
    Call<Response> addKelas(
            @Field("id_kelas") String id_kelas,
            @Field("biaya") int biaya
    );

    @FormUrlEncoded
    @POST("addRuangan")
    Call<Response> addRuangan(
            @Field("id_ruangan") String id_ruangan,
            @Field("nama_ruangan") String nama_ruangan,
            @Field("id_kelas") String id_kelas,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("updateRuangan")
    Call<Response> updateRuangan(
            @Field("id_ruangan") String id_ruangan,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("updatePeriksaDokter")
    Call<Response> updatePeriksaDokter(
            @Field("id_periksa") String id_periksa,
            @Field("id_rm") String id_rm,
            @Field("id_dokter") String id_dokter,
            @Field("diagnosa") String diagnosa,
            @Field("tgl_periksa") String tgl_periksa,
            @Field("status_rawat") String status_rawat,
            @Field("status_periksa") String status_periksa
    );

    @GET("getKelas")
    Call<List<ListKelas>> getAllKelas();

    @GET("getPasien")
    Call<List<ListPasien>> getAllPasien();

    @GET("getIdpasien/{id_pasien}")
    Call<ListPasien> getPasien(
            @Path("id_pasien") String id_pasien
    );

    @GET("getDokter")
    Call<List<ListDokter>> getAllDokter();

    @GET("getIdDokter/{id_dokter}")
    Call<ListDokter> getDokter(
            @Path("id_dokter") String id_dokter
    );

    @GET("getPeriksaDokter/{id_dokter}")
    Call<List<ListPeriksa>> getPeriksaDokter(
            @Path("id_dokter") String id_dokter
    );

    @GET("getRawatInap")
    Call<List<ListRawatInap>> getAllRawat();

    @GET("getRM")
    Call<List<ListRM>> getAllRM();

    @GET("getPendaftaran")
    Call<List<ListPendaftaran>> getAllPendaftaran();

    @GET("getIdPendaftaran/{id_pasien}")
    Call<List<ListPendaftaran>> getPendaftaran(
            @Path("id_pasien") String id_pasien
    );

    @GET("getPembayaran")
    Call<List<ListPembayaran>> getAllPembayaran();

    @GET("getIdPembayaran/{id_pasien}")
    Call<List<ListPembayaran>> getPembayaran(
            @Path("id_pasien") String id_pasien
    );

    @FormUrlEncoded
    @POST("addRM")
    Call<Response> addRM(
            @Field("id_rm") String id_rm,
            @Field("id_pasien") String id_pasien
    );

    @GET("getPerawat")
    Call<List<ListPerawat>> getAllPerawat();

    @GET("getIdPerawat/{id_perawat}")
    Call<ListPerawat> getPerawat(
            @Path("id_perawat") String id_perawat
     );

    @GET("getRuangan")
    Call<List<ListRuangan>> getAllRuangan();

    @FormUrlEncoded
    @POST("addShiftJaga")
    Call<Response> addShiftJaga(
            @Field("id_jaga") String id_jaga,
            @Field("id_ruangan") String id_ruangan,
            @Field("id_perawat") String id_perawat,
            @Field("tgl_jaga") String tgl_jaga,
            @Field("shift") String shift
    );

    @FormUrlEncoded
    @POST("loginMobile")
    Call<Response> login(
            @Field("id") String id,
            @Field("password") String password,
            @Field("status") int status

    );

    @GET("getShiftJaga")
    Call<List<ListShiftJaga>> getAllShift();

    @GET("getJadwalJaga/{id_perawat}")
    Call<List<ListShiftJaga>> getJadwalJaga(
            @Path("id_perawat") String id_perawat
    );

    @FormUrlEncoded
    @POST("rawatInap")
    Call<Response> addRawat(
            @Field("id_rawat") String id_rawat,
            @Field("id_jaga") String id_jaga,
            @Field("id_pasien") String id_pasien,
            @Field("id_admin") String id_admin,
            @Field("tgl_masuk") String tgl_masuk
    );

    @FormUrlEncoded
    @POST("deleteKelas")
    Call<Response> deleteKelas(
            @Field("id_kelas") String id_kelas
    );

    @FormUrlEncoded
    @POST("deleteSpesialis")
    Call<Response> deleteSpesialis(
            @Field("id_spesialis") String id_spesialis
    );

    @FormUrlEncoded
    @POST("deleteRuangan")
    Call<Response> deleteRuangan(
            @Field("id_ruangan") String id_ruangan
    );

    @FormUrlEncoded
    @POST("deletePasien")
    Call<Response> deletePasien(
            @Field("id_pasien") String id_pasien
    );

    @FormUrlEncoded
    @POST("deletePerawat")
    Call<Response> deletePerawat(
            @Field("id_perawat") String id_perawat
    );

    @FormUrlEncoded
    @POST("deleteDokter")
    Call<Response> deleteDokter(
            @Field("id_dokter") String id_dokter
    );

    @FormUrlEncoded
    @POST("deleteShifJaga")
    Call<Response> deleteShiftJaga(
            @Field("id_jaga") String id_jaga,
            @Field("id_ruangan") String id_ruangan
    );

    @FormUrlEncoded
    @POST("deleteRawatInap")
    Call<Response> deleteRawatInap(
            @Field("id_rawat") String id_rawat
    );

    @FormUrlEncoded
    @POST("deletePendaftaran")
    Call<Response> deletePendaftaran(
            @Field("id_pendaftaran") String id_pendaftaran
    );

    @FormUrlEncoded
    @POST("deletePembayaran")
    Call<Response> deletePembayaran(
            @Field("id_pembayaran") String id_pembayaran
    );

    @FormUrlEncoded
    @POST("deletePeriksa")
    Call<Response> deletePeriksa(
            @Field("id_periksa") String id_periksa
    );


}
