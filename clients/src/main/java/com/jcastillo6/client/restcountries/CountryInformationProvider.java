package com.jcastillo6.client.restcountries;

import java.util.Set;
import com.jcastillo6.client.restcountries.model.Country;

public interface CountryInformationProvider {
    Set<Country> getCountries();
}
