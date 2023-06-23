package edu.prog2.controllers;

import static spark.Spark.*;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.prog2.helpers.StandardResponse;

import edu.prog2.services.*;

public class ReservasVuelosController {
    public ReservasVuelosController(final ReservasVuelosService reservasVuelosService) throws IOException {
        path("/vuelos-reservas", () -> {

            get("", (request, response) -> {
                response.type("application/json");
                try {
                    response.status(201);
                    JSONArray json = reservasVuelosService.getJSON();
                    return new StandardResponse(response, 201, "ok", json);
                } catch (Exception exception) {
                    response.status(404);
                    return new StandardResponse(response, 404, exception);
                }

            }); // fin del get()

            get("/:vuelos-reservas", (request, response) -> {
                response.type("application/json");
                try {
                    response.status(201);
                    String params = request.params(":vuelos-reservas");
                    JSONObject json = reservasVuelosService.get(params);
                    return new StandardResponse(response, 201, "ok", json);
                } catch (Exception e) {
                    response.status(404);
                    e.printStackTrace();
                    return new StandardResponse(response, 404, e);
                }
            });

            post("", (request, response) -> {
                try {
                    reservasVuelosService.add(new JSONObject(request.body()));
                    response.type("application/json");
                    response.status(201);
                    return new StandardResponse(response, 201, "ok");
                } catch (Exception exception) {
                    response.status(404);
                    return new StandardResponse(response, 404, exception);
                }
            });

            put("/:vuelo", (request, response) -> {
                try {
                    String params = request.params(":vuelo");
                    JSONObject jsonBody = new JSONObject(request.body());
                    JSONObject json = reservasVuelosService.set(params, jsonBody);
                    return new StandardResponse(response, 201, "ok", json);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new StandardResponse(response, 404, e);
                }
            });

            delete("/:reservaVuelo", (request, response) -> {
                try {
                    String params = request.params(":reservaVuelo");
                    reservasVuelosService.remove(params);
                    return new StandardResponse(response, 201, "ok");
                } catch (Exception e) {
                    return new StandardResponse(response, 404, e);
                }
            });
        }); // fin del path()

    }

}
