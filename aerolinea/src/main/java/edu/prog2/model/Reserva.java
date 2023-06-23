package edu.prog2.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.json.JSONObject;

public class Reserva implements IFormatCSV {
    private LocalDateTime fechaHora;
    private Boolean cancelada;
    private Pasajero pasajero;

    public Reserva() {

    }

    public Reserva(JSONObject json) {
        this(
                LocalDateTime.parse(json.getString("fechaHoraSinStr")),
                new Pasajero(json.getJSONObject("pasajero")),
                json.getBoolean("cancelada"));
    }

    public Reserva(LocalDateTime fechaHora, Pasajero pasajero) {
        this.fechaHora = fechaHora;
        this.pasajero = pasajero;
        cancelada = false;
    }

    public Reserva(String json) {
        this(new JSONObject(json));
    }

    public Reserva(LocalDateTime fechaHora, Pasajero pasajero, boolean cancelada) {
        this.fechaHora = fechaHora;
        this.pasajero = pasajero;
        this.cancelada = cancelada;
    }

    public Reserva(Reserva copia) {
        this(copia.fechaHora, copia.pasajero);
    }

    public String getFechaHora() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return fechaHora.format(formatter);
    }

    public LocalDateTime getFechaHoraSinStr() {
        return fechaHora;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public String getCanceladaString() {
        return (cancelada) ? "Cancelada" : "Vigente";
    }

    public Boolean getCancelada() {
        return cancelada;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public void setCancelada(boolean cancelada) {
        this.cancelada = cancelada;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Reserva)) {
            return false;
        }
        Reserva p = (Reserva) obj;
        return (fechaHora.truncatedTo(ChronoUnit.MINUTES).plusMinutes(1))
                .equals(p.fechaHora.truncatedTo(ChronoUnit.MINUTES).plusMinutes(1)) && pasajero.equals(p.pasajero);
    }

    @Override
    public String toCSV() {
        return String.format("%s;%s;%b%n", fechaHora, pasajero.getIdentifiacion(), getCancelada());
    }

    @Override
    public String toString() {
        return String.format("%-22s %-46s %-36s", getFechaHora(), pasajero, getCanceladaString());// estado
    }
}
