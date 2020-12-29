package GE_HH.vehicleRoutingProblem.Utilities;

import GE_HH.vehicleRoutingProblem.components.Customer;
import GE_HH.vehicleRoutingProblem.components.Route;
import GE_HH.vehicleRoutingProblem.components.Vehicle;
import cmt.CMT;
import org.apache.commons.lang.Validate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProblemInstance {
	private Customer depot;
	private List<Customer> customers= new ArrayList<>();
	private CostMatrix problemInstanceCostMatrix;
	private double vehicleCapacity;
	private double maxRouteTime;
	private double dropTime;
	private int numCustomers;
	private int numVehicles;
	private int[] quantity;


	ComparePairList<Integer, Integer> coordinates = new ComparePairList<>();
	
	//Constructor
	
	public ProblemInstance() {
		}

	public void loadChristofidesDataset(File filename) throws IOException {
   {

	    String str = filename.toString();
     	CMT cmt=new CMT(str);
     	cmt.ReadDS();
     	this.numCustomers=cmt.getNumCust();
     	this.depot=new Customer(-1,cmt.getDepotPos(),0);
    	coordinates.add((int)cmt.getDepotPos().GetXCoord(),(int)cmt.getDepotPos().GetYCoord());
     	this.quantity=cmt.getQuantity();

     	for(int i = 0; i< cmt.getCustPos().length; i++)
		{
			Customer tempCustomer =new Customer(i,cmt.getCustPos()[i], this.getQuantity()[i]);
			coordinates.add((int)tempCustomer.getLocation().GetXCoord(),(int)tempCustomer.getLocation().GetYCoord());
			customers.add(tempCustomer);
		}
		this.vehicleCapacity=cmt.getVehCap();
	    this.maxRouteTime=cmt.getMaxRouteTime();
	    this.dropTime=cmt.getDropTime();
	    this.problemInstanceCostMatrix= calculateCostMatrix(coordinates);
   }

   }



	/**
	 * Perform some tests to check problem validity.
	 * @return	true if pass all tests, false otherwise.
	 */
	public boolean isValid() {
		
		Validate.notNull(getDepot(), "depot must be defined");
		Validate.notEmpty(getCustomers(), "list of customers must be be defined");
		Validate.notNull(getProblemInstnceCostMatrix(), "cost matrix must be defined");
		Validate.notNull(getVehicleCapacity(), "Vehicle Capacity must be defined");
		Validate.notNull(getMaxRouteTime(), "Maximum Route time must be defined");
		Validate.notNull(getMaxRouteTime(), "Drop time must be defined");
		
		if (getCustomers().size()+1 != getProblemInstnceCostMatrix().size())
			return false;
		
		for (Customer c : getCustomers()) {
			if (c.getCustomerDemand()>getVehicleCapacity())
				return false;

		}
		
		return true;
	}

	/**
	 * Return the demand of a customer.
	 * @param customer a customer
	 * @return the demand of the customer
	 */
	public int getDemand(Customer customer) {
		if(0 <= customer.getCustomerID() && customer.getCustomerID() < getQuantity().length)
			return getQuantity()[customer.getCustomerID()];
		return 0;
	}

	public Customer getDepot() {
		return depot;
	}


	public List<Customer> getCustomers() {
		return customers;
	}

	public CostMatrix getProblemInstnceCostMatrix() {
		return problemInstanceCostMatrix;
	}


	public double getVehicleCapacity() {
		return vehicleCapacity;
	}

	public double getMaxRouteTime() {
		return maxRouteTime;
	}

	public double getDropTime() {
		return dropTime;
	}

	public int getNumVehicles() {
		return numVehicles;
	}

	public void setNumVehicles(int numVehicles) {
		this.numVehicles = numVehicles;
	}

	public int[] getQuantity() {
		return quantity;
	}


	public static CostMatrix calculateCostMatrix(ComparePairList<Integer, Integer> coordinates) {
		CostMatrix costMatrix = new CostMatrix( coordinates.size() );

		double tmp;

		for (int i=0; i<coordinates.size()-1; i++)
			for (int j=i+1; j<coordinates.size(); j++) {
				tmp = euclideanDistance(coordinates.get(i), coordinates.get(j));
				costMatrix.setCost(i, j, tmp);
			}

		return costMatrix;
	}

	public static double euclideanDistance(Pairs<Integer,Integer> a, Pairs<Integer,Integer> b) {
		return
				Math.sqrt(
						Math.pow( a.getFirst() - b.getFirst(), 2 ) +
								Math.pow( a.getSecond() - b.getSecond(), 2 )
				);

	}




	public int getNumCustomers() {
		return numCustomers;
	}
}
