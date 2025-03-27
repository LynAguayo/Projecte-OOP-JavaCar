package org.JavaCar;

import java.util.Scanner;

// Clase estàtica per validar els inputs del user
public class AjudaEntrada {
    // Constant per llegir de teclat
    private static final Scanner scanner = new Scanner(System.in);

    // Funció per demanar un número dins d'un rang
    public static int demanarNumero(String missatge, int min, int max) {
        int input;
        do {
            System.out.print(missatge);
            while (!scanner.hasNextInt()) {
                System.out.println("Si us plau, introdueix un número vàlid.");
                scanner.next();
            }
            input = scanner.nextInt();
            scanner.nextLine();
            if (input < min || input > max) {
                System.out.println("Opció no vàlida. Ha de ser entre " + min + " i " + max + ".");
            }
        } while (input < min || input > max);
        return input;
    }

    // Funció per demanar text que no estigui buit
    public static String demanarText(String missatge) {
        String input;
        do {
            System.out.print(missatge);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("No pots deixar aquest camp buit.");
            }
        } while (input.isEmpty());
        return input;
    }