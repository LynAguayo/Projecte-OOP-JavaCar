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

    // Getters
    public String getDni() {
        return dni;
    }

    public String getNom() {
        return nom;
    }

    public String getAdreca() {
        return adreca;
    }

    public String getTipus() {
        return tipus;
    }

    // Setters
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAdreca(String adreca) {
        this.adreca = adreca;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }
}
