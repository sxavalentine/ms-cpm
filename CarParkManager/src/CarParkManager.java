import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}