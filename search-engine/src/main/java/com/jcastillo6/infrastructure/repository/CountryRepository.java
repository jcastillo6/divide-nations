package com.jcastillo6.infrastructure.repository;

import java.util.Set;
import com.jcastillo6.domain.entity.Country;

public interface CountryRepository {
    Set<Country> getAllCountries();
}
