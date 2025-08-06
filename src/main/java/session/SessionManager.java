package session;

public class SessionManager {
    private static String loggedUserEmail;

    public static void setLoggedUserEmail(String email) {
        loggedUserEmail = email;
    }

    public static String getLoggedUserEmail() {
        return loggedUserEmail;
    }

    public static void clear() {
        loggedUserEmail = null;
    }
}
