package it.blog.springboot.redis.redisairplane.component;

import it.blog.springboot.redis.redisairplane.bean.Flight;
import it.blog.springboot.redis.redisairplane.bean.Passenger;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class FlightComponent {

	@Autowired
	@Qualifier("redisTemplateString")
	StringRedisTemplate redisTemplateString;

	@Autowired
	@Qualifier("redisTemplateFactory")
	RedisTemplate<String, Flight> redisTemplateFlight;

	@Autowired
	@Qualifier("redisTemplateFactory")
	RedisTemplate<String, Passenger> redisTemplatePassenger;
	
	public void insertNewFlight(Scanner scanner) {

		Flight flight = new Flight();

		System.out.print("Insert the flight Code: ");

		String input = scanner.nextLine();

		flight.setCode(input);

		System.out.print("Insert the departure: ");

		input = scanner.nextLine();

		flight.setFrom(input);

		System.out.print("Insert the destination: ");

		input = scanner.nextLine();

		flight.setTo(input);

		System.out.print("Insert the gate: ");

		input = scanner.nextLine();

		flight.setGate(input);

		System.out
				.print("Insert the departure time in format (2017-03-24T14:47:00.000Z) : ");

		input = scanner.nextLine();

		flight.setDepartureTime(input);

		flight.setPassengers(new ArrayList<String>());

		String key = "fly:" + flight.getCode();

		redisTemplateFlight.opsForHash().put("Flight", key, flight);

	}
	
	public void printPassengerList(Scanner scanner) {
		Map<Object, Object> entries = redisTemplateFlight.opsForHash().entries(
				"Flight");

		System.out.println("Flight inserted:");

		for (Map.Entry<Object, Object> entry : entries.entrySet()) {
			System.out.println(((Flight) entry.getValue()).getCode());
		}

		System.out.print("Insert the Flight Number: ");

		String input = scanner.nextLine();

		String key = "fly:" + input;

		// Flight details
		Flight storedFlight = (Flight) redisTemplateFlight.opsForHash().get(
				"Flight", key);

		if (storedFlight != null) {
			
			System.out.println(storedFlight.toString());

			System.out.println("Passengers List:");
			
			// Passenger List for this flight
			for (String passengerCode : storedFlight.getPassengers()) {
				String keyPassenger = "passenger:" + passengerCode;
				Passenger passenger = (Passenger) redisTemplateFlight
						.opsForHash().get("Passenger", keyPassenger);

				System.out.println(passenger.toString());
			}
		}
		System.out.println("");
	}	
}
