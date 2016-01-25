package com.softwareverde.driverreport;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGenerateReport() throws Exception {
        // Setup
        final String input = "Driver Dan\nDriver Alex\nDriver Ilya\nTrip Dan 07:15 07:45 17.3\nTrip Dan 06:12 06:32 12.9\nTrip Alex 12:01 13:16 42.0";
        final String expectedOutput = "Alex: 42 miles @ 34 mph\nDan: 30 miles @ 36 mph\nIlya: 0 miles\n";
        final String output;

        // Action
        output = Main.generateReport(input);

        // Assert
        assertEquals("Report formatting is incorrect.", expectedOutput, output);
    }


    @Test
    public void testGenerateReportWithNoInput() throws Exception {
        // Setup
        final String input = "";
        final String expectedOutput = "";
        final String output;

        // Action
        output = Main.generateReport(input);

        // Assert
        assertEquals("Report invalid with no input.", expectedOutput, output);
    }
}