package org.JavaCar;

import java.util.ArrayList;
import java.util.List;

public class GestorLloguers {
    // constructor privat per evitar la instanciació
    private GestorLloguers() {}

    /**
     * Calcula els ingressos totals pel lloguer de tots els vehicles durant un nombre determinat de dies.
     * @param vehicles Llista de vehicles disponibles per al lloguer
     * @param dies Nombre de dies de lloguer.
     * @return El total d'ingressos generat pel lloguer.
     */
    public static double calcularIngressosTotals(List<Vehicle> vehicles, int dies){
        double total = 0.0;
        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle vehicle = vehicles.get(i);
            total += vehicle.calcularPreu(dies);
        }
        return total;
    }

    /**
     * Filtrar la llista de vehicles segons un preu màxim de lloguer diari.
     * @param vehicles Llista de vehicles disponibles per al lloguer.
     * @param preuMax Preu màxim permès per al lloguer
     * @return Una llista de vehicles que tenen un preu base menor o igual al preu màxim indicat
     */
    public static List<Vehicle> filtrarPerPreu(List<Vehicle> vehicles, double preuMax) {
        List<Vehicle> vehiclesFiltrats = new ArrayList<>();

        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle vehicle = vehicles.get(i);
            if (vehicle.getPreuBase() <= preuMax) {
                vehiclesFiltrats.add(vehicle);
            }
        }
        return vehiclesFiltrats;
    }
}