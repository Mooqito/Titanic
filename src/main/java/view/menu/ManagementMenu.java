package view.menu;


import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import view.BrandManagement;
import view.CategoryManagement;
import view.product.ProductManagement;

public class ManagementMenu extends VBox {
    private MainMenu mainMenu;
    private VBox contentArea;
    private ProductManagement productManagement;
    private BrandManagement brandManagement;
    private CategoryManagement categoryManagement;
//    private SupplierManagement supplierManagement;

    public ManagementMenu(VBox contentArea) {
        super(5);
        this.contentArea = contentArea;
        setVisible(false);
        setManaged(false);
        setStyle("-fx-padding: 0 20 0 0;"); // ایجاد تورفتگی
        setAlignment(Pos.TOP_RIGHT);
        createManagementMenu();
    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    private void createManagementMenu() {
        // استایل مشترک برای همه دکمه‌ها
        String buttonStyle = "-fx-alignment: CENTER_RIGHT; -fx-padding: 5 10 5 5; -fx-content-display: RIGHT;";
//
//        // ایجاد بخش‌های مدیریتی
        productManagement = new ProductManagement(contentArea);
        brandManagement = new BrandManagement(contentArea);
        categoryManagement = new CategoryManagement(contentArea);
//        supplierManagement = new SupplierManagement(contentArea);
//
//        // مخفی کردن همه بخش‌های مدیریتی در ابتدا
        productManagement.setVisible(false);
        productManagement.setManaged(false);
        brandManagement.setVisible(false);
        brandManagement.setManaged(false);
        categoryManagement.setVisible(false);
        categoryManagement.setManaged(false);
//        supplierManagement.setVisible(false);
//        supplierManagement.setManaged(false);

        // دکمه‌های اصلی مدیریت
        Button productManagementBtn = new Button("مدیریت محصول");
        Button brandManagementBtn = new Button("مدیریت برند");
        Button categoryManagementBtn = new Button("مدیریت دسته‌بندی");
        Button supplierManagementBtn = new Button("مدیریت تامین‌کنندگان");

        // تنظیم استایل دکمه‌ها
        productManagementBtn.setStyle(buttonStyle);
        brandManagementBtn.setStyle(buttonStyle);
        categoryManagementBtn.setStyle(buttonStyle);
        supplierManagementBtn.setStyle(buttonStyle);

        // تنظیم عرض دکمه‌ها
        productManagementBtn.setMaxWidth(Double.MAX_VALUE);
        brandManagementBtn.setMaxWidth(Double.MAX_VALUE);
        categoryManagementBtn.setMaxWidth(Double.MAX_VALUE);
        supplierManagementBtn.setMaxWidth(Double.MAX_VALUE);

        // دکمه بازگشت به منوی اصلی
        Button backToMainBtn = new Button("بازگشت به منو");
        backToMainBtn.setStyle(buttonStyle);
        backToMainBtn.setMaxWidth(Double.MAX_VALUE);
        backToMainBtn.setOnAction(e -> {
            this.setVisible(false);
            this.setManaged(false);
            mainMenu.setVisible(true);
            mainMenu.setManaged(true);

//            // مخفی کردن همه زیرمنوها هنگام بازگشت
            productManagement.setVisible(false);
            productManagement.setManaged(false);
            brandManagement.setVisible(false);
            brandManagement.setManaged(false);
            categoryManagement.setVisible(false);
            categoryManagement.setManaged(false);
//            supplierManagement.setVisible(false);
//            supplierManagement.setManaged(false);
        });

        // تنظیم رویدادها
        setupManagementButton(productManagementBtn, productManagement);
        setupManagementButton(brandManagementBtn, brandManagement);
        setupManagementButton(categoryManagementBtn, categoryManagement);
//        setupManagementButton(supplierManagementBtn, supplierManagement);
//
//        // اضافه کردن همه المان‌ها به منو
        getChildren().addAll(
                productManagementBtn, productManagement,
                brandManagementBtn, brandManagement,
                categoryManagementBtn, categoryManagement,
//                supplierManagementBtn, supplierManagement,
                backToMainBtn
        );
    }

    private void setupManagementButton(Button button, VBox subMenu) {
        button.setOnAction(e -> {
            boolean isVisible = subMenu.isVisible();

            // مخفی کردن همه زیرمنوها
            productManagement.setVisible(false);
            productManagement.setManaged(false);
            brandManagement.setVisible(false);
            brandManagement.setManaged(false);
            categoryManagement.setVisible(false);
            categoryManagement.setManaged(false);
//            supplierManagement.setVisible(false);
//            supplierManagement.setManaged(false);

            // نمایش زیرمنوی انتخاب شده
            subMenu.setVisible(!isVisible);
            subMenu.setManaged(!isVisible);
        });
    }
}