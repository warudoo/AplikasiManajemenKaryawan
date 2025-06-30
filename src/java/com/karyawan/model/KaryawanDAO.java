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

public class KaryawanDAO {

    public void tambahKaryawan(Karyawan karyawan) throws SQLException {
        String sql = "INSERT INTO karyawan (nik, nama_lengkap, email, no_telepon, alamat, id_jabatan) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, karyawan.getNik());
            stmt.setString(2, karyawan.getNamaLengkap());
            stmt.setString(3, karyawan.getEmail());
            stmt.setString(4, karyawan.getNoTelepon());
            stmt.setString(5, karyawan.getAlamat());
            stmt.setInt(6, karyawan.getJabatan().getId());
            stmt.executeUpdate();
        }
    }

    public void updateKaryawan(Karyawan karyawan) throws SQLException {
        String sql = "UPDATE karyawan SET nik=?, nama_lengkap=?, email=?, no_telepon=?, alamat=?, id_jabatan=? WHERE id_karyawan=?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, karyawan.getNik());
            stmt.setString(2, karyawan.getNamaLengkap());
            stmt.setString(3, karyawan.getEmail());
            stmt.setString(4, karyawan.getNoTelepon());
            stmt.setString(5, karyawan.getAlamat());
            stmt.setInt(6, karyawan.getJabatan().getId());
            stmt.setInt(7, karyawan.getId());
            stmt.executeUpdate();
        }
    }

    public void hapusKaryawan(int id) throws SQLException {
        String sql = "DELETE FROM karyawan WHERE id_karyawan = ?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    private Karyawan mapResultSetToKaryawan(ResultSet rs) throws SQLException {
        Karyawan k = new Karyawan();
        k.setId(rs.getInt("id_karyawan"));
        k.setNik(rs.getString("nik"));
        k.setNamaLengkap(rs.getString("nama_lengkap"));
        k.setEmail(rs.getString("email"));
        k.setNoTelepon(rs.getString("no_telepon"));
        k.setAlamat(rs.getString("alamat"));

        Jabatan j = new Jabatan(
            rs.getInt("id_jabatan"),
            rs.getString("nama_jabatan"),
            rs.getDouble("gaji_pokok")
        );
        k.setJabatan(j);
        return k;
    }
    
    public Karyawan getKaryawanById(int id) throws SQLException {
        String sql = "SELECT k.*, j.nama_jabatan, j.gaji_pokok FROM karyawan k LEFT JOIN jabatan j ON k.id_jabatan = j.id_jabatan WHERE k.id_karyawan = ?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToKaryawan(rs);
                }
            }
        }
        return null;
    }
    
    public List<Karyawan> cariKaryawan(String keyword) throws SQLException {
        List<Karyawan> daftarKaryawan = new ArrayList<>();
        String sql = "SELECT k.*, j.nama_jabatan, j.gaji_pokok FROM karyawan k LEFT JOIN jabatan j ON k.id_jabatan = j.id_jabatan WHERE k.nik LIKE ? OR k.nama_lengkap LIKE ? ORDER BY k.nama_lengkap";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String searchKeyword = "%" + keyword + "%";
            stmt.setString(1, searchKeyword);
            stmt.setString(2, searchKeyword);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    daftarKaryawan.add(mapResultSetToKaryawan(rs));
                }
            }
        }
        return daftarKaryawan;
    }

    public List<Karyawan> semuaKaryawan() throws SQLException {
        List<Karyawan> daftarKaryawan = new ArrayList<>();
        String sql = "SELECT k.*, j.nama_jabatan, j.gaji_pokok FROM karyawan k LEFT JOIN jabatan j ON k.id_jabatan = j.id_jabatan ORDER BY k.nama_lengkap";
        try (Connection conn = Koneksi.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                daftarKaryawan.add(mapResultSetToKaryawan(rs));
            }
        }
        return daftarKaryawan;
    }
}
