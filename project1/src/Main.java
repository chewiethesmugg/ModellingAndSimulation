import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
		int queueLength=0;
		int totalQueue=0;
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
		futureEvents.add(new Customer(0,generateArrivalTime(lambda),1));
		
		//creating output files
		BufferedWriter outputFile = null;
		try {
			outputFile = new BufferedWriter(new FileWriter("trace.txt"));
			outputFile.write("Time 		**		Future Events List		**		Delayed Events List\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Customer current = futureEvents.peek();
		System.out.println("first: "+current.printData());
		Boolean first = true;
		int customersServed =0;
		while(customersServed<totalCustomers) {
			//print the trace
			if(customersServed<30) {
				//add events from delayed and future lists
				String delayed="";
				String future="";
				for(int j=0;j<futureEvents.size();j++) {
					future=future+futureEvents.get(j).printData();
				}
				for(int j=0;j<delayedEvents.size();j++) {
					delayed=delayed+delayedEvents.get(j).printData();
				}
				String output = "Time "+currentEventTime +" ** "+future+" ** "+delayed;
				try {
					outputFile.write(output+"\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			//check delayed events list first
			if(!delayedEvents.isEmpty()){
				//get future event
				current = delayedEvents.remove();
			}else{
				current = futureEvents.remove();
			}

			//this is an arrival event
			if(current.checkArrival()) {
				queueLength++;
				currentEventTime=current.arrivalTime;

				//generate next arrival
				futureEvents.add(new Customer(customersServed+1,currentEventTime+generateArrivalTime(lambda),1));
				System.out.println("generated next arrival: "+customersServed+1);
				totalQueue+=(queueLength+server);
				//server is free to handle customer
				if(server==0) {
					System.out.println("servicing: "+current.customerId);
					current.finished=true;
					totalServerIdleTime = totalServerIdleTime + (currentEventTime-lastEventTime);
					futureEvents.add(new Customer(current.customerId,currentEventTime+generateEndOfService(mu),0));
					//set server to busy
					server=1;
				}
				//server is busy, customer gets pushed to delayed queue
				else {
					//event gets added to delayed list
					//set flag to prevent infinte loop of events in future events list
					current.finished=false;
					//check if event is not a previous arrival
					if(current.finished){
						delayedEvents.add(current);
					}

					//used to calculate avg system queue length
				}
			}
			//this is a departure event
			else {
				currentEventTime=current.arrivalTime;
				System.out.println("got a departure");
				//getting idle time
				//time between current arrival and last departure
				totalServerIdleTime = totalServerIdleTime + (currentEventTime-lastEventTime);
				System.out.println("idle time: "+totalServerIdleTime);
				queueLength--;
				//flag server as free
				server=0;
				//check if anything on delayed list
				//get the current event from delayed list
				if(queueLength>0) {
					//add this to future events list
					futureEvents.add(new Customer(current.customerId,currentEventTime+generateEndOfService(mu),0));
				}
			}
			lastEventTime=currentEventTime;
			System.out.println("last event time: "+lastEventTime);
			//adding items from delayed list
			/*
			for(int i=0;i<delayedEvents.size();i++){
				//if arrived before current time
				if(!delayedEvents.get(i).checkArrival() && delayedEvents.get(i).arrivalTime<currentEventTime){
					System.out.println("Adding: "+delayedEvents.get(i).customerId+" to future events");
					futureEvents.add(delayedEvents.remove(i));
				}
			}

			while(!delayedEvents.isEmpty() && delayedEvents.peek().arrivalTime<currentEventTime){
				futureEvents.add(delayedEvents.poll());
			}
			*/
			customersServed++;
		}


		//all customers have been served
		//return information values here
		double avgQueueLength = totalQueue/totalCustomers;
		double avgServerIdle = totalServerIdleTime/currentEventTime;
		System.out.println("Average Queue Length: "+avgQueueLength);
		System.out.println("Average Server idle: "+avgServerIdle);
		try {
			outputFile.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	
	//takes in lambda
	public static double generateArrivalTime(double l) {
		return Math.log(1 - Math.random()) * (-1*l);
	}
	//takes in mu
	public static double generateEndOfService(double m) {
		return Math.log(1 - Math.random()) * (-1*m);
	}		

}
