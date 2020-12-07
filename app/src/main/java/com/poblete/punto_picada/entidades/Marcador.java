package com.poblete.punto_picada.entidades;
/*
    Version by Draigh on 25/11/2020.
 */


public class Marcador {
    private String id;
    private double latitud;
    private double longitud;
    private String name;
    private String descripcion;

    public Marcador() {
    }

    public Marcador(String id, double latitud, double longitud, String name, String descripcion) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.name = name;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
