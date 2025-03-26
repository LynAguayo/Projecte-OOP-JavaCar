package org.JavaCar;

public class Motor {
    // Variables
    private String tipus;
    private double potencia;

    // Constructor per inicialitzar el motor
    public Motor(String tipus, double potencia) {
        this.tipus = tipus;
        this.potencia = potencia;
    }

    // Getter de Tipus
    public String getTipus() {
        return tipus;
    }

    // Getter de Potencia
    public double getPotencia() {
        return potencia;
    }


}