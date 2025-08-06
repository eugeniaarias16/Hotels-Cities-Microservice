package com.tourismSystem.hotels.service;

import com.tourismSystem.hotels.dto.HotelDTO;

import java.util.List;

public interface IHotelService {

    HotelDTO findHotelById(Long id);
    HotelDTO findHotelByName(String name);
    HotelDTO findHotelByAddress(String address);
    List<HotelDTO> findHotelsByCity(String city);
    List<HotelDTO> findHotelsByState(String state);
    List<HotelDTO>findHotelsByCountry(String country);
    List<HotelDTO> findHotelsByStars(Integer stars);
    List<HotelDTO> findHotelByFilters(String city, String state, String country, Integer stars);

}
