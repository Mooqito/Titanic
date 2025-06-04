package view;
import controller.Authnticate.SingUp;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import runner.Main;

public class SignUpForm {
    private Scene scene;
    private Stage primaryStage;
    private Text passwordStrengthText;
    private Text usernameError;
    private Text emailError;
    private Text passwordError;
    private Text confirmPwError;
    private TextField userTextField;
    private TextField emailField;
    private PasswordField pwBox;
    private PasswordField confirmPwBox;
    private TextField visiblePasswordField;
    private TextField visibleConfirmPasswordField;
    private Text statusMessage;
    private final String fieldStyle = "-fx-text-align: right; -fx-background-radius: 5; -fx-background-color: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-padding: 5 30 5 5; -fx-prompt-text-fill: #ffffff;";
    private final String errorStyle = "-fx-fill: #d13d3d; -fx-font-size: 11px;";
    private final String successStyle = "-fx-fill: #058a0a; -fx-font-size: 11px;";
    private final String warningStyle = "-fx-fill: #dcd834; -fx-font-size: 11px;";

    public SignUpForm(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createRegisterScene();
    }
    private void createRegisterScene() {
        // Background to very dark blue (close to navy)
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #3a5c79;");

        // Transparent panel to light panel with rounded corners
        VBox registerBox = new VBox(5);
        registerBox.setPadding(new Insets(30, 30, 30, 20)); // Adjust left padding to shift the title 10 pixels to the left (5 more pixels)
        registerBox.setAlignment(Pos.CENTER);
        registerBox.setStyle("-fx-background-color: rgba(175, 180, 204, 0.58); -fx-background-radius: 15;"); // Lighter and transparent background
        registerBox.setPrefWidth(380); // Approximate width needed based on fields and padding
        registerBox.setPrefHeight(550); // Approximate height needed based on content and element spacing
        registerBox.setMaxWidth(380); // Limit maximum width
        registerBox.setMaxHeight(550); // Limit maximum height

        // Add logo image
        ImageView logo = new ImageView(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/Picture2.png")));
        logo.setFitWidth(100);
        logo.setFitHeight(100);
        logo.setPreserveRatio(true);

        // White title
        Label title = new Label("ثبت نام");
        title.setFont(javafx.scene.text.Font.font("System", javafx.scene.text.FontWeight.BOLD, 24));
        title.setTextFill(javafx.scene.paint.Color.web("#ffffff"));

        // Username field with icon
        HBox userBox = new HBox(10);
        userBox.setAlignment(Pos.CENTER);
        userBox.setPrefWidth(250);
        userBox.setPrefHeight(50);  // Height of username box
        javafx.scene.image.ImageView userIcon = new javafx.scene.image.ImageView("https://img.icons8.com/ios-filled/24/ffffff/user.png");
        userTextField = new TextField();
        userTextField.setPromptText("نام کاربری");
        userTextField.setPrefWidth(250);
        userTextField.setPrefHeight(50);  // Height of username field
        userTextField.setStyle("-fx-text-align: right; -fx-background-radius: 5; -fx-background-color: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-prompt-text-fill: #ffffff;"); // Text field style
        userTextField.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        userBox.getChildren().setAll(userIcon, userTextField);

        // Email field with icon
        HBox emailBox = new HBox(10);
        emailBox.setAlignment(Pos.CENTER);
        emailBox.setPrefWidth(250);
        emailBox.setPrefHeight(50);  // Height of email box
        javafx.scene.image.ImageView emailIcon = new javafx.scene.image.ImageView("https://img.icons8.com/ios-filled/24/ffffff/new-post.png");
        emailField = new TextField();
        emailField.setPromptText("ایمیل");
        emailField.setPrefWidth(250);
        emailField.setPrefHeight(50);  // Height of email field
        emailField.setStyle("-fx-text-align: right; -fx-background-radius: 5; -fx-background-color: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-prompt-text-fill: #ffffff;"); // Text field style
        emailField.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        emailBox.getChildren().setAll(emailIcon, emailField);

        // Password field with icon and eye
        HBox passBox = new HBox(10);
        passBox.setAlignment(Pos.CENTER);
        passBox.setPrefWidth(250);
        passBox.setPrefHeight(50);  // Height of password box
        ImageView passIcon = new ImageView("https://img.icons8.com/ios-filled/24/ffffff/lock-2.png");
        pwBox = new PasswordField();
        visiblePasswordField = new TextField();
        pwBox.setPromptText("رمز عبور");
        visiblePasswordField.setPromptText("رمز عبور");
        pwBox.setPrefWidth(250);
        pwBox.setPrefHeight(50);  // Height of password field
        visiblePasswordField.setPrefWidth(250);
        visiblePasswordField.setPrefHeight(50);  // Height of visible password field
        visiblePasswordField.setVisible(false);
        ToggleButton passToggle = new ToggleButton("👁");
        passToggle.setStyle("-fx-font-size: 14px; -fx-background-color: transparent; -fx-padding: 0 5 0 0;");
        pwBox.setStyle(fieldStyle);
        visiblePasswordField.setStyle(fieldStyle);
        pwBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        visiblePasswordField.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        // Define password strength message
        Text passwordStrength = new Text();
        passwordStrength.setStyle("-fx-font-size: 11px;");

        // Real-time password validation and strength display
        pwBox.textProperty().addListener((observable, oldValue, newValue) -> {
            checkPasswordStrength(newValue, passwordStrength);
            validateConfirmPassword(newValue, confirmPwBox.getText(), confirmPwError);
        });

        // Create StackPane to place eye button inside the field
        StackPane passwordStack = new StackPane();
        passwordStack.setPrefWidth(250);
        passwordStack.setPrefHeight(40);  // Height of password stack pane
        passwordStack.getChildren().addAll(pwBox, visiblePasswordField);
        StackPane.setAlignment(passToggle, Pos.CENTER_RIGHT);
        StackPane.setMargin(passToggle, new Insets(0, 5, 0, 0));

        // Add the toggle button to the StackPane
        passwordStack.getChildren().add(passToggle);

        // Confirm password field with icon and eye
        HBox confirmPassBoxHBox = new HBox(10);
        confirmPassBoxHBox.setAlignment(Pos.CENTER);
        confirmPassBoxHBox.setPrefWidth(250);
        confirmPassBoxHBox.setPrefHeight(50);  // Height of confirm password box
        ImageView confirmIcon = new ImageView("https://img.icons8.com/ios-filled/24/ffffff/lock-2.png");
        confirmPwBox = new PasswordField();
        visibleConfirmPasswordField = new TextField();
        confirmPwBox.setPromptText("تایید رمز عبور");
        visibleConfirmPasswordField.setPromptText("تایید رمز عبور");
        confirmPwBox.setPrefWidth(250);
        confirmPwBox.setPrefHeight(50);  // Height of confirm password field
        visibleConfirmPasswordField.setPrefWidth(250);
        visibleConfirmPasswordField.setPrefHeight(50);  // Height of visible confirm password field
        visibleConfirmPasswordField.setVisible(false);
        ToggleButton confirmToggle = new ToggleButton("👁");
        confirmToggle.setStyle("-fx-font-size: 14px; -fx-background-color: transparent; -fx-padding: 0 5 0 0;");
        confirmPwBox.setStyle(fieldStyle);
        visibleConfirmPasswordField.setStyle(fieldStyle);
        confirmPwBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        visibleConfirmPasswordField.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        // Create StackPane to place eye button inside the field
        StackPane confirmPasswordStack = new StackPane();
        confirmPasswordStack.setPrefWidth(250);
        confirmPasswordStack.setPrefHeight(40);  // Height of confirm password stack pane
        confirmPasswordStack.getChildren().addAll(confirmPwBox, visibleConfirmPasswordField);
        StackPane.setAlignment(confirmToggle, Pos.CENTER_RIGHT);
        StackPane.setMargin(confirmToggle, new Insets(0, 5, 0, 0));

        // Add the toggle button to the StackPane
        confirmPasswordStack.getChildren().add(confirmToggle);

        passBox.getChildren().setAll(passIcon, passwordStack);
        confirmPassBoxHBox.getChildren().setAll(confirmIcon, confirmPasswordStack);

        // Eye behavior
        passToggle.setOnAction(e -> {
            if (passToggle.isSelected()) {
                visiblePasswordField.setText(pwBox.getText());
                visiblePasswordField.setVisible(true);
                pwBox.setVisible(false);
            } else {
                pwBox.setText(visiblePasswordField.getText());
                pwBox.setVisible(true);
                visiblePasswordField.setVisible(false);
            }
        });

        confirmToggle.setOnAction(e -> {
            if (confirmToggle.isSelected()) {
                visibleConfirmPasswordField.setText(confirmPwBox.getText());
                visibleConfirmPasswordField.setVisible(true);
                confirmPwBox.setVisible(false);
            } else {
                confirmPwBox.setText(visibleConfirmPasswordField.getText());
                confirmPwBox.setVisible(true);
                visibleConfirmPasswordField.setVisible(false);
            }
        });

        // Sync values
        pwBox.textProperty().bindBidirectional(visiblePasswordField.textProperty());
        confirmPwBox.textProperty().bindBidirectional(visibleConfirmPasswordField.textProperty());

        // Blue signup button with white text and rounded corners
        Button registerBtn = new Button("ثبت نام");
        registerBtn.setPrefWidth(270);
        registerBtn.setPrefHeight(50);  // Height of sign up button
        registerBtn.setStyle("-fx-background-color: #52799b; -fx-text-fill: white; -fx-background-radius: 5;"); // Blue button color

        // Signup button action
        registerBtn.setOnAction(e -> {
            String username = userTextField.getText();
            String emailAddress = emailField.getText();
            String password = pwBox.getText();
            String confirmPassword = confirmPwBox.getText();

            // Use the visible text fields if they are visible
            if (visiblePasswordField != null && visiblePasswordField.isVisible()) {
                password = visiblePasswordField.getText();
            }
            if (visibleConfirmPasswordField != null && visibleConfirmPasswordField.isVisible()) {
                confirmPassword = visibleConfirmPasswordField.getText();
            }

            // Check validation errors and empty fields
            if (!usernameError.getText().isEmpty() ||
                    !emailError.getText().isEmpty() ||
                    !passwordError.getText().isEmpty() ||
                    !confirmPwError.getText().isEmpty() ||
                    username.isEmpty() ||
                    emailAddress.isEmpty() ||
                    password.isEmpty() ||
                    confirmPassword.isEmpty()) {
                statusMessage.setText("لطفاً تمام فیلدها را پر کنید.");
                statusMessage.setStyle("-fx-fill: #d13d3d;");
                return;
            }

            // Call backend signup method
            if (SingUp.sing_up(username, password, confirmPassword, emailAddress)) {
                statusMessage.setText("ثبت نام با موفقیت انجام شد!");
                statusMessage.setStyle("-fx-fill: #058a0a;");
                // Delay navigation slightly to show message
                javafx.animation.Timeline timeline = new javafx.animation.Timeline(new javafx.animation.KeyFrame(javafx.util.Duration.seconds(1.5), event -> {
                    DashboardForm dashboardForm = new DashboardForm(primaryStage);
                    primaryStage.setScene(dashboardForm.getScene());
                }));
                timeline.play();
            } else {
                statusMessage.setText("کاربر با این مشخصات موجود است.");
                statusMessage.setStyle("-fx-fill: #d13d3d;");
            }
        });

        // Back button to login
        Hyperlink backToLogin = new Hyperlink("بازگشت به ورود");
        backToLogin.setStyle("-fx-text-fill: white; -fx-underline: true;");

        backToLogin.setOnAction(e -> {
            LoginForm loginForm = new LoginForm(primaryStage);
            primaryStage.setScene(loginForm.getScene());
            primaryStage.setMaximized(true);
            primaryStage.centerOnScreen();
        });

        // Real-time error definitions
        usernameError = new Text("");
        usernameError.setStyle(errorStyle);
        usernameError.setWrappingWidth(250);
        usernameError.setTextAlignment(TextAlignment.CENTER);

        emailError = new Text("");
        emailError.setStyle(errorStyle);
        emailError.setWrappingWidth(250);
        emailError.setTextAlignment(TextAlignment.CENTER);

        passwordError = new Text("");
        passwordError.setStyle(errorStyle);
        passwordError.setWrappingWidth(250);
        passwordError.setTextAlignment(TextAlignment.CENTER);

        confirmPwError = new Text("");
        confirmPwError.setStyle(errorStyle);
        confirmPwError.setWrappingWidth(250);
        confirmPwError.setTextAlignment(TextAlignment.CENTER);

        // Real-time username validation
        userTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() < 4) {
                usernameError.setText("نام کاربری باید حداقل 4 کاراکتر باشد"); // Username must be at least 4 characters
            } else if (!newValue.matches("[a-zA-Z0-9]+")) {
                usernameError.setText("نام کاربری فقط باید شامل حروف انگلیسی و اعداد باشد"); // Username must contain only English letters and numbers
            } else {
                usernameError.setText("");
            }
        });
        // Real-time email validation
        emailField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.endsWith("@gmail.com")) {
                emailError.setText("ایمیل باید با @gmail.com تمام شود");
            } else {
                emailError.setText("");
            }
        });
        // Real-time confirm password validation
        confirmPwBox.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(pwBox.getText())) {
                confirmPwError.setText("رمز عبور و تکرار آن یکسان نیستند");
            } else {
                confirmPwError.setText("");
            }
        });

        // Adding errors to the panel
        VBox buttonsBox = new VBox(10, registerBtn, backToLogin);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setPadding(new Insets(0, 0, 0, 25)); // پدینگ از سمت چپ

        // Status message (error/success)
        statusMessage = new Text();
        statusMessage.setStyle("-fx-font-size: 12px;");

        // Grouping fields with their respective errors
        VBox userFieldGroup = new VBox(5);
        userFieldGroup.setAlignment(Pos.CENTER);
        StackPane usernameErrorPane = new StackPane();
        usernameErrorPane.setAlignment(Pos.CENTER);
        usernameErrorPane.getChildren().add(usernameError);
        userFieldGroup.getChildren().addAll(userBox, usernameErrorPane);

        VBox emailFieldGroup = new VBox(5);
        emailFieldGroup.setAlignment(Pos.CENTER);
        StackPane emailErrorPane = new StackPane();
        emailErrorPane.setAlignment(Pos.CENTER);
        emailErrorPane.getChildren().add(emailError);
        emailFieldGroup.getChildren().addAll(emailBox, emailErrorPane);

        VBox passwordFieldGroup = new VBox(5);
        passwordFieldGroup.setAlignment(Pos.CENTER);
        StackPane passwordStrengthPane = new StackPane();
        passwordStrengthPane.setAlignment(Pos.CENTER);
        passwordStrengthPane.getChildren().add(passwordStrength);
        passwordFieldGroup.getChildren().addAll(passBox, passwordStrengthPane);

        VBox confirmPasswordFieldGroup = new VBox(5);
        confirmPasswordFieldGroup.setAlignment(Pos.CENTER);
        StackPane confirmPwErrorPane = new StackPane();
        confirmPwErrorPane.setAlignment(Pos.CENTER);
        confirmPwErrorPane.getChildren().add(confirmPwError);
        confirmPasswordFieldGroup.getChildren().addAll(confirmPassBoxHBox, confirmPwErrorPane);

        registerBox.getChildren().setAll(logo, title, userFieldGroup, emailFieldGroup, passwordFieldGroup, confirmPasswordFieldGroup, buttonsBox, statusMessage);
        registerBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        root.getChildren().setAll(registerBox);
        StackPane.setAlignment(registerBox, Pos.CENTER);
        scene = new Scene(root, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        primaryStage.setMaximized(true);
        primaryStage.setX(0);
        primaryStage.setY(0);
    }

    private void checkPasswordStrength(String password, Text passwordStrength) {
        boolean hasLetter = password.matches(".*[a-zA-Z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");

        if (password.isEmpty()) {
            passwordStrength.setText("رمز عبور نمی‌تواند خالی باشد");
            passwordStrength.setStyle(errorStyle);
            return;
        }

        if (password.length() < 8) {
            passwordStrength.setText("رمز عبور باید حداقل 8 کاراکتر باشد");
            passwordStrength.setStyle(errorStyle);
        } else if ((hasLetter && !hasDigit && !hasSpecial) || (!hasLetter && hasDigit && !hasSpecial) || (!hasLetter && !hasDigit && hasSpecial)) {
            passwordStrength.setText("قدرت رمز: ضعیف");
            passwordStrength.setStyle(errorStyle);
        } else if ((hasLetter && hasDigit && !hasSpecial) || (hasLetter && !hasDigit && hasSpecial) || (!hasLetter && hasDigit && hasSpecial)) {
            passwordStrength.setText("قدرت رمز: متوسط");
            passwordStrength.setStyle(warningStyle);
        } else if (hasLetter && hasDigit && hasSpecial) {
            passwordStrength.setText("قدرت رمز: قوی");
            passwordStrength.setStyle(successStyle);
        } else {
            passwordStrength.setText("");
        }
    }

    private void validateConfirmPassword(String password, String confirmPassword, Text confirmPwError) {
        if (confirmPassword.isEmpty()) {
            confirmPwError.setText("تکرار رمز عبور نمی‌تواند خالی باشد");
        } else if (!confirmPassword.equals(password)) {
            confirmPwError.setText("رمز عبور و تکرار آن یکسان نیستند");
        } else {
            confirmPwError.setText("");
        }
    }

    public Scene getScene() {
        return scene;
    }
}