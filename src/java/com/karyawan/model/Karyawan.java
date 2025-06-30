/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.karyawan.model;

/**
 *
 * @author Warudo
 */
// Konsep OOP: Karyawan "HAS-A" (memiliki sebuah) Jabatan -> Composition
public class Karyawan {
    private int id;
    private String nik;
    private String namaLengkap;
    private String email;
    private String noTelepon;
    private String alamat;
    private Jabatan jabatan; // Objek Jabatan, bukan hanya ID

    // Konstruktor
    public Karyawan() {
        this.jabatan = new Jabatan(); // Inisialisasi untuk menghindari NullPointerException
    }

    // Getter dan Setter (Enkapsulasi)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; }
    public String getNamaLengkap() { return namaLengkap; }
    public void setNamaLengkap(String namaLengkap) { this.namaLengkap = namaLengkap; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNoTelepon() { return noTelepon; }
    public void setNoTelepon(String noTelepon) { this.noTelepon = noTelepon; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public Jabatan getJabatan() { return jabatan; }
    public void setJabatan(Jabatan jabatan) { this.jabatan = jabatan; }
}