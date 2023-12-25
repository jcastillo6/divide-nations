package com.jcastillo6.domain.domain_service;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;
import org.junit.jupiter.api.Test;
import com.jcastillo6.domain.entity.Country;

class CountrySearchServiceImplTest {
    @Test
    void shouldReturnASortedListByDensityPopulation() {
        var countries = getSampleCountries();
        var countrySearchService = new CountrySearchServiceImpl();
        var sortedCountriesByDensityPopulation = countrySearchService.getCountriesByPopulationDensitySortedDesc(countries);

        assertNotNull(sortedCountriesByDensityPopulation);
        assertFalse(sortedCountriesByDensityPopulation.isEmpty());
        assertEquals(3, sortedCountriesByDensityPopulation.size());
        assertEquals("Brazil", sortedCountriesByDensityPopulation.get(0).getName().common());
        assertEquals("Colombia", sortedCountriesByDensityPopulation.get(1).getName().common());
        assertEquals("Venezuela", sortedCountriesByDensityPopulation.get(2).getName().common());
    }

    @Test
    void shouldReturnTheCountryWithMostBorderingCountriesOfADifferentRegion() {
        var countries = getSampleCountriesFromDifferentRegions();
        var countrySearchService = new CountrySearchServiceImpl();
        var countryWithMostBorderingCountriesOfADifferentRegion = countrySearchService.getCountryWithMostBorderingCountriesOfADifferentRegion(countries);
        assertNotNull(countryWithMostBorderingCountriesOfADifferentRegion);
        assertEquals("Venezuela", countryWithMostBorderingCountriesOfADifferentRegion.getName().common());
    }

    private Set<Country> getSampleCountriesFromDifferentRegions() {
        var venName = new com.jcastillo6.domain.entity.Name("Venezuela", "Venezuela");
        var venezuela = new com.jcastillo6.domain.entity.Country(venName, 10, 10d, "VEN", "Americas", "South America");

        var colName = new com.jcastillo6.domain.entity.Name("Colombia", "Colombia");
        var colombia = new com.jcastillo6.domain.entity.Country(colName, 20, 10d, "COL", "Americas", "South America");

        var braName = new com.jcastillo6.domain.entity.Name("Brazil", "Brazil");
        var brazil = new com.jcastillo6.domain.entity.Country(braName, 30, 10d, "BRA", "Americas", "South America");

        var chinaName = new com.jcastillo6.domain.entity.Name("China", "China");
        var china = new com.jcastillo6.domain.entity.Country(chinaName, 30, 10d, "CHI", "Asia", "Asia");

        var saudiArabiaName = new com.jcastillo6.domain.entity.Name("Saudi Arabia", "Saudi Arabia");
        var saudiArabia = new com.jcastillo6.domain.entity.Country(saudiArabiaName, 30, 10d, "SAU", "Middle East", "Middle East");

        var netherlandName = new com.jcastillo6.domain.entity.Name("Netherlands", "Netherlands");
        var netherlands = new com.jcastillo6.domain.entity.Country(netherlandName, 30, 10d, "NLD", "Europe", "Europe");


        venezuela.addAllNeighbours(Set.of(colombia, brazil, china, saudiArabia, netherlands));
        colombia.addAllNeighbours(Set.of(venezuela, brazil, china));
        brazil.addAllNeighbours(Set.of(venezuela, colombia, saudiArabia));
        return Set.of(venezuela, colombia, brazil);
    }

    private Set<Country> getSampleCountries() {
        var venName = new com.jcastillo6.domain.entity.Name("Venezuela", "Venezuela");
        var venezuela = new com.jcastillo6.domain.entity.Country(venName, 10, 10d, "VEN", "Americas", "South America");

        var colName = new com.jcastillo6.domain.entity.Name("Colombia", "Colombia");
        var colombia = new com.jcastillo6.domain.entity.Country(colName, 20, 10d, "COL", "Americas", "South America");

        var braName = new com.jcastillo6.domain.entity.Name("Brazil", "Brazil");
        var brazil = new com.jcastillo6.domain.entity.Country(braName, 30, 10d, "BRA", "Americas", "South America");

        venezuela.addAllNeighbours(Set.of(colombia, brazil));
        colombia.addAllNeighbours(Set.of(venezuela, brazil));
        brazil.addAllNeighbours(Set.of(venezuela, colombia));
        return Set.of(venezuela, colombia, brazil);
    }
}