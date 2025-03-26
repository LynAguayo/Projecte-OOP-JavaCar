package org.JavaCar;

public class Cotxe extends Vehicle{
    // Atribut
    private int nombrePlaces;

    // Constructor
    public Cotxe(String matricula, String marca, String model, double preuBase, int nombrePlaces, Motor motor, Roda[] rodes) {
        super(matricula, marca, model, preuBase, motor, rodes);
        this.nombrePlaces = nombrePlaces;
    }

    // Getter
    public int getNombrePlaces() {
        return nombrePlaces;
    }

    // Calcula el preu de lloguer segons els dies
    @Override
    public double calcularPreu(int dies) {
        return preuBase * dies;
    }

    // Converteix les dades del cotxe a string per guardar-ho a un fitxer
    @Override
    public String toFileString() {
        return String.join(";",
                "Cotxe",
                getMatricula(),
                getMarca(),
                getModel(),
                String.valueOf(getPreuBase()),
                getMotor().getTipus(),
                String.valueOf(getMotor().getPotencia()),
                String.valueOf(getRodes() != null ? getRodes().length : 0),
                String.valueOf(nombrePlaces)
        );
    }

    // Crea un cotxe a partir d'un array de strings (des de fitxer)
    public static Cotxe fromFileString(String[] parts) {
        Motor motor = new Motor(parts[5], Integer.parseInt(parts[6]));
        Roda[] rodes = new Roda[Integer.parseInt(parts[7])];
        return new Cotxe(
                parts[1], parts[2], parts[3],
                Double.parseDouble(parts[4]),
                Integer.parseInt(parts[8]),
                motor, rodes
        );
    }
}
