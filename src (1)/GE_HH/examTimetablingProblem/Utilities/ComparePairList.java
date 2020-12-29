package GE_HH.examTimetablingProblem.Utilities;

import GE_HH.vehicleRoutingProblem.Utilities.Pairs;

import java.util.ArrayList;
import java.util.List;


public class ComparePairList<A extends Comparable<A>,B extends Comparable<B>> extends ArrayList<Pairs<A,B>> {
	

	public boolean add(A first, B second){
		return this.add(new Pairs<A,B>(first, second));
	}
	
	public List<A> getFirstList(){
		ArrayList<A> returnValue = new ArrayList<A>();
		
		for (Pairs<A,B> obj : this)
			returnValue.add(obj.getFirst());
		
		return returnValue;		
	}
	
	public List<B> getSecondList(){
		ArrayList<B> returnValue = new ArrayList<B>();
		
		for (Pairs<A,B> obj : this)
			returnValue.add(obj.getSecond());
		
		return returnValue;		
	}
	
}
