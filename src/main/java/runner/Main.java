package runner;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import database.DBinitialazation.*;
import view.Authnticate.LoginForm;

public class Main {

    public static void main(String[] args) {
        Application.launch(InitApp.class, args);
    }

    public static void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static class InitApp extends Application {
        @Override
        public void start(Stage primaryStage) {
            // مقداردهی اولیه دیتابیس‌ها
            InitCategoryProduct.initCategory();
            InitBrandProduct.initbrand();
            InitProviderProduct.inintprovider();
            DbInitializerAuth.initAuth();
            DbInitializerProduct.initProduct();

            LoginForm loginForm = new LoginForm(primaryStage);
            primaryStage.setTitle("سیستم مدیریت فروشگاه");
            primaryStage.setScene(loginForm.getScene());
            primaryStage.show();
        }
    }
}
