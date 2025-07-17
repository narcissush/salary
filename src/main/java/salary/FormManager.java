package salary;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import salary.controller.EmployeeListController;
import salary.controller.EmployeeTabController;
import salary.controller.LoginFormController;
import salary.controller.MainFormController;

public class FormManager {
    public static LoginFormController loginFormController;
    public static MainFormController mainFormController;
    public static EmployeeTabController employeeTabController;
    private static EmployeeListController employeeListController;

    private Stage loginStage;
    private Stage mainFormStage;

    public LoginFormController showLoginController() throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/LoginForm.fxml"));
        Parent root = (Parent)loader.load();
        loginFormController = (LoginFormController) loader.getController();
        Scene scene = new Scene(root);
        this.loginStage = new Stage();
        this.loginStage.setScene(scene);
        this.loginStage.setTitle("ورود");
        this.loginStage.show();
        return loginFormController;
    }

    public MainFormController showMainFormController() throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/MainForm.fxml"));
        Parent root = (Parent)loader.load();
        mainFormController = (MainFormController) loader.getController();
        Scene scene = new Scene(root);
        this.mainFormStage = new Stage();
        this.mainFormStage.setScene(scene);
        this.mainFormStage.show();

        return mainFormController;
    }

    public EmployeeTabController showEmployeeTabController() throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/EmployeeTab.fxml"));
       Parent root = (Parent)loader.load();
        employeeTabController = (EmployeeTabController) loader.getController();
        return employeeTabController;

    }
    public EmployeeListController showEmployeeListController() throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/EmployeeList.fxml"));
        Parent root = (Parent)loader.load();
        employeeListController = (EmployeeListController) loader.getController();
        return employeeListController;

    }




}
