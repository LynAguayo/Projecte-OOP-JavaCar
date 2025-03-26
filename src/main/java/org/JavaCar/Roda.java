package org.JavaCar;

public class Roda {
    // Atributs
    private String marca;
    private double diametre;

    // Constructor
    public Roda(String marca, double diametre) {
        this.marca = marca;
        this.diametre = diametre;
    }

    // Getters

    public String getMarca() {
        return marca;
    }

    public double getDiametre() {
        return diametre;
    }

    // Representació en format String
    @Override
    public String toString() {
        return "Roda{" +
                "marca='" + marca + '\'' +
                ", diametre=" + diametre +
                '}';
    }
}