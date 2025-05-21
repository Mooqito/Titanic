package runner;

import controller.Authnticate.AuthMenu;
import database.DbInitializerAuth;

public class Main {
    public static void main(String[] args) {
        DbInitializerAuth.initAuth();
        new AuthMenu();
    }
}
