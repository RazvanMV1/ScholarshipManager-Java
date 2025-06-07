package ro.scholarship.ui.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.scholarship.model.Facultate;
import ro.scholarship.model.Specializare;
import ro.scholarship.model.Student;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class StudentRestClient {
    private static final String BASE_URL = "http://localhost:8081/api/students";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Student> loadAllStudents() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Răspuns JSON studenți: " + response.body());

            return mapper.readValue(response.body(), new TypeReference<List<Student>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
    public static void addStudent(Student student) {
        try {
            if (student.getSpecializare() != null) {
                student.getSpecializare().setFacultate(null);
            }

            String json = mapper.writeValueAsString(student);
            System.out.println("Trimitem JSON student: " + json);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException("Nu s-a putut adăuga studentul!", e);
        }

    }
    public static void updateStudent(Student student) {
        try {
            if (student.getSpecializare() != null) {
                student.getSpecializare().setFacultate(null);
            }

            String json = mapper.writeValueAsString(student);
            System.out.println("Trimitem UPDATE JSON student: " + json);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/" + student.getId()))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException("Nu s-a putut actualiza studentul!", e);
        }
    }

    public static void deleteStudent(int id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/" + id))
                    .DELETE()
                    .build();
            HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException("Eroare la ștergerea studentului!", e);
        }
    }



}
