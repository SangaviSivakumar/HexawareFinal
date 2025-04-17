package com.hexaware.am.entity;

import java.time.LocalDate;

public class MaintenaceRecords {
	private int maintenaceID;
	private Assets AssetID;
	private LocalDate maintenanceDate;
	private String description;
	private Double cost;
	
	// Default Constructor
	public MaintenaceRecords() {
		super();
	}

	// Parameterized Constructor
	public MaintenaceRecords(int maintenaceID, Assets assetID, LocalDate maintenanceDate, String description,
			Double cost) {
		super();
		this.maintenaceID = maintenaceID;
		AssetID = assetID;
		this.maintenanceDate = maintenanceDate;
		this.description = description;
		this.cost = cost;
	}	
	
	// Getters and Setters
	public int getMaintainaceID() {
		return maintenaceID;
	}

	public void setMaintainaceID(int maintenaceID) {
		if (maintenaceID <= 0) {
            throw new IllegalArgumentException("Maintenance ID must be a positive number.");
        }
		this.maintenaceID = maintenaceID;
	}

	public Assets getAssetID() {
		return AssetID;
	}

	public void setAssetID(Assets assetID) {
		if (assetID == null || assetID.getAssetID() <= 0) {
            throw new IllegalArgumentException("A valid asset must be associated with the maintenance record.");
        }
		AssetID = assetID;
	}

	public LocalDate getMaintenanceDate() {
		return maintenanceDate;
	}

	public void setMaintenanceDate(LocalDate maintenanceDate) {
		if (maintenanceDate == null) {
            throw new IllegalArgumentException("Maintenance date must be mentioned.");
        }
		this.maintenanceDate = maintenanceDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
		this.description = description;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		if (cost < 0) {
            throw new IllegalArgumentException("Cost cannot be negative.");
        }
		this.cost = cost;
	}
	
	

}
