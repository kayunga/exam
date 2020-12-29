package GE_HH.vehicleRoutingProblem.ProblemDomain;
import GE_HH.vehicleRoutingProblem.Utilities.ProblemInstance;
import GE_HH.vehicleRoutingProblem.components.Customer;
import GE_HH.vehicleRoutingProblem.components.Route;
import GE_HH.vehicleRoutingProblem.components.Vehicle;
import ec.gp.GPData;

import java.util.Random;

/*
 * VrpData.java - our return type will be a string
 *
 * @author George Mweshi
 *
 */

public class VrpData extends GPData
{

    // An array of vehicles
    public String x;

     public void copyTo(final GPData gpd)
    {
        ((VrpData)gpd).x = x;

    }


}