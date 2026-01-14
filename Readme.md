# Perpustakaan Microservices System

> Implementasi sistem manajemen perpustakaan modern berbasis Microservices dengan Spring Boot, orkestrasi Kubernetes, serta penerapan DevOps lengkap (CI/CD, Logging, & Monitoring).

Project ini mendemonstrasikan arsitektur microservices yang scalable, mencakup pola **CQRS**, **Centralized Logging**, **Monitoring**, dan otomatisasi **CI/CD Pipeline**.

---

## Arsitektur & Teknologi

Sistem ini dibangun di atas ekosistem Java & Spring Boot dengan komponen infrastruktur sebagai berikut:

### 🧩 Core Services & Logic
*   **Domain Services:** Buku, Anggota, Pengembalian, Notifikasi.
*   **Peminjaman:** Menggunakan pola **CQRS** (Command & Query Responsibility Segregation).

### ⚙️ Infrastructure & Discovery
*   **API Gateway:** Pintu masuk utama request.
*   **Eureka Server:** Service Discovery.
*   **Orchestration:** Kubernetes (K8s) untuk deployment dan scaling.

### 💾 Database & Messaging
*   **Database:** MongoDB & H2 Database.
*   **Message Broker:** RabbitMQ (untuk komunikasi asinkron antar service).

### 📊 Observability (DevOps)
*   **Monitoring:** Prometheus (Metrics) & Grafana (Visualization).
*   **Logging:** ELK Stack (Elasticsearch, Logstash, Kibana, Filebeat).
*   **CI/CD:** Jenkins (Automated Build & Deploy).

---

## Panduan Instalasi & Deployment

Langkah-langkah untuk menjalankan sistem secara berurutan:

1.  **Deploy Core & Database:**
    Setup MongoDB, RabbitMQ, dan deploy service utama (Buku, Anggota, dll) ke dalam cluster.

2.  **Setup Monitoring:**
    Implementasi Prometheus dan import dashboard Grafana untuk memantau kesehatan aplikasi.

3.  **Setup Centralized Logging:**
    Konfigurasi Filebeat dan ELK Stack untuk mengagregasi log dari seluruh container.

4.  **Eksekusi CI/CD:**
    Jalankan Jenkins Pipeline untuk otomatisasi proses build hingga deployment. 

Selengkapnya: **[Panduan Konfigurasi & Instalasi](docs/langkah_konfigurasi.md)**

---

## Prasyarat

Pastikan *environment* lokal sudah terinstall:

- [ ] **Docker Desktop** (dengan Kubernetes Enabled)
- [ ] **Git**
- [ ] **Java JDK 17**
- [ ] **Maven**

---