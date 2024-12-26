import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class App {
    private static final String URL = "jdbc:mysql://localhost:3306/studentinfo";
    private static final String USERNAME = "root"; // Replace with your MySQL username
    private static final String PASSWORD = "112233"; // Replace with your MySQL password

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int choice;

        System.out.println("== Ledger System == ");

        do {
            System.out.print("Login or Register \n1. Login \n2. Register \n3. Exit \n->");
            choice = input.nextInt();
            input.nextLine();  // Consume newline character
            // Login page
            if (choice == 1) {
                loginUser(input);
            }
            // Register page
            else if (choice == 2) {
                registerUser(input);
            }
            else if (choice == 3){
                break; 
            }
        } while (choice != 1);

        input.close();  // Close the scanner object
    }

    // Method to register user
    private static void registerUser(Scanner scanner) {
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();


        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        // Hash password for security
        String hashedPassword = hashPassword(password);

        String insertQuery = "INSERT INTO student (username, password) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            // Set parameters for the query
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Registration successful!");
            } else {
                System.out.println("Registration failed.");
            }

        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                System.out.println("Error: Username already exists.");
            } else {
                e.printStackTrace();
            }
        }
    }

    // Method to log in a user
    private static void loginUser(Scanner scanner) {
        boolean success = false;
        while (!success) {
            System.out.print("Enter your username (Enter ! to escape): ");
            String username = scanner.nextLine();

            if (username.equals("!")){
                break; 
            }

            else{
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            // Hash the entered password to compare with the stored hash
            String hashedPassword = hashPassword(password);

            String selectQuery = "SELECT * FROM student WHERE username = ? AND password = ?";

            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

                // Set parameters for the query
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, hashedPassword);

                // Execute the query
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    System.out.println("Login successful! Welcome, " + username + "!");
                    success = true; // Login successful, exit loop
                } else {
                    System.out.println("Invalid username or password. Please try again.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        }
    }

    // Hash the password using SHA-256 
    private static String hashPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = messageDigest.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
