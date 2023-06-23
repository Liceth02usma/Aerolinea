package edu.prog2.services;

import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;

import edu.prog2.helpers.UtilFiles;
import edu.prog2.model.Pasajero;
import edu.prog2.model.Reserva;
import edu.prog2.model.ReservaVuelo;

public class ReservasService {
    private ArrayList<Reserva> reservas;
    private PasajerosService pasajeros;
    private String fileName;

    public ReservasService(PasajerosService pasajeros) throws IOException {
        this.pasajeros = pasajeros;
        reservas = new ArrayList<>();
        fileName = UtilFiles.FILE_PATH + "reservas";

        if (UtilFiles.fileExists(fileName + ".csv")) {
            loadCSV();
        } else if (UtilFiles.fileExists(fileName + ".json")) {
            loadJSON();
        } else {
            System.out.println("Aún no se ha creado un archivo: " + fileName);
        }
    }

    public boolean add(Reserva reserva) throws IOException {
        if (contains(reserva)) {
            throw new ArrayStoreException(
                    String.format("la reserva ya existe"));
        }

        boolean ok = reservas.add(reserva);
        UtilFiles.writeData(reservas, fileName);
        return ok;
    }

    public boolean contains(Reserva reserva) {
        return reservas.contains(reserva);
    }

    public Reserva get(int index) {
        return reservas.get(index);
    }

    public void remove(String params) throws Exception {
        JSONObject json = get(params);
        System.out.println(json.toString(2));
        Reserva reserva = new Reserva(LocalDateTime.parse(json.getString("fechaHoraSinStr")),
                new Pasajero(json.getJSONObject("pasajero")));

        if ((UtilFiles.exists("reservasVuelos", "reserva", reserva))) {
            throw new Exception(String.format(
                    "No se eliminó la reserva %s, está referenciada en multiples clases", reserva));
        }
        if (!reservas.remove(reserva)) {
            throw new Exception(String.format("No se encontró la reserva %s", reserva));
        }

        UtilFiles.writeData(reservas, fileName);
    }

    public void update() throws IOException {
        reservas = new ArrayList<>();
        loadCSV();
        UtilFiles.writeJSON(reservas, fileName + ".json");
    }

    public Reserva get(Reserva reserva) {
        int index = reservas.indexOf(reserva);
        return index > -1 ? reservas.get(index) : null;
    }

    public ArrayList<Reserva> getList() {
        return reservas;
    }

    public PasajerosService getPasajeros() {
        return pasajeros;
    }

    public void add(JSONObject json) throws IOException {
        LocalDateTime fechaHora = LocalDateTime.parse(
                json.getString("fechaHora"));

        Pasajero pasajero = new Pasajero(json.getString("pasajero"), "", "");

        pasajero = pasajeros.get(pasajero);

        add(new Reserva(fechaHora, pasajero));
    }

    public ArrayList<Reserva> loadJSON() throws IOException { // metodo objetivo
        reservas = new ArrayList<>();
        String data = UtilFiles.readText(fileName + ".json");
        JSONArray jsonArr = new JSONArray(data);

        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            reservas.add(new Reserva(jsonObj));
        }

        return reservas;
    }

    public JSONArray getJSON() throws IOException {
        return new JSONArray(UtilFiles.readText(fileName + ".json"));
    }

    public JSONObject getJSON(int index) {
        return new JSONObject(reservas.get(index));
    }

    public JSONObject getJSON(Reserva reserva) {
        int index = reservas.indexOf(reserva);
        return index > -1 ? getJSON(index) : null;
    }

    public JSONObject set(JSONObject json) throws IOException {
        Reserva reserva = new Reserva(LocalDateTime.parse(json.getString("fechaHora")),
                new Pasajero(json.getString("pasajero"), "", ""));
        // referenciar la instancia a actualizar
        reserva = get(reserva);
        reserva.setCancelada(json.getBoolean("cancelada"));
        // intentar actualizar el costo y la duración de la referencia

        UtilFiles.writeData(reservas, fileName);
        return new JSONObject(reserva);
    }

    public JSONObject get(String paramsVuelo) throws Exception {

        JSONObject json = UtilFiles.paramsToJson(paramsVuelo);

        Pasajero pasajero = pasajeros.get(new Pasajero(json.getString("pasajero"), "", ""));

        LocalDateTime fechaHora = LocalDateTime.parse(json.getString("fechaHora"));
        Reserva reserva = get(new Reserva(fechaHora, pasajero));
        if (reserva == null) {
            throw new Exception(String.format("No se encontró la reserva"));
        }
        return getJSON(reserva);
    }

    @SuppressWarnings("unchecked") // ver esto…
    public void listAll(ReservasVuelosService reservasVuelos) throws Exception {
        ArrayList<String> list = new ArrayList<String>();
        String encabezado = "F. RESERVA              ID         NOMBRE            APELLIDO         ESTADO       ";
        for (Reserva r : reservas) {
            list.add(r.toString());
            list.add("          ");
            list.add("VUELOS DE RESERVA");
            ArrayList<ReservaVuelo> reservaVuelo = reservasVuelos.getList();
            for (ReservaVuelo rv : reservaVuelo) {
                if (rv.getReserva().equals(r)) {
                    list.add(rv.toString());
                }
            }
        }
        int length = encabezado.length() + 9;
        String title = "LISTADO DE RESERVAS CON VUELOS RESERVADOS";
        list.add(0, " ".repeat((length - title.length()) / 2) + title);
        list.add(1, "-".repeat(length));
        list.add(2, encabezado);
        list.add(3, "-".repeat(length));
        list.add("-".repeat(length));

        UtilFiles.writeText(list, fileName + ".txt");
    }

    private void loadCSV() throws IOException {
        String text = UtilFiles.readText(fileName + ".csv");

        try (Scanner sc = new Scanner(text).useDelimiter(";|[\n]+|[\r\n]+")) {
            while (sc.hasNext()) {
                LocalDateTime fechaHora = LocalDateTime.parse(sc.next());
                Pasajero pasajero = pasajeros.get(new Pasajero(sc.next(), "", ""));
                boolean cancelada = sc.nextBoolean();
                reservas.add(new Reserva(fechaHora, pasajero, cancelada));
                sc.nextLine();
            }
        }
    }

}
