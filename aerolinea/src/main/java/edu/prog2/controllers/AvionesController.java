package edu.prog2.controllers;

import static spark.Spark.*;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.prog2.helpers.StandardResponse;
import edu.prog2.model.Avion;
import edu.prog2.services.AvionesService;

public class AvionesController {
    public AvionesController(final AvionesService avionesService) throws IOException {
        path("/aviones", () -> {

            get("", (request, response) -> {
                try {
                    JSONArray json = avionesService.getJSON();
                    return new StandardResponse(response, 201, "ok", json);
                } catch (Exception exception) {
                    response.status(404);
                    return new StandardResponse(response, 404, exception);
                }

            }); // fin del get()

            get("/:matricula", (request, response) -> {
                try {
                    String params = request.params(":matricula");

                    JSONObject json = avionesService.get(params);
                    return new StandardResponse(response, 201, "ok", json);
                } catch (Exception exception) {
                    return new StandardResponse(response, 404, exception);
                }
            });
            // … espacio para definir otras rutas del grupo pasajeros …
            post("", (request, response) -> {
                try {
                    Avion avion = new Avion(request.body());
                    avionesService.add(avion);
                    return new StandardResponse(response, 201, "ok");
                } catch (Exception exception) {
                    return new StandardResponse(response, 404, exception);
                }
            });

            put("/:matricula", (request, response) -> {
                response.type("application/json");
                response.status(201);

                try {
                    String matricula = request.params(":matricula");
                    JSONObject json = new JSONObject(request.body());
                    json = avionesService.set(matricula, json);

                    return new StandardResponse(response, 201, "ok", json);
                } catch (Exception e) {
                    response.status(404);
                    return new StandardResponse(response, 404, e);
                }
            });

            delete("/:avion", (request, response) -> {
                try {
                    String params = request.params(":avion");
                    avionesService.remove(params);
                    return new StandardResponse(response, 201, "ok");
                } catch (Exception e) {
                    return new StandardResponse(response, 404, e);
                }
            });

        }); // fin del path()

    }

}
