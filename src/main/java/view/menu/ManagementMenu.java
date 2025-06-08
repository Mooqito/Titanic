package view.menu;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.Brand.BrandManagement;
import view.product.ProductManagement;
import view.Category.CategoryManagement;

public class ManagementMenu extends VBox {
    private MainMenu mainMenu;
    private VBox contentArea;
    private ProductManagement productManagement;
    private BrandManagement brandManagement;
    private CategoryManagement categoryManagement;
    private DashboardForm dashboardForm;

    public ManagementMenu(VBox contentArea) {
        super(5);
        this.contentArea = contentArea;
        setVisible(false);
        setManaged(false);
        setStyle("-fx-background-color: rgba(175, 180, 204, 0.58); -fx-background-radius: 10;");
        setPadding(new Insets(0, 20, 20, 20));
        setAlignment(Pos.TOP_CENTER);
        setPrefWidth(350);
        setMaxHeight(Double.MAX_VALUE);
        createManagementMenu();
    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void setDashboardForm(DashboardForm dashboardForm) {
        this.dashboardForm = dashboardForm;
    }

    private void createManagementMenu() {
        // Dashboard title and logo
        ImageView dashboardIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/Dashboard.png")));
        dashboardIcon.setFitWidth(40);
        dashboardIcon.setFitHeight(40);

        Label dashboardTitle = new Label("داشبورد");
        dashboardTitle.setFont(javafx.scene.text.Font.font("System", javafx.scene.text.FontWeight.BOLD, 30)); // Larger font
        dashboardTitle.setTextFill(javafx.scene.paint.Color.WHITE);

        HBox dashboardHeader = new HBox(10);
        dashboardHeader.setAlignment(Pos.CENTER_LEFT);
        dashboardHeader.getChildren().addAll(dashboardIcon, dashboardTitle);
        dashboardHeader.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT); // Set RTL orientation
        VBox.setMargin(dashboardHeader, new Insets(20, 20, 20, 0)); // Changed right margin to 20

        // Common style for all buttons
        String buttonStyle = "-fx-background-color: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 5; -fx-alignment: CENTER_RIGHT; -fx-content-display: RIGHT;";
        String buttonHoverStyle = "-fx-background-color: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 5; -fx-effect: dropshadow(gaussian, #a6cbeb, 10, 0.5, 0, 0); -fx-alignment: CENTER_RIGHT; -fx-content-display: RIGHT;";

        // Create management sections
        productManagement = new ProductManagement(contentArea);
        brandManagement = new BrandManagement(contentArea);
        categoryManagement = new CategoryManagement(contentArea);

        // Hide all management sections initially
        productManagement.setVisible(false);
        productManagement.setManaged(false);
        brandManagement.setVisible(false);
        brandManagement.setManaged(false);
        categoryManagement.setVisible(false);
        categoryManagement.setManaged(false);

        // Main management buttons
        Button productManagementBtn = createManagementButton("مدیریت محصول", "/images/ProductManagement.png");
        Button brandManagementBtn = createManagementButton("مدیریت برند", "/images/BrandManagement.png");
        Button categoryManagementBtn = createManagementButton("مدیریت دسته‌بندی", "/images/CategoryManagement.png");
        Button supplierManagementBtn = createManagementButton("مدیریت تامین‌کنندگان", "/images/ProviderManagement.png");

        // Set button widths
        productManagementBtn.setPrefWidth(270);
        brandManagementBtn.setPrefWidth(270);
        categoryManagementBtn.setPrefWidth(270);
        supplierManagementBtn.setPrefWidth(270);

        // Set button actions
        setupManagementButton(productManagementBtn, productManagement);
        setupManagementButton(brandManagementBtn, brandManagement);
        setupManagementButton(categoryManagementBtn, categoryManagement);

        // Group buttons with their submenus
        VBox productGroup = new VBox(5); // 5 pixel spacing between button and submenu
        productGroup.getChildren().addAll(productManagementBtn, productManagement);
        productGroup.setAlignment(Pos.TOP_CENTER);

        VBox brandGroup = new VBox(5); // 5 pixel spacing between button and submenu
        brandGroup.getChildren().addAll(brandManagementBtn, brandManagement);
        brandGroup.setAlignment(Pos.TOP_CENTER);

        VBox categoryGroup = new VBox(5); // 5 pixel spacing between button and submenu
        categoryGroup.getChildren().addAll(categoryManagementBtn, categoryManagement);
        categoryGroup.setAlignment(Pos.TOP_CENTER);

        // Add all elements to the menu
        getChildren().addAll(
                dashboardHeader, // Add dashboard header at the top
                productGroup,
                brandGroup,
                categoryGroup,
                supplierManagementBtn
        );
    }

    private void setupManagementButton(Button button, VBox subMenu) {
        button.setOnAction(e -> {
            boolean isVisible = subMenu.isVisible();

            // Hide all submenus
            productManagement.setVisible(false);
            productManagement.setManaged(false);
            brandManagement.setVisible(false);
            brandManagement.setManaged(false);
            categoryManagement.setVisible(false);
            categoryManagement.setManaged(false);

            // Show selected submenu
            subMenu.setVisible(!isVisible);
            subMenu.setManaged(!isVisible);
        });
    }

    private Button createManagementButton(String text, String imagePath) {
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        icon.setFitWidth(35);
        icon.setFitHeight(35);

        Label textLabel = new Label(text);
        textLabel.setFont(javafx.scene.text.Font.font("System", 14));
        textLabel.setTextFill(javafx.scene.paint.Color.WHITE);

        HBox buttonContent = new HBox(10);
        buttonContent.setAlignment(Pos.CENTER_LEFT);
        buttonContent.getChildren().addAll(icon, textLabel);
        buttonContent.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        Button button = new Button();
        button.setGraphic(buttonContent);
        button.setStyle("-fx-background-color: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 5; -fx-alignment: CENTER_RIGHT; -fx-content-display: RIGHT;");

        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 5; -fx-effect: dropshadow(gaussian, #a6cbeb, 10, 0.5, 0, 0); -fx-alignment: CENTER_RIGHT; -fx-content-display: RIGHT;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 5; -fx-alignment: CENTER_RIGHT; -fx-content-display: RIGHT;"));

        return button;
    }
}