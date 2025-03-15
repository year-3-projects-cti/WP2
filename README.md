# Private School CRM System

## Team Members
PRODAN Florin Mihai Alexandru - florin_mihai.prodan@stud.fils.upb.ro  
DUMITRU Vlad Andrei - vlad.dumitru1006@stud.fils.upb.ro  

## Project Description
This project is a Customer Relationship Management (CRM) system specifically designed for private schools. The platform helps manage interactions between parents, students, and administrators, ensuring smooth communication and organization.

## Key Features
- **Multiple User Roles**: Parents, Students, and Admins have distinct functionalities.
- **Timetable Management**: A detailed schedule for classes and activities.
- **User Relationships**: Families are linked, allowing better interaction between parents and students.
- **Course Management**: Users can enroll in courses and track progress.
- **Automated Google Meet Integration**: A meeting link is generated for each scheduled class.
- **Payment System**: A token-based system is used for transactions.

## Technologies Used
### Backend
- **Spring Boot** (Java 21) - for API and business logic  
- **Spring Security** - for authentication and authorization  
- **Hibernate (JPA)** - for ORM and database interactions  
- **MariaDB** - as the relational database  
- **Docker** - for containerized deployment  

### Frontend
- **Vue.js 3** - for the user interface  
- **Vite** - for fast development and build process  

### DevOps & Deployment
- **Docker Compose** - for managing multi-container setup  
- **HikariCP** - for efficient database connection pooling  

## Running the Application
### Prerequisites
- Install **Docker** and **Docker Compose** on your system.

### Start the Application
1. Open a terminal in the project root directory.
2. Run the following command:
   ```sh
   docker compose up
   ```
   This command will:
   - Build and start the **MariaDB** database container.
   - Build and start the **Spring Boot** backend.
   - Build and start the **Vue.js** frontend.

3. Access the application:
   - **Backend API**: `http://localhost:8080/api`
   - **Frontend UI**: `http://localhost:3000`

### Stop the Application
To stop all containers, run:
```sh
docker compose down
```
