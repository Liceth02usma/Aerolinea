package edu.prog2.services;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.prog2.helpers.UtilFiles;
import edu.prog2.model.Trayecto;

public class TrayectosService {
    private ArrayList<Trayecto> trayectos;
    private String fileName;

    public TrayectosService() throws IOException {
        trayectos = new ArrayList<>();
        fileName = UtilFiles.FILE_PATH + "trayectos";

        if (UtilFiles.fileExists(fileName + ".csv")) {
            loadCSV();
        } else if (UtilFiles.fileExists(fileName + ".json")) {
            loadJSON();
        } else {
            System.out.println("Aún no se ha creado un archivo: " + fileName);
        }
    }

    public boolean add(Trayecto trayecto) throws IOException {
        if (contains(trayecto)) {
            throw new ArrayStoreException(
                    String.format("El trayecto  de %s a %s ya existe", trayecto.getOrigen(), trayecto.getDestino()));
        }

        boolean ok = trayectos.add(trayecto);
        UtilFiles.writeData(trayectos, fileName);
        return ok;
    }

    public boolean contains(Trayecto trayecto) {
        return trayectos.contains(trayecto);
    }

    public Trayecto get(int index) {
        return trayectos.get(index);
    }

    public void remove(String params) throws Exception {
        JSONObject json = get(params);
        Trayecto trayecto = new Trayecto(
                json.getString("origen"), json.getString("destino"), Duration.ZERO, 0.0);

        if (UtilFiles.exists("vuelos", "trayecto", trayecto)) {
            throw new Exception(String.format(
                    "No se eliminó el trayecto %s, está en vuelos", trayecto));
        }
        if (!trayectos.remove(trayecto)) {
            throw new Exception(String.format("No se encontró el trayecto %s", trayecto));
        }

        UtilFiles.writeData(trayectos, fileName);
    }

    public void update() throws IOException {
        trayectos = new ArrayList<>();
        loadCSV();
        UtilFiles.writeJSON(trayectos, fileName + ".json");
    }

    public Trayecto get(Trayecto trayecto) {
        int index = trayectos.indexOf(trayecto);
        return index > -1 ? trayectos.get(index) : null;
    }

    public ArrayList<Trayecto> getList() {
        return trayectos;
    }

    public JSONObject set(JSONObject json) throws IOException {
        Trayecto trayecto = new Trayecto(
                json.getString("origen"), json.getString("destino"), Duration.ZERO, 0.0);
        // referenciar la instancia a actualizar
        trayecto = get(trayecto);
        // intentar actualizar el costo y la duración de la referencia
        trayecto.setCosto(json.getDouble("costo"));
        trayecto.setDuracion(Duration.parse(json.getString("duracion")));
        UtilFiles.writeData(trayectos, fileName);
        return new JSONObject(trayecto);
    }

    public ArrayList<Trayecto> loadJSON() throws IOException {
        trayectos = new ArrayList<>();
        String data = UtilFiles.readText(fileName + ".json");
        JSONArray jsonArr = new JSONArray(data);

        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            trayectos.add(new Trayecto(jsonObj));
        }

        return trayectos;
    }

    public JSONArray getJSON() throws IOException {
        return new JSONArray(UtilFiles.readText(fileName + ".json"));
    }

    public JSONObject getJSON(int index) {
        return new JSONObject(trayectos.get(index));
    }

    public JSONObject getJSON(Trayecto trayecto) {
        int index = trayectos.indexOf(trayecto);
        return index > -1 ? getJSON(index) : null;
    }

    public JSONObject get(String paramsVuelo) throws Exception {

        JSONObject json = UtilFiles.paramsToJson(paramsVuelo);

        Trayecto trayecto = get(
                new Trayecto(
                        json.getString("origen"), json.getString("destino"), Duration.ZERO, 0.0));
        if (trayecto == null) {
            throw new Exception(String.format("No se encontró el trayecto"));
        }

        return getJSON(trayecto);
    }

    @SuppressWarnings("unchecked") // ver esto…
    public void listAll() throws Exception {
        ArrayList<String> list = (ArrayList<String>) (ArrayList<?>) (trayectos);
        String encabezado = "ORIGEN            DESTINO              COSTO          DURACION";
        int length = encabezado.length() + 9;
        String title = "LISTADO DE TRAYECTOS";
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
                String origen = sc.next();
                String destino = sc.next();
                double costo = Double.parseDouble((sc.next().replace(",", ".")));
                Duration duracion = Duration.parse(sc.next());
                trayectos.add(new Trayecto(origen, destino, duracion, costo));
                sc.nextLine();
            }
        }
    }

}
