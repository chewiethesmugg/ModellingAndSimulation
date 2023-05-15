import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;



public class ElevatorSimulation {
	public static void main(String[] args) {
		//File
		File output = new File("src/output.txt");

		try{
			//FileWriters
			FileWriter fw = new FileWriter(output);


			double lamda = 1.0/3.0; // arrival rate (passengers per minute)
			double currentTime = 0.0;
			double endTime = 100.0; //Simulation end time (minutes)
			int elevatorCount = 5;

			double[][] floorMatrix = new double[10][10];

			//to track waitedTime for every passenger
			List<Double> waitedTimes = new ArrayList<>();

			//Create a list of elevators
			List<Elevator> elevators = new ArrayList<>();
			for(int i = 0; i< elevatorCount; i++){
				elevators.add(new Elevator(i));
			}

			//int nextElevator = 0; // Index of the next elevator to receive a passenger

			// Passenger Counter
			int passengerCounter = 0;
			while(currentTime < endTime){
				/*
				//Generate the next interarrival time
				double interarrivalTime = -Math.log(1.0 - Math.random()) / lamda;

				//Update the current time
				currentTime += interarrivalTime;

				// Generate a random number to select a floor
				double randomFloor = Math.random();

				// Choose the destination floor based on the distribution
				int destinationFloor;
				if (randomFloor < 0.2) {
					destinationFloor = 7;
				} else if (randomFloor < 0.3) {
					destinationFloor = 2;
				} else if (randomFloor < 0.4) {
					destinationFloor = 3;
				} else if (randomFloor < 0.5) {
					destinationFloor = 4;
				} else if (randomFloor < 0.6) {
					destinationFloor = 5;
				} else if (randomFloor < 0.7) {
					destinationFloor = 6;
				} else if (randomFloor < 0.8) {
					destinationFloor = 8;
				} else if (randomFloor < 0.9) {
					destinationFloor = 9;
				} else {
					destinationFloor = 10;
				}
	*/
				//generate passengers for all elevators here

				for(int i=0;i<5;i++){
					//Generate the next interarrival time
					double interarrivalTime = -Math.log(1.0 - Math.random()) / lamda;

					//Update the current time
					currentTime += interarrivalTime;

					// Generate a random number to select a floor
					double randomFloor = Math.random();

					// Choose the destination floor based on the distribution
					int destinationFloor;
					if (randomFloor < 0.2) {
						destinationFloor = 7;
					} else if (randomFloor < 0.3) {
						destinationFloor = 2;
					} else if (randomFloor < 0.4) {
						destinationFloor = 3;
					} else if (randomFloor < 0.5) {
						destinationFloor = 4;
					} else if (randomFloor < 0.6) {
						destinationFloor = 5;
					} else if (randomFloor < 0.7) {
						destinationFloor = 6;
					} else if (randomFloor < 0.8) {
						destinationFloor = 8;
					} else if (randomFloor < 0.9) {
						destinationFloor = 9;
					} else {
						destinationFloor = 10;
					}

					//get a random elevator to be assigned passengers
					Random rand = new Random();
					int nextElevator = rand.nextInt(5);

					// Create a new passenger with the chosen destination floor
					Passenger passenger = new Passenger(destinationFloor);

					// Here we would create a new passenger and add them to the queue
					// For now, we'll just print the arrival time
					if (currentTime <= endTime) {
						passengerCounter++;
						//finding elevator
						Elevator elevator = null;
						for(Elevator el : elevators){
							if(el.id==nextElevator){
								elevator = el;
								break;
							}
						}
						elevator.addPassenger(passenger);
						elevator.addRequestedFloor(destinationFloor);
						// Format the time to 2 decimal places


					}
				}

				//printing info for each elevator
				for(Elevator el:elevators){
					String formattedTime = String.format("%.2f", currentTime);
					fw.write("Passenger " + passengerCounter + " arrived at: " + formattedTime +
							" minutes, Destination Floor: " + el.getPassengers().peek().getDestinationFloor() +
							", Elevator: " + el.id +"\n");
				}

				//moving the elevators
				for(Elevator el:elevators){
					//moving+updating elevator time
					double waitedTime = el.move(fw);
					waitedTimes.add(waitedTime);
				}

				fw.write("\n");
				// Update the index of the next elevator to receive a passenger
				//nextElevator = (nextElevator + 1) % elevatorCount;
			}

			//Find average of all waited Times
			double totalWaitedTime = 0.0;
			double avgWaitedTime = 0.0;
			for(double waitedTime: waitedTimes){
				totalWaitedTime += waitedTime;
			}
			avgWaitedTime = totalWaitedTime/waitedTimes.size();
			fw.write("The average waited time is: " + String.format("%.2f",avgWaitedTime) + "\n");

			//close FileWriter
			fw.close();
		}catch(IOException e){
			System.out.println(e);
		}

	}//main class
}//ElevatorSimulation Class
