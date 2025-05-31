package view;

import controller.Authnticate.Login;
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
import runner.Main;

public class LoginForm {
    private Scene scene;
    private Stage primaryStage;
    private TextField visiblePasswordField;
    private Text usernameError;
    private Text passwordError;

    public LoginForm(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createLoginScene();
    }

    private void createLoginScene() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(2);
        grid.setPadding(new Insets(15, 15, 15, 15));
        grid.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        Text sceneTitle = new Text("ورود به سیستم");
        sceneTitle.setStyle("-fx-font-size: 18px;");
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label userName = new Label("نام کاربری:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        userTextField.setAlignment(Pos.CENTER_RIGHT);
        userTextField.setPrefHeight(30);
        userTextField.setMaxWidth(Double.MAX_VALUE);
        grid.add(userTextField, 1, 1);

        usernameError = new Text();
        usernameError.setStyle("-fx-fill: red; -fx-font-size: 10px;");
        grid.add(usernameError, 1, 2);

        userTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() < 4) {
                usernameError.setText("نام کاربری باید حداقل 4 کاراکتر باشد");
            } else if (!newValue.matches("[a-zA-Z0-9]+")) {
                usernameError.setText("نام کاربری فقط باید شامل حروف انگلیسی و اعداد باشد");
            } else {
                usernameError.setText("");
            }
        });

        Label pw = new Label("رمز عبور:");
        grid.add(pw, 0, 3);

        HBox passwordBox = new HBox(2);
        passwordBox.setAlignment(Pos.CENTER_RIGHT);

        PasswordField pwBox = new PasswordField();
        pwBox.setAlignment(Pos.CENTER_RIGHT);
        pwBox.setPrefHeight(30);
        pwBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(pwBox, javafx.scene.layout.Priority.ALWAYS);

        visiblePasswordField = new TextField();
        visiblePasswordField.setAlignment(Pos.CENTER_RIGHT);
        visiblePasswordField.setPrefHeight(30);
        visiblePasswordField.setMaxWidth(Double.MAX_VALUE);
        visiblePasswordField.setManaged(false);
        visiblePasswordField.setVisible(false);
        HBox.setHgrow(visiblePasswordField, javafx.scene.layout.Priority.ALWAYS);

        passwordError = new Text();
        passwordError.setStyle("-fx-fill: red; -fx-font-size: 10px;");
        grid.add(passwordError, 1, 4);

        pwBox.textProperty().addListener((observable, oldValue, newValue) -> {
            validatePassword(newValue);
        });
        visiblePasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            validatePassword(newValue);
        });

        ToggleButton toggleVisibilityBtn = new ToggleButton("👁");
        toggleVisibilityBtn.setStyle("-fx-font-size: 12px; -fx-padding: 2 5;");
        toggleVisibilityBtn.setPrefHeight(30);

        passwordBox.getChildren().addAll(toggleVisibilityBtn, pwBox, visiblePasswordField);
        grid.add(passwordBox, 1, 3);

        pwBox.textProperty().bindBidirectional(visiblePasswordField.textProperty());

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

        Button loginBtn = new Button("ورود");
        Button registerBtn = new Button("ثبت نام");
        Button forgotPasswordBtn = new Button("فراموشی رمز عبور");

        loginBtn.setPrefHeight(30);
        registerBtn.setPrefHeight(30);
        forgotPasswordBtn.setPrefHeight(30);

        loginBtn.setMaxWidth(Double.MAX_VALUE);
        registerBtn.setMaxWidth(Double.MAX_VALUE);
        forgotPasswordBtn.setMaxWidth(Double.MAX_VALUE);

        VBox buttons = new VBox(3);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(loginBtn, registerBtn, forgotPasswordBtn);
        grid.add(buttons, 1, 5);

        loginBtn.setOnAction(e -> {
            String username = userTextField.getText();
            String password = pwBox.isVisible() ? pwBox.getText() : visiblePasswordField.getText();

            if (!usernameError.getText().isEmpty() || !passwordError.getText().isEmpty()) {
                Main.showAlert("خطا", "لطفاً خطاهای فرم را برطرف کنید.");
                return;
            }
            if (username.isEmpty() || password.isEmpty()) {
                Main.showAlert("خطا", "لطفاً تمام فیلدها را پر کنید.");
                return;
            }
            if (Login.login(username, password)) {
                Main.showAlert("موفقیت", "ورود موفقیت‌آمیز بود!");
                DashboardForm dashboardForm = new DashboardForm(primaryStage);
                primaryStage.setScene(dashboardForm.getScene());
            } else {
                Main.showAlert("خطا", "نام کاربری یا رمز عبور اشتباه است.");
            }
        });
        registerBtn.setOnAction(e -> {
            SignUpForm signUpForm = new SignUpForm(primaryStage);
            primaryStage.setScene(signUpForm.getScene());
        });

        forgotPasswordBtn.setOnAction(e -> {
            ForgotPasswordForm forgotPasswordForm = new ForgotPasswordForm(primaryStage);
            primaryStage.setScene(forgotPasswordForm.getScene());
        });

        scene = new Scene(grid, 380, 320);
    }

    private void validatePassword(String password) {
        if (password.length() < 8) {
            passwordError.setText("رمز عبور باید حداقل 8 کاراکتر باشد");
        }else {
            passwordError.setText("");
        }
    }

    public Scene getScene() {
        return scene;
    }
}