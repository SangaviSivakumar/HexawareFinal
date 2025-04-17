package com.hexaware.am.dao;

import java.sql.SQLException;

import com.hexaware.am.entity.Assets;
import com.hexaware.am.myexceptions.AssetNotFoundException;
import com.hexaware.am.myexceptions.AssetNotMaintainException;

public interface AssetManagementService {
	
	boolean addAsset(Assets asset);
	boolean updateAsset(Assets asset);
    boolean deleteAsset(int assetID);
    boolean allocateAsset(int assetId, int employeeId, String allocationDate, String returnDate) throws AssetNotFoundException, AssetNotMaintainException;
 	boolean deallocateAsset(int assetId, int employeeId, String returnDate);
 	boolean performMaintenance(int assetId, String maintenanceDate,String description, double cost);
	boolean reserveAsset(int assetId, int employeeId, String reservationDate, String startDate, String endDate);
	boolean withdrawReservation(int reservationId);
	boolean addMaintenanceRecord(int assetId, String issue, String maintenanceDate) throws SQLException;

}
