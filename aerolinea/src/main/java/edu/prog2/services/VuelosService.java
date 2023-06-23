package edu.prog2.services;

import java.util.ArrayList;
import edu.prog2.model.*;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.prog2.helpers.UtilFiles;

public class VuelosService {
    private ArrayList<Vuelo> vuelos;
    private AvionesService aviones;
    private TrayectosService trayectos;
    private String fileName;

    public VuelosService(TrayectosService trayectos, AvionesService aviones) throws IOException {
        this.trayectos = trayectos;
        this.aviones = aviones;
        vuelos = new ArrayList<>();
        fileName = UtilFiles.FILE_PATH + "vuelos";

        if (UtilFiles.fileExists(fileName + ".csv")) {
            loadCSV();
        } else if (UtilFiles.fileExists(fileName + ".json")) {
            loadJSON();
        } else {
            System.out.println("Aún no se ha creado un archivo: " + fileName);
        }
    }

    public JSONObject get(String paramsVuelo) throws IOException {

        JSONObject json = UtilFiles.paramsToJson(paramsVuelo);

        Avion avion = aviones.get(new Avion(json.getString("avion"), ""));

        Trayecto trayecto = trayectos.get(
                new Trayecto(
                        json.getString("origen"), json.getString("destino"), Duration.ZERO, 0.0));

        LocalDateTime fechaHora = LocalDateTime.parse(json.getString("fechaHora"));
        Vuelo vuelo = get(new Vuelo(fechaHora, trayecto, avion));
        if (vuelo == null) {
            throw new IOException("Ese vuelo no existe no invente");
        }
        return getJSON(vuelo);
    }

    public boolean add(Vuelo vuelo) throws IOException {
        if (contains(vuelo)) {
            throw new ArrayStoreException(
                    String.format("El vuelo ya existe"));
        }

        boolean ok = vuelos.add(vuelo);
        UtilFiles.writeData(vuelos, fileName);
        return ok;
    }

    public void add(JSONObject json) throws IOException {
        LocalDateTime fechaHora = LocalDateTime.parse(
                json.getString("fechaHora"));

        Trayecto trayecto = new Trayecto(
                json.getString("origen"),
                json.getString("destino"),
                Duration.ZERO, 0.0);

        trayecto = trayectos.get(trayecto);
        Avion avion = aviones.get(new Avion(json.getString("avion"), ""));

        add(new Vuelo(fechaHora, trayecto, avion));
    }

    public JSONArray getVuelo(String params) throws IOException {
        JSONArray data = new JSONArray();
        String[] params2 = params.split("&");
        String origen = params2[0];
        String destino = params2[1];

        Trayecto trayecto = new Trayecto(origen, destino, null, 0.0);

        for (Vuelo v : vuelos) {
            if (v.getTrayecto().equals(trayecto)) {
                data.put(new JSONObject(v));
            }
        }

        return data;
    }

    public JSONObject set(JSONObject json) throws IOException {
        Vuelo vuelo = new Vuelo(LocalDateTime.parse(json.getString("fechaHora")),
                new Trayecto(json.getString("origen"), json.getString("destino"), null, 0.0),
                new Avion(json.getString("avion"), ""));
        // referenciar la instancia a actualizar
        vuelo = get(vuelo);
        vuelo.setCancelada(json.getBoolean("cancelado"));
        // intentar actualizar el costo y la duración de la referencia

        UtilFiles.writeData(vuelos, fileName);
        return new JSONObject(vuelo);
    }

    public void remove(String params) throws Exception {
        JSONObject json = get(params);
        Vuelo vuelo = new Vuelo(json);
        System.out.println(vuelo);
        if ((UtilFiles.exists("reservasVuelos", "vuelo", vuelo))) {
            throw new Exception(String.format(
                    "No se eliminó el avion %s, está referenciado en multiples clases", vuelo));
        }
        if (!vuelos.remove(vuelo)) {
            throw new Exception(String.format("No se encontró el trayecto %s", vuelo));
        }

        UtilFiles.writeData(vuelos, fileName);
    }

    public Vuelo get(Vuelo vuelo) {
        int index = vuelos.indexOf(vuelo);
        return index > -1 ? vuelos.get(index) : null;
    }

    public boolean contains(Vuelo vuelo) {
        return vuelos.contains(vuelo);
    }

    public AvionesService getAviones() {
        return aviones;
    }

    public TrayectosService getTrayectos() {
        return trayectos;
    }

    public Vuelo get(int index) {
        return vuelos.get(index);
    }

    public ArrayList<Vuelo> getList() {
        return vuelos;
    }

    public ArrayList<Vuelo> loadJSON() throws IOException {
        vuelos = new ArrayList<>();
        String data = UtilFiles.readText(fileName + ".json");
        JSONArray jsonArr = new JSONArray(data);

        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            vuelos.add(new Vuelo(jsonObj));
        }

        return vuelos;
    }

    public void update() throws IOException {
        vuelos = new ArrayList<>();
        loadCSV();
        UtilFiles.writeJSON(vuelos, fileName + ".json");
    }

    public JSONArray getJSON() throws IOException {
        return new JSONArray(UtilFiles.readText(fileName + ".json"));
    }

    public JSONObject getJSON(int index) {
        return new JSONObject(vuelos.get(index));
    }

    public JSONObject getJSON(Vuelo vuelo) {
        int index = vuelos.indexOf(vuelo);
        return index > -1 ? getJSON(index) : null;
    }

    @SuppressWarnings("unchecked") // ver esto…
    public void listAll() throws Exception {
        ArrayList<String> list = (ArrayList<String>) (ArrayList<?>) (vuelos);
        int length = 72;
        String title = "LISTADO DE VUELOS";

        list.add(0, " ".repeat((length - title.length()) / 2) + title);
        list.add(1, "-".repeat(length));
        list.add(2, "MATR.   ORIGEN         DESTINO            PRECIO  DUR.    FECHA Y HORA");
        list.add(3, "-".repeat(length));
        list.add("-".repeat(length));

        UtilFiles.writeText(list, fileName + ".txt");
    }

    private void loadCSV() throws IOException {
        String text = UtilFiles.readText(fileName + ".csv");
        try (Scanner sc = new Scanner(text).useDelimiter(";|[\n]+|[\r\n]+")) {
            while (sc.hasNext()) {
                LocalDateTime fechaHora = LocalDateTime.parse(sc.next());
                String origen = sc.next();
                String destino = sc.next();
                Trayecto trayecto = trayectos.get(new Trayecto(origen, destino, null, 0.0));
                Avion avion = aviones.get(new Avion(sc.next(), ""));
                Vuelo v = new Vuelo(fechaHora, trayecto, avion, sc.nextBoolean());
                vuelos.add(v);
                sc.nextLine();
            }
        }
    }

}
