package com.tourismSystem.hotels.service;

import com.tourismSystem.hotels.dto.HotelDTO;
import com.tourismSystem.hotels.entities.Hotel;
import com.tourismSystem.hotels.exceptions.BadRequest;
import com.tourismSystem.hotels.exceptions.InformationNotAvailable;
import com.tourismSystem.hotels.exceptions.ResourceNotFound;
import com.tourismSystem.hotels.repositories.HotelRepository;
import jakarta.ws.rs.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelService implements IHotelService {

    final private HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }


    //Method to try Circuit Breaker
    @Override
    public HotelDTO findHotelsByID(Long id) {
        throw new InformationNotAvailable("Circuit Breaker's Testing");
    /*    Hotel hotel= hotelRepository.findById(id)
                .orElseThrow(()->new ResourceNotFound("No Hotel Found."));
        return new HotelDTO(hotel);*/
    }



    @Override
    public HotelDTO findHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Hotel not found with id" + id));
        return new HotelDTO(hotel);
    }

    @Override
    public HotelDTO findHotelByName(String name) {
        Hotel hotel = hotelRepository.findHotelByNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFound("Hotel not found with that name" + name));
        return new HotelDTO(hotel);
    }

    @Override
    public HotelDTO findHotelByAddress(String address) {
        Hotel hotel = hotelRepository.findHotelByAddressIgnoreCase(address)
                .orElseThrow(() -> new ResourceNotFound("Hotel not found with that address " + address));
        return new HotelDTO(hotel);
    }

    @Override
    public List<HotelDTO> findHotelsByCity(String city) {
        List<Hotel> hotels = hotelRepository.findHotelsByCityIgnoreCase(city)
                .orElse(null);

        if (hotels == null || hotels.isEmpty()) {
            throw new ResourceNotFound("Hotel not found with that city " + city);
        }

        return hotels.stream()
                .map(HotelDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<HotelDTO> findHotelsByState(String state) {
        List<Hotel> hotels = hotelRepository.findHotelsByStateIgnoreCase(state)
                .orElse(null);

        if (hotels == null || hotels.isEmpty()) {
            throw new ResourceNotFound("Hotel not found in that State: " + state);
        }

        return hotels.stream()
                .map(HotelDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<HotelDTO> findHotelsByCountry(String country) {
        List<Hotel> hotels = hotelRepository.findHotelsByCountryIgnoreCase(country)
                .orElse(null);

        if (hotels == null || hotels.isEmpty()) {
            throw new ResourceNotFound("Hotel not found in that country  " + country);
        }

        return hotels.stream()
                .map(HotelDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<HotelDTO> findHotelsByStars(Integer stars) {
        if (errorStars(stars)) {
            throw new BadRequest("Stars cannot be lower than 1 or greater tha 5");
        }
        String starsIcon = "★".repeat(stars);
        List<Hotel> hotels = hotelRepository.findHotelsByStars(stars)
                .orElse(null);

        if (hotels == null || hotels.isEmpty()) {
            throw new ResourceNotFound("Hotel not found with " + starsIcon);
        }

        return hotels.stream()
                .map(HotelDTO::new)
                .collect(Collectors.toList());
    }

    public boolean errorStars(Integer numbStars) {
        return numbStars < 1 || numbStars > 5;
    }

    @Override
    public List<HotelDTO> findHotelByFilters(String city, String state, String country, Integer stars) {
        // Input validation
        if (errorStars(stars)) {
            throw new BadRequest("Stars cannot be lower than 1 or greater tha 5");
        }
        if (areAllParametersNull(city, state, country, stars)) {
            throw new BadRequest("At least one filter must be provided");
        }

        List<Hotel> hotels = hotelRepository.findByFilters(city, state, country, stars)
                .orElse(null);

        if (hotels == null || hotels.isEmpty()) {
            throw new ResourceNotFound("No Hotel Found.");
        }

        return hotels.stream()
                .map(HotelDTO::new)
                .collect(Collectors.toList());
    }


    private boolean areAllParametersNull(String city, String state, String country, Integer stars) {
        return city == null && state == null && country == null && stars == null;
    }

}