package view.validation;

import javafx.scene.control.TextFormatter;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class ProductValidation {

    // محدودیت‌های ورودی
    private static final int MAX_TITLE_LENGTH = 100;
    private static final long MAX_PRICE = 1000000000; // یک میلیارد تومان
    private static final int MAX_DESCRIPTION_LENGTH = 500;
    private static final int MAX_QUANTITY = 1000000;

    // الگوهای اعتبارسنجی
    private static final Pattern TITLE_PATTERN = Pattern.compile("[\\p{L}\\p{N}\\s\\-_.,]+");
    private static final Pattern PRICE_PATTERN = Pattern.compile("\\d*");
    private static final Pattern QUANTITY_PATTERN = Pattern.compile("\\d*");

    // اعتبارسنجی نام محصول
    public static TextFormatter<String> createTitleFormatter() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            // بررسی طول
            if (newText.length() > MAX_TITLE_LENGTH) {
                return null;
            }

            // بررسی کاراکترهای مجاز
            if (!TITLE_PATTERN.matcher(newText).matches() && !newText.isEmpty()) {
                return null;
            }

            return change;
        };

        return new TextFormatter<>(filter);
    }

    // اعتبارسنجی قیمت
    public static TextFormatter<String> createPriceFormatter() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            // فقط اعداد مجاز هستند
            if (!PRICE_PATTERN.matcher(newText).matches() && !newText.isEmpty()) {
                return null;
            }

            // بررسی حداکثر مقدار
            try {
                if (!newText.isEmpty()) {
                    long price = Long.parseLong(newText);
                    if (price > MAX_PRICE) {
                        return null;
                    }
                }
            } catch (NumberFormatException e) {
                return null;
            }

            return change;
        };

        return new TextFormatter<>(filter);
    }

    // اعتبارسنجی توضیحات
    public static TextFormatter<String> createDescriptionFormatter() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            // بررسی طول
            if (newText.length() > MAX_DESCRIPTION_LENGTH) {
                return null;
            }

            return change;
        };

        return new TextFormatter<>(filter);
    }

    // اعتبارسنجی تعداد
    public static TextFormatter<String> createQuantityFormatter() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            // فقط اعداد مجاز هستند
            if (!QUANTITY_PATTERN.matcher(newText).matches() && !newText.isEmpty()) {
                return null;
            }

            // بررسی حداکثر مقدار
            try {
                if (!newText.isEmpty()) {
                    int quantity = Integer.parseInt(newText);
                    if (quantity > MAX_QUANTITY) {
                        return null;
                    }
                }
            } catch (NumberFormatException e) {
                return null;
            }

            return change;
        };

        return new TextFormatter<>(filter);
    }

    // اعتبارسنجی کامل فرم
    public static String validateForm(String title, String price, String description,
                                      String category, String brand, String provider, String quantity) {

        if (title == null || title.trim().isEmpty()) {
            return "لطفاً نام محصول را وارد کنید";
        }
        if (title.length() > MAX_TITLE_LENGTH) {
            return "نام محصول نمی‌تواند بیشتر از " + MAX_TITLE_LENGTH + " کاراکتر باشد";
        }
        if (!TITLE_PATTERN.matcher(title).matches()) {
            return "نام محصول فقط می‌تواند شامل حروف، اعداد و علائم - _ . باشد";
        }

        if (price == null || price.trim().isEmpty()) {
            return "لطفاً قیمت را وارد کنید";
        }
        try {
            long priceValue = Long.parseLong(price);
            if (priceValue <= 0) {
                return "قیمت باید بزرگتر از صفر باشد";
            }
            if (priceValue > MAX_PRICE) {
                return "قیمت نمی‌تواند بیشتر از " + MAX_PRICE + " باشد";
            }
        } catch (NumberFormatException e) {
            return "لطفاً قیمت را به صورت عدد صحیح وارد کنید";
        }

        if (description != null && description.length() > MAX_DESCRIPTION_LENGTH) {
            return "توضیحات نمی‌تواند بیشتر از " + MAX_DESCRIPTION_LENGTH + " کاراکتر باشد";
        }

        if (category == null || category.trim().isEmpty()) {
            return "لطفاً دسته‌بندی را انتخاب کنید";
        }

        if (brand == null || brand.trim().isEmpty()) {
            return "لطفاً برند را انتخاب کنید";
        }

        if (provider == null || provider.trim().isEmpty()) {
            return "لطفاً تامین‌کننده را انتخاب کنید";
        }

        if (quantity == null || quantity.trim().isEmpty()) {
            return "لطفاً تعداد را وارد کنید";
        }
        try {
            int quantityValue = Integer.parseInt(quantity);
            if (quantityValue < 0) {
                return "تعداد نمی‌تواند منفی باشد";
            }
            if (quantityValue > MAX_QUANTITY) {
                return "تعداد نمی‌تواند بیشتر از " + MAX_QUANTITY + " باشد";
            }
        } catch (NumberFormatException e) {
            return "لطفاً تعداد را به صورت عدد صحیح وارد کنید";
        }

        return null; // اگر همه موارد معتبر باشند
    }
}