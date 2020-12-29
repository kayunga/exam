package GE_HH.vehicleRoutingProblem.Utilities;

import java.util.Comparator;

public class RouteSavingsSorter implements Comparator<RouteSavings>
{

	@Override
	public int compare(RouteSavings o1, RouteSavings o2)
	{
		if(o1.getSaving() < o2.getSaving()) return 1;
		else if(o1.getSaving() > o2.getSaving()) return -1;
		return 0;
	}
}
