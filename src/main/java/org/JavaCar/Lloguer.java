package org.JavaCar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Lloguer {
    public enum Estat {
        PENDENT,    // Reserva pendient (futura)
        ACTIU,      // Llogue en curs
        COMPLETAT,  // Finalizat normalmente
        CANCELAT    // Cancelado
    }

    private String id;
    private String dniClient;
    private String matricula;
    private String dataInici;
    private String dataFinal;
    private int dies;
    private double preuTotal;
    private Estat estat;

    // Constructor per llogar un vehicle avui
    public Lloguer(String dniClient, String matricula, int dies) {
        this(dniClient, matricula, new SimpleDateFormat("dd/MM/yyyy").format(new Date()), dies);
    }

    // Constructor
    public Lloguer(String dniClient, String matricula, String dataInici, int dies) {
        this.id = generarId();
        this.dniClient = dniClient;
        this.matricula = matricula;
        this.dataInici = dataInici;
        this.dies = dies;
        this.dataFinal = calcularDataFinal(dataInici, dies);
        this.estat = id.startsWith("RES-") ? Estat.PENDENT : Estat.ACTIU;
    }

    // Genera un ID únic per a cada lloguer
    private String generarId() {
        return (id == null) ? "LL" + System.currentTimeMillis() : id;
    }

    // Calcula la data final a partir de la inicial i els dies
    private String calcularDataFinal(String dataInici, int dies) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(dataInici);
            long ms = date.getTime() + (long) dies * 24 * 60 * 60 * 1000;
            return sdf.format(new Date(ms));
        } catch (Exception e) {
            System.err.println("Error calculando fecha final: " + e.getMessage());
            return dataInici;
        }
    }
