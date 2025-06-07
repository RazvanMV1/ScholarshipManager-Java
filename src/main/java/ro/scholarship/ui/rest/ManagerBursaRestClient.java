package ro.scholarship.ui.rest;

import ro.scholarship.model.BursaAcordata;
import ro.scholarship.util.JsonUtil;

import java.net.http.*;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

public class ManagerBursaRestClient {
    private static final String BASE_URL = "http://localhost:8081/api/manager-burse";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final com.fasterxml.jackson.databind.ObjectMapper mapper = JsonUtil.MAPPER;

    public static List<BursaAcordata> proceseazaBursa(int idBursa) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/proceseaza/" + idBursa))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Folosește mapper-ul corect!
            BursaAcordata[] rezultat = mapper.readValue(response.body(), BursaAcordata[].class);
            return Arrays.asList(rezultat);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
