package com.hexaware.am.entity;

import java.time.LocalDate;

public class Assets {
	
	private int assetId;
	private String name;
	private String type;
	private String serialNumber;
	private LocalDate purchaseDate;
	private String location;
	private String status;
	private Employees Employee;
	
	// Default Constructor
	public Assets() {
		super();
		
	}
	
	// Parameterized Constructor
	public Assets(int assetId, String name, String type, String serialNumber, LocalDate purchaseDate, String location,
			String status, Employees employee) {
		    setAssetId(assetId);
		    setName(name);
		    setType(type);
		    setSerialNumber(serialNumber);
		    setPurchaseDate(purchaseDate);
		    setLocation(location);
		    setStatus(status);
		    setEmployee(employee);
	}


	//Getters and Setters
	public int getAssetId() {
		return assetId;
	}

	public void setAssetId(int assetId) {
		if (assetId <= 0) {
            throw new IllegalArgumentException("Asset ID must be a positive number.");
        }
		this.assetId = assetId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null || name.trim().isEmpty() || !name.matches("^[A-Za-z0-9\\s]{2,100}$")) {
            throw new IllegalArgumentException("Name must be 2-100 characters and contain only letters, numbers, or spaces.");
        }
		this.name = name.trim();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Type is required.");
        }
		this.type = type.trim();
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		if (serialNumber == null || serialNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Serial number cannot be empty.");
        }
		this.serialNumber = serialNumber;
	}

	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(LocalDate purchaseDate) {
		if (purchaseDate == null) {
            throw new IllegalArgumentException("Purchase date must be in the past or today.");
        }
		this.purchaseDate = purchaseDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be empty.");
        }
		this.location = location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
	    if (!status.equals("In Use") && 
	        !status.equals("Decommissioned") && 
	        !status.equals("Under Maintenance")) {
	        throw new IllegalArgumentException("Invalid asset status.");
	    }
	    this.status = status;
	}
	
	public Employees getEmployee() {
		return Employee;
	}

	public void setEmployee(Employees employee) {
		Employee = employee;
	}
	
	// toString method 
	@Override
	public String toString() {
		return "Assets [assetId=" + assetId + ", name=" + name + ", type=" + type + ", serialNumber=" + serialNumber
				+ ", purchaseDate=" + purchaseDate + ", location=" + location + ", status=" + status + ", Employee="
				+ Employee + "]";
	}


}
