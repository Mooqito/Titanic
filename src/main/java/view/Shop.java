package view;

import controller.product.ProductInput;

import java.util.Scanner;

public class Dashboard {

    Scanner in = new Scanner(System.in);
    ProductInput productInput = new ProductInput();
    Showproduct showproduct = new Showproduct();

    public void shop() {
        System.out.println(" 1)وضعیت فروشگاه ");
        System.out.println(" 2)داشبورد ");

        int option = in.nextInt();

        switch (option) {
            case 1:
                //
                break;
            case 2:
                product_management();
                break;
        }
    }

    public void dashbord (){
        System.out.println("1)مدیریت محصولات");
        System.out.println("2)مدیریت دسته بندی ها");
        System.out.println("3)مدیریت برندها");
        System.out.println("4)مدیریت تأمینکننده ها");
        System.out.println("5)مدیریت کاربران");
        System.out.println("6)مدیریت سفارش ها");

        int option = in.nextInt();

        switch (option) {
            case 1:
                //
                break;
            case 2:
                //remove;
                break;
            case 3:
                //
                break;
            case 4:
                //
                break;
            case 5:
                //
                break;
            case 6:
                //
                break;
        }

    }

    public void product_management() {

        System.out.println(" 1) فزودن محصول جدید ");
        System.out.println(" 2) ویرایش محصول ");
        System.out.println(" 3) / حذف ");
        System.out.println(" 4) نمایش لیست محصولات ");

        int option = in.nextInt();

        switch (option) {
            case 1:
                productInput.ProductInputList();
                break;
            case 2:
                //remove;
                break;
            case 3:
//                showproduct.show();
                break;
            case 4:
                showproduct.show();
                break;
        }
    }

    public void order_management() {

        System.out.println(" 1) place order ");
        System.out.println(" 2) change order ");
        System.out.println(" 3) view order ");

        int option = in.nextInt();

        switch (option) {
            case 1:
                //place;
                break;
            case 2:
                //change;
                break;
            case 3:
                //view;
                break;
        }
    }

    public void payment_management() {

        System.out.println(" 1) view invoice ");
        int option = in.nextInt();

        switch (option) {
            case 1:
                //view;
                break;
        }
    }
}
