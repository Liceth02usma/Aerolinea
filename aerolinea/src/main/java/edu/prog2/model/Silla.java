package edu.prog2.model;

import org.json.JSONObject;

public class Silla implements IFormatCSV {
    protected int fila;
    protected char columna;
    protected boolean disponible;
    protected Avion avion;
    protected Ubicacion ubicacion;

    public Silla() {

    }

    public Silla(String json) {
        this(new JSONObject(json));
    }

    public Silla(JSONObject json) {
        this(
                json.getInt("fila"),
                json.getString("columna").charAt(0),
                new Avion(json.getJSONObject("avion")));
        setDisponible(json.getBoolean("disponible"));
    }

    public Silla(int fila, char columna, Avion avion) {
        columna = Character.toUpperCase(columna);
        if (columna == 'A' || columna == 'F') {
            ubicacion = Ubicacion.VENTANA;
        } else if (columna == 'B' || columna == 'E') {
            ubicacion = Ubicacion.CENTRAL;
        } else if (columna == 'C' || columna == 'D') {
            ubicacion = Ubicacion.PASILLO;
        } else {
            throw new IndexOutOfBoundsException("Columna erronea ");
        }
        this.avion = avion;
        this.fila = fila;
        this.columna = columna;
        disponible = true;

    }

    public Silla(Silla silla) {
        this(silla.fila, silla.columna, silla.avion);
    }

    public boolean getDisponible() {
        return disponible;

    }

    public String getDisponibleString() {
        return (disponible) ? "Si" : "No";

    }

    public Avion getAvion() {
        return avion;

    }

    public int getFila() {
        return fila;

    }

    public char getColumna() {
        return columna;

    }

    public Ubicacion getUbicacion() {
        return ubicacion;

    }

    public String getPosicion() {
        return String.format("%02d%c", fila, columna); // otra forma de imprimir patron de entero %d02 patron de char %c
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setPosicion(int fila, char columna) {
        this.fila = fila;
        columna = Character.toUpperCase(columna);
        this.columna = columna;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Silla)) {
            return false;
        }
        Silla p = (Silla) obj;
        return getPosicion().equals(p.getPosicion()) && avion.equals(p.avion);
    }

    @Override
    public String toCSV() {
        return String.format("%s;%s;%s;%s;%s%n", avion.getMatricula(), fila, columna, ubicacion, disponible);
    }

    @Override
    public String toString() {
        return String.format("%-10s%-10s%-10s", getPosicion(), ubicacion, getDisponibleString());
    }

}