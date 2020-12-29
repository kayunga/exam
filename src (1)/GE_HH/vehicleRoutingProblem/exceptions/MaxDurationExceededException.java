package GE_HH.vehicleRoutingProblem.exceptions;

/**
 * @version 1.0
 * @author Bishma Stornelli
 * @author Vicente Santacoloma
 */
public class MaxDurationExceededException extends RuntimeException {

  public MaxDurationExceededException() {
    super("Maximum Route Duration Reached");
  }
    
}
