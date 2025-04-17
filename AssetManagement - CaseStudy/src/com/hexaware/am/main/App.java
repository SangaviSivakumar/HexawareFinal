package com.hexaware.am.main;

import java.util.Scanner;

import com.hexaware.am.myexceptions.AssetNotFoundException;
import com.hexaware.am.myexceptions.AssetNotMaintainException;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MenuHandler handler = new MenuHandler();

        while (true) {
            System.out.println("\n===== ASSET MANAGEMENT SYSTEM =====");
            System.out.println("1. Employee Management");
            System.out.println("2. Asset Management");
            System.out.println("3. Asset Allocation");
            System.out.println("4. Asset Maintenance");
            System.out.println("5. Asset Reservation");
            System.out.println("6. Generate Reports");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(sc.nextLine());

            try {
                switch (choice) {
                    case 1 -> {
                        System.out.println("\n--- Employee Management ---");
                        System.out.println("1. Add Employee");
                        int empChoice = Integer.parseInt(sc.nextLine());
                        if (empChoice == 1) handler.addEmployee(sc);
                    }
                    case 2 -> {
                        System.out.println("\n--- Asset Management ---");
                        System.out.println("1. Add Asset");
                        System.out.println("2. Update Asset");
                        System.out.println("3. Delete Asset");
                        System.out.print("Enter your choice: ");
                        int assetChoice = Integer.parseInt(sc.nextLine());
                        switch (assetChoice) {
                            case 1 -> handler.addAsset(sc);
                            case 2 -> handler.updateAsset(sc);
                            case 3 -> handler.deleteAsset(sc);
                            default -> System.out.println("Invalid Asset Management choice.");
                        }
                    }
                    case 3 -> {
                        System.out.println("\n--- Asset Allocation ---");
                        System.out.println("1. Allocate Asset");
                        System.out.println("2. Deallocate Asset");
                        System.out.print("Enter your choice: ");
                        int allocChoice = Integer.parseInt(sc.nextLine());
                        switch (allocChoice) {
                            case 1 -> handler.allocateAsset(sc);
                            case 2 -> handler.deallocateAsset(sc);
                            default -> System.out.println("Invalid Allocation choice.");
                        }
                    }
                    case 4 -> {
                        System.out.println("\n--- Asset Maintenance ---");
                        handler.performMaintenance(sc);
                    }
                    case 5 -> {
                        System.out.println("\n--- Asset Reservation ---");
                        System.out.println("1. Reserve Asset");
                        System.out.println("2. Withdraw Reservation");
                        System.out.print("Enter your choice: ");
                        int resChoice = Integer.parseInt(sc.nextLine());
                        switch (resChoice) {
                            case 1 -> handler.reserveAsset(sc);
                            case 2 -> handler.withdrawReservation(sc);
                            default -> System.out.println("Invalid Reservation choice.");
                        }
                    }
                    case 6 -> handler.generateReport(sc);
                    case 7 -> {
                        System.out.println("Exiting...");
                        sc.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (AssetNotFoundException | AssetNotMaintainException ex) {
                System.err.println("Exception: " + ex.getMessage());
            } catch (Exception e) {
                System.err.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }
}
