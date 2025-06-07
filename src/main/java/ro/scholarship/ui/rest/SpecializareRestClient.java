package ro.scholarship.ui.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import ro.scholarship.model.Specializare;
import ro.scholarship.util.JsonUtil;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class SpecializareRestClient {
    private static final String BASE_URL = "http://localhost:8081/api/specializari";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final com.fasterxml.jackson.databind.ObjectMapper mapper = JsonUtil.MAPPER;

    public static List<Specializare> loadAllSpecializari() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return mapper.readValue(response.body(), new TypeReference<List<Specializare>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public static Specializare addSpecializare(Specializare spec) {
        try {
            String json = mapper.writeValueAsString(spec);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .header("Content-Type", "application/json")
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return mapper.readValue(response.body(), Specializare.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void updateSpecializare(Specializare specializare) {
        try {
            String json = mapper.writeValueAsString(specializare);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/" + specializare.getId()))
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .header("Content-Type", "application/json")
                    .build();
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Eroare la update specializare!", e);
        }
    }

    public static void deleteSpecializare(int id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/" + id))
                    .DELETE()
                    .build();
            client.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
