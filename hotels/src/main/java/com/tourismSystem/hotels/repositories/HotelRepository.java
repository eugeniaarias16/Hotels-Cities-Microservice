package com.tourismSystem.hotels.repositories;

import com.tourismSystem.hotels.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel,Long> {

    Optional<Hotel> findHotelByNameIgnoreCase(String name);
    Optional<Hotel> findHotelByAddressIgnoreCase(String address);
    Optional<List<Hotel>> findHotelsByCityIgnoreCase(String city);
    Optional<List<Hotel>> findHotelsByStateIgnoreCase(String state);
    Optional<List<Hotel>> findHotelsByCountryIgnoreCase(String country);
    Optional<List<Hotel>> findHotelsByStars(Integer stars);

    @Query("SELECT h FROM Hotel h WHERE " +
            "(:city IS NULL OR LOWER(h.city) LIKE LOWER(CONCAT('%', :city, '%'))) AND " +
            "(:state IS NULL OR LOWER(h.state) LIKE LOWER(CONCAT('%', :state, '%'))) AND " +
            "(:country IS NULL OR LOWER(h.country) LIKE LOWER(CONCAT('%', :country, '%'))) AND " +
            "(:stars IS NULL OR h.stars = :stars)")
    Optional<List<Hotel>> findByFilters(String city, String state, String country, Integer stars);
}
