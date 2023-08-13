module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;

    opens program.mainFormController to javafx.fxml;
    opens program.applicationView to javafx.fxml;
    opens program to javafx.fxml;
    opens program.objects to javafx.base;
    opens program.applicationLogic to javafx.base;
    opens program.factories to javafx.base;
    exports program;
}