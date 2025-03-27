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

    // Funció per validar DNI
    public static String demanarDNI(String missatge) {
        String dni;
        do {
            System.out.print(missatge);
            dni = scanner.nextLine().trim().toUpperCase();
            if (!dni.matches("^[0-9]{8}[A-Z]$")) {
                System.out.println("DNI incorrecte. Exemple: 12345678X");
            }
        } while (!dni.matches("^[0-9]{8}[A-Z]$"));
        return dni;
    }

    // Funció per demanar confirmació (S/N)
    public static boolean demanarConfirmacio(String missatge) {
        String resposta;
        do {
            System.out.print(missatge + " (s/n): ");
            resposta = scanner.nextLine().trim().toLowerCase();
            if (!resposta.equals("s") && !resposta.equals("n")) {
                System.out.println("Respon 's' per sí o 'n' per no.");
            }
        } while (!resposta.equals("s") && !resposta.equals("n"));
        return resposta.equals("s");
    }
}