package com.tourismSystem.hotels.dto;
import com.tourismSystem.hotels.entities.Hotel;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelDTO {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;

    @Min(value = 1)
    @Max(value = 5)
    private Integer stars;
    private String city;
    private String state;
    private String country;

    public HotelDTO(Hotel hotel){
        id= hotel.getId();
        name= hotel.getName();
        address= hotel.getAddress();
        stars= hotel.getStars();
        city= hotel.getCity();
        state= hotel.getState();
        country= hotel.getCountry();
    }

    public Hotel toEntity(){
        Hotel hotel = new Hotel();
        hotel.setId(id);
        hotel.setName(name);
        hotel.setCity(city);
        hotel.setCountry(country);
        hotel.setState(state);
        hotel.setAddress(address);
        hotel.setStars(stars);
        return hotel;
    }
}
