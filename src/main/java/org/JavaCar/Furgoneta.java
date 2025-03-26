package org.JavaCar;

public class Furgoneta extends Vehicle{
    // Atribut
    private double capacitatCarga;

    // Constants per als càlculs de preu
    private static final double MAX_CARGA_SENSE_RECARREG = 1000.0;
    private static final double RECARREG_PER_DIA = 10.0;

    // Constructor
    public Furgoneta(String matricula, String marca, String model, double preuBase, double capacitatCarga, Motor motor, Roda[] rodes) {
        super(matricula, marca, model, preuBase, motor, rodes);
        this.capacitatCarga = capacitatCarga;
    }

    // Getter
    public double getCapacitatCarga() {
        return capacitatCarga;
    }

    // Calcula el preu de lloguer amb recàrrec per gran capacitat (>1000kg)
    @Override
    public double calcularPreu(int dies) {
        if (dies <= 0) {
            throw new IllegalArgumentException("El nombre de dies ha de ser positiu");
        }

        double preuFinal = preuBase * dies;
        if (capacitatCarga > MAX_CARGA_SENSE_RECARREG) {
            double carrecAdicional = RECARREG_PER_DIA * dies;
            preuFinal += carrecAdicional;
        }
        return preuFinal;
    }

    // Converteix les dades de la furgoneta a format string per guardar-ho a un fitxer
    @Override
    public String toFileString() {
        return String.join(";",
                "Furgoneta",
                getMatricula(),
                getMarca(),
                getModel(),
                String.valueOf(getPreuBase()),
                getMotor().getTipus(),
                String.valueOf(getMotor().getPotencia()),
                String.valueOf(getRodes() != null ? getRodes().length : 0),
                String.valueOf(capacitatCarga)
        );
    }

    // Crea una furgoneta a partir d'un array de strings (des de fitxer)
    public static Furgoneta fromFileString(String[] parts) {
        Motor motor = new Motor(parts[5], Integer.parseInt(parts[6]));
        Roda[] rodes = new Roda[Integer.parseInt(parts[7])];
        return new Furgoneta(
                parts[1], parts[2], parts[3],
                Double.parseDouble(parts[4]),
                Double.parseDouble(parts[8]),
                motor, rodes
        );
    }
}
