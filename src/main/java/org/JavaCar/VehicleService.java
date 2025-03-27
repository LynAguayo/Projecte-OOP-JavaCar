package org.JavaCar;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleService {
    // Constants
    private static final String DATA_DIR = "data";
    private static final String VEHICLES_FILE = DATA_DIR + "/vehicles.txt";

    // Constructor que crea el directori si no existeix
    public VehicleService() {
        crearDirectoriSiNoExisteix();
    }

    // Crea el directori si no existeix
    private void crearDirectoriSiNoExisteix() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    // Retorna tots els vehícles del sistema
    public List<Vehicle> obtenirTotsVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(VEHICLES_FILE))) {
            String linia;
            while ((linia = br.readLine()) != null) {
                Vehicle vehicle = Vehicle.fromFileString(linia);
                if (vehicle != null) {
                    vehicles.add(vehicle);
                }
            }
        } catch (IOException e) {
            System.out.println("Error en llegir vehicles: " + e.getMessage());
        }

        return vehicles;
    }

    // Retorna un vehículo por matrícula
    public Vehicle getVehicleByMatricula(String matricula) {
        List<Vehicle> vehicles = obtenirTotsVehicles();

        for (Vehicle vehicle : vehicles) {
            if (vehicle.getMatricula().equalsIgnoreCase(matricula)) {
                return vehicle;
            }
        }
        return null;
    }

    // Verifica si un vehícle existeix per matrícula
    public boolean existeixVehicle(String matricula) {
        return getVehicleByMatricula(matricula) != null;
    }

    // Afegeix un nou vehicle al sistema
    public void afegirVehicle(Vehicle vehicle) {
        if (existeixVehicle(vehicle.getMatricula())) {
            throw new IllegalArgumentException("Ja existeix un vehicle amb aquesta matrícula");
        }

        try (PrintWriter out = new PrintWriter(new FileWriter(VEHICLES_FILE, true))) {
            out.println(vehicle.toFileString());
        } catch (IOException e) {
            System.out.println("Error en afegir vehicle: " + e.getMessage());
        }
    }

    // Actualizar un vehícle existent
    public void actualitzarVehicle(Vehicle vehicleActualitzat) {
        List<Vehicle> vehicles = obtenirTotsVehicles();
        boolean trobat = false;

        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getMatricula().equalsIgnoreCase(vehicleActualitzat.getMatricula())) {
                vehicles.set(i, vehicleActualitzat);
                trobat = true;
                break;
            }
        }

        if (trobat) {
            guardarTotsVehicles(vehicles);
        } else {
            throw new IllegalArgumentException("No s'ha trobat el vehicle a actualitzar");
        }
    }

    // Guarda tots els vehícles
    private void guardarTotsVehicles(List<Vehicle> vehicles) {
        try (PrintWriter out = new PrintWriter(new FileWriter(VEHICLES_FILE))) {
            for (Vehicle vehicle : vehicles) {
                out.println(vehicle.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar vehicles: " + e.getMessage());
        }
    }
}