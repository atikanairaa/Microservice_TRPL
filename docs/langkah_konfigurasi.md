# Panduan Konfigurasi & Instalasi Sistem

Dokumen ini merangkum seluruh langkah teknis yang diperlukan untuk menjalankan ekosistem **Perpustakaan Microservices**. Panduan ini mencakup setup infrastruktur dasar di Kubernetes, implementasi monitoring, logging terpusat, hingga otomatisasi CI/CD.

---

## A. Setup Infrastruktur & Core Services

Langkah pertama adalah menyiapkan fondasi sistem, termasuk namespace, database, message broker, dan seluruh service aplikasi.

### 1. Persiapan Namespace
Buat namespace khusus `perpustakaan-ns` untuk mengisolasi resource aplikasi.
```bash
kubectl create namespace perpustakaan-ns
```

### 2. Deploy Database & Broker
Masuk ke direktori `kubernetes` dan terapkan konfigurasi untuk MongoDB (Database) dan RabbitMQ (Message Broker).
```bash
kubectl apply -f mongodb-secret.yaml
kubectl apply -f mongodb-pvc.yaml
kubectl apply -f mongodb-deployment.yaml
kubectl apply -f rabbitmq-deployment.yaml
```

### 3. Deploy Microservices
Jalankan perintah berikut untuk mengaktifkan seluruh service, mulai dari infrastruktur (Gateway & Discovery) hingga domain services (Buku, Anggota, dll).
```bash
# Infrastructure
kubectl apply -f eureka-deployment.yaml
kubectl apply -f api-gateway-deployment.yaml

# Domain Services
kubectl apply -f buku-service-deployment.yaml
kubectl apply -f anggota-service-deployment.yaml
kubectl apply -f notification-service-deployment.yaml
kubectl apply -f peminjaman-query-service-deployment.yaml
kubectl apply -f peminjaman-service-deployment.yaml
kubectl apply -f pengembalian-service-deployment.yaml
```

### 4. Verifikasi Deployment
Pastikan seluruh pod berjalan dengan status `Running`.
```bash
kubectl get pods -n perpustakaan-ns
```

---

## B. Implementasi Monitoring (Observability)

Sistem menggunakan **Prometheus** untuk mengumpulkan metrik performa dan **Grafana** untuk visualisasi dashboard. Konfigurasi terdapat di folder `kubernetes/monitoring`.

### 1. Setup Namespace Monitoring
```bash
kubectl create namespace monitoring-ns
```

### 2. Deploy Prometheus
Prometheus dikonfigurasi untuk mendeteksi service Spring Boot secara otomatis (auto-discovery) melalui annotations.
```bash
kubectl apply -f prometheus-configmap.yaml
kubectl apply -f prometheus-deployment.yaml
kubectl apply -f prometheus-rbac.yaml
```

### 3. Deploy Exporters
Exporter diperlukan agar Prometheus dapat membaca metrik dari MongoDB dan RabbitMQ.
```bash
kubectl apply -f monitoring-mongodb-secret.yaml
kubectl apply -f mongodb-exporter.yaml
kubectl apply -f rabbitmq-exporter.yaml
```

### 4. Deploy Grafana
```bash
kubectl apply -f grafana-deployment.yaml
```

### 🔗 Akses Dashboard
*   **Grafana:** [http://localhost:30003](http://localhost:30003) (Login default: `admin` / `admin`)
*   **Prometheus:** [http://localhost:30090](http://localhost:30090)

---

## C. Centralized Logging (ELK Stack)

Untuk manajemen log terpusat, kami menggunakan **ELK Stack (Elasticsearch, Logstash, Kibana)** dengan **Filebeat** sebagai agen pengirim log (*log shipper*).

**Arsitektur Logging:**
*   **Filebeat (DaemonSet):** Mengambil log dari setiap node Kubernetes.
*   **Logstash:** Memproses dan memfilter log.
*   **Elasticsearch:** Menyimpan data log.
*   **Kibana:** Antarmuka visual untuk pencarian log.

### 1. Setup Namespace Logging
```bash
kubectl create namespace logging-ns
```

### 2. Deploy Komponen ELK
Arahkan terminal ke folder `kubernetes/logging` lalu jalankan:
```bash
kubectl apply -f elasticsearch.yaml
kubectl apply -f kibana.yaml
kubectl apply -f logstash.yaml
kubectl apply -f filebeat.yaml
```

### 3. Konfigurasi Kibana
1.  Akses [http://localhost:30601](http://localhost:30601) melalui browser.
2.  Navigasi ke menu **Stack Management > Index Patterns**.
3.  Buat index pattern baru dengan pola: `microservices-log-*`.
4.  Buka menu **Discover** untuk memantau log aplikasi secara real-time.

---

## D. Implementasi CI/CD (Jenkins)

Jenkins digunakan untuk mengotomatisasi seluruh siklus hidup aplikasi: Build, Dockerize, dan Deployment ke Kubernetes.

### 1. Deploy Jenkins
Masuk ke folder `kubernetes/jenkins` dan terapkan konfigurasi:
```bash
kubectl apply -f jenkins-setup.yaml
kubectl apply -f jenkins-pvc.yaml
kubectl apply -f jenkins-deployment.yaml
```

### 2. Akses Jenkins
Dashboard Jenkins dapat diakses di [http://localhost:32000](http://localhost:32000).

### 3. Alur Pipeline (Jenkinsfile)
Setiap microservice dilengkapi dengan `Jenkinsfile` yang menjalankan tahapan berikut:
1.  **Initialize:** Menyiapkan environment Docker & Kubectl.
2.  **Build Maven:** Kompilasi kode Java (`mvn clean package -DskipTests`).
3.  **Build Docker:** Membuat image container dengan tag versi terbaru.
4.  **Push Image:** Mengunggah image ke Local Registry (`localhost:5000`).
5.  **Deploy:** Memperbarui deployment di Kubernetes dan melakukan *rollout restart*.

### 4. Daftar Pipeline
Pastikan pipeline berikut telah dibuat di Jenkins:
*   `Pipeline-bukuservice`
*   `Pipeline-anggotaservice`
*   `Pipeline-peminjaman-command`
*   `Pipeline-peminjaman-query`
*   `Pipeline-pengembalian-service`
*   `Pipeline-notification-service`