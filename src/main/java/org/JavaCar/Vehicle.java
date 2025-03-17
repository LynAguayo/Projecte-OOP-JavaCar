package org.JavaCar;

public abstract class Vehicle implements Llogable{
    protected String matricula;
    protected String marca;
    protected String model;
    protected double preuBase;
    protected Motor motor;
    protected List<Roda> rodes;
    protected String etiquetaAmbiental;

    public Vehicle(String matricula, String marca, String model, double preuBase, Motor motor, List<Roda> rodes) {
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

    public List<Roda> getRodes() {
        return rodes;
    }

    public String getEtiquetaAmbiental() {
        return etiquetaAmbiental;
    }

    private String calcularEtiquetaAmbiental() {
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
