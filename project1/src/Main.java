import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
public class Main {
	//input parameters
	public static int totalCustomers = 10000;
	public static double lambda = 10;
	public static double mu=8;
	
	public static void main(String[] args) {
		LinkedList<Customer> futureList = new LinkedList<>();//tracks all events
		LinkedList<Customer> delayedList = new LinkedList<>(); //only add new arrivals that arrive during server busy wait here
		int queueLength=0;
		int totalQueue=0;
		int queueCheck=0;
		//server==0 - free
		//server==1 - busy
		int server =0;

		//this keeps track of the simulation time
		double currentEventTime=0.0;
		
		//variables for calculating server idle time
		double lastEventTime = 0.0;
		double totalServerIdleTime=0.0;

		//creating output files
		BufferedWriter outputFile = null;
		try {
			outputFile = new BufferedWriter(new FileWriter("trace.txt"));
			outputFile.write("Time 		**		Future Events List		**		Delayed Events List\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//create initial event
		futureList.add(new Customer(0,generateArrivalTime(lambda),1));
		Customer current = futureList.peek();
		int customersServed =0;
		while(customersServed<10000) {
			//printing the trace
			if(customersServed<30) {
				//add events from delayed and future lists
				String delayed="";
				String future="";
				for(int j=0;j<futureList.size();j++) {
					future=future+futureList.get(j).printData();
				}
				for(int j=0;j<delayedList.size();j++) {
					delayed=delayed+delayedList.get(j).printData();
				}
				String output = "TIME:: "+currentEventTime +" ::FUTURE:: "+future+" ::DELAYED:: "+delayed;
				try {
					outputFile.write(output+"\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			//get future event
			current = futureList.remove();
			currentEventTime=current.arrivalTime;

			//this is an arrival event
			if(current.checkArrival()) {
				//server is free to handle customer
				if(server==0) {
					futureList.add(new Customer(current.customerId,currentEventTime+generateEndOfService(mu),0));
					server=1;
				}
				//server is busy, new customer gets pushed to delayed queue
				else {
					//used to calculate avg system queue length
					totalQueue+=(queueLength-1);
					queueCheck+=1;
					//event gets added to delayed list
					delayedList.add(current);
				}
				//generate next arrival
				//only generate new arrival at an arrival event
				futureList.add(new Customer(customersServed+1,currentEventTime+generateArrivalTime(lambda),1));
			}

			//this is a departure event
			else {
				queueLength--;
				if(delayedList.size()>0) {
					Customer end = delayedList.remove();
					//add end of service event to futures list
					end.finished=true;
					futureList.add(new Customer(end.customerId,end.arrivalTime+generateEndOfService(mu),0));
				}
				//server is idle
				else{
					server=0;
					//time between current arrival and last departure
					totalServerIdleTime = totalServerIdleTime + (currentEventTime-lastEventTime);
				}
			}
			lastEventTime=currentEventTime;
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
