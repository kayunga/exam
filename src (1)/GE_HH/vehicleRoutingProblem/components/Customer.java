package GE_HH.vehicleRoutingProblem.components;

import cmt.TwoDimCoord;

public class Customer {

    //Customer variables
    private int customerID;
    private TwoDimCoord location;
    private int customerDemand;

    //constructor
    public Customer(int id, TwoDimCoord pos, int demand)
    {
        this.customerID=++id;
        this.location =pos;
        this.customerDemand=demand;

    }

    public int getCustomerID() {
        return customerID;
    }

    public TwoDimCoord getLocation() {
        return location;
    }

    public int getCustomerDemand() {
        return customerDemand;
    }

    public String toString()
    {
        return "C"+getCustomerID();
    }

}
