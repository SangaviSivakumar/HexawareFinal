package com.hexaware.am.dao;

import java.util.List;
import java.util.Map;

public interface Report {
    List<Map<String, String>> getAllAssetAllocations();
    List<Map<String, String>> getAllMaintenanceRecords();
    List<Map<String, String>> getAllReservations();
}
