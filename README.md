# Wedding RSVP Spring Boot REST API

A production-ready, standalone Spring Boot REST API backend specifically architected for a React wedding invitation website. It captures guest RSVPs, stores records in PostgreSQL using Flyway migrations, and provides clean APIs for the `/wedding-response` admin dashboard without requiring authentication.

---

## 1. Requirements

* **Java**: OpenJDK 17 or higher
* **Build Tool**: Apache Maven 3.8+
* **Database**: PostgreSQL 13+
* **Environment**: Linux, macOS, or Windows with Bash/PowerShell

---

## 2. Technology Stack & Architecture

* **Framework**: Spring Boot 3.3.4
* **Web**: Spring Web (`spring-boot-starter-web`)
* **Persistence**: Spring Data JPA (`spring-boot-starter-data-jpa`) with Hibernate
* **Database Migrations**: Flyway (`flyway-core`, `flyway-database-postgresql`)
* **Validation**: Jakarta Bean Validation (`spring-boot-starter-validation`)
* **Database**: PostgreSQL
* **Testing**: JUnit 5, Mockito, Spring Boot Starter Test, in-memory H2 (test profile)

### Layered Architecture
```text
React Wedding Website / Dashboard
               │
               ▼
       Controller Layer  (RsvpController, HealthController)
               │
               ▼
        Service Layer    (RsvpService - validation & business logic)
               │
               ▼
       Repository Layer  (RsvpResponseRepository - Spring Data JPA)
               │
               ▼
      PostgreSQL Database (rsvp_responses table via Flyway V1)
```

---

## 3. Database Setup

### Create PostgreSQL Database and User
Log in to PostgreSQL using `psql`:

```bash
psql -U postgres
```

Run the following SQL commands to create the database:

```sql
CREATE DATABASE wedding_db;

-- Optional: Create dedicated user if not using postgres superuser
CREATE USER wedding_user WITH ENCRYPTED PASSWORD 'wedding_secure_password';
GRANT ALL PRIVILEGES ON DATABASE wedding_db TO wedding_user;
```

> **Note on Flyway:** You do not need to run `CREATE TABLE` manually. When Spring Boot starts, Flyway will automatically execute migration script `V1__create_rsvp_responses.sql` to create the `rsvp_responses` table and index.

---

## 4. Environment Variables

Configure the following environment variables prior to running the application. You can export them directly or create a `.env` file (refer to `.env.example`).

| Variable | Description | Default | Example |
| :--- | :--- | :--- | :--- |
| `PORT` | HTTP port on which the server listens | `8080` | `8080` |
| `DB_URL` | PostgreSQL JDBC connection URL | `jdbc:postgresql://localhost:5432/wedding_db` | `jdbc:postgresql://localhost:5432/wedding_db` |
| `DB_USERNAME` | PostgreSQL username | `postgres` | `wedding_user` |
| `DB_PASSWORD` | PostgreSQL password | `postgres` | `wedding_secure_password` |
| `FRONTEND_URL`| Allowed frontend origin(s) for CORS | `http://localhost:5173` | `http://localhost:5173,https://mywedding.com` |

---

## 5. Running the Backend

### Build the Project
```bash
mvn clean package
```

### Run Automated Tests
```bash
mvn test
```

### Start the Application with Maven
```bash
# Option A: Passing environment variables inline
DB_URL="jdbc:postgresql://localhost:5432/wedding_db" \
DB_USERNAME="postgres" \
DB_PASSWORD="your_password" \
FRONTEND_URL="http://localhost:5173" \
mvn spring-boot:run

# Option B: After exporting environment variables
export DB_URL="jdbc:postgresql://localhost:5432/wedding_db"
export DB_USERNAME="postgres"
export DB_PASSWORD="your_password"
export FRONTEND_URL="http://localhost:5173"
mvn spring-boot:run
```

### Run Executable JAR Directly
```bash
java -jar target/wedding-rsvp-api-1.0.0.jar
```

---

## 6. Form Fields & Schema Mapping

The backend strictly matches the React RSVP form fields:

| React Form Field Label | Backend Field | Java Type | PostgreSQL Column | Constraints |
| :--- | :--- | :--- | :--- | :--- |
| `FULL NAME · পূর্ণ নাম` | `fullName` | `String` | `full_name VARCHAR(100)` | Required, Non-blank, Max 100 |
| `PHONE NUMBER · ফোন নম্বর` | `phoneNumber`| `String` | `phone_number VARCHAR(30)` | Required, String format (supports `+91`) |
| `WILL YOU ATTEND? · আপনার উপস্থিতি` | `attendance` | `AttendanceStatus` | `attendance VARCHAR(30)` | Required Enum (`JOYFULLY_ACCEPT`, `REGRETFULLY_DECLINE`) |
| `BLESSINGS & MESSAGE · যুগলের উদ্দেশ্য আশীর্বাদ ও বার্তা` | `message` | `String` | `message TEXT` | Optional, Max 1000 characters |
| *(Server-generated)* | `submittedAt` | `LocalDateTime` | `submitted_at TIMESTAMP` | Automatically set on save |

> **Strict Rule:** There are **NO** `email`, `guest_count`, or `number_of_guests` fields.

---

## 7. API Endpoints Documentation

### 1. Health Check
* **Method**: `GET`
* **Path**: `/api/health`
* **Response Status**: `200 OK`
* **Response Body**:
```json
{
  "status": "UP"
}
```

---

### 2. Submit Guest RSVP
* **Method**: `POST`
* **Path**: `/api/rsvp`
* **Headers**: `Content-Type: application/json`
* **Request Body**:
```json
{
  "fullName": "Subhashish Mukherjee",
  "phoneNumber": "+919830000000",
  "attendance": "JOYFULLY_ACCEPT",
  "message": "Wishing you both a beautiful life together!"
}
```
* **Success Response Status**: `201 Created`
* **Success Response Body**:
```json
{
  "success": true,
  "message": "RSVP submitted successfully"
}
```
> **Note:** As specified, the guest submission endpoint does **not** echo back guest records; it returns this clean success acknowledgment so your React website can trigger its success animation.

* **Validation Error Response Status**: `400 Bad Request`
* **Validation Error Response Body**:
```json
{
  "success": false,
  "message": "Full name is required"
}
```

---

### 3. Get All RSVP Responses
* **Method**: `GET`
* **Path**: `/api/rsvp`
* **Description**: Returns all RSVP submissions sorted by `submittedAt DESC` (newest first). Used by the `/wedding-response` dashboard.
* **Success Response Status**: `200 OK`
* **Success Response Body**:
```json
[
  {
    "id": 5,
    "fullName": "Subhashish Mukherjee",
    "phoneNumber": "+919830000000",
    "attendance": "JOYFULLY_ACCEPT",
    "message": "Wishing you both a beautiful life together!",
    "submittedAt": "2026-10-03T20:42:15"
  },
  {
    "id": 4,
    "fullName": "Example Guest",
    "phoneNumber": "+919812345678",
    "attendance": "REGRETFULLY_DECLINE",
    "message": "Congratulations to you both.",
    "submittedAt": "2026-10-03T18:20:10"
  }
]
```

---

### 4. Get Individual RSVP by ID
* **Method**: `GET`
* **Path**: `/api/rsvp/{id}`
* **Success Response Status**: `200 OK`
* **Success Response Body**:
```json
{
  "id": 5,
  "fullName": "Subhashish Mukherjee",
  "phoneNumber": "+919830000000",
  "attendance": "JOYFULLY_ACCEPT",
  "message": "Wishing you both a beautiful life together!",
  "submittedAt": "2026-10-03T20:42:15"
}
```
* **Not Found Response Status**: `404 Not Found`
* **Not Found Response Body**:
```json
{
  "success": false,
  "message": "RSVP response not found with ID: 5"
}
```

---

### 5. Delete RSVP by ID
* **Method**: `DELETE`
* **Path**: `/api/rsvp/{id}`
* **Description**: Deletes an RSVP response. Used by the response dashboard to remove duplicate or invalid entries.
* **Success Response Status**: `200 OK`
* **Success Response Body**:
```json
{
  "success": true,
  "message": "RSVP response deleted successfully"
}
```
* **Not Found Response Status**: `404 Not Found`
* **Not Found Response Body**:
```json
{
  "success": false,
  "message": "RSVP response not found with ID: 99"
}
```

---

## 8. React Integration Examples

### Submitting RSVP from React Form
```javascript
async function submitRsvp(formData) {
  const response = await fetch(`${BACKEND_URL}/api/rsvp`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      fullName: formData.fullName,
      phoneNumber: formData.phoneNumber,
      attendance: formData.willAttend ? 'JOYFULLY_ACCEPT' : 'REGRETFULLY_DECLINE',
      message: formData.message || null,
    }),
  });

  const result = await response.json();
  if (!response.ok || !result.success) {
    throw new Error(result.message || 'Submission failed');
  }

  // Trigger existing React celebration / success animation
  return result;
}
```

### Fetching & Calculating Dashboard Stats on `/wedding-response`
```javascript
async function loadWeddingResponses() {
  const response = await fetch(`${BACKEND_URL}/api/rsvp`);
  const data = await response.json();

  const totalResponses = data.length;
  const joyfullyAcceptCount = data.filter(r => r.attendance === 'JOYFULLY_ACCEPT').length;
  const regretfullyDeclineCount = data.filter(r => r.attendance === 'REGRETFULLY_DECLINE').length;

  return {
    responses: data,
    stats: {
      total: totalResponses,
      accepted: joyfullyAcceptCount,
      declined: regretfullyDeclineCount,
    },
  };
}
```

---

## 9. Testing Strategy

The project includes automated integration and unit tests:
* `HealthControllerTest`: Verifies health endpoint.
* `RsvpControllerTest`: Full MockMvc integration tests for valid/invalid submissions, missing fields, enum errors, ordering, and deletion.
* `RsvpServiceTest`: Isolated unit tests using Mockito.

To run tests without needing a running PostgreSQL instance (uses in-memory H2 with PostgreSQL dialect compatibility):
```bash
mvn test
```
