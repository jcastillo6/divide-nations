package com.jcastillo6.domain.domain_service;

import java.util.List;
import java.util.Set;

import com.jcastillo6.domain.entity.Country;

public interface CountrySearchService {
    List<Country> getCountriesByPopulationDensitySortedDesc(Set<Country> countries);
    Country getCountryWithMostBorderingCountriesOfADifferentRegion(Set<Country> countries);
    Set<Country> getCountriesByRegion(String region, Set<Country> countries);
}
