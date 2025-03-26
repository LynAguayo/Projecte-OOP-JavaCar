package org.JavaCar;

public class Motor {
    // Variables
    private String tipus;
    private int potencia;

    // Constructor per inicialitzar el motor
    public Motor(String tipus, int potencia) {
        this.tipus = tipus;
        this.potencia = potencia;
    }

    // Getter de Tipus
    public String getTipus() {
        return tipus;
    }

    // Getter de Potencia
    public int getPotencia() {
        return potencia;
    }

    @Override
    public String toString() {
        return "Motor {" +
                "Tipus: " + tipus + " / Potencia: " + potencia +
                "}";
    }
}