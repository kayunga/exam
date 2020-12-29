package GE_HH.vehicleRoutingProblem.Utilities;

import GE_HH.vehicleRoutingProblem.components.Customer;
import GE_HH.vehicleRoutingProblem.components.Route;
import GE_HH.vehicleRoutingProblem.components.Vehicle;
import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Solution {

	private ProblemInstance problem;


	public Solution(ProblemInstance prob)
	{
		this.problem=prob;
	}

	private List<Route> routes = new ArrayList<>();

	private HashSet<Customer> uniqueCustomer = new HashSet<>();

	/**
	 * Return the cost of the solution. 
	 * @return the total cost of the solution
	 */
	public double getSolutionCost() {
		double cost = 0;
		
		for (Route v : routes) {
			cost+=v.getRouteCost(this.problem.getProblemInstnceCostMatrix());
		}
		
		return cost;
	}
	/**
	 * Return the duration of the solution.
	 * @return the total duration of the solution
	 */
	public double getSolutionDuration() {
		double duration = 0;

		for (Route v : routes) {
			duration+=v.getRouteDuration();
		}

		return duration;
	}


	/**
	 * Check if the solution is valid.
	 * @return true if it's valid, false otherwise.
	 */
	public boolean isValid() {
		Validate.notEmpty(routes, "routes must be defined");
		
		for (Route v : routes) {
			if (!v.isValid()) {
				return false;
			}

			if(v.getRouteDuration()>this.problem.getMaxRouteTime())
				return false;

		}
		//if(getSolutionDuration() > this.problem.getMaxRouteTime())
		//	return false;


		if(!checkAllCustomersVisited())
			return false;
		
		return true;
	}//

	/**
	 * Return the cost of the solution.
	 * @return the total cost of the solution
	 */
	public boolean checkAllCustomersVisited()
		{
		Validate.notEmpty(routes, "routes must be defined");

		for (Route v : routes) {
			 uniqueCustomer.addAll(v.getCustomers());
			}
        if ((this.problem.getNumCustomers()+1)!= uniqueCustomer.size())
			return false;

		return true;
	}



	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (Route v : routes)
			sb.append(" Route Found :"+v.getCustomers().toString())
			.append(" Demand = ").append(v.getTotalRouteDemand()).append(" ")
			.append("\n");
		
		return sb.substring(0, sb.length()-1);
		
	}
	
	public String toString(CostMatrix costMatrix) {
		StringBuilder sb = new StringBuilder();

		for (Route v : routes)
			sb.append(" Route Found :"+v.getCustomers().toString())
			.append(" Demand =").append(v.getTotalRouteDemand()).append(" ")
			.append(" Route Cost=").append(v.getRouteCost(costMatrix)).append(" ")
			.append("\n");
			
		return sb.substring(0, sb.length()-1);
		
	}
	public String toString(CostMatrix costMatrix, ProblemInstance problem) {
		StringBuilder sb = new StringBuilder();

		for (Route v : routes)
			sb.append(" Route Found :"+v.getCustomers().toString())
					.append(" Demand =").append(v.getTotalRouteDemand()).append(" ")
					.append(" num Customers =").append(v.getCustomers().size()).append(" ")
					.append(" Route Cost=").append(v.getRouteCost(costMatrix)).append(" ")
				//	.append(" Route Saving=").append(v.getRouteSavings()).append(" ")
					.append("\n");

		return sb.substring(0, sb.length()-1);

	}

	public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Route> routes) {
		Validate.notEmpty(routes, "you must specify a non empty list of routes");
		this.routes= routes;
	}


	public HashSet<Customer> getUniqueCustomer() {
		return uniqueCustomer;
	}

	}
