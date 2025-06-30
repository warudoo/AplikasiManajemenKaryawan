# 🏢 Aplikasi Manajemen Karyawan

<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/NetBeans%20IDE-1B6AC6?style=for-the-badge&logo=apache-netbeans-ide&logoColor=white" alt="NetBeans">
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL">
  <img src="https://img.shields.io/badge/Bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white" alt="Bootstrap 5">
  <img src="https://img.shields.io/badge/Architecture-MVC%20%26%20DAO-blue?style=for-the-badge" alt="MVC & DAO Architecture">
</p>

Aplikasi web untuk manajemen data karyawan, dibangun sepenuhnya dengan **Java Servlet** menggunakan **NetBeans IDE**. Proyek ini menerapkan pola desain **Model-View-Controller (MVC)** dan **Data Access Object (DAO)** untuk memastikan kode yang terstruktur, terorganisir, dan mudah dikelola.

---

## ✨ Fitur Utama

-   **🔐 Sistem Otentikasi:** Sistem login dan logout yang aman untuk admin, lengkap dengan filter untuk melindungi halaman.
-   **👤 Manajemen Admin:** Operasi CRUD (Create, Read, Update, Delete) penuh untuk mengelola pengguna/admin aplikasi.
-   **🧑‍🎓 Manajemen Karyawan:** Mengelola data master karyawan (NIK, Nama, Kontak) beserta jabatannya.
-   **🛠️ Manajemen Jabatan:** Mengelola data master jabatan (Nama Jabatan, Gaji Pokok).
-   **🔒 Keamanan Password:** Enkripsi password menggunakan algoritma **SHA-256** untuk keamanan data login.
-   **🔍 Fitur Pencarian:** Fungsi pencarian karyawan berdasarkan NIK atau Nama.
-   **🎨 Antarmuka Modern:** Tampilan antarmuka yang bersih dan responsif dibangun dengan **Bootstrap 5**.

---

## 🏗️ Arsitektur & Teknologi

Aplikasi ini dibangun di atas tumpukan teknologi Java EE klasik dan mengikuti arsitektur berlapis yang memisahkan antara data (Model), tampilan (View), dan kontrol alur aplikasi (Controller).

| Kategori       | Teknologi / Pustaka                                    |
| :------------- | :----------------------------------------------------- |
| **Bahasa** | `Java`                                                 |
| **IDE** | `NetBeans IDE 8.2+`                                    |
| **Server** | `GlassFish Server` / `Apache Tomcat`                   |
| **Database** | `MySQL` (terhubung via JDBC)                           |
| **Frontend** | `Bootstrap 5` (via CDN)                                |
| **Dependensi** | `MySQL Connector/J`                                    |

<br>

<details>
<summary>📂 Struktur Proyek</summary>

<pre>
AplikasiManajemenKaryawan/
├── src/
│   └── java/
│       └── com/
│           └── karyawan/
│               ├── <strong>controller/</strong>  (Logika & Alur Aplikasi)
│               │   ├── AdminController.java
│               │   ├── JabatanController.java
│               │   ├── KaryawanController.java
│               │   ├── LoginController.java
│               │   ├── LogoutController.java
│               │   └── LoginFilter.java
│               │
│               ├── <strong>model/</strong>         (Logika Bisnis & Database)
│               │   ├── Enkripsi.java
│               │   ├── Jabatan.java
│               │   ├── JabatanDAO.java
│               │   ├── Karyawan.java
│               │   ├── KaryawanDAO.java
│               │   └── User.java
│               │
│               ├── <strong>util/</strong>          (Kelas Bantuan)
│               │   └── Koneksi.java
│               │
│               └── <strong>view/</strong>          (Komponen Tampilan)
│                   └── Layout.java
│
├── web/
│   └── WEB-INF/
│       ├── lib/
│       │   └── mysql-connector-j-x.x.x.jar
│       └── web.xml
│
└── build.xml
</pre>
</details>

---

## 🚀 Panduan Instalasi & Penggunaan

### **Prasyarat**
1.  **JDK (Java Development Kit)** versi 8 atau lebih tinggi.
2.  **IDE NetBeans** (disarankan versi yang mendukung Java EE).
3.  **Server Web** (Apache Tomcat atau GlassFish).
4.  **Database MySQL** (disarankan menggunakan XAMPP).

### **Langkah-langkah Instalasi**

1.  **Setup Database**
    - Buka **phpMyAdmin** dan buat database baru bernama `db_manajemen_karyawan`.
    - Jalankan query SQL yang sesuai untuk membuat tabel `users`, `jabatan`, dan `karyawan`, lalu isi data admin awal.

2.  **Buka Proyek**
    - Buka NetBeans, pilih `File > Open Project`, dan arahkan ke folder proyek ini.

3.  **Konfigurasi Koneksi**
    - Pastikan driver `mysql-connector-j-x.x.x.jar` sudah ada di folder `web/WEB-INF/lib/` atau di Libraries proyek.
    - Jika perlu, sesuaikan `USER` dan `PASSWORD` database di file `src/java/com/karyawan/util/Koneksi.java`.

4.  **Jalankan Aplikasi**
    - Klik kanan pada proyek, pilih **Clean and Build**.
    - Setelah selesai, klik kanan lagi dan pilih **Run**.

### **Akses dan Penggunaan Aplikasi**
- Buka browser dan akses aplikasi yang berjalan (misalnya `http://localhost:8080/NamaProyekAnda/`).
- Anda akan diarahkan ke halaman login. Gunakan kredensial default:
  - **Username**: `admin`
  - **Password**: `admin`
- Setelah login, Anda dapat mulai mengelola data melalui menu navigasi yang tersedia.

<br>
<div align="center">
  Dibuat dengan Java Servlet, MVC, dan DAO Pattern
</div>
