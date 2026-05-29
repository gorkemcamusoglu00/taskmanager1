# Task Manager API

Spring Boot ile geliştirilmiş RESTful bir görev yönetimi API'si. Temel CRUD işlemlerinin yanı sıra arama, filtreleme ve rol tabanlı yetkilendirme özelliklerini içermektedir.

## Teknolojiler

- **Java 17**
- **Spring Boot 4.0.6**
- **Spring Data JPA**
- **Spring Security** (HTTP Basic Auth, rol tabanlı erişim)
- **H2** (in-memory veritabanı)
- **Bean Validation** (Jakarta Validation)
- **Maven**

## Proje Yapısı

```
src/main/java/org/example/taskmanager1/
├── config/
│   └── SecurityConfig.java        # Güvenlik ve kullanıcı ayarları
├── controller/
│   └── TaskController.java        # REST endpoint'leri
├── dto/
│   └── TaskRequest.java           # İstek gövdesi için DTO
├── entity/
│   └── Task.java                  # JPA varlık sınıfı
├── exception/
│   └── GlobalExceptionHandler.java # Global hata yönetimi
├── repository/
│   └── TaskRepository.java        # Veritabanı sorguları
└── service/
    └── TaskService.java           # İş mantığı katmanı
```

## Kurulum ve Çalıştırma

### Gereksinimler

- Java 17+
- Maven

### Adımlar

```bash
# Repoyu klonlayın
git clone https://github.com/gorkemcamusoglu00/taskmanager1.git
cd taskmanager1

# Uygulamayı derleyip çalıştırın
./mvnw spring-boot:run
```

Uygulama `http://localhost:8080` adresinde ayağa kalkar.

H2 konsol paneline `http://localhost:8080/h2-console` adresinden erişilebilir.

## Güvenlik

API, HTTP Basic Authentication kullanmaktadır. İki varsayılan kullanıcı mevcuttur:

| Kullanıcı | Şifre      | Rol   | Yetkiler                    |
|-----------|------------|-------|-----------------------------|
| `user`    | `1234`     | USER  | Yalnızca GET istekleri      |
| `admin`   | `admin123` | ADMIN | GET, POST, PUT, DELETE      |

## API Endpoint'leri

Tüm endpoint'ler `/tasks` prefix'i altındadır.

### Görev İşlemleri

| Method | Endpoint                    | Açıklama                              | Yetki |
|--------|-----------------------------|---------------------------------------|-------|
| GET    | `/tasks`                    | Tüm görevleri listele                 | USER  |
| GET    | `/tasks/{id}`               | ID'ye göre görev getir                | USER  |
| POST   | `/tasks`                    | Yeni görev oluştur                    | ADMIN |
| PUT    | `/tasks/{id}`               | Görevi güncelle                       | ADMIN |
| DELETE | `/tasks/{id}`               | Görevi sil                            | ADMIN |

### Sorgulama ve Filtreleme

| Method | Endpoint                        | Açıklama                                         | Yetki |
|--------|---------------------------------|--------------------------------------------------|-------|
| GET    | `/tasks/completed/{completed}`  | Tamamlanma durumuna göre filtrele                | USER  |
| GET    | `/tasks/search?title=`          | Başlığa göre ara (büyük/küçük harf duyarsız)     | USER  |
| GET    | `/tasks/filter?title=&completed=` | Başlık ve durum kombinasyonuna göre filtrele   | USER  |
| GET    | `/tasks/count?completed=`       | Duruma göre görev sayısını getir                 | USER  |
| GET    | `/tasks/exists?title=`          | Başlığa göre görev var mı kontrol et             | USER  |
| GET    | `/tasks/latest`                 | En son eklenen 5 görevi getir                    | USER  |

### Test

| Method | Endpoint               | Açıklama                              | Yetki |
|--------|------------------------|---------------------------------------|-------|
| POST   | `/tasks/rollback-test` | Transaction rollback davranışını test et | ADMIN |

### İstek Gövdesi (POST / PUT)

```json
{
  "title": "Görev başlığı",
  "description": "Görev açıklaması",
  "completed": false
}
```

**Validasyon kuralları:**
- `title`: zorunlu, 3–100 karakter
- `description`: isteğe bağlı, en fazla 500 karakter
- `completed`: isteğe bağlı, varsayılan `false`

### Örnek İstekler

```bash
# Tüm görevleri listele
curl -u user:1234 http://localhost:8080/tasks

# Yeni görev oluştur
curl -u admin:admin123 -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{"title": "Alışveriş yap", "description": "Süt ve ekmek al"}'

# Başlığa göre ara
curl -u user:1234 "http://localhost:8080/tasks/search?title=alış"

# Tamamlanmamış görev sayısı
curl -u user:1234 "http://localhost:8080/tasks/count?completed=false"
```

## Hata Yönetimi

Validasyon hataları `400 Bad Request` ile birlikte aşağıdaki formatta döner:

```json
{
  "timestamp": "2026-05-29T10:00:00",
  "status": 400,
  "error": "Validation Error",
  "messages": {
    "title": "Başlık boş bırakılamaz."
  }
}
```

## Lisans

Bu proje MIT Lisansı ile lisanslanmıştır.
