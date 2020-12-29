package GE_HH.vehicleRoutingProblem.Utilities;

import GE_HH.vehicleRoutingProblem.components.Customer;

import java.util.Comparator;
import java.util.Random;

public class RandomSort implements Comparator<Customer>{

	public int compare(Customer a, Customer b) {
	/*	if(a.getCustomerID() < b.getCustomerID()) return 1;
		if(a.getCustomerID() > b.getCustomerID()) return -1;
		return 0;
		*/
		Random r = new Random();
		int ri = r.nextInt(100);
		if(ri < 50) return 1;
		if(ri >= 50) return -1;
		return 0;
	}
}
