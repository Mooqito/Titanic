این پروژه نرم افزار دسکتاپی برای مدیریت محصولات فروشگاه لوازم الکتریکی است که با استفاده از زبان java برای بک اند و javaFX برای گرافیک آن و postgreSQL به عنوان پایگاه داده طراحی و پیاده سازی شده است. این برنامه امکان افزودن، حذف، ویرایش، جستوجو و ثبت سفارش محصولات را دارد
ویژگی ها:
1. طراحی لایه‌بندی MVC (Model-View-Controller)
2. اضافه کردن محصول با اعتبار سنجی لحظه ای
3. قابلیت تعریف لحظه ای تامین کننده، برند و دسته بندی
4. بررسی خودکار وجود محصول پیش از افزودن
5. به‌روزرسانی تعداد محصول در صورت تکراری بودن مشخصات
6. ثبت سفارش مشتری شامل اطلاعات خریدار و سبد خرید
7. اتصال به دیتابیس PostgreSQL با JDBC
8. پیاده‌سازی اصول TDD (توسعه آزمون‌محور)
9. استفاده از TextFormatter برای فیلدهای عددی و متنی
10. طراحی گرافیکی تمیز و کاربرپسند با JavaFX

تکونولوژی‌های استفاده شده:
زبان اصلی پروژه java
رابط گرافیکی javaFX
دیتابیس رابطه‌ای postgreSQL
ارتباط با پایگاه داده jdbc
مدیریت وابستگی‌ها maven
تست واحد تی دی دی: Junit
محیط توسعه: IntelliJ IDEA

src/
├── controller/
│   ├── auth/                   ← کنترل‌های مربوط به احراز هویت
│   │   ├── LoginController.java
│   │   ├── SignUpController.java
│   │   └── ForgetPasswordController.java
│   └── product/                ← کنترل‌های مدیریت محصول
│       ├── ProductInput.java
│       ├── BrandInput.java
│       ├── CategoryInput.java
│       └── ProviderInput.java
│
├── model/
│   ├── auth/                   ← مدل‌های مربوط به کاربران و احراز هویت
│   │   ├── User.java
│   │   ├── HashUtil.java
│   │   └── AuthService.java
│   └── product/                ← مدل‌های محصول و جزئیات آن
│       ├── Product.java
│       ├── Brand.java
│       ├── Category.java
│       └── Provider.java
│
├── database/
│   ├── DBConnection.java       ← اتصال به پایگاه‌داده
│   └── initializer/            ← مقداردهی اولیه پایگاه‌داده
│       ├── DbInitializerAuth.java
│       ├── DbInitializerProduct.java
│       ├── InitBrandProduct.java
│       ├── InitCategoryProduct.java
│       ├── InitProviderProduct.java
│       ├── InitCustomer.java
│       ├── InitOrder.java
│       └── InitOrderItem.java
│
src/
├── main/
│   ├── java/
│   │   ├── controller/
│   │   │   ├── BrandController.java
│   │   │   ├── CategoryController.java
│   │   │   ├── ProductController.java
│   │   │   └── ProviderController.java
│   │   ├── model/
│   │   │   ├── Brand.java
│   │   │   ├── BrandInputToDB.java
│   │   │   ├── Category.java
│   │   │   ├── CategoryInputToDB.java
│   │   │   ├── Product.java
│   │   │   ├── ProductInputToDB.java
│   │   │   ├── Provider.java
│   │   │   └── ProviderInputToDB.java
│   │   ├── repository/
│   │   │   ├── BrandRepository.java
│   │   │   ├── CategoryRepository.java
│   │   │   ├── ProductRepository.java
│   │   │   └── ProviderRepository.java
│   │   ├── service/
│   │   │   ├── BrandService.java
│   │   │   ├── CategoryService.java
│   │   │   ├── ProductService.java
│   │   │   └── ProviderService.java
│   │   ├── view/
│   │   │   ├── auth/
│   │   │   │   ├── ForgotPasswordForm.java
│   │   │   │   ├── LoginForm.java
│   │   │   │   └── SignUpForm.java
│   │   │   ├── brand/
│   │   │   │   ├── AddDeleteBrand.java
│   │   │   │   ├── BrandManagement.java
│   │   │   │   └── ShowBrandList.java
│   │   │   ├── category/
│   │   │   │   ├── AddDeleteCategory.java
│   │   │   │   ├── CategoryManagement.java
│   │   │   │   └── ShowCategoryList.java
│   │   │   ├── product/
│   │   │   │   ├── AddProductForm.java
│   │   │   │   ├── DeleteProductForm.java
│   │   │   │   ├── EditProductForm.java
│   │   │   │   ├── ProductManagement.java
│   │   │   │   ├── ProductValidation.java
│   │   │   │   ├── ShowProduct.java
│   │   │   │   └── ShowProductForm.java
│   │   │   ├── provider/
│   │   │   │   ├── ProviderManagement.java
│   │   │   │   └── ...
│   │   │   ├── menu/
│   │   │   │   ├── DashboardForm.java
│   │   │   │   ├── MainMenu.java
│   │   │   │   └── ManagementMenu.java
│   │   │   └── Main.java
│   │   └── db/
│   │       └── ConnectionFactory.java
│   └── resources/
│       ├── images/
│       │   ├── AddDeleteBrand.png
│       │   ├── AddProduct.png
│       │   ├── BackButton.png
│       │   ├── BrandManagement.png
│       │   ├── CategoryManagement.png
│       │   ├── Dashboard.png
│       │   ├── DeleteProduct.png
│       │   ├── EditProduct.png
│       │   ├── Lamp.png
│       │   ├── ProductManagement.png
│       │   ├── ProviderManagement.png
│       │   └── ShowProduct.png
│       └── ...
├── test/
│   └── java/
│       └── ... (تست‌های واحد)
└── ...

نصب و اجرا:
1. دیتابیس postgreSQL را نصب و اجرا کنید
2. دیتابیس store_db را ایجاد کنید
3. پروژه را با IntelliJ باز کرده و maven را اجرا کنید
4. وارد کلاس Main.java شوید و آن را ران کنید

توسعه دهندگان:
محمد قریشی
github: https://github.com/Mooqito
linkdin: https://www.linkedin.com/in/mohamad-qoreishi-0453212b6?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app
gmail: mghorishi89@gmail.com

مبین محمودیان
github: https://github.com/mobinmahmoodian
gmail: mobinmahmoudian12@gmail.com
بیتا ابراهیم گل
github: https://github.com/BitaEbrahimgol
gmail: bitaebrahimgol@gmail.com
پریا اسدی
github: https://github.com/popo8383
gmail: paryaasadi818@gmail.com
