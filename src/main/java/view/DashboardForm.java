package view;

import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DashboardForm {
    private Scene scene;
    private Stage primaryStage;
    private VBox contentArea;

    public DashboardForm(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createDashboardScene();
    }

    private void createDashboardScene() {
        BorderPane mainLayout = new BorderPane();

        // ناحیه محتوا در سمت راست
        contentArea = new VBox(10);
        contentArea.setAlignment(Pos.CENTER_RIGHT);
        contentArea.setStyle("-fx-padding: 20;");
        mainLayout.setCenter(contentArea);

        // ایجاد منوی مدیریت
        ManagementMenu managementMenu = new ManagementMenu(contentArea);

        // ایجاد منوی اصلی
        MainMenu mainMenu = new MainMenu(contentArea, managementMenu);
        managementMenu.setMainMenu(mainMenu);

        // قرار دادن منوها در سمت چپ
        VBox leftSideMenu = new VBox(10);
        leftSideMenu.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10;");
        leftSideMenu.setPrefWidth(200);
        leftSideMenu.setAlignment(Pos.TOP_RIGHT);
        leftSideMenu.getChildren().addAll(mainMenu, managementMenu);
        mainLayout.setLeft(leftSideMenu);

        // تنظیم جهت راست به چپ
        mainLayout.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        scene = new Scene(mainLayout, 1024, 768);
    }

    public Scene getScene() {
        return scene;
    }
}