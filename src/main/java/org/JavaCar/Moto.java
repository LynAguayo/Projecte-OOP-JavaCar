package org.JavaCar;

public class Moto extends Vehicle{
    // Atribut
    private int cilindrada;

    // Constants per al càlcul de preus
    private static final int CILINDRADA_MAXIMA_SENSE_RECARREG = 500;
    private static final double IMPORT_RECARREG_CILINDRADA = 5.0;

    // Constructor
    public Moto(String matricula, String marca, String model, double preuBase, int cilindrada, Motor motor, Roda[] rodes) {
        super(matricula, marca, model, preuBase, motor, rodes);
        this.cilindrada = cilindrada;
    }

    // Getter
    public int getCilindrada() {
        return cilindrada;
    }

    // Calcula el preu de lloguer amb recàrrec per alta cilindrada (>500cc)
    @Override
    public double calcularPreu(int dies) {
        if (dies <= 0) {
            throw new IllegalArgumentException("El nombre de dies ha de ser positiu");
        }

        double preuFinal = preuBase * dies;
        if (cilindrada > CILINDRADA_MAXIMA_SENSE_RECARREG) {
            double carrecAdicional = IMPORT_RECARREG_CILINDRADA * dies;
            preuFinal += carrecAdicional;
        }
        return preuFinal;
    }

    // Converteix les dades de la moto a format string per guardar-ho a un fitxer
    @Override
    public String toFileString() {
        return String.join(";",
                "Moto",
                getMatricula(),
                getMarca(),
                getModel(),
                String.valueOf(getPreuBase()),
                getMotor().getTipus(),
                String.valueOf(getMotor().getPotencia()),
                String.valueOf(getRodes() != null ? getRodes().length : 0),
                String.valueOf(cilindrada)
        );
    }

    // Crea una moto a partir d'un array de strings (des de fitxer)
    public static Moto fromFileString(String[] parts) {
        Motor motor = new Motor(parts[5], Integer.parseInt(parts[6]));
        Roda[] rodes = new Roda[Integer.parseInt(parts[7])];
        return new Moto(
                parts[1], parts[2], parts[3],
                Double.parseDouble(parts[4]),
                Integer.parseInt(parts[8]),
                motor, rodes
        );
    }
}
