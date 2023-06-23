package edu.prog2.services;

import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import edu.prog2.helpers.UtilFiles;
import edu.prog2.model.*;

public class AvionesService {
    private ArrayList<Avion> aviones;
    private String fileName;

    public AvionesService() throws IOException {
        aviones = new ArrayList<>();
        fileName = UtilFiles.FILE_PATH + "aviones";

        if (UtilFiles.fileExists(fileName + ".csv")) {
            loadCSV();
        } else if (UtilFiles.fileExists(fileName + ".json")) {
            loadJSON();
        } else {
            System.out.println("Aún no se ha creado un archivo: " + fileName);
        }
    }

    public boolean add(Avion avion) throws IOException {
        if (contains(avion)) {
            throw new ArrayStoreException(
                    String.format("El avion %s ya existe", avion.getMatricula()));
        }

        boolean ok = aviones.add(avion);
        UtilFiles.writeData(aviones, fileName);
        return ok;
    }

    public boolean contains(Avion avion) {
        return aviones.contains(avion);
    }

    public Avion get(int index) {
        return aviones.get(index);
    }

    public void remove(String params) throws Exception {
        JSONObject json = get(params);
        Avion avion = new Avion(json.getString("matricula"), "");

        if ((UtilFiles.exists("vuelos", "avion", avion))) {
            throw new Exception(String.format(
                    "No se eliminó el avion %s, está referenciado en multiples clases", avion));
        }

        if (!aviones.remove(avion)) {
            throw new Exception(String.format("No se encontró el trayecto %s", avion));
        }

        UtilFiles.writeData(aviones, fileName);
    }

    public Avion get(Avion avion) {
        int index = aviones.indexOf(avion);
        return index > -1 ? aviones.get(index) : null;
    }

    public ArrayList<Avion> getList() {
        return aviones;
    }

    public JSONObject set(String matricula, JSONObject json) throws IOException {
        Avion avion = new Avion(json);
        avion.setMatricula(matricula);
        int index = aviones.indexOf(avion);
        aviones.set(index, avion);
        UtilFiles.writeData(aviones, fileName);
        return new JSONObject(avion);
    }

    private void loadCSV() throws IOException {

        String text = UtilFiles.readText(fileName + ".csv");

        try (Scanner sc = new Scanner(text).useDelimiter(";|[\n]+|[\r\n]+")) {
            while (sc.hasNext()) {
                String matricula = sc.next();
                String modelo = sc.next();

                aviones.add(new Avion(matricula, modelo));
                sc.nextLine();
            }
        }
    }

    public void update() throws IOException {
        aviones = new ArrayList<>();
        loadCSV();
        UtilFiles.writeJSON(aviones, fileName + ".json");
    }

    public ArrayList<Avion> loadJSON() throws IOException {
        aviones = new ArrayList<>();
        String data = UtilFiles.readText(fileName + ".json");
        JSONArray jsonArr = new JSONArray(data);

        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            aviones.add(new Avion(jsonObj));
        }

        return aviones;
    }

    public JSONArray getJSON() throws IOException {
        return new JSONArray(UtilFiles.readText(fileName + ".json"));
    }

    public JSONObject getJSON(int index) {
        return new JSONObject(aviones.get(index));
    }

    public JSONObject getJSON(Avion avion) {
        int index = aviones.indexOf(avion);
        return index > -1 ? getJSON(index) : null;
    }

    public JSONObject get(String paramsVuelo) throws IOException {

        JSONObject json = UtilFiles.paramsToJson(paramsVuelo);
        Avion avion = new Avion(json.getString("matricula"), "");
        avion = get(avion);
        return getJSON(avion);
    }

    @SuppressWarnings("unchecked") // ver esto…
    public void listAll(SillasService sillas) throws Exception {
        ArrayList<String> list = new ArrayList<String>();
        String encabezado = "MATRICULA     MODELO";
        for (Avion a : aviones) {
            list.add(a.toString());
            list.add("          ");
            list.add("SILLAS");
            list.add("          ");
            ArrayList<Silla> silla = sillas.getList();
            for (Silla s : silla) {
                if (s.getAvion().equals(a))
                    list.add(s.toString());
            }
            list.add("          ");
        }
        int length = encabezado.length() + 9;
        String title = "LISTADO DE AVIONES CON SILLAS";
        list.add(0, " ".repeat((length - title.length()) / 2) + title);
        list.add(1, "-".repeat(length));
        list.add(2, encabezado);
        list.add(3, "-".repeat(length));
        list.add("-".repeat(length));

        UtilFiles.writeText(list, fileName + ".txt");
    }

}
