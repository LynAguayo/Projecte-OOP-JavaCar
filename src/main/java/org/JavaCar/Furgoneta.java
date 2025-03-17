package org.JavaCar;

public class Furgoneta extends Vehicle{
    private double capacitatCarga;

    public Furgoneta(String matricula, String marca, String model, double preuBase, double capacitatCarga, Motor motor, List<Roda> rodes) {
        super(matricula, marca, model, preuBase, motor, rodes);
        this.capacitatCarga = capacitatCarga;
    }

    public double getCapacitatCarga() {
        return capacitatCarga;
    }

    @Override
    public double calcularPreu(int dies) {
        double preuFinal = preuBase * dies;
        if (capacitatCarga > 1000) {
            preuFinal += 10 * dies;
        }
        return preuFinal;
    }
}
