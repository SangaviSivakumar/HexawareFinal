package com.hexaware.am.test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.hexaware.am.entity.Assets;
import com.hexaware.am.myexceptions.AssetNotFoundException;
import com.hexaware.am.myexceptions.EmployeeNotFoundException;
import com.hexaware.am.service.AssetManagementServiceImpl;

public class AssetManagementServiceTest {

    static AssetManagementServiceImpl assetService;

    @BeforeAll
    public static void setUp() {
        assetService = new AssetManagementServiceImpl();
    }

    @Test
    public void testAssetCreatedSuccessfully() {
        Assets asset = new Assets();
        asset.setName("HP Laptop");
        asset.setType("Laptop");
        asset.setSerialNumber("SN79865");
        asset.setPurchaseDate(LocalDate.parse("2025-04-13"));
        asset.setLocation("Branch Office");
        asset.setStatus("In Use");

        boolean result = assetService.addAsset(asset);

        assertTrue(result, "Asset should be created successfully.");
    }


    @Test
    public void testAssetReservedSuccessfully() {
        int assetId = 3; 
        int employeeId = 1; 
        String reservationDate = "2025-04-16";
        String startDate = "2025-04-17";
        String endDate = "2025-04-20";

        boolean result = assetService.reserveAsset(assetId, employeeId, reservationDate, startDate, endDate);

        assertTrue(result, "Asset should be reserved successfully.");
    }

    @Test
    public void testInvalidAssetId() {
        int invalidAssetId = 20;
        int employeeId = 1;
        String reservationDate = "2025-04-16";
        String startDate = "2025-04-17";
        String endDate = "2025-04-20";

        AssetNotFoundException exception = assertThrows(AssetNotFoundException.class, () -> {
            assetService.reserveAsset(invalidAssetId, employeeId, reservationDate, startDate, endDate);
        });

        System.out.println("Exception caught as expected: " + exception.getMessage());
        assertTrue(exception.getMessage().contains("not found"));
    }
    
    @Test
    public void testInvalidEmployeeId() {
        int assetId = 3;
        int invalidEmployeeId = 20; 
        String reservationDate = "2025-04-16";
        String startDate = "2025-04-17";
        String endDate = "2025-04-20";

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            assetService.reserveAsset(assetId, invalidEmployeeId, reservationDate, startDate, endDate);
        });
        System.out.println("Exception caught as expected: " + exception.getMessage());
        assertTrue(exception.getMessage().contains("Employee ID " + invalidEmployeeId + " not found."));
    }

    @AfterAll
    public static void tearDown() {
        assetService = null;
    }
}
