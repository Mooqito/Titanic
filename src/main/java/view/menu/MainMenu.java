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
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import view.menu.ManagementMenu;
import view.menu.DashboardForm;
import runner.Main;
import view.Authnticate.LoginForm;

public class MainMenu extends StackPane {
    private ManagementMenu managementMenu;
    private DashboardForm dashboardForm;
    private LoginForm loginForm;
    VBox originalMenuVBox;
    private final String buttonStyle = "-fx-background-color: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 5;";
    private final String buttonHoverStyle = "-fx-background-color: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 5; -fx-effect: dropshadow(gaussian, #a6cbeb, 10, 0.5, 0, 0);";

    public MainMenu(ManagementMenu managementMenu, DashboardForm dashboardForm, LoginForm loginForm) {
        this.managementMenu = managementMenu;
        this.dashboardForm = dashboardForm;
        this.loginForm = loginForm;
        createMainMenu();

        // Ensure window is centered when shown
        Platform.runLater(() -> {
            if (getScene() != null) {
                Stage stage = (Stage) getScene().getWindow();
                stage.centerOnScreen();
                // Set window position to center of screen
                stage.setX((Screen.getPrimary().getVisualBounds().getWidth() - stage.getWidth()) / 2);
                stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - stage.getHeight()) / 2);
            }
        });
    }

    private void createMainMenu() {
        // Create main menu container
        setStyle("-fx-background-color: transparent;");

        VBox centerPanel = new VBox(15);
        centerPanel.setAlignment(Pos.CENTER);
        centerPanel.setStyle("-fx-background-color: rgba(175, 180, 204, 0.58); -fx-background-radius: 15;");
        centerPanel.setPrefWidth(400);
        centerPanel.setPrefHeight(400);
        centerPanel.setMaxWidth(400);
        centerPanel.setMaxHeight(400);

        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/images/Lamp.png")));
        logo.setFitWidth(100);
        logo.setFitHeight(130);

        Label titleLabel = new Label("منوی اصلی");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.WHITE);

        Button dashboardBtn = new Button("داشبورد");
        dashboardBtn.setStyle(buttonStyle);
        dashboardBtn.setOnMouseEntered(e -> dashboardBtn.setStyle(buttonHoverStyle));
        dashboardBtn.setOnMouseExited(e -> dashboardBtn.setStyle(buttonStyle));
        dashboardBtn.setPrefWidth(200);

        Button reportBtn = new Button("گزارش فروشگاه");
        reportBtn.setStyle(buttonStyle);
        reportBtn.setOnMouseEntered(e -> reportBtn.setStyle(buttonHoverStyle));
        reportBtn.setOnMouseExited(e -> reportBtn.setStyle(buttonStyle));
        reportBtn.setPrefWidth(200);

        // Create menu buttons container
        centerPanel.getChildren().addAll(logo, titleLabel, dashboardBtn, reportBtn);

        // Create Dashboard button
        originalMenuVBox = centerPanel;
        getChildren().add(originalMenuVBox);
        StackPane.setAlignment(originalMenuVBox, Pos.CENTER); // Changed alignment to CENTER

        // Create Management button
        dashboardBtn.setOnAction(e -> {
            if (dashboardForm != null) {
                dashboardForm.showView(managementMenu);
            }
        });

        // Create Reports button action
        Label developmentMessageLabel = new Label("گزارش فروشگاه در حال توسعه...");
        developmentMessageLabel.setFont(Font.font("System", 14));
        developmentMessageLabel.setTextFill(Color.WHITE);
        developmentMessageLabel.setVisible(false); // Initially hidden
        developmentMessageLabel.setManaged(false); // Does not take up space when hidden

        reportBtn.setOnAction(e -> {
            // Remove any existing development message to prevent duplication
            if (originalMenuVBox.getChildren().contains(developmentMessageLabel)) {
                originalMenuVBox.getChildren().remove(developmentMessageLabel);
            }
            originalMenuVBox.getChildren().add(developmentMessageLabel);
            developmentMessageLabel.setVisible(true);
            developmentMessageLabel.setManaged(true);
            VBox.setMargin(developmentMessageLabel, new Insets(10, 0, 0, 0)); // Add some top margin

            // Make the message disappear after 10 seconds
            PauseTransition delay = new PauseTransition(Duration.seconds(10));
            delay.setOnFinished(event -> {
                developmentMessageLabel.setVisible(false);
                developmentMessageLabel.setManaged(false);
                originalMenuVBox.getChildren().remove(developmentMessageLabel); // Remove from children to free space
            });
            delay.play();
        });
    }

    // setFullScreen method (kept from previous version, but might need adjustment depending on where it's called)
    public void setFullScreen() {
        if (getScene() != null) {
            Stage stage = (Stage) getScene().getWindow();
            stage.setMaximized(true);
        }
    }
}