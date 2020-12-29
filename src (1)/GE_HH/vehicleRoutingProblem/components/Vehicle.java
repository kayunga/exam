package GE_HH.vehicleRoutingProblem.components;

import GE_HH.vehicleRoutingProblem.Utilities.CostMatrix;
import org.apache.commons.lang.Validate;

public class Vehicle implements Comparable<Vehicle>{
	
	private final double capacity;
	private Route route;
	
	
	//Constructor
	public Vehicle (double capacity) {
		Validate.isTrue(capacity>0, "vehicle capacity must be greater than 0");
		this.capacity = capacity;
	}
	
	/**
	 * Check if the vehicle is valid. In particular, route must be valid and the
	 * vehicle capacity must be positive and greater than the amount of demand.
	 * @return true if the vehicle is valid, false otherwise
	 */
	public boolean isValid() {
		return route!=null && route.isValid() && route.getTotalRouteDemand() <= capacity;
	}
	
	/**
	 * Return the cost of the vehicle's route.
	 * @param costMatrix
	 * @return the cost of the vehicle's route
	 */
	public double getRouteCost(CostMatrix costMatrix) {
		return route.getRouteCost(costMatrix);
	}

	/**
	 * Return the duration the vehicle's route.
	 * @return the duration of the vehicle's route
	 */
	public double getRouteDuration() {
		return route.getRouteDuration();
	}

	@Override
	public int compareTo(Vehicle o) {
		return Double.compare(this.capacity, o.getCapacity());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		builder.append(capacity);
		builder.append(") ");
		builder.append(route);
		return builder.toString();
	}
	

	public Route getRoute() {
		return route;
	}
	
	public void setRoute(Route route) {
		Validate.notNull(route, "cannot set null route");
		this.route = route;
	}
	
	public double getCapacity() {
		return capacity;
	}


}
