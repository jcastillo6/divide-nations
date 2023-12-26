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

/**
 * Default implementation of the CountryInformationProvider
 * the provider in this case is <a href="https://gitlab.com/restcountries/restcountries">git repo</a>
 *
 */
public class RestCountries implements CountryInformationProvider {

    private static final HttpClient httpClient = HttpClient.newHttpClient();
    public static final String DEFAULT_BASE_URL = "https://restcountries.com/v3.1";
    public static final String DEFAULT_ALL_COUNTRIES_PATH = "/all";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public Set<Country> getCountries(String basePath, String path) {
        var request = HttpRequest.newBuilder()
            .uri(getURI(basePath + path))
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

    @Override
    public Set<Country> getCountries() {
        return getCountries(DEFAULT_BASE_URL, DEFAULT_ALL_COUNTRIES_PATH);
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
