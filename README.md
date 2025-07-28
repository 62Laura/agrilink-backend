# AgriLink Backend

This is the backend API for the **AgriLink** platform — a web application that connects farmers and buyers.  
The backend is built with **Spring Boot** and handles user authentication, product listings, and data persistence.

---
## 🚀 Getting Started

### 🔗 Clone the Repository
```bash
git clone https://github.com/62Laura/agrilink-backend.git
cd agrilink-backend
```

---

## ⚙️ Configure the Database

Before running the application, open `src/main/resources/application.properties` and set the following properties:

```properties
spring.datasource.url=jdbc:postgresql://<your-host>:<port>/<your-db-name>
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
```

> 💡 Make sure PostgreSQL is installed and the database is created.

---

## ▶️ Run the Application

Use the Maven wrapper to start the server:
```bash
./mvnw spring-boot:run
```

Once started, the API will run on:
```
http://localhost:8080
```

---

## 🌐 Deployment

This backend is deployed on **Railway**.  
🔗 [https://agrilink-backend-production.up.railway.app](https://agrilink-backend-production.up.railway.app)

Ensure CORS and frontend URL settings are properly configured for production use.

---

## 📦 Tech Stack

- Java 17+
- Spring Boot
- PostgreSQL
- Maven
- Railway (Deployment)
