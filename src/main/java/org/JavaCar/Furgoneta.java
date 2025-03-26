package org.JavaCar;

public class Furgoneta extends Vehicle{
    private double capacitatCarga;

    public Furgoneta(String matricula, String marca, String model, double preuBase, double capacitatCarga, Motor motor, Roda[] rodes) {
        super(matricula, marca, model, preuBase, motor, rodes);
        this.capacitatCarga = capacitatCarga;
    }

    public double getCapacitatCarga() {
        return capacitatCarga;
    }

    @Override
    public double calcularPreu(int dies) {
        if (dies <= 0) {
            throw new IllegalArgumentException("El nombre de dies ha de ser positiu");
        }

        double preuFinal = preuBase * dies;
        if (capacitatCarga > 1000) {
            double carrecAdicional = 10 * dies;
            preuFinal += carrecAdicional;
        }
        return preuFinal;
    }
}
