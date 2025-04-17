package com.hexaware.am.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Scanner;

import com.hexaware.am.dao.AssetManagementServiceImpl;
import com.hexaware.am.dao.EmployeeDAOImpl;
import com.hexaware.am.entity.Assets;
import com.hexaware.am.entity.Employees;
import com.hexaware.am.util.DBConnection;

public class MenuHandler {
    private final EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl();
    private final AssetManagementServiceImpl assetService = new AssetManagementServiceImpl();
    
    // To add Employee
    public void addEmployee(Scanner sc) {
        System.out.println("\n--- Add Employee ---");

        try {
            int empId;
            while (true) {
                System.out.print("Enter Employee ID: ");
                empId = Integer.parseInt(sc.nextLine());
                try {
                    new Employees().setEmployeeID(empId);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            String name;
            while (true) {
                System.out.print("Enter Name: ");
                name = sc.nextLine();
                try {
                    new Employees().setName(name);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            String dept;
            while (true) {
                System.out.print("Enter Department (IT/SALES/HR/MARKETING): ");
                dept = sc.nextLine();
                try {
                    new Employees().setDepartment(dept);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            String email;
            while (true) {
                System.out.print("Enter Email: ");
                email = sc.nextLine();
                try {
                    new Employees().setEmail(email);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            String pwd;
            while (true) {
                System.out.print("Enter Password: ");
                pwd = sc.nextLine();
                try {
                    new Employees().setPassword(pwd);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            Employees emp = new Employees(empId, name, dept, email, pwd);
            boolean added = employeeDAO.addEmployee(emp);
            System.out.println(added ? "Employee added successfully!" : "Failed to add employee.");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    	// To add an Asset
    public void addAsset(Scanner sc) {
        System.out.println("\n--- Add Asset ---");
        try {
            System.out.print("Enter Asset ID: ");
            int assetId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter Asset Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Type (Laptop/Vehicle/etc): ");
            String type = sc.nextLine();

            System.out.print("Enter Serial Number: ");
            String serial = sc.nextLine();

            System.out.print("Enter Purchase Date (yyyy-mm-dd): ");
            LocalDate date = LocalDate.parse(sc.nextLine());

            System.out.print("Enter Location: ");
            String location = sc.nextLine();

            String status = "";
            while (true) {
                System.out.println("Select Status:");
                System.out.println("1. In Use");
                System.out.println("2. Decommissioned");
                System.out.println("3. Under Maintenance");
                System.out.print("Enter choice (1-3): ");
                int ch = Integer.parseInt(sc.nextLine());
                if (ch == 1) {
                    status = "In Use";
                    break;
                } else if (ch == 2) {
                    status = "Decommissioned";
                    break;
                } else if (ch == 3) {
                    status = "Under Maintenance";
                    break;
                } else {
                    System.out.println("Invalid choice. Try again.");
                }
            }

            System.out.print("Enter Employee ID (Owner): ");
            int ownerId = Integer.parseInt(sc.nextLine());
            Employees owner = new Employees();
            owner.setEmployeeID(ownerId);

            Assets asset = new Assets(assetId, name, type, serial, date, location, status, owner);

            boolean added = assetService.addAsset(asset);
            System.out.println(added ? "Asset added successfully!" : "Failed to add asset.");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    // To update an Asset
    public void updateAsset(Scanner sc) {
        System.out.println("\n--- Update Asset ---");

        try {
            System.out.print("Enter Asset ID to update: ");
            int assetId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter New Asset Name: ");
            String name = sc.nextLine();

            System.out.print("Enter New Type: ");
            String type = sc.nextLine();

            System.out.print("Enter New Serial Number: ");
            String serial = sc.nextLine();

            System.out.print("Enter New Purchase Date (yyyy-mm-dd): ");
            LocalDate date = LocalDate.parse(sc.nextLine());

            System.out.print("Enter New Location: ");
            String location = sc.nextLine();

            String status = "";
            while (true) {
                System.out.println("Select New Status:");
                System.out.println("1. In Use");
                System.out.println("2. Decommissioned");
                System.out.println("3. Under Maintenance");
                System.out.print("Enter choice (1-3): ");
                int statusChoice = Integer.parseInt(sc.nextLine());

                switch (statusChoice) {
                    case 1 -> status = "In Use";
                    case 2 -> status = "Decommissioned";
                    case 3 -> status = "Under Maintenance";
                    default -> {
                        System.out.println("Invalid choice. Try again.");
                        continue;
                    }
                }
                break;
            }

            System.out.print("Enter New Owner's Employee ID: ");
            int ownerId = Integer.parseInt(sc.nextLine());

            Employees owner = new Employees();
            owner.setEmployeeID(ownerId);

            Assets asset = new Assets(assetId, name, type, serial, date, location, status, owner);

            boolean updated = assetService.updateAsset(asset);

            System.out.println(updated ? "Asset updated successfully!" : "Failed to update asset.");
        } catch (Exception e) {
            System.err.println("Error updating asset: " + e.getMessage());
        }
    }

    // To Delete Assets
    public void deleteAsset(Scanner sc) {
        System.out.println("\n--- Delete Asset ---");
        try {
            System.out.print("Enter Asset ID to delete: ");
            int assetId = Integer.parseInt(sc.nextLine());

            boolean deleted = assetService.deleteAsset(assetId);
            System.out.println(deleted ? "Asset deleted successfully!" : "Failed to delete asset.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    // To Allocate Asset
    public void allocateAsset(Scanner sc) {
        System.out.println("\n--- Allocate Asset ---");

        try {
            System.out.print("Enter Asset ID to allocate: ");
            int assetId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter Employee ID to allocate to: ");
            int employeeId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter Allocation Date (yyyy-mm-dd): ");
            String date = sc.nextLine();

            boolean allocated = assetService.allocateAsset(assetId, employeeId, date);

            System.out.println(allocated ? "Asset allocated successfully!" : "Failed to allocate asset.");
        } catch (Exception e) {
            System.err.println("Error during allocation: " + e.getMessage());
        }
    }

    




}
