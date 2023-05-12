package com.example.demo.servlet.user;

import com.example.demo.service.EmployeeService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/saveServlet")
public class SaveServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        EmployeeService.create(name, email, country);
        EmployeeService.output(response, "viewServlet",
            "Sorry! Cannot create a record", "text/plain", "UTF-8");
    }
}
