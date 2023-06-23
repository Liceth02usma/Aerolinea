package edu.prog2;

import org.json.JSONObject;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.json.JSONArray;
import edu.prog2.model.*;

import edu.prog2.helpers.Keyboard;
import edu.prog2.helpers.UtilFiles;

public class AppJSON {

    private static JSONObject jsonPasajero;
    private static JSONObject jsonTrayecto;
    private static JSONObject jsonAvion;
    private static JSONObject jsonVuelo;
    private static JSONArray jsonArray;
    private static JSONObject jsonSilla;
    private static JSONObject jsonReserva;
    private static JSONObject jsonReservaVuelo;
    private static JSONObject jsonSillaEjecutiva;

    public static void main(String[] args) {
        menu();
    }

    private static void menu() {
        do {
            try {
                int opcion = leerOpcion();
                switch (opcion) {
                    case 1:
                        testPut();
                        break;
                    case 2:
                        JsonAvion();
                        break;
                    case 3:
                        JsonPasajero();
                        break;
                    case 4:
                        JsonTrayecto();
                        break;
                    case 5:
                        JsonVuelo();
                        break;
                    case 6:
                        JsonSilla();
                        break;
                    case 7:
                        JsonSillaEjecutiva();
                        break;
                    case 8:
                        JsonReserva();
                        break;
                    case 9:
                        JsonReservaVuelo();
                        break;
                    case 10:
                        JsonConstructor();
                        break;
                    case 11:
                        JsonConstructorClase();
                        break;
                    case 12:
                        getTrayecto();
                        break;
                    case 13:
                        getSillaEjecutiva();
                        break;
                    case 14:
                        metodoHas();
                        break;
                    case 15:
                        jsonArray();
                        break;
                    case 16:
                        recorrerJsonArray();
                        break;
                    case 17:
                        instanceTests();
                        break;
                    case 18:
                        jsonArrayTest();
                        break;
                    case 0:
                        System.exit(0);
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (true);
    }

    static int leerOpcion() {
        String opciones = "\nMenú de opciones:\n"
                + " 1 - Test Put                              10 - Creacion objeto Json por constructor (Cadena String)\n"
                + " 2 - Listar trayectos Json                 11 - Creacion objeto Json por constructor (Clase)        \n"
                + " 3 - Listar avion Json                     12 - Prueba metodo Get trayecto                          \n"
                + " 4 - Listar pasajero Json                  13 - Prueba metodo Get Silla Ejecutiva                   \n"
                + " 5 - Listar trayecto Json                  14 - Metodo Has                                          \n"
                + " 6 - Listar vuelo Json                     15 - JsonArray                                           \n"
                + " 7 - Listar silla Json                     16 - Recorrer JsonArray                                  \n"
                + " 8 - Listar silla ejecutiva Json           17 - Instance Tests                                      \n"
                + " 9 - Listar reserva Json                   18 - JsonArrayTest                                       \n"
                + "99 - Salir\n"
                + "\nElija una opción (99 para salir) > ";

        int opcion = Keyboard.readInt(opciones);
        System.out.println();
        return opcion;
    }

    private static void testPut() {
        jsonTrayecto = new JSONObject();
        jsonTrayecto.put("origen", "Manizales");
        jsonTrayecto.put("destino", "Bogotá");
        jsonTrayecto.put("duracion", "PT45M");
        jsonTrayecto.put("costo", 120000);

        System.out.println(jsonTrayecto.toString(2));
    }

    private static void JsonAvion() {
        jsonAvion = new JSONObject();
        jsonAvion.put("Matricula", "HK2001");
        jsonAvion.put("Modelo", "Airbus 201");

        System.out.println(jsonAvion.toString(2));
    }

    private static void JsonPasajero() {
        jsonPasajero = new JSONObject();
        jsonPasajero.put("Identificacion", "P1001");
        jsonPasajero.put("Nombre", "Liceth Juanita");
        jsonPasajero.put("Apellidos", "Usma Londoño");

        System.out.println(jsonPasajero.toString(2));
    }

    private static void JsonTrayecto() {
        jsonTrayecto = new JSONObject();
        jsonTrayecto.put("Origen", "Manizales");
        jsonTrayecto.put("Destino", "Bogota");
        jsonTrayecto.put("Costo", 10000000);
        jsonTrayecto.put("Duracion", "PH1M30");

        System.out.println(jsonTrayecto.toString(2));
    }

    private static void JsonVuelo() {
        jsonVuelo = new JSONObject();
        jsonVuelo.put("fechaHora", "2022-09-10T08:30");
        jsonVuelo.put("trayecto", jsonTrayecto);
        jsonVuelo.put("avion", jsonAvion);

        System.out.println(jsonVuelo.toString(2));
    }

    private static void JsonSilla() {
        jsonSilla = new JSONObject();
        jsonSilla.put("fila", 1);
        jsonSilla.put("columna", "A");
        jsonSilla.put("disponible", true);
        jsonSilla.put("avion", jsonAvion);

        System.out.println(jsonSilla.toString(2));
    }

    private static void JsonSillaEjecutiva() {
        jsonSillaEjecutiva = new JSONObject();
        jsonSillaEjecutiva.put("fila", 1);
        jsonSillaEjecutiva.put("columna", "A");
        jsonSillaEjecutiva.put("disponible", true);
        jsonSillaEjecutiva.put("menu", "POLLO_A_LA_NARANJA");
        jsonSillaEjecutiva.put("licor", "WHISKEY");

        jsonSillaEjecutiva.put("avion", jsonAvion);

        System.out.println(jsonSillaEjecutiva.toString(2));

    }

    private static void JsonReserva() {
        jsonReserva = new JSONObject();
        jsonReserva.put("fechaHora", "2022/10/1 03:15 p.m.");
        jsonReserva.put("pasajero", jsonPasajero);
        jsonReserva.put("cancelada", false);

        System.out.println(jsonReserva.toString(2));

    }

    private static void JsonReservaVuelo() {
        jsonReservaVuelo = new JSONObject();
        jsonReservaVuelo.put("reserva", jsonReserva);
        jsonReservaVuelo.put("vuelo", jsonVuelo);
        jsonReservaVuelo.put("silla", jsonSilla);
        jsonReservaVuelo.put("Checkin", false);

        System.out.println(jsonReservaVuelo.toString(2));
    }

    private static void JsonConstructor() {
        jsonPasajero = new JSONObject(
                "{ \"identificacion\": \"P1001\",\"nombre\": \"Liceth Juanita\",\"apellido\": \"Usma Londoño\"}");
        jsonTrayecto = new JSONObject(
                "{ \"origen\": \"Manizales\",\"destino\": \"Bogota\",\"costo\": 100000,\"duracion\": \"PH1M30\"}");
        jsonAvion = new JSONObject(
                "{ \"matricula\": \"HK2001\",\"modelo\": \"Airbus 210\"}");

        System.out.println("Pasajero: ");
        System.out.println(jsonPasajero.toString(2));
        System.out.println("Trayecto: ");
        System.out.println(jsonTrayecto.toString(2));
        System.out.println("Avion: ");
        System.out.println(jsonAvion.toString(2));
    }

    private static void JsonConstructorClase() {
        Avion avion = new Avion("HK2015", "Airbus 330");
        JSONObject jsonAvion = new JSONObject(avion);
        Pasajero pasajero = new Pasajero("P1001", "Liceth Juanita", "Usma Londoño");
        JSONObject jsonPasajero = new JSONObject(pasajero);
        Trayecto trayecto = new Trayecto("Manizales", "Bogota", Duration.parse("PTH10M"), 1000.0);
        JSONObject jsonTrayecto = new JSONObject(trayecto);
        Vuelo vuelo = new Vuelo(LocalDateTime.parse("2022/10/1 03:15 p.m."), trayecto, avion);
        JSONObject jsonVuelo = new JSONObject(vuelo);
        System.out.println(jsonAvion.toString(2));
        System.out.println(jsonPasajero.toString(2));
        System.out.println(jsonTrayecto.toString(2));
        System.out.println(jsonVuelo.toString(2));
    }

    private static void getTrayecto() {
        System.out.printf("Origen: %s%n", jsonTrayecto.getString("origen"));
        System.out.printf("Destino: %s%n", jsonTrayecto.getString("destino"));
        System.out.printf("Duración: %s%n", jsonTrayecto.getString("duracion"));
        System.out.printf("Costo: %.0f%n", jsonTrayecto.getDouble("costo"));
    }

    private static void getSillaEjecutiva() {
        System.out.printf("Fila: %s%n", jsonSillaEjecutiva.getInt("fila"));
        System.out.printf("Columna: %s%n", jsonSillaEjecutiva.getString("columna").charAt(0));
        System.out.printf("Avion: %s%n", jsonSillaEjecutiva.getJSONArray("avion"));
        System.out.printf("Disponible: %s%n", jsonSillaEjecutiva.getBoolean("disponible"));
        System.out.printf("Menu: %s%n", jsonSillaEjecutiva.getEnum(Menu.class, "menu"));
        System.out.printf("Licor: %s%n", jsonSillaEjecutiva.getEnum(Licor.class, "licor"));
    }

    private static void metodoHas() {
        if (jsonSilla.has("licor")) {
            System.out.println("Es una silla ejecutiva");
        } else {
            System.out.println("Es una silla económica");
        }
        System.out.println("Existe = " + jsonTrayecto.has("duración"));
    }

    private static void jsonArray() {
        jsonArray.put(jsonTrayecto);
        jsonArray.put(jsonVuelo);
        jsonArray.put(jsonPasajero);
        jsonArray.put(jsonAvion);
        jsonArray.put(jsonSilla);
    }

    private static void recorrerJsonArray() {
        for (int i = 0; i < jsonArray.length(); i++) {
            System.out.println(jsonArray.get(i));
        }
    }

    private static void instanceTests() throws IOException {
        String strAvion = UtilFiles.readText("./data/tests/test-avion.json");
        String strReserva = UtilFiles.readText("./data/tests/test-reserva.json");
        String strReservaVuelo = UtilFiles.readText("./data/tests/test-vuelo-reserva.json");
        String strSillaEjecutiva = UtilFiles.readText("./data/tests/test-silla-ejecutiva.json");
        String strSilla = UtilFiles.readText("./data/tests/test-silla.json");
        String strTrayecto = UtilFiles.readText("./data/tests/test-trayecto.json");
        String strVuelo = UtilFiles.readText("./data/tests/test-vuelo.json");
        String strPasajero = UtilFiles.readText("./data/tests/test-pasajero.json"); // lee un archivo json
        // se instancia el atributo de clase jsonPasajero definido en el paso 4:
        jsonPasajero = new JSONObject(strPasajero); // se crea la instancia apartir del string leido
        // se usa el constructor del paso 17 para crear una instancia Java:
        jsonAvion = new JSONObject(strAvion);
        jsonReserva = new JSONObject(strReserva);
        jsonReservaVuelo = new JSONObject(strReservaVuelo);
        jsonSillaEjecutiva = new JSONObject(strSillaEjecutiva);
        jsonSilla = new JSONObject(strSilla);
        jsonTrayecto = new JSONObject(strTrayecto);
        jsonVuelo = new JSONObject(strVuelo);
        // -------------------------------------
        Pasajero pasajero = new Pasajero(jsonPasajero); // se utiliza el contructor antes implementado
        Avion avion = new Avion(jsonAvion);
        Reserva reserva = new Reserva(jsonReserva);
        ReservaVuelo reservaVuelo = new ReservaVuelo(jsonReservaVuelo);
        SillaEjecutiva sillaEjecutiva = new SillaEjecutiva(jsonSillaEjecutiva);
        Silla silla = new Silla(jsonSilla);
        Trayecto trayecto = new Trayecto(jsonTrayecto);
        Vuelo vuelo = new Vuelo(jsonVuelo);
        // ----------------------------------------
        System.out.println(pasajero);
        System.out.println(avion);
        System.out.println(reserva);
        System.out.println(reservaVuelo);
        System.out.println(sillaEjecutiva);
        System.out.println(silla);
        System.out.println(trayecto);
        System.out.println(vuelo);

        UtilFiles.writeText("Hola mundo\nAdios mundo", "./data/tests/prueba1.txt");
        UtilFiles.writeText(jsonReservaVuelo.toString(2), "./data/tests/prueba2.txt");

    }

    private static void jsonArrayTest() throws Exception {
        instanceTests();

        ArrayList<Object> list = new ArrayList<>();

        list.add(new Pasajero(jsonPasajero));
        list.add(new Avion(jsonAvion));
        list.add(new Silla(jsonSilla));
        list.add(new SillaEjecutiva(jsonSillaEjecutiva));
        list.add(new Trayecto(jsonTrayecto));
        list.add(new Vuelo(jsonVuelo));
        list.add(new Reserva(jsonReserva));
        list.add(new ReservaVuelo(jsonReservaVuelo));

        UtilFiles.writeJSON(list, "./data/tests/array.json");
    }

}
