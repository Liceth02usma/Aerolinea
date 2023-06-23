package edu.prog2.controllers;

import static spark.Spark.*;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.prog2.helpers.StandardResponse;
import edu.prog2.services.*;

public class VuelosController {
    public VuelosController(final VuelosService vuelosService) throws IOException {
        path("/vuelos", () -> {

            get("/vueloIda/:vuelo", (request, response) -> {
                response.type("application/json");
                try {
                    String params = request.params(":vuelo");
                    JSONArray json = vuelosService.getVuelo(params);
                    response.status(201);
                    return new StandardResponse(response, 201, "ok", json);
                } catch (Exception e) {
                    response.status(404);
                    return new StandardResponse(response, 404, e);
                }
            });

            get("", (request, response) -> {
                response.type("application/json");
                try {
                    response.status(201);
                    JSONArray json = vuelosService.getJSON();
                    return new StandardResponse(response, 201, "ok", json);
                } catch (Exception exception) {
                    response.status(404);
                    return new StandardResponse(response, 404, exception);
                }

            }); // fin del get()

            get("/:vuelo", (request, response) -> {
                response.type("application/json");
                try {
                    String params = request.params(":vuelo");
                    JSONObject json = vuelosService.get(params);
                    response.status(201);
                    return new StandardResponse(response, 201, "ok", json);
                } catch (Exception e) {
                    response.status(404);
                    return new StandardResponse(response, 404, e);
                }
            });

            // … espacio para definir otras rutas del grupo pasajeros …
            post("", (request, response) -> {
                try {
                    vuelosService.add(new JSONObject(request.body()));
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
                    json = vuelosService.set(json);
                    return new StandardResponse(response, 201, "ok", json);
                } catch (Exception e) {
                    return new StandardResponse(response, 404, e);
                }
            });

            delete("/:vuelo", (request, response) -> {
                try {
                    String params = request.params(":vuelo");
                    System.out.println(params);
                    vuelosService.remove(params);
                    return new StandardResponse(response, 201, "ok");
                } catch (Exception e) {
                    return new StandardResponse(response, 404, e);
                }
            });

        }); // fin del path()

    }

}
