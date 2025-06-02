import controller.Authnticate.ForgetPassword;
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
import model.Authneticate.AuthService;
import runner.Main;

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

        Label userName = new Label("نام کاربری:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        userTextField.setAlignment(Pos.CENTER_RIGHT);
        userTextField.setMaxWidth(Double.MAX_VALUE);
        grid.add(userTextField, 1, 1);

        usernameError = new Text();
        usernameError.setStyle("-fx-fill: red; -fx-font-size: 11px;");
        grid.add(usernameError, 1, 2);

        Label email = new Label("ایمیل:");
        grid.add(email, 0, 3);

        TextField emailField = new TextField();
        emailField.setAlignment(Pos.CENTER_RIGHT);
        emailField.setMaxWidth(Double.MAX_VALUE);
        grid.add(emailField, 1, 3);

        emailError = new Text();
        emailError.setStyle("-fx-fill: red; -fx-font-size: 11px;");
        grid.add(emailError, 1, 4);

        userTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() < 4) {
                usernameError.setText("نام کاربری باید حداقل 4 کاراکتر باشد");
            } else if (!newValue.matches("[a-zA-Z0-9]+")) {
                usernameError.setText("نام کاربری فقط باید شامل حروف انگلیسی و اعداد باشد");
            } else {
                usernameError.setText("");
            }
        });

        emailField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.endsWith("@gmail.com")) {
                emailError.setText("ایمیل باید با @gmail.com تمام شود");
            } else {
                emailError.setText("");
            }
        });

        Button submitBtn = new Button("ارسال لینک بازیابی");
        Button backBtn = new Button("بازگشت به صفحه ورود");

        submitBtn.setMaxWidth(Double.MAX_VALUE);
        backBtn.setMaxWidth(Double.MAX_VALUE);

        VBox buttons = new VBox(5);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(submitBtn, backBtn);
        grid.add(buttons, 1, 5);

        submitBtn.setOnAction(e -> {
            String username = userTextField.getText();
            String emailAddress = emailField.getText();

            if (!usernameError.getText().isEmpty() || !emailError.getText().isEmpty()) {
                Main.showAlert("خطا", "لطفاً خطاهای فرم را برطرف کنید.");
                return;
            }

            if (username.isEmpty() || emailAddress.isEmpty()) {
                Main.showAlert("خطا", "لطفاً تمام فیلدها را پر کنید.");
                return;
            }

            String resetToken = AuthService.FindUser(username, emailAddress);
            if (resetToken != null) {
                showResetPasswordDialog(resetToken);
            } else {
                Main.showAlert("خطا", "کاربری با این مشخصات یافت نشد.");
            }
        });

        backBtn.setOnAction(e -> {
            LoginForm loginForm = new LoginForm(primaryStage);
            primaryStage.setScene(loginForm.getScene());
        });

        scene = new Scene(grid, 400, 300);
    }

    private void showResetPasswordDialog(String resetToken) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("تغییر رمز عبور");
        dialog.setHeaderText("لطفاً رمز عبور جدید خود را وارد کنید");

        ButtonType submitButtonType = new ButtonType("ثبت", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        PasswordField newPassword = new PasswordField();
        TextField newPasswordVisible = new TextField();
        newPassword.setPromptText("رمز عبور جدید");
        newPasswordVisible.setPromptText("رمز عبور جدید");
        newPasswordVisible.setManaged(false);
        newPasswordVisible.setVisible(false);

        PasswordField confirmPassword = new PasswordField();
        TextField confirmPasswordVisible = new TextField();
        confirmPassword.setPromptText("تکرار رمز عبور جدید");
        confirmPasswordVisible.setPromptText("تکرار رمز عبور جدید");
        confirmPasswordVisible.setManaged(false);
        confirmPasswordVisible.setVisible(false);

        ToggleButton newPassToggle = new ToggleButton("👁");
        newPassToggle.setStyle("-fx-font-size: 14px;");

        ToggleButton confirmPassToggle = new ToggleButton("👁");
        confirmPassToggle.setStyle("-fx-font-size: 14px;");

        newPassToggle.setOnAction(e -> {
            if (newPassToggle.isSelected()) {
                newPasswordVisible.setText(newPassword.getText());
                newPasswordVisible.setManaged(true);
                newPasswordVisible.setVisible(true);
                newPassword.setManaged(false);
                newPassword.setVisible(false);
            } else {
                newPassword.setText(newPasswordVisible.getText());
                newPassword.setManaged(true);
                newPassword.setVisible(true);
                newPasswordVisible.setManaged(false);
                newPasswordVisible.setVisible(false);
            }
        });

        confirmPassToggle.setOnAction(e -> {
            if (confirmPassToggle.isSelected()) {
                confirmPasswordVisible.setText(confirmPassword.getText());
                confirmPasswordVisible.setManaged(true);
                confirmPasswordVisible.setVisible(true);
                confirmPassword.setManaged(false);
                confirmPassword.setVisible(false);
            } else {
                confirmPassword.setText(confirmPasswordVisible.getText());
                confirmPassword.setManaged(true);
                confirmPassword.setVisible(true);
                confirmPasswordVisible.setManaged(false);
                confirmPasswordVisible.setVisible(false);
            }
        });

        newPassword.textProperty().addListener((observable, oldValue, newValue) ->
                newPasswordVisible.setText(newValue));
        newPasswordVisible.textProperty().addListener((observable, oldValue, newValue) ->
                newPassword.setText(newValue));
        confirmPassword.textProperty().addListener((observable, oldValue, newValue) ->
                confirmPasswordVisible.setText(newValue));
        confirmPasswordVisible.textProperty().addListener((observable, oldValue, newValue) ->
                confirmPassword.setText(newValue));

        grid.add(new Label("رمز عبور جدید:"), 0, 0);
        HBox newPassBox = new HBox(5);
        newPassBox.getChildren().addAll(newPassToggle, newPassword, newPasswordVisible);
        grid.add(newPassBox, 1, 0);

        grid.add(new Label("تکرار رمز عبور:"), 0, 1);
        HBox confirmPassBox = new HBox(5);
        confirmPassBox.getChildren().addAll(confirmPassToggle, confirmPassword, confirmPasswordVisible);
        grid.add(confirmPassBox, 1, 1);

        Text passwordStrength = new Text();
        passwordStrength.setStyle("-fx-font-size: 11px;");
        grid.add(passwordStrength, 1, 2);

        newPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() < 8) {
                passwordStrength.setText("رمز عبور ضعیف");
                passwordStrength.setStyle("-fx-fill: red; -fx-font-size: 11px;");
            } else if (newValue.matches(".*[a-z].*") && newValue.matches(".*[A-Z].*") &&
                    newValue.matches(".*\\d.*") && newValue.matches(".*[!@#$%^&*()].*")) {
                passwordStrength.setText("رمز عبور قوی");
                passwordStrength.setStyle("-fx-fill: green; -fx-font-size: 11px;");
            } else {
                passwordStrength.setText("رمز عبور متوسط");
                passwordStrength.setStyle("-fx-fill: orange; -fx-font-size: 11px;");
            }
        });

        dialog.getDialogPane().setContent(grid);
        newPassword.requestFocus();

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType) {
                String password = newPassword.getText();
                String confirm = confirmPassword.getText();

                if (password.isEmpty() || confirm.isEmpty()) {
                    Main.showAlert("خطا", "لطفاً تمام فیلدها را پر کنید.");
                    return null;
                }

                if (!password.equals(confirm)) {
                    Main.showAlert("خطا", "رمز عبور و تکرار آن یکسان نیستند.");
                    return null;
                }

                if (password.length() < 8) {
                    Main.showAlert("خطا", "رمز عبور باید حداقل 8 کاراکتر باشد.");
                    return null;
                }

                if (ForgetPassword.rest(resetToken, password,confirm)) {
                    Main.showAlert("موفقیت", "رمز عبور با موفقیت تغییر کرد.");
                    LoginForm loginForm = new LoginForm(primaryStage);
                    primaryStage.setScene(loginForm.getScene());
                } else {
                    Main.showAlert("خطا", "خطا در تغییر رمز عبور. لطفاً دوباره تلاش کنید.");
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    public Scene getScene() {
        return scene;
    }
}