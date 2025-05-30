package view;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SignUpForm {
    private Scene scene;
    private Stage primaryStage;
    private Text passwordStrengthText;
    private Label confirmPwError;
    private Label usernameError;
    private Label emailError;
    private TextField visiblePasswordField;
    private TextField visibleConfirmPasswordField;

    public SignUpForm(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createRegisterScene();
    }
}