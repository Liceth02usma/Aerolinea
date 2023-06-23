package edu.prog2.controllers;

import static spark.Spark.*;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.prog2.helpers.StandardResponse;
import edu.prog2.services.*;

public class SillasController {
    public SillasController(final SillasService sillasService) throws IOException {
        path("/sillas", () -> {

            get("", (request, response) -> {
                response.type("application/json");
                try {
                    response.status(201);
                    JSONArray json = sillasService.getJSON();
                    return new StandardResponse(response, 201, "ok", json);
                } catch (Exception exception) {
                    response.status(404);
                    return new StandardResponse(response, 404, exception);
                }

            }); // fin del get()
            get("/disponibles/:avion", (request, response) -> {
                try {
                    String params = request.params(":avion");
                    JSONArray json = sillasService.DisponibleVuelo(params);
                    return new StandardResponse(response, 201, "ok", json);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new StandardResponse(response, 404, e);
                }
            });
            get("/avion/:avion", (request, response) -> {
                try {
                    String params = request.params(":avion");
                    JSONObject json = sillasService.numberOfSeats(params);
                    return new StandardResponse(response, 201, "ok", json);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new StandardResponse(response, 404, e);
                }
            });

            get("/total", (request, response) -> {
                try {

                    JSONArray json = sillasService.aircraftWithNumberSeats();
                    return new StandardResponse(response, 201, "ok", json);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new StandardResponse(response, 404, e);
                }
            });

            get("/:sillas", (request, response) -> {
                try {
                    String params = request.params(":sillas");
                    JSONObject json = sillasService.get(params);
                    return new StandardResponse(response, 201, "ok", json);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new StandardResponse(response, 404, e);
                }
            });

            post("", (request, response) -> {
                try {
                    JSONObject json = new JSONObject(request.body());
                    sillasService.create(json.getString("avion"), json.getInt("ejecutivas"),
                            json.getInt("economicas"));
                    return new StandardResponse(response, 201, "ok");
                } catch (Exception exception) {
                    response.status(404);
                    return new StandardResponse(response, 404, exception);
                }
            });

            put("", (request, response) -> {
                try {
                    JSONObject json = new JSONObject(request.body());
                    json = sillasService.set(json);
                    return new StandardResponse(response, 201, "ok", json);
                } catch (Exception e) {
                    return new StandardResponse(response, 404, e);
                }
            });

            delete("/remove/:sillas", (request, response) -> {
                try {
                    String params = request.params(":sillas");
                    sillasService.removeAvion(params);
                    return new StandardResponse(response, 201, "ok");
                } catch (Exception e) {
                    System.out.println(e);
                    return new StandardResponse(response, 404, e);
                }
            });

            delete("/:sillas", (request, response) -> {
                try {
                    String params = request.params(":sillas");
                    sillasService.remove(params);
                    return new StandardResponse(response, 201, "ok");
                } catch (Exception e) {
                    System.out.println(e);
                    return new StandardResponse(response, 404, e);
                }
            });

            // … espacio para definir otras rutas del grupo pasajeros …

        }); // fin del path()

    }

}
