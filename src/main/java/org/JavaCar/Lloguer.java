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

    // Mètodes per canviar l'estat
    public void marcarCompletat() {
        this.estat = Estat.COMPLETAT;
    }

    public void cancelar() {
        this.estat = Estat.CANCELAT;
    }

    // Getters i setters
    public String getId() {
        return id;
    }

    public String getDniClient() {
        return dniClient;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getDataInici() {
        return dataInici;
    }

    public String getDataFinal() {
        return dataFinal;
    }

    public int getDies() {
        return dies;
    }

    public double getPreuTotal() {
        return preuTotal;
    }

    public Estat getEstat() {
        return estat;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setPreuTotal(double preuTotal) {
        this.preuTotal = preuTotal;
    }

    public void setDataInici(String dataInici) {
        this.dataInici = dataInici;
    }

    public void setDataFinal(String dataFinal) {
        this.dataFinal = dataFinal;
    }

    public void setDies(int dies) {
        this.dies = dies;
    }

    // Pasa el lloguer a String per guardar en arxiu
    public String toFileString() {
        return String.join(";",
                id, dniClient, matricula,
                dataInici, dataFinal,
                String.valueOf(dies),
                String.valueOf(preuTotal),
                estat.name()
        );
    }

    // Crear un lloguer des d'un String de fitxer
    public static Lloguer fromFileString(String linia) {
        String[] parts = linia.split(";");
        if (parts.length != 8) return null;

        try {
            Lloguer ll = new Lloguer(parts[1], parts[2], parts[3], Integer.parseInt(parts[5]));
            ll.id = parts[0];
            ll.dataFinal = parts[4];
            ll.preuTotal = Double.parseDouble(parts[6]);
            ll.estat = Estat.valueOf(parts[7]);
            return ll;
        } catch (Exception e) {
            System.err.println("Error parsing Lloguer: " + e.getMessage());
            return null;
        }
    }
}