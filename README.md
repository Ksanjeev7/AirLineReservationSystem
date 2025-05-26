# ✈️ Airline Reservation System - Spring Boot

This is a role-based Airline Reservation System built with **Spring Boot**, **Spring Security**, **JWT**, **MySQL**, and **JPA/Hibernate**.

---

## ✅ Features

### 👤 User (ROLE\_USER)

* Register, Login (JWT token-based)
* Search available flights (filter by source, destination, date)
* Book flights
* View and cancel own bookings

### 🛡️ Admin (ROLE\_ADMIN)

* Create, update, delete flights
* View all flights and bookings
* Protected access via Spring Security + JWT

---

## 🔐 Role-Based Access Control (RBAC)

| Endpoint           | Role         | Description                      |
| ------------------ | ------------ | -------------------------------- |
| `/api/auth/**`     | Public       | Register & Login                 |
| `/api/flights/**`  | `ROLE_ADMIN` | Create, update, delete flights   |
| `/api/bookings/**` | `ROLE_USER`  | Book, view, cancel user bookings |

---

## 🏗️ Tech Stack

* Java 17+
* Spring Boot 3+
* Spring Security + JWT
* Spring Data JPA + Hibernate
* MySQL
* Lombok

---

## ⚙️ Setup

1. **Clone the repo**:

```bash
git clone https://github.com/your-username/airline-reservation-system.git
```

2. **Create** `application.properties` in:

```
src/main/resources/application.properties
```

3. **Sample** `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/airline
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=your-256-bit-secret-here
```

4. **Run the project**:

```bash
mvn spring-boot:run
```

---

## 🔑 Sample Roles for JWT

```json
{
  "username": "admin",
  "role": "ROLE_ADMIN"
}

{
  "username": "user1",
  "role": "ROLE_USER"
}
```

---

## 📫 Endpoints

### 🔐 Auth

* `POST /api/auth/register`
* `POST /api/auth/login`

### 🛡️ Flights (Admin Only)

* `POST /api/flights/create`
* `PUT /api/flights/{id}`
* `DELETE /api/flights/{id}`
* `GET /api/flights/all`
* `GET /api/flights/search?source=A&destination=B`

### 👤 Bookings (User Only)

* `GET /api/bookings/available?source=A&destination=B&date=YYYY-MM-DD`
* `POST /api/bookings/book`
* `GET /api/bookings/my`
* `DELETE /api/bookings/cancel/{bookingId}`

---

## 🔄 Postman Collections

You can import these collections individually depending on the feature set:

* 👉 [Auth Collection](docs/postman-auth-collection.json)
* 👉 [Admin Flight Management](docs/postman-admin-flights-collection.json)
* 👉 [User Booking Operations](docs/postman-user-bookings-collection.json)

> Each collection includes fully tested endpoints, sample request bodies, and token examples.

---

## 🙌 Contributing

Feel free to fork, improve, or open issues!

---
