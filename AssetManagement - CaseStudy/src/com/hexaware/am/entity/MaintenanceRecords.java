package com.hexaware.am.entity;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class MaintenanceRecords {
	
	private int maintenanceId;
	private int AssetId;
	private String maintenanceDate;
	private String description;
	private Double cost;
	
	// Default Constructor
	public MaintenanceRecords() {
		super();
	}

	// Parameterized Constructor
	public MaintenanceRecords(int maintenaceId, int assetId, String maintenanceDate, String description,
			Double cost) {
		super();
		this.maintenanceId = maintenanceId;
		this.AssetId = assetId;
		this.maintenanceDate = maintenanceDate;
		this.description = description;
		this.cost = cost;
	}	
	
	// Getters and Setters
	public int getMaintenanceId() {
		return maintenanceId;
	}

	public void setMaintenanceId(int maintenanceId) {
		if (maintenanceId <= 0) {
            throw new IllegalArgumentException("Maintenance ID must be a positive number.");
        }
		this.maintenanceId = maintenanceId;
	}

	public int getAssetId() {
		return AssetId;
	}

	public void setAssetId(int assetId) {
	    if (assetId <= 0) {
	        throw new IllegalArgumentException("Asset ID must be a positive integer.");
	    }
	    this.AssetId = assetId;
	}

	public String getMaintenanceDate() {
		return maintenanceDate;
	}

	public void setMaintenanceDate(String maintenanceDate) {
	    if (maintenanceDate == null || maintenanceDate.trim().isEmpty()) {
	        throw new IllegalArgumentException("Maintenance date must be mentioned.");
	    }

	    try {
	        // Attempt to parse the date string to check if it's in the correct format
	        LocalDate parsedDate = LocalDate.parse(maintenanceDate);  // Only for validation
	    } catch (DateTimeParseException e) {
	        throw new IllegalArgumentException("Invalid date format. Please use YYYY-MM-DD.");
	    }

	    this.maintenanceDate = maintenanceDate;  // Store as string after validation
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
	
	// toString method 
	@Override
	public String toString() {
		return "MaintenanceRecords [maintenanceId=" + maintenanceId + ", AssetId=" + AssetId + ", maintenanceDate="
				+ maintenanceDate + ", description=" + description + ", cost=" + cost + "]";
	}


}
