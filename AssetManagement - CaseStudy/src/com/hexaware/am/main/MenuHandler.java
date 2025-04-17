package com.hexaware.am.main;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.hexaware.am.entity.Assets;
import com.hexaware.am.entity.Employees;
import com.hexaware.am.myexceptions.AssetNotFoundException;
import com.hexaware.am.myexceptions.AssetNotMaintainException;
import com.hexaware.am.service.AssetManagementServiceImpl;
import com.hexaware.am.service.EmployeeDAOImpl;
import com.hexaware.am.service.ReportImpl;

public class MenuHandler {
    private final EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl();
    private final AssetManagementServiceImpl assetService = new AssetManagementServiceImpl();
    private final ReportImpl reportService = new ReportImpl();
    
    // To add Employee
    public void addEmployee(Scanner sc) {
        System.out.println("\n--- Add Employee ---");

        try {
            int empId;
            while (true) {
                System.out.print("Enter Employee ID: ");
                empId = Integer.parseInt(sc.nextLine());
                try {
                    new Employees().setEmployeeId(empId);
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
                    Employees employees = new Employees();
					employees.setPassword(pwd); 
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
            owner.setEmployeeId(ownerId);

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
            owner.setEmployeeId(ownerId);

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
    public void allocateAsset(Scanner sc) throws AssetNotFoundException, AssetNotMaintainException {
        System.out.println("\n--- Allocate Asset ---");

        int assetId;
        while (true) {
            System.out.print("Enter Asset ID to allocate: ");
            String input = sc.nextLine().trim();
            try {
                assetId = Integer.parseInt(input);
                if (assetId <= 0) throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid Asset ID. Must be a positive number.");
            }
        }

        int employeeId;
        while (true) {
            System.out.print("Enter Employee ID to allocate to: ");
            String input = sc.nextLine().trim();
            try {
                employeeId = Integer.parseInt(input);
                if (employeeId <= 0) throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid Employee ID. Must be a positive number.");
            }
        }

        String allocationDateStr;
        while (true) {
            System.out.print("Enter Allocation Date (yyyy-mm-dd): ");
            allocationDateStr = sc.nextLine().trim();
            try {
                LocalDate.parse(allocationDateStr);
                break;
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter in yyyy-mm-dd format.");
            }
        }

        String returnDateStr;
        while (true) {
            System.out.print("Enter Return Date (yyyy-mm-dd): ");
            returnDateStr = sc.nextLine().trim();
            try {
                LocalDate allocationDateObj = LocalDate.parse(allocationDateStr);
                LocalDate returnDateObj = LocalDate.parse(returnDateStr);

                if (returnDateObj.isBefore(allocationDateObj)) {
                    System.out.println("Return date cannot be before allocation date. Try again.");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter in yyyy-mm-dd format.");
            }
        }

       
        boolean success = assetService.allocateAsset(assetId, employeeId, allocationDateStr, returnDateStr);

        if (success) {
            System.out.println("Asset allocated successfully!");
        } else {
           
            throw new AssetNotFoundException("Asset or Employee not found. Allocation failed.");
        }
    }

    
    // To Deallocate Asset
    public void deallocateAsset(Scanner sc) {
        System.out.println("\n--- Deallocate Asset ---");

        int assetId;
        while (true) {
            System.out.print("Enter Asset ID: ");
            String assetInput = sc.nextLine().trim();
            try {
                assetId = Integer.parseInt(assetInput);
                if (assetId <= 0) {
                    System.out.println("Asset ID must be a positive number.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer for Asset ID.");
            }
        }

        int employeeId;
        while (true) {
            System.out.print("Enter Employee ID: ");
            String employeeInput = sc.nextLine().trim();
            try {
                employeeId = Integer.parseInt(employeeInput);
                if (employeeId <= 0) {
                    System.out.println("Employee ID must be a positive number.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer for Employee ID.");
            }
        }

        String returnDate;
        while (true) {
            System.out.print("Enter Return Date (yyyy-mm-dd): ");
            String dateStr = sc.nextLine().trim();
            try {
                LocalDate parsedDate = LocalDate.parse(dateStr);
                returnDate = parsedDate.toString();
                break;
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter in yyyy-mm-dd format.");
            }
        }

        boolean success = assetService.deallocateAsset(assetId, employeeId, returnDate);

        if (success) {
            System.out.println("Asset deallocated successfully.");
        } else {
            System.out.println("Failed to deallocate asset. Either no active allocation exists or an error occurred.");
        }
    }
    
    // To Perform Maintenance
    public void performMaintenance(Scanner sc) {
        System.out.println("\n--- Perform Maintenance ---");

        try {
            int assetId;
            while (true) {
                System.out.print("Enter Asset ID: ");
                String input = sc.nextLine().trim();
                try {
                    assetId = Integer.parseInt(input);
                    if (assetId <= 0) throw new NumberFormatException();
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Asset ID. Please enter a positive integer.");
                }
            }

            String maintenanceDate;
            while (true) {
                System.out.print("Enter Maintenance Date (yyyy-mm-dd): ");
                maintenanceDate = sc.nextLine().trim();
                try {
                    LocalDate.parse(maintenanceDate);
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid date format. Please try again.");
                }
            }

            System.out.print("Enter Description: ");
            String description = sc.nextLine().trim();
            if (description.isEmpty()) {
                System.out.println("Description cannot be empty.");
                return;
            }

            double cost;
            while (true) {
                System.out.print("Enter Maintenance Cost: ");
                String costInput = sc.nextLine().trim();
                try {
                    cost = Double.parseDouble(costInput);
                    if (cost < 0) throw new NumberFormatException();
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid cost. Please enter a non-negative number.");
                }
            }

            boolean success = assetService.performMaintenance(assetId, maintenanceDate, description, cost);
            System.out.println(success ? "Maintenance recorded successfully." : "Failed to record maintenance.");

        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }


    // To Reserve Asset
    public void reserveAsset(Scanner sc) {
        try {
            System.out.print("Enter Asset ID: ");
            int assetId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter Employee ID: ");
            int employeeId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter Reservation Date (yyyy-mm-dd): ");
            String reservationDate = sc.nextLine();

            System.out.print("Enter Start Date (yyyy-mm-dd): ");
            String startDate = sc.nextLine();

            System.out.print("Enter End Date (yyyy-mm-dd): ");
            String endDate = sc.nextLine();

            boolean success = assetService.reserveAsset(assetId, employeeId, reservationDate, startDate, endDate);
            System.out.println(success ? "Asset reserved." : "Reservation failed.");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // To withdraw Reservation
    public void withdrawReservation(Scanner sc) {
        try {
            System.out.print("Enter Reservation ID to withdraw: ");
            int reservationId = Integer.parseInt(sc.nextLine());

            boolean success = assetService.withdrawReservation(reservationId);
            System.out.println(success ? "Reservation withdrawn." : "Failed to withdraw reservation.");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    // To generate Report
    public void generateReport(Scanner sc) {
        while (true) {
            System.out.println("\n=== Report Generation Menu ===");
            System.out.println("1. Asset Allocation Report");
            System.out.println("2. Maintenance Report");
            System.out.println("3. Reservation Report");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    displayReport(reportService.getAllAssetAllocations(), "Asset Allocation Report");
                    break;
                case 2:
                    displayReport(reportService.getAllMaintenanceRecords(), "Maintenance Report");
                    break;
                case 3:
                    displayReport(reportService.getAllReservations(), "Reservation Report");
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void displayReport(List<Map<String, String>> reportData, String title) {
        System.out.println("\n=== " + title + " ===");

        if (reportData.isEmpty()) {
            System.out.println("No records found.");
            return;
        }
        Set<String> headers = reportData.get(0).keySet();
        int columnWidth = 20;      
        System.out.println("-".repeat(columnWidth * headers.size()));
        for (String header : headers) {
            System.out.printf("%-" + columnWidth + "s", header);
        }
        System.out.println();
        System.out.println("-".repeat(columnWidth * headers.size()));

        for (Map<String, String> record : reportData) {
            for (String header : headers) {
                System.out.printf("%-" + columnWidth + "s", record.getOrDefault(header, ""));
            }
            System.out.println();
        }

        System.out.println("-".repeat(columnWidth * headers.size()));
    }
   
}
