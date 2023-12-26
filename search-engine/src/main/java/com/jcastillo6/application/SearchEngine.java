package com.jcastillo6.application;

import java.util.List;
import java.util.Set;
import com.jcastillo6.domain.entity.Country;

/**
 * Search engine services contract
 *
 */
public interface SearchEngine {
    /**
     * Get a list of countries sorted by population density
     *
     * @return List of countries
     */
    List<Country> getCountriesByPopulationDensitySortedDesc();

    /**
     * Get the country with most bordering countries of different region
     *
     * @param region to look up
     * @return country with most bordering countries of different region
     */
    Country getCountryWithMostBorderingCountriesOfADifferentRegion(String region);

    /**
     * look up the countries of a specific region
     *
     * @param region to look up
     * @return set of countries
     */
    Set<Country> getCountriesByRegion(String region);
}