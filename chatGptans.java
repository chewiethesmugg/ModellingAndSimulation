import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class MM1Queue {
    private final int MAX_ARRIVALS = 10000;
    private final int MAX_SERVICE_TIME = 10;
    private final int MIN_SERVICE_TIME = 1;

    private Queue<Integer> queue;
    private int totalArrivals;
    private int totalDepartures;
    private int totalWaitingTime;
    private int currentTime;
    private int serviceTime;
    private Random random;

    public MM1Queue() {
        queue = new LinkedList<>();
        totalArrivals = 0;
        totalDepartures = 0;
        totalWaitingTime = 0;
        currentTime = 0;
        serviceTime = 0;
        random = new Random();
    }

    public void simulate() {
        while (totalArrivals < MAX_ARRIVALS) {
            if (newArrival()) {
                totalArrivals++;
                if (queue.isEmpty()) {
                    serviceTime = generateServiceTime();
                } else {
                    queue.offer(currentTime);
                }
            }
            if (serviceTime == currentTime && !queue.isEmpty()) {
                totalWaitingTime += currentTime - queue.poll();
                totalDepartures++;
                serviceTime = generateServiceTime();
            }
            currentTime++;
        }
    }

    private boolean newArrival() {
        return random.nextInt(MAX_SERVICE_TIME + MIN_SERVICE_TIME) == 0;
    }

    private int generateServiceTime() {
        return currentTime + random.nextInt(MAX_SERVICE_TIME) + MIN_SERVICE_TIME;
    }

    public int getAverageWaitingTime() {
        return totalWaitingTime / totalDepartures;
    }
}