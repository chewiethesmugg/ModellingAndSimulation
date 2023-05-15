import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

class Elevator{
    private int currentFloor;
    private int currentDirection; // "up", "down", or "idle"
    private Queue<Integer> requestedFloors;
    private Queue<Passenger> passengers;
    private double time;
    public int id;

    //methods
    //constructor
    public Elevator(int id){
        this.currentFloor = 1;
        this.currentDirection = 0;
        this.time = 0.0;
        this.requestedFloors = new LinkedList<>();
        this.passengers = new LinkedList<>();
        this.id=id;
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
