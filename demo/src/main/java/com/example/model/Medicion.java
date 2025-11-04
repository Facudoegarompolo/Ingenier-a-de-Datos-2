package com.example.model;

import java.util.Date;
import java.util.UUID;

public class Medicion {

    private UUID medicionId;
    private UUID sensorId;
    private Date fechaHora;

    private String pais;
    private String ciudad;
    private String estado;

    private int temperatura;
    private int humedad;

    public Medicion() {}

    // constructor + getters + setters
}
