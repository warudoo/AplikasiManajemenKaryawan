package com.karyawan.controller;

import com.karyawan.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Cek jika sudah login, langsung redirect ke halaman utama
        if (request.getSession(false) != null && request.getSession(false).getAttribute("user") != null) {
            // PERBAIKAN 1: Redirect ke halaman karyawan, bukan MainForm
            response.sendRedirect("karyawan"); 
            return;
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            
            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");
            out.println("<head>");
            out.println("    <meta charset='UTF-8'>");
            out.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("    <title>Login - HRMS</title>");
            out.println("    <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
            out.println("    <style>");
            out.println("        body { display: flex; align-items: center; justify-content: center; height: 100vh; background-color: #f8f9fa; }");
            out.println("        .login-card { max-width: 400px; width: 100%; }");
            out.println("    </style>");
            out.println("</head>");
            out.println("<body>");
            
            out.println("<div class='card login-card shadow-sm'>");
            out.println("  <div class='card-body p-5'>");
            out.println("    <h3 class='card-title text-center mb-4'>Login PT Muhamad Salwarud</h3>");
            
            String error = (String) session.getAttribute("error");
            if (error != null) {
                out.println("    <div class='alert alert-danger'>" + error + "</div>");
                session.removeAttribute("error");
            }
            
            // PERBAIKAN 2: Ganti action ke "login" agar sesuai URL pattern
            out.println("    <form method='post' action='login'>");
            out.println("      <div class='mb-3'>");
            // PERBAIKAN 3: Ganti name dari 'user' menjadi 'username' agar cocok dengan doPost
            out.println("        <label for='username' class='form-label'>Username</label>");
            out.println("        <input type='text' class='form-control' id='username' name='username' required autofocus>");
            out.println("      </div>");
            out.println("      <div class='mb-3'>");
            // PERBAIKAN 4: Ganti name dari 'pass' menjadi 'password' agar cocok dengan doPost
            out.println("        <label for='password' class='form-label'>Password</label>");
            out.println("        <input type='password' class='form-control' id='password' name='password' required>");
            out.println("      </div>");
            out.println("      <div class='d-grid'>");
            out.println("        <button type='submit' class='btn btn-primary'>Login</button>");
            out.println("      </div>");
            out.println("    </form>");
            out.println("  </div>");
            out.println("</div>");

            out.println("<script src='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js'></script>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User userModel = new User();
        // Sekarang request.getParameter akan mendapatkan nilai yang benar karena nama input sudah disamakan
        userModel.setUsername(request.getParameter("username"));
        userModel.setPassword(request.getParameter("password"));

        try {
            User loggedInUser = userModel.login();
            if (loggedInUser != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", loggedInUser);
                response.sendRedirect("karyawan"); // Arahkan ke halaman utama yang benar
            } else {
                // Set pesan error di session agar bisa ditampilkan di doGet
                HttpSession session = request.getSession();
                session.setAttribute("error", "Username atau Password salah!");
                response.sendRedirect("login"); // Redirect kembali ke halaman login
            }
        } catch (SQLException e) {
            throw new ServletException("Login failed due to a database error.", e);
        }
    }
}