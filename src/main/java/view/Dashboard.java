package view;

import java.util.Scanner;

public class Dashboard {

    Scanner in = new Scanner(System.in);

    public Dashboard() {
        System.out.println(" 1) product management ");
        System.out.println(" 2) order management ");
        System.out.println(" 3) payment management ");

        int option = in.nextInt();

        switch (option) {
            case 1:
                //pr;
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

        int option = in.nextInt();

        switch (option) {
            case 1:
                //add;
                break;
            case 2:
                //remove;
                break;
            case 3:
                //edit;
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
