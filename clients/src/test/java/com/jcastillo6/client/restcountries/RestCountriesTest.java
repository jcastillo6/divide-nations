package com.jcastillo6.client.restcountries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class RestCountriesTest {

    @Test
    void shouldReturnAListOfAllCountries() {
        RestCountries restCountries = new RestCountries();
        var countries = restCountries.getCountries();
        assertFalse(countries.isEmpty());
        var firstCountry = countries.iterator().next();
        assertNotNull(firstCountry);
        assertFalse(firstCountry.name().official().isEmpty());
        assertFalse(firstCountry.name().common().isEmpty());
        assertNotNull(firstCountry.population());
        assertNotNull(firstCountry.area());
        assertFalse(firstCountry.region().isEmpty());
        assertFalse(firstCountry.subregion().isEmpty());
    }
}