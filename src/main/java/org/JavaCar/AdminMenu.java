package org.JavaCar;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminMenu {
    // Constants per a la gestió de fitxers
    private static final String DATA_DIR = "data";
    private static final String VALORACIONS_FILE = DATA_DIR + "/valoracions.txt";

    // Serveis
    private final VehicleService vehicleService;
    private final LloguerService lloguerService;
    private final ClientService clientService;

    // Constructor
    public AdminMenu(VehicleService vs, LloguerService ls, ClientService cs) {
        this.vehicleService = vs;
        this.lloguerService = ls;
        this.clientService = cs;
        crearDirectoriSiNoExisteix();
    }

    // Crear directori si no existeix
    private void crearDirectoriSiNoExisteix() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }
