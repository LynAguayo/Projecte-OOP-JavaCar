package org.JavaCar;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LloguerService {
    // Constants
    private static final String DATA_DIR = "data";
    private static final String LLOGUERS_FILE = DATA_DIR + "/lloguers.txt";
    private static final String VALORACIONS_FILE = DATA_DIR + "/valoracions.txt";

    // Serveis
    private final VehicleService vehicleService;

    // Constructor
    public LloguerService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
        crearDirectoriSiNoExisteix();
    }

    // Crea el directori si no existeix
    private void crearDirectoriSiNoExisteix() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    // Guarda un lloguer a l'arxiu
    private void guardarLloguer(Lloguer lloguer) {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(LLOGUERS_FILE, true));
            out.println(lloguer.toFileString());
            out.close();
        } catch (IOException e) {
            System.out.println("Error al guardar lloguer: " + e.getMessage());
        }
    }

    // Llegeix tots els lloguers de l'arxiu
    protected List<Lloguer> obtenirTotsLloguers() {
        List<Lloguer> lloguers = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(LLOGUERS_FILE));
            String linia;
            while ((linia = br.readLine()) != null) {
                Lloguer ll = Lloguer.fromFileString(linia);
                if (ll != null) {
                    lloguers.add(ll);
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error en llegir lloguers: " + e.getMessage());
        }

        return lloguers;
    }

    // Guarda tots els lloguers (sobreescriu l'arxiu)
    private void guardarTotsLloguers(List<Lloguer> lloguers) {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(LLOGUERS_FILE));
            for (Lloguer ll : lloguers) {
                out.println(ll.toFileString());
            }
            out.close();
        } catch (IOException e) {
            System.out.println("Error al guardar lloguers: " + e.getMessage());
        }
    }

    // Comprova si un vehicle està disponible per a les dates seleccionades
    public boolean esVehicleDisponible(String matricula, String dataInici, int dies) {
        String dataFinal = calcularDataFinal(dataInici, dies);
        String avui = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        List<Lloguer> lloguers = obtenirTotsLloguers();

        for (Lloguer ll : lloguers) {
            if (ll.getMatricula().equals(matricula)) {
                // Alquileres activos (deben estar disponibles)
                if (ll.getEstat() == Lloguer.Estat.ACTIU &&
                        datesSolapades(dataInici, dataFinal, ll.getDataInici(), ll.getDataFinal())) {
                    return false;
                }

                // Reservas pendientes (solo bloquean si solapan)
                if (ll.getEstat() == Lloguer.Estat.PENDENT &&
                        datesSolapades(dataInici, dataFinal, ll.getDataInici(), ll.getDataFinal())) {
                    return false;
                }
            }
        }
        return true;
    }

    // Comprova si dues dates estan solapades
    private boolean datesSolapades(String inicio1, String fin1, String inicio2, String fin2) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date dInicio1 = sdf.parse(inicio1);
            Date dFin1 = sdf.parse(fin1);
            Date dInicio2 = sdf.parse(inicio2);
            Date dFin2 = sdf.parse(fin2);

            return dInicio1.before(dFin2) && dFin1.after(dInicio2);
        } catch (Exception e) {
            System.out.println("Error en format de data: " + e.getMessage());
            return false;
        }
    }

    // Calcula la data final a partir de la inicial i els dies
    private String calcularDataFinal(String dataInici, int dies) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = sdf.parse(dataInici);
            long msPerDay = 24 * 60 * 60 * 1000;
            long msTotal = date.getTime() + (dies * msPerDay);
            return sdf.format(new Date(msTotal));
        } catch (Exception e) {
            System.out.println("Error calculant data final: " + e.getMessage());
            return dataInici;
        }
    }

    // Mètodes per fer lloguers
    public double realitzarLloguer(String dniClient, String matricula, int dies) {
        String avui = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        return realitzarLloguer(dniClient, matricula, avui, dies);
    }

    // Mètodes per fer lloguers
    public double realitzarLloguer(String dniClient, String matricula, String dataInici, int dies) {
        if (!vehicleService.existeixVehicle(matricula)) {
            throw new RuntimeException("Vehicle no trobat");
        }

        if (!esVehicleDisponible(matricula, dataInici, dies)) {
            throw new RuntimeException("Vehicle no disponible per a les dates seleccionades");
        }

        Vehicle vehicle = vehicleService.getVehicleByMatricula(matricula);
        double preuTotal = vehicle.calcularPreu(dies);

        Lloguer lloguer = new Lloguer(dniClient, matricula, dataInici, dies);
        lloguer.setPreuTotal(preuTotal);
        guardarLloguer(lloguer);

        return preuTotal;
    }

    // Mètode per finalitzar  lloguers
    public boolean finalitzarLloguer(String idLloguer) {
        List<Lloguer> lloguers = obtenirTotsLloguers();
        for (Lloguer ll : lloguers) {
            if (ll.getId().equals(idLloguer)) {
                ll.marcarCompletat();
                guardarTotsLloguers(lloguers);
                return true;
            }
        }
        return false;
    }

    // Mètodes per cancelar lloguers
    public boolean cancelarLloguer(String idLloguer, String dniClient) {
        List<Lloguer> lloguers = obtenirTotsLloguers();
        for (Lloguer ll : lloguers) {
            if (ll.getId().equals(idLloguer) && ll.getDniClient().equals(dniClient)) {
                ll.cancelar();
                guardarTotsLloguers(lloguers);
                return true;
            }
        }
        return false;
    }

    // Mètode per fer reserves
    public String ferReserva(String dni, String matricula, String dataInici, int dies) {
        if (!vehicleService.existeixVehicle(matricula)) {
            throw new RuntimeException("Vehicle no trobat");
        }

        if (!esVehicleDisponible(matricula, dataInici, dies)) {
            throw new RuntimeException("Vehicle no disponible per a les dates seleccionades");
        }

        Vehicle vehicle = vehicleService.getVehicleByMatricula(matricula);
        double preuTotal = vehicle.calcularPreu(dies);

        Lloguer reserva = new Lloguer(dni, matricula, dataInici, dies);
        reserva.setPreuTotal(preuTotal);
        reserva.setId("RES-" + System.currentTimeMillis());
        guardarLloguer(reserva);

        return reserva.getId();
    }

    // Mètode per modificar reserves
    public boolean modificarReserva(String idReserva, String dniClient, String novaDataInici, int nousDies) {
        List<Lloguer> lloguers = obtenirTotsLloguers();
        for (Lloguer ll : lloguers) {
            if (ll.getId().equals(idReserva) &&
                    ll.getDniClient().equals(dniClient) &&
                    ll.getId().startsWith("RES-")) {

                if (!esVehicleDisponible(ll.getMatricula(), novaDataInici, nousDies)) {
                    throw new RuntimeException("Vehicle no disponible per a les noves dates");
                }

                ll.setDataInici(novaDataInici);
                ll.setDies(nousDies);
                ll.setDataFinal(calcularDataFinal(novaDataInici, nousDies));

                Vehicle vehicle = vehicleService.getVehicleByMatricula(ll.getMatricula());
                ll.setPreuTotal(vehicle.calcularPreu(nousDies));

                guardarTotsLloguers(lloguers);
                return true;
            }
        }
        return false;
    }

    // Mètode per cancelar reserves
    public boolean cancelarReserva(String idReserva, String dniClient) {
        List<Lloguer> lloguers = obtenirTotsLloguers();
        for (Lloguer ll : lloguers) {
            if (ll.getId().equals(idReserva) &&
                    ll.getDniClient().equals(dniClient) &&
                    ll.getId().startsWith("RES-")) {

                ll.cancelar();
                guardarTotsLloguers(lloguers);
                return true;
            }
        }
        return false;
    }

    // Mètode per obtenir lloguers actius
    public List<Lloguer> obtenirHistorial(String dniClient) {
        List<Lloguer> historial = new ArrayList<>();
        for (Lloguer ll : obtenirTotsLloguers()) {
            if (ll.getDniClient().equals(dniClient)) {
                historial.add(ll);
            }
        }
        return historial;
    }

    // Mètode per afegir valoracions
    public boolean afegirValoracio(String dni, String matricula, int puntuacio, String comentari) {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(VALORACIONS_FILE, true));
            out.println(dni + ";" + matricula + ";" + puntuacio + ";" + comentari);
            out.close();
            return true;
        } catch (IOException e) {
            System.out.println("Error en afegir valoració: " + e.getMessage());
            return false;
        }
    }
}