package view.Authnticate;
import controller.Authnticate.Login;
import javafx.application.Platform;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import runner.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Screen;
import view.menu.DashboardForm;

public class LoginForm {
    private Scene scene;
    private Stage primaryStage;
    private TextField visiblePasswordField;
    private Text usernameError;
    private Text passwordError;
    private TextField username;
    private PasswordField password;
    private ToggleButton passToggle;
    private TextField visiblePasswordFieldLogin;
    private Text statusMessage;
    private SignUpForm signUpForm;
    private ForgotPasswordForm forgotPasswordForm;
    private DashboardForm dashboardForm;

    public LoginForm(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createLoginScene();
        signUpForm = new SignUpForm(primaryStage, this);
        forgotPasswordForm = new ForgotPasswordForm(primaryStage, this);
        dashboardForm = new DashboardForm(primaryStage, this);
    }

    private void createLoginScene() {
        // Background to very dark blue (close to navy)
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #3a5c79;");

        // Transparent panel to light panel with rounded corners
        VBox loginBox = new VBox(30);
        loginBox.setPadding(new Insets(30, 30, 30, 30)); // Reset padding
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setStyle("-fx-background-color: rgba(175, 180, 204, 0.58); -fx-background-radius: 15;"); // Lighter and transparent background
        loginBox.setPrefWidth(380); // Approximate width needed based on fields and padding
        loginBox.setPrefHeight(510); // Approximate height needed based on content and element spacing
        loginBox.setMaxWidth(380); // Limit maximum width
        loginBox.setMaxHeight(510); // Limit maximum height

        // Add logo image
        ImageView logo = new ImageView(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/Lamp.png")));
        logo.setFitWidth(100);
        logo.setFitHeight(100);
        logo.setPreserveRatio(true);

        // White title
        Label title = new Label("فروشگاه لوازم الکتریکی");
        title.setFont(javafx.scene.text.Font.font("System", javafx.scene.text.FontWeight.BOLD, 24));
        title.setTextFill(javafx.scene.paint.Color.web("#ffffff"));

        // Create a container for logo and title with specific spacing
        VBox headerBox = new VBox(10);  // 10 pixels spacing between logo and title
        headerBox.setAlignment(Pos.CENTER);
        headerBox.getChildren().addAll(logo, title);

        // Username field with icon
        HBox userBox = new HBox(10);
        userBox.setAlignment(Pos.CENTER);
        userBox.setPrefWidth(250);
        userBox.setPrefHeight(50);  // Height of username box
        ImageView userIcon = new ImageView("https://img.icons8.com/ios-filled/24/ffffff/user.png");
        username = new TextField();
        username.setPromptText("نام کاربری");
        username.setPrefWidth(250);
        username.setPrefHeight(50);  // Height of username field
        username.setStyle("-fx-text-alignment: right; -fx-background-radius: 5; -fx-background-color: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-prompt-text-fill: #ffffff;"); // Text field style
        username.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        userBox.getChildren().setAll(userIcon, username);
        userBox.setAlignment(Pos.CENTER);

        // Password field with icon and eye
        HBox passBox = new HBox(10);
        passBox.setAlignment(Pos.CENTER);
        passBox.setPrefWidth(250);
        passBox.setPrefHeight(50);  // Height of password box
        ImageView passIcon = new ImageView("https://img.icons8.com/ios-filled/24/ffffff/lock-2.png");
        password = new PasswordField();
        visiblePasswordFieldLogin = new TextField();
        password.setPromptText("رمز عبور");
        visiblePasswordFieldLogin.setPromptText("رمز عبور");
        password.setPrefWidth(250);
        password.setPrefHeight(50);  // Height of password field
        visiblePasswordFieldLogin.setPrefWidth(250);
        visiblePasswordFieldLogin.setPrefHeight(50);  // Height of visible password field
        visiblePasswordFieldLogin.setVisible(false);
        ToggleButton passToggle = new ToggleButton("👁");
        passToggle.setStyle("-fx-font-size: 14px; -fx-background-color: transparent; -fx-padding: 0 5 0 0;");
        String fieldStyle = "-fx-text-align: right; -fx-background-radius: 5; -fx-background-color: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-padding: 5 30 5 5; -fx-prompt-text-fill: #ffffff;"; // Text field and password field style
        password.setStyle(fieldStyle);
        visiblePasswordFieldLogin.setStyle(fieldStyle);
        password.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        visiblePasswordFieldLogin.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        // Create StackPane to place eye button inside the field
        StackPane passwordStack = new StackPane();
        passwordStack.setPrefWidth(250);
        passwordStack.setPrefHeight(40);  // Height of password stack pane
        passwordStack.getChildren().addAll(password, visiblePasswordFieldLogin);
        StackPane.setAlignment(passToggle, Pos.CENTER_RIGHT);
        StackPane.setMargin(passToggle, new Insets(0, 5, 0, 0));

        // Add the toggle button to the StackPane
        passwordStack.getChildren().add(passToggle);
        passBox.getChildren().setAll(passIcon, passwordStack);
        passBox.setAlignment(Pos.CENTER);

        // Eye behavior
        passToggle.setOnAction(e -> {
            if (passToggle.isSelected()) {
                visiblePasswordFieldLogin.setText(password.getText());
                visiblePasswordFieldLogin.setVisible(true);
                password.setVisible(false);
            } else {
                password.setText(visiblePasswordFieldLogin.getText());
                password.setVisible(true);
                visiblePasswordFieldLogin.setVisible(false);
            }
        });
        // Sync values
        password.textProperty().bindBidirectional(visiblePasswordFieldLogin.textProperty());

        // Blue login button with white text and rounded corners
        Button signIn = new Button("ورود");
        signIn.setPrefWidth(290);
        signIn.setPrefHeight(50);  // Height of sign in button
        signIn.setStyle("-fx-background-color: #52799b; -fx-text-fill: white; -fx-background-radius: 5;"); // Blue button color
        signIn.setOnMouseEntered(e -> signIn.setStyle("-fx-background-color: #52799b; -fx-text-fill: white; -fx-background-radius: 5; -fx-effect: dropshadow(gaussian, #a6cbeb, 10, 0.5, 0, 0);"));
        signIn.setOnMouseExited(e -> signIn.setStyle("-fx-background-color: #52799b; -fx-text-fill: white; -fx-background-radius: 5;"));

        // Login button action
        signIn.setOnAction(e -> {
            String enteredUsername = username.getText();
            String enteredPassword = password.getText();
            statusMessage.setText(""); // Clear previous message

            if (visiblePasswordFieldLogin.isVisible()) {
                enteredPassword = visiblePasswordFieldLogin.getText();
            }

            if (!usernameError.getText().isEmpty() || !passwordError.getText().isEmpty() || enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
                statusMessage.setText("لطفاً تمام فیلدها را پر کنید.");
                statusMessage.setStyle("-fx-fill: #d13d3d; -fx-font-size: 12px;"); // Display in red color
                return;
            }

            boolean loginSuccessful = Login.login(enteredUsername, enteredPassword);

            if (loginSuccessful) {
                statusMessage.setText("ورود با موفقیت انجام شد!");
                statusMessage.setStyle("-fx-fill: #058a0a; -fx-font-size: 12px;");
                primaryStage.setScene(dashboardForm.getScene());
                primaryStage.centerOnScreen();
                primaryStage.setMaximized(true);
                primaryStage.show();
            } else {
                statusMessage.setText("نام کاربری یا رمز عبور اشتباه است.");
                statusMessage.setStyle("-fx-fill: #d13d3d; -fx-font-size: 12px;");
            }
        });

        // Forgot Password and Sign Up options
        Button signUp = new Button("ثبت نام");
        signUp.setPrefWidth(290);
        signUp.setPrefHeight(50);  // Height of sign up button
        signUp.setStyle("-fx-background-color: #52799b; -fx-text-fill: white; -fx-background-radius: 5;"); // Blue button color
        signUp.setOnMouseEntered(e -> signUp.setStyle("-fx-background-color: #52799b; -fx-text-fill: white; -fx-background-radius: 5; -fx-effect: dropshadow(gaussian, #a6cbeb, 10, 0.5, 0, 0);"));
        signUp.setOnMouseExited(e -> signUp.setStyle("-fx-background-color: #52799b; -fx-text-fill: white; -fx-background-radius: 5;"));
        signUp.setOnAction(e -> {
            primaryStage.setScene(signUpForm.getScene());
            primaryStage.setMaximized(true);
        });

        // Forgot Password hyperlink
        Hyperlink forgotPassword = new Hyperlink("فراموشی رمز عبور؟");
        forgotPassword.setStyle("-fx-text-fill: #ffffff; -fx-underline: true;");
        forgotPassword.setPrefWidth(250);
        forgotPassword.setAlignment(Pos.CENTER);
        forgotPassword.setOnMouseEntered(e -> forgotPassword.setStyle("-fx-text-fill: #ffffff; -fx-underline: true; -fx-effect: dropshadow(gaussian, #9ac5ea, 10, 0.5, 0, 0);"));
        forgotPassword.setOnMouseExited(e -> forgotPassword.setStyle("-fx-text-fill: #ffffff; -fx-underline: true;"));

        forgotPassword.setOnAction(e -> {
            primaryStage.setScene(forgotPasswordForm.getScene());
            primaryStage.setMaximized(true);
        });

        // Real-time error definitions
        usernameError = new Text();
        usernameError.setStyle("-fx-fill: #d13d3d; -fx-font-size: 11px;");
        passwordError = new Text();
        passwordError.setStyle("-fx-fill: #d13d3d; -fx-font-size: 11px;");

        // Status message (error/success)
        statusMessage = new Text();
        statusMessage.setStyle("-fx-font-size: 12px;");

        // Real-time username validation
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() < 4) {
                usernameError.setText("نام کاربری باید حداقل 4 کاراکتر باشد"); // Username must be at least 4 characters
            } else if (!newValue.matches("[a-zA-Z0-9]+")) {
                usernameError.setText("نام کاربری فقط باید شامل حروف انگلیسی و اعداد باشد"); // Username must contain only English letters and numbers
            } else {
                usernameError.setText("");
            }
        });
        // Real-time password validation
        password.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() < 8) {
                passwordError.setText("رمز عبور باید حداقل 8 کاراکتر باشد"); // Password must be at least 8 characters
            } else {
                passwordError.setText("");
            }
        });

        // Adding errors to the panel
        VBox fieldsContainer = new VBox(5);
        fieldsContainer.setAlignment(Pos.CENTER);
        fieldsContainer.getChildren().addAll(userBox, usernameError, passBox, passwordError);

        // Create a container for buttons with specific spacing
        VBox buttonsContainer = new VBox(10);  // 10 pixels spacing between buttons
        buttonsContainer.setAlignment(Pos.CENTER);
        buttonsContainer.getChildren().addAll(signIn, signUp, forgotPassword, statusMessage);

        // Add everything to the root
        root.getChildren().setAll(loginBox);
        StackPane.setAlignment(loginBox, Pos.CENTER);

        loginBox.getChildren().setAll(headerBox, fieldsContainer, buttonsContainer);
        loginBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        scene = new Scene(root, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
    }

    private void validatePassword(String password) {
        if (password.length() < 8) {
            passwordError.setText("رمز عبور باید حداقل 8 کاراکتر باشد"); // Password must be at least 8 characters
        }else {
            passwordError.setText("");
        }
    }

    public Scene getScene() {
        return scene;
    }
}