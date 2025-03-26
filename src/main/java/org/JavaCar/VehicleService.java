package org.JavaCar;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleService {
    private static final String DATA_DIR = "data";
    private static final String VEHICLES_FILE = DATA_DIR + "/vehicles.txt";

    public void afegirVehicle(Vehicle vehicle) {
        try (PrintWriter out = new PrintWriter(new FileWriter(VEHICLES_FILE, true))) {
            out.println(vehicle.toFileString());
        } catch (IOException e) {
            System.err.println("Error al guardar vehicle: " + e.getMessage());
        }
    }

    public Vehicle getVehicleByMatricula(String matricula) {
        try (BufferedReader br = new BufferedReader(new FileReader(VEHICLES_FILE))) {
            String linia;
            while ((linia = br.readLine()) != null) {
                Vehicle v = Vehicle.fromFileString(linia);
                if (v.getMatricula().equalsIgnoreCase(matricula)) {
                    return v;
                }
            }
        } catch (IOException e) {
            System.err.println("Error llegint vehicles: " + e.getMessage());
        }
        return null;
    }

    public List<Vehicle> obtenirVehiclesDisponibles() {
        // Implementación simplificada - todos están disponibles
        return obtenirTotsVehicles();
    }

    public List<Vehicle> obtenirTotsVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(VEHICLES_FILE))) {
            String linia;
            while ((linia = br.readLine()) != null) {
                vehicles.add(Vehicle.fromFileString(linia));
            }
        } catch (IOException e) {
            System.err.println("Error llegint vehicles: " + e.getMessage());
        }
        return vehicles;
    }

    public List<Vehicle> filtrarVehicles(String tipus) {
        List<Vehicle> vehicles = obtenirTotsVehicles();
        List<Vehicle> filtrats = new ArrayList<>();

        for (Vehicle v : vehicles) {
            if (v.getEtiquetaAmbiental().equalsIgnoreCase(tipus) ||
                    v.getMotor().getTipus().equalsIgnoreCase(tipus)) {
                filtrats.add(v);
            }
        }
        return filtrats;
    }

    public void actualitzarVehicle(Vehicle vehicle) {
        List<Vehicle> vehicles = obtenirTotsVehicles();
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getMatricula().equalsIgnoreCase(vehicle.getMatricula())) {
                vehicles.set(i, vehicle);
                break;
            }
        }
        guardarTotsVehicles(vehicles);
    }

    public boolean eliminarVehicle(String matricula) {
        List<Vehicle> vehicles = obtenirTotsVehicles();
        boolean trobat = false;

        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getMatricula().equalsIgnoreCase(matricula)) {
                vehicles.remove(i);
                trobat = true;
                break;
            }
        }

        if (trobat) {
            guardarTotsVehicles(vehicles);
        }
        return trobat;
    }

    private void guardarTotsVehicles(List<Vehicle> vehicles) {
        try (PrintWriter out = new PrintWriter(new FileWriter(VEHICLES_FILE))) {
            for (Vehicle v : vehicles) {
                out.println(v.toFileString());
            }
        } catch (IOException e) {
            System.err.println("Error al guardar vehicles: " + e.getMessage());
        }
    }
}
