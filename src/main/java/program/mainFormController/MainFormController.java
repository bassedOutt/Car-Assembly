package program.mainFormController;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import program.servicesController.ServicesController;
import program.applicationView.ApplicationView;
import java.io.File;

public class MainFormController extends ApplicationView {
    private final ServicesController servicesController = ServicesController.getServicesController();
    private final int displayThreadDelayTimeValue = 500;
    private final int baseProducingTimeInterval = 5000;
    private Boolean isTerminated = false;
    private final DisplayThread displayThread;

    public MainFormController()
    {
        displayThread = new DisplayThread();
        displayThread.start();
    }

    @FXML
    public void openConfigurationFile()
    {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("xml", "*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle("Відкрити файл для задання конфігурації");
        File config = fileChooser.showOpenDialog(new Stage());
        servicesController.openFile(config);
    }

    class DisplayThread extends Thread
    {
        @Override
        public void run()
        {
            while (!isTerminated)
            {
                engineList.clear();
                engineList.addAll(FXCollections.observableArrayList(servicesController.getEngineList()));
                bodyList.clear();
                bodyList.addAll(FXCollections.observableArrayList(servicesController.getCarBodyList()));
                accessoryList.clear();
                accessoryList.addAll(FXCollections.observableArrayList(servicesController.getAccessoryList()));
                carList.clear();
                carList.addAll(FXCollections.observableArrayList(servicesController.getCarList()));

                synchronized (Thread.currentThread())
                {
                    try
                    {
                        Thread.currentThread().wait(displayThreadDelayTimeValue);
                    }
                    catch (InterruptedException e)
                    {
                        isTerminated = true;
                    }
                }
            }
        }

        void terminateThread()
        {
            isTerminated = true;
        }
    }

    @Override
    public void setSpeedOfEngine()
    {
        int sliderValue = (int)carEngine.getValue();
        carEngineLabel.setText(String.valueOf(sliderValue));
        servicesController.setSpeedOfEngineFactory(baseProducingTimeInterval / sliderValue);
    }

    @Override
    public void setAccessorySpeed()
    {
        int sliderValue = (int) carAccesoires.getValue();
        carAccesoiresLabel.setText(String.valueOf(sliderValue));
        servicesController.setSpeedOfAccessoryFactory(baseProducingTimeInterval / sliderValue);
    }

    @Override
    public void setBodySpeed()
    {
        int sliderValue = (int) carBody.getValue();
        carBodyLabel.setText(String.valueOf(sliderValue));
        servicesController.setSpeedOfBodyFactory(baseProducingTimeInterval / sliderValue);
    }

    @Override
    public void setDealerSpeed()
    {
        int sliderValue = (int)completeGoods.getValue();
        completeGoodsLabel.setText(String.valueOf(sliderValue));
        servicesController.setSpeedOfDealer(baseProducingTimeInterval / sliderValue);
    }

    public void terminateApplication()
    {
        servicesController.terminateFactoryAssemblyLine();
        displayThread.terminateThread();
        System.exit(0);
    }
}
