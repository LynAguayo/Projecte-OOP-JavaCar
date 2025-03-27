package org.JavaCar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientMenu {
    // Atributs
    private final Client client;
    private final VehicleService vehicleService;
    private final LloguerService lloguerService;
    private final ClientService clientService;

    // Constructor
    public ClientMenu(Client client, VehicleService vehicleService, LloguerService lloguerService, ClientService clientService) {
        this.client = client;
        this.vehicleService = vehicleService;
        this.lloguerService = lloguerService;
        this.clientService = clientService;
    }

    public void mostrarMenu() {
        int opcio;
        do {
            System.out.println("\n=== MENÚ CLIENT ===");
            System.out.println("1. Lloguer de vehicles");
            System.out.println("2. Gestió de reserves");
            System.out.println("3. Comparar vehicles");
            System.out.println("4. El meu compte");
            System.out.println("5. Tornar al menú principal");
            System.out.print("Selecciona una opció: ");

            opcio = AjudaEntrada.demanarNumero("", 1, 5);

            switch (opcio) {
                case 1 -> menuLloguer();
                case 2 -> menuReserves();
                case 3 -> compararVehicles();
                case 4 -> menuCompte();
                case 5 -> System.out.println("Tornant al menú principal...");
                default -> System.out.println("Opció no vàlida");
            }
        } while (opcio != 5);
    }

    private void menuLloguer() {
        int opcio;
        do {
            System.out.println("\n--- LLOGUER DE VEHICLES ---");
            System.out.println("1. Veure disponibilitat");
            System.out.println("2. Realitzar lloguer");
            System.out.println("3. Cancel·lar lloguer");
            System.out.println("4. Tornar");
            System.out.print("Selecciona una opció: ");

            opcio = AjudaEntrada.demanarNumero("", 1, 4);

            switch (opcio) {
                case 1 -> mostrarDisponibilitat();
                case 2 -> realitzarLloguer();
                case 3 -> cancelarLloguer();
                case 4 -> System.out.println("Tornant...");
                default -> System.out.println("Opció no vàlida");
            }
        } while (opcio != 4);
    }
