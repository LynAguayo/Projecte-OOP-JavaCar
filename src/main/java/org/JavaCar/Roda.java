package org.JavaCar;

public class Roda {

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


}
