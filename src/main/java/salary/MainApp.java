package salary;


import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MainApp extends Application {
    public void start(Stage primaryStage) throws Exception {
        try {
            FormManager formManager = new FormManager();
            formManager.showLoginController();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }

    }
}