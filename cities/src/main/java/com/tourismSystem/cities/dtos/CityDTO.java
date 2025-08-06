package com.tourismSystem.cities.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tourismSystem.cities.entities.City;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String name;
    private String state;
    private String country;


    public CityDTO(City city){
        id= city.getId();
        name= city.getName();
        country=city.getCountry();
        state= city.getState();
    }

    public City toEntity(){
        City city= new City();
        city.setId(id);
        city.setName(name);
        city.setCountry(country);
        city.setState(state);
        return city;
    }
}
