package com.softwareverde.driverreport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DriverRecordParser {
    private static final String DRIVER_TOKEN = "driver";
    private static final String TRIP_TOKEN = "trip";

    private Map<String, List<Trip>> _records = new HashMap<String, List<Trip>>();
    private Tokenizer _tokenizer = new Tokenizer();
    private Boolean _isValid = true;

    public DriverRecordParser(String content) {
        _tokenizer.setContent(content);
        _parse();
    }

    private Long _parseTimeString(String timeString) {
        final String timeDeliminator = ":";
        if (! timeString.contains(timeDeliminator)) {
            return null;
        }

        final Integer deliminatorPosition = timeString.indexOf(timeDeliminator);
        if (deliminatorPosition + 1 >= timeString.length()) {
            return null;
        }

        try {
            final String hoursString = timeString.substring(0, deliminatorPosition);
            final String minutesString = timeString.substring(deliminatorPosition + 1);
            return Long.parseLong(hoursString) * 60L * 60L + Long.parseLong(minutesString) * 60L;
        } catch (NumberFormatException e) {
            return null;
        }

    }

    private Boolean _parseAndStoreDriver() {
        if (_tokenizer.hasNextWord()) {
            String driverName = _tokenizer.getNextWord();

            if (! _records.containsKey(driverName)) {
                _records.put(driverName, new ArrayList<Trip>());
            }
        }
        else {
            return false;
        }

        return true;
    }

    private Boolean _parseAndStoreTrip() {
        Trip trip = new Trip();

        final String driverName;
        if (_tokenizer.hasNextWord()) {
            driverName = _tokenizer.getNextWord();
        }
        else {
            return false;
        }

        if (_tokenizer.hasNextWord()) {
            final String startTimeString = _tokenizer.getNextWord();
            trip.startTime = _parseTimeString(startTimeString);
            if (trip.startTime == null) {
                return false;
            }
        }
        else {
            return false;
        }

        if (_tokenizer.hasNextWord()) {
            final String endTimeString = _tokenizer.getNextWord();
            trip.endTime = _parseTimeString(endTimeString);
            if (trip.endTime == null) {
                return false;
            }
        }
        else {
            return false;
        }

        if (_tokenizer.hasNextWord()) {
            final String distanceString = _tokenizer.getNextWord();
            try {
                trip.distance = Double.parseDouble(distanceString);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        else {
            return false;
        }

        if (! _records.containsKey(driverName)) {
            return false;
        }

        _records.get(driverName).add(trip);
        return true;
    }

    private void _parse() {
        while (_tokenizer.hasNextWord()) {
            final String token = _tokenizer.getNextWord();

            switch (token.toLowerCase()) {
                case DRIVER_TOKEN: {
                    final Boolean wasSuccess = _parseAndStoreDriver();
                    if (! wasSuccess) {
                        _isValid = false;
                        return;
                    }
                } break;

                case TRIP_TOKEN: {
                    final Boolean wasSuccess = _parseAndStoreTrip();
                    if (! wasSuccess) {
                        _isValid = false;
                        return;
                    }
                } break;
            }
        }
    }

    public List<Driver> getDrivers() {
        List<Driver> drivers = new ArrayList<Driver>();

        if (_isValid) {
            for (String driverName : _records.keySet()) {
                Driver driver = new Driver();
                driver.driverName = driverName;
                drivers.add(driver);
            }
        }

        return drivers;
    }

    public List<Trip> getTripsForDriver(String driverName) {
        List<Trip> trips = new ArrayList<Trip>();

        if (_isValid) {
            for (Trip trip : _records.get(driverName)) {
                trips.add(trip);
            }
        }

        return trips;
    }

}
