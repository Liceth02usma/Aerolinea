package edu.prog2.controllers;

import static spark.Spark.*;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.prog2.helpers.StandardResponse;
import edu.prog2.services.*;

public class ReservasController {
    public ReservasController(final ReservasService reservasService) throws IOException {
        path("/reservas", () -> {

            get("", (request, response) -> {
                response.type("application/json");
                try {
                    response.status(201);
                    JSONArray json = reservasService.getJSON();
                    return new StandardResponse(response, 201, "ok", json);
                } catch (Exception exception) {
                    response.status(404);
                    return new StandardResponse(response, 404, exception);
                }

            }); // fin del get()

            get("/:reservas", (request, response) -> {
                response.type("application/json");
                try {
                    response.status(201);
                    String params = request.params(":reservas");
                    JSONObject json = reservasService.get(params);
                    return new StandardResponse(response, 201, "ok", json);
                } catch (Exception e) {
                    response.status(404);
                    e.printStackTrace();
                    return new StandardResponse(response, 404, e);
                }
            });

            post("", (request, response) -> {
                try {
                    reservasService.add(new JSONObject(request.body()));
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
                    json = reservasService.set(json);
                    return new StandardResponse(response, 201, "ok", json);
                } catch (Exception e) {
                    return new StandardResponse(response, 404, e);
                }
            });

            delete("/:reserva", (request, response) -> {
                try {
                    String params = request.params(":reserva");
                    reservasService.remove(params);
                    return new StandardResponse(response, 201, "ok");
                } catch (Exception e) {
                    return new StandardResponse(response, 404, e);
                }
            });

            // … espacio para definir otras rutas del grupo pasajeros …

        }); // fin del path()

    }

}