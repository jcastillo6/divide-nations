package com.jcastillo6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import com.jcastillo6.application.SearchEngineImpl;
import com.jcastillo6.client.restcountries.RestCountries;
import com.jcastillo6.domain.domain_service.CountrySearchServiceImpl;
import com.jcastillo6.domain.entity.Country;
import com.jcastillo6.infrastructure.repository.CountryRepositoryImpl;

public class Main {
    public static final String DEFAULT_FILE_NAME_FOR_COUNTRY_WITH_MOST_BORDERS = "CountryWithMostBorderingCountriesOfADifferentRegion.txt";
    public static final String DEFAULT_FILE_NAME_FOR_POPULATION_DENSITY = "CountriesByPopulationDensitySortedDesc.txt";
    private static String basePath = ".";

    public static void main(String[] args) {
        if (args != null && args.length > 0) {
            basePath = args[0];
        }

        var countryInformationProvider = new RestCountries();
        var countryRepository = new CountryRepositoryImpl(countryInformationProvider);
        var countrySearchService = new CountrySearchServiceImpl();
        var searchEngine = new SearchEngineImpl(countryRepository, countrySearchService);
        var countryWithMostOutBorders = searchEngine.getCountryWithMostBorderingCountriesOfADifferentRegion("Asia");
        var countriesSortedByPopulationDensity = searchEngine.getCountriesByPopulationDensitySortedDesc();

        printResult(countriesSortedByPopulationDensity, DEFAULT_FILE_NAME_FOR_POPULATION_DENSITY);
        printResult(List.of(countryWithMostOutBorders), DEFAULT_FILE_NAME_FOR_COUNTRY_WITH_MOST_BORDERS);


    }

    private static void printResult(List<Country> countries, String fileName) {
        try {
            printToFile(countries, Paths.get(basePath, fileName));
        } catch (IOException e) {
            System.out.println("Error while trying to create a the files, fallback to default output: " + e);
            printToConsole(countries, fileName);
        }
    }

    private static void printToConsole(List<Country> countries, String fileName) {
        System.out.println("Starting to write file "+fileName);
        countries.stream().forEach(System.out::println);
        System.out.println("End to write file "+fileName);
    }

    private static void printToFile(List<Country> countries,  Path path) throws IOException {
        Path parent = path.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        var pathResult = Files.write(path, countries.stream().map(Country::toString)
            .toList(), StandardOpenOption.CREATE_NEW, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("Path created at "+pathResult);
    }
}