public class Customer {
	
	public double arrivalTime;
	public double endTime;
	public long customerId;
	public boolean finished; //used to keep track if an event has already had its turn on the future events list
	
	public Customer(int id){
		this.arrivalTime=-0.0;
		this.endTime=-0.0;
		this.customerId=(long)id;
		this.finished=false;
	}
	public Customer(int id,double arr) {
		this.arrivalTime=arr;
		this.endTime=-0.0;
		this.customerId=(long)id;
		this.finished=false;
	}
	
	public void printData() {
		//printing arrival event
		if(this.checkArrival()) {
			System.out.print(this.customerId+" : "+this.arrivalTime+" A,");
		}
		//printing end of service
		else{
			System.out.print(this.customerId+" : "+this.endTime+" D,");
		}
	}
	
	//checks if the event is an arrival or not
	//true if it is, false if not
	public boolean checkArrival() {
		return this.endTime==-0.0;
	}

}
