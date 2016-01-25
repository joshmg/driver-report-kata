package com.softwareverde.driverreport;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TripAccumulatorTest {
    private TripAccumulator _subject;

    @Before
    public void setUp() throws Exception {
        _subject = new TripAccumulator();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testAccumulateMilesDrivenWhenNoTripsGiven() throws Exception {
        // Setup
        final Double delta = 0.00001D;
        Double expectedMilesDriven = 0D;
        Double milesDriven;

        // Action
        milesDriven = _subject.accumulateMilesDriven();

        // Assert
        assertTrue("Miles driven should be zero when no trips are provided.", Math.abs(expectedMilesDriven - milesDriven) < delta);
    }

    @Test
    public void testAccumulateMilesDrivenWhenOneTripGiven() throws Exception {
        // Setup
        final Double delta = 0.00001D;
        Double expectedMilesDriven = 10D;
        Double milesDriven;

        final Long oneHour = 1L * 60L * 60L;
        final Trip trip1 = new Trip();
        trip1.distance = 10.0D;
        trip1.startTime = oneHour;
        trip1.endTime = trip1.startTime + oneHour;

        _subject.add(trip1);

        // Action
        milesDriven = _subject.accumulateMilesDriven();

        // Assert
        assertTrue("Miles driven should be summed.", Math.abs(expectedMilesDriven - milesDriven) < delta);
    }

    @Test
    public void testAccumulateMilesDrivenWhenMultipleTripsGiven() throws Exception {
        // Setup
        final Double delta = 0.00001D;
        Double expectedMilesDriven = 60.6D;
        Double milesDriven;

        final Long oneHour = 1L * 60L * 60L;

        final Trip trip1 = new Trip(oneHour, oneHour * 2L, 10.1D);
        final Trip trip2 = new Trip(oneHour, oneHour * 3L, 20.2D);
        final Trip trip3 = new Trip(oneHour, oneHour * 4L, 30.3D);

        _subject.add(trip1);
        _subject.add(trip2);
        _subject.add(trip3);

        // Action
        milesDriven = _subject.accumulateMilesDriven();

        // Assert
        assertTrue("Miles driven should be summed.", Math.abs(expectedMilesDriven - milesDriven) < delta);
    }

    @Test
    public void testAccumulateMilesDrivenWhenMultipleTripsGivenWithRoundedDistances() throws Exception {
        // Setup
        Double expectedMilesDriven = 60D;
        Double milesDriven;

        final Long oneHour = 1L * 60L * 60L;

        final Trip trip1 = new Trip(oneHour, oneHour * 2L, 10.0D);
        final Trip trip2 = new Trip(oneHour, oneHour * 3L, 20.0D);
        final Trip trip3 = new Trip(oneHour, oneHour * 4L, 30.0D);

        _subject.add(trip1);
        _subject.add(trip2);
        _subject.add(trip3);

        // Action
        milesDriven = _subject.accumulateMilesDriven();

        // Assert
        assertEquals("Miles driven should be summed.", expectedMilesDriven, milesDriven);
    }

    @Test
    public void testAverageMilesPerHourWhenNoTripsGiven() throws Exception {
        // Setup
        Double expectedMilesPerHour = 0D;
        Double milesPerHour;


        // Action
        milesPerHour = _subject.averageMilesPerHour();

        // Assert
        assertEquals("Miles per hour should be zero when empty.", expectedMilesPerHour, milesPerHour);
    }

    @Test
    public void testAverageMilesPerHourWhenOneTripGiven() throws Exception {
        // Setup
        Double expectedMilesPerHour = 25.0D;
        Double milesPerHour;

        final Long oneHour = 1L * 60L * 60L;

        final Trip trip1 = new Trip(oneHour, oneHour * 2L, 25.0D);

        _subject.add(trip1);

        // Action
        milesPerHour = _subject.averageMilesPerHour();

        // Assert
        assertEquals("Miles per hour should be averaged.", expectedMilesPerHour, milesPerHour);
    }

    @Test
    public void testAverageMilesPerHourWhenMultipleTripsGiven() throws Exception {
        // Setup
        Double expectedMilesPerHour = 36D; // 36.65D; // 36 MPH according to the example output...
        // NOTE: 36 MPH is only attainable if the MPH is FLOORED (aka integer math) instead of ROUNDED.
        //  Alternatively, 36 can be obtained if the average mph is calculated by adding the rounded distances divided by the total time of all trips.
        //  We will follow the example output instead of the instructions...

        Double milesPerHour;

        final Trip trip1 = new Trip(26100L, 27900L, 17.3D); // 1800 seconds, 17.3 Miles (34.6 MPH) (35 MPH if miles rounded before calculation...) (34 if floored...)
        final Trip trip2 = new Trip(22320L, 23520L, 12.9D); // 1200 seconds, 12.9 Miles (38.7 MPH) (39 MPH if miles rounded before calculation...) (38 if floored...)

        _subject.add(trip1);
        _subject.add(trip2);

        // Action
        milesPerHour = _subject.averageMilesPerHour();

        // Assert
        assertEquals("Miles per hour should be averaged.", expectedMilesPerHour, milesPerHour);
    }
}

