package session;

public class SessionManager {
    private static String loggedManagerEmail;

    public static void setLoggedManagerEmail(String email) {
        loggedManagerEmail = email;
    }

    public static String getLoggedManagerEmail() {
        return loggedManagerEmail;
    }

    public static void clear() {
        loggedManagerEmail = null;
    }
}
