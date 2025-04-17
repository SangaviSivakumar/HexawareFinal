package com.hexaware.am.entity;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AssetAllocations {
	
	private int allocationId;
	private Assets assetId;
	private Employees employeeId;
	private String allocationDate;
	private String returnDate;

	// Default Constructor
	public AssetAllocations() {
		super();
	}

	// Parameterized Constructor
	public AssetAllocations(int allocationId, Assets assetId, Employees employeeId, String allocationDate, String returnDate) {
		super();
		setAllocationId(allocationId);
		setAssetId(assetId);
		setEmployeeId(employeeId);
		setAllocationDate(allocationDate);
		setReturnDate(returnDate); 
	}

	// Getters and Setters with validation
	public int getAllocationId() {
		return allocationId;
	}

	public void setAllocationId(int allocationId) {
		if (allocationId <= 0) {
			throw new IllegalArgumentException("Allocation ID must be positive.");
		}
		this.allocationId = allocationId;
	}

	public Assets getAssetId() {
		return assetId;
	}

	public void setAssetId(Assets assetId) {
		if (assetId == null || assetId.getAssetId() <= 0) {
			throw new IllegalArgumentException("A valid asset must be assigned.");
		}
		this.assetId = assetId;
	}

	public Employees getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Employees employeeId) {
		if (employeeId == null || employeeId.getEmployeeId() <= 0) {
			throw new IllegalArgumentException("A valid employee must be assigned.");
		}
		this.employeeId = employeeId;
	}

	public String getAllocationDate() {
		return allocationDate;
	}

	public void setAllocationDate(String allocationDate) {
		if (allocationDate == null) {
			throw new IllegalArgumentException("Allocation date cannot be null.");
		}
		this.allocationDate = allocationDate;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		if (returnDate == null) {
			throw new IllegalArgumentException("Return date cannot be null.");
		}
		this.allocationDate = allocationDate;
	}
	
	// toString method
	@Override
	public String toString() {
		return "AssetAllocations [allocationId=" + allocationId + ", assetId=" + assetId + ", employeeId=" + employeeId
				+ ", allocationDate=" + allocationDate + ", returnDate=" + returnDate + "]";
	}



}
