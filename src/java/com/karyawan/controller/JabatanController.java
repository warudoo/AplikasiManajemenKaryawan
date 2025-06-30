package com.karyawan.controller;

import com.karyawan.model.Jabatan;
import com.karyawan.model.JabatanDAO;
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

@WebServlet(name = "JabatanController", urlPatterns = {"/jabatan"})
public class JabatanController extends HttpServlet {
    private final JabatanDAO jabatanDAO = new JabatanDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String idStr = request.getParameter("id");
        
        try {
            // Logika untuk menghapus
            if ("delete".equals(action) && idStr != null) {
                jabatanDAO.hapusJabatan(Integer.parseInt(idStr));
                request.getSession().setAttribute("message", "Data jabatan berhasil dihapus.");
                response.sendRedirect("jabatan");
                return;
            }
            
            Jabatan jabatanEdit = null;
            // Logika untuk menampilkan form edit
            if ("edit".equals(action) && idStr != null) {
                jabatanEdit = jabatanDAO.getJabatanById(Integer.parseInt(idStr));
            }
            
            // Logika utama untuk menampilkan halaman
            tampilkanHalaman(request, response, jabatanEdit);
            
        } catch (SQLException e) {
            // Exception handling yang baik
            throw new ServletException("Kesalahan Database saat memuat halaman Jabatan", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        
        String namaJabatan = request.getParameter("nama_jabatan");
        double gajiPokok = Double.parseDouble(request.getParameter("gaji_pokok"));

        try {
            Jabatan jabatan = new Jabatan();
            jabatan.setNamaJabatan(namaJabatan);
            jabatan.setGajiPokok(gajiPokok);

            if (idStr == null || idStr.isEmpty()) {
                // Proses Simpan Baru
                jabatanDAO.tambahJabatan(jabatan);
                request.getSession().setAttribute("message", "Jabatan baru berhasil disimpan.");
            } else {
                // Proses Update
                jabatan.setId(Integer.parseInt(idStr));
                jabatanDAO.updateJabatan(jabatan);
                request.getSession().setAttribute("message", "Data jabatan berhasil diperbarui.");
            }
            response.sendRedirect("jabatan");
        } catch (SQLException e) {
            // Menangani error (misalnya duplikasi nama jabatan)
            request.getSession().setAttribute("error", "Gagal menyimpan data: " + e.getMessage());
            response.sendRedirect("jabatan");
        }
    }

    private void tampilkanHalaman(HttpServletRequest request, HttpServletResponse response, Jabatan jabatanEdit) throws IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            
            // Panggil Header dari kelas Layout, kirim session untuk info login
            Layout.printHeader(out, "Manajemen Jabatan", session);

            out.println("<h1><i class='bi bi-briefcase-fill'></i> Manajemen Jabatan</h1>");

            // Tampilkan pesan sukses atau error dari session
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
            out.println("  <div class='card-header'>" + (jabatanEdit != null ? "Edit Jabatan" : "Tambah Jabatan Baru") + "</div>");
            out.println("  <div class='card-body'>");
            out.println("    <form method='post' action='jabatan'>");
            if (jabatanEdit != null) {
                out.println("      <input type='hidden' name='id' value='" + jabatanEdit.getId() + "'>");
            }
            out.println("      <div class='row'>");
            out.println("        <div class='col-md-6 mb-3'>");
            out.println("          <label for='nama_jabatan' class='form-label'>Nama Jabatan</label>");
            out.println("          <input type='text' id='nama_jabatan' name='nama_jabatan' class='form-control' value='" + (jabatanEdit != null ? jabatanEdit.getNamaJabatan() : "") + "' required>");
            out.println("        </div>");
            out.println("        <div class='col-md-6 mb-3'>");
            out.println("          <label for='gaji_pokok' class='form-label'>Gaji Pokok</label>");
            out.println("          <input type='number' step='1000' id='gaji_pokok' name='gaji_pokok' class='form-control' value='" + (jabatanEdit != null ? jabatanEdit.getGajiPokok() : "") + "' required>");
            out.println("        </div>");
            out.println("      </div>");
            out.println("      <button type='submit' class='btn btn-primary'>" + (jabatanEdit != null ? "Update Jabatan" : "Simpan Jabatan") + "</button>");
            if (jabatanEdit != null) {
                out.println("      <a href='jabatan' class='btn btn-secondary ms-2'>Batal</a>");
            }
            out.println("    </form>");
            out.println("  </div>");
            out.println("</div>");

            // Tabel Daftar Jabatan dalam Card
            List<Jabatan> daftarJabatan = jabatanDAO.semuaJabatan();
            out.println("<div class='card'>");
            out.println("  <div class='card-header'>Daftar Jabatan</div>");
            out.println("  <div class='card-body'>");
            out.println("    <div class='table-responsive'>");
            out.println("      <table class='table table-striped table-hover'>");
            out.println("        <thead><tr><th>ID</th><th>Nama Jabatan</th><th>Gaji Pokok</th><th>Aksi</th></tr></thead>");
            out.println("        <tbody>");
            for (Jabatan j : daftarJabatan) {
                out.println("<tr>");
                out.println("  <td>" + j.getId() + "</td>");
                out.println("  <td>" + j.getNamaJabatan() + "</td>");
                out.println("  <td>Rp " + String.format("%,.0f", j.getGajiPokok()) + "</td>");
                out.println("  <td>");
                out.println("    <a href='jabatan?action=edit&id=" + j.getId() + "' class='btn btn-sm btn-warning'><i class='bi bi-pencil-square'></i> Edit</a>");
                out.println("    <a href='jabatan?action=delete&id=" + j.getId() + "' class='btn btn-sm btn-danger ms-1' onclick='return confirm(\"Yakin ingin menghapus? Karyawan dengan jabatan ini akan kehilangan jabatannya.\")'><i class='bi bi-trash-fill'></i> Hapus</a>");
                out.println("  </td>");
                out.println("</tr>");
            }
            out.println("        </tbody>");
            out.println("      </table>");
            out.println("    </div>");
            out.println("  </div>");
            out.println("</div>");
            
            // Panggil Footer dari kelas Layout
            Layout.printFooter(out);
        }
    }
}