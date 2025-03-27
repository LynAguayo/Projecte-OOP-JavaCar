package org.JavaCar;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LloguerService {
    // Constants
    private static final String DATA_DIR = "data";
    private static final String LLOGUERS_FILE = DATA_DIR + "/lloguers.txt";
    private static final String VALORACIONS_FILE = DATA_DIR + "/valoracions.txt";

    // Serveis
    private final VehicleService vehicleService;

    // Constructor
    public LloguerService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
        crearDirectoriSiNoExisteix();
    }

    // Crea el directori si no existeix
    private void crearDirectoriSiNoExisteix() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }