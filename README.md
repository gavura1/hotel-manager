# Hotel Manager

Webová aplikácia na správu hotelov, izieb a rezervácií.

---

## Obsah

- [Popis aplikácie](#popis-aplikácie)
- [Technológie](#technológie)
- [Architektúra](#architektúra)
- [Používateľské roly](#používateľské-roly)
- [Spustenie cez Docker](#spustenie-cez-docker)
- [Spustenie lokálne](#spustenie-lokálne)
- [Testovacie prihlasovacie údaje](#testovacie-prihlasovacie-údaje)
- [API dokumentácia](#api-dokumentácia)
- [Štruktúra projektu](#štruktúra-projektu)
- [CI/CD pipeline](#cicd-pipeline)

---

## Popis aplikácie

Hotel Manager umožňuje správu hotelového systému s tromi úrovňami prístupu. Admin spravuje hotely, izby a používateľov. Manager spravuje izby a rezervácie svojho hotela. Bežný používateľ si môže prezerať hotely, izby a spravovať vlastné rezervácie.

Autentifikácia je riešená pomocou JWT tokenov a Google OAuth2 prihlásenia.

---

## Technológie

**Backend**
- Java 21
- Spring Boot 3.4.5
- Spring Security + JWT + Google OAuth2
- Spring Data JPA + Hibernate
- MySQL 8.0
- Lombok
- Springdoc OpenAPI (Swagger)

**Frontend**
- Angular 18+
- Angular Material
- TypeScript
- RxJS

**Infraštruktúra**
- Docker + Docker Compose
- nginx
- GitLab CI/CD

---

## Architektúra

```
┌─────────────────┐     HTTP      ┌─────────────────┐     JDBC      ┌─────────────┐
│   Angular       │ ────────────► │  Spring Boot    │ ────────────► │   MySQL     │
│   (port 4200)   │ ◄──────────── │  (port 8080)    │ ◄──────────── │  (port 3306)│
└─────────────────┘               └─────────────────┘               └─────────────┘
        │                                  │
        │ nginx                    JWT Filter + Security
        │ try_files → index.html   STATELESS session
```

Frontend je Angular SPA servovaný cez nginx. Každý HTTP request na backend obsahuje JWT token v `Authorization: Bearer` hlavičke. Backend validuje token, overí rolu používateľa a vráti odpoveď.

---

## Používateľské roly

| Rola | Popis |
|------|-------|
| `ADMIN` | Plný prístup — správa hotelov, izieb, používateľov a ich rolí |
| `MANAGER` | Správa izieb a rezervácií len pre vlastné hotely |
| `USER` | Prezeranie hotelov, izieb a správa vlastných rezervácií |

---

## Spustenie cez Docker

### Požiadavky
- Docker Desktop (alebo Docker Engine + Docker Compose)

### Postup

```bash
# 1. Klonovanie repozitára
git clone <url-repozitara>
cd hotel-manager

# 2. Spustenie všetkých služieb
docker compose up --build -d

# 3. Počkaj ~30 sekúnd kým nabehnú všetky kontajnery
```

### Dostupné služby po spustení

| Služba | URL |
|--------|-----|
| Frontend | http://localhost:4200 |
| Backend API | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger-ui/index.html |
| phpMyAdmin | http://localhost:8081 |

### Zastavenie

```bash
docker compose down
```

### Vymazanie databázy a nový štart

```bash
docker compose down -v
docker compose up --build -d
```

---

## Spustenie lokálne

### Požiadavky
- Java 21+
- Node.js 20+
- Maven 3.9+
- MySQL 8.0 (alebo cez Docker)

### Backend

```bash
# Spusti len MySQL cez Docker
docker compose up mysql -d

# Spusti backend z IntelliJ IDEA alebo:
cd backend
./mvnw spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm start
```

Frontend beží na `http://localhost:4200`, backend na `http://localhost:8080`.

---

## Testovacie prihlasovacie údaje

Všetky účty majú heslo: **`heslo`**

| Email | Rola | Popis |
|-------|------|-------|
| admin@gmail.com | ADMIN | Plný prístup |
| manager1@gmail.com | MANAGER | Spravuje Grand Hotel BB a City Boutique Hotel BA |
| manager2@gmail.com | MANAGER | Spravuje Alpine Resort Jasná |
| user1@gmail.com | USER | Bežný používateľ |
| user2@gmail.com | USER | Bežný používateľ |

Alternatívne sa môžeš prihlásiť cez **Google OAuth2** tlačidlom na prihlasovacej stránke.

---

## API dokumentácia

Po spustení aplikácie je Swagger UI dostupný na:

```
http://localhost:8080/swagger-ui/index.html
```

### Prehľad endpointov

| Endpoint | Metóda | Popis | Prístup |
|----------|--------|-------|---------|
| `/auth/login` | POST | Prihlásenie emailom a heslom | Verejný |
| `/auth/me` | GET | Info o prihlásenom používateľovi | Prihlásený |
| `/api/hotely` | GET | Zoznam všetkých hotelov | Verejný |
| `/api/hotely/{id}` | GET/POST/PUT/DELETE | CRUD operácie s hotelom | ADMIN |
| `/api/izby` | GET | Zoznam izieb | Verejný |
| `/api/izby/hotel/{hotelId}` | GET | Izby konkrétneho hotela | Verejný |
| `/api/izby/{id}` | POST/PUT/DELETE | CRUD operácie s izbou | ADMIN/MANAGER |
| `/api/rezervacie` | POST | Vytvorenie rezervácie | Prihlásený |
| `/api/rezervacie/user/{userId}` | GET | Rezervácie používateľa | Prihlásený |
| `/api/rezervacie/hotel/{hotelId}` | GET | Rezervácie hotela | Prihlásený |
| `/api/rezervacie/{id}` | DELETE | Zrušenie rezervácie | Prihlásený |
| `/api/admin/pouzivatelia` | GET | Zoznam používateľov | ADMIN |
| `/api/admin/pouzivatelia/{id}/rola` | PUT | Zmena roly používateľa | ADMIN |
| `/api/admin/pouzivatelia/{id}/hotely` | PUT | Priradenie hotelov managerovi | ADMIN |

---

## Štruktúra projektu

```
hotel-manager/
├── backend/
│   └── src/main/java/sk/umb/hotelmanager/
│       ├── config/          # SecurityConfig
│       ├── controller/      # REST kontrolery
│       ├── dto/             # Data Transfer Objects
│       ├── entity/          # JPA entity (User, Hotel, Room, Booking)
│       ├── enums/           # Role, RoomType, RoomStatus, BookingStatus
│       ├── repository/      # Spring Data JPA repozitáre
│       ├── security/        # JWT filter, OAuth2 handler
│       └── service/         # Business logika
├── frontend/
│   └── src/app/
│       ├── auth/            # Login, Guard, Interceptor, OAuth2 callback
│       ├── features/
│       │   ├── hotels/      # Hotely — zoznam, detail, formulár
│       │   ├── rooms/       # Izby — zoznam, formulár
│       │   ├── booking/     # Rezervácie — zoznam, formulár
│       │   └── admin/       # Správa používateľov
│       └── layout/          # Navbar, Sidebar, Dashboard layout
├── docker-compose.yml
└── README.md
```

---

## CI/CD pipeline

Projekt má nastavený GitLab CI/CD pipeline (`.gitlab-ci.yml`) s dvoma fázami:

- **build** — builduje backend (Maven) aj frontend (npm)
- **test** — spúšťa testy

Pipeline sa spúšťa automaticky pri každom `git push` do repozitára.

---

## Databázová schéma

Hlavné tabuľky:

- `users` — používatelia s rolami (ADMIN, MANAGER, USER)
- `hotels` — hotely s referencou na managera
- `rooms` — izby patriace hotelom
- `bookings` — rezervácie prepájajúce usera s izbou