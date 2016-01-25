package com.softwareverde.driverreport;

public class Trip {
    public Long startTime;  // Epoch + Seconds
    public Long endTime;    // Epoch + Seconds
    public Double distance; // Miles

    public Trip() { }
    public Trip(Long startTime, Long endTime, Double distance) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.distance = distance;
    }
}

