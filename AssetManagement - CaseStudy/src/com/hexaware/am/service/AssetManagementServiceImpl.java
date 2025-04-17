package com.hexaware.am.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.hexaware.am.dao.AssetManagementService;
import com.hexaware.am.entity.Assets;
import com.hexaware.am.myexceptions.AssetNotFoundException;
import com.hexaware.am.myexceptions.AssetNotMaintainException;
import com.hexaware.am.util.DBConnection;

public class AssetManagementServiceImpl implements AssetManagementService {
	
	
	public boolean addAsset(Assets asset) {
	 

	    String query = "INSERT INTO Assets (Asset_name, Asset_type, Serial_number, Purchase_date, Location, Status, Employee_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(query)) {

	        ps.setString(1, asset.getName());
	        ps.setString(2, asset.getType());
	        ps.setString(3, asset.getSerialNumber());
	        ps.setDate(4, java.sql.Date.valueOf(asset.getPurchaseDate()));
	        ps.setString(5, asset.getLocation());
	        ps.setString(6, asset.getStatus());

	        if (asset.getEmployee() != null) {
	            ps.setInt(7, asset.getEmployee().getEmployeeId());
	        } else {
	            ps.setNull(7, java.sql.Types.INTEGER); 
	        }

	        int rows = ps.executeUpdate();
	        return rows > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return false;
	}

	public boolean updateAsset(Assets asset) {
		
	    String sql = "UPDATE assets SET Asset_name=?, Asset_type=?, Serial_number=?, Purchase_date=?, Location=?, Status=?, Employee_id=? WHERE Asset_id=?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, asset.getName());
	        ps.setString(2, asset.getType());
	        ps.setString(3, asset.getSerialNumber());
	        ps.setDate(4, java.sql.Date.valueOf(asset.getPurchaseDate()));
	        ps.setString(5, asset.getLocation());
	        ps.setString(6, asset.getStatus());
	        ps.setInt(7, asset.getEmployee().getEmployeeId());
	        ps.setInt(8, asset.getAssetId());

	        int rows = ps.executeUpdate();
	        return rows > 0;

	    } catch (SQLException e) {
	        System.err.println("Error updating asset: " + e.getMessage());
	    }
	    return false;
	}

	
	public boolean deleteAsset(int assetID) {
	    String sql = "DELETE FROM Assets WHERE Asset_id = ?";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setInt(1, assetID);
	        int rows = ps.executeUpdate();
	        return rows > 0;

	    } catch (SQLException e) {
	        System.err.println("Error deleting asset: " + e.getMessage());
	    }
	    return false;
	}
	
	
	public boolean allocateAsset(int assetId, int employeeId, String allocationDate, String returnDate)
	        throws AssetNotFoundException, AssetNotMaintainException {

	    String sqlUpdateAsset = "UPDATE Assets SET Employee_id = ?, Status = 'In Use' WHERE Asset_id = ?";
	    String sqlInsertAllocation = "INSERT INTO AssetAllocations (Asset_id, Employee_id, Allocation_date, Return_date) VALUES (?, ?, ?, ?)";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement psUpdateAsset = conn.prepareStatement(sqlUpdateAsset);
	         PreparedStatement psInsertAllocation = conn.prepareStatement(sqlInsertAllocation)) {

	        
	        if (!isAssetExists(conn, assetId)) {
	            throw new AssetNotFoundException("Asset with ID " + assetId + " not found.");
	        }

	        
	        LocalDate lastMaintained = getLastMaintenanceDate(conn, assetId);
	        if (lastMaintained != null && lastMaintained.isBefore(LocalDate.now().minusYears(2))) {
	            throw new AssetNotMaintainException("Asset " + assetId + " hasn't been maintained in the last 2 years.");
	        }

	        
	        if (!isEmployeeExists(conn, employeeId)) {
	            System.out.println("Employee with ID " + employeeId + " does not exist.");
	            return false;
	        }

	        
	        LocalDate allocDate = LocalDate.parse(allocationDate);
	        LocalDate retDate = LocalDate.parse(returnDate);
	        if (retDate.isBefore(allocDate)) {
	            System.out.println("Return date cannot be before allocation date.");
	            return false;
	        }
	        psUpdateAsset.setInt(1, employeeId);
	        psUpdateAsset.setInt(2, assetId);
	        if (psUpdateAsset.executeUpdate() == 0) {
	            System.out.println("Failed to update asset.");
	            return false;
	        }
	        psInsertAllocation.setInt(1, assetId);
	        psInsertAllocation.setInt(2, employeeId);
	        psInsertAllocation.setDate(3, Date.valueOf(allocDate));
	        psInsertAllocation.setDate(4, Date.valueOf(retDate));
	        if (psInsertAllocation.executeUpdate() > 0) {
	            System.out.println("Asset allocated successfully and logged in AssetAllocations table.");
	            return true;
	        } else {
	            System.out.println("Failed to insert allocation record.");
	        }

	    } catch (SQLException e) {
	        System.err.println("SQL Error during asset allocation: " + e.getMessage());
	    }

	    return false;
	}


	public boolean deallocateAsset(int assetId, int employeeId, String returnDate) {
	    String sqlUpdateAsset = "UPDATE Assets SET Employee_id = NULL, Status = 'Available' WHERE Asset_id = ?";
	    String sqlDeleteAllocation = "DELETE FROM AssetAllocations WHERE Asset_id = ? AND Employee_id = ?";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement psUpdateAsset = conn.prepareStatement(sqlUpdateAsset);
	         PreparedStatement psDeleteAllocation = conn.prepareStatement(sqlDeleteAllocation)) {
	        String allocationCheckQuery = "SELECT COUNT(*) FROM AssetAllocations WHERE Asset_id = ? AND Employee_id = ?";
	        try (PreparedStatement allocationCheckPs = conn.prepareStatement(allocationCheckQuery)) {
	            allocationCheckPs.setInt(1, assetId);
	            allocationCheckPs.setInt(2, employeeId);
	            ResultSet rs = allocationCheckPs.executeQuery();
	            if (rs.next() && rs.getInt(1) == 0) {
	                System.out.println("No active allocation found for this asset and employee.");
	                return false;
	            }
	        }

	        
	        psDeleteAllocation.setInt(1, assetId);
	        psDeleteAllocation.setInt(2, employeeId);
	        int rowsDeleted = psDeleteAllocation.executeUpdate();
	        if (rowsDeleted == 0) {
	            System.out.println("Failed to delete allocation from AssetAllocations.");
	            return false;
	        }

	        
	        psUpdateAsset.setInt(1, assetId);
	        int rowsUpdated = psUpdateAsset.executeUpdate();
	        if (rowsUpdated > 0) {
	            System.out.println("Asset deallocated successfully!");
	            return true;
	        } else {
	            System.out.println("Failed to update asset in Assets table.");
	            return false;
	        }

	    } catch (SQLException e) {
	        System.err.println("Error during deallocation: " + e.getMessage());
	        return false;
	    }
	}
	
	@Override
    public boolean performMaintenance(int assetId, String maintenanceDate, String description, double cost) {
        try (Connection conn = DBConnection.getConnection()) {

            
            String checkQuery = "SELECT * FROM Assets WHERE Asset_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(checkQuery)) {
                ps.setInt(1, assetId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        System.err.println(new AssetNotFoundException("Asset ID " + assetId + " not found."));
                        return false;
                    }

                    String status = rs.getString("Status");
                    LocalDate purchaseDate = rs.getDate("Purchase_date").toLocalDate();
                    LocalDate maintenanceDateParsed = LocalDate.parse(maintenanceDate);

                    
                    if (purchaseDate.plusYears(2).isAfter(maintenanceDateParsed)) {
                        System.err.println(new AssetNotMaintainException("Asset with ID " + assetId + " is not eligible for maintenance (not 2 years old)."));
                        return false;
                    }

                    
                    if ("Under Maintenance".equalsIgnoreCase(status) || "Decommissioned".equalsIgnoreCase(status)) {
                        System.err.println(new AssetNotMaintainException("Asset is either under maintenance or decommissioned."));
                        return false;
                    }
                }
            }

           
            String insertMaintenanceSQL = "INSERT INTO MaintenanceRecords (Asset_id, Maintenance_date, Description, Cost) VALUES (?, ?, ?, ?)";
            String updateAssetStatusSQL = "UPDATE Assets SET Status = 'Under Maintenance' WHERE Asset_id = ?";

            try (
                PreparedStatement insertPs = conn.prepareStatement(insertMaintenanceSQL);
                PreparedStatement updatePs = conn.prepareStatement(updateAssetStatusSQL)
            ) {
                insertPs.setInt(1, assetId);
                insertPs.setDate(2, java.sql.Date.valueOf(LocalDate.parse(maintenanceDate)));
                insertPs.setString(3, description);
                insertPs.setDouble(4, cost);

                int inserted = insertPs.executeUpdate();

                updatePs.setInt(1, assetId);
                int updated = updatePs.executeUpdate();

                return inserted > 0 && updated > 0;
            }

        } catch (SQLException e) {
            System.err.println("SQL Error during maintenance: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }

        return false;
    }
	
	public boolean reserveAsset(int assetId, int employeeId, String reservationDate, String startDate, String endDate) {
	    if (!assetExists(assetId)) {
	        throw new AssetNotFoundException("Asset ID " + assetId + " not found.");
	    }

	    if (!employeeExists(employeeId)) {
	        throw new RuntimeException("Employee ID " + employeeId + " not found.");
	    }

	    if (LocalDate.parse(startDate).isAfter(LocalDate.parse(endDate))) {
	        throw new IllegalArgumentException("Start date cannot be after end date.");
	    }

	    String sql = "INSERT INTO Reservations (Asset_id, Employee_id, Reservation_date, Start_date, End_date) VALUES (?, ?, ?, ?, ?)";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setInt(1, assetId);
	        ps.setInt(2, employeeId);
	        ps.setDate(3, java.sql.Date.valueOf(LocalDate.parse(reservationDate)));
	        ps.setDate(4, java.sql.Date.valueOf(LocalDate.parse(startDate)));
	        ps.setDate(5, java.sql.Date.valueOf(LocalDate.parse(endDate)));

	        return ps.executeUpdate() > 0;

	    } catch (SQLException e) {
	        throw new RuntimeException("Failed to reserve asset due to DB error: " + e.getMessage(), e);
	    }
	}

	private boolean assetExists(int assetId) {
	    String query = "SELECT COUNT(*) FROM Assets WHERE Asset_id = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(query)) {
	        ps.setInt(1, assetId);
	        ResultSet rs = ps.executeQuery();
	        return rs.next() && rs.getInt(1) > 0;
	    } catch (SQLException e) {
	        System.err.println("Error checking asset existence: " + e.getMessage());
	    }
	    return false;
	}


	private boolean employeeExists(int employeeId) {
	    String query = "SELECT COUNT(*) FROM Employees WHERE Employee_id = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(query)) {
	        ps.setInt(1, employeeId);
	        ResultSet rs = ps.executeQuery();
	        return rs.next() && rs.getInt(1) > 0;
	    } catch (SQLException e) {
	        System.err.println("Error checking employee existence: " + e.getMessage());
	    }
	    return false;
	}

	
	
	public boolean withdrawReservation(int reservationId) {
	    String sql = "DELETE FROM Reservations WHERE Reservation_id = ?";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setInt(1, reservationId);
	        return ps.executeUpdate() > 0;

	    } catch (SQLException e) {
	        System.err.println("Error withdrawing reservation: " + e.getMessage());
	    }
	    return false;
	}
	
	private boolean isAssetExists(Connection conn, int assetId) throws SQLException {
	    String query = "SELECT COUNT(*) FROM Assets WHERE Asset_id = ?";
	    try (PreparedStatement ps = conn.prepareStatement(query)) {
	        ps.setInt(1, assetId);
	        ResultSet rs = ps.executeQuery();
	        return rs.next() && rs.getInt(1) > 0;
	    }
	}

	private boolean isEmployeeExists(Connection conn, int employeeId) throws SQLException {
	    String query = "SELECT COUNT(*) FROM Employees WHERE Employee_id = ?";
	    try (PreparedStatement ps = conn.prepareStatement(query)) {
	        ps.setInt(1, employeeId);
	        ResultSet rs = ps.executeQuery();
	        return rs.next() && rs.getInt(1) > 0;
	    }
	}

	private LocalDate getLastMaintenanceDate(Connection conn, int assetId) throws SQLException {
	    String query = "SELECT MAX(Maintenance_date) FROM MaintenanceLogs WHERE Asset_id = ?";
	    try (PreparedStatement ps = conn.prepareStatement(query)) {
	        ps.setInt(1, assetId);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next() && rs.getDate(1) != null) {
	            return rs.getDate(1).toLocalDate();
	        }
	    }
	    return null;
	}

	@Override
	public boolean addMaintenanceRecord(int assetId, String issue, String maintenanceDate) throws SQLException {
	    String sql = "INSERT INTO maintenance_records (asset_id, issue_description, maintenance_date) VALUES (?, ?, ?)";
	    
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, assetId);
	        ps.setString(2, issue);
	        ps.setString(3, maintenanceDate);
	        int result = ps.executeUpdate();
	        return result > 0;
	    }
	}

}






