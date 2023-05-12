package com.example.demo.servlet.user;

import com.example.demo.model.Employee;

import com.example.demo.service.EmployeeService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/viewServlet")
public class ViewServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<Employee> list = EmployeeService.getAll();

        EmployeeService.output(response, "", "Cannot select any record",
            "text/html", "UTF-8");
    }
}
