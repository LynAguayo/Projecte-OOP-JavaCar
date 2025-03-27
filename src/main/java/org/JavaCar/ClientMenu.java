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

    // Mètode per mostrar els vehicles disponibles
    private void mostrarDisponibilitat() {
        System.out.println("\nVEHICLES DISPONIBLES:");
        List<Vehicle> vehicles = vehicleService.obtenirTotsVehicles();
        List<Vehicle> disponibles = new ArrayList<>();

        String avui = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

        for (Vehicle v : vehicles) {
            if (lloguerService.esVehicleDisponible(v.getMatricula(), avui, 1)) {
                disponibles.add(v);
            }
        }

        if (disponibles.isEmpty()) {
            System.out.println("No hi ha vehicles disponibles actualment.");
        } else {
            System.out.println("| Matrícula | Marca     | Model    | Preu/dia | Etiqueta |");
            System.out.println("|-----------|-----------|----------|----------|----------|");

            for (Vehicle v : disponibles) {
                System.out.printf("| %-9s | %-9s | %-8s | %8.2f€ | %-8s |%n",
                        v.getMatricula(), v.getMarca(), v.getModel(),
                        v.getPreuBase(), v.getEtiquetaAmbiental());
            }
        }
    }

    // Mètode per fer un lloguer
    private void realitzarLloguer() {
        mostrarDisponibilitat();
        String matricula = AjudaEntrada.demanarText("\nIntrodueix matrícula del vehicle: ");

        Vehicle vehicle = vehicleService.getVehicleByMatricula(matricula);
        if (vehicle == null) {
            System.out.println("No existeix cap vehicle amb aquesta matrícula.");
            return;
        }

        int dies = AjudaEntrada.demanarNumero("Dies de lloguer (1-30): ", 1, 30);

        try {
            double cost = lloguerService.realitzarLloguer(client.getDni(), matricula, dies);
            System.out.printf("\nLloguer realitzat correctament.%nCost total: %.2f€%n", cost);

            if ("ECO".equalsIgnoreCase(vehicle.getEtiquetaAmbiental()) || "0 Emissions".equalsIgnoreCase(vehicle.getEtiquetaAmbiental())) {
                clientService.afegirPunts(client.getDni(), vehicle.getEtiquetaAmbiental(), dies, cost);
            }
        } catch (Exception e) {
            System.out.println("Error en el lloguer: " + e.getMessage());
        }

    }

    // Mètode per cancel·lar un lloguer
    private void cancelarLloguer() {
        System.out.println("\n--- CANCEL·LAR LLOGUER ---");
        String idLloguer = AjudaEntrada.demanarText("Introdueix ID de lloguer a cancel·lar: ");

        try {
            boolean cancelat = lloguerService.cancelarLloguer(idLloguer, client.getDni());
            if (cancelat) {
                System.out.println("Lloguer cancel·lat correctament.");
            } else {
                System.out.println("No s'ha pogut cancel·lar el lloguer (ID no trobat o ja està cancel·lat).");
            }
        } catch (Exception e) {
            System.out.println("Error en cancel·lar: " + e.getMessage());
        }
    }

    // Mètode per gestionar les reserves
    private void menuReserves() {
        int opcio;
        do {
            System.out.println("\n--- GESTIÓ DE RESERVES ---");
            System.out.println("1. Fer reserva");
            System.out.println("2. Modificar reserva");
            System.out.println("3. Cancel·lar reserva");
            System.out.println("4. Tornar");
            System.out.print("Selecciona una opció: ");

            opcio = AjudaEntrada.demanarNumero("", 1, 4);

            switch (opcio) {
                case 1 -> ferReserva();
                case 2 -> modificarReserva();
                case 3 -> cancelarReserva();
                case 4 -> System.out.println("Tornant...");
                default -> System.out.println("Opció no vàlida");
            }
        } while (opcio != 4);
    }

    // Mètode per fer una reserva
    private void ferReserva() {
        mostrarDisponibilitat();
        String matricula = AjudaEntrada.demanarText("\nIntrodueix matrícula del vehicle: ");
        String dataInici = AjudaEntrada.demanarText("Data d'inici (dd/mm/aaaa): ");
        int dies = AjudaEntrada.demanarNumero("Nombre de dies: ", 1, 30);

        try {
            String codiReserva = lloguerService.ferReserva(client.getDni(), matricula, dataInici, dies);
            System.out.println("\nReserva realitzada correctament.");
            System.out.println("Codi de reserva: " + codiReserva);
        } catch (Exception e) {
            System.out.println("Error en fer reserva: " + e.getMessage());
        }
    }

    // Mètode per modificar una reserva
    private void modificarReserva() {
        System.out.println("\n--- MODIFICAR RESERVA ---");
        String codi = AjudaEntrada.demanarText("Introdueix codi de reserva: ");
        String novaData = AjudaEntrada.demanarText("Nova data d'inici (dd/mm/aaaa): ");
        int nousDies = AjudaEntrada.demanarNumero("Nous dies de lloguer: ", 1, 30);

        try {
            boolean actualitzada = lloguerService.modificarReserva(codi, client.getDni(), novaData, nousDies);
            if (actualitzada) {
                System.out.println("Reserva modificada correctament.");
            } else {
                System.out.println("No s'ha pogut modificar la reserva.");
            }
        } catch (Exception e) {
            System.out.println("Error en modificar reserva: " + e.getMessage());
        }
    }

    // Mètode per cancel·lar una reserva
    private void cancelarReserva() {
        System.out.println("\n--- CANCEL·LAR RESERVA ---");
        String codi = AjudaEntrada.demanarText("Introdueix codi de reserva: ");

        try {
            boolean cancelada = lloguerService.cancelarReserva(codi, client.getDni());
            if (cancelada) {
                System.out.println("Reserva cancel·lada correctament.");
            } else {
                System.out.println("No s'ha pogut cancel·lar la reserva.");
            }
        } catch (Exception e) {
            System.out.println("Error en cancel·lar reserva: " + e.getMessage());
        }
    }

    // Mètode per comparar vehicles
    private void compararVehicles() {
        System.out.println("\n--- COMPARAR VEHICLES ---");
        List<Vehicle> vehicles = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            String matricula = AjudaEntrada.demanarText("Introdueix matrícula del vehicle " + (i+1) + " (o 'fi' per acabar): ");

            if (matricula.equalsIgnoreCase("fi")) break;

            Vehicle v = vehicleService.getVehicleByMatricula(matricula);
            if (v != null) {
                vehicles.add(v);
            } else {
                System.out.println("Vehicle no trobat.");
                i--; // Per tornar a demanar aquest
            }
        }

        if (!vehicles.isEmpty()) {
            System.out.println("\nCOMPARATIVA DE VEHICLES:");
            System.out.println("| Matrícula | Marca     | Model    | Preu/dia | Etiqueta | Motor    |");
            System.out.println("|-----------|-----------|----------|----------|----------|----------|");

            for (Vehicle v : vehicles) {
                System.out.printf("| %-9s | %-9s | %-8s | %8.2f€ | %-8s | %-8s |%n",
                        v.getMatricula(), v.getMarca(), v.getModel(), v.getPreuBase(),
                        v.getEtiquetaAmbiental(), v.getMotor().getTipus());
            }
        }
    }
