import java.util.LinkedList;

public class Main {
	LinkedList<Event> futureEvents = new LinkedList<>();
	public static void main(String[] args) {
		int totalCustomers = 10000;
		int customersServed = 0;
		double currentTime=0.0;
		
		while(true) {
			//served total amount of customers
			if(customersServed==totalCustomers) {
				return;
			}
			//arrival object
			generateArrival();
			
			
			
			//increment time regardless
			currentTime++;
		}
	
	}
	public static Event generateArrival() {
	
	}
	public static Event generateEndOfService(int objectId) {
		
	}
	
}
