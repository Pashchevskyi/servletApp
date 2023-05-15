package com.example.demo.service;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserService {

  private final Properties properties;

  public UserService() {
    properties = new Properties();
    try (InputStreamReader reader = new FileReader("./src/main/resources/application.properties")) {
      properties.load(reader);
    } catch (IOException e) {
      System.err.println("<<< Exception: " + e.getMessage());
    }
  }

  public void setSession(HttpServletRequest request, String name, String value) {
    HttpSession session = request.getSession();
    session.setAttribute(name, value);
    String strSessionLifetime=properties.getProperty("session.lifetime_seconds");
    session.setMaxInactiveInterval(Integer.parseInt(strSessionLifetime));
  }

  public void setCookie(HttpServletResponse response, String userLoggedIn) {
    Cookie userName = new Cookie("user", userLoggedIn);
    String strCookieLifetimeSeconds = properties.getProperty("cookie.lifetime_seconds");
    userName.setMaxAge(Integer.parseInt(strCookieLifetimeSeconds));
    response.addCookie(userName);
  }

  public boolean checkUser(String user, String password) {
    return user.equals(properties.getProperty("admin.user")) &&
        password.equals(properties.getProperty("admin.password"));
  }
}
