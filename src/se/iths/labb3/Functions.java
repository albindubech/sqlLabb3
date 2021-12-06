package se.iths.labb3;

import java.sql.*;
import java.util.Scanner;

public class Functions {
    private final Scanner scanner = new Scanner(System.in);
    private final Statement statement;
    private ResultSet resultSet;
    private String firstName;
    private String lastName;
    private int age;
    private int id;

    Functions() throws SQLException {
        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/labb3", "root", "password123");
        statement = connection.createStatement();
        createTable();
    }

    private void artistValuesInput() {
        firstName = verifyString("Ange förnamn: ");
        lastName = verifyString("Ange efternamn: ");
        age = verifyInteger("Ange Ålder: ");
        while (age < 0)
            age = verifyInteger("Ålder kan ej ha ett negativt värde, försök igen");
    }

    int verifyInteger(String input) {
        System.out.println(input);
        while (!scanner.hasNextInt()) {
            System.out.println("Ej giltig input, försök igen");
            scanner.next();
        }
        return Integer.parseInt(scanner.next());
    }

    private String verifyString(String input) {
        System.out.println(input);
        while (scanner.hasNextInt()) {
            System.out.println("Ej giltig input, försök igen");
            scanner.next();
        }
        return scanner.next();
    }

    void add() throws SQLException {
        artistValuesInput();
        statement.executeUpdate("INSERT INTO artist(first_name, last_name, age) " +
                "VALUES ('" + firstName + "', '" + lastName + "', " + age + ")");

        System.out.println("Följande värden lades till i tabellen: " + firstName + ", " + lastName + ", " + age);
    }

    void delete() throws SQLException {
        id = verifyInteger("Ange ID för den artist som ska tas bort: ");

            statement.executeUpdate("DELETE FROM artist WHERE id = " + id);
    }

    void update() throws SQLException {
        id = verifyInteger("Ange ID för den artist som ska ändras: ");
        artistValuesInput();
        statement.executeUpdate("UPDATE artist SET first_name = '"
                + firstName + "', last_name = '"
                + lastName + "', age = '"
                + age + "' WHERE id = "
                + id);
    }

    void showAll() throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM artist");
        System.out.println("Här är alla artisterna jag hittade : ");
        printer(resultSet);
    }

    void findById() throws SQLException {
        id = verifyInteger("Ange ID för den artist du vill se: ");
        resultSet = statement.executeQuery("SELECT * FROM artist WHERE id = " + id);

        printer(resultSet);
    }

    void findByAge() throws SQLException {
        id = verifyInteger("Ange ID för den artist du vill se: ");
        resultSet = statement.executeQuery("SELECT * FROM artist WHERE age = " + age);

        printer(resultSet);
    }

    void findByName() throws SQLException {
        firstName = verifyString("Ange förnamn för artisten som skall hittas: ");
        lastName = verifyString("Ange efternamn: ");
        resultSet = statement.executeQuery("SELECT * FROM artist " +
                "WHERE first_name = '" + firstName + "' AND last_name = '" + lastName + "'");

        printer(resultSet);
    }

    private void printer(ResultSet resultSet) throws SQLException {
        while (resultSet.next())
            System.out.println("ID: " + resultSet.getString("id") + " | Förnamn: "
                    + resultSet.getString("first_name") + " | Efternamn: "
                    + resultSet.getString("last_name") + " | Ålder: "
                    + resultSet.getString("age"));
    }

    private void createTable() throws SQLException {
        try {
            statement.executeUpdate("CREATE TABLE artist (" +
                    "id SMALLINT AUTO_INCREMENT," +
                    "first_name VARCHAR(50)," +
                    "last_name VARCHAR(50)," +
                    "age SMALLINT," +
                    "PRIMARY KEY (id))");
            System.out.println("""
                    En ny tabell har skapats, består av följande kolumner:
                    id
                    first_name
                    last_name
                    age""");
        } catch (SQLSyntaxErrorException s) {
            System.out.println("""
                    Tabellen består av följande kolumner:
                    id
                    first_name
                    last_name
                    age""");
        }
    }
}
