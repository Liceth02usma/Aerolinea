package edu.prog2.controllers;

import static spark.Spark.*;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.prog2.helpers.StandardResponse;
import edu.prog2.model.*;
import edu.prog2.services.PasajerosService;

public class PasajerosController {

    public PasajerosController(final PasajerosService pasajerosService) throws IOException {
        path("/pasajeros", () -> {

            get("", (request, response) -> {
                response.type("application/json");
                try {
                    response.status(201);
                    JSONArray json = pasajerosService.getJSON();
                    return new StandardResponse(response, 201, "ok", json);
                } catch (Exception exception) {
                    response.status(404);
                    return new StandardResponse(response, 404, exception);
                }

            }); // fin del get()

            get("/:id", (request, response) -> {
                response.type("application/json");
                try {
                    response.status(201);
                    String params = request.params(":id");

                    JSONObject json = pasajerosService.get(params);
                    return new StandardResponse(response, 201, "ok", json);
                } catch (Exception exception) {
                    response.status(404);
                    return new StandardResponse(response, 404, exception);
                }
            });

            // … espacio para definir otras rutas del grupo pasajeros …
            post("", (request, response) -> {
                try {
                    Pasajero pasajero = new Pasajero(request.body());
                    pasajerosService.add(pasajero);
                    response.type("application/json");
                    response.status(201);
                    return new StandardResponse(response, 201, "ok");
                } catch (Exception exception) {
                    response.status(404);
                    return new StandardResponse(response, 404, exception);
                }
            });

            put("/:identificacion", (request, response) -> {
                response.type("application/json");
                response.status(201);

                try {
                    String identificacion = request.params(":identificacion");
                    JSONObject json = new JSONObject(request.body());
                    json = pasajerosService.set(identificacion, json);

                    return new StandardResponse(response, 201, "ok", json);
                } catch (Exception e) {
                    response.status(404);
                    return new StandardResponse(response, 404, e);
                }
            });

            delete("/:id", (request, response) -> {
                try {
                    String id = request.params(":id");
                    pasajerosService.remove(id);
                    return new StandardResponse(response, 201, "ok");
                } catch (Exception e) {
                    return new StandardResponse(response, 404, e);
                }
            });

        }); // fin del path()

    }

}