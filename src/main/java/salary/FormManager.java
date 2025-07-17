package salary;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import salary.controller.EmployeeTabController;
import salary.controller.LoginFormController;
import salary.controller.MainFormController;

public class FormManager {
    public static LoginFormController loginFormController;
    public static MainFormController mainFormController;
    public static EmployeeTabController employeeTabController;

    private Stage loginStage;
    private Stage mainFormStage;
    private Stage employeeTabStage;

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

        FXMLLoader loader2 = new FXMLLoader(this.getClass().getResource("/view/EmployeeTab.fxml"));
        Parent root2 = (Parent)loader2.load();
        employeeTabController = (EmployeeTabController) loader2.getController();
        Scene scene2 = new Scene(root2);
        this.employeeTabStage = new Stage();
        this.employeeTabStage.setScene(scene2);

        return mainFormController;
    }

    public EmployeeTabController showEmployeeTabController() throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/EmployeeTab.fxml"));
        Parent root = (Parent)loader.load();
        employeeTabController = (EmployeeTabController) loader.getController();
        Scene scene = new Scene(root);
        this.employeeTabStage = new Stage();
        this.employeeTabStage.setScene(scene);
        this.employeeTabStage.show();
        return employeeTabController;

    }




}
