package org.JavaCar;

public abstract class Vehicle implements Llogable{
    protected String matricula;
    protected String marca;
    protected String model;
    protected double preuBase;
    protected Motor motor;
    protected Roda[] rodes;
    protected String etiquetaAmbiental;

    // Patró per validar la matrícula
    private static final String PATRO_MATRICULA = "^[0-9]{4}[A-Z]{3}$";

    public Vehicle(String matricula, String marca, String model, double preuBase, Motor motor, Roda[] rodes) {
        this.matricula = matricula;
        this.marca = marca;
        this.model = model;
        this.preuBase = preuBase;
        this.motor = motor;
        this.rodes = rodes;
        this.etiquetaAmbiental = calcularEtiquetaAmbiental();
    }

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

    private void validarMatricula(String matricula) {
        if (matricula == null || !matricula.matches(PATRO_MATRICULA)) {
            throw new IllegalArgumentException(
                    "Matrícula invàlida. Ha de tenir 4 números seguits de 3 lletres majúscules. Exemple: 1234ABC"
            );
        }
    }

    private String calcularEtiquetaAmbiental() {
        if (motor == null) {
            return "Sense Etiqueta";
        }
        switch (motor.getTipus().toLowerCase()) {
            case "electric": return "0 Emissions";
            case "hibrid": return "ECO";
            case "gasolina": return "C";
            case "diesel": return "B";
            default: return "Sense Etiqueta";
        }
    }

    public void recalcularEtiquetaAmbiental() {
        this.etiquetaAmbiental = calcularEtiquetaAmbiental();
    }

}
