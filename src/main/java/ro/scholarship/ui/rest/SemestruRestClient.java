package ro.scholarship.ui.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import ro.scholarship.model.SemestruUniversitar;
import ro.scholarship.util.JsonUtil;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class SemestruRestClient {
    private static final String BASE_URL = "http://localhost:8081/api/semestre";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final com.fasterxml.jackson.databind.ObjectMapper mapper = JsonUtil.MAPPER;

    public static List<SemestruUniversitar> loadAllSemestre() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return mapper.readValue(response.body(), new TypeReference<List<SemestruUniversitar>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
