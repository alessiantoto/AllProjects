package Model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * La classe WeatherApi est responsable de la récupération des données météorologiques à partir d'une API externe.
 * Elle fournit des méthodes pour interroger les données météo basées sur une ville et une date spécifiques.
 */
public class WeatherApi {
    /**
     * Récupère les données météorologiques en fonction de la ville et de la date fournies.
     *
     * @param city - le nom de la ville pour laquelle récupérer les données météo.
     * @param date - la date choisie pour laquelle récupérer les données météo.
     * @return WeatherObject - contenant les données météo récupérées.
     * @throws WeatherException - si un problème survient lors du processus de récupération.
     */
    static WeatherObject fetch(String city, LocalDate date) throws WeatherException {
        if (city.isEmpty() || date == null) {
            throw new WeatherException("Ville ou date invalide.");
        }

        try {
            String locationUrl = "https://nominatim.openstreetmap.org/search.php?q=" + city + "&format=jsonv2";
            HttpClient client = HttpClient.newHttpClient();

            // Envoyer la requête pour obtenir les coordonnées de la ville
            HttpRequest locationRequest = HttpRequest.newBuilder().uri(URI.create(locationUrl)).build();
            HttpResponse<String> locationResponse = client.send(locationRequest, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode locationJson = objectMapper.readTree(locationResponse.body());

            if (locationJson.isEmpty()) {
                throw new WeatherException("La ville spécifiée n'a pas été trouvée.");
            }

            double latitude = locationJson.get(0).get("lat").asDouble();
            double longitude = locationJson.get(0).get("lon").asDouble();

            // Format de la date au format YYYY-MM-DD
            String formattedDate = date.toString();

            String weatherUrl = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude +
                    "&longitude=" + longitude + "&current=temperature_2m&daily=weather_code," +
                    "temperature_2m_max,temperature_2m_min&timezone=Europe%2FBerlin&start_date=" +
                    formattedDate + "&end_date=" + formattedDate;

            HttpRequest weatherRequest = HttpRequest.newBuilder().uri(URI.create(weatherUrl)).build();
            HttpResponse<String> weatherResponse = client.send(weatherRequest, HttpResponse.BodyHandlers.ofString());

            JsonNode weatherJson = objectMapper.readTree(weatherResponse.body());

            // Affichez le JSON pour débogage
            System.out.println(weatherResponse.body()); // Debug

            // Après avoir récupéré les données via l'API
            if (weatherJson.has("daily") && weatherJson.get("daily").has("temperature_2m_min") &&
                    weatherJson.get("daily").get("temperature_2m_min").size() > 0) {

                double tempMin = weatherJson.get("daily").get("temperature_2m_min").get(0).asDouble();
                double tempMax = weatherJson.get("daily").get("temperature_2m_max").get(0).asDouble();
                int weatherCode = weatherJson.get("daily").get("weather_code").get(0).asInt();

                // Retourner un nouvel objet WeatherObject avec les données récupérées
                return new WeatherObject(city, date, weatherCode, tempMin, tempMax);
            } else {
                throw new WeatherException("Aucune information météo disponible pour cette journée.");
            }

        } catch (IOException | InterruptedException e) {
            // Encapsuler l'exception dans WeatherException
            throw new WeatherException("Erreur lors de la récupération des données météo : " + e.getMessage(), e);
        }
    }

    /**
     * La classe WeatherObject représente les données météorologiques d'une localité
     * pour une date spécifique.
     *
     * @param locality  La localité pour laquelle les données météorologiques sont fournies.
     * @param date      La date pour laquelle les données météorologiques sont fournies.
     * @param weatherCode Le code météo indiquant le type de conditions météorologiques.
     * @param tempMin   La température minimale prévue pour cette date.
     * @param tempMax   La température maximale prévue pour cette date.
     */
    public static record WeatherObject(String locality, LocalDate date, int weatherCode, double tempMin, double tempMax) {
    }
}
