package program.applicationView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import program.objects.*;

public abstract class ApplicationView {
    @FXML
    protected Slider carBody, carEngine, carAccesoires, completeGoods;

    @FXML
    protected Label carBodyLabel, carEngineLabel, carAccesoiresLabel, completeGoodsLabel;

    @FXML
    protected TableColumn<Engine, Integer> engineId;

    @FXML
    protected TableColumn<Engine, String> engineData, engineType;

    @FXML
    protected TableColumn<Accessory, Integer> accessoryId;

    @FXML
    protected TableColumn<Accessory, String> accessoryData, accessoryType;

    @FXML
    protected TableColumn<Body, Integer> bodyId;

    @FXML
    protected TableColumn<Body, String> bodyData, bodyType;

    @FXML
    protected TableColumn<Car, Integer> carId;

    @FXML
    protected TableColumn<Car, String> carData, carType;

    @FXML
    protected TableView<CarPart> engineTable;

    @FXML
    protected TableView<CarPart> bodyTable;

    @FXML
    protected TableView<CarPart> accessoryTable;

    @FXML
    protected TableView<Car> carTable;

    protected ObservableList<CarPart> engineList = FXCollections.observableArrayList();
    protected ObservableList<Car> carList = FXCollections.observableArrayList();
    protected ObservableList<CarPart> bodyList = FXCollections.observableArrayList();
    protected ObservableList<CarPart> accessoryList = FXCollections.observableArrayList();

    @FXML
    private CategoryAxis xAxis;

    @FXML
    public void initialize() {
        carBody.valueProperty().addListener(observable -> setBodySpeed());
        carEngine.valueProperty().addListener(observable -> setSpeedOfEngine());
        carAccesoires.valueProperty().addListener(observable -> setAccessorySpeed());
        completeGoods.valueProperty().addListener(observable -> setDealerSpeed());

        engineTable.setItems(engineList);
        carTable.setItems(carList);
        bodyTable.setItems(bodyList);
        accessoryTable.setItems(accessoryList);
        engineId.setCellValueFactory(new PropertyValueFactory<>("id"));
        engineType.setCellValueFactory(new PropertyValueFactory<>("type"));
        engineData.setCellValueFactory(new PropertyValueFactory<>("creationTime"));
        accessoryId.setCellValueFactory(new PropertyValueFactory<>("id"));
        accessoryType.setCellValueFactory(new PropertyValueFactory<>("type"));
        accessoryData.setCellValueFactory(new PropertyValueFactory<>("creationTime"));
        carId.setCellValueFactory(new PropertyValueFactory<>("id"));
        carType.setCellValueFactory(new PropertyValueFactory<>("type"));
        carData.setCellValueFactory(new PropertyValueFactory<>("carCreateTime"));
        bodyId.setCellValueFactory(new PropertyValueFactory<>("id"));
        bodyType.setCellValueFactory(new PropertyValueFactory<>("type"));
        bodyData.setCellValueFactory(new PropertyValueFactory<>("creationTime"));
    }

    protected SpinnerValueFactory<Integer> getSpinnerValueFactory(int initialValue) {
        return new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, initialValue);
    }

    public abstract void setDealerSpeed();

    public abstract void setSpeedOfEngine();

    public abstract void setAccessorySpeed();

    public abstract void setBodySpeed();
}
