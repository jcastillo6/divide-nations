package com.jcastillo6.application;

import java.util.List;
import java.util.Set;
import com.jcastillo6.domain.entity.Country;

public interface SearchEngine {
    List<Country> getCountriesByPopulationDensitySortedDesc();
    Country getCountryWithMostBorderingCountriesOfADifferentRegion(String region);
    Set<Country> getCountriesByRegion(String region);
}