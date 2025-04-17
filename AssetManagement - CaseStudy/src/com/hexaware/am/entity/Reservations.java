package com.hexaware.am.entity;

import java.time.LocalDate;

public class Reservations {
	
	private int reservationId;
	private Assets AssetId;
	private Employees EmployeeId;
	private String reservationDate;
	private String startDate;
	private String endDate;
	private String status;
	
	// Default Constructor
	public Reservations() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Parameterized Constructor
	public Reservations(int reservationId, Assets assetId, Employees employeeId, String reservationDate, String startDate,
			String endDate, String status) {
		super();
		this.reservationId = reservationId;
		AssetId = assetId;
		EmployeeId = employeeId;
		this.reservationDate = reservationDate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
	}

	// Getters and Setters
	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		if (reservationId <= 0) {
            throw new IllegalArgumentException("Reservation ID must be a positive number.");
        }
		this.reservationId = reservationId;
	}

	public Assets getAssetId() {
		return AssetId;
	}

	public void setAssetId(Assets assetId) {
		if (assetId == null || assetId.getAssetId() <= 0) {
            throw new IllegalArgumentException("A valid asset must be selected for reservation.");
        }
		this.AssetId = assetId;
	}

	public Employees getEmployeeId() {
		return EmployeeId;
	}

	public void setEmployeeId(Employees employeeId) {
		if (employeeId == null || employeeId.getEmployeeId() <= 0) {
            throw new IllegalArgumentException("A valid employee must be assigned to the reservation.");
        }
		this.EmployeeId = employeeId;
	}

	public String getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(String reservationDate) {
        if (reservationDate == null) {
            throw new IllegalArgumentException("Reservation date cannot be null.");
        }
        LocalDate resDate = LocalDate.parse(reservationDate);
        if (resDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Reservation date must not be in the future.");
        }
        this.reservationDate = reservationDate;
    }


    public void setStartDate(String startDate) {
        if (startDate == null || reservationDate == null) {
            throw new IllegalArgumentException("Start date and reservation date cannot be null.");
        }
        LocalDate start = LocalDate.parse(startDate);
        LocalDate resDate = LocalDate.parse(reservationDate);
        if (start.isBefore(resDate)) {
            throw new IllegalArgumentException("Start date must be on or after the reservation date.");
        }
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setEndDate(String endDate) {
        if (endDate == null || startDate == null) {
            throw new IllegalArgumentException("End date and start date cannot be null.");
        }
        LocalDate end = LocalDate.parse(endDate);
        LocalDate start = LocalDate.parse(startDate);
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End date must be on or after the start date.");
        }
    }
	
    
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Reservation status is required.");
        }

        String[] allowedStatuses = {"pending", "approved", "canceled"};
        boolean valid = false;
        for (String s : allowedStatuses) {
            if (s.equalsIgnoreCase(status.trim())) {
                valid = true;
                break;
            }
        }

        if (!valid) {
            throw new IllegalArgumentException("Status must be one of: pending, approved, canceled.");
        }

        this.status = status.trim().toLowerCase();  
	}
	
	// toString method
	@Override
	public String toString() {
		return "Reservations [reservationId=" + reservationId + ", AssetId=" + AssetId + ", EmployeeId=" + EmployeeId
				+ ", reservationDate=" + reservationDate + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", status=" + status + "]";
	}	

}
