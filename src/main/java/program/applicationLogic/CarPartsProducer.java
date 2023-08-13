package program.applicationLogic;

import program.factories.CarPartFactory;
import program.warehouse.ParticleWarehouse;

public class CarPartsProducer extends Thread {
    int numberOfGeneratedParts = 0;
    long threadWaitTime;
    boolean isTerminated = false;
    private ParticleWarehouse warehouse;
    CarPartFactory factory;

    public CarPartsProducer(ParticleWarehouse warehouse, CarPartFactory factory, long initialWaitTime) {
        this.warehouse = warehouse;
        this.factory = factory;
        threadWaitTime = initialWaitTime;
    }

    void setThreadWaitTime(long milliseconds) {
        threadWaitTime = milliseconds;
    }

    public void terminate() {
        isTerminated = true;
        Thread.currentThread().interrupt();
    }

    public void run() {
        while (!isTerminated) {
            synchronized (Thread.currentThread()) {
                try {
                    Thread.currentThread().wait(threadWaitTime);
                } catch (InterruptedException e) {
                    isTerminated = true;
                    e.printStackTrace();
                }
            }

            synchronized (warehouse)
            {
                if (!warehouse.isFull())
                {
                    warehouse.add(factory.getPart());
                    ++numberOfGeneratedParts;
                }
            }
        }
    }
}
