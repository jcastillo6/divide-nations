package com.jcastillo6.infrastructure.repository;

import java.util.Set;
import com.jcastillo6.client.restcountries.CountryInformationProvider;
import com.jcastillo6.domain.entity.Country;

public class CountryRepositoryImpl implements CountryRepository {
    private final CountryInformationProvider countryInformationProvider;

    public CountryRepositoryImpl(CountryInformationProvider countryInformationProvider) {
        this.countryInformationProvider = countryInformationProvider;
    }
    @Override
    public Set<Country> getAllCountries() {
        Set<com.jcastillo6.client.restcountries.model.Country> restCountry = countryInformationProvider.getCountries();
        return CountryMapper.mapToCountryEntity(restCountry);
    }
}
