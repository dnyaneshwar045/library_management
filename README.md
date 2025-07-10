# 📚 Library Management System (Java Swing + MySQL)

This is a simple **Library Management System** built using **Java Swing** for the GUI and **MySQL** for the database backend. It supports adding, viewing, editing, and deleting book records from a database.

---

## ✅ Features

* Add, view, edit, and delete books
* Stylish and responsive GUI
* Book image displayed inside the Book Details panel
* Clear form fields and exit option

---

## 🖥️ Technologies Used

* Java (Swing)
* MySQL (Database)
* JDBC (Java Database Connectivity)

---

## 🗃️ Database Setup

1. Open MySQL and create the database and table:

```sql
CREATE DATABASE library;
USE library;

CREATE TABLE books (
    book_id VARCHAR(20) PRIMARY KEY,
    title VARCHAR(100),
    author VARCHAR(100),
    publisher VARCHAR(100),
    year_of_publication INT,
    isbn VARCHAR(20),
    number_of_copies INT
);
```

---

## 🧩 Files Included

```
LibraryManagement/
├── LibraryManagement.java    // Main GUI & logic
├── DBConnection.java         // Handles MySQL connection
├── book.png                  // Book image used in UI
├── README.md                 // Project instructions
```

---

## 🔌 DBConnection.java (Sample)

```java
import java.sql.*;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/library";
        String user = "root";          // your MySQL username
        String password = "password";  // your MySQL password
        return DriverManager.getConnection(url, user, password);
    }
}
```

---

## ▶️ How to Run

### Compile:

```bash
javac LibraryManagement.java DBConnection.java
```

### Run:

```bash
java LibraryManagement
```

Make sure:

* MySQL is running
* The `library` database and `books` table exist
* `book.png` is in the same folder as `.java` files
* JDBC driver (e.g., `mysql-connector-java.jar`) is in your classpath

---

## 🧰 Adding JDBC Driver

If you're running from the terminal:

```bash
javac -cp .;mysql-connector-java.jar LibraryManagement.java DBConnection.java
java -cp .;mysql-connector-java.jar LibraryManagement
```

For IDEs like IntelliJ or Eclipse:

* Right-click your project → Add Library → External JAR → Add `mysql-connector-java.jar`

---

## 🙋‍♂️ Developer

Developed by **\[Your Name]**

Feel free to contribute, enhance the UI, or integrate with other modules.

---

Would you like me to package this whole project into a `.zip` or prepare a `.bat` file to auto-run the app on double-click?
