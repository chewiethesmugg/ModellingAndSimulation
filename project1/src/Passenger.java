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
