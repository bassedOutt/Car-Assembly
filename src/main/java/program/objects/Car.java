package program.objects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Car {
    Integer id;
    LocalDateTime carCreateTime;
    CarPart body;
    CarPart accessory;
    CarPart engine;
    static Integer previewId = 1;

    public Car(CarPart _engine, CarPart _body, CarPart _accessory) {
        engine = _engine;
        body = _body;
        accessory = _accessory;
        carCreateTime = LocalDateTime.now();
        previewId = getCurrentId();
        id = getCurrentId();
    }

    private static Integer getCurrentId() {
        synchronized (previewId) {
            return previewId++;
        }
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return "МАШИНА";
    }
    public String getCarCreateTime() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return dateFormatter.format(carCreateTime);
    }
}
