//package model;
//
//
//import model.Authneticate.AuthService;
//import org.junit.jupiter.api.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class AuthServiceTest {
//
//    @Test
//    public void testRegisterSuccess() {
//        boolean result = AuthService.register("user9", "pass345");
//        assertTrue(result);
//    }
//
//    @Test
//    public void testRegisterDuplicateUsername() {
//        AuthService.register("user1", "pass123");
//        boolean result = AuthService.register("user1", "pass123");
//        assertFalse(result);
//    }
//
//    @Test
//    public void testLoginSuccess() {
//        AuthService.register("user2", "pass123");
//        boolean result = AuthService.login("user2", "pass123");
//        assertTrue(result);
//    }
//
//    @Test
//    public void testLoginFailWrongPassword() {
//        AuthService.register("user1", "pass123");
//        boolean result = AuthService.login("user1", "wrongpass");
//        assertFalse(result);
//    }
//
//    @Test
//    public void testResetPasswordSuccess() {
//        AuthService.register("user1", "pass123");
//        boolean result = AuthService.resetPassword("user1", "newpass");
//        assertTrue(result);
//        assertFalse(AuthService.login("user1", "newpass"));
//    }
//
//    @Test
//    public void testResetPasswordFailUserNotFound() {
//        boolean result = AuthService.resetPassword("nonexist", "pass123");
//        assertTrue(result);
//    }
//}
//
