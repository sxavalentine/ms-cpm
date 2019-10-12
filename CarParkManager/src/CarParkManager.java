import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class CarParkManager {

	private final Map<Integer, ParkedCar> parkedCars; // In a database this would be a table with 3 columns: ticket_number(PK), plate and parking_slot_number.
	private final List<Integer> slotsAvailable; // In a database, this would be a table with a single column, where each row represents a parking slot.
	private int ticketCounter; // In a database this would be an auto-generate, auto-increment value.
	
	// Inner class that represent a record in the table parkedCars
	private class ParkedCar {
		
		private String plate;
		private Integer ticketNumber;
		private Integer parkingSlot;
		
		public String getPlate() {
			return plate;
		}
		public void setPlate(String plate) {
			this.plate = plate;
		}
		public Integer getTicketNumber() {
			return ticketNumber;
		}
		public void setTicketNumber(Integer ticketNumber) {
			this.ticketNumber = ticketNumber;
		}
		public Integer getParkingSlot() {
			return parkingSlot;
		}
		public void setParkingSlot(Integer parkingSlot) {
			this.parkingSlot = parkingSlot;
		}
	}
	
	public CarParkManager() {
		parkedCars = new HashMap<Integer, ParkedCar>();
		slotsAvailable = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		ticketCounter = 5000;
	}
	
	public void park(String plate) {
		if (parkedCars.size() < 10) {// If the car park is not full
			
			List<Integer> slotsTaken = new ArrayList<>();
			List<String> registeredPlates = new ArrayList<>();
			for (Entry<Integer, ParkedCar> entry: parkedCars.entrySet()) {
				slotsTaken.add(entry.getValue().getParkingSlot());
				registeredPlates.add(entry.getValue().getPlate());
			}
			
			if (!registeredPlates.contains(plate)) {// If the license plate is not already present in the car park
				ParkedCar car = new ParkedCar();
				
				// Setting as parking slot the very first one in the list slotsAvailable that is not included in the list slotsTaken
				car.setParkingSlot(slotsAvailable
						.stream()
						.filter(x -> !slotsTaken.contains(x))
						.findFirst()
						.orElse(null));
				car.setPlate(plate);
				car.setTicketNumber(ticketCounter);
				
				parkedCars.put(ticketCounter, car);
				ticketCounter++;
			}
			else {
				System.out.println("Invalid command: plate " + plate + " is already present in the car park");
			}
		}
		else {
			System.out.println("Invalid command: car park is full");
		}
	}
	
	public void unpark(Integer ticketNumber) {
		// If the ticket number is actually registered in the map
		if (parkedCars.containsKey(ticketNumber)) {
			// Removing the unparked car from the map
			parkedCars.remove(ticketNumber);
		}
		else {
			System.out.println("Invalid command: ticket number " + ticketNumber + " not present in the car park");
		}
	}
	
	public void compact() {
		// Getting the list of parked cars from the map
		List<ParkedCar> sortedParkedCars = new ArrayList<>();
		for (Entry<Integer, ParkedCar> entry: parkedCars.entrySet()) {
			sortedParkedCars.add(entry.getValue());
		}

		// Sorting the cars based on their parking slot using lambda expression
		sortedParkedCars.sort((ParkedCar o1, ParkedCar o2) -> o1.getParkingSlot().compareTo(o2.getParkingSlot()));
		
		// Re-set the parking slot number of each car
		int parkingSlot = 1;
		for (ParkedCar car : sortedParkedCars) {
			car.setParkingSlot(parkingSlot);
			parkingSlot++;
		}
	}
	
	// For this method, I decided to strictly follow the example, so I will only accept as valid commands the ones starting with lower case letter
	public String processInputString(String stdin) {
		String[] commands = stdin.split(",");
		
		for (int i=0; i < commands.length; i++) {
			String command = commands[i].trim();
			
			// park
			if (command.startsWith("p")) {
				park(command.substring(1));
			}
			// unpark
			else if (command.startsWith("u")) {
				try {
					int ticketNumber = Integer.parseInt(command.substring(1));
					unpark(ticketNumber);
				}
				catch (Exception e) {
					System.out.println("Invalid command: ticket number " + command.substring(1) + " is not numeric");
				}
			}
			// compact
			else if (command.equals("c")) {
				compact();
			}
		}
		
		String output = "";
		
		// Getting the list of parked cars from the map
		List<ParkedCar> sortedParkedCars = new ArrayList<>();
		for (Entry<Integer, ParkedCar> entry: parkedCars.entrySet()) {
			sortedParkedCars.add(entry.getValue());
		}

		// Sorting the cars based on their parking slot using lambda expression
		sortedParkedCars.sort((ParkedCar o1, ParkedCar o2) -> o1.getParkingSlot().compareTo(o2.getParkingSlot()));
		
		// Completing the list sortedParkedCars with the eventual free parking slots
		while (sortedParkedCars.size() < 10) {
			sortedParkedCars.add(null);
		}
		
		// Composing the output string
		for (ParkedCar car : sortedParkedCars) {
			output += car != null ? car.getPlate() + "," : ",";
		}
		
		// Removing the final comma after the last parking slot and returning the output
		return output.substring(0, output.length()-1);
	}
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		String stdin = scanner.next();
		scanner.close();
		
		CarParkManager cpm = new CarParkManager();
		System.out.println(cpm.processInputString(stdin));	
//		System.out.println(cpm.processInputString("pABC,pXYZ,pEFG,u5000,c")); // Input string given in the example
	}
}