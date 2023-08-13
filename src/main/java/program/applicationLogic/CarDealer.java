package program.applicationLogic;

import program.objects.Car;
import program.logFile.Log;
import program.warehouse.CarWarehouse;

import java.util.ArrayList;

public class CarDealer extends Thread {
    int id;
    static int lastId = 0;
    boolean isTerminated = false;
    long threadWaitTime;
    private CarWarehouse carWarehouse;
    ArrayList<Car> cars = new ArrayList<>();


    public CarDealer(CarWarehouse carWarehouse,
        long threadWaitTime) {
        id = getFreeId();
        this.threadWaitTime = threadWaitTime;
        this.carWarehouse = carWarehouse;
        Log.getObjLog().writeLog("Create car dealer #" + id);
    }

    public void run() {
        while (!isTerminated) {
            synchronized (Thread.currentThread()) {
                try {
                    while (carWarehouse.isEmpty()) {
                    }

                    synchronized (carWarehouse) {
                        if (!carWarehouse.isEmpty())
                        {
                            Log.getObjLog().writeLog("Dealer received purchased a new car ");
                            cars.add(carWarehouse.get());
                        }
                    }
                    Thread.currentThread().wait(threadWaitTime);

                } catch (InterruptedException e) {
                    isTerminated = true;
                }
            }
        }
    }

    public void terminate() {
        isTerminated = true;
        Thread.currentThread().interrupt();
    }

    synchronized static int getFreeId() {
        return ++lastId;
    }

    void setThreadWaitTime(long milliseconds) {
        this.threadWaitTime = milliseconds;
    }
}
