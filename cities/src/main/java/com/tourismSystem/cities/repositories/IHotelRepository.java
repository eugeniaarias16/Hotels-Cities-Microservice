package com.tourismSystem.cities.repositories;

import com.tourismSystem.cities.dtos.HotelsDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@FeignClient(name="hotels-service") // ¡Sin el atributo fallback aquí!
public interface IHotelRepository {

    Logger logger = LoggerFactory.getLogger(IHotelRepository.class);

    @CircuitBreaker(name = "hotels-service", fallbackMethod = "fallbackFindHotelsById")
    @GetMapping("hotels/errorId/{id}")
    HotelsDTO findHotelsById(@PathVariable("id") Long id);

    @CircuitBreaker(name = "hotels-service", fallbackMethod = "fallbackFindHotelsByCity")
    @GetMapping("/hotels/city/{city}")
    List<HotelsDTO> findHotelsByCity(@PathVariable("city") String city);

    @CircuitBreaker(name = "hotels-service", fallbackMethod = "fallbackFindHotelsByState")
    @GetMapping("/hotels/state/{state}")
    List<HotelsDTO> findHotelsByState(@PathVariable("state") String state);

    @CircuitBreaker(name = "hotels-service", fallbackMethod = "fallbackFindHotelsByCountry")
    @GetMapping("/hotels/country/{country}")
    List<HotelsDTO> findHotelsByCountry(@PathVariable("country") String country);

    @CircuitBreaker(name = "hotels-service", fallbackMethod = "fallbackFindHotelsByFilters")
    @GetMapping("/hotels/filters")
    List<HotelsDTO> findHotelsByFilters(@RequestParam("city") String city,
                                        @RequestParam("state") String state,
                                        @RequestParam("country") String country,
                                        @RequestParam("stars") Integer stars);

    // Métodos fallback dentro de la interfaz
    default  HotelsDTO fallbackFindHotelsById(Long id, Throwable ex){
        logger.warn("🔴 RESILIENCE4J FALLBACK - findHotelsByID para ID: {} por excepción: {}", id, ex.getMessage());
        return null;
    }

    default List<HotelsDTO> fallbackFindHotelsByCity(String city, Throwable ex) {
        logger.warn("🔴 RESILIENCE4J FALLBACK - findHotelsByCity para ciudad: {} por excepción: {}", city, ex.getMessage());
        return Collections.emptyList();
    }

    default List<HotelsDTO> fallbackFindHotelsByState(String state, Throwable ex) {
        logger.warn("🔴 RESILIENCE4J FALLBACK - findHotelsByState para estado: {} por excepción: {}", state, ex.getMessage());
        return Collections.emptyList();
    }

    default List<HotelsDTO> fallbackFindHotelsByCountry(String country, Throwable ex) {
        logger.warn("🔴 RESILIENCE4J FALLBACK - findHotelsByCountry para país: {} por excepción: {}", country, ex.getMessage());
        return Collections.emptyList();
    }

    default List<HotelsDTO> fallbackFindHotelsByFilters(String city, String state, String country, Integer stars, Throwable ex) {
        logger.warn("🔴 RESILIENCE4J FALLBACK - findHotelsByFilters con filtros: {}, {}, {}, {} por excepción: {}", city, state, country, stars, ex.getMessage());
        return Collections.emptyList();
    }
}