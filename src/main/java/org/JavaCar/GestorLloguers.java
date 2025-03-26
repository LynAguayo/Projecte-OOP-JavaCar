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


}