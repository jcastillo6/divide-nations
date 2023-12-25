package com.jcastillo6.client.restcountries.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Name(String common, String official) {
}
