package program.warehouse;

import program.objects.CarPart;
import program.objects.Engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EngineWarehouse extends ParticleWarehouse
{
    private List<Engine> carEngineList;

    public  EngineWarehouse(int maxCapacity)
    {
        super(maxCapacity);
        this.carEngineList = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public synchronized Engine get()
    {
        return carEngineList.remove(0);
    }

    @Override
    public synchronized void add(CarPart engine)
    {
        carEngineList.add((Engine)engine);
    }

    public List<CarPart> getCarPartList()
    {
        List<CarPart> carParts = new ArrayList<>();

        for(var carBody : carEngineList)
            carParts.add(carBody);

        return carParts;
    }

    @Override
    public boolean isEmpty()
    {
        return carEngineList.isEmpty();
    }

    @Override
    public boolean isFull()
    {
        return carEngineList.size() == super.maxCapacity;
    }
}
