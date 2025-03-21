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
        return total;
    }

    /**
     * Filtrar la llista de vehicles segons un preu màxim de lloguer.
     * @param vehicles Llista de vehicles disponibles per al lloguer.
     * @param preuMax Preu màxim permès per al lloguer
     * @return Una llista de vehicles que tenen un preu base menor o igual al preu màxim indicat
     */
    public static List<Vehicle> filtrarPerPreu(List<Vehicle> vehicles, double preuMax) {
        return vehicles.stream().filter(v -> v.getPreuBase() <= preuMax).collect(Collectors.toList());
    }
}
