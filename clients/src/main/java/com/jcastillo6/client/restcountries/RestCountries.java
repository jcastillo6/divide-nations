package com.jcastillo6.client.restcountries;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcastillo6.client.restcountries.model.Country;

public class RestCountries implements CountryInformationProvider {

    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final String BASE_URL = "https://restcountries.com/v3.1";
    private static final String ALL_COUNTRIES_PATH = "/all";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Set<Country> getCountries() {
        var request = HttpRequest.newBuilder()
            .uri(getURI(BASE_URL + ALL_COUNTRIES_PATH))
            .GET()
            .build();
        Country[] countries;
        try {
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            countries = objectMapper.readValue(response.body(), Country[].class);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        return new HashSet<>(Arrays.asList(countries));
    }

    private URI getURI(String uriAsString) {
        URI uri;
        try {
            uri = new URI(uriAsString);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
        return uri;
    }
}
