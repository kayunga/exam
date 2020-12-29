package GE_HH.vehicleRoutingProblem.exceptions;

/**
 * @version 1.0
 * @author Bishma Stornelli
 * @author Vicente Santacoloma
 */
public class MaxCapacityExceededException extends Exception {

  public MaxCapacityExceededException() {
    super("Maximum Route Capacity Reached");

  }
    
}
