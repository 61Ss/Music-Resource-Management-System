# Music Resource Management System

A full-stack **Music Resource Management System** project with a separated **backend** and **frontend** codebase.

- **Backend:** `Source_code_backend/`
- **Frontend:** `Source_code_frontend/`

> Repository languages shown by GitHub include JavaScript, Vue, Java, CSS, HTML, and SCSS.  
> (You can refine the stack details below after you confirm the exact backend framework / frontend build tool.)  

---

## Table of Contents

- [Project Overview](#project-overview)
- [Repository Structure](#repository-structure)
- [Requirements](#requirements)
- [Quick Start](#quick-start)
  - [1) Backend](#1-backend)
  - [2) Frontend](#2-frontend)
- [Configuration](#configuration)
- [Database](#database)
- [Build & Deployment](#build--deployment)
---

## Project Overview

This project is designed to manage music-related resources in a structured way (e.g., content entries, categories, users/admin roles, and related assets), with:

- A backend service to provide APIs and business logic
- A frontend web application to provide UI pages and interactions
- (Optional) A database for persistence (if your backend uses one)

> If you want this README to be 100% accurate (e.g., exact API routes, database schema, default accounts), add a short “Project Features” list or screenshots and I’ll tailor it precisely.

---

## Repository Structure

```text
Music-Resource-Management-System/
├─ Source_code_backend/        # Backend source code
└─ Source_code_frontend/       # Frontend source code
````

---

## Requirements

### Backend (typical)

* JDK (e.g., Java 8/11/17) — depending on your backend project configuration
* Maven / Gradle — depending on your backend build tool
* Database (optional) — if your backend persists data (e.g., MySQL)

### Frontend (typical)

* Node.js (recommended: Node 16+)
* npm / yarn / pnpm (whichever your frontend uses)

---

## Quick Start

### 1) Backend

1. Open a terminal and go to the backend folder:

```bash
cd Source_code_backend
```

2. Start the backend (choose the one that matches your project):

**Option A — IDE run**

* Open the backend project in IntelliJ IDEA / Eclipse
* Run the main application entry (e.g., `Application.java` / `Main.java`)

**Option B — Maven**

```bash
mvn clean package
mvn spring-boot:run
```

**Option C — Gradle**

```bash
./gradlew build
./gradlew bootRun
```

> ✅ If you are not using Spring Boot, remove the `spring-boot:run` / `bootRun` commands and replace them with the correct run instructions.

---

### 2) Frontend

1. Open a new terminal and go to the frontend folder:

```bash
cd Source_code_frontend
```

2. Install dependencies:

```bash
npm install
```

3. Run the development server:

```bash
npm run dev
```

4. Build for production:

```bash
npm run build
```

> If your frontend uses `serve`/`start` instead of `dev`, update the scripts above accordingly.

---

## Configuration

### Backend config

Look for configuration files in your backend such as:

* `application.yml` / `application.properties`
* environment variables
* `config/` folder

Common things to configure:

* Server port (e.g., `8080`)
* Database connection (`host`, `port`, `username`, `password`, `database`)
* CORS settings (frontend origin)
* Upload/static file directory (if you store assets locally)

### Frontend config

Typical places:

* `.env`, `.env.development`, `.env.production`
* `src/config/*`
* Axios/fetch base URL setup

Common things to configure:

* API base URL (e.g., `http://localhost:8080`)
* Public path / routing base
* Build output directory

---

## Database

If your backend requires a database:

1. Create a local database (example: MySQL)
2. Import any provided SQL initialization script (if your backend folder includes one)
3. Update backend configuration to point to your local DB
4. Start backend and verify DB connectivity

> If you tell me your DB name + the SQL filename (if any), I can write an exact “Database Setup” section with copy-paste commands.

---

## Build & Deployment

### Recommended local deployment flow

1. Start database (if needed)
2. Start backend
3. Start frontend

### Production deployment (typical approach)

* Frontend: build static files (`npm run build`) and deploy with Nginx / CDN / static hosting
* Backend: package into a JAR/WAR (or containerize with Docker) and deploy on a server
* Set environment variables for production DB and API domain
