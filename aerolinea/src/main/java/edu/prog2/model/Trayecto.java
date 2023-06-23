package edu.prog2.model;

import java.time.Duration;

import org.json.JSONObject;

public class Trayecto implements IFormatCSV {
    private String origen;
    private String destino;
    private double costo;
    private Duration duracion;

    /**
     * Constructor vacio
     */
    public Trayecto() {

    }

    public Trayecto(JSONObject json) {
        this(
                json.getString("origen"),
                json.getString("destino"),
                Duration.parse(json.getString("duracion")),
                json.getDouble("costo"));
    }

    /**
     * Constructor encargado de copiar los valores dados por el argumento.
     * 
     * @param copia la clase
     */
    public Trayecto(Trayecto copia) {
        this(copia.origen, copia.destino, copia.duracion, copia.costo);

    }

    public Trayecto(String json) {
        this(new JSONObject(json));
    }

    /**
     * Cosntructor encargado de asignar el valor de los argumentos a los atributos
     * de clase
     * 
     * @param duracion
     * 
     * @param origen   origen de la persona
     * @param destino  destino de la persona
     * @param costo    costo de la persona
     */
    public Trayecto(String origen, String destino, Duration duracion, Double costo) {
        this.origen = origen;
        this.destino = destino;
        this.costo = costo;
        this.duracion = duracion;

    }

    /**
     * Devuelve el origen de la persona
     * 
     * @return el origen
     */
    public String getOrigen() {
        return this.origen;
    }

    /**
     * Devuelve el destino de la persona
     * 
     * @return destino
     */
    public String getDestino() {
        return this.destino;
    }

    /**
     * Devuelve la duracion del vuelo
     * 
     * @return duracion
     */
    public Duration getDuracion() {
        return this.duracion;
    }

    /**
     * Devuelve la duracion del vuelo con formato
     * 
     * @return duracion
     */
    public String getDuracionS() {
        long hh = duracion.toHours();
        long mm = duracion.toMinutesPart();
        return String.format("%02d:%02d", hh, mm);
    }

    /**
     * Devuelve el costo
     * 
     * @return costo
     */
    public double getCosto() {
        return this.costo;
    }

    /**
     * se le asigna el valor del argumento al atributo origen
     * 
     * @param origen
     */
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    /**
     * se le asigna el valor del argumento al atributo destino
     * 
     * @param destino
     */
    public void setDestino(String destino) {
        this.destino = destino;
    }

    /**
     * se le asigna el valor del argumento al atributo costo
     * 
     * @param d
     */
    public void setCosto(double d) {
        this.costo = d;
    }

    /**
     * se le asigna el valor del argumento al atributo costo
     * 
     * @param duracion
     */
    public void setDuracion(Duration duracion) {
        this.duracion = duracion;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Trayecto)) {
            return false;
        }
        Trayecto p = (Trayecto) obj;
        return origen.equals(p.origen) && destino.equals(p.destino);
    }

    @Override
    public String toCSV() {
        return String.format("%s;%s;%f;%s%n", origen, destino, costo, getDuracion());
    }

    @Override
    public String toString() {
        return String.format("%-17s%-22s%-16.0f%s", origen, destino, costo, getDuracionS());
    }

}
