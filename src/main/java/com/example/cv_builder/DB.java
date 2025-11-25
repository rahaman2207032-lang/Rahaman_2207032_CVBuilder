package com.example.cv_builder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DB {
    private static final String DB_URL = "jdbc:sqlite:cvbuilder.db";
    private static Connection connection;

    private static final String CREATE_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "email TEXT NOT NULL," +
                    "phone TEXT NOT NULL," +
                    "address TEXT NOT NULL," +
                    "education TEXT NOT NULL," +
                    "skills TEXT NOT NULL," +
                    "work_experience TEXT NOT NULL," +
                    "projects TEXT NOT NULL" +
                    ");";


    public static void initDatabase() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL);
                createTable();
                System.out.println("✅ Database initialized successfully!");
            }
        } catch (SQLException e) {
            System.err.println("❌ Database initialization failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void createTable() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_QUERY)) {
            statement.executeUpdate();
            System.out.println("✅ Table created/verified successfully!");
        } catch (SQLException e) {
            System.err.println("❌ Table creation failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
        }
        return connection;
    }

    public static boolean updateCV(int id, CV_INFORMATION cv) {
        String updateQuery = """
        UPDATE users SET 
            name = ?, 
            email = ?, 
            phone = ?, 
            address = ?, 
            education = ?, 
            skills = ?, 
            work_experience = ?, 
            projects = ?
        WHERE id = ?
    """;

        try (PreparedStatement pstmt = getConnection().prepareStatement(updateQuery)) {
            pstmt.setString(1, cv.getName());
            pstmt.setString(2, cv.getEmail());
            pstmt.setString(3, cv.getPhone());
            pstmt.setString(4, cv.getAddress());
            pstmt.setString(5, cv.getEd_Qualification());
            pstmt.setString(6, cv.getSkills());
            pstmt.setString(7, cv.getWork_experience());
            pstmt.setString(8, cv.getProjects());
            pstmt.setInt(9, id);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("✅ CV updated successfully (ID: " + id + ")");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Failed to update CV: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public static int saveCV(CV_INFORMATION cv) {
        String insertQuery = "INSERT INTO users (name, email, phone, address, education, skills, work_experience, projects) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = getConnection().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, cv.getName());
            pstmt.setString(2, cv.getEmail());
            pstmt.setString(3, cv.getPhone());
            pstmt.setString(4, cv.getAddress());
            pstmt.setString(5, cv.getEd_Qualification());
            pstmt.setString(6, cv.getSkills());
            pstmt.setString(7, cv.getWork_experience());
            pstmt.setString(8, cv.getProjects());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        System.out.println("✅ CV saved successfully with ID: " + id);
                        return id;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Failed to save CV: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }


    public static List<CV_INFORMATION> getAllCVs() {
        List<CV_INFORMATION> cvList = new ArrayList<>();
        String query = "SELECT * FROM users ORDER BY id DESC";

        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                CV_INFORMATION cv = new CV_INFORMATION(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("education"),
                        rs.getString("skills"),
                        rs.getString("work_experience"),
                        rs.getString("projects")
                );
                cvList.add(cv);
            }
            System.out.println("✅ Retrieved " + cvList.size() + " CVs from database");
        } catch (SQLException e) {
            System.err.println("❌ Failed to retrieve CVs: " + e.getMessage());
            e.printStackTrace();
        }
        return cvList;
    }


    public static boolean deleteCV(int id) {
        String deleteQuery = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement pstmt = getConnection().prepareStatement(deleteQuery)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✅ CV deleted successfully");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Failed to delete CV: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }


    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✅ Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("❌ Failed to close connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static CV_INFORMATION getCVById(int id) {
        String query = "SELECT * FROM users WHERE id = ?";

        try (PreparedStatement pstmt = getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("✅ CV found with ID: " + id);
                    return new CV_INFORMATION(
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("address"),
                            rs.getString("education"),
                            rs.getString("skills"),
                            rs.getString("work_experience"),
                            rs.getString("projects")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Failed to get CV by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}