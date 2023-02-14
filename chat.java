public class MM1QueueSimulation {
    private final static double LAMBDA = 0.1;
    private final static double MU = 0.125;

    public static void main(String[] args) {
        int numCustomers = 10000;
        int queueLength = 0;
        int serverStatus = 0;
        double nextArrival = getInterArrivalTime();
        double nextDeparture = Double.POSITIVE_INFINITY;
        double totalQueueLength = 0;
        double totalServerIdleTime = 0;
        double lastEventTime = 0;
        for (int i = 0; i < numCustomers; i++) {
            double eventTime;
            if (nextArrival < nextDeparture) {
                eventTime = nextArrival;
                queueLength++;
                totalQueueLength += queueLength + serverStatus;
                if (serverStatus == 0) {
                    serverStatus = 1;
                    nextDeparture = eventTime + getNextDepartureTime();
                    totalServerIdleTime += eventTime - lastEventTime;
                }
                nextArrival = eventTime + getInterArrivalTime();
            } else {
                eventTime = nextDeparture;
                queueLength--;
                serverStatus = 0;
                if (queueLength > 0) {
                    serverStatus = 1;
                    nextDeparture = eventTime + getNextDepartureTime();
                } else {
                    nextDeparture = Double.POSITIVE_INFINITY;
                }
            }
            lastEventTime = eventTime;
        }
        double avgQueueLength = totalQueueLength / numCustomers;
        double avgServerIdleTime = totalServerIdleTime / numCustomers;
        System.out.println("Average queue length: " + avgQueueLength);
        System.out.println("Average server idle time: " + avgServerIdleTime);
    }

    private static double getInterArrivalTime() {
        return -Math.log(1 - Math.random()) / LAMBDA;
    }

    private static double getNextDepartureTime() {
        return -Math.log(1 - Math.random()) / MU;
    }
}
//https://www.eventhelix.com/congestion-control/m-m-1/
//https://github.com/mcialini/MM1/blob/master/Controller.java
