# 🏢 Aplikasi Penggajian Karyawan PT. Salwarud

<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/NetBeans%20IDE-1B6AC6?style=for-the-badge&logo=apache-netbeans-ide&logoColor=white" alt="NetBeans">
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL">
  <img src="https://img.shields.io/badge/GlassFish-2C568C?style=for-the-badge&logo=glassfish&logoColor=white" alt="GlassFish">
  <img src="https://img.shields.io/badge/MVC%20Architecture-blue?style=for-the-badge" alt="MVC Architecture">
</p>

Aplikasi web penggajian karyawan yang dirancang untuk mengelola data gaji secara efisien. Proyek ini dibangun sepenuhnya dengan **Java** menggunakan **NetBeans IDE 8.2** dan menerapkan pola desain **Model-View-Controller (MVC)** untuk memastikan kode yang terstruktur dan mudah dikelola.

---

## ✨ Fitur Utama

-   **👤 Manajemen Karyawan:** Operasi CRUD (Create, Read, Update, Delete) penuh untuk data karyawan.
-   **🛠️ Manajemen Pekerjaan:** Mengelola data master pekerjaan beserta detailnya.
-   **💰 Transaksi Gaji:** Memproses, menghitung, dan menyimpan riwayat gaji karyawan.
-   **🔐 Sistem Login & Keamanan:** Sistem otentikasi pengguna dengan enkripsi password (kemungkinan MD5 atau SHA).
-   **🔍 Pencarian Data:** Fitur pencarian untuk data gaji berdasarkan KTP atau kode pekerjaan.
-   **📄 Laporan Dinamis:** Menghasilkan laporan penggajian yang fleksibel, kemungkinan menggunakan **JasperReports**, yang dapat diekspor ke berbagai format.

---

## 🏗️ Arsitektur & Teknologi

Aplikasi ini dibangun di atas tumpukan teknologi Java EE klasik dan mengikuti arsitektur MVC yang jelas untuk memisahkan logika bisnis, data, dan presentasi.

| Kategori | Teknologi / Pustaka |
| :--- | :--- |
| **Bahasa** | `Java` |
| **IDE** | `NetBeans IDE 8.2` |
| **Server** | `GlassFish Server` |
| **Database** | `MySQL` (terhubung via JDBC) |
| **Pelaporan** | Kemungkinan `JasperReports` |
| **Dependensi** | `MySQL JDBC Driver` |

<br>

<details>
<summary>📂 Struktur Proyek</summary>
<pre>
AplikasiGajiKaryawan/
├── src/
│   └── java/
│       └── com/
│           └── unpam/
│               ├── <strong>controller/</strong> (Logika & Alur Aplikasi)
│               │   ├── GajiController.java
│               │   ├── KaryawanController.java
│               │   ├── LaporanGajiController.java
│               │   ├── LoginController.java
│               │   ├── LogoutController.java
│               │   └── PekerjaanController.java
│               │
│               ├── <strong>model/</strong> (Data & Logika Bisnis)
│               │   ├── CompileReport.java
│               │   ├── Enkripsi.java
│               │   ├── Gaji.java
│               │   ├── Karyawan.java
│               │   ├── Koneksi.java
│               │   └── Pekerjaan.java
│               │
│               └── <strong>view/</strong> (Tampilan & UI)
│                   ├── MainForm.java
│                   └── PesanDialog.java
│
├── web/
│   ├── WEB-INF/
│   │   └── web.xml
│   └── style.css
│
└── build.xml
</pre>
</details>

---

## 🚀 Alur Kerja Aplikasi

1.  **Login:** Pengguna melakukan otentikasi melalui `LoginController`.
2.  **Dashboard:** Setelah berhasil, pengguna diarahkan ke `MainForm` yang menjadi pusat navigasi.
3.  **Manajemen Data:** Pengguna mengelola data Karyawan, Pekerjaan, atau Gaji melalui `Controller` yang sesuai.
4.  **Pembuatan Laporan:** `LaporanGajiController` memproses permintaan laporan dan menggunakan `CompileReport.java` untuk menghasilkan output.

<br>

<details>
<summary>📦 Contoh Kode Model (Koneksi.java)</summary>

```java
// com/unpam/model/Koneksi.java
package com.unpam.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Koneksi {
    public Connection con;
    public Statement stm;

    public void koneksi() {
        try {
            String url = "jdbc:mysql://localhost/dbaplikasigajikaryawan";
            String username = "root";
            String password = ""; // Sesuaikan jika perlu
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
            stm = con.createStatement();
        } catch (Exception e) {
            System.err.println("Koneksi Gagal: " + e.getMessage());
        }
    }
}
```
</details>

<details>
<summary>🎮 Contoh Kode Controller (KaryawanController.java)</summary>

```java
// com/unpam/controller/KaryawanController.java
package com.unpam.controller;

import com.unpam.model.Karyawan;
import com.unpam.view.MainForm;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "KaryawanController", urlPatterns = {"/KaryawanController"})
public class KaryawanController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Karyawan karyawan = new Karyawan();
            String proses = request.getParameter("proses");
            String[] data = null;
            
            if (proses != null) {
                // Logika untuk simpan, ubah, atau hapus data
            }

            // Tampilkan view
            MainForm.buka(request, response, "Karyawan", karyawan.tampilData());
        }
    }
    // ... metode doGet dan doPost
}
```
</details>
<br>

<div align="center">
  Dibuat dengan Java Servlet & MVC Pattern
</div>
