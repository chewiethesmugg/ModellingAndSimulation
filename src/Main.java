import java.util.LinkedList;
import java.util.Queue;
public class Main {
	//input parameters
	public static int totalCustomers = 10000;
	public static double lambda = 10;
	public static double mu=8;
	
	public static void main(String[] args) {
		LinkedList<Customer> futureEvents = new LinkedList<>();//tracks all events
		LinkedList<Customer> delayedEvents = new LinkedList<>(); //only add new arrivals that arrive during server busy wait here
		Queue<Customer> events= new LinkedList<>();
		double currentTime=0.0;
		int queueLength=0;
		//server==0 - free
		//server==1 - busy
		int server =0;
		//initial arrival time is 0
		double nextArrivalTime=0.0;
		//initial departure is infinite
		double nextDepartureTime=Double.MAX_VALUE;
		double currentEventTime=0.0;
		
		//variables for calculating server idle time
		double lastEventTime = 0.0;
		double totalServerIdleTime=0.0;
		
		//create initial event
		futureEvents.add(new Customer(0,generateArrivalTime(lambda)));
		
		//creating output files
		for(int i=0;i<totalCustomers;i++) {
			//print the trace
			if(i<30) {
				//add events from delayed and future lists
				String delayed="";
				String future="";
				for(int j=0;j<futureEvents.size();j++) {
					future+=futureEvents.get(i).
				}
				String output = currentEventTime +" : "+
			}
			
			
			
			Customer current = null;
			//check if anything on delayed list
			//get the current event from delayed list
			if(delayedEvents.size()>0) {
				current = delayedEvents.remove();
			}
			//the delayed list is empty, get from futureEvents
			//event is not a delayed list item  
			if(!futureEvents.peek().finished && !futureEvents.peek().checkArrival()) {
				current = futureEvents.remove();
			}
			
			//this is an arrival event
			if(current.arrivalTime<currentEventTime) {
				//update the current time to the arrival time 
				currentEventTime = current.arrivalTime;
				//generate next arrival
				futureEvents.add(new Customer(i+1,currentEventTime+generateArrivalTime(lambda)));
				//check if server is busy
				//server is free to handle customer
				if(server==0) {
					current.endTime=currentEventTime+generateEndOfService(mu);
					//add the end of service event to the future events queue
					futureEvents.add(current);
					//set server to busy
					server=1;
					totalServerIdleTime = totalServerIdleTime + (currentEventTime-lastEventTime);
			
				}
				//server is busy, customer gets pushed to delayed queue
				else {
					//event gets added to delayed list
					//set flag to prevent infinte loop of events in future events list
					current.finished=false;
					delayedEvents.add(current);
					//used to calculate avg system queue length 
					queueLength+=1;
				}
			}
			//this is a departure event
			else {
				currentEventTime=current.endTime;
				queueLength--;
				//flag server as free
				server=0;
			}
		}
		//all customers have been served
		//return information values here
		double avgQueueLength = queueLength/totalCustomers;
		double avgServerIdle = totalServerIdleTime/totalCustomers;
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
