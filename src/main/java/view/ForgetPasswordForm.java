package view;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ForgotPasswordForm {
    private Scene scene;
    private Stage primaryStage;
    private Text usernameError;
    private Text emailError;

    public ForgotPasswordForm(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createForgotPasswordScene();
    }

    private void createForgotPasswordScene() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(5);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        Text sceneTitle = new Text("بازیابی رمز عبور");
        sceneTitle.setStyle("-fx-font-size: 20px;");
        grid.add(sceneTitle, 0, 0, 2, 1);

        // نام کاربری
        Label userName = new Label("نام کاربری:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        userTextField.setAlignment(Pos.CENTER_RIGHT);
        userTextField.setMaxWidth(Double.MAX_VALUE);
        grid.add(userTextField, 1, 1);

        // پیام خطای نام کاربری
        usernameError = new Text();
        usernameError.setStyle("-fx-fill: red; -fx-font-size: 11px;");
        grid.add(usernameError, 1, 2);

        // ایمیل
        Label email = new Label("ایمیل:");
        grid.add(email, 0, 3);

        TextField emailField = new TextField();
        emailField.setAlignment(Pos.CENTER_RIGHT);
        emailField.setMaxWidth(Double.MAX_VALUE);
        grid.add(emailField, 1, 3);

        // پیام خطای ایمیل
        emailError = new Text();
        emailError.setStyle("-fx-fill: red; -fx-font-size: 11px;");
        grid.add(emailError, 1, 4);

        // اعتبارسنجی نام کاربری
        userTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() < 4) {
                usernameError.setText("نام کاربری باید حداقل 4 کاراکتر باشد");
            } else if (!newValue.matches("[a-zA-Z0-9]+")) {
                usernameError.setText("نام کاربری فقط باید شامل حروف انگلیسی و اعداد باشد");
            } else {
                usernameError.setText("");
            }
        });

        // اعتبارسنجی ایمیل
        emailField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.endsWith("@gmail.com")) {
                emailError.setText("ایمیل باید با @gmail.com تمام شود");
            } else {
                emailError.setText("");
            }
        });
    }