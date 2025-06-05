package view.menu;


import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import view.menu.ManagementMenu;

public class MainMenu extends VBox {
    private ManagementMenu managementMenu;
    private VBox contentArea;

    public MainMenu(VBox contentArea, ManagementMenu managementMenu) {
        super(5);
        this.contentArea = contentArea;
        this.managementMenu = managementMenu;
        setAlignment(Pos.TOP_RIGHT);
        createMainMenu();
    }

    private void createMainMenu() {
        Button dashboardBtn = new Button("داشبورد");
        Button reportBtn = new Button("گزارش فروشگاه");

        // تنظیم استایل دکمه‌ها
        String buttonStyle = "-fx-alignment: CENTER_RIGHT; -fx-padding: 5 10 5 5; -fx-content-display: RIGHT;";
        dashboardBtn.setStyle(buttonStyle);
        reportBtn.setStyle(buttonStyle);

        dashboardBtn.setMaxWidth(Double.MAX_VALUE);
        reportBtn.setMaxWidth(Double.MAX_VALUE);

        dashboardBtn.setOnAction(e -> {
            managementMenu.setVisible(true);
            managementMenu.setManaged(true);
            this.setVisible(false);
            this.setManaged(false);
        });

        reportBtn.setOnAction(e -> {
            // TODO: پیاده‌سازی نمایش گزارش فروشگاه
            Label reportLabel = new Label("گزارش فروشگاه در حال توسعه...");
            contentArea.getChildren().clear();
            contentArea.getChildren().add(reportLabel);
        });

        getChildren().addAll(dashboardBtn, reportBtn);
    }
}
