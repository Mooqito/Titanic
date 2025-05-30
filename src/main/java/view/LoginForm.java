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
