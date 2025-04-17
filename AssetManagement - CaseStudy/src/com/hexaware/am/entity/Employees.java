package com.hexaware.am.entity;

public class Employees {
	
	private int employeeId;
	private String name;
	private String department;
	private String email;
	private String password;

	// Default Constructor
	public Employees() {
		super();
		
	}
	
	public Employees(int id, String name, String department, String email, String password) {
	    setEmployeeId(id);
	    setName(name);
	    setDepartment(department);
	    setEmail(email);
	    setPassword(password);
	}
	
	// Getters and Setters
	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		if(employeeId <= 0) {
			throw new IllegalArgumentException("Employee ID must be a positive integer");
		}
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(name == null || name.trim().isEmpty() || !name.matches("^[A-Za-z ]{2,30}$")) {
			 throw new IllegalArgumentException("Name must contain only letters and spaces (2-30 characters).");
		}
		this.name = name.trim();
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
        if (department == null || !(department.equalsIgnoreCase("IT") ||
                                   department.equalsIgnoreCase("SALES") ||
                                   department.equalsIgnoreCase("HR") ||
                                   department.equalsIgnoreCase("MARKETING"))) {
            throw new IllegalArgumentException("Invalid department.");
        }
        this.department = department.toUpperCase();
    }
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email == null || email.trim().isEmpty() || 
		        !email.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,6}$")) {
		        throw new IllegalArgumentException("Invalid email format.");
		    }
		this.email = email.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
	    if (password == null || !password.matches("^(?=.*[A-Za-z])(?=.*\\d).{6,}$")) {
	        throw new IllegalArgumentException("Password must be at least 6 characters long and include at least one letter and one number.");
	    }
	    this.password = password;
	}

	
	// toString method
	@Override
	public String toString() {
		return "Employees [employeeId=" + employeeId + ", name=" + name + ", department=" + department + ", email="
				+ email + ", password=" + password + "]";
	}

	
	
}
