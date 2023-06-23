package edu.prog2.controllers;

import static spark.Spark.*;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.prog2.helpers.StandardResponse;
import edu.prog2.model.*;
import edu.prog2.services.TrayectosService;

public class TrayectosController {
    public TrayectosController(final TrayectosService trayectosService) throws IOException {
        path("/trayectos", () -> {

            get("", (request, response) -> {
                response.type("application/json");
                try {
                    response.status(201);
                    JSONArray json = trayectosService.getJSON();
                    return new StandardResponse(response, 201, "ok", json);
                } catch (Exception exception) {
                    response.status(404);
                    return new StandardResponse(response, 404, exception);
                }

            }); // fin del get()

            get("/:trayecto", (request, response) -> {
                response.type("application/json");
                try {
                    response.status(201);
                    String params = request.params(":trayecto");
                    JSONObject json = trayectosService.get(params);
                    return new StandardResponse(response, 201, "ok", json);
                } catch (Exception e) {
                    response.status(404);
                    return new StandardResponse(response, 404, e);
                }
            });

            post("", (request, response) -> {
                try {
                    Trayecto trayecto = new Trayecto(request.body());
                    trayectosService.add(trayecto);
                    response.type("application/json");
                    response.status(201);
                    return new StandardResponse(response, 201, "ok");
                } catch (Exception exception) {
                    response.status(404);
                    return new StandardResponse(response, 404, exception);
                }
            });

            put("", (request, response) -> {
                try {
                    JSONObject json = new JSONObject(request.body());
                    json = trayectosService.set(json);
                    return new StandardResponse(response, 201, "ok", json);
                } catch (Exception e) {
                    return new StandardResponse(response, 404, e);
                }
            });

            delete("/:trayecto", (request, response) -> {
                try {
                    String params = request.params(":trayecto");
                    trayectosService.remove(params);
                    return new StandardResponse(response, 201, "ok");
                } catch (Exception e) {
                    return new StandardResponse(response, 404, e);
                }
            });

            // … espacio para definir otras rutas del grupo pasajeros …

        }); // fin del path()

    }

}