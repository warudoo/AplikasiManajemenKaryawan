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
import com.karyawan.util.Koneksi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JabatanDAO {

    public void tambahJabatan(Jabatan jabatan) throws SQLException {
        String sql = "INSERT INTO jabatan (nama_jabatan, gaji_pokok) VALUES (?, ?)";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jabatan.getNamaJabatan());
            stmt.setDouble(2, jabatan.getGajiPokok());
            stmt.executeUpdate();
        }
    }

    public void updateJabatan(Jabatan jabatan) throws SQLException {
        String sql = "UPDATE jabatan SET nama_jabatan = ?, gaji_pokok = ? WHERE id_jabatan = ?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jabatan.getNamaJabatan());
            stmt.setDouble(2, jabatan.getGajiPokok());
            stmt.setInt(3, jabatan.getId());
            stmt.executeUpdate();
        }
    }

    public void hapusJabatan(int id) throws SQLException {
        String sql = "DELETE FROM jabatan WHERE id_jabatan = ?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    public Jabatan getJabatanById(int id) throws SQLException {
        String sql = "SELECT * FROM jabatan WHERE id_jabatan = ?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Jabatan(
                        rs.getInt("id_jabatan"),
                        rs.getString("nama_jabatan"),
                        rs.getDouble("gaji_pokok")
                    );
                }
            }
        }
        return null; // Mengembalikan null jika tidak ditemukan
    }

    public List<Jabatan> semuaJabatan() throws SQLException {
        List<Jabatan> daftarJabatan = new ArrayList<>();
        String sql = "SELECT * FROM jabatan ORDER BY nama_jabatan";
        try (Connection conn = Koneksi.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Jabatan j = new Jabatan(
                    rs.getInt("id_jabatan"),
                    rs.getString("nama_jabatan"),
                    rs.getDouble("gaji_pokok")
                );
                daftarJabatan.add(j);
            }
        }
        return daftarJabatan;
    }
}
