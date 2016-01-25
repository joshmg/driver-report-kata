package com.softwareverde.driverreport;

public class Driver {
    public String driverName;

    public Driver() { }
    public Driver(String driverName) {
        this.driverName = driverName;
    }

    @Override
    public int hashCode() {
        return this.driverName.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (! (object instanceof Driver)) {
            return false;
        }

        final Driver rhs = (Driver) object;
        if (rhs.driverName == null && this.driverName == null) {
            return true;
        }

        if (this.driverName != null) {
            return this.driverName.equals(rhs.driverName);
        }

        return false;
    }
}