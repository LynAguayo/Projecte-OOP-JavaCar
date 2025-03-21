package org.JavaCar;

public class GestorLloguers {
    /**
     * Calcula els ingressos totals pel lloguer de tots els vehicles durant un nombre determinat de dies.
     * @param vehicles Llista de vehicles disponibles per al lloguer
     * @param dies Nombre de dies de lloguer.
     * @return El total d'ingressos generat pel lloguer.
     */
    public static double calcularIngressosTotals(List<Vehicle> vehicles, int dies){
        double total = 0;
        for (Vehicle v : vehicles) {
            total += v.calcularPreu(dies);
        }
    }


}
