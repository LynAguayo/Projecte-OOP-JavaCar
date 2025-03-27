package org.JavaCar;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    // Constants
    private static final String DATA_DIR = "data";
    private static final String CLIENTS_FILE = DATA_DIR + "/clients.txt";

    // SConstants per càlcul de punts
    public static final int PUNTS_PER_DIA_ECO = 10;       // Punts per día de ECO
    public static final int PUNTS_PER_DIA_ZERO_EM = 15;   // Punts per día de 0 Emissions
    public static final int PUNTS_PER_100_EURO = 5;       // Punts per cada 100€ gastats
    public static final int MIN_PUNTS_CANVI = 100;        // Mínim punts per canjear
    public static final double EUROS_PER_CANVI = 5.0;     // € de descompte per cada 100 punts

    // Constructor que crea el directori si no existeix
    public ClientService() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    // Guarda un nou client a l'arxiu
    public void guardarClient(Client client) {
        try (PrintWriter out = new PrintWriter(new FileWriter(CLIENTS_FILE, true))) {
            out.println(client.toFileString());
        } catch (IOException e) {
            System.out.println("Error al guardar client: " + e.getMessage());
        }
    }

    // Mètode per buscar un client per DNI
    public Client trobarClient(String dni) {
        List<Client> clients = obtenirTotsClients();
        for (Client client : clients) {
            if (client.getDni().equalsIgnoreCase(dni)) {
                return client;
            }
        }
        return null;
    }

    // Métode per llegir tots els clients de l'arxiu
    public List<Client> obtenirTotsClients() {
        List<Client> clients = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CLIENTS_FILE))) {
            String linia;
            while ((linia = br.readLine()) != null) {
                Client client = Client.fromFileString(linia);
                if (client != null) {
                    clients.add(client);
                }
            }
        } catch (IOException e) {
            System.out.println("Error en llegir clients: " + e.getMessage());
        }
        return clients;
    }

    // Actualitza les dades d'un client
    public void actualitzarClient(Client client) {
        List<Client> clients = obtenirTotsClients();
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getDni().equalsIgnoreCase(client.getDni())) {
                clients.set(i, client);
                guardarTotsClients(clients);
                break;
            }
        }
    }

    // Guarda tots els clients (sobreescriu l'arxiu)
    private void guardarTotsClients(List<Client> clients) {
        try (PrintWriter out = new PrintWriter(new FileWriter(CLIENTS_FILE))) {
            for (Client client : clients) {
                out.println(client.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar llista de clients: " + e.getMessage());
        }
    }

    // Mètode per afegir punts a un client segons el lloguer
    public void afegirPunts(String dni, String tipusVehicle, int dies, double preuTotal) {
        Client client = trobarClient(dni);
        if (client == null) return;

        int punts = 0;

        // Punts por tipus de vehícle ecològic
        if ("ECO".equalsIgnoreCase(tipusVehicle)) {
            punts += dies * PUNTS_PER_DIA_ECO;
        } else if ("0 Emissions".equalsIgnoreCase(tipusVehicle)) {
            punts += dies * PUNTS_PER_DIA_ZERO_EM;
        }

        // Punts per gastar (1 punt per cada 100€)
        punts += (int)(preuTotal / 100) * PUNTS_PER_100_EURO;

        if (punts > 0) {
            client.setPunts(client.getPunts() + punts);
            actualitzarClient(client);
            System.out.printf("Has guanyat %d punts! Total acumulat: %d%n", punts, client.getPunts());
        }
    }

    // Canvia punts per descompte (100 punts = 5€)
    public double canviarPunts(String dni, int punts) throws Exception {
        if (punts < MIN_PUNTS_CANVI || punts % MIN_PUNTS_CANVI != 0) {
            throw new Exception(String.format("Els punts han de ser múltiples de %d", MIN_PUNTS_CANVI));
        }

        Client client = trobarClient(dni);
        if (client == null) {
            throw new Exception("Client no trobat");
        }

        if (client.getPunts() < punts) {
            throw new Exception(String.format("Punts insuficients. Tens %d i necessites %d", client.getPunts(), punts));
        }

        client.setPunts(client.getPunts() - punts);
        actualitzarClient(client);

        return (punts / MIN_PUNTS_CANVI) * EUROS_PER_CANVI;
    }

    // Retorna true si el DNI ya está registrado
    public boolean dniEstaRegistrat(String dni) {
        return trobarClient(dni) != null;
    }

    // Retorna els punts d'un client
    public int obtenirPunts(String dni) {
        Client client = trobarClient(dni);
        return client != null ? client.getPunts() : 0;
    }
}