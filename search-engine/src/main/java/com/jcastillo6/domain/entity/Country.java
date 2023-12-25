package com.jcastillo6.domain.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Country {
     private final Name name;
     private final Set<Country> neighbours;
     private final Integer population;
     private final Double area;
     private final String cca3;
     private final String region;
     private final String subregion;

    public Country(Name name, Integer population, Double area, String cca3, String region, String subregion) {
        this.name = name;
        this.neighbours = new HashSet<>();
        this.population = population;
        this.area = area;
        this.cca3 = cca3;
        this.region = region;
        this.subregion = subregion;
    }

    public Name getName() {
        return name;
    }

    public Integer getPopulation() {
        return population;
    }

    public Double getArea() {
        return area;
    }

    public String getCca3() {
        return cca3;
    }

    public String getRegion() {
        return region;
    }

    public String getSubregion() {
        return subregion;
    }

    public boolean addNeighbour(Country neighbour) {
        return this.neighbours.add(neighbour);
    }

    public boolean addAllNeighbours(Collection<Country> neighbours) {
        return this.neighbours.addAll(neighbours);
    }

    public Iterator<Country> getNeighboursIterator() {
        return neighbours.iterator();
    }

    public boolean hasNeighbours() {
        return !neighbours.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        if (!Objects.equals(name, country.name)) return false;
        if (!Objects.equals(population, country.population)) return false;
        if (!Objects.equals(area, country.area)) return false;
        if (!Objects.equals(cca3, country.cca3)) return false;
        if (!Objects.equals(region, country.region)) return false;
        return Objects.equals(subregion, country.subregion);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (population != null ? population.hashCode() : 0);
        result = 31 * result + (area != null ? area.hashCode() : 0);
        result = 31 * result + (cca3 != null ? cca3.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (subregion != null ? subregion.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Country{" +
            "name=" + name +
            ", neighbours=" + getNeighboursAsText() +
            ", population=" + population +
            ", area=" + area +
            ", fifa='" + cca3 + '\'' +
            ", region='" + region + '\'' +
            ", subregion='" + subregion + '\'' +
            '}';
    }

    private String getNeighboursAsText() {
        return neighbours.stream()
            .map(neighbour -> neighbour.getName().official())
            .collect(Collectors.joining(","));
    }
}
