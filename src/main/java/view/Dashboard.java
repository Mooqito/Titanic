package view;

import controller.product.ProductInput;

import java.util.Scanner;

public class Dashboard {

    Scanner in = new Scanner(System.in);
    ProductInput productInput = new ProductInput();
    Showproduct showproduct = new Showproduct();

    public Dashboard() {
        System.out.println(" 1) product management ");
        System.out.println(" 2) order management ");
        System.out.println(" 3) payment management ");

        int option = in.nextInt();

        switch (option) {
            case 1:
                product_management();
                break;
            case 2:
                //or;
                break;
            case 3:
                //py;
                break;
        }
    }

    public void product_management() {

        System.out.println(" 1) add product ");
        System.out.println(" 2) remove product ");
        System.out.println(" 3) edit product ");
        System.out.println(" 4) show ");

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
