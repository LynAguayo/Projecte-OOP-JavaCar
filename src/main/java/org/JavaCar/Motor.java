package org.JavaCar;

public class Motor {
    // Variables
    public String tipus;
    public double potencia;

    // Constructor per inicialitzar el motor
    public Motor(String tipus, double potencia) {
        this.tipus = tipus;
        this.potencia = potencia;
    }

    // Getter i Setter de Tipus
    public String getTipus() {
        return tipus;
    }
    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    // Getter i Setter de Potencia
    public double getPotencia() {
        return potencia;
    }

}
