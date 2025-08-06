package com.tourismSystem.hotels.controllers;

import com.tourismSystem.hotels.dto.HotelDTO;
import com.tourismSystem.hotels.service.IHotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@Tag(
        name = "Hotels Management",
        description = "API for comprehensive hotel management including search, filtering, and detailed information retrieval"
)
public class HotelController {

    private final IHotelService hotelService;

    public HotelController(IHotelService hotelService) {
        this.hotelService = hotelService;
    }

    /* =================== SINGLE HOTEL ENDPOINTS =================== */

    @Operation(
            summary = "Find hotel by ID",
            description = "Retrieves detailed information of a specific hotel using its unique identifier",
            tags = {"Hotel Details"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Hotel found successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HotelDTO.class),
                            examples = @ExampleObject(
                                    name = "Hotel details",
                                    value = "{\"id\":1,\"name\":\"Ocean Drive Luxury Resort\",\"address\":\"123 Ocean Drive, Miami Beach\",\"stars\":5,\"city_name\":\"Miami\",\"state\":\"Florida\",\"country\":\"USA\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Hotel not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Hotel not found",
                                    value = "{\"error\":\"Not Found\",\"message\":\"Hotel not found with ID: 999\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid ID format"
            )
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<HotelDTO> findHotelById(
            @Parameter(
                    description = "Unique hotel identifier",
                    example = "1",
                    required = true
            )
            @PathVariable Long id) {
        HotelDTO hotelDTO = hotelService.findHotelById(id);
        return ResponseEntity.ok(hotelDTO);
    }

    @Operation(
            summary = "Find hotel by name",
            description = "Searches for a specific hotel using its name. The search is case-insensitive and supports partial matches",
            tags = {"Hotel Details"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Hotel found successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HotelDTO.class),
                            examples = @ExampleObject(
                                    name = "Hotel by name",
                                    value = "{\"id\":2,\"name\":\"Paradise Beach Hotel\",\"address\":\"456 Paradise Ave\",\"stars\":4,\"city_name\":\"Miami\",\"state\":\"Florida\",\"country\":\"USA\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No hotel found with the specified name"
            )
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<HotelDTO> findHotelByName(
            @Parameter(
                    description = "Name of the hotel to search for",
                    example = "Paradise Beach Hotel",
                    required = true
            )
            @PathVariable String name) {
        HotelDTO hotelDTO = hotelService.findHotelByName(name);
        return ResponseEntity.ok(hotelDTO);
    }

    @Operation(
            summary = "Find hotel by address",
            description = "Locates a hotel using its physical address. Useful for finding hotels in specific locations",
            tags = {"Hotel Details"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Hotel found at the specified address",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HotelDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No hotel found at the specified address"
            )
    })
    @GetMapping("/address/{address}")
    public ResponseEntity<HotelDTO> findHotelByAddress(
            @Parameter(
                    description = "Physical address of the hotel",
                    example = "123 Ocean Drive, Miami Beach",
                    required = true
            )
            @PathVariable String address) {
        HotelDTO hotelDTO = hotelService.findHotelByAddress(address);
        return ResponseEntity.ok(hotelDTO);
    }

    /* =================== LOCATION-BASED SEARCH ENDPOINTS =================== */

    @Operation(
            summary = "Find hotels by city",
            description = "Retrieves all hotels located in a specific city. Perfect for travelers planning city visits",
            tags = {"Location Search"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Hotels found in the specified city",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HotelDTO.class),
                            examples = @ExampleObject(
                                    name = "Hotels in city",
                                    value = "[{\"id\":1,\"name\":\"Ocean Drive Resort\",\"address\":\"123 Ocean Dr\",\"stars\":5,\"city_name\":\"Miami\",\"state\":\"Florida\",\"country\":\"USA\"},{\"id\":2,\"name\":\"Downtown Hotel\",\"address\":\"789 Main St\",\"stars\":3,\"city_name\":\"Miami\",\"state\":\"Florida\",\"country\":\"USA\"}]"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No hotels found in the specified city"
            )
    })
    @GetMapping("/city/{city}")
    public ResponseEntity<List<HotelDTO>> findHotelByCity(
            @Parameter(
                    description = "Name of the city to search hotels in",
                    example = "Miami",
                    required = true
            )
            @PathVariable String city) {
        List<HotelDTO> hotelDTOList = hotelService.findHotelsByCity(city);
        return ResponseEntity.ok(hotelDTOList);
    }

    @Operation(
            summary = "Find hotels by state",
            description = "Retrieves all hotels located in a specific state or province. Ideal for regional travel planning",
            tags = {"Location Search"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Hotels found in the specified state",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HotelDTO.class),
                            examples = @ExampleObject(
                                    name = "Hotels in state",
                                    value = "[{\"id\":3,\"name\":\"Orlando Resort\",\"address\":\"321 Theme Park Blvd\",\"stars\":4,\"city_name\":\"Orlando\",\"state\":\"Florida\",\"country\":\"USA\"},{\"id\":4,\"name\":\"Tampa Bay Hotel\",\"address\":\"654 Bay Street\",\"stars\":3,\"city_name\":\"Tampa\",\"state\":\"Florida\",\"country\":\"USA\"}]"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No hotels found in the specified state"
            )
    })
    @GetMapping("/state/{state}")
    public ResponseEntity<List<HotelDTO>> findHotelByState(
            @Parameter(
                    description = "Name of the state/province to search hotels in",
                    example = "Florida",
                    required = true
            )
            @PathVariable String state) {
        List<HotelDTO> hotelDTOList = hotelService.findHotelsByState(state);
        return ResponseEntity.ok(hotelDTOList);
    }

    @Operation(
            summary = "Find hotels by country",
            description = "Retrieves all hotels located in a specific country. Perfect for international travel planning",
            tags = {"Location Search"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Hotels found in the specified country",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HotelDTO.class),
                            examples = @ExampleObject(
                                    name = "Hotels in country",
                                    value = "[{\"id\":5,\"name\":\"New York Plaza\",\"address\":\"5th Avenue\",\"stars\":5,\"city_name\":\"New York\",\"state\":\"New York\",\"country\":\"USA\"},{\"id\":6,\"name\":\"LA Beach Resort\",\"address\":\"Santa Monica Blvd\",\"stars\":4,\"city_name\":\"Los Angeles\",\"state\":\"California\",\"country\":\"USA\"}]"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No hotels found in the specified country"
            )
    })
    @GetMapping("/country/{country}")
    public ResponseEntity<List<HotelDTO>> findHotelByCountry(
            @Parameter(
                    description = "Name of the country to search hotels in",
                    example = "USA",
                    required = true
            )
            @PathVariable String country) {
        List<HotelDTO> hotelDTOList = hotelService.findHotelsByCountry(country);
        return ResponseEntity.ok(hotelDTOList);
    }

    /* =================== QUALITY-BASED SEARCH ENDPOINTS =================== */

    @Operation(
            summary = "Find hotels by star rating",
            description = "Retrieves all hotels with a specific star rating. Great for finding hotels that match your quality preferences",
            tags = {"Quality Search"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Hotels found with the specified star rating",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HotelDTO.class),
                            examples = @ExampleObject(
                                    name = "5-star hotels",
                                    value = "[{\"id\":7,\"name\":\"Luxury Grand Hotel\",\"address\":\"100 Luxury Lane\",\"stars\":5,\"city_name\":\"Miami\",\"state\":\"Florida\",\"country\":\"USA\"},{\"id\":8,\"name\":\"Premium Resort & Spa\",\"address\":\"200 Premium Ave\",\"stars\":5,\"city_name\":\"Orlando\",\"state\":\"Florida\",\"country\":\"USA\"}]"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid star rating (must be between 1 and 5)"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No hotels found with the specified star rating"
            )
    })
    @GetMapping("/stars/{stars}")
    public ResponseEntity<List<HotelDTO>> findHotelByStars(
            @Valid @Parameter(
                    description = "Star rating of hotels to search for (1-5 stars)",
                    example = "5",
                    required = true,
                    schema = @Schema(minimum = "1", maximum = "5")
            )
            @PathVariable Integer stars) {
        List<HotelDTO> hotelDTOList = hotelService.findHotelsByStars(stars);
        return ResponseEntity.ok(hotelDTOList);
    }

    /* =================== ADVANCED SEARCH ENDPOINTS =================== */

    @Operation(
            summary = "Advanced hotel search with multiple filters",
            description = "Powerful search functionality that combines multiple criteria including location (city, state, country) and quality (star rating). All parameters are optional and can be used independently or in combination for precise results",
            tags = {"Advanced Search"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Hotels found matching the specified criteria",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HotelDTO.class),
                            examples = @ExampleObject(
                                    name = "Filtered search results",
                                    value = "[{\"id\":9,\"name\":\"Miami Luxury Resort\",\"address\":\"500 Beach Boulevard\",\"stars\":5,\"city_name\":\"Miami\",\"state\":\"Florida\",\"country\":\"USA\"},{\"id\":10,\"name\":\"Miami Premium Hotel\",\"address\":\"600 Downtown Ave\",\"stars\":5,\"city_name\":\"Miami\",\"state\":\"Florida\",\"country\":\"USA\"}]"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid filter parameters or conflicting criteria",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Validation error",
                                    value = "{\"error\":\"Bad Request\",\"message\":\"At least one filter parameter must be provided\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No hotels found matching the specified criteria",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "No results",
                                    value = "{\"error\":\"Not Found\",\"message\":\"No hotels found with the specified filters\"}"
                            )
                    )
            )
    })
    @GetMapping("/filters")
    public ResponseEntity<List<HotelDTO>> findHotelByFilters(
            @Parameter(
                    description = "Filter by city name. Case-insensitive search",
                    example = "Miami",
                    required = false
            )
            @RequestParam(required = false) String city,

            @Parameter(
                    description = "Filter by state or province name. Case-insensitive search",
                    example = "Florida",
                    required = false
            )
            @RequestParam(required = false) String state,

            @Parameter(
                    description = "Filter by country name. Case-insensitive search",
                    example = "USA",
                    required = false
            )
            @RequestParam(required = false) String country,

            @Parameter(
                    description = "Filter by exact star rating (1-5 stars). Use this to find hotels of specific quality levels",
                    example = "5",
                    required = false,
                    schema = @Schema(minimum = "1", maximum = "5")
            )
            @Valid @RequestParam(required = false) Integer stars) {

        List<HotelDTO> hotelDTOList = hotelService.findHotelByFilters(city, state, country, stars);
        return ResponseEntity.ok(hotelDTOList);
    }
}