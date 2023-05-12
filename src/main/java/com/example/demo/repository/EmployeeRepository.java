package com.example.demo.repository;

import com.example.demo.connection.ConnectionManager;
import com.example.demo.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    private static OperationType theLatestOperationType;

    public static int create(Employee employee) {
        int status = 0;
        theLatestOperationType = OperationType.CREATE;
        Connection connection = ConnectionManager.createConnection();
        try {
            PreparedStatement ps = connection
                .prepareStatement("insert into users(name,email,country) values (?,?,?)");
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getCountry());

            status = ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        ConnectionManager.closeConnection();
        return status;
    }

    public static int update(Employee employee) {

        int status = 0;
        theLatestOperationType = OperationType.UPDATE;
        Connection connection = ConnectionManager.createConnection();
        try {
            PreparedStatement ps = connection
                .prepareStatement("update users set name=?,email=?,country=? where id=? and is_deleted=?");
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getCountry());
            ps.setInt(4, employee.getId());
            ps.setBoolean(5, false);

            status = ps.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        ConnectionManager.closeConnection();
        return status;
    }

    public static int delete(int id) {
        int status = 0;
        theLatestOperationType = OperationType.DELETE;
        Connection connection = ConnectionManager.createConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("update users set is_deleted=? where id=?");
            ps.setBoolean(1, true);
            ps.setInt(2, id);
            status = ps.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        ConnectionManager.closeConnection();
        return status;
    }

    public static Employee getEmployeeById(int id) {
        theLatestOperationType = OperationType.READ;
        Employee employee = new Employee();
        Connection connection = ConnectionManager.createConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("select * from users where id=? and is_deleted=?");
            ps.setInt(1, id);
            ps.setBoolean(2, false);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                employee.setId(rs.getInt(1));
                employee.setName(rs.getString(2));
                employee.setEmail(rs.getString(3));
                employee.setCountry(rs.getString(4));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        ConnectionManager.closeConnection();
        return employee;
    }

    public static List<Employee> getAllEmployees() {
        List<Employee> listEmployees = new ArrayList<>();
        theLatestOperationType = OperationType.READ_ALL;
        Connection connection = ConnectionManager.createConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("select * from users where is_deleted=?");
            ps.setBoolean(1, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Employee employee = new Employee();

                employee.setId(rs.getInt(1));
                employee.setName(rs.getString(2));
                employee.setEmail(rs.getString(3));
                employee.setCountry(rs.getString(4));

                listEmployees.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionManager.closeConnection();
        return listEmployees;
    }

    public static OperationType getTheLatestOperationType() {
        return theLatestOperationType;
    }
}
