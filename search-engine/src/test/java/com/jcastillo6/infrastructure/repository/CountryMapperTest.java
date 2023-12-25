package com.jcastillo6.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import com.jcastillo6.client.restcountries.model.Country;
import com.jcastillo6.client.restcountries.model.Name;

class CountryMapperTest {

    @Test
    void shouldReturnAListOfMappedCountries() {
        var restCountries = getRestCountryData();

        var countryEntities = CountryMapper.mapToCountryEntity(restCountries);

        assertNotNull(countryEntities);
        assertFalse(countryEntities.isEmpty());
        assertEquals(getExpectedEntityCountries(), countryEntities);
    }

    private Set<com.jcastillo6.domain.entity.Country> getExpectedEntityCountries() {
        var venName = new com.jcastillo6.domain.entity.Name("Venezuela", "Venezuela");
        var venezuela = new com.jcastillo6.domain.entity.Country(venName, 1, 1d, "VEN", "Americas", "South America");

        var colName = new com.jcastillo6.domain.entity.Name("Colombia", "Colombia");
        var colombia = new com.jcastillo6.domain.entity.Country(colName, 2, 2d, "COL", "Americas", "South America");

        var braName = new com.jcastillo6.domain.entity.Name("Brazil", "Brazil");
        var brazil = new com.jcastillo6.domain.entity.Country(braName, 3, 3d, "BRA", "Americas", "South America");

        venezuela.addAllNeighbours(Set.of(colombia, brazil));
        colombia.addAllNeighbours(Set.of(venezuela, brazil));
        brazil.addAllNeighbours(Set.of(venezuela, colombia));
        return Set.of(venezuela, colombia, brazil);
    }

    private Set<Country> getRestCountryData() {
        var venName = new Name("Venezuela", "Venezuela");
        var venezuela = new Country(venName, List.of("BRA", "COL"),
            1, 1d, "VEN", "Americas", "South America");

        var colName = new Name("Colombia", "Colombia");
        var colombia = new Country(colName, List.of("BRA", "VEN"),
            2, 2d, "COL", "Americas", "South America");

        var braName = new Name("Brazil", "Brazil");
        var brazil = new Country(braName, List.of("VEN", "COL"),
            3, 3d, "BRA", "Americas", "South America");

        return Set.of(venezuela, colombia, brazil);
    }
}