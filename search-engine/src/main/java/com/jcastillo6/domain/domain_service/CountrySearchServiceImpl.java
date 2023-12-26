package com.jcastillo6.domain.domain_service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import com.jcastillo6.domain.entity.Country;

public class CountrySearchServiceImpl implements CountrySearchService {

    @Override
    public List<Country> getCountriesByPopulationDensitySortedDesc(Set<Country> countries) {
        return countries.stream()
            .sorted(Comparator.comparingDouble(this::calculatePopulationDensity).reversed())
            .toList();
    }

    @Override
    public Country getCountryWithMostBorderingCountriesOfADifferentRegion(Set<Country> countries) {
        Map<Long, List<Country>> countriesByOutSideBorders = getCountriesByOutsideBorderCount(countries);
        var sortedCountryByBorders = new TreeMap<>(countriesByOutSideBorders);
        var mostOutSideBorderCountries = sortedCountryByBorders.lastEntry();

        if (mostOutSideBorderCountries.getValue() != null && !mostOutSideBorderCountries.getValue().isEmpty()) {
            return mostOutSideBorderCountries.getValue().get(0);
        } else {
            throw new IllegalArgumentException("Something went wrong");
        }
    }

    @Override
    public Set<Country> getCountriesByRegion(String region, Set<Country> countries) {
        return countries.stream()
            .filter(country -> region.equalsIgnoreCase(country.getRegion()))
            .collect(Collectors.toSet());
    }

    private static Map<Long, List<Country>> getCountriesByOutsideBorderCount(Set<Country> countries) {
        return countries.stream()
            .filter(Country::hasNeighbours)
            .collect(Collectors.groupingBy(country -> {
                Iterable<Country> iterable = country::getNeighboursIterator;
                return StreamSupport.stream(iterable.spliterator(), false)
                    .filter(neighbour -> !country.getRegion().equalsIgnoreCase(neighbour.getRegion()))
                    .map(Country::getRegion)
                    .distinct()
                    .count();
            }));
    }

    private double calculatePopulationDensity(Country country) {
        double density = -1d;
        var population = country.getPopulation();
        var area = country.getArea();
        if (area > 0) {
            density = population / area;
        }
        return density;
    }
}
