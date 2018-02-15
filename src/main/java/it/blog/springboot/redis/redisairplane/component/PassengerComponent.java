package it.blog.springboot.redis.redisairplane.component;

import it.blog.springboot.redis.redisairplane.bean.Flight;
import it.blog.springboot.redis.redisairplane.bean.Passenger;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class PassengerComponent {

	@Autowired
	@Qualifier("redisTemplateString")
	StringRedisTemplate redisTemplateString;

	@Autowired
	@Qualifier("redisTemplateFactory")
	RedisTemplate<String, Flight> redisTemplateFlight;

	@Autowired
	@Qualifier("redisTemplateFactory")
	RedisTemplate<String, Passenger> redisTemplatePassenger;
	
	public void insertNewPassenger(Scanner scanner) {

		Map<Object, Object> entries = redisTemplateFlight.opsForHash().entries(
				"Flight");

		System.out.println("Flight inserted:");

		for (Map.Entry<Object, Object> entry : entries.entrySet()) {
			System.out.println(entry.getValue());
		}

		System.out.println("------");

		Passenger passenger = new Passenger();

		System.out.print("Insert the passenger's flight: ");

		String input = scanner.nextLine();

		String key = "fly:" + input;

		// Flight details
		Flight storedFlight = (Flight) redisTemplateFlight.opsForHash().get(
				"Flight", key);

		if (storedFlight != null) {
			passenger.setFlightCode(input);

			System.out.print("Insert ticket number: ");

			input = scanner.nextLine();

			passenger.setCode(input);

			System.out.print("Insert passenger's name: ");

			input = scanner.nextLine();

			passenger.setName(input);

			System.out.print("Insert passenger's seat: ");

			input = scanner.nextLine();

			passenger.setSeat(input);

			key = "passenger:" + passenger.getCode();
			redisTemplatePassenger.opsForHash()
					.put("Passenger", key, passenger);

			redisTemplateString
					.opsForValue()
					.set("passenger:name:"
							+ normalizePassengerNameForKey(passenger.getName()),
							passenger.getCode());

			// Passenger List
			List<String> passengerList = storedFlight.getPassengers();

			passengerList.add(passenger.getCode());

			key = "fly:" + passenger.getFlightCode();

			redisTemplateFlight.opsForHash().put("Flight", key, storedFlight);

		} else {
			System.out.println("Flight not found - " + input);
		}

	}
	
	public void printPassengerDetail(Scanner scanner) {
		
		System.out.print("Insert the Passenger Name: ");

		String input = scanner.nextLine();

		// Looking for a passenger by his name
		String passengerTicket = redisTemplateString.opsForValue().get(
				"passenger:name:" + normalizePassengerNameForKey(input));

		String keyPassenger = "passenger:" + passengerTicket;
		Passenger passenger = (Passenger) redisTemplateFlight.opsForHash().get(
				"Passenger", keyPassenger);

		System.out.println("------");		
		System.out.println(passenger.toString());	
		System.out.println("");		
	}

	public void deletePassenger(Scanner scanner)
	{
		System.out.print("Insert the Passenger Name: ");

		String input = scanner.nextLine();

		// Looking for a passenger by his name
		String passengerTicket = redisTemplateString.opsForValue().get(
				"passenger:name:" + normalizePassengerNameForKey(input));
		
		redisTemplateString.delete("passenger:name:" + normalizePassengerNameForKey(input));

		String keyPassenger = "passenger:" + passengerTicket;
		Passenger passenger = (Passenger) redisTemplateFlight.opsForHash().get(
				"Passenger", keyPassenger);
		
		redisTemplatePassenger.opsForHash().delete("Passenger", keyPassenger);

		String key = "fly:" + passenger.getFlightCode();
		
		Flight storedFlight = (Flight) redisTemplateFlight.opsForHash().get(
				"Flight", key);
		
		List<String> passengerList = storedFlight.getPassengers();

		passengerList.remove(passenger.getCode());

		redisTemplateFlight.opsForHash().put("Flight", key, storedFlight);
		
		System.out.println("------");					
		System.out.println("");		
	}
	
	private String normalizePassengerNameForKey(String name) {
		String key = name.toLowerCase();
		key = key.replaceAll("\\s", "_");

		return key;
	}
	
}
