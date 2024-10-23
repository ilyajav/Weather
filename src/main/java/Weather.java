import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Weather {
    private static final String HEADER_KEY_NAME = "X-Yandex-Weather-Key";
    private static final String ACCESS_KEY = "00f263eb-112b-4c03-bf99-acca8284f0bb";

    static double summaryTemp;
    static double averageTemperature;

    static String fistData;
    static String lastData;

    public static void getData(Double lat, Double lon, int limit) {
        String URL = String.format("https://api.weather.yandex.ru/v2/forecast?lat=%s&lon=%s&limit=%s", lat, lon, limit);

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header(HEADER_KEY_NAME, ACCESS_KEY)
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("All Response: " + response.body());

            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode rootNode = objectMapper.readTree(response.body());

            JsonNode temperature = rootNode.path("fact").get(("temp"));

            System.out.println("Temperature: " + temperature + " celsius");

            JsonNode forecasts = rootNode.path(("forecasts"));

            if (forecasts.isArray()) {
                int index = 0;
                int forecastSize = forecasts.size();

                for (JsonNode item : forecasts) {
                    double dayTemperature = item.path("parts").path("day").path("temp_avg").asDouble();

                    String date = item.path("date").asText();

                    if (index == 0) {
                        fistData = item.path("date").asText();
                    }

                    if (index == forecastSize - 1) {
                        lastData = date;
                    }

                    index++;

                    summaryTemp += dayTemperature;
                }

            }

            averageTemperature = summaryTemp / limit;

            System.out.println();
            System.out.println(
                    "Average temperature from "
                            + fistData
                            + " to "
                            + lastData
                            + " = "
                            + averageTemperature + " celsius"
            );



        } catch (Exception e) {
            System.err.println("Error:" + e.getMessage());
        }
    }
}