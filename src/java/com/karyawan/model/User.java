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

public class User {
    private int idUser;
    private String username;
    private String password;
    private String namaLengkap;

    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public String getNamaLengkap() { return namaLengkap; }
    public void setNamaLengkap(String namaLengkap) { this.namaLengkap = namaLengkap; }

    public User login() throws SQLException {
        String sql = "SELECT id_user, username, nama_lengkap FROM users WHERE username = ? AND password = ?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, this.username);
            stmt.setString(2, Enkripsi.sha256(this.password));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User loggedInUser = new User();
                    loggedInUser.setIdUser(rs.getInt("id_user"));
                    loggedInUser.setUsername(rs.getString("username"));
                    loggedInUser.setNamaLengkap(rs.getString("nama_lengkap"));
                    return loggedInUser;
                }
            }
        }
        return null;
    }
    
    // Metode CRUD lainnya
    public void simpan() throws SQLException {
        String sql = "INSERT INTO users (username, password, nama_lengkap) VALUES (?, ?, ?)";
        try (Connection conn = Koneksi.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, this.username);
            stmt.setString(2, Enkripsi.sha256(this.password));
            stmt.setString(3, this.namaLengkap);
            stmt.executeUpdate();
        }
    }

    public void update() throws SQLException {
        String sql = (this.password != null && !this.password.isEmpty())
            ? "UPDATE users SET username=?, nama_lengkap=?, password=? WHERE id_user=?"
            : "UPDATE users SET username=?, nama_lengkap=? WHERE id_user=?";
        try (Connection conn = Koneksi.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, this.username);
            stmt.setString(2, this.namaLengkap);
            if (this.password != null && !this.password.isEmpty()) {
                stmt.setString(3, Enkripsi.sha256(this.password));
                stmt.setInt(4, this.idUser);
            } else {
                stmt.setInt(3, this.idUser);
            }
            stmt.executeUpdate();
        }
    }

    public static void hapus(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id_user = ?";
        try (Connection conn = Koneksi.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public static User getById(int id) throws SQLException {
        String sql = "SELECT id_user, username, nama_lengkap FROM users WHERE id_user = ?";
        try (Connection conn = Koneksi.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setIdUser(rs.getInt("id_user"));
                    user.setUsername(rs.getString("username"));
                    user.setNamaLengkap(rs.getString("nama_lengkap"));
                    return user;
                }
            }
        }
        return null;
    }

    public static List<User> semuaUser() throws SQLException {
        List<User> daftarUser = new ArrayList<>();
        String sql = "SELECT id_user, username, nama_lengkap FROM users ORDER BY nama_lengkap";
        try (Connection conn = Koneksi.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User();
                user.setIdUser(rs.getInt("id_user"));
                user.setUsername(rs.getString("username"));
                user.setNamaLengkap(rs.getString("nama_lengkap"));
                daftarUser.add(user);
            }
        }
        return daftarUser;
    }
}