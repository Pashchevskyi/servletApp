package com.example.demo.servlet.session;

import com.example.demo.service.UserService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final UserService userService;

    public LoginServlet() {
        userService = new UserService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // here is values of 2 parameters we transfer
        String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");

        if (userService.checkUser(user, pwd)) {
            userService.setSession(request, "user", "user");
            userService.setCookie(response, user);
            PrintWriter out = response.getWriter();
            out.println("Welcome back to the team, " + user + "!");
        } else {
            PrintWriter out = response.getWriter();
            out.println("Either user name or password is wrong!");
        }
    }
}
