package program.applicationLogic;

import program.objects.Car;
import program.logFile.Log;
import program.threadpool.ThreadPool;
import program.warehouse.CarWarehouse;
import program.warehouse.ParticleWarehouse;

public class CarAssembler extends Thread
{
    private ParticleWarehouse accessoryWarehouse;
    private ParticleWarehouse engineWarehouse;
    private ParticleWarehouse bodyWarehouse;
    private CarWarehouse carWarehouse;

    boolean isTerminated = false;
    int numberOfGeneratedParts = 0;
    long threadWaitTime;
    static ThreadPool threadpool;

    private class CreateCar implements Runnable {
        public void run() {
            if (!accessoryWarehouse.isEmpty()
                    && !engineWarehouse.isEmpty()
                    && !bodyWarehouse.isEmpty())
            {
                carWarehouse.add(new Car(engineWarehouse.get(),
                                       bodyWarehouse.get(),
                                       accessoryWarehouse.get()));
            }
        }
    }


    public CarAssembler(ParticleWarehouse accessoryWarehouse,
                        ParticleWarehouse engineWarehouse,
                        ParticleWarehouse bodyWarehouse,
                        CarWarehouse carWarehouse,
                        long threadWaitTime,
                        ThreadPool th)
    {
        this.accessoryWarehouse = accessoryWarehouse;
        this.engineWarehouse = engineWarehouse;
        this.bodyWarehouse = bodyWarehouse;
        this.carWarehouse = carWarehouse;
        this.threadWaitTime = threadWaitTime;
        threadpool = th;
    }

    static public void init() {
        threadpool.start();
    }

    public void run() {
        Log.getObjLog().writeLog("Starting create the car");
        while (!isTerminated)
        {

            while (accessoryWarehouse.isEmpty() 
                    || engineWarehouse.isEmpty() 
                    || bodyWarehouse.isEmpty()) {
            }
            
            Runnable r = new CreateCar();
            numberOfGeneratedParts++;
            threadpool.addRunnableIntoQueue(r);

            synchronized (Thread.currentThread()) {
                try {
                    Thread.currentThread().wait(threadWaitTime);
                } catch (InterruptedException e) {
                    isTerminated = true;
                }
            }
        }
    }

    public void terminate() {
        isTerminated = true;
        threadpool.setDoStop();
        threadpool.interrupt();
        Thread.currentThread().interrupt();
        threadpool.pollRunnableFromQueue();
        Log.getObjLog().writeLog("Terminated car assembler");
    }
}
