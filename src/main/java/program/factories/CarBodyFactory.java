package program.factories;

import program.objects.Body;

public class CarBodyFactory implements CarPartFactory {
    public Body getPart() {
        return new Body();
    }
}