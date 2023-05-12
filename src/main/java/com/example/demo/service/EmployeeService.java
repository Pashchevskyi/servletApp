package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.OperationType;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public class EmployeeService {

  private static List<Employee> employeeList;
  private static Employee employee;

  private static long affectedRows;

  public static List<Employee> getAll() {
    employeeList = EmployeeRepository.getAllEmployees();
    affectedRows = employeeList.size();
    return employeeList;
  }

  public static Employee getById(int id) {
    employee = EmployeeRepository.getEmployeeById(id);
    affectedRows = (employee.getName() != null || employee.getEmail() != null ||
        employee.getCountry() != null) ? 1 : 0;
    return employee;
  }

  public static void create(String name, String email, String country) {

    Employee employee = new Employee();

    employee.setName(name);
    employee.setEmail(email);
    employee.setCountry(country);

    affectedRows = EmployeeRepository.create(employee);
  }

  public static void update(int id, String name, String email, String country) {
    Employee employee = new Employee();
    employee.setId(id);
    employee.setName(name);
    employee.setEmail(email);
    employee.setCountry(country);

    affectedRows = EmployeeRepository.update(employee);
  }

  public static void deleteById(int id) {
    affectedRows = EmployeeRepository.delete(id);
  }

  public static void output(HttpServletResponse response, String uriToRedirect, String errorMessage,
      String mimeType, String charset) throws IOException {

    response.setContentType(mimeType);
    response.setCharacterEncoding(charset);

    PrintWriter out = response.getWriter();
    OperationType theLatestOperationType = EmployeeRepository.getTheLatestOperationType();
    if (theLatestOperationType == OperationType.READ_ALL) {
      for (Employee e : employeeList) {
        out.print(e);
      }
    }
    if (theLatestOperationType == OperationType.CREATE ||
        theLatestOperationType == OperationType.UPDATE ||
        theLatestOperationType == OperationType.DELETE) {
      if (affectedRows > 0) {
        response.sendRedirect(uriToRedirect);
      } else {
        out.print(errorMessage);
      }
    }
    if (theLatestOperationType == OperationType.READ) {
      if (affectedRows > 0) {
        out.println(employee);
      } else {
        out.print(errorMessage);
      }
    }
    out.close();
  }
}
