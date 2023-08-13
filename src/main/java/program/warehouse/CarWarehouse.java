package program.warehouse;

import program.objects.Car;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CarWarehouse implements IWarehouse
{
    private Integer maxCapacity;

    private List<Car> carList;

    public CarWarehouse(int maxCapacity)
    {
        this.maxCapacity = maxCapacity;
        this.carList = Collections.synchronizedList(new ArrayList<>());
    }

    public synchronized Car get()
    {
        return carList.remove(0);
    }

    public synchronized void add(Car car)
    {
        carList.add(car);
    }

    public List<Car> getCarList()
    {
        return carList;
    }

    @Override
    public boolean isEmpty()
    {
        return carList.isEmpty();
    }

    @Override
    public boolean isFull()
    {
        return carList.size() == maxCapacity;
    }
}
