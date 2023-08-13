package program.servicesController;

import program.objects.*;
import program.configurator.Configurator;
import program.applicationLogic.FactoryAssemblyLine;

import java.io.File;
import java.util.List;

public class ServicesController
{
    private static ServicesController servicesController;
    private FactoryAssemblyLine factoryAssemblyLine;
    private final Configurator configurator = new Configurator();
    private final int initialTime = 3000;

    private ServicesController()
    {
        initFactoryAssemblyLine();
    }

    private void initFactoryAssemblyLine()
    {
        factoryAssemblyLine = new FactoryAssemblyLine(initialTime,
                                                      configurator.GetStorageSize(),
                                                      configurator.GetDealersCount(),
                                                      configurator.GetProvidersCount(),
                                                      configurator.GetCollectorsCount());

        factoryAssemblyLine.start();
    }

    public static ServicesController getServicesController()
    {
        if (servicesController == null)
        {
            servicesController = new ServicesController();
        }

        return servicesController;
    }

    public void openFile(File file)
    {
        configurator.LoadConfigFromFile(file);
        factoryAssemblyLine.terminate();
        initFactoryAssemblyLine();
    }

    public List<CarPart> getEngineList()
    {
        return factoryAssemblyLine.getEngineWarehouse().getCarPartList();
    }

    public List<CarPart> getAccessoryList()
    {
        return factoryAssemblyLine.getAccessoryWarehouse().getCarPartList();
    }

    public List<CarPart> getCarBodyList()
    {
        return factoryAssemblyLine.getBodyWarehouse().getCarPartList();
    }

    public List<Car> getCarList()
    {
        return factoryAssemblyLine.getCarWarehouse().getCarList();
    }

    public void setSpeedOfEngineFactory(int speedValue)
    {
        factoryAssemblyLine.setEngineProducerWaitTime(speedValue);
    }

    public void setSpeedOfAccessoryFactory(int speedValue)
    {
        factoryAssemblyLine.setAccessoryProducerWaitTime(speedValue);
    }

    public void setSpeedOfBodyFactory(int speedValue)
    {
        factoryAssemblyLine.setCarBodyProducerWaitTime(speedValue);
    }

    public void terminateFactoryAssemblyLine()
    {
        factoryAssemblyLine.terminate();
    }

    public void setSpeedOfDealer(int speedValue)
    {
        factoryAssemblyLine.setDealersWaitTime(speedValue);
    }
}
