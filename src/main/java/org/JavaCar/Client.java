public class Client {
    // Atributs
    private String dni;
    private String nom;
    private String adreca;
    private int punts;  // Puntos ecológicos

    // Constructor
    public Client(String dni, String nom, String adreca) {
        this(dni, nom, adreca, 0);
    }

    // Constructor amb punts
    public Client(String dni, String nom, String adreca, int punts) {
        this.dni = dni;
        this.nom = nom;
        this.adreca = adreca;
        this.punts = punts;
    }