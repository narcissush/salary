package salary;


import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import salary.controller.AppState;
import salary.controller.MainFormController;
import salary.model.entity.Employee;
import salary.model.services.EmployeeService;

public class MainApp extends Application {
    public void start(Stage primaryStage) throws Exception {

        FormManager formManager = new FormManager();
        formManager.showMainFormController();

    }
}