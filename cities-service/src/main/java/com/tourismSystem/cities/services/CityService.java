package com.tourismSystem.cities.services;

import com.tourismSystem.cities.dtos.CityDTO;
import com.tourismSystem.cities.entities.City;
import com.tourismSystem.cities.exceptions.BadRequest;
import com.tourismSystem.cities.exceptions.ResourceNotFound;
import com.tourismSystem.cities.repositories.CityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CityService implements ICityService {

    final private CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }


    @Override
    public CityDTO findCityById(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("City not found with the id " + id));
        return new CityDTO(city);
    }


    @Override
    public CityDTO findCityByName(String name) {
        City city = cityRepository.findCityByName(name)
                .orElseThrow(() -> new ResourceNotFound("City not found with the name " + name));
        return new CityDTO(city);
    }

    @Override
    public List<CityDTO> findAllCites() {
        return cityRepository.findAll()
                .stream()
                .map(CityDTO::new)
                .collect(Collectors.toList());

    }

    @Override
    public List<CityDTO> findCitiesByState(String state) {
        List<City> citiesList= cityRepository.findCityByState(state)
                .orElseThrow(()->new ResourceNotFound("No cities were found in the state of"+state));
        return citiesList.stream()
                .map(CityDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<CityDTO> findCitiesByCountry(String country) {
        List<City> citiesList= cityRepository.findCityByCountry(country)
                .orElseThrow(()->new ResourceNotFound("No cities were found in the country of "+country));
        return citiesList.stream()
                .map(CityDTO::new)
                .collect(Collectors.toList());
    }



    @Override
    public CityDTO createCiy(CityDTO cityDTO) {
        City city= cityDTO.toEntity();
        City citySaved= cityRepository.save(city);
        return new CityDTO(citySaved);
    }

    @Override
    public CityDTO updateCityById(Long id, Map<String,Object> updates) {
        City existingCity=cityRepository.findById(id)
                .orElseThrow(()->new ResourceNotFound("City not found with the id + id"));
        updates.forEach((key,value)->{
            switch (key){
                case "name"-> existingCity.setName((String) value);
                case "state"->existingCity.setState((String) value);
                case "country"->existingCity.setCountry((String) value);
                default -> System.out.println("Field not recognized"+ key);
            }
        });

        City savedCity= cityRepository.save(existingCity);
        return new CityDTO(savedCity);
    }

    @Override
    public List<CityDTO> findCityByStateAndCountry(String state, String country) {
        List<City> cities= cityRepository.findCityByStateAndCountry(state,country)
                .orElseThrow(()-> new ResourceNotFound("No cities found in that location."));
        return cities.stream()
                .map(CityDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCityById(Long id) {
        cityRepository.deleteById(id);

    }


}
