package edu.prog2;

import java.io.IOException;

import edu.prog2.controllers.*;
import edu.prog2.helpers.*;
import static spark.Spark.*;
import edu.prog2.services.*;

public class AppRest {

    private static void enableCORS() {
        staticFiles.header("Access-Control-Allow-Origin", "*");

        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
    }

    public static void main(String[] args) throws IOException {
        enableCORS();
        PasajerosService pasajerosService = new PasajerosService();
        new PasajerosController(pasajerosService);

        TrayectosService trayectosService = new TrayectosService();
        new TrayectosController(trayectosService);

        AvionesService avionesService = new AvionesService();
        new AvionesController(avionesService);

        VuelosService vuelosService = new VuelosService(trayectosService, avionesService);
        new VuelosController(vuelosService);

        SillasService sillasService = new SillasService(avionesService);
        new SillasController(sillasService);

        ReservasService reservasService = new ReservasService(pasajerosService);
        new ReservasController(reservasService);

        ReservasVuelosService reservasVuelosService = new ReservasVuelosService(reservasService, vuelosService,
                sillasService);
        new ReservasVuelosController(reservasVuelosService);

        after("/*/:param", (request, response) -> {
            if (request.requestMethod().equals("PUT")) {
                String[] path = request.pathInfo().split("/");
                try {
                    if (path[1].equals("pasajeros")) {
                        reservasService.update();
                        reservasVuelosService.update();
                    } else if (path[1].equals("aviones")) {
                        sillasService.update();
                        avionesService.update();
                        reservasService.update();
                    }
                } catch (Exception e) {
                    new StandardResponse(response, 404, e);
                }
            }
        });
        after("/*", (request, response) -> {
            System.out.println("adentro");
            if (request.requestMethod().equals("PUT")) {
                String[] path = request.pathInfo().split("/");
                try {
                    if (path[1].equals("vuelos")) {
                        reservasVuelosService.update();
                    } else if (path[1].equals("trayectos")) {
                        vuelosService.update();
                        reservasVuelosService.update();
                    } else if (path[1].equals("reservas")) {
                        System.out.println("adentroX6");
                        reservasVuelosService.update();
                    }
                } catch (Exception e) {
                    new StandardResponse(response, 404, e);
                }
            }
        });
    }
}
