package org.JavaCar;

public class Moto extends Vehicle{
    private int cilindrada;

    public Moto(String matricula, String marca, String model, double preuBase, int cilindrada, Motor motor, List<Roda> rodes) {
        super(matricula, marca, model, preuBase, motor, rodes);
        this.cilindrada = cilindrada;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    @Override
    public double calcularPreu(int dies) {
        double preuFinal = preuBase * dies;
        if (cilindrada > 500) {
            preuFinal += 5 * dies;
        }
        return preuFinal;
    }
}
