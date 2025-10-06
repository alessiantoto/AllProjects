package Model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * La classe Model gère la récupération des données météorologiques.
 * Elle est responsable de la logique métier, y compris la mise en cache des résultats pour éviter
 * des appels répétés à l'API lorsque les mêmes données sont demandées.
 */
public class Model implements Observable {
    private String address; // Adresse pour laquelle récupérer la météo
    private LocalDate date; // Date pour laquelle récupérer la météo
    private WeatherApi.WeatherObject cachedWeather; // Données météo mises en cache
    private final Set<Observer> observers = new HashSet<>(); // Ensemble d'observateurs

    /**
     * Récupère les données météorologiques pour l'adresse et la date spécifiées.
     * Vérifie d'abord si les données sont déjà en cache. Si elles le sont, elles sont retournées directement.
     * Sinon, les données sont récupérées via l'API.
     *
     * @param address L'adresse (nom de la ville) pour laquelle récupérer les données météorologiques.
     * @param date    La date pour laquelle récupérer les données météorologiques.
     * @return Un objet WeatherObject contenant les données météorologiques récupérées.
     * @throws WeatherException Si une erreur se produit lors de la récupération des données.
     */
    public WeatherApi.WeatherObject fetch(String address, LocalDate date) throws WeatherException {
        if (isCached(address, date)) {
            return cachedWeather; // Retourne les données mises en cache
        }

        // Récupération des données depuis l'API
        cachedWeather = WeatherApi.fetch(address, date);
        this.address = address; // Met à jour l'adresse
        this.date = date; // Met à jour la date

        // Notifier tous les observateurs avec les nouvelles données météo
        notifyObservers(cachedWeather);
        return cachedWeather; // Retourne les nouvelles données
    }

    /**
     * Vérifie si les données pour l'adresse et la date spécifiées sont déjà mises en cache.
     *
     * @param address L'adresse à vérifier.
     * @param date    La date à vérifier.
     * @return true si les données sont en cache, false sinon.
     */
    private boolean isCached(String address, LocalDate date) {
        return this.address != null && this.address.equals(address) &&
                this.date != null && this.date.equals(date);
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o); // Ajoute un observateur
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o); // Supprime un observateur
    }

    @Override
    public void notifyObservers(WeatherApi.WeatherObject values) {
        for (Observer observer : observers) {
            observer.update(values); // Notifie chaque observateur avec les nouvelles données
        }
    }
}
