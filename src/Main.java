import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/studentmanagementsystem";
    private static final String Username = "root";
    private static final String Password ="bharatmarwah2804";

    public Main() {
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver not found: " + e.getMessage());
        }

        try (Connection connection = DriverManager.getConnection(url, Username, Password)) {
            try (Scanner sc = new Scanner(System.in)) {
                while (true) {
                    System.out.println("\n==== STUDENT MANAGEMENT SYSTEM ====");
                    System.out.println("1) ADD STUDENT");
                    System.out.println("2) VIEW STUDENT ");
                    System.out.println("3) DELETE STUDENT ");
                    System.out.println("4) EXIT");
                    System.out.print("ENTER THE CHOICE: ");

                    try {
                        String input = sc.nextLine();
                        int choice = Integer.parseInt(input);
                        switch (choice) {
                            case 1:
                                addStudents(sc, connection);
                                break;
                            case 2:
                                viewstudent(connection);
                                break;
                            case 3:
                                deletestudent(sc, connection);
                                break;
                            case 4:
                                exit();
                                return;
                            default:
                                System.out.println("Invalid choice.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid number..");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }

    private static void addStudents(Scanner sc, Connection connection) {
        try {
            while (true) {
                System.out.print("NAME : ");
                String name = sc.nextLine();
                System.out.print("EMAIL : ");
                String email = sc.nextLine();
                System.out.print("COURSE : ");
                String course = sc.nextLine();
                String query = "INSERT INTO student(name, email, course) VALUES (?, ?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, email);
                    preparedStatement.setString(3, course);
                    int rows = preparedStatement.executeUpdate();
                    System.out.println(rows + " student added successfully.");
                }

                System.out.println("MORE(Y/N)");
                String yn = sc.next();
                if (yn.toUpperCase().equals("N")) {
                    break;
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to add student: " + e.getMessage());
        }
    }

    private static void viewstudent(Connection conn) {
        String query = "SELECT * FROM student";

        try (
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String course = resultSet.getString("course");
                System.out.println("-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-");
                System.out.println("NAME :" + name);
                System.out.println("EMAIL :" + email);
                System.out.println("COURSE :" + course);
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void deletestudent(Scanner sc, Connection connection) {
        System.out.println("ENTER THE NAME OF THE STUDENT YOU WANT REMOVE...");
        String nametobedel = sc.nextLine();
        String query = "DELETE FROM student WHERE name=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nametobedel);
            int rowaffected = preparedStatement.executeUpdate();
            if (rowaffected > 0) {
                System.out.println("STUDENT DELETED SUCCESSFULLY");
            } else {
                System.out.println("NO STUDENT FOUND WITH THE NAME OF " + nametobedel);
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void exit() {
        System.out.println("THANK");
        System.out.println("FOR");
        System.out.println("VISTING...");
    }
}