package com.softwareverde.driverreport;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DriverRecordParserTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDriverEqualsDriverWithTheSameName() throws Exception {
        // Setup
        final Boolean expectedEqual = true;
        final Driver driver1 = new Driver("Dan");
        final Driver driver2 = new Driver("Dan");
        final Boolean driversAreEqual;

        // Action
        driversAreEqual = driver1.equals(driver2);

        // Assert
        assertEquals("Driver.equals fails when names are the same.", expectedEqual, driversAreEqual);
    }

    @Test
    public void testDriverEqualsDriverWithNoName() throws Exception {
        // Setup
        final Boolean expectedEqual = true;
        final Driver driver1 = new Driver();
        final Driver driver2 = new Driver();
        final Boolean driversAreEqual;

        // Action
        driversAreEqual = driver1.equals(driver2);

        // Assert
        assertEquals("Driver.equals fails when names are the same.", expectedEqual, driversAreEqual);
    }

    @Test
    public void testDriverEqualsDriverWithDifferentNames() throws Exception {
        // Setup
        final Boolean expectedEqual = false;
        final Driver driver1 = new Driver("Dan");
        final Driver driver2 = new Driver("Alex");
        final Boolean driversAreEqual;

        // Action
        driversAreEqual = driver1.equals(driver2);

        // Assert
        assertEquals("Driver.equals fails when names are not the same.", expectedEqual, driversAreEqual);
    }

    @Test
    public void testDriverEqualsDriverWithRHSUnsetName() throws Exception {
        // Setup
        final Boolean expectedEqual = false;
        final Driver driver1 = new Driver("Dan");
        final Driver driver2 = new Driver();
        final Boolean driversAreEqual;

        // Action
        driversAreEqual = driver1.equals(driver2);

        // Assert
        assertEquals("Driver.equals fails when names are not the same.", expectedEqual, driversAreEqual);
    }

    @Test
    public void testDriverEqualsDriverWithLHSUnsetName() throws Exception {
        // Setup
        final Boolean expectedEqual = false;
        final Driver driver1 = new Driver();
        final Driver driver2 = new Driver("Alex");
        final Boolean driversAreEqual;

        // Action
        driversAreEqual = driver1.equals(driver2);

        // Assert
        assertEquals("Driver.equals fails when names are not the same.", expectedEqual, driversAreEqual);
    }

    @Test
    public void testGetDriversReturnsDriversWhenDriversProvided() throws Exception {
        // Setup
        final String content = "Driver Dan\nDriver Alex\nDriver Ilya\nTrip Dan 07:15 07:45 17.3\nTrip Dan 06:12 06:32 12.9\nTrip Alex 12:01 13:16 42.0";
        final DriverRecordParser subject = new DriverRecordParser(content);
        List<Driver> driverList;

        final Driver driver1 = new Driver("Dan");
        final Driver driver2 = new Driver("Alex");
        final Driver driver3 = new Driver("Ilya");
        final long expectedDriverListLength = 3L;

        // Action
        driverList = subject.getDrivers();

        // Assert
        assertEquals("List of drivers is incorrect.", expectedDriverListLength, driverList.size());
        assertTrue("List of drivers does not contain driver.", driverList.contains(driver1));
        assertTrue("List of drivers does not contain driver.", driverList.contains(driver2));
        assertTrue("List of drivers does not contain driver.", driverList.contains(driver3));
    }

    @Test
    public void testGetDriversReturnsDriversWhenZeroDriversProvided() throws Exception {
        // Setup
        final String content = "Trip Dan 07:15 07:45 17.3\nTrip Dan 06:12 06:32 12.9\nTrip Alex 12:01 13:16 42.0";
        final DriverRecordParser subject = new DriverRecordParser(content);
        List<Driver> driverList;

        final long expectedDriverListLength = 0L;

        // Action
        driverList = subject.getDrivers();

        // Assert
        assertEquals("List of drivers is incorrect.", expectedDriverListLength, driverList.size());
    }

    @Test
    public void testGetDriversReturnsDriversWhenInvalidProvided() throws Exception {
        // Setup
        final String content = "Driver Trip Dan 07:15 07:45 17.3\nTrip Dan 06:12 06:32 12.9\nTrip Alex 12:01 13:16 42.0";
        final DriverRecordParser subject = new DriverRecordParser(content);
        List<Driver> driverList;

        final long expectedDriverListLength = 0L;

        // Action
        driverList = subject.getDrivers();

        // Assert
        assertEquals("List of drivers is incorrect.", expectedDriverListLength, driverList.size());
    }

    @Test
    public void testGetTripsForDriverWithValidTrips() throws Exception {
        final String content = "Driver Dan\nDriver Alex\nDriver Ilya\nTrip Dan 07:15 07:45 17.3\nTrip Dan 06:12 06:32 12.9\nTrip Alex 12:01 13:16 42.0";
        final DriverRecordParser subject = new DriverRecordParser(content);
        List<Trip> tripList;
        final String inspectedDriver = "Dan";

        final Trip trip1 = new Trip();
        trip1.startTime = 26100L;
        trip1.endTime = 27900L;
        trip1.distance = 17.3D;

        final Trip trip2 = new Trip();
        trip2.startTime = 22320L;
        trip2.endTime = 23520L;
        trip2.distance = 12.9D;

        final long expectedDriverListLength = 2L;

        // Action
        tripList = subject.getTripsForDriver(inspectedDriver);

        // Assert
        assertEquals("List of trips is incorrect.", expectedDriverListLength, tripList.size());

        assertEquals("Trip field was parsed incorrectly.", trip1.startTime, tripList.get(0).startTime);
        assertEquals("Trip field was parsed incorrectly.", trip1.endTime, tripList.get(0).endTime);
        assertEquals("Trip field was parsed incorrectly.", trip1.distance, tripList.get(0).distance);

        assertEquals("Trip field was parsed incorrectly.", trip2.startTime, tripList.get(1).startTime);
        assertEquals("Trip field was parsed incorrectly.", trip2.endTime, tripList.get(1).endTime);
        assertEquals("Trip field was parsed incorrectly.", trip2.distance, tripList.get(1).distance);
    }

    @Test
    public void testGetTripsForDriverWithInvalidTrips() throws Exception {
        final String content = "Trip Dan 07:15 07:45 17.3\nTrip Dan 06:12 06:32 12.9\nTrip Alex 12:01 13:16 42.0";
        final DriverRecordParser subject = new DriverRecordParser(content);
        List<Trip> tripList;
        final String inspectedDriver = "Dan";

        final long expectedDriverListLength = 0L;

        // Action
        tripList = subject.getTripsForDriver(inspectedDriver);

        // Assert
        assertEquals("List of trips is incorrect.", expectedDriverListLength, tripList.size());
    }
}