package program.factories;

import program.objects.Accessory;
import program.objects.CarPart;

public class AccessoryFactory implements CarPartFactory
{
    public CarPart getPart() {
        return new Accessory();
    }
}