package org.JavaCar;

public class Moto extends Vehicle{
    private int cilindrada;

    public Moto(String matricula, String marca, String model, double preuBase, int cilindrada, Motor motor, List<Roda> rodes) {
        super(matricula, marca, model, preuBase, motor, rodes, "C"); // Etiqueta ambiental per defecte
        this.cilindrada = cilindrada;
    }

    public int getCilindrada() {
        return cilindrada;
    }
}
