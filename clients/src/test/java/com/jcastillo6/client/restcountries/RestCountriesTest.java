package com.jcastillo6.client.restcountries;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

@WireMockTest
class RestCountriesTest {

    private final String venezuela = """
          [{
            "name": {
              "common": "Venezuela",
              "official": "Bolivarian Republic of Venezuela"
            },
            "cca3": "VEN",
            "region": "Americas",
            "subregion": "South America",
            "borders": [
              "BRA",
              "COL",
              "GUY"
            ],
            "area": 916445.0,
            "population": 28435943
          }]
        """;
    @Test
    void shouldReturnAListOfAllCountries(WireMockRuntimeInfo wmRuntimeInfo) {
        // Define WireMock mappings
        stubFor(get(urlEqualTo("/v3.1/all"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(venezuela)));

        RestCountries restCountries = new RestCountries();
        var countries = restCountries.getCountries("http://localhost:" + wmRuntimeInfo.getHttpPort() + "/v3.1", "/all");
        assertFalse(countries.isEmpty());
        var firstCountry = countries.iterator().next();
        assertNotNull(firstCountry);
        assertEquals("Bolivarian Republic of Venezuela", firstCountry.name().official());
        assertEquals("Venezuela", firstCountry.name().common());
        assertEquals(28435943, firstCountry.population());
        assertEquals(916445.0, firstCountry.area());
        assertEquals("Americas", firstCountry.region());
        assertEquals("South America", firstCountry.subregion());
        assertFalse(firstCountry.borders().isEmpty());
        assertEquals(3, firstCountry.borders().size());
    }
}