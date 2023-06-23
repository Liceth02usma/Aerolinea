package edu.prog2.services;

import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import edu.prog2.helpers.UtilFiles;
import edu.prog2.model.Pasajero;

public class PasajerosService {
    private ArrayList<Pasajero> pasajeros;
    private String fileName;

    public PasajerosService() throws IOException {
        pasajeros = new ArrayList<>();
        fileName = UtilFiles.FILE_PATH + "pasajeros";

        if (UtilFiles.fileExists(fileName + ".csv")) {
            loadCSV();
        } else if (UtilFiles.fileExists(fileName + ".json")) {
            loadJSON();
        } else {
            System.out.println("Aún no se ha creado un archivo: " + fileName);
        }
    }

    public boolean add(Pasajero pasajero) throws IOException {
        if (contains(pasajero)) {
            System.out.printf(
                    "No agregado, el pasajero %s ya existe%n",
                    pasajero.getIdentifiacion());
        }

        boolean ok = pasajeros.add(pasajero);
        UtilFiles.writeData(pasajeros, fileName);
        return ok;
    }

    public boolean contains(Pasajero pasajero) {
        return pasajeros.contains(pasajero);
    }

    public Pasajero get(int index) {
        return pasajeros.get(index);
    }

    public Pasajero get(Pasajero pasajero) {
        int index = pasajeros.indexOf(pasajero);
        return index > -1 ? pasajeros.get(index) : null;
    }

    public ArrayList<Pasajero> loadJSON() throws IOException {
        pasajeros = new ArrayList<>();
        String data = UtilFiles.readText(fileName + ".json");
        JSONArray jsonArr = new JSONArray(data);

        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            pasajeros.add(new Pasajero(jsonObj));
        }

        return pasajeros;
    }

    public JSONObject set(String identificacion, JSONObject json) throws IOException {
        Pasajero pasajero = new Pasajero(json);
        pasajero.setIdentificacion(identificacion);
        System.out.println(pasajero.getIdentifiacion());
        int index = pasajeros.indexOf(pasajero);
        pasajeros.set(index, pasajero);
        UtilFiles.writeData(pasajeros, fileName);
        return new JSONObject(pasajero);
    }

    public ArrayList<Pasajero> getList() {
        return pasajeros;
    }

    public void update() throws IOException {
        pasajeros = new ArrayList<>();
        loadCSV();
        UtilFiles.writeJSON(pasajeros, fileName + ".json");
    }

    public JSONArray getJSON() throws IOException {
        return new JSONArray(UtilFiles.readText(fileName + ".json"));
    }

    public JSONObject getJSON(int index) {
        return new JSONObject(pasajeros.get(index));
    }

    public void remove(String identificacion) throws Exception {
        Pasajero pasajero = new Pasajero(identificacion, "", "");
        if (UtilFiles.exists("reservas", "pasajero", pasajero)) {
            throw new Exception(String.format(
                    "No se eliminó el pasajero %s, porque tiene reservas", identificacion));
        }
        if (!pasajeros.remove(pasajero)) {
            throw new Exception(String.format(
                    "No se encontró el pasajero con identificación %s", identificacion));
        }

        UtilFiles.writeData(pasajeros, fileName);
    }

    public JSONObject getJSON(Pasajero pasajero) {
        int index = pasajeros.indexOf(pasajero);
        return index > -1 ? getJSON(index) : null;
    }

    public JSONObject get(String paramsVuelo) throws IOException {

        JSONObject json = UtilFiles.paramsToJson(paramsVuelo);

        return getJSON(new Pasajero(json.getString("id"), "", ""));
    }

    @SuppressWarnings("unchecked") // ver esto…
    public void listAll() throws Exception {
        ArrayList<String> list = (ArrayList<String>) (ArrayList<?>) (pasajeros);
        String encabezado = "ID.       NOMBRES              APELLIDOS";
        int length = encabezado.length() + 9;
        String title = "LISTADO DE PASAJEROS";
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
                String identificacion = sc.next();
                String nombres = sc.next();
                String apellidos = sc.next();
                pasajeros.add(new Pasajero(identificacion, nombres, apellidos));
                sc.nextLine();
            }
        }
    }

}
