import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Elevator{
	private int currentFloor;
	private int currentDirection; // "up", "down", or "idle"
	private Queue<Integer> requestedFloors;
	private Queue<Passenger> passengers;
	private double time;

	//methods
	//constructor
	public Elevator(){
		this.currentFloor = 1;
		this.currentDirection = 0;
		this.time = 0.0;
		this.requestedFloors = new LinkedList<>();
		this.passengers = new LinkedList<>();
	}//constructor

	public int getCurrentFloor(){
		return currentFloor;
	}//getCurrentFloor

	public void setCurrentFloor(int currentFloor){
		this.currentFloor = currentFloor;
	}//setCurrentFloor

	public int getCurrentDirection(){
		return currentDirection;
	}//getCurrentDirection

	public void setCurrentDirection(int currentDirection){
		this.currentDirection = currentDirection;
	}//setCurrentDirection

	public Queue<Integer> getRequestedFloors() {
		return requestedFloors;
	}//getRequestedFloors

	public void addRequestedFloor(int floor) {
		this.requestedFloors.add(floor);
	}//addRequestedFloor

	public void removeRequestedFloor() {
		this.requestedFloors.poll();
	}//removeRequestedFloor

	public Queue<Passenger> getPassengers() {
		return passengers;
	}//getPassengers

	public void addPassenger(Passenger passenger) {
		this.passengers.add(passenger);
	}//addPassenger

	public void removePassenger() {
		this.passengers.poll();
	}//removePassenger

	public double move(FileWriter fw){
		double waitedTime = 0.0; // to get average later on

		try{
			// If elevator is not at floor 1, it must come down first
			if (currentFloor != 1) {
				// Set the direction to down
				currentDirection = -1;

				// Travel floor by floor
				while (currentFloor != 1) {
					// Update the time for moving to the next floor
					time += 0.5; // 30 seconds per floor in minutes

					// Update the current floor in the direction of travel
					currentFloor += currentDirection;

					// Format the time to 2 decimal places
					String formattedTime = String.format("%.2f", time);

					// Update the waitedTime for all passengers
					for (Passenger passenger : passengers) {
						passenger.setWaitedTime(passenger.getWaitedTime() + 0.5);
						//System.out.println("Passenger waited time: " + String.format("%.2f", passenger.getWaitedTime()) + " minutes.");
						fw.write("Elevator reached floor: " + currentFloor +
								" at time: " + formattedTime + " minutes, direction: " +
								(currentDirection == 1 ? "up" : "down" + " | Passenger waited time: " + String.format("%.2f", passenger.getWaitedTime()) + " minutes")+"\n");
					}

                /*System.out.println("Elevator reached floor: " + currentFloor +
                        " at time: " + formattedTime + " minutes, direction: " +
                        (currentDirection == 1 ? "up" : "down"));*/
				}

				// Wait 10 seconds at floor 1
				time += 0.1667;

				// Update the waitedTime for all passengers
				for (Passenger passenger : passengers) {
					passenger.setWaitedTime(passenger.getWaitedTime() + 0.1667);
					waitedTime = passenger.getWaitedTime();
				}
			}
			while (!requestedFloors.isEmpty()) {
				int nextFloor = requestedFloors.poll();

				// Update the direction
				currentDirection = nextFloor > currentFloor ? 1 : -1;

				// Travel floor by floor
				while (currentFloor != nextFloor) {
					// Update the time for moving to the next floor
					//time += Math.abs(nextFloor - currentFloor) * 0.5;
					time += 0.5; // 30 seconds per floor in minutes

					// Update the current floor
					//currentFloor =
					// Update the current floor in the direction of travel
					currentFloor += currentDirection;

					// Format the time to 2 decimal places
					String formattedTime = String.format("%.2f", time);

					fw.write("Elevator reached floor: " + currentFloor +
							" at time: " + formattedTime + " minutes, direction: " +
							(currentDirection == 1 ? "up" : "down")+"\n");

					// Let passengers exit and enter
					time += 0.1667; // 10 seconds in minutes

					// Remove the passengers who have reached their destination floor
					passengers.removeIf(p -> p.getDestinationFloor() == currentFloor);
				}
			}

			// The elevator is now stationary
			currentDirection = 0;
		}catch(IOException e){
			System.out.println(e);
		}


		return waitedTime;
	}//move

	public double getTime(){
		return time;
	}//getTime
}//Elevator

class Passenger{
	private int destinationFloor;
	private double waitedTime;

	//methods
	//constructor
	public Passenger(int destinationFloor){
		this.destinationFloor = destinationFloor;
	}//constructor

	public double getWaitedTime(){
		return waitedTime;
	}//getWaitedTime

	public void setWaitedTime(double waitedTime){
		this.waitedTime = waitedTime;
	}//setWaitedTime

	public int getDestinationFloor(){
		return destinationFloor;
	}//getDestinationFloor

	public void setDestinationFloor(int destinationFloor){
		this.destinationFloor = destinationFloor;
	}
}//Person

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

			//to track waitedTime for every passenger
			List<Double> waitedTimes = new ArrayList<>();

			//Create a list of elevators
			List<Elevator> elevators = new ArrayList<>();
			for(int i = 0; i< elevatorCount; i++){
				elevators.add(new Elevator());
			}

			int nextElevator = 0; // Index of the next elevator to receive a passenger

			// Passenger Counter
			int passengerCounter = 0;
			while(currentTime < endTime){
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

				// Create a new passenger with the chosen destination floor
				Passenger passenger = new Passenger(destinationFloor);

				// Here we would create a new passenger and add them to the queue
				// For now, we'll just print the arrival time
				if (currentTime <= endTime) {
					passengerCounter++;
					Elevator elevator = elevators.get(nextElevator);
					elevator.addPassenger(passenger);
					elevator.addRequestedFloor(destinationFloor);
					// Format the time to 2 decimal places
					String formattedTime = String.format("%.2f", currentTime);
					fw.write("Passenger " + passengerCounter + " arrived at: " + formattedTime +
							" minutes, Destination Floor: " + passenger.getDestinationFloor() +
							", Elevator: " + (nextElevator + 1) +"\n");
					double waitedTime = elevator.move(fw);
					waitedTimes.add(waitedTime);
				}
				fw.write("\n");
				// Update the index of the next elevator to receive a passenger
				nextElevator = (nextElevator + 1) % elevatorCount;
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
