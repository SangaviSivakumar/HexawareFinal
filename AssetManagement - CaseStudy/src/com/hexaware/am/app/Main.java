package com.hexaware.am.app;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MenuHandler handler = new MenuHandler();

        while (true) {
            System.out.println("\n===== ASSET MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Employee");
            System.out.println("2. Add Asset");
            System.out.println("3. Update Asset");
            System.out.println("4. Delete Asset");
            System.out.println("5. Allocate Asset");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> handler.addEmployee(sc);
                case 2 -> handler.addAsset(sc);
                case 3 -> handler.updateAsset(sc);
                case 4 -> handler.deleteAsset(sc);
                case 5 -> handler.allocateAsset(sc);
                case 6 -> {
                    System.out.println("Exiting...");
                    sc.close();
                    return; 
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
