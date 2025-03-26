package org.JavaCar;

// Representa les etiquetes ambientals segons la DGT
public enum EtiquetaAmbiental {
    ZERO_EMISSIONS("0 Emissions"), // vehicles elèctrics purs, hidrogen
    ECO("ECO"), // vehicles ecològics (híbrids, GNC, GLP)
    C("C"), // vehicles emissions mitjanes (gasolina Euro 4+/dièsel Euro 6)
    B("B"), // vehicles amb emissions altes (gasolina Euro 3/dièsel Euro 4-5)
    SENSE_ETIQUETA("Sense Etiqueta"); // vehicles més contaminants

    // Atribut
    private final String nom;

    // Constructor
    EtiquetaAmbiental(String nom) {
        this.nom = nom;
    }

    // Representació en format String
    @Override
    public String toString() {
        return nom;
    }
}