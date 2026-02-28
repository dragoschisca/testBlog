# Blog Application

Aceasta este aplicația **Blog** construită cu Spring Boot, care rulează într-un container Docker și folosește PostgreSQL ca bază de date.

---

## 🛠️ Cerințe

- Docker și Docker Compose instalate pe sistem
- Maven (sau Gradle) pentru build local Java
- Cont Docker Hub (dacă vrei să împingi imaginea)

---

## 1️⃣ Structura proiectului
-Blog/
--- 
- ├─ src/ # cod sursă Spring Boot
- ├─ target/ # build Maven/Gradle (conține jar-ul)
- ├─ Dockerfile # imagine aplicație
- ├─ docker-compose.yml # pentru DB + aplicație
- └─ src/main/resources/application.properties


---

## 2️⃣ Fișierul `application.properties`

Acesta configurează baza de date și Spring Security:

```properties
spring.application.name=blog
spring.datasource.url=jdbc:postgresql://db:5432/blogdb
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.security.user.name=admin
spring.security.user.password=admin
  ``` 

## 3️⃣ Dockerfile
```properties
#Imagine de bază cu Java 17 
FROM eclipse-temurin:17-jdk

# Setăm folderul de lucru
WORKDIR /app

# Copiem jar-ul construit în container
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expunem portul aplicației
EXPOSE 8080

# Comanda de rulare a aplicației
ENTRYPOINT ["java", "-jar", "app.jar"]

```
## 4️⃣ Docker Compose

Fișierul docker-compose.yml pentru rularea aplicației + PostgreSQL:

```properties
version: '3.8'

services:
  db:
    image: postgres:15
    container_name: blog-db
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: blogdb
    ports:
      - "5432:5432"
    volumes:
      - blog-db-data:/var/lib/postgresql/data

  app:
    build: .
    image: blog-app:1.0
    container_name: blog-app
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/blogdb
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: postgres
      SPRING_SECURITY_USER_NAME: admin
      SPRING_SECURITY_USER_PASSWORD: admin
    depends_on:
      - db

volumes:
  blog-db-data:
```
## 5️⃣ Build aplicație

Din directorul rădăcină al proiectului rulăm în Terminal:

```properties
mvn clean package
```
Vei obține target/demo-0.0.1-SNAPSHOT.jar.

### 6️⃣ Construirea imaginii Docker

```properties

docker build -t blog-app:1.0 .
```

Test rapid fără Docker Compose:
```properties
docker run -p 8080:8080 blog-app:1.0
```
Dacă apare eroare legată de DB, folosește Docker Compose pentru a porni și containerul PostgreSQL.

## 7️⃣ Rularea aplicației cu Docker Compose

Pornire:

```properties
docker-compose up --build
```

Acces aplicație:
```properties
http://localhost:8080
```
Login Spring Security:
```properties
Username: admin
Password: admin
```
## 8️⃣ Oprirea aplicației

În foreground:
```properties
Ctrl + C
```

Volumele DB rămân, deci datele nu se pierd.

## 9️⃣ Încărcarea imaginii pe Docker Hub

Logare:

```properties
docker login
```

Tag imagine:
```properties
docker tag blog-app:1.0 yourdockerhubusername/blog-app:1.0
```

Push pe Docker Hub:
```properties
docker push yourdockerhubusername/blog-app:1.0
```

Imaginea este acum disponibilă pe Docker Hub.

## Note importante

- Trebuie să ne asigurăm că porturile 8080 (aplicația Java) și 5432 (PostgreSQL) sunt libere pe host.

- Dacă este necesar se pot modifica porturi și useri DB în docker-compose.yml și application.properties.

- Folosește docker-compose logs -f pentru a urmări logurile aplicației sau DB.
