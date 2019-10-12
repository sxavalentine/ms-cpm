import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CarParkManager {

	private final Map<Integer, ParkedCar> parkedCars;
	private final List<Integer> slotsAvailable;
	private int ticketCounter;
	
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
}