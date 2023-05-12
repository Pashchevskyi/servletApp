package com.example.demo.servlet.user;


import com.example.demo.service.EmployeeService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/putServlet")
public class PutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sid = request.getParameter("id");
        int id = Integer.parseInt(sid);

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        EmployeeService.update(id, name, email, country);

        EmployeeService.output(response, "viewByIDServlet?id=" + sid,
            "Sorry! Cannot ubdate the record#" + sid, "text/html",
            "UTF-8");
    }
}
