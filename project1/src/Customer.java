public class Customer {
	//1:arrival
	//0:departure
	public int type;
	public double arrivalTime;
	public long customerId;
	public boolean finished; //used to keep track if an event has already had its turn on the future events list
	
	public Customer(int id){
		this.type=-1;
		this.arrivalTime=-0.0;
		this.customerId=(long)id;
		this.finished=false;
	}
	public Customer(long id,double arr,int t) {
		this.type=t;
		this.arrivalTime=arr;
		this.customerId=(long)id;
		this.finished=false;
	}
	
	public String printData() {
		//printing arrival event
		if(this.checkArrival()) {
			return "|ID "+this.customerId+" ARR : "+this.arrivalTime+"|";
		}
		//printing end of service
		else{
			return "|ID "+this.customerId+" DEP : "+this.arrivalTime+"| ";
		}
	}
	
	//checks if the event is an arrival or not
	//true if it is, false if not
	public boolean checkArrival() {
		return this.type==1;
	}

}
