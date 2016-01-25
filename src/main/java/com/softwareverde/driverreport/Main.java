package com.softwareverde.driverreport;


import java.util.*;

class Main {
    static private List<Driver> _sortMap(Map<Driver, Long> map) {
        List<Map.Entry<Driver, Long>> list = new ArrayList<Map.Entry<Driver, Long>>();
        for (Map.Entry<Driver, Long> entry : map.entrySet()) {
            list.add(entry);
        }

        Collections.sort(list, new Comparator<Map.Entry<Driver, Long>>() {
            @Override
            public int compare( Map.Entry<Driver, Long> lhs, Map.Entry<Driver, Long> rhs ) {
                return (rhs.getValue()).compareTo(lhs.getValue());
            }
        });

        List<Driver> sortedDrivers = new ArrayList<Driver>();
        for (Map.Entry<Driver, Long> entry : list) {
            sortedDrivers.add(entry.getKey());
        }
        return sortedDrivers;
    }

    private static String _generateReport(String content) {
        Map<Driver, Long> driverDistanceMap = new HashMap<Driver, Long>();
        Map<Driver, Long> driverMphMap = new HashMap<Driver, Long>();

        DriverRecordParser driverRecordParser = new DriverRecordParser(content);
        for (Driver driver : driverRecordParser.getDrivers()) {
            TripAccumulator tripAccumulator = new TripAccumulator();
            for (Trip trip : driverRecordParser.getTripsForDriver(driver.driverName)) {
                tripAccumulator.add(trip);
            }

            final Double milesDriven = tripAccumulator.accumulateMilesDriven();
            final Double mph = tripAccumulator.averageMilesPerHour();

            driverDistanceMap.put(driver, milesDriven.longValue());
            driverMphMap.put(driver, mph.longValue());
        }

        StringBuilder report = new StringBuilder();
        List<Driver> sortedDrivers = _sortMap(driverDistanceMap);
        for (Driver driver : sortedDrivers) {
            report.append(driver.driverName);
            report.append(": ");
            report.append(driverDistanceMap.get(driver));
            report.append(" miles");

            if (driverDistanceMap.get(driver) > 0.0001) {
                report.append(" @ ");
                report.append(driverMphMap.get(driver));
                report.append(" mph");
            }

            report.append("\n");
        }

        return report.toString();
    }

    public static String generateReport(String content) {
        return _generateReport(content);
    }

    public static void main(String[] argv) {
        // Accumulate the argv into a single content...
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : argv) {
            stringBuilder.append(string +" "); // NOTE: This essentially replaces all newlines with spaces; but the parser is agnostic.
        }

        final String content = stringBuilder.toString();
        System.out.println(_generateReport(content));
    }
}
