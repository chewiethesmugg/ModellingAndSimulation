import java.util.LinkedList;
import java.util.Queue;

public class Main {
	//input parameters
	public static int totalCustomers = 10000;
	public static double lambda = 10;
	public static double mu=8;
	
	public static void main(String[] args) {
		LinkedList<Event> futureEvents = new LinkedList<>();//pull from here to serve events
		LinkedList<Event> delayedEvents = new LinkedList<>(); //only add new arrivals that arrive during server busy wait here
		Queue<Event> events= new LinkedList<>();
		double currentTime=0.0;
		int queueLength=0;
		//server==0 - free
		//server==1 - busy
		int server =0;
		//initial arrival time is 0
		double nextArrivalTime=0.0;
		//initial departure is infinite
		double nextDepartureTime=Double.MAX_VALUE;
		double totalServerIdle=0.0;
		
		//create initial event
		events.add(new Event(0,generateArrivalTime(lambda)));
		
		for(int i=0;i<totalCustomers;i++) {
			double currentEventTime;
			//got a new arrival
			if(nextArrivalTime<nextDepartureTime) {
				//getting the individuals events off the event queue
				Event individualEvent = events.remove();
				currentEventTime = individualEvent.arrivalTime;
				//check if server is busy
				//server is free we can handle it
				if(server==0) {
					individualEvent.endTime=currentEventTime+generateEndOfService(mu);
				}
			}
			
			
		}
		//all customers have been served
		//return information values here
	}
	
	
	
	//takes in lambda
	public static double generateArrivalTime(double l) {
		return Math.log(1 - Math.random()) /(-1*l);
	}
	//takes in mu
	public static double generateEndOfService(double m) {
		return Math.log(1 - Math.random()) / (-1*m);
	}		

}
