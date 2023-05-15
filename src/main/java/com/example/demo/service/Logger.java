package com.example.demo.service;

import java.util.Enumeration;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class Logger {
  public static String logRequest(HttpServletRequest request) {
    StringBuilder sb = new StringBuilder();
    Enumeration<String> params = request.getParameterNames();
    while (params.hasMoreElements()) {
      String name = params.nextElement();
      String value = request.getParameter(name);
      sb.append(new LogMessage(request.getRemoteAddr(), "Request Params", name, value));
      if (params.hasMoreElements()) {
        sb.append("\n");
      }
    }
    return sb.toString();
  }

  public static String logCookie(HttpServletRequest request) {
    StringBuilder sb = new StringBuilder();
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      int i = 0;
      for (Cookie cookie : cookies) {
        sb.append(new LogMessage(request.getRemoteAddr(),"Cookie", cookie.getName(),
            cookie.getValue()));
        if (i < cookies.length - 1) {
          sb.append("\n");
        }
        i++;
      }
    }
    return sb.toString();
  }
}
