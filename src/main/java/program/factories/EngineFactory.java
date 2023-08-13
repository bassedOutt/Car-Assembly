package program.factories;

import program.objects.CarPart;
import program.objects.Engine;

public class EngineFactory implements CarPartFactory {
    public CarPart getPart() {
        return new Engine();
    }
}