package com.example.demo.servlet.user;

import com.example.demo.service.EmployeeService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteServlet")
public class DeleteServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String sid = request.getParameter("id");
        int id = Integer.parseInt(sid);
        EmployeeService.deleteById(id);
        EmployeeService.output(response, "viewServlet", "Sorry! Cannot delete the record#" + sid,
            "text/html", "UTF-8");
    }
}
