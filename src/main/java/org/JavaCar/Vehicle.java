package org.JavaCar;

public abstract class Vehicle implements Llogable{
    // Atributs
    protected String matricula;
    protected String marca;
    protected String model;
    protected double preuBase;
    protected Motor motor;
    protected Roda[] rodes;
    protected String etiquetaAmbiental;

    // Patró per validar la matrícula
    private static final String PATRO_MATRICULA = "^[0-9]{4}[A-Z]{3}$";

    // Constants per calcular les etiquetes ambientals
    private static final int ANY_TRANSICIO_SEGLE = 30; // Límit per determinar segle (2000 o 1900)
    private static final int ANY_BASE = 2000; // Any base per matrícules <=30
    private static final int ANY_BASE_ANTIC = 1900; // Any base per matrícules >30
    private static final int EURO_2_MAX_ANY = 1999;
    private static final int EURO_3_MAX_ANY = 2004;
    private static final int EURO_4_MAX_ANY = 2009;
    private static final int EURO_5_MAX_ANY = 2014;

    // Constructor
    public Vehicle(String matricula, String marca, String model, double preuBase, Motor motor, Roda[] rodes) {
        validarMatricula(matricula);
        this.matricula = matricula;
        this.marca = marca;
        this.model = model;
        this.preuBase = preuBase;
        this.motor = motor;
        this.rodes = rodes;
        this.etiquetaAmbiental = calcularEtiquetaAmbiental();
    }

    // Setters i getters
    public String getMatricula() {
        return matricula;
    }

    public String getMarca() {
        return marca;
    }

    public String getModel() {
        return model;
    }

    public double getPreuBase() {
        return preuBase;
    }

    public Motor getMotor() {
        return motor;
    }

    public Roda[] getRodes() {
        return rodes;
    }

    public String getEtiquetaAmbiental() {
        return etiquetaAmbiental;
    }

    public void setPreuBase(double preuBase) { // s'utilitza a lloguerService
        this.preuBase = preuBase;
    }

}
