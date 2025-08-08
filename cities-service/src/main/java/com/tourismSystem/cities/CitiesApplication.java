package com.tourismSystem.cities;

import com.tourismSystem.cities.entities.City;
import com.tourismSystem.cities.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients

public class CitiesApplication implements CommandLineRunner {

	@Autowired
	private CityRepository cityRepository;

    public static void main(String[] args) {
		SpringApplication.run(CitiesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(cityRepository.count()==0){
			System.out.println("Loading city data from file CSV...");
			loadCitiesFromCSV();
			System.out.println("City data successfully uploaded.");
		}else {
			System.out.println("City data already exists, skipping load.");
		}
	}

	private void loadCitiesFromCSV() throws Exception {
		InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream("citiesBD.csv");

		if (inputStream == null) {
			System.err.println("The citiesBD file could not be found..csv");
			return;
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line;

		// Skip header (first line)
		reader.readLine();

		while ((line = reader.readLine()) != null) {
			String[] data = line.split(",");

			// Create City entity based on CSV: id,name,state,country
			City city = new City();
			// ID that it is self-generated
			city.setName(data[1].trim());      // name
			city.setState(data[2].trim());     // state
			city.setCountry(data[3].trim());   // country

			cityRepository.save(city);
		}

		reader.close();
	}
}
