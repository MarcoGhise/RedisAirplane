package it.blog.springboot.redis.redisairplane;

import java.util.Scanner;
import it.blog.springboot.redis.redisairplane.component.FlightComponent;
import it.blog.springboot.redis.redisairplane.component.PassengerComponent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:redis-config.xml")
public class RedisairplaneApplication implements CommandLineRunner {

	@Autowired
	FlightComponent flight;
	
	@Autowired
	PassengerComponent passenger;
	
	public static void main(String[] args) {
		SpringApplication.run(RedisairplaneApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		String input = "-1";
		Scanner scanner = new Scanner(System.in);

		while (!input.equals("0")) {
			System.out.println("1 - Insert new Flight;");
			System.out.println("2 - Insert new Passenger;");
			System.out.println("3 - Looking for Flight's passengers");
			System.out.println("4 - Looking for passengers");
			System.out.println("5 - Delete passengers");
			
			System.out.println("0 - Exit");
			System.out.println("");
			System.out.print("Enter something: ");

			input = scanner.nextLine();

			switch (input) {
			case "1":
				flight.insertNewFlight(scanner);
				break;
			case "2":
				passenger.insertNewPassenger(scanner);
				break;
			case "3":
				flight.printPassengerList(scanner);
				break;
			case "4":
				passenger.printPassengerDetail(scanner);
				break;
			case "5":
				passenger.deletePassenger(scanner);
				break;
			}
		}

		scanner.close();
		System.exit(0);
	}
}
