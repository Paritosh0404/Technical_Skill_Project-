# 📚 Course Registration System  

A Java-based GUI application for course registration, enabling students to register, view, and withdraw from courses while allowing admins to manage course listings.  

## 🚀 Features  

- **Login System**: Role-based login for Admins and Students.  
- **Student Dashboard**:  
  - View available courses  
  - Register for a course  
  - Withdraw from a course  
  - View registered courses  
- **Admin Dashboard**:  
  - Create, edit, and delete courses  
  - View all registered courses  
- **Database Integration**: Uses MySQL for managing course registrations.  

## 🛠️ Tech Stack  

- **Language**: Java (Swing, AWT)  
- **Database**: MySQL  
- **Libraries**: JDBC for database connectivity  

## 📷 Screenshots  
![Picture1](https://github.com/user-attachments/assets/f4573928-5c7e-4178-9793-15d921492b49)
![Picture2](https://github.com/user-attachments/assets/038365a5-3ce9-4250-a416-845bdcb7da12)
![Picture3](https://github.com/user-attachments/assets/c17b75ed-4c65-41af-bbd1-ce239ce4aec2)
![Picture4](https://github.com/user-attachments/assets/736e889d-c34f-4b09-88b3-972404db5660)
![Picture5](https://github.com/user-attachments/assets/229a9292-2eb7-48ed-9fd2-f2a93ee9b27f)
![Picture6](https://github.com/user-attachments/assets/ee47c3da-a680-433c-9b2f-5c7c0562a9db)
![Picture7](https://github.com/user-attachments/assets/0d239aae-cb4c-4a9d-827d-7aede307d0bc)
![Picture8](https://github.com/user-attachments/assets/2218aa1e-b0c7-4fc1-84ab-0cd576682446)
![Picture9](https://github.com/user-attachments/assets/0af3474e-4b1a-4ac0-9d9f-285bded24af2)
![Picture10](https://github.com/user-attachments/assets/e665d81c-9316-44a9-a37e-7afe33131b4d)
![Picture11](https://github.com/user-attachments/assets/cd40c6a0-9bd5-493f-b92f-9c33fa5ce9e2)


## 📂 Project Structure  

```
/src  
  ├── LoginScreen.java  
  ├── StudentDashboard.java  
  ├── AdminDashboard.java  
  ├── CreateCourseFrame.java  
  ├── RegisterCourseFrame.java  
  ├── ViewCourseFrame.java  
  ├── DatabaseConnection.java  
  └── ... (other files)  
```  

## 🏗️ Setup Instructions  

### 1️⃣ Prerequisites  

- Install **Java (JDK 8 or later)**  
- Install **MySQL Server**  
- Add MySQL JDBC Driver to your project  

### 2️⃣ Database Setup  

1. Create a MySQL database:  
   ```sql
   CREATE DATABASE course_registration_system;
   ```  
2. Use the database:  
   ```sql
   USE course_registration_system;
   ```  
3. Create the necessary tables:  
   ```sql
   CREATE TABLE users (
       id INT AUTO_INCREMENT PRIMARY KEY,  
       username VARCHAR(50) NOT NULL UNIQUE,  
       password VARCHAR(50) NOT NULL,  
       role ENUM('Admin', 'Student') NOT NULL  
   );

   CREATE TABLE courses (
       course_id VARCHAR(10) PRIMARY KEY,  
       course_name VARCHAR(100) NOT NULL,  
       instructor VARCHAR(50),  
       location VARCHAR(50),  
       max_students INT,  
       section INT  
   );

   CREATE TABLE registrations (
       id INT AUTO_INCREMENT PRIMARY KEY,  
       username VARCHAR(50),  
       course_id VARCHAR(10),  
       FOREIGN KEY (username) REFERENCES users(username),  
       FOREIGN KEY (course_id) REFERENCES courses(course_id)  
   );
   ```  

### 3️⃣ Run the Project  

1. Clone this repository:  
2. Open the project in an IDE (e.g., IntelliJ IDEA, Eclipse, or NetBeans).  
3. Compile and run `LoginScreen.java`.  
