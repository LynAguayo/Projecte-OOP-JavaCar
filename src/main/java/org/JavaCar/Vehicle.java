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

    // Valida el format de la matrícula
    private void validarMatricula(String matricula) {
        if (matricula == null || !matricula.matches(PATRO_MATRICULA)) {
            throw new IllegalArgumentException(
                    "Matrícula invàlida. Ha de tenir 4 números seguits de 3 lletres majúscules. Exemple: 1234ABC"
            );
        }
    }

    // Mètode principal per calcular l'etiqueta ambiental
    private String calcularEtiquetaAmbiental() {
        if (motor == null) {
            return EtiquetaAmbiental.SENSE_ETIQUETA.toString();
        }

        int anyMatriculacio = estimarAnyMatriculacioPerMatricula(matricula);
        int euroStandard = estimarEuroStandard(anyMatriculacio);
        String tipusMotor = motor.getTipus().toLowerCase();

        // Vehicles elèctrics
        if (tipusMotor.equals("electric")) {
            return EtiquetaAmbiental.ZERO_EMISSIONS.toString();
        }

        // Càlcul per cada tipus de vehicle
        if (this instanceof Cotxe) {
            return calcularEtiquetaCotxe(tipusMotor, euroStandard);
        }

        if (this instanceof Furgoneta) {
            return calcularEtiquetaFurgoneta(tipusMotor, euroStandard);
        }

        if (this instanceof Moto) {
            return calcularEtiquetaMoto(tipusMotor, euroStandard);
        }

        // Vehícle de més de 8 places
        if (this instanceof Cotxe && ((Cotxe) this).getNombrePlaces() > 8) {
            return calcularEtiquetaVehiclesGrans(euroStandard);
        }

        return EtiquetaAmbiental.SENSE_ETIQUETA.toString();
    }

    // Recalcula l'etiqueta ambiental
    public void recalcularEtiquetaAmbiental() {
        this.etiquetaAmbiental = calcularEtiquetaAmbiental();
    }

    // Calcula etiqueta per a cotxes
    private String calcularEtiquetaCotxe(String tipusMotor, int euroStandard) {
        if (tipusMotor.equals("gasolina")) {
            if (euroStandard >= 4) {
                return EtiquetaAmbiental.C.toString();
            } else if (euroStandard == 3) {
                return EtiquetaAmbiental.B.toString();
            } else {
                return EtiquetaAmbiental.SENSE_ETIQUETA.toString();
            }
        }

        if (tipusMotor.equals("diesel")) {
            if (euroStandard >= 6) {
                return EtiquetaAmbiental.C.toString();
            } else if (euroStandard >= 4) {
                return EtiquetaAmbiental.B.toString();
            } else {
                return EtiquetaAmbiental.SENSE_ETIQUETA.toString();
            }
        }

        if (tipusMotor.equals("hibrid") || tipusMotor.equals("gnc") || tipusMotor.equals("glp")) {
            if (euroStandard >= 4) {
                return EtiquetaAmbiental.ECO.toString();
            } else {
                return EtiquetaAmbiental.SENSE_ETIQUETA.toString();
            }
        }

        return EtiquetaAmbiental.SENSE_ETIQUETA.toString();
    }

    // Calcula etiqueta per a furgonetes
    private String calcularEtiquetaFurgoneta(String tipusMotor, int euroStandard) {
        if (tipusMotor.equals("diesel")) {
            if (euroStandard >= 6) {
                return EtiquetaAmbiental.C.toString();
            } else if (euroStandard >= 4) {
                return EtiquetaAmbiental.B.toString();
            } else {
                return EtiquetaAmbiental.SENSE_ETIQUETA.toString();
            }
        }

        if (tipusMotor.equals("hibrid") || tipusMotor.equals("gnc") || tipusMotor.equals("glp")) {
            if (euroStandard >= 6) {
                return EtiquetaAmbiental.ECO.toString();
            } else {
                return EtiquetaAmbiental.SENSE_ETIQUETA.toString();
            }
        }

        return EtiquetaAmbiental.SENSE_ETIQUETA.toString();
    }

    // Calcula etiqueta per a motos
    private String calcularEtiquetaMoto(String tipusMotor, int euroStandard) {
        if (tipusMotor.equals("gasolina")) {
            if (euroStandard >= 4) {
                return EtiquetaAmbiental.C.toString();
            } else if (euroStandard == 3) {
                return EtiquetaAmbiental.C.toString();
            } else if (euroStandard == 2) {
                return EtiquetaAmbiental.B.toString();
            } else {
                return EtiquetaAmbiental.SENSE_ETIQUETA.toString();
            }
        }

        if (tipusMotor.equals("hibrid")) {
            return EtiquetaAmbiental.ECO.toString();
        }

        return EtiquetaAmbiental.SENSE_ETIQUETA.toString();
    }

    // Calcula etiqueta per a vehicles grans (+8 places)
    private String calcularEtiquetaVehiclesGrans(int euroStandard) {
        if (euroStandard >= 6) {
            return EtiquetaAmbiental.C.toString();
        } else if (euroStandard >= 4) {
            return EtiquetaAmbiental.B.toString();
        } else {
            return EtiquetaAmbiental.SENSE_ETIQUETA.toString();
        }
    }

    // Estima l'any de matriculació a partir de la matrícula
    private int estimarAnyMatriculacioPerMatricula(String matricula) {
        try {
            int any = Integer.parseInt(matricula.substring(0, 2));
            if (any <= ANY_TRANSICIO_SEGLE) {
                return ANY_BASE + any; // matricules 00-30 --> 2000-2030
            } else {
                return ANY_BASE_ANTIC + any; // // matrícules 31-99 --> 1931-1999
            }
        } catch (Exception e) {
            return 2000; // per defecte
        }
    }

    // Determina l'estàndard Euro segons l'any
    private int estimarEuroStandard(int anyMatriculacio) {
        if (anyMatriculacio < EURO_2_MAX_ANY) {
            return 2;
        } else if (anyMatriculacio < EURO_3_MAX_ANY) {
            return 3;
        } else if (anyMatriculacio < EURO_4_MAX_ANY) {
            return 4;
        } else if (anyMatriculacio < EURO_5_MAX_ANY) {
            return 5;
        } else {
            return 6; // 2015 o posterior
        }
    }

}
