package com.tourismSystem.cities.dtos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class HotelsDTO {
    private String name;
    private String address;
    private Integer stars;
    private String city_name;
    private String state;
    private String country;


}
