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
    private void createRegisterScene() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(5);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        int rowIndex = 0;

        Text sceneTitle = new Text("ثبت نام");
        sceneTitle.setStyle("-fx-font-size: 20px;");
        grid.add(sceneTitle, 0, rowIndex, 2, 1);
        rowIndex++;

        // نام کاربری
        Label userName = new Label("نام کاربری:");
        grid.add(userName, 0, rowIndex);
        TextField userTextField = new TextField();
        userTextField.setAlignment(Pos.CENTER_RIGHT);
        userTextField.setMaxWidth(Double.MAX_VALUE);
        grid.add(userTextField, 1, rowIndex);
        rowIndex++;

        usernameError = new Label("");
        usernameError.setTextFill(Color.RED);
        usernameError.setStyle("-fx-font-size: 10px;");
        grid.add(usernameError, 1, rowIndex);
        rowIndex++;

        // رمز عبور
        Label pw = new Label("رمز عبور:");
        grid.add(pw, 0, rowIndex);

        HBox passwordBox = new HBox(5);
        passwordBox.setAlignment(Pos.CENTER_RIGHT);

        PasswordField pwBox = new PasswordField();
        pwBox.setAlignment(Pos.CENTER_RIGHT);
        pwBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(pwBox, javafx.scene.layout.Priority.ALWAYS);

        visiblePasswordField = new TextField();
        visiblePasswordField.setAlignment(Pos.CENTER_RIGHT);
        visiblePasswordField.setMaxWidth(Double.MAX_VALUE);
        visiblePasswordField.setManaged(false);
        visiblePasswordField.setVisible(false);
        HBox.setHgrow(visiblePasswordField, javafx.scene.layout.Priority.ALWAYS);

        ToggleButton toggleVisibilityBtn = new ToggleButton("👁");
        toggleVisibilityBtn.setStyle("-fx-font-size: 14px;");

        passwordBox.getChildren().addAll(toggleVisibilityBtn, pwBox, visiblePasswordField);
        grid.add(passwordBox, 1, rowIndex);
        rowIndex++;

        passwordStrengthText = new Text("");
        passwordStrengthText.setStyle("-fx-font-size: 10px;");
        grid.add(passwordStrengthText, 1, rowIndex);
        rowIndex++;

        // تایید رمز عبور
        Label confirmPw = new Label("تایید رمز عبور:");
        grid.add(confirmPw, 0, rowIndex);

        HBox confirmPasswordBox = new HBox(5);
        confirmPasswordBox.setAlignment(Pos.CENTER_RIGHT);

        PasswordField confirmPwBox = new PasswordField();
        confirmPwBox.setAlignment(Pos.CENTER_RIGHT);
        confirmPwBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(confirmPwBox, javafx.scene.layout.Priority.ALWAYS);

        visibleConfirmPasswordField = new TextField();
        visibleConfirmPasswordField.setAlignment(Pos.CENTER_RIGHT);
        visibleConfirmPasswordField.setMaxWidth(Double.MAX_VALUE);
        visibleConfirmPasswordField.setManaged(false);
        visibleConfirmPasswordField.setVisible(false);
        HBox.setHgrow(visibleConfirmPasswordField, javafx.scene.layout.Priority.ALWAYS);

        ToggleButton toggleConfirmVisibilityBtn = new ToggleButton("👁");
        toggleConfirmVisibilityBtn.setStyle("-fx-font-size: 14px;");

        confirmPasswordBox.getChildren().addAll(toggleConfirmVisibilityBtn, confirmPwBox, visibleConfirmPasswordField);
        grid.add(confirmPasswordBox, 1, rowIndex);
        rowIndex++;

        confirmPwError = new Label("");
        confirmPwError.setTextFill(Color.RED);
        confirmPwError.setStyle("-fx-font-size: 10px;");
        grid.add(confirmPwError, 1, rowIndex);
        rowIndex++;

        // ایمیل
        Label email = new Label("ایمیل:");
        grid.add(email, 0, rowIndex);
        TextField emailField = new TextField();
        emailField.setAlignment(Pos.CENTER_RIGHT);
        emailField.setMaxWidth(Double.MAX_VALUE);
        grid.add(emailField, 1, rowIndex);
        rowIndex++;

        emailError = new Label("");
        emailError.setTextFill(Color.RED);
        emailError.setStyle("-fx-font-size: 10px;");
        grid.add(emailError, 1, rowIndex);
        rowIndex++;

        // دکمه‌ها
        Button registerBtn = new Button("ثبت نام");
        Button backBtn = new Button("بازگشت به صفحه ورود");

        // تنظیم عرض دکمه‌ها
        registerBtn.setMaxWidth(Double.MAX_VALUE);
        backBtn.setMaxWidth(Double.MAX_VALUE);

        VBox buttons = new VBox(5);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(registerBtn, backBtn);
        grid.add(buttons, 1, rowIndex);

        // همگام‌سازی فیلدهای رمز عبور
        pwBox.textProperty().bindBidirectional(visiblePasswordField.textProperty());
        confirmPwBox.textProperty().bindBidirectional(visibleConfirmPasswordField.textProperty());

        toggleVisibilityBtn.setOnAction(e -> {
            if (toggleVisibilityBtn.isSelected()) {
                visiblePasswordField.setText(pwBox.getText());
                visiblePasswordField.setManaged(true);
                visiblePasswordField.setVisible(true);
                pwBox.setManaged(false);
                pwBox.setVisible(false);
            } else {
                pwBox.setText(visiblePasswordField.getText());
                pwBox.setManaged(true);
                pwBox.setVisible(true);
                visiblePasswordField.setManaged(false);
                visiblePasswordField.setVisible(false);
            }
        });
}