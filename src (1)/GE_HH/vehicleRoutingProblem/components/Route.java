package GE_HH.vehicleRoutingProblem.components;

import GE_HH.vehicleRoutingProblem.Utilities.CostMatrix;
import GE_HH.vehicleRoutingProblem.exceptions.MaxDurationExceededException;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.math.IntRange;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;


public class Route {

	private Customer currentCustomer;
	private Customer depot;
	private CopyOnWriteArrayList<Customer> customers;
	private double dropTime;


	private HashSet<Customer> uniqueCustomer = new HashSet<>();

	//Constructor
	public Route(Customer depot, double dropTime) {
		this.setCustomers(new CopyOnWriteArrayList<>());
		this.depot=depot;
		getCustomers().add(this.depot);
		this.dropTime=dropTime;

	}

	public void addRoute(Route route){
		this.customers.addAll(route.getCustomers());

		uniqueCustomer.addAll(getCustomers());

	}

	public void removeFromRoute(int cust){
		this.customers.remove(cust);

	}
	/**
	 * Adds a customer to a customers.
	 * @param customer a customer
	 * @throws MaxDurationExceededException
	 */
	public void  addCustomer(Customer customer) throws MaxDurationExceededException {
		 if(!customer.equals(this.depot))
		  {
			this.currentCustomer=customer;
		  }
		  getCustomers().add(customer);


	}

	/**
	 * get the customers duration	 *
	 */
	public double getRouteDuration()
	{
		double newDuration = 0;
		//for(int i = 0; i < getCustomers().size() - 1 ; ++i)
		//	newDuration += cost.getCost(getCustomers().get(i).getCustomerID(), getCustomers().get(i+1).getCustomerID());

		newDuration += this.dropTime * (getCustomers().size() - 2);

		return newDuration;
	}

	/**
	 * Check if the customers is valid. A valid customers starts and ends in a depot,
	 * contain at least one customer and a customer can appear only once.
	 * @return true if customers is valid, false otherwise
	 */
	public boolean isValid() {
		// the customers contains at least 1 customer
		// D ==> c ==> D
		if (getCustomers().size() < 3)
			return false;

		// first and last node MUST be a depot
		if ((customers.get(0).getCustomerID()!=(this.depot.getCustomerID()) || customers.get(customers.size()-1).getCustomerID()!=(this.depot.getCustomerID())))
			return false;

			return true;
	}

	/**
	 * Generate the string representation of the route.
	 *
	 * @return the customers as a string
	 */
	@Override
	public String toString() {
		String r = "";
		for (Customer i : getCustomers()) {
			r += i.getCustomerID() + " ";
		}

		return r;
	}

	public int size() {
		return this.getCustomers().size();
	}

	public boolean isEmpty() {
		return this.getCustomers().isEmpty();
	}
	/**
	 * Calculate the total cost of the customers.
	 * @param costMatrix
	 * @return the total cost of the customers
	 */
	public double getRouteCost(CostMatrix costMatrix) {
		double cost = 0;
		int pred = -1;

		for (Customer cust : getCustomers()) {
			if (pred!=-1)
				cost+=costMatrix.getCost(pred, cust.getCustomerID() );

			pred = cust.getCustomerID();
		}

		return cost;
	}
	/**
	 * Return the total amount of demand of customers's customers.
	 * @return the total amount of demand of customers's customers
	 */
	public double getTotalRouteDemand() {
		double amount = 0;
		for ( Customer cust : this.getCustomers())
			amount+=cust.getCustomerDemand();
		return amount;
	}

	/**
	 * Return the customer at the specific position.
	 *
	 * @param index a position inside the customers
	 * @return the customer
	 */
	public Customer getCustomerAtIndex(int index) {
		return this.getCustomers().get(index);
	}



	public CopyOnWriteArrayList<Customer> getCustomers() {
		return customers;
	}



	public Customer getCurrentCustomer() {
		return currentCustomer;
	}

	public void setCustomers(CopyOnWriteArrayList<Customer> customers) {
		Collection<Customer> uniques = customers;
		this.getUniqueCustomer().addAll(uniques);
		this.customers = customers;
		//this.routeStartPoint = customers.get(0);
		//this.routeEndPoint = customers.get(customers.size() - 1);
	}

	public HashSet<Customer> getUniqueCustomer() {
		return uniqueCustomer;
	}

	public void swap(int a, int b) {
		Validate.isTrue(this.isValid(), "check customers validity: "+this.toString());

		IntRange range = new IntRange(0, customers.size());
		Validate.isTrue(range.containsInteger(a), "index "+a+"out of range");
		Validate.isTrue(range.containsInteger(b), "index "+b+"out of range" );

		Validate.isTrue(a>0, "cannot swap depots" );
		Validate.isTrue(b< customers.size()-1, "cannot swap depots" );

		Validate.isTrue(b>a, "invalid indexes: "+a+">="+b);

		Collections.reverse(customers.subList(a, b+1));

	}

	public void moveNode(int i, int j) {
		Validate.isTrue(this.isValid(), "check customers validity: "+this.toString());

		IntRange range = new IntRange(0, customers.size());
		Validate.isTrue(range.containsInteger(i), "index "+i+"out of range");
		Validate.isTrue(range.containsInteger(j), "index "+j+"out of range" );
		Validate.isTrue(range.containsInteger(j+1), "index "+(j+1)+"out of range" );

		Validate.isTrue(i>0, "cannot swap depots" );
		Validate.isTrue(j< customers.size()-1, "cannot swap depots" );

		if (i<=j) {
			Customer n = this.customers.remove(i);
			this.customers.add(j, n);
		}
		else {
			Customer n = this.customers.remove(i);
			this.customers.add(j+1, n);
		}
	}

}
