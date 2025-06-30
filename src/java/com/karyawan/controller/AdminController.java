/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Warudo
 */
package com.karyawan.controller;

import com.karyawan.model.User;
import com.karyawan.view.Layout;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AdminController", urlPatterns = {"/admin"})
public class AdminController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String idStr = request.getParameter("id");
        
        try {
            if ("delete".equals(action) && idStr != null) {
                handleDelete(request, response, Integer.parseInt(idStr));
                return;
            }
            
            User userEdit = null;
            if ("edit".equals(action) && idStr != null) {
                userEdit = User.getById(Integer.parseInt(idStr));
            }
            
            tampilkanHalaman(request, response, userEdit);
            
        } catch (SQLException e) {
            throw new ServletException("Kesalahan Database saat memuat halaman Admin", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idStr = request.getParameter("idUser");
        
        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setNamaLengkap(request.getParameter("nama_lengkap"));
        user.setPassword(request.getParameter("password")); // Model akan menangani jika password kosong

        try {
            if (idStr == null || idStr.isEmpty()) {
                // Proses Simpan Baru
                user.simpan();
                request.getSession().setAttribute("message", "Admin baru berhasil disimpan.");
            } else {
                // Proses Update
                user.setIdUser(Integer.parseInt(idStr));
                user.update();
                request.getSession().setAttribute("message", "Data admin berhasil diperbarui.");
            }
            response.sendRedirect("admin");
        } catch (SQLException e) {
            request.getSession().setAttribute("error", "Gagal menyimpan data: " + e.getMessage());
            response.sendRedirect("admin");
        }
    }
    
    private void handleDelete(HttpServletRequest request, HttpServletResponse response, int idHapus) throws IOException, SQLException {
        HttpSession session = request.getSession();
        User userLogin = (User) session.getAttribute("user");

        // Fitur Keamanan Penting: Mencegah user menghapus akunnya sendiri.
        if (userLogin != null && userLogin.getIdUser() == idHapus) {
            session.setAttribute("error", "Operasi dibatalkan: Anda tidak dapat menghapus akun Anda sendiri!");
        } else {
            User.hapus(idHapus);
            session.setAttribute("message", "Data admin berhasil dihapus.");
        }
        response.sendRedirect("admin");
    }

    private void tampilkanHalaman(HttpServletRequest request, HttpServletResponse response, User userEdit) throws IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            
            Layout.printHeader(out, "Manajemen Admin", session);

            out.println("<h1><i class='bi bi-person-gear'></i> Manajemen Admin</h1>");

            // Menampilkan pesan sukses atau error
            String message = (String) session.getAttribute("message");
            if (message != null) {
                out.println("<div class='alert alert-success'>" + message + "</div>");
                session.removeAttribute("message");
            }
            String error = (String) session.getAttribute("error");
            if (error != null) {
                out.println("<div class='alert alert-danger'>" + error + "</div>");
                session.removeAttribute("error");
            }

            // Form Tambah/Edit dalam Card
            out.println("<div class='card mb-4'>");
            out.println("  <div class='card-header'>" + (userEdit != null ? "Edit Admin" : "Tambah Admin Baru") + "</div>");
            out.println("  <div class='card-body'>");
            out.println("    <form method='post' action='admin'>");
            if (userEdit != null) {
                out.println("      <input type='hidden' name='idUser' value='" + userEdit.getIdUser() + "'>");
            }
            out.println("      <div class='mb-3'>");
            out.println("        <label for='nama_lengkap' class='form-label'>Nama Lengkap</label>");
            out.println("        <input type='text' id='nama_lengkap' name='nama_lengkap' class='form-control' value='" + (userEdit != null ? userEdit.getNamaLengkap() : "") + "' required>");
            out.println("      </div>");
            out.println("      <div class='mb-3'>");
            out.println("        <label for='username' class='form-label'>Username</label>");
            out.println("        <input type='text' id='username' name='username' class='form-control' value='" + (userEdit != null ? userEdit.getUsername() : "") + "' required>");
            out.println("      </div>");
            out.println("      <div class='mb-3'>");
            out.println("        <label for='password' class='form-label'>Password</label>");
            out.println("        <input type='password' id='password' name='password' class='form-control' " + (userEdit == null ? "required" : "") + ">");
            if (userEdit != null) {
                out.println("      <div class='form-text'>Kosongkan jika tidak ingin mengubah password.</div>");
            }
            out.println("      </div>");
            out.println("      <button type='submit' class='btn btn-primary'>" + (userEdit != null ? "Update Admin" : "Simpan Admin") + "</button>");
            if (userEdit != null) {
                out.println("      <a href='admin' class='btn btn-secondary ms-2'>Batal</a>");
            }
            out.println("    </form>");
            out.println("  </div>");
            out.println("</div>");

            // Tabel Daftar Admin dalam Card
            List<User> daftarUser = User.semuaUser();
            out.println("<div class='card'>");
            out.println("  <div class='card-header'>Daftar Admin</div>");
            out.println("  <div class='card-body'>");
            out.println("    <div class='table-responsive'>");
            out.println("      <table class='table table-striped table-hover'>");
            out.println("        <thead><tr><th>ID</th><th>Nama Lengkap</th><th>Username</th><th>Aksi</th></tr></thead>");
            out.println("        <tbody>");
            for (User u : daftarUser) {
                out.println("<tr>");
                out.println("  <td>" + u.getIdUser() + "</td>");
                out.println("  <td>" + u.getNamaLengkap() + "</td>");
                out.println("  <td>" + u.getUsername() + "</td>");
                out.println("  <td>");
                out.println("    <a href='admin?action=edit&id=" + u.getIdUser() + "' class='btn btn-sm btn-warning'><i class='bi bi-pencil-square'></i> Edit</a>");
                out.println("    <a href='admin?action=delete&id=" + u.getIdUser() + "' class='btn btn-sm btn-danger ms-1' onclick='return confirm(\"Yakin ingin menghapus admin ini?\")'><i class='bi bi-trash-fill'></i> Hapus</a>");
                out.println("  </td>");
                out.println("</tr>");
            }
            out.println("        </tbody>");
            out.println("      </table>");
            out.println("    </div>");
            out.println("  </div>");
            out.println("</div>");
            
            Layout.printFooter(out);
        }
    }
}