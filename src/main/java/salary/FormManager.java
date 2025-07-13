package salary;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import salary.controller.LoginController;
import salary.controller.MainFormController;

public class FormManager {
    public static LoginController loginController;
    public static MainFormController mainFormController;


    private Stage loginStage;
    private Stage mainFormStage;

    public LoginController showLoginController() throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/LoginForm.fxml"));
        Parent root = (Parent)loader.load();
        loginController = (LoginController) loader.getController();
        Scene scene = new Scene(root);
        this.loginStage = new Stage();
        this.loginStage.setScene(scene);
        this.loginStage.setTitle("ورود");
        this.loginStage.show();
        return loginController;
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



}
