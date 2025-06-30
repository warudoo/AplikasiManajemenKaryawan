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
// Ini adalah contoh kelas POJO (Plain Old Java Object)
public class Jabatan {
    private int id;
    private String namaJabatan;
    private double gajiPokok;

    // Konstruktor
    public Jabatan() {}

    public Jabatan(int id, String namaJabatan, double gajiPokok) {
        this.id = id;
        this.namaJabatan = namaJabatan;
        this.gajiPokok = gajiPokok;
    }

    // Getter dan Setter (Enkapsulasi)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNamaJabatan() { return namaJabatan; }
    public void setNamaJabatan(String namaJabatan) { this.namaJabatan = namaJabatan; }
    public double getGajiPokok() { return gajiPokok; }
    public void setGajiPokok(double gajiPokok) { this.gajiPokok = gajiPokok; }
}
