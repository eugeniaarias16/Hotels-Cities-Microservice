package com.tourismSystem.cities.repositories;

import com.tourismSystem.cities.dtos.HotelsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="hotels-service")
public interface IHotelRepository {

    @GetMapping("/hotels/city/{city}")
    List<HotelsDTO> findHotelsByCity(@PathVariable("city") String city);

    @GetMapping("/hotels/state/{state}")
    List<HotelsDTO> findHotelsByState(@PathVariable("state") String state);

    @GetMapping("/hotels/country/{country}")
    List<HotelsDTO>findHotelsByCountry(@PathVariable("country") String country);


    @GetMapping("/hotels/filters")
    List<HotelsDTO> findHotelsByFilters(@RequestParam ("city") String city,
                                        @RequestParam ("state")String state,
                                        @RequestParam ("country") String country,
                                        @RequestParam ("stars")Integer stars);

}
