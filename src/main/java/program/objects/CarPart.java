package program.objects;

import program.logFile.Log;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CarPart {
    final LocalTime creationTime;
    Integer id;

    static Integer currentId = 1;

    protected CarPart() {
        creationTime = LocalTime.now();
        id = getNextId();
        Log.getObjLog().writeLog("Created new particle of type: "
                + getPartTypeString(this.getClass().getName()));

    }
    private String getPartTypeString(String defName){
        String part[] = defName.split("\\.");
        return part[part.length - 1];
    }

    private synchronized static Integer getNextId() {
        synchronized (currentId) {
            return currentId++;
        }
    }

    public Integer getId() {
        return id;
    }

    public String getCreationTime() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return dateFormatter.format(creationTime);
    }

}
