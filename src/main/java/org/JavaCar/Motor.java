package org.JavaCar;

public class Motor {
    // Variables
    private String tipus;
    private double potencia;

    // Constructor per inicialitzar el motor
    public Motor(String tipus, double potencia) {
        this.tipus = tipus;
        setPotencia(potencia);
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
    public void setPotencia(double potencia) {
        if (potencia > 0) {
            this.potencia = potencia;
        } else {
            throw new IllegalArgumentException("La potència ha de ser un valor positiu!");
        }
    }

    @Override
    public String toString() {
        return "Motor {Tipus: " + tipus + " / Potencia: " + potencia;
    }
}
