package org.JavaCar;

public class Furgoneta extends Vehicle{
    private double capacitatCarga;

    public Furgoneta(String matricula, String marca, String model, double preuBase, double capacitatCarga, Motor motor, List<Roda> rodes) {
        super(matricula, marca, model, preuBase, motor, rodes, "E"); // Etiqueta ambiental per defecte
        this.capacitatCarga = capacitatCarga;
    }
}
