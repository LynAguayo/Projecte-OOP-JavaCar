package org.JavaCar;

public class Motor {
    // Atributs
    private String tipus;
    private int potencia;

    // Tipus de motor permesos (important perquè s'usa per determinar l'etiqueta ambiental)
    private static final String[] TIPUS_VALIDS = {
            "gasolina", "diesel", "electric", "hibrid", "gnc", "glp"
    };

    // Constructor
    public Motor(String tipus, int potencia) {
        this.tipus = tipus;
        this.potencia = potencia;
    }

    // Getters
    public String getTipus() {
        return tipus;
    }

    public int getPotencia() {
        return potencia;
    }

    // Funció per validar el tipus de motor
    private String validarTipus(String tipus) {
        String tipusLower = tipus.toLowerCase();
        for (String tipoValido : TIPUS_VALIDS) {
            if (tipoValido.equals(tipusLower)) {
                return tipus;
            }
        }
        throw new IllegalArgumentException("Tipus de motor no vàlid. Opcions: gasolina, diesel, electric, hibrid, gnc, glp");
    }

    // Representació en format String
    @Override
    public String toString() {
        return "Motor{" +
                "tipus='" + tipus + '\'' +
                ", potencia=" + potencia +
                '}';
    }
}