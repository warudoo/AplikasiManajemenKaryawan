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

import com.karyawan.model.Jabatan;
import com.karyawan.model.JabatanDAO;
import com.karyawan.model.Karyawan;
import com.karyawan.model.KaryawanDAO;
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

// Mapping ke URL root ("") dan "/karyawan"
@WebServlet(name = "KaryawanController", urlPatterns = {"/karyawan", ""})
public class KaryawanController extends HttpServlet {
    private final KaryawanDAO karyawanDAO = new KaryawanDAO();
    private final JabatanDAO jabatanDAO = new JabatanDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String idStr = request.getParameter("id");
        String keyword = request.getParameter("keyword");

        try {
            // Logika untuk menghapus data
            if ("delete".equals(action) && idStr != null) {
                karyawanDAO.hapusKaryawan(Integer.parseInt(idStr));
                request.getSession().setAttribute("message", "Data karyawan berhasil dihapus.");
                response.sendRedirect("karyawan");
                return;
            }

            // Menyiapkan data untuk form edit
            Karyawan karyawanEdit = null;
            if ("edit".equals(action) && idStr != null) {
                karyawanEdit = karyawanDAO.getKaryawanById(Integer.parseInt(idStr));
            }
            
            // Mengambil daftar karyawan (berdasarkan pencarian atau semua)
            List<Karyawan> daftarKaryawan;
            if (keyword != null && !keyword.isEmpty()) {
                daftarKaryawan = karyawanDAO.cariKaryawan(keyword);
            } else {
                daftarKaryawan = karyawanDAO.semuaKaryawan();
            }
            
            // Mengambil daftar jabatan untuk dropdown form
            List<Jabatan> daftarJabatan = jabatanDAO.semuaJabatan();

            // Memanggil metode untuk menampilkan seluruh halaman
            tampilkanHalaman(request, response, karyawanEdit, daftarKaryawan, daftarJabatan, keyword);
            
        } catch (SQLException e) {
            throw new ServletException("Kesalahan Database saat memuat data Karyawan", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idStr = request.getParameter("id");
        try {
            Karyawan k = new Karyawan();
            k.setNik(request.getParameter("nik"));
            k.setNamaLengkap(request.getParameter("nama"));
            k.setEmail(request.getParameter("email"));
            k.setNoTelepon(request.getParameter("telepon"));
            k.setAlamat(request.getParameter("alamat"));
            k.getJabatan().setId(Integer.parseInt(request.getParameter("id_jabatan")));
            
            if (idStr == null || idStr.isEmpty()) {
                // Proses Simpan Baru
                karyawanDAO.tambahKaryawan(k);
                request.getSession().setAttribute("message", "Karyawan baru berhasil ditambahkan.");
            } else {
                // Proses Update
                k.setId(Integer.parseInt(idStr));
                karyawanDAO.updateKaryawan(k);
                request.getSession().setAttribute("message", "Data karyawan berhasil diperbarui.");
            }
            response.sendRedirect("karyawan");
        } catch (SQLException e) {
            request.getSession().setAttribute("error", "Gagal menyimpan data: " + e.getMessage());
            response.sendRedirect("karyawan");
        }
    }

    private void tampilkanHalaman(HttpServletRequest request, HttpServletResponse response, Karyawan karyawanEdit, List<Karyawan> daftarKaryawan, List<Jabatan> daftarJabatan, String keyword) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            Layout.printHeader(out, "Manajemen Karyawan", session);

            // Menampilkan pesan sukses atau error dari session
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
            out.println("<div class='card mb-4'><div class='card-header'><i class='bi bi-person-plus-fill'></i> " + (karyawanEdit != null ? "Edit Data Karyawan" : "Tambah Karyawan Baru") + "</div><div class='card-body'>");
            out.println("<form method='post' action='karyawan'>");
            if(karyawanEdit != null) out.println("<input type='hidden' name='id' value='" + karyawanEdit.getId() + "'>");
            out.println("<div class='row'><div class='col-md-6 mb-3'><label class='form-label'>NIK</label><input type='text' name='nik' class='form-control' value='" + (karyawanEdit != null ? karyawanEdit.getNik() : "") + "' required></div>");
            out.println("<div class='col-md-6 mb-3'><label class='form-label'>Nama Lengkap</label><input type='text' name='nama' class='form-control' value='" + (karyawanEdit != null ? karyawanEdit.getNamaLengkap() : "") + "' required></div></div>");
            out.println("<div class='row'><div class='col-md-6 mb-3'><label class='form-label'>Email</label><input type='email' name='email' class='form-control' value='" + (karyawanEdit != null ? karyawanEdit.getEmail() : "") + "'></div>");
            out.println("<div class='col-md-6 mb-3'><label class='form-label'>No Telepon</label><input type='text' name='telepon' class='form-control' value='" + (karyawanEdit != null ? karyawanEdit.getNoTelepon() : "") + "'></div></div>");
            out.println("<div class='mb-3'><label class='form-label'>Jabatan</label><select name='id_jabatan' class='form-select' required><option value=''>Pilih Jabatan</option>");
            for (Jabatan j : daftarJabatan) {
                String selected = (karyawanEdit != null && karyawanEdit.getJabatan().getId() == j.getId()) ? "selected" : "";
                out.println("<option value='" + j.getId() + "' " + selected + ">" + j.getNamaJabatan() + "</option>");
            }
            out.println("</select></div>");
            out.println("<div class='mb-3'><label class='form-label'>Alamat</label><textarea name='alamat' class='form-control'>"+(karyawanEdit != null ? karyawanEdit.getAlamat() : "")+"</textarea></div>");
            out.println("<button type='submit' class='btn btn-primary'>" + (karyawanEdit != null ? "<i class='bi bi-save-fill'></i> Update Karyawan" : "<i class='bi bi-plus-circle-fill'></i> Simpan Karyawan") + "</button>");
            if (karyawanEdit != null) out.println(" <a href='karyawan' class='btn btn-secondary'>Batal</a>");
            out.println("</form></div></div>");

            // Tabel Daftar Karyawan dan Fitur Pencarian
            out.println("<div class='card'><div class='card-header'><i class='bi bi-table'></i> Daftar Karyawan</div><div class='card-body'>");
            out.println("<form class='d-flex mb-3' method='get' action='karyawan'><input class='form-control me-2' type='search' name='keyword' placeholder='Cari berdasarkan NIK atau Nama...' value='"+(keyword != null ? keyword : "")+"'><button class='btn btn-outline-success' type='submit'><i class='bi bi-search'></i> Cari</button></form>");
            out.println("<div class='table-responsive'><table class='table table-striped table-hover'><thead><tr><th>NIK</th><th>Nama Lengkap</th><th>Jabatan</th><th>Gaji Pokok</th><th>Email</th><th>No. Telepon</th><th>Aksi</th></tr></thead><tbody>");
            for (Karyawan k : daftarKaryawan) {
                out.println("<tr>");
                out.println("<td>" + k.getNik() + "</td>");
                out.println("<td>" + k.getNamaLengkap() + "</td>");
                out.println("<td>" + (k.getJabatan().getNamaJabatan() != null ? k.getJabatan().getNamaJabatan() : "<span class='text-muted'>N/A</span>") + "</td>");
                out.println("<td>Rp " + String.format("%,.0f", k.getJabatan().getGajiPokok()) + "</td>");
                out.println("<td>" + k.getEmail() + "</td>");
                out.println("<td>" + k.getNoTelepon() + "</td>");
                out.println("<td><a href='karyawan?action=edit&id="+k.getId()+"' class='btn btn-sm btn-warning'><i class='bi bi-pencil-square'></i></a> <a href='karyawan?action=delete&id="+k.getId()+"' class='btn btn-sm btn-danger ms-1' onclick='return confirm(\"Yakin ingin menghapus karyawan ini?\")'><i class='bi bi-trash-fill'></i></a></td>");
                out.println("</tr>");
            }
            out.println("</tbody></table></div></div></div>");

            Layout.printFooter(out);
        }
    }
}