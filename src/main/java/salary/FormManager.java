package salary;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import salary.controller.AllowanceFormController;
import salary.controller.LoanTypeFormController;
import salary.controller.LoginFormController;
import salary.controller.MainFormController;

public class FormManager {
    public static LoginFormController loginFormController;
    public static MainFormController mainFormController;
    public static AllowanceFormController allowanceFormController;
    public static LoanTypeFormController loanTypeFormController;




    private Stage loginStage;
    private Stage mainFormStage;
    public Stage allowanceFormStage;
    public Stage loanTypeFormStage;


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
    public AllowanceFormController showAllowanceFormController() throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/AllowanceForm.fxml"));
        Parent root = (Parent)loader.load();
        allowanceFormController = (AllowanceFormController) loader.getController();
        Scene scene = new Scene(root);
        this.allowanceFormStage = new Stage();
        this.allowanceFormStage.setScene(scene);
        this.allowanceFormStage.show();
        return allowanceFormController;
    }

    public LoanTypeFormController showLoanTypeFormController() throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/LoanTypeForm.fxml"));
        Parent root = (Parent)loader.load();
        loanTypeFormController = (LoanTypeFormController) loader.getController();
        Scene scene = new Scene(root);
        this.loanTypeFormStage = new Stage();
        this.loanTypeFormStage.setScene(scene);
        this.loanTypeFormStage.show();
        return loanTypeFormController;
    }


}
