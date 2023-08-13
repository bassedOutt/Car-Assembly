package program.warehouse;

import program.objects.Accessory;
import program.objects.CarPart;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccessoryWarehouse extends ParticleWarehouse
{
    private List<Accessory> accessoryList;

    public AccessoryWarehouse(int maxCapacity)
    {
        super(maxCapacity);
        this.accessoryList = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public synchronized Accessory get()
    {
        return accessoryList.remove(0);
    }

    @Override
    public synchronized void add(CarPart accessory)
    {
        accessoryList.add((Accessory)accessory);
    }

    public List<CarPart> getCarPartList()
    {
        List<CarPart> carParts = new ArrayList<>();

        for(var carBody : accessoryList)
            carParts.add(carBody);

        return carParts;
    }

    @Override
    public boolean isEmpty()
    {
        return accessoryList.isEmpty();
    }

    @Override
    public boolean isFull()
    {
        return accessoryList.size() == super.maxCapacity;
    }
}