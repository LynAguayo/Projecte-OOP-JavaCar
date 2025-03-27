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

    // Mètode per mostrar els vehicles més llogats
    private void mostrarVehiclesMesLlogats() {
        System.out.println("\n--- VEHICLES MÉS LLOGATS ---");

        List<Vehicle> vehicles = vehicleService.obtenirTotsVehicles();
        List<Lloguer> totsLloguers = lloguerService.obtenirTotsLloguers();

        // Arrat per comptar lloguers completats per vehicle
        int[] comptadors = new int[vehicles.size()];

        // Contar solo los alquileres COMPLETAT
        for (Lloguer ll : totsLloguers) {
            if (ll.getEstat() == Lloguer.Estat.COMPLETAT) {
                for (int i = 0; i < vehicles.size(); i++) {
                    if (vehicles.get(i).getMatricula().equals(ll.getMatricula())) {
                        comptadors[i]++;
                        break;
                    }
                }
            }
        }

        // Ordenar vehícles per nombre de lloguers completats (de major a menor)
        for (int i = 0; i < vehicles.size(); i++) {
            for (int j = i + 1; j < vehicles.size(); j++) {
                if (comptadors[i] < comptadors[j]) {
                    // Intercambiar vehículos
                    Vehicle tempV = vehicles.get(i);
                    vehicles.set(i, vehicles.get(j));
                    vehicles.set(j, tempV);

                    // Intercambiar contadores
                    int tempC = comptadors[i];
                    comptadors[i] = comptadors[j];
                    comptadors[j] = tempC;
                }
            }
        }

        // Mostrar els 5 primers
        System.out.println("| Posició | Matrícula | Model    | Vegades llogat |");
        System.out.println("|---------|-----------|----------|----------------|");

        int limit = Math.min(5, vehicles.size());
        for (int i = 0; i < limit; i++) {
            Vehicle v = vehicles.get(i);
            System.out.printf("| %7d | %-9s | %-8s | %14d |%n",
                    i + 1, v.getMatricula(), v.getModel(), comptadors[i]);
        }
    }

    // Mètode per mostrar els vehicles millor valorats
    private void mostrarVehiclesMillorValorats() {
        System.out.println("\n--- MILLORS VEHICLES VALORATS ---");

        try {
            // Llegir totes les valoracions
            List<String> valoracions = llegirValoracionsDelFitxer();

            // Processar les valoracions
            String[] matricules = new String[valoracions.size()];
            int[] puntuacionsTotals = new int[valoracions.size()];
            int[] numValoracions = new int[valoracions.size()];
            int contador = 0;

            for (String valoracio : valoracions) {
                String[] parts = valoracio.split(";");
                if (parts.length >= 3) {
                    String matricula = parts[1];
                    int puntuacio = Integer.parseInt(parts[2]);

                    // Buscar si ja existeix la matrícula
                    int posicio = -1;
                    for (int i = 0; i < contador; i++) {
                        if (matricules[i].equals(matricula)) {
                            posicio = i;
                            break;
                        }
                    }

                    if (posicio == -1) {
                        // Nova matrícula
                        matricules[contador] = matricula;
                        puntuacionsTotals[contador] = puntuacio;
                        numValoracions[contador] = 1;
                        contador++;
                    } else {
                        // Matrícula existent
                        puntuacionsTotals[posicio] += puntuacio;
                        numValoracions[posicio]++;
                    }
                }
            }

            // Calcular mitjanes i ordenar
            ordenarIMostrarMillorsVehicles(matricules, puntuacionsTotals, numValoracions, contador);

        } catch (IOException e) {
            System.out.println("Error en llegir valoracions: " + e.getMessage());
        }
    }

    // Llegir valoracions del fitxer
    private List<String> llegirValoracionsDelFitxer() throws IOException {
        List<String> valoracions = new ArrayList<>();
        File fitxer = new File(VALORACIONS_FILE);

        if (!fitxer.exists()) {
            System.out.println("Encara no hi ha valoracions.");
            return valoracions;
        }

        BufferedReader br = new BufferedReader(new FileReader(fitxer));
        String linia;
        while ((linia = br.readLine()) != null) {
            valoracions.add(linia);
        }
        br.close();

        return valoracions;
    }

    // Ordenar i mostrar els millors vehicles valorats
    private void ordenarIMostrarMillorsVehicles(String[] matricules, int[] puntuacionsTotals, int[] numValoracions, int contador) {
        // Calcular mitjanes
        float[] mitjanes = new float[contador];
        for (int i = 0; i < contador; i++) {
            mitjanes[i] = (float) puntuacionsTotals[i] / numValoracions[i];
        }

        // Ordenar per mitjana (de major a menor)
        for (int i = 0; i < contador - 1; i++) {
            for (int j = i + 1; j < contador; j++) {
                if (mitjanes[i] < mitjanes[j]) {
                    // Intercanviar totes les dades
                    float tempMitjana = mitjanes[i];
                    mitjanes[i] = mitjanes[j];
                    mitjanes[j] = tempMitjana;

                    String tempMat = matricules[i];
                    matricules[i] = matricules[j];
                    matricules[j] = tempMat;
                }
            }
        }

        // Mostrar resultats
        System.out.println("| Posició | Matrícula | Model    | Mitjana |");
        System.out.println("|---------|-----------|----------|---------|");

        int limit = Math.min(5, contador);
        for (int i = 0; i < limit; i++) {
            Vehicle v = vehicleService.getVehicleByMatricula(matricules[i]);
            System.out.printf("| %7d | %-9s | %-8s | %7.2f |%n", i + 1, matricules[i],v.getModel(), mitjanes[i]);
        }

        if (contador == 0) {
            System.out.println("No hi ha vehicles valorats.");
        }
    }

    // Mètode per mostrar els clients amb més punts
    private void mostrarTopClients() {
        System.out.println("\n--- CLIENTS AMB MÉS PUNTS ---");

        List<Client> clients = clientService.obtenirTotsClients();

        // Ordenar clients per punts
        for (int i = 0; i < clients.size(); i++) {
            for (int j = i + 1; j < clients.size(); j++) {
                int puntsI = clientService.obtenirPunts(clients.get(i).getDni());
                int puntsJ = clientService.obtenirPunts(clients.get(j).getDni());

                if (puntsI < puntsJ) {
                    // Intercanviar posicions
                    Client temp = clients.get(i);
                    clients.set(i, clients.get(j));
                    clients.set(j, temp);
                }
            }
        }

        // Mostrar els 5 primers
        System.out.println("| Posició | DNI       | Nom       | Punts |");
        System.out.println("|---------|-----------|-----------|-------|");

        int limit = Math.min(5, clients.size());
        for (int i = 0; i < limit; i++) {
            Client c = clients.get(i);
            int punts = clientService.obtenirPunts(c.getDni());
            System.out.printf("| %7d | %-9s | %-9s | %5d |%n", i + 1, c.getDni(), c.getNom(), punts);
        }
    }

    // Mètode per mostrar els lloguers actius
    private void mostrarLloguersActius() {
        System.out.println("\n--- LLOGUERS ACTIUS ---");

        String avui = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        List<Lloguer> lloguers = lloguerService.obtenirTotsLloguers();

        System.out.println("| Matrícula | Client    | Data Inici | Data Fi   |");
        System.out.println("|-----------|-----------|------------|-----------|");

        for (Lloguer ll : lloguers) {
            if (esLloguerActiu(ll, avui)) {
                imprimirLloguer(ll);
            }
        }
    }

    // Mètode per comprovar si un lloguer està actiu
    private boolean esLloguerActiu(Lloguer lloguer, String dataAvui) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date avui = sdf.parse(dataAvui);
            Date inici = sdf.parse(lloguer.getDataInici());
            Date fi = sdf.parse(lloguer.getDataFinal());

            boolean esActiuEstat = lloguer.getEstat() == Lloguer.Estat.ACTIU;
            boolean dinsTermini = !avui.before(inici) && !avui.after(fi);

            return esActiuEstat || dinsTermini;

        } catch (Exception e) {
            System.out.println("Error en dates: " + e.getMessage());
            return false;
        }
    }

    // Mètode per imprimir un lloguer
    private void imprimirLloguer(Lloguer lloguer) {
        System.out.printf("| %-9s | %-9s | %-10s | %-9s |%n", lloguer.getMatricula(), lloguer.getDniClient(), lloguer.getDataInici(), lloguer.getDataFinal());
    }
}