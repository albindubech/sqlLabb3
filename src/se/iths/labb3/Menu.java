package se.iths.labb3;

import java.sql.SQLException;

public class Menu {
    private final Functions functions;

    private Menu() throws SQLException {
        functions = new Functions();
    }

    void run() throws SQLException {
        int choice;
        do {
            printMenuOption();
            choice = functions.verifyInteger("");
            executeChoice(choice);
        } while (choice != 0);
    }

    private void executeChoice(int choice) throws SQLException {
        switch (choice) {
            case 0 -> System.out.println("Programmet avslutas");
            case 1 -> functions.add();
            case 2 -> functions.delete();
            case 3 -> functions.update();
            case 4 -> functions.showAll();
            case 5 -> functions.findById();
            case 6 -> functions.findByAge();
            case 7 -> functions.findByName();
            default -> System.out.println("Ej giltig input, försök igen");
        }
    }

    private void printMenuOption() {
        System.out.println("""
        
        Artist table main menu:
        1. Lägg till
        2. Ta bort
        3. Uppdatera
        4. Visa alla artister
        5. Visa artister med ett specifikt ID
        6. Visa artister med en specifik ålder
        7. Visa artister med ett specifikt namn
        0. Avsluta programmet""");
    }

//    private int readChoice() {
//        return functions.verifyInteger("Skriv ditt val: ");
//    }

    public static void main(String[] args) throws SQLException {
        Menu menu = new Menu();
        menu.run();
    }
}
