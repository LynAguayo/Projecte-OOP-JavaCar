package org.JavaCar;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    // Constants
    private static final String DATA_DIR = "data";
    private static final String CLIENTS_FILE = DATA_DIR + "/clients.txt";

    // SConstants per càlcul de punts
    public static final int PUNTS_PER_DIA_ECO = 10;       // Punts per día de ECO
    public static final int PUNTS_PER_DIA_ZERO_EM = 15;   // Punts per día de 0 Emissions
    public static final int PUNTS_PER_100_EURO = 5;       // Punts per cada 100€ gastats
    public static final int MIN_PUNTS_CANVI = 100;        // Mínim punts per canjear
    public static final double EUROS_PER_CANVI = 5.0;     // € de descompte per cada 100 punts

    // Constructor que crea el directori si no existeix
    public ClientService() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }
