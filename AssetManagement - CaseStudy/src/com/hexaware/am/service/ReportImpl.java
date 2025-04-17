package com.hexaware.am.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hexaware.am.dao.Report;
import com.hexaware.am.util.DBConnection;

public class ReportImpl implements Report {

    @Override
    public List<Map<String, String>> getAllAssetAllocations() {
        String query = "SELECT aa.Allocation_id, aa.asset_id, a.Asset_name, a.Asset_type, " +
                       "aa.employee_id, e.Employee_name, e.Department, " +
                       "aa.Allocation_date, aa.Return_Date " +
                       "FROM AssetAllocations aa " +
                       "JOIN Assets a ON aa.asset_id = a.Asset_id " +
                       "JOIN Employees e ON aa.employee_id = e.Employee_id";

        return fetchReportData(query);
    }

    @Override
    public List<Map<String, String>> getAllMaintenanceRecords() {
        String query = "SELECT m.Maintainance_id, m.Asset_id, a.Asset_name, a.Asset_type, " +
                       "m.Maintainace_dat, m.Description, m.Cost " +
                       "FROM MaintainanceRecords m " +
                       "JOIN Assets a ON m.Asset_id = a.Asset_id";

        return fetchReportData(query);
    }

    @Override
    public List<Map<String, String>> getAllReservations() {
        String query = "SELECT r.Reservation_id, r.asset_id, a.Asset_name, a.Asset_type, " +
                       "r.employee_id, e.Employee_name, e.Department, " +
                       "r.Reservation_date, r.Start_date, r.End_date, r.Status " +
                       "FROM Reservations r " +
                       "JOIN Assets a ON r.Asset_id = a.Asset_id " +
                       "JOIN Employees e ON r.Employee_id = e.Employee_id";

        return fetchReportData(query);
    }

    private List<Map<String, String>> fetchReportData(String query) {
        List<Map<String, String>> reportList = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            while (rs.next()) {
                Map<String, String> row = new LinkedHashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(meta.getColumnLabel(i), rs.getString(i));
                }
                reportList.add(row);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching report data: " + e.getMessage());
        }

        return reportList;
    }
}
