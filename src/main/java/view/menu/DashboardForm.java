package view.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.application.Platform;

import view.menu.ManagementMenu;
import view.menu.MainMenu;
import runner.Main;
import view.Authnticate.LoginForm;

public class DashboardForm {
    private Scene scene;
    private Stage primaryStage;
    private LoginForm loginForm;
    private MainMenu mainMenu;
    private ManagementMenu managementMenu;
    private BorderPane contentBorderPane;
    private StackPane sceneRootStackPane;
    private VBox mainContentArea;
    private Button mainBackButton;
    private javafx.scene.Node currentView;
    private VBox centerContentContainer;

    public DashboardForm(Stage primaryStage, LoginForm loginForm) {
        this.primaryStage = primaryStage;
        this.loginForm = loginForm;
        createDashboardScene();
    }

    public DashboardForm(Stage primaryStage) {
    }

    private void createDashboardScene() {
        mainContentArea = new VBox();
        mainContentArea.setAlignment(Pos.CENTER);
        mainContentArea.setPadding(new Insets(20));
        mainContentArea.setStyle("-fx-background-color: transparent;");

        managementMenu = new ManagementMenu(mainContentArea);
        mainMenu = new MainMenu(managementMenu, this, loginForm);

        centerContentContainer = new VBox();
        centerContentContainer.setAlignment(Pos.CENTER);
        centerContentContainer.setStyle("-fx-background-color: transparent;");

        contentBorderPane = new BorderPane();
        contentBorderPane.setStyle("-fx-background-color: transparent;");
        contentBorderPane.setPrefWidth(1370);
        contentBorderPane.setPrefHeight(720);
        contentBorderPane.setMaxWidth(Double.MAX_VALUE);
        contentBorderPane.setMaxHeight(Double.MAX_VALUE);

        contentBorderPane.setRight(managementMenu);
        contentBorderPane.setCenter(centerContentContainer);

        managementMenu.setVisible(false);
        managementMenu.setManaged(false);

        ImageView mainBackIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/BackButton.png")));
        mainBackIcon.setFitWidth(40);
        mainBackIcon.setFitHeight(40);
        mainBackButton = new Button();
        mainBackButton.setGraphic(mainBackIcon);
        mainBackButton.setStyle("-fx-background-color: transparent; -fx-padding: 5;");
        mainBackButton.setOnMouseEntered(event -> mainBackButton.setStyle("-fx-background-color: transparent; -fx-padding: 5; -fx-effect: dropshadow(gaussian, #a6cbeb, 10, 0.5, 0, 0);"));
        mainBackButton.setOnMouseExited(event -> mainBackButton.setStyle("-fx-background-color: transparent; -fx-padding: 5;"));

        sceneRootStackPane = new StackPane();
        sceneRootStackPane.setStyle("-fx-background-color: #3a5c79;");
        sceneRootStackPane.getChildren().addAll(contentBorderPane, mainBackButton);
        StackPane.setAlignment(mainBackButton, Pos.TOP_LEFT);

        currentView = mainMenu;
        showView(mainMenu);

        scene = new Scene(sceneRootStackPane);
    }

    public void showView(javafx.scene.Node view) {
        centerContentContainer.getChildren().clear();

        if (view == mainMenu) {
            centerContentContainer.getChildren().add(mainMenu);
            mainMenu.setVisible(true);
            mainMenu.setManaged(true);
            managementMenu.setVisible(false);
            managementMenu.setManaged(false);
            currentView = mainMenu;

        } else if (view == managementMenu) {
            centerContentContainer.getChildren().add(mainContentArea);
            mainContentArea.getChildren().clear();
            managementMenu.setVisible(true);
            managementMenu.setManaged(true);
            mainMenu.setVisible(false);
            mainMenu.setManaged(false);
            currentView = managementMenu;

        } else {
            if (!centerContentContainer.getChildren().contains(mainContentArea)) {
                centerContentContainer.getChildren().add(mainContentArea);
            }
            mainContentArea.getChildren().clear();
            mainContentArea.getChildren().add(view);
            view.setVisible(true);
            view.setManaged(true);
            managementMenu.setVisible(true);
            managementMenu.setManaged(true);
            mainMenu.setVisible(false);
            mainMenu.setManaged(false);
            currentView = view;
        }

        if (currentView == mainMenu) {
            mainBackButton.setOnAction(e -> {
                if (primaryStage != null && loginForm != null) {
                    primaryStage.setScene(loginForm.getScene());
                    primaryStage.show();
                }
            });
        } else {
            mainBackButton.setOnAction(e -> {
                showView(mainMenu);
            });
        }
    }

    public Scene getScene() {
        return scene;
    }
}