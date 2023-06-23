package edu.prog2.model;

import org.json.JSONObject;

public class ReservaVuelo implements IFormatCSV {
    private Boolean checkIn;
    private Vuelo vuelo;
    private Reserva reserva;
    private Silla silla;

    public ReservaVuelo() {

    }

    public ReservaVuelo(String json) {
        this(new JSONObject(json));
    }

    public ReservaVuelo(Reserva reserva, Vuelo vuelo, Silla silla) {
        if (!silla.getAvion().equals(vuelo.getAvion())) {
            throw new IllegalArgumentException(
                    "La silla reservada no corresponde a una silla del avión del vuelo");
        }
        this.reserva = reserva;
        this.vuelo = vuelo;
        this.silla = silla instanceof SillaEjecutiva ? new SillaEjecutiva((SillaEjecutiva) silla) : new Silla(silla);
        checkIn = false;
    }

    public ReservaVuelo(JSONObject json) {
        this(
                new Reserva(json.getJSONObject("reserva")),
                new Vuelo(json.getJSONObject("vuelo")),
                json.getJSONObject("silla").has("menu") ? new SillaEjecutiva(json.getJSONObject("silla"))
                        : new Silla(json.getJSONObject("silla")));
        setCheckIn(json.getBoolean("checkIn"));
    }

    public ReservaVuelo(Reserva reserva, Vuelo vuelo, Silla silla, Boolean checkIn) {
        this(reserva, vuelo, silla);
        this.checkIn = checkIn;
    }

    public Reserva getReserva() {
        return this.reserva;
    }

    public Vuelo getVuelo() {
        return this.vuelo;
    }

    public String getCheckInString() {
        return (checkIn) ? "Confirmado" : "Pendiente";
    }

    public Boolean getCheckIn() {
        return checkIn;
    }

    public Silla getSilla() {
        return this.silla;
    }

    public Reserva setReserva() {
        return this.reserva;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public void setCheckIn(boolean checkIn) {
        this.checkIn = checkIn;
    }

    public void setSilla(Silla silla) {
        this.silla = silla;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ReservaVuelo)) {
            return false;
        }
        ReservaVuelo p = (ReservaVuelo) obj;
        return vuelo.equals(p.vuelo) && silla.equals(p.silla);
    }

    @Override
    public String toCSV() {
        if (silla instanceof SillaEjecutiva) {
            SillaEjecutiva aux = (SillaEjecutiva) silla;
            return String.format("%s;%s;%s;%s;%s;%s;%s;%c;%b;%b;%s;%s%n", reserva.getFechaHoraSinStr(),
                    reserva.getPasajero().getIdentifiacion(),
                    vuelo.getFechaHora(), vuelo.getTrayecto().getOrigen(), vuelo.getTrayecto().getDestino(),
                    vuelo.getAvion().getMatricula(),
                    silla.getFila(), silla.getColumna(), silla.getDisponible(), checkIn, aux.getMenu(), aux.getLicor());
        } else {
            return String.format("%s;%s;%s;%s;%s;%s;%s;%c;%b;%b%n", reserva.getFechaHoraSinStr(),
                    reserva.getPasajero().getIdentifiacion(),
                    vuelo.getFechaHora(), vuelo.getTrayecto().getOrigen(), vuelo.getTrayecto().getDestino(),
                    vuelo.getAvion().getMatricula(),
                    silla.getFila(), silla.getColumna(), silla.getDisponible(), checkIn);
        }
    }

    @Override
    public String toString() {
        String fecha = String.valueOf(vuelo.strFechaHora()).replaceAll("T", " ");
        if (silla instanceof SillaEjecutiva) {
            SillaEjecutiva aux = (SillaEjecutiva) silla;

            return String.format(
                    "Fecha y hora: %s - Estado: %s - Checkin: %s \n Avion: %s - %s, Silla: %s-%s, menú: %s, licor: %s%n",
                    fecha,
                    reserva.getCanceladaString(), getCheckInString(), vuelo.getAvion().getMatricula(),
                    vuelo.getAvion().getModelo(), aux.getPosicion(),
                    "Ejecutiva", aux.getMenu(), aux.getLicor());
        } else {
            return String.format(
                    "Fecha y hora: %s - Estado: %s - Checkin: %s \n Avion: %s - %s, Silla: %s-%s%n",
                    fecha,
                    reserva.getCanceladaString(),
                    getCheckInString(), vuelo.getAvion().getMatricula(),
                    vuelo.getAvion().getModelo(), silla.getPosicion(), "Economica");
        }
    }

}