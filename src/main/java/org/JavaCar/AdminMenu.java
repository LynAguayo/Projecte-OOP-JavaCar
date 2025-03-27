package org.JavaCar;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminMenu {
    // Constants per a la gestió de fitxers
    private static final String DATA_DIR = "data";
    private static final String VALORACIONS_FILE = DATA_DIR + "/valoracions.txt";

    // Serveis
    private final VehicleService vehicleService;
    private final LloguerService lloguerService;
    private final ClientService clientService;

    // Constructor
    public AdminMenu(VehicleService vs, LloguerService ls, ClientService cs) {
        this.vehicleService = vs;
        this.lloguerService = ls;
        this.clientService = cs;
        crearDirectoriSiNoExisteix();
    }

    // Crear directori si no existeix
    private void crearDirectoriSiNoExisteix() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public void mostrarMenu() {
        int opcio;
        do {
            System.out.println("\n=== MENÚ ADMINISTRADOR ===");
            System.out.println("1. Veure vehicles més llogats");
            System.out.println("2. Veure millors vehicles valorats");
            System.out.println("3. Veure clients amb més punts");
            System.out.println("4. Veure lloguers actius");
            System.out.println("5. Tornar al menú principal");
            System.out.print("Selecciona una opció: ");

            opcio = AjudaEntrada.demanarNumero("", 1, 5);

            switch (opcio) {
                case 1 -> mostrarVehiclesMesLlogats();
                case 2 -> mostrarVehiclesMillorValorats();
                case 3 -> mostrarTopClients();
                case 4 -> mostrarLloguersActius();
                case 5 -> System.out.println("Tornant...");
                default -> System.out.println("Opció no vàlida");
            }
        } while (opcio != 5);
    }
