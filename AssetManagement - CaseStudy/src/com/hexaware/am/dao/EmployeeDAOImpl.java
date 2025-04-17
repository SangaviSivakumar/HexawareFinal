package com.hexaware.am.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.hexaware.am.entity.Employees;
import com.hexaware.am.util.DBConnection;

public class EmployeeDAOImpl implements EmployeeDAO {

	public boolean addEmployee(Employees employee) {
	    String sql = "INSERT INTO employees (Employee_id, Employee_name, Department, Email, Password) VALUES (?, ?, ?, ?, ?)";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setInt(1, employee.getEmployeeID());
	        ps.setString(2, employee.getName());
	        ps.setString(3, employee.getDepartment());
	        ps.setString(4, employee.getEmail());
	        ps.setString(5, employee.getPassword());

	        int rows = ps.executeUpdate();
	        System.out.println("Rows affected: " + rows);  // DEBUG

	        return rows > 0;

	    } catch (Exception e) {
	        System.err.println("Error adding employee: " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }
	}
}
