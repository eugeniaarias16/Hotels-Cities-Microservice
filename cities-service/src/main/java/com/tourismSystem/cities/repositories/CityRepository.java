package com.tourismSystem.cities.repositories;

import com.tourismSystem.cities.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City,Long> {
    Optional<List<City>> findCityByState(String state);
    Optional<List<City>> findCityByCountry(String country);
    Optional <City> findCityByName(String name);
    Optional<List<City>>findCityByStateAndCountry(String state, String country);
}
