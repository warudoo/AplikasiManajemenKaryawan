/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.karyawan.view;

/**
 *
 * @author Warudo
 */

import com.karyawan.model.User;
import java.io.PrintWriter;
import javax.servlet.http.HttpSession;


public class Layout {
    public static void printHeader(PrintWriter out, String title, HttpSession session) { // Tambahkan parameter session
        User user = (User) session.getAttribute("user"); // Ambil user dari session

        out.println("<!DOCTYPE html><html lang='en'><head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>" + title + " - PT Warud</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css'>");
        out.println("</head><body>");
        
        out.println("<nav class='navbar navbar-expand-lg navbar-dark bg-dark shadow-sm'>");
        out.println("<div class='container'><a class='navbar-brand' href='karyawan'>PT Warud</a>");
        out.println("<div class='collapse navbar-collapse'><ul class='navbar-nav me-auto'>");
        out.println("<li class='nav-item'><a class='nav-link' href='karyawan'>Karyawan</a></li>");
        out.println("<li class='nav-item'><a class='nav-link' href='jabatan'>Jabatan</a></li>");
        // Tambahkan menu admin jika perlu
        out.println("<li class='nav-item'><a class='nav-link' href='admin'>Kelola Admin</a></li>");
        out.println("</ul>");

        if(user != null) {
            out.println("<span class='navbar-text me-3'>Halo, " + user.getNamaLengkap() + "</span>");
            out.println("<a href='logout' class='btn btn-outline-light'>Logout</a>");
        }
        
        out.println("</div></div></nav>");
        out.println("<div class='container mt-4'>");
    }

    public static void printFooter(PrintWriter out) {
        out.println("</div><script src='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js'></script></body></html>");
    }
}