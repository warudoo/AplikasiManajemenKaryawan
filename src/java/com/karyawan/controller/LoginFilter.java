package com.karyawan.controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Filter ini mencegat semua permintaan ke halaman utama dan memastikan
 * pengguna sudah login sebelum memberikan akses.
 */
// PERBAIKAN UTAMA:
// URL Patterns disesuaikan dengan Controller yang benar di proyek ini.
// Kita melindungi URL "", "/karyawan", "/jabatan", dan "/admin".
@WebFilter(filterName = "LoginFilter", urlPatterns = {
    "",                    
    "/karyawan", 
    "/jabatan",
    "/admin"
})
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false); // Ambil sesi yang ada, jangan buat baru

        // Logika pengecekan sesi (logika ini sudah benar)
        // Jika tidak ada sesi ATAU tidak ada atribut "user" di dalam sesi...
        if (session == null || session.getAttribute("user") == null) {
            // ...maka paksa pengguna kembali ke halaman login.
            res.sendRedirect(req.getContextPath() + "/login");
        } else {
            // Jika sudah login, izinkan permintaan untuk melanjutkan ke tujuan.
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Bisa dibiarkan kosong
    }

    @Override
    public void destroy() {
        // Bisa dibiarkan kosong
    }
}