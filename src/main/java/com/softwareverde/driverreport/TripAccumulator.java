package com.softwareverde.driverreport;

import java.util.ArrayList;
import java.util.List;

public class TripAccumulator {
    private List<Trip> _trips = new ArrayList<Trip>();
    // private Boolean _floorVelocity = true;

    public void add(Trip trip) {
        _trips.add(trip);
    }

    public Double accumulateMilesDriven() {
        Double totalDistance = 0D;

        for (Trip trip : _trips) {
            totalDistance += trip.distance;
        }

        return totalDistance;
    }

    // NOTE:
    //  This function average of the sum of the miles travelled divided by the sum of the time elapsed,
    //  Which is NOT the same as the average MPH of each trip.
    public Double averageMilesPerHour() {
        final Double secondsToHours = 60D * 60D;

        if (_trips.size() == 0) {
            return 0D;
        }

        /*
            // NOTE: This implementation averages the individual MPH for each trip...
            //  Apparently the instructions want an average of the sum of the miles travelled divided by the sum of the time elapsed.
            Double summedMph = 0D;
            for (Trip trip : _trips) {
                final Long duration = trip.endTime - trip.startTime; // Seconds

                if (duration <= 0L) {
                    continue;
                }

                Double velocity = trip.distance / duration.doubleValue() * secondsToHours;
                if (_floorVelocity) {
                    velocity = Math.floor(velocity);
                }
                else {
                    velocity = (double) Math.round(velocity);
                }

                summedMph += velocity;
            }

            return summedMph / (double) _trips.size();
        */

        // Average of the sum of the miles travelled divided by the sum of the time elapsed:

        Double totalDistance = 0D;
        Double totalDuration = 0D;

        for (Trip trip : _trips) {
            final Long distance = Math.round(trip.distance);        // Miles
            final Long duration = trip.endTime - trip.startTime;    // Seconds

            totalDistance += distance;
            totalDuration += duration;
        }

        if (totalDuration == 0D) {
            return 0D;
        }

        return (double) Math.round( totalDistance / totalDuration * secondsToHours);
    }
}
