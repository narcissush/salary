package salary;


import javafx.application.Application;
import javafx.stage.Stage;


public class MainApp extends Application {
    public void start(Stage primaryStage) throws Exception {

        FormManager formManager = new FormManager();
        formManager.showMainFormController();

    }
}