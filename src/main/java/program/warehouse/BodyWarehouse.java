package program.warehouse;

import program.objects.Body;
import program.objects.CarPart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BodyWarehouse extends ParticleWarehouse
{
    private List<Body> carBodyList;

    public  BodyWarehouse(int maxCapacity)
    {
        super(maxCapacity);
        this.carBodyList = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public synchronized Body get()
    {
        return carBodyList.remove(0);
    }

    @Override
    public synchronized void add(CarPart body)
    {
        carBodyList.add((Body)body);
    }

    public List<CarPart> getCarPartList()
    {
        List<CarPart> carParts = new ArrayList<>();

        for(var carBody : carBodyList)
            carParts.add(carBody);

        return carParts;
    }

    @Override
    public boolean isEmpty()
    {
        return carBodyList.isEmpty();
    }

    @Override
    public boolean isFull()
    {
        return carBodyList.size() == super.maxCapacity;
    }
}
