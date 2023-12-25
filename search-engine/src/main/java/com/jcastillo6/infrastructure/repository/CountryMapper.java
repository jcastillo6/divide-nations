package com.jcastillo6.infrastructure.repository;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.jcastillo6.domain.entity.Country;
import com.jcastillo6.domain.entity.Name;

public final class CountryMapper {

    private CountryMapper() {
    }

    public static Set<Country> mapToCountryEntity(Set<com.jcastillo6.client.restcountries.model.Country> restCountries) {
        var countries = toCountries(restCountries);
        var restCountriesByCca3Code = getRestCountriesByCca3Code(restCountries);
        var countriesByCc3Code = getCountriesByCc3Code(countries);
        for (var country : countries) {
            if (country.getCca3() != null) {
                var neighbours = getNeighbours(country.getCca3(), restCountriesByCca3Code, countriesByCc3Code);
                neighbours.ifPresent(country::addAllNeighbours);
            }
        }

        return countries;
    }

    private static Optional<Set<Country>> getNeighbours(String cca3, Map<String, com.jcastillo6.client.restcountries.model.Country> restCountriesByCca3Code, Map<String, Country> countriesByCc3Code) {
        Set<Country> countries = new HashSet<>();
        if (restCountriesByCca3Code.containsKey(cca3)) {
            var restCountry = restCountriesByCca3Code.get(cca3);
            if (restCountry.borders() != null) {
                for (var border : restCountry.borders()) {
                    if (countriesByCc3Code.containsKey(border)){
                        countries.add(countriesByCc3Code.get(border));
                    } else {
                        throw new IllegalArgumentException("Border not found "+cca3+": border "+border);
                    }
                }
            }
        }
        return countries.isEmpty() ? Optional.empty() : Optional.of(countries);
    }

    private static Map<String, Country> getCountriesByCc3Code(Set<Country> countries) {
        return countries.stream()
            .filter(country -> country.getCca3() != null)
            .collect(Collectors.toMap(Country::getCca3, Function.identity()));
    }

    private static Map<String, com.jcastillo6.client.restcountries.model.Country> getRestCountriesByCca3Code(Set<com.jcastillo6.client.restcountries.model.Country> restCountries) {
        return restCountries.stream().filter(restCountry -> restCountry.cca3() != null)
            .collect(Collectors.toMap(com.jcastillo6.client.restcountries.model.Country::cca3, Function.identity()));
    }

    private static Set<Country> toCountries(Set<com.jcastillo6.client.restcountries.model.Country> restCountries) {
        return restCountries.stream()
            .map(CountryMapper::toCountryEntity)
            .collect(Collectors.toSet());
    }

    private static Country toCountryEntity(com.jcastillo6.client.restcountries.model.Country restCountry) {
        var name = Optional.ofNullable(restCountry.name())
            .map(restName -> new Name(restName.common(), restName.official()))
            .orElseThrow(IllegalArgumentException::new);
        return new Country(name, restCountry.population(), restCountry.area(), restCountry.cca3(), restCountry.region(), restCountry.subregion());
    }
}
