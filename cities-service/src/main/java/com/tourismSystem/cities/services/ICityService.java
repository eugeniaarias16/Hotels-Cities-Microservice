package com.tourismSystem.cities.services;

import com.tourismSystem.cities.dtos.CityDTO;

import java.util.List;
import java.util.Map;


public interface ICityService {
    CityDTO findCityById(Long id);

    CityDTO findCityByName(String name);

    List<CityDTO> findAllCites();
    List<CityDTO> findCitiesByState(String state);
    List<CityDTO> findCitiesByCountry(String country);

    CityDTO createCiy(CityDTO cityDTO);
    CityDTO updateCityById(Long id, Map<String, Object> updates);
    List<CityDTO>  findCityByStateAndCountry(String state, String country);
    void deleteCityById(Long id);
}
