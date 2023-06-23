package edu.prog2.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.json.JSONObject;

public class Vuelo implements IFormatCSV {
    private LocalDateTime fechaHora;
    private Boolean cancelado;
    private Trayecto trayecto;
    private Avion avion;

    public Vuelo() {

    }

    public Vuelo(JSONObject json) {
        this(
                LocalDateTime.parse(json.getString("fechaHora")),
                new Trayecto(json.getJSONObject("trayecto")),
                new Avion(json.getJSONObject("avion")));
    }

    public Vuelo(String json) {
        this(new JSONObject(json));
    }

    public Vuelo(LocalDateTime fechaHora, Trayecto trayecto, Avion avion) {
        this.fechaHora = fechaHora;
        this.trayecto = new Trayecto(trayecto); // composición
        this.avion = avion;
        this.cancelado = false;
    }

    public Vuelo(LocalDateTime fechaHora, Trayecto trayecto, Avion avion, boolean cancelado) {
        this.fechaHora = fechaHora;
        this.trayecto = new Trayecto(trayecto); // composición
        this.avion = avion;
        this.cancelado = cancelado;
    }

    public Avion getAvion() {
        return avion;
    }

    public Trayecto getTrayecto() {
        return trayecto;
    }

    public String getCancelado2() {
        return (cancelado) ? "Si" : "No";
    }

    public Boolean getCancelado() {
        return cancelado;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String strFechaHora() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return fechaHora.format(formatter);
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public void setCancelada(boolean cancelado) {
        this.cancelado = cancelado;
    }

    public void setTrayecto(Trayecto trayecto) {
        this.trayecto = trayecto;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Vuelo)) {
            return false;
        }
        Vuelo p = (Vuelo) obj;
        return trayecto.equals(p.trayecto) && avion.equals(p.avion)
                && (fechaHora.truncatedTo(ChronoUnit.MINUTES).plusMinutes(1))
                        .equals(p.fechaHora.truncatedTo(ChronoUnit.MINUTES).plusMinutes(1));
    }

    @Override
    public String toCSV() {
        return String.format("%s;%s;%s;%s;%b%n", fechaHora, trayecto.getOrigen(), trayecto.getDestino(),
                avion.getMatricula(), cancelado);
    }

    @Override
    public String toString() {
        return String.format("%-8s%-15s%-20s%-7.0f%-7s%s", avion.getMatricula(), trayecto.getOrigen(),
                trayecto.getDestino(), trayecto.getCosto(), trayecto.getDuracionS(), strFechaHora());
    }

}
