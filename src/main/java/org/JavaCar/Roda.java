package org.JavaCar;

public class Roda {

    // Variables
    public String marca;
    public double diametre;

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
}
