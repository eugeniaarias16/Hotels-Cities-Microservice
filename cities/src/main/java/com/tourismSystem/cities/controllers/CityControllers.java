package com.tourismSystem.cities.controllers;

import com.tourismSystem.cities.dtos.CityDTO;
import com.tourismSystem.cities.dtos.HotelsDTO;
import com.tourismSystem.cities.repositories.IHotelRepository;
import com.tourismSystem.cities.services.ICityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tourism")
@Tag(name = "Tourism Management", description = "API for managing tourist cities and hotel search operations")
public class CityControllers {

    private final ICityService cityService;
    private final IHotelRepository hotelRepository;

    public CityControllers(ICityService cityService, IHotelRepository hotelRepository) {
        this.cityService = cityService;
        this.hotelRepository = hotelRepository;
    }

    /* =================== GET ENDPOINTS =================== */

    @Operation(
            summary = "Get all cities",
            description = "Returns a complete list of all tourist cities available in the system",
            tags = {"Cities"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cities list retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CityDTO.class),
                            examples = @ExampleObject(
                                    name = "Cities list",
                                    value = "[{\"id\":1,\"name\":\"Miami\",\"state\":\"Florida\",\"country\":\"USA\"},{\"id\":2,\"name\":\"Barcelona\",\"state\":\"Catalonia\",\"country\":\"Spain\"}]"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @GetMapping("/cities")
    public ResponseEntity<List<CityDTO>> findAllCities() {
        List<CityDTO> cities = cityService.findAllCites();
        return ResponseEntity.ok(cities);
    }

    @Operation(
            summary = "Find city by ID",
            description = "Retrieves detailed information of a specific city using its unique identifier",
            tags = {"Cities"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "City found successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CityDTO.class),
                            examples = @ExampleObject(
                                    name = "City found",
                                    value = "{\"id\":1,\"name\":\"Miami\",\"state\":\"Florida\",\"country\":\"USA\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "City not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Error 404",
                                    value = "{\"error\":\"Not Found\",\"message\":\"City not found with ID: 999\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid ID format",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @GetMapping("/city/id/{id}")
    public ResponseEntity<CityDTO> findCityById(
            @Parameter(
                    description = "Unique city identifier",
                    example = "1",
                    required = true
            )
            @PathVariable Long id) {
        CityDTO city = cityService.findCityById(id);
        return ResponseEntity.ok(city);
    }

    @Operation(
            summary = "Find city by name",
            description = "Finds a specific city using its name. The search is case-insensitive",
            tags = {"Cities"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "City found successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CityDTO.class),
                            examples = @ExampleObject(
                                    name = "City by name",
                                    value = "{\"id\":1,\"name\":\"Miami\",\"state\":\"Florida\",\"country\":\"USA\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No city found with the specified name"
            )
    })
    @GetMapping("/city/name/{name}")
    public ResponseEntity<CityDTO> findCityByName(
            @Parameter(
                    description = "Name of the city to search for",
                    example = "Miami",
                    required = true
            )
            @PathVariable String name) {
        CityDTO city = cityService.findCityByName(name);
        return ResponseEntity.ok(city);
    }

    /* =================== POST ENDPOINTS =================== */

    @Operation(
            summary = "Create new city",
            description = "Registers a new tourist city in the system with all its information",
            tags = {"Cities"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "City created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CityDTO.class),
                            examples = @ExampleObject(
                                    name = "Created city",
                                    value = "{\"id\":3,\"name\":\"Madrid\",\"state\":\"Madrid\",\"country\":\"Spain\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Validation error",
                                    value = "{\"error\":\"Bad Request\",\"message\":\"City name is required\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "City already exists",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PostMapping("/city/create")
    public ResponseEntity<CityDTO> createCity(
            @Parameter(
                    description = "City data to create",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CityDTO.class),
                            examples = @ExampleObject(
                                    name = "New city",
                                    value = "{\"name\":\"Madrid\",\"state\":\"Madrid\",\"country\":\"Spain\"}"
                            )
                    )
            )
            @Valid @RequestBody CityDTO cityDTO) {
        CityDTO city = cityService.createCiy(cityDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(city);
    }

    /* =================== PUT ENDPOINTS =================== */

    @Operation(
            summary = "Update city",
            description = "Partially updates an existing city's data. Only provided fields will be modified",
            tags = {"Cities"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "City updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CityDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "City not found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid update data"
            )
    })
    @PutMapping("/city/{id}")
    public ResponseEntity<CityDTO> updateCity(
            @Parameter(
                    description = "ID of the city to update",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,
            @Parameter(
                    description = "Fields to update with their new values",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Update fields",
                                    value = "{\"name\":\"New Miami\",\"description\":\"Updated description\"}"
                            )
                    )
            )
            @RequestBody Map<String, Object> updates) {
        CityDTO updatedCity = cityService.updateCityById(id, updates);
        return ResponseEntity.ok(updatedCity);
    }

    /* =================== DELETE ENDPOINTS =================== */

    @Operation(
            summary = "Delete city",
            description = "Removes a city from the system permanently",
            tags = {"Cities"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "City deleted successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Success message",
                                    value = "\"The city was deleted successfully\""
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "City not found"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Cannot delete city - it may have associated hotels"
            )
    })
    @DeleteMapping("/city/{id}")
    public ResponseEntity<String> deleteCity(
            @Parameter(
                    description = "ID of the city to delete",
                    example = "1",
                    required = true
            )
            @PathVariable Long id) {
        cityService.deleteCityById(id);
        return ResponseEntity.ok("The city was deleted successfully");
    }

    /* =================== HOTEL INTEGRATION ENDPOINTS =================== */
    @Operation(
            summary = "Endpoint to try Circuit Breaker",
            description = "This endpoint is for testing the correct operation of Circuit Breaker. We do this by manually triggering a fault.",
            tags = {"Circuit Breaker"}
    )
    @GetMapping("/hotels/errorId/{id}")
    ResponseEntity<HotelsDTO> findHotelsById(@PathVariable Long id){
        HotelsDTO hotel= hotelRepository.findHotelsById(id);
        return ResponseEntity.ok(hotel);
    }


    @Operation(
            summary = "Find hotels by city",
            description = "Retrieves all hotels available in a specific city through integration with Hotels Service",
            tags = {"Hotel Integration"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Hotels found successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HotelsDTO.class),
                            examples = @ExampleObject(
                                    name = "Hotels in city",
                                    value = "[{\"name\":\"Ocean Drive Resort\",\"address\":\"123 Ocean Dr\",\"stars\":5,\"city_name\":\"Miami\",\"state\":\"Florida\",\"country\":\"USA\"}]"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No hotels found in the specified city"
            )
    })
    @GetMapping("/city/{city}/hotel")
    public ResponseEntity<List<HotelsDTO>> findHotelByCity(
            @Parameter(
                    description = "Name of the city to search hotels in",
                    example = "Miami",
                    required = true
            )
            @PathVariable String city) {
        return ResponseEntity.ok(hotelRepository.findHotelsByCity(city));
    }

    @Operation(
            summary = "Find hotels by state",
            description = "Retrieves all hotels available in a specific state/province",
            tags = {"Hotel Integration"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Hotels found successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HotelsDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No hotels found in the specified state"
            )
    })
    @GetMapping("/state/{state}/hotel")
    public ResponseEntity<List<HotelsDTO>> findHotelByState(
            @Parameter(
                    description = "Name of the state/province to search hotels in",
                    example = "Florida",
                    required = true
            )
            @PathVariable String state) {
        return ResponseEntity.ok(hotelRepository.findHotelsByState(state));
    }

    @Operation(
            summary = "Find hotels by country",
            description = "Retrieves all hotels available in a specific country",
            tags = {"Hotel Integration"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Hotels found successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HotelsDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No hotels found in the specified country"
            )
    })
    @GetMapping("/country/{country}/hotel")
    public ResponseEntity<List<HotelsDTO>> findHotelByCountry(
            @Parameter(
                    description = "Name of the country to search hotels in",
                    example = "USA",
                    required = true
            )
            @PathVariable String country) {
        return ResponseEntity.ok(hotelRepository.findHotelsByCountry(country));
    }

    @Operation(
            summary = "Search hotels with filters",
            description = "Advanced hotel search using multiple criteria including city, state, country, and star rating",
            tags = {"Hotel Integration"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Hotels found matching the criteria",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HotelsDTO.class),
                            examples = @ExampleObject(
                                    name = "Filtered hotels",
                                    value = "[{\"name\":\"Luxury Beach Resort\",\"address\":\"456 Beach Ave\",\"stars\":5,\"city_name\":\"Miami\",\"state\":\"Florida\",\"country\":\"USA\"}]"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid filter parameters"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No hotels found matching the specified criteria"
            )
    })
    @GetMapping("/hotel/search")
    public ResponseEntity<List<HotelsDTO>> findHotelByFilters(
            @Parameter(
                    description = "City name to filter by",
                    example = "Miami"
            )
            @RequestParam(required = false) String city,
            @Parameter(
                    description = "State/province name to filter by",
                    example = "Florida"
            )
            @RequestParam(required = false) String state,
            @Parameter(
                    description = "Country name to filter by",
                    example = "USA"
            )
            @RequestParam(required = false) String country,
            @Parameter(
                    description = "Star rating to filter by (1-5 stars)",
                    example = "5"
            )
            @RequestParam(required = false) Integer stars) {
        return ResponseEntity.ok(hotelRepository.findHotelsByFilters(city, state, country, stars));
    }
}