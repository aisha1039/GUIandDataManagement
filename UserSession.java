package service;

import java.util.prefs.Preferences;

public class UserSession {
    private static volatile UserSession instance;
    private String userName;
    private String password;
    private String privileges;

    private UserSession(String userName, String password, String privileges) {
        this.userName = userName;
        this.password = password;
        this.privileges = privileges;
        saveSession();
    }

    public static UserSession getInstance(String userName, String password, String privileges) {
        if (instance == null) {
            synchronized (UserSession.class) {
                if (instance == null) {
                    instance = new UserSession(userName, password, privileges);
                }
            }
        }
        return instance;
    }

    public static UserSession getInstance(String userName, String password) {
        return getInstance(userName, password, "NONE");
    }

    public String getUserName() { return this.userName; }
    public String getPassword() { return this.password; }
    public String getPrivileges() { return this.privileges; }

    public void cleanUserSession() {
        this.userName = "";
        this.password = "";
        this.privileges = "";
        saveSession();
    }

    private void saveSession() {
        Preferences prefs = Preferences.userRoot();
        prefs.put("USERNAME", userName);
        prefs.put("PASSWORD", password);
        prefs.put("PRIVILEGES", privileges);
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "userName='" + userName + '\'' +
                ", privileges='" + privileges + '\'' +
                '}';
    }
}
