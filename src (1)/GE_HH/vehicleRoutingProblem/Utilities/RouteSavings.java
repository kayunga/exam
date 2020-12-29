package GE_HH.vehicleRoutingProblem.Utilities;

import GE_HH.vehicleRoutingProblem.components.Route;

import java.util.Comparator;

public class RouteSavings  {

	private Double saving;
	private Route r1;
	private Route r2;
	
	public RouteSavings(Double saving, Route r1, Route r2)
	{
		this.saving = saving;
		this.r1 = r1;
		this.r2 = r2;
	}

	public Double getSaving() {
		return this.saving;
	}

	public void setSaving(Double saving) {
		this.saving = saving;
	}

	public Route getR1() {
		return this.r1;
	}

	public void setR1(Route r1) {
		this.r1 = r1;
	}

	public Route getR2() {
		return this.r2;
	}

	public void setR2(Route r2) {
		this.r2 = r2;
	}

	@Override
	public String toString() {
		return "Route Saving ("+r1+","+r2+"): " + this.saving;
	}


}
