package homesafe.digital;

/**
 * Power - represents the power supply for the Home Safe
 * Designed to run in its own thread of execution as long as its batteryPercentage > 20
 */
public class Power implements Runnable{
    private int batteryPercentage;

    /**
     * Power - constructor for the Power class
     * @param batteryPercentage - battery power left
     */
    public Power(int batteryPercentage){
        this.batteryPercentage = batteryPercentage;
    }

    @Override
    public void run() {
        while(batteryPercentage > 20){

        }
    }
}
