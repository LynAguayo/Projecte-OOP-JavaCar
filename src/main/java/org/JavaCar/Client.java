package org.JavaCar;

public class Client {
    // Variables
    private String dni;
    private String nom;
    private String adreca;
    private String tipus; // si és "client" o "admin"

    // Constructor
    public Client(String dni, String nom, String adreca, String tipus) {
        this.dni = dni;
        this.nom = nom;
        this.adreca = adreca;
        this.tipus = tipus;
    }
}
