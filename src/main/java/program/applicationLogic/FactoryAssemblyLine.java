package program.applicationLogic;

import program.factories.AccessoryFactory;
import program.factories.CarBodyFactory;
import program.factories.EngineFactory;
import program.logFile.Log;
import program.threadpool.ThreadPool;
import program.warehouse.*;

import java.util.ArrayList;
import java.util.List;

public class FactoryAssemblyLine extends Thread {
    int dealersCount;
    int producersCount;
    int mountersCount;
    private ParticleWarehouse accessoryWarehouse;
    private ParticleWarehouse engineWarehouse;
    private ParticleWarehouse bodyWarehouse;
    private CarWarehouse carWarehouse;
    List<CarPartsProducer> engineProducers;
    List<CarPartsProducer> accessoryProducers;
    List<CarPartsProducer> bodyProducers;
    List<CarAssembler> carAssemblers;
    ArrayList<CarDealer> dealers = new ArrayList<>();
    ThreadPool threadpool;

    public FactoryAssemblyLine(int initialTime,
                               int maxSize,
                               int dealersCount,
                               int producersCount,
                               int mountersCount)
    {
        Log.getObjLog().writeLog("Program start");

        accessoryWarehouse = new AccessoryWarehouse(maxSize);
        engineWarehouse = new EngineWarehouse(maxSize);
        bodyWarehouse = new BodyWarehouse(maxSize);
        carWarehouse = new CarWarehouse(maxSize);
        this.dealersCount = dealersCount;
        this.mountersCount = mountersCount;
        this.producersCount = producersCount;
        threadpool = new ThreadPool();
        accessoryProducers = new ArrayList<>();
        engineProducers = new ArrayList<>();
        bodyProducers = new ArrayList<>();
        carAssemblers = new ArrayList<>();

        for (int i = 0; i < dealersCount; i++) {
            dealers.add(new CarDealer(carWarehouse, initialTime));
        }
        for (int i = 0; i < producersCount; i++) {
            accessoryProducers.add(new CarPartsProducer(accessoryWarehouse, new AccessoryFactory(), initialTime));
            engineProducers.add(new CarPartsProducer(engineWarehouse, new EngineFactory(), initialTime));
            bodyProducers.add(new CarPartsProducer(bodyWarehouse, new CarBodyFactory(), initialTime));
        }
        for(int i = 0; i < mountersCount; i++) {
            carAssemblers.add(new CarAssembler(accessoryWarehouse,
                    engineWarehouse,
                    bodyWarehouse,
                    carWarehouse,
                    initialTime,
                    threadpool));
        }
        CarAssembler.init();
    }

    public void run() {
        for (CarAssembler d : carAssemblers) {
            d.start();
        }
        for (CarPartsProducer d : accessoryProducers) {
            d.start();
        }
        for (CarPartsProducer d : engineProducers) {
            d.start();
        }
        for (CarPartsProducer d : bodyProducers) {
            d.start();
        }
        for (CarDealer d : dealers) {
            d.start();
        }
    }

    public void terminate() {
        for(CarPartsProducer p : bodyProducers) {
            p.terminate();
        }
        for(CarPartsProducer p : engineProducers) {
            p.terminate();
        }
        for(CarPartsProducer p : accessoryProducers) {
            p.terminate();
        }
        for(CarAssembler m : carAssemblers) {
            m.terminate();
        }
        for (CarDealer d : dealers) {
            d.terminate();
        }

        try {
            for(CarPartsProducer p : bodyProducers) {
                p.join();
            }
            for(CarPartsProducer p : engineProducers) {
                p.join();
            }
            for(CarPartsProducer p : accessoryProducers) {
                p.join();
            }
            for(CarAssembler m : carAssemblers) {
                m.join();
            }
            for (CarDealer d : dealers) {
                d.join();
            }
        }
        catch (InterruptedException e) {
        }
    }

    public ParticleWarehouse getAccessoryWarehouse() {
        return accessoryWarehouse;
    }

    public ParticleWarehouse getEngineWarehouse() {
        return engineWarehouse;
    }

    public ParticleWarehouse getBodyWarehouse() {
        return bodyWarehouse;
    }

    public CarWarehouse getCarWarehouse() {
        return carWarehouse;
    }

    public void setAccessoryProducerWaitTime(long milliseconds) {
        for (CarPartsProducer accessoryProducer : accessoryProducers) {
            accessoryProducer.setThreadWaitTime(milliseconds);
        }
    }

    public void setEngineProducerWaitTime(long milliseconds) {
        for (CarPartsProducer engineProducer : engineProducers) {
            engineProducer.setThreadWaitTime(milliseconds);
        }
    }

    public void setCarBodyProducerWaitTime(long milliseconds) {
        for (CarPartsProducer bodyProducer : bodyProducers) {
            bodyProducer.setThreadWaitTime(milliseconds);
        }
    }

    public void setDealersWaitTime(long milliseconds) {
        for (CarDealer d : dealers) {
            d.setThreadWaitTime(milliseconds);
        }
    }
}
