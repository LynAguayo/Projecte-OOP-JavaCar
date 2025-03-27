package org.JavaCar;

public class Client {
    // Atributs
    private String dni;
    private String nom;
    private String adreca;
    private int punts;  // Puntos ecológicos

    // Constructor
    public Client(String dni, String nom, String adreca) {
        this(dni, nom, adreca, 0);
    }

    // Constructor amb punts
    public Client(String dni, String nom, String adreca, int punts) {
        this.dni = dni;
        this.nom = nom;
        this.adreca = adreca;
        this.punts = punts;
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

    public int getPunts() {
        return punts;
    }

    // Setters
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAdreca(String adreca) {
        this.adreca = adreca;
    }

    public void setPunts(int punts) {
        this.punts = punts;
    }

    // Pasa el client a String per guardar en arxiu
    public String toFileString() {
        return dni + ";" + nom + ";" + adreca + ";" + punts;
    }

    // Crear un client des d'un String de fitxer
    public static Client fromFileString(String linia) {
        String[] parts = linia.split(";");
        if (parts.length != 4) {
            return null;
        }
        try {
            return new Client(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
        } catch (NumberFormatException e) {
            System.out.println("Error en format de punts: " + e.getMessage());
            return null;
        }
    }
}