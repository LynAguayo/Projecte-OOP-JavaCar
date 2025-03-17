package org.JavaCar;

public class Roda {

    // Variables
    private String marca;
    private double diametre;

    // Constructor per inicialitzar la roda
    public Roda(String marca, double diametre) {
        this.marca = marca;
        this.diametre = diametre;
    }

    // Getter i Setter de la Marca
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }

    // Getter i Setter del Diametre
    public double getDiametre() {
        return diametre;
    }
    public void setDiametre(double diametre) {
        this.diametre = diametre;
    }

    @Override
    public String toString() {
        return "Roda{ Marca: " + marca + " / Diàmetre: " + diametre+ "}";
    }
}
