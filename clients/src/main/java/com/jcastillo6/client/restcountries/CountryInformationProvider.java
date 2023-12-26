package com.jcastillo6.client.restcountries;

import java.util.Set;
import com.jcastillo6.client.restcountries.model.Country;

/**
 * Country Information provider contract
 *
 */
public interface CountryInformationProvider {
    /**
     * Retrieve a list of all the countries with the detail information
     * @return set of countries
     */
    Set<Country> getCountries();
}
