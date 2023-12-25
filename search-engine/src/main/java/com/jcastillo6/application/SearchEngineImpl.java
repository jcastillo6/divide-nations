package com.jcastillo6.application;

import java.util.List;
import java.util.Set;
import com.jcastillo6.domain.domain_service.CountrySearchService;
import com.jcastillo6.domain.entity.Country;
import com.jcastillo6.infrastructure.repository.CountryRepository;

public class SearchEngineImpl implements SearchEngine {
    private final CountrySearchService countrySearchService;
    private final Set<Country> countries;

    public SearchEngineImpl(CountryRepository countryRepository, CountrySearchService countrySearchService) {
        if (countryRepository == null) {
            throw new IllegalArgumentException("Invalid CountryRepository");
        }
        if (countrySearchService == null) {
            throw new IllegalArgumentException("Invalid countrySearchService");
        }

        this.countrySearchService = countrySearchService;
        this.countries = countryRepository.getAllCountries();
        if (countries == null || countries.isEmpty()) {
            throw new IllegalArgumentException("Countries provider fail");
        }
    }

    @Override
    public List<Country> getCountriesByPopulationDensitySortedDesc() {
        return countrySearchService.getCountriesByPopulationDensitySortedDesc(countries);
    }

    @Override
    public Country getCountryWithMostBorderingCountriesOfADifferentRegion(String region) {
        var countriesOfRegion = getCountriesByRegion(region);
        return countrySearchService.getCountryWithMostBorderingCountriesOfADifferentRegion(countriesOfRegion);
    }

    @Override
    public Set<Country> getCountriesByRegion(String region) {
        if (region == null || region.isEmpty()) {
            throw new IllegalArgumentException("Invalid region");
        }

        return countrySearchService.getCountriesByRegion(region, countries);
    }
}
