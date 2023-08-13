package program.warehouse;

import program.objects.CarPart;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

abstract public class ParticleWarehouse implements IWarehouse
{
    protected Integer maxCapacity;

    public ParticleWarehouse(int maxCapacity)
    {
        this.maxCapacity = maxCapacity;
    }

    abstract public CarPart get();

    abstract public void add(CarPart carParticle);

    abstract public List<CarPart> getCarPartList();

    @Override
    abstract public boolean isEmpty();

    @Override
    abstract public boolean isFull();
}