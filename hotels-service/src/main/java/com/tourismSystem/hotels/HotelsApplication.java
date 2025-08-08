package com.tourismSystem.hotels;

import com.tourismSystem.hotels.entities.Hotel;
import com.tourismSystem.hotels.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@SpringBootApplication
@EnableDiscoveryClient
public class HotelsApplication implements CommandLineRunner {

	@Autowired
	private HotelRepository hotelRepository;
	public static void main(String[] args) {
		SpringApplication.run(HotelsApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		if (hotelRepository.count() == 0) {
			System.out.println("Loading hotel data from CSV...");
			loadHotelsFromCSV();
			System.out.println("Hotel data successfully uploaded!");
		} else {
			System.out.println("Hotel data already exists, skipping load.");
		}
	}
	private void loadHotelsFromCSV() throws Exception {
		InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream("hotelsBD.csv");

		if (inputStream == null) {
			System.err.println("The file hotelsBD.csv could not be found.");
			return;
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line;

		// Skip header
		reader.readLine();

		while ((line = reader.readLine()) != null) {
			String[] data = line.split(",");

			// Create Entity Hotel based on CSV: id,name,address,stars,city,state,country
			Hotel hotel = new Hotel();
			//  Self-generated ID
			hotel.setName(data[1].trim());        // name
			hotel.setAddress(data[2].trim());     // address
			hotel.setStars(Integer.parseInt(data[3].trim())); // stars
			hotel.setCity(data[4].trim());        // city
			hotel.setState(data[5].trim());       // state
			hotel.setCountry(data[6].trim());     // country

			hotelRepository.save(hotel);
		}

		reader.close();
	}
}
