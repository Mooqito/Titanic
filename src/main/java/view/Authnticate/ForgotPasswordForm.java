package view.Authnticate;
import controller.Authnticate.ForgetPassword;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Authneticate.AuthService;
import runner.Main;
import javafx.stage.Screen;

public class ForgotPasswordForm {
    private Scene scene;
    private Stage primaryStage;
    private Text usernameError;
    private Text emailError;
    private Text passwordError;
    private PasswordField pwBox;
    private PasswordField confirmPwBox;
    private Text statusMessage;
    private Text resetStatusMessage;
    private TextField userTextField;
    private TextField emailField;
    private PasswordField confirmPassword;
    private TextField confirmPasswordVisible;
    private Text confirmPwError;
    private Text passwordStrength;
    private final String fieldStyle = "-fx-text-align: right; -fx-background-radius: 5; -fx-background-color: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-padding: 5 30 5 5; -fx-prompt-text-fill: #ffffff;";
    private LoginForm loginForm;

    public ForgotPasswordForm(Stage primaryStage, LoginForm loginForm) {
        this.primaryStage = primaryStage;
        this.loginForm = loginForm;
        createForgotPasswordScene();
    }

    private void createForgotPasswordScene() {
        // Background to very dark blue (close to navy)
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #3a5c79;");

        // Transparent panel to light panel with rounded corners
        VBox forgotBox = new VBox(5);
        forgotBox.setPadding(new Insets(30, 30, 30, 25)); // Adjust left padding to shift the title 5 pixels to the left
        forgotBox.setAlignment(Pos.CENTER);
        forgotBox.setStyle("-fx-background-color: rgba(175, 180, 204, 0.58); -fx-background-radius: 15;"); // Lighter and transparent background
        forgotBox.setPrefWidth(380); // Approximate width needed based on fields and padding
        forgotBox.setPrefHeight(390); // Approximate height needed based on content and element spacing
        forgotBox.setMaxWidth(380); // Limit maximum width
        forgotBox.setMaxHeight(390); // Limit maximum height

        // White title
        Label title = new Label("بازیابی رمز عبور");
        title.setFont(javafx.scene.text.Font.font("System", javafx.scene.text.FontWeight.BOLD, 24));
        title.setTextFill(javafx.scene.paint.Color.web("#ffffff"));

        // Add larger lock icon above the title
        ImageView largeLockIcon = new ImageView("https://img.icons8.com/ios-filled/48/ffffff/lock-2.png");
        largeLockIcon.setFitWidth(48);
        largeLockIcon.setFitHeight(48);

        // Create a container for title and first field with specific spacing
        VBox titleAndFirstField = new VBox(30);  // 30 pixels spacing between title and first field
        titleAndFirstField.setAlignment(Pos.CENTER);

        // Add the large lock icon and title to a container
        VBox titleContainer = new VBox(10);  // 10 pixels spacing between icon and title
        titleContainer.setAlignment(Pos.CENTER);
        titleContainer.getChildren().addAll(largeLockIcon, title);

        // Username field with icon
        HBox userBox = new HBox(10);
        userBox.setAlignment(Pos.CENTER);
        userBox.setPrefWidth(250);
        userBox.setPrefHeight(50);  // Height of username box
        ImageView userIcon = new ImageView("https://img.icons8.com/ios-filled/24/ffffff/user.png");
        userTextField = new TextField();
        userTextField.setPromptText("نام کاربری");
        userTextField.setPrefWidth(250);
        userTextField.setPrefHeight(50);  // Height of username field
        userTextField.setStyle("-fx-text-alignment: right; -fx-background-radius: 5; -fx-background-color:rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-prompt-text-fill: #ffffff;"); // Text field style
        userTextField.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        userBox.getChildren().setAll(userIcon, userTextField);

        // Add title and first field to their container
        titleAndFirstField.getChildren().addAll(titleContainer, userBox);

        // Create a container for fields with different spacing
        VBox fieldsContainer = new VBox(5);  // 5 pixels spacing between fields
        fieldsContainer.setAlignment(Pos.CENTER);

        // Email field with icon
        HBox emailBox = new HBox(10);
        emailBox.setAlignment(Pos.CENTER);
        emailBox.setPrefWidth(250);
        emailBox.setPrefHeight(50);  // Height of email box
        ImageView emailIcon = new ImageView("https://img.icons8.com/ios-filled/24/ffffff/mail.png");
        emailField = new TextField();
        emailField.setPromptText("ایمیل");
        emailField.setPrefWidth(250);
        emailField.setPrefHeight(50);  // Height of email field
        emailField.setStyle("-fx-text-alignment: right; -fx-background-radius: 5; -fx-background-color: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-prompt-text-fill: #ffffff;"); // Text field style
        emailField.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        emailBox.getChildren().setAll(emailIcon, emailField);
        emailBox.setAlignment(Pos.CENTER);

        // Error message
        usernameError = new Text();
        usernameError.setStyle("-fx-fill: #d13d3d; -fx-font-size: 11px;");
        emailError = new Text();
        emailError.setStyle("-fx-fill: #d13d3d; -fx-font-size: 11px;");

        // Add fields and errors to fields container
        fieldsContainer.getChildren().addAll(usernameError, emailBox, emailError);

        // Blue buttons with white text and rounded corners
        Button submitBtn = new Button(" بازیابی رمز عبور");
        submitBtn.setPrefWidth(270);
        submitBtn.setPrefHeight(50);  // Height of submit button
        submitBtn.setStyle("-fx-background-color: #52799b; -fx-text-fill: white; -fx-background-radius: 5;"); // Blue button color
        submitBtn.setOnMouseEntered(e -> submitBtn.setStyle("-fx-background-color: #52799b; -fx-text-fill: white; -fx-background-radius: 5; -fx-effect: dropshadow(gaussian, #9ac5ea, 10, 0.5, 0, 0);"));
        submitBtn.setOnMouseExited(e -> submitBtn.setStyle("-fx-background-color: #52799b; -fx-text-fill: white; -fx-background-radius: 5;"));
        Hyperlink backBtn = new Hyperlink("بازگشت به ورود");
        backBtn.setStyle("-fx-text-fill: #ffffff; -fx-underline: true;");
        backBtn.setOnMouseEntered(e -> backBtn.setStyle("-fx-text-fill: #ffffff; -fx-underline: true; -fx-effect: dropshadow(gaussian, #9ac5ea, 10, 0.5, 0, 0);"));
        backBtn.setOnMouseExited(e -> backBtn.setStyle("-fx-text-fill: #ffffff; -fx-underline: true;"));

        backBtn.setOnAction(e -> {
            if (getScene() != null && loginForm != null) {
                Stage stage = (Stage) getScene().getWindow();
                stage.setScene(loginForm.getScene());
                stage.setMaximized(true);
                stage.centerOnScreen();
            }
        });

        // Validation
        userTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() < 4) {
                usernameError.setText("نام کاربری باید حداقل 4 کاراکتر باشد"); // Username must be at least 4 characters
            } else if (!newValue.matches("[a-zA-Z0-9]+")) {
                usernameError.setText("نام کاربری فقط باید شامل حروف انگلیسی و اعداد باشد"); // Username must contain only English letters and numbers
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

        submitBtn.setOnAction(e -> {
            String username = userTextField.getText();
            String emailAddress = emailField.getText();
            if (!usernameError.getText().isEmpty() || !emailError.getText().isEmpty()) {
                statusMessage.setText("لطفاً خطاهای فرم را برطرف کنید.");
                statusMessage.setStyle("-fx-fill: #d13d3d;");
                return;
            }
            if (username.isEmpty() || emailAddress.isEmpty()) {
                statusMessage.setText("لطفاً تمام فیلدها را پر کنید.");
                statusMessage.setStyle("-fx-fill: #d13d3d;");
                return;
            }
            String resetToken = AuthService.FindUser(username, emailAddress);
            if (resetToken != null) {
                statusMessage.setText("کاربر یافت شد. به فرم تغییر رمز عبور هدایت می‌شوید.");
                statusMessage.setStyle("-fx-fill: #058a0a;");
                // Delay navigation slightly to show message
                javafx.animation.Timeline timeline = new javafx.animation.Timeline(new javafx.animation.KeyFrame(javafx.util.Duration.seconds(1), event -> {
                    showResetPasswordForm(resetToken);
                }));
                timeline.play();
            } else {
                statusMessage.setText("کاربری با این مشخصات یافت نشد.");
                statusMessage.setStyle("-fx-fill: #d13d3d;");
            }
        });

        VBox buttonsBox = new VBox(10, submitBtn, backBtn);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setPadding(new Insets(0, 0, 0, 25)); // Left padding

        // Status message (error/success)
        statusMessage = new Text();
        statusMessage.setStyle("-fx-font-size: 12px;");

        // Add everything to the glass panel
        forgotBox.getChildren().setAll(titleAndFirstField, fieldsContainer, buttonsBox, statusMessage);
        forgotBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        // Add everything to the root
        root.getChildren().setAll(forgotBox);
        StackPane.setAlignment(forgotBox, Pos.CENTER);
        scene = new Scene(root, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        primaryStage.setMaximized(true);
        primaryStage.setX(0);
        primaryStage.setY(0);
    }

    private void showResetPasswordForm(String resetToken) {
        // Background to very dark blue (close to navy)
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #3a5c79;");

        // Transparent panel to light panel with rounded corners
        VBox resetBox = new VBox(15); // Spacing in main VBox
        resetBox.setPadding(new Insets(30, 30, 30, 30));
        resetBox.setAlignment(Pos.CENTER);
        resetBox.setStyle("-fx-background-color: rgba(175, 180, 204, 0.58); -fx-background-radius: 15;"); // Lighter and transparent background
        resetBox.setPrefWidth(380); // Approximate width needed based on fields and padding
        resetBox.setPrefHeight(400); // Approximate height needed based on content and element spacing
        resetBox.setMaxWidth(380); // Limit maximum width
        resetBox.setMaxHeight(400); // Limit maximum height

        // White title
        Label title = new Label("تغییر رمز عبور");
        title.setFont(javafx.scene.text.Font.font("System", javafx.scene.text.FontWeight.BOLD, 24));
        title.setTextFill(javafx.scene.paint.Color.web("#ffffff"));

        // Add larger lock icon above the title
        ImageView largeLockIcon = new ImageView("https://img.icons8.com/ios-filled/48/ffffff/lock-2.png");
        largeLockIcon.setFitWidth(48);
        largeLockIcon.setFitHeight(48);

        // Create a container for the title and icon
        VBox titleContainer = new VBox(10);  // 10 pixels spacing between icon and title
        titleContainer.setAlignment(Pos.CENTER);
        titleContainer.getChildren().addAll(largeLockIcon, title);

        // New password field with icon and eye
        HBox newPassBox = new HBox(10);
        newPassBox.setAlignment(Pos.CENTER);
        newPassBox.setPrefWidth(250);
        newPassBox.setPrefHeight(50);
        ImageView passIcon = new ImageView("https://img.icons8.com/ios-filled/24/ffffff/lock-2.png");
        PasswordField newPassword = new PasswordField();
        TextField newPasswordVisible = new TextField();
        newPassword.setPromptText("رمز عبور جدید");
        newPasswordVisible.setPromptText("رمز عبور جدید");
        newPassword.setPrefWidth(250);
        newPassword.setPrefHeight(50);
        newPasswordVisible.setPrefWidth(250);
        newPasswordVisible.setPrefHeight(50);
        newPasswordVisible.setVisible(false);
        ToggleButton newPassToggle = new ToggleButton("👁");
        newPassToggle.setStyle("-fx-font-size: 14px; -fx-background-color: transparent; -fx-padding: 0 5 0 0;");
        newPassword.setStyle(fieldStyle);
        newPasswordVisible.setStyle(fieldStyle);
        newPassword.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        newPasswordVisible.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        // Create StackPane to place eye button inside the field
        StackPane newPasswordStack = new StackPane();
        newPasswordStack.setPrefWidth(250);
        newPasswordStack.setPrefHeight(newPassword.getPrefHeight());
        newPasswordStack.getChildren().addAll(newPassword, newPasswordVisible);
        StackPane.setAlignment(newPassToggle, Pos.CENTER_RIGHT);
        StackPane.setMargin(newPassToggle, new Insets(0, 5, 0, 0));

        // Add the toggle button to the StackPane
        newPasswordStack.getChildren().add(newPassToggle);
        newPassBox.getChildren().setAll(passIcon, newPasswordStack);

        // Error messages
        passwordStrength = new Text();
        passwordStrength.setStyle("-fx-fill: #d13d3d; -fx-font-size: 11px;");
        passwordStrength.setVisible(true);
        HBox passwordStrengthBox = new HBox();
        passwordStrengthBox.setAlignment(Pos.CENTER);
        passwordStrengthBox.getChildren().add(passwordStrength);

        // Confirm password field with icon and eye
        HBox confirmPassBox = new HBox(10);
        confirmPassBox.setAlignment(Pos.CENTER);
        confirmPassBox.setPrefWidth(250);
        confirmPassBox.setPrefHeight(50);
        ImageView confirmIcon = new ImageView("https://img.icons8.com/ios-filled/24/ffffff/lock-2.png");
        confirmPassword = new PasswordField();
        confirmPasswordVisible = new TextField();
        confirmPassword.setPromptText("تکرار رمز عبور جدید");
        confirmPasswordVisible.setPromptText("تکرار رمز عبور جدید");
        confirmPassword.setPrefWidth(250);
        confirmPassword.setPrefHeight(50);
        confirmPasswordVisible.setPrefWidth(250);
        confirmPasswordVisible.setPrefHeight(50);
        confirmPasswordVisible.setVisible(false);
        ToggleButton confirmPassToggle = new ToggleButton("👁");
        confirmPassToggle.setStyle("-fx-font-size: 14px; -fx-background-color: transparent; -fx-padding: 0 5 0 0;");
        confirmPassword.setStyle(fieldStyle);
        confirmPasswordVisible.setStyle(fieldStyle);
        confirmPassword.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        confirmPasswordVisible.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        // Create StackPane to place eye button inside the field
        StackPane confirmPasswordStack = new StackPane();
        confirmPasswordStack.setPrefWidth(250);
        confirmPasswordStack.setPrefHeight(confirmPassword.getPrefHeight());
        confirmPasswordStack.getChildren().addAll(confirmPassword, confirmPasswordVisible);
        StackPane.setAlignment(confirmPassToggle, Pos.CENTER_RIGHT);
        StackPane.setMargin(confirmPassToggle, new Insets(0, 5, 0, 0));

        // Add the toggle button to the StackPane
        confirmPasswordStack.getChildren().add(confirmPassToggle);
        confirmPassBox.getChildren().setAll(confirmIcon, confirmPasswordStack);

        // Error messages
        confirmPwError = new Text();
        confirmPwError.setStyle("-fx-fill: #d13d3d; -fx-font-size: 11px;");
        confirmPwError.setVisible(true);
        HBox confirmPwErrorBox = new HBox();
        confirmPwErrorBox.setAlignment(Pos.CENTER);
        confirmPwErrorBox.getChildren().add(confirmPwError);

        // Add real-time validation listeners
        newPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            validatePassword(newValue, passwordStrength, confirmPassword, confirmPwError);
        });

        newPasswordVisible.textProperty().addListener((observable, oldValue, newValue) -> {
            validatePassword(newValue, passwordStrength, confirmPassword, confirmPwError);
        });

        confirmPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            String mainPassword = newPassword.isVisible() ? newPassword.getText() : newPasswordVisible.getText();
            validateConfirmPassword(newValue, mainPassword, confirmPwError);
        });

        confirmPasswordVisible.textProperty().addListener((observable, oldValue, newValue) -> {
            String mainPassword = newPassword.isVisible() ? newPassword.getText() : newPasswordVisible.getText();
            validateConfirmPassword(newValue, mainPassword, confirmPwError);
        });

        // Group field groups with 5px spacing between them
        VBox fieldsContainer = new VBox(5);
        fieldsContainer.setAlignment(Pos.CENTER);
        fieldsContainer.getChildren().addAll(newPassBox, passwordStrengthBox, confirmPassBox, confirmPwErrorBox);

        // Blue submit button with white text and rounded corners
        Button submitBtn = new Button("ثبت");
        submitBtn.setPrefWidth(270);
        submitBtn.setPrefHeight(60);
        submitBtn.setStyle("-fx-background-color: #52799b; -fx-text-fill: white; -fx-background-radius: 5;"); // Blue button color
        submitBtn.setOnMouseEntered(e -> submitBtn.setStyle("-fx-background-color: #52799b; -fx-text-fill: white; -fx-background-radius: 5; -fx-effect: dropshadow(gaussian, #9ac5ea, 10, 0.5, 0, 0);"));
        submitBtn.setOnMouseExited(e -> submitBtn.setStyle("-fx-background-color: #52799b; -fx-text-fill: white; -fx-background-radius: 5;"));

        // Submit button action
        submitBtn.setOnAction(e -> {
            String password = newPassword.getText();
            String confirm = confirmPassword.getText();

            if (newPasswordVisible.isVisible()) {
                password = newPasswordVisible.getText();
            }
            if (confirmPasswordVisible.isVisible()) {
                confirm = confirmPasswordVisible.getText();
            }

            // Check validation errors and empty fields
            if (confirmPwError.getText().isEmpty() &&
                    !password.isEmpty() && !confirm.isEmpty()) {
                // If no errors, proceed with password reset
                if (ForgetPassword.rest(resetToken, password, confirm)) {
                    resetStatusMessage.setText("رمز عبور با موفقیت تغییر کرد. در حال انتقال به صفحه ورود...");
                    resetStatusMessage.setStyle("-fx-fill: #058a0a; -fx-font-size: 14px;");
                    resetStatusMessage.setVisible(true);
                    // Delay navigation slightly to show message
                    javafx.animation.Timeline timeline = new javafx.animation.Timeline(new javafx.animation.KeyFrame(javafx.util.Duration.seconds(2), event -> {
                        LoginForm loginForm = new LoginForm(primaryStage);
                        primaryStage.setScene(loginForm.getScene());
                        primaryStage.setMaximized(true);
                        primaryStage.centerOnScreen();
                    }));
                    timeline.play();
                } else {
                    resetStatusMessage.setText("خطا در تغییر رمز عبور. لطفاً دوباره تلاش کنید.");
                    resetStatusMessage.setStyle("-fx-fill: #d13d3d; -fx-font-size: 14px;");
                    resetStatusMessage.setVisible(true);
                }
            } else {
                resetStatusMessage.setText("لطفاً خطاهای فرم را برطرف کنید.");
                resetStatusMessage.setStyle("-fx-fill: #d13d3d; -fx-font-size: 14px;");
                resetStatusMessage.setVisible(true);
            }
        });

        // Back button to previous forgot password form
        Hyperlink backToForgotPassword = new Hyperlink("بازگشت");
        backToForgotPassword.setStyle("-fx-text-fill: #ffffff; -fx-underline: true;");
        backToForgotPassword.setOnMouseEntered(e -> backToForgotPassword.setStyle("-fx-text-fill: #ffffff; -fx-underline: true; -fx-effect: dropshadow(gaussian, #9ac5ea, 10, 0.5, 0, 0);"));
        backToForgotPassword.setOnMouseExited(e -> backToForgotPassword.setStyle("-fx-text-fill: #ffffff; -fx-underline: true;"));

        backToForgotPassword.setOnAction(e -> {
            view.Authnticate.ForgotPasswordForm forgotPasswordForm = new view.Authnticate.ForgotPasswordForm(primaryStage, loginForm);
            primaryStage.setScene(forgotPasswordForm.getScene());
            primaryStage.setMaximized(true);
            primaryStage.centerOnScreen();
        });

        // Adding elements to the panel
        VBox buttonsBox = new VBox(10); // Spacing between buttons
        buttonsBox.getChildren().addAll(submitBtn, backToForgotPassword);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setPadding(new Insets(0, 0, 0, 25)); // Left padding

        // Status message (error/success) in the reset password form
        resetStatusMessage = new Text();
        resetStatusMessage.setStyle("-fx-font-size: 12px;");
        resetStatusMessage.setVisible(true);
        HBox statusMessageBox = new HBox();
        statusMessageBox.setAlignment(Pos.CENTER);
        statusMessageBox.getChildren().add(resetStatusMessage);

        // Add all components to the main resetBox
        resetBox.getChildren().setAll(titleContainer, fieldsContainer, buttonsBox, statusMessageBox);
        resetBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        root.getChildren().setAll(resetBox);
        StackPane.setAlignment(resetBox, Pos.CENTER);

        // Eye behavior
        newPassToggle.setOnAction(e -> {
            if (newPassToggle.isSelected()) {
                newPasswordVisible.setText(newPassword.getText());
                newPasswordVisible.setVisible(true);
                newPassword.setVisible(false);
            } else {
                newPassword.setText(newPasswordVisible.getText());
                newPassword.setVisible(true);
                newPasswordVisible.setVisible(false);
            }
        });

        confirmPassToggle.setOnAction(e -> {
            if (confirmPassToggle.isSelected()) {
                confirmPasswordVisible.setText(confirmPassword.getText());
                confirmPasswordVisible.setVisible(true);
                confirmPassword.setVisible(false);
            } else {
                confirmPassword.setText(confirmPasswordVisible.getText());
                confirmPassword.setVisible(true);
                confirmPasswordVisible.setVisible(false);
            }
        });

        // Sync values
        newPassword.textProperty().bindBidirectional(newPasswordVisible.textProperty());
        confirmPassword.textProperty().bindBidirectional(confirmPasswordVisible.textProperty());

        // Set the scene
        Scene resetScene = new Scene(root, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        primaryStage.setScene(resetScene);
        primaryStage.setTitle("تغییر رمز عبور");
        primaryStage.setMaximized(true);
        primaryStage.setX(0);
        primaryStage.setY(0);
    }

    private void validatePassword(String password, Text passwordStrength, PasswordField confirmPassword, Text confirmPwError) {
        boolean hasLetter = password.matches(".*[a-zA-Z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");

        if (password.isEmpty()) {
            passwordStrength.setText("رمز عبور نمی‌تواند خالی باشد");
            passwordStrength.setStyle("-fx-fill: #d13d3d; -fx-font-size: 11px;");
            passwordStrength.setVisible(true);
        } else if (password.length() < 8) {
            passwordStrength.setText("رمز عبور باید حداقل 8 کاراکتر باشد");
            passwordStrength.setStyle("-fx-fill: #d13d3d; -fx-font-size: 11px;");
            passwordStrength.setVisible(true);
        } else if ((hasLetter && !hasDigit && !hasSpecial) || (!hasLetter && hasDigit && !hasSpecial) || (!hasLetter && !hasDigit && hasSpecial)) {
            passwordStrength.setText("قدرت رمز: ضعیف");
            passwordStrength.setStyle("-fx-fill: #d13d3d; -fx-font-size: 11px;");
            passwordStrength.setVisible(true);
        } else if ((hasLetter && hasDigit && !hasSpecial) || (hasLetter && !hasDigit && hasSpecial) || (!hasLetter && hasDigit && hasSpecial)) {
            passwordStrength.setText("قدرت رمز: متوسط");
            passwordStrength.setStyle("-fx-fill: #dcd834; -fx-font-size: 11px;");
            passwordStrength.setVisible(true);
        } else if (hasLetter && hasDigit && hasSpecial) {
            passwordStrength.setText("قدرت رمز: قوی");
            passwordStrength.setStyle("-fx-fill: #058a0a; -fx-font-size: 11px;");
            passwordStrength.setVisible(true);
        } else {
            passwordStrength.setText("");
            passwordStrength.setVisible(false);
        }

        // Validate confirm password whenever password changes
        if (!confirmPassword.getText().isEmpty()) {
            validateConfirmPassword(confirmPassword.getText(), password, confirmPwError);
        }
    }

    private void validateConfirmPassword(String confirmPassword, String password, Text confirmPwError) {
        if (confirmPassword.isEmpty()) {
            confirmPwError.setText("تکرار رمز عبور نمی‌تواند خالی باشد");
            confirmPwError.setVisible(true);
        } else if (!confirmPassword.equals(password)) {
            confirmPwError.setText("رمز عبور و تکرار آن یکسان نیستند");
            confirmPwError.setVisible(true);
        } else {
            confirmPwError.setText("");
            confirmPwError.setVisible(false);
        }
    }

    public Scene getScene() {
        return scene;
    }
}