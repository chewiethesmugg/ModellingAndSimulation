public class Event {
	
	double arrivalTime;
	double endTime;
	long eventId;
	
	public Event(int id){
		this.arrivalTime=-0.0;
		this.endTime=-0.0;
		this.eventId=(long)id;
	}
	public Event(int id,double arr) {
		this.arrivalTime=arr;
		this.endTime=-0.0;
		this.eventId=(long)id;
	}
	
	public long getEventId() {
		return eventId;
	}
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	
	public void printData() {
		//printing arrival event
		if(this.endTime==-0.0) {
			System.out.print(this.eventId+" : "+this.arrivalTime+" A,");
		}
		//printing end of service
		else{
			System.out.print(this.eventId+" : "+this.endTime+" D,");
		}
	}

}
