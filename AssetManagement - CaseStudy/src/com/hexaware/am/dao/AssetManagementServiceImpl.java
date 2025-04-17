package com.hexaware.am.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.hexaware.am.entity.Assets;
import com.hexaware.am.util.DBConnection;

public class AssetManagementServiceImpl implements AssetManagementService {
	public boolean addAsset(Assets asset) {
	    System.out.println("DEBUG: Inserting Status = " + asset.getStatus());

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
	            ps.setInt(7, asset.getEmployee().getEmployeeID());
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
	        ps.setInt(7, asset.getEmployee().getEmployeeID());
	        ps.setInt(8, asset.getAssetID());

	        int rows = ps.executeUpdate();
	        return rows > 0;

	    } catch (SQLException e) {
	        System.err.println("Error updating asset: " + e.getMessage());
	    }
	    return false;
	}

	@Override
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


	@Override
	public boolean allocateAsset(int assetId, int employeeId, String allocationDate) {
	    String sql = "UPDATE Assets SET Employee_id = ?, Status = 'In Use' WHERE Asset_id = ?";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setInt(1, employeeId);
	        ps.setInt(2, assetId);

	        int rows = ps.executeUpdate();
	        return rows > 0;

	    } catch (SQLException e) {
	        System.err.println("Error in allocating asset: " + e.getMessage());
	    }
	    return false;
	}

	
//
//	@Override
//	public boolean deallocateAsset(int assetId, int employeeId, String returnDate) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean performMaintenance(int assetId, String maintenanceDate, String description, double cost) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean reserveAsset(int assetId, int employeeId, String reservationDate, String startDate, String endDate) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean withdrawReservation(int reservationId) {
//		// TODO Auto-generated method stub
//		return false;
//	}
	
}
