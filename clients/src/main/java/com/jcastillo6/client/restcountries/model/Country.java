package com.jcastillo6.client.restcountries.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Country(Name name, List<String> borders, Integer population, Double area, String cca3, String region, String subregion) {

}
