package GE_HH.examTimetablingProblem.Utilities;


import GE_HH.vehicleRoutingProblem.exceptions.UndefinedCostException;
import org.apache.commons.lang.Validate;

import java.util.Arrays;

public class ClashMatrix {

	private static final double UNDEFINED = 0;
	public static final double INFINITE = 0;

	private double[][] clashMatrix;


	//constructor

	public ClashMatrix(int n) {
		clashMatrix = new double[n][n];

		for (int i=0; i<n; i++) {
			Arrays.fill(clashMatrix[i], UNDEFINED);
			clashMatrix[i][i] = INFINITE;
		}
	}

	/**
	 * Set the cost between two indexes. The cost is reflective
	 * @param a	the first index
	 * @param b the second index
	 * @param cost the cost to be set
	 */
	public void setCost(int a, int b, double cost) {
		Validate.isTrue(a>=0 && a<this.size(), "index out of bound");
		Validate.isTrue(b>=0 && b<this.size(), "index out of bound");
		Validate.isTrue(a!=b, "you cannot set cost between a node and itself");
		
		clashMatrix[a][b]=cost;
		clashMatrix[b][a]=cost;
	}
	
	/**
	 * Return the nearest index to i, that is the one with the least cost. 
	 * @param i the index
	 * @return the nearest index
	 */
	public int getNearestIndex(int i) {
		Validate.isTrue(i>=0 && i<this.size(), "index out of bound");
		
		double min = Double.MAX_VALUE;
		int minIndex = -1;
		for (int j=0; j<clashMatrix[i].length; j++) {
			if (this.getCost(i, j)<min) {
				min = this.getCost(i, j);
				minIndex = j;
			}
		}
		return minIndex;
	}
	
	/**
	 * Return the cost between two indexes.
	 * @param a
	 * @param b
	 * @return the cost
	 * @throws UndefinedCostException if the cost is undefined
	 */
	public double getCost(int a, int b) {
		Validate.isTrue(a>=0 && a<this.size(), "index out of bound");
		Validate.isTrue(b>=0 && b<this.size(), "index out of bound");
		
		double cost = clashMatrix[a][b];

		
		return cost;
	}

	public int size() {
		return clashMatrix.length;
	}
	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i<clashMatrix.length; i++)
			sb.append("____");
		sb.append("\n\n");
		
		for (int i=0; i<clashMatrix.length; i++) {
			for (int j=0; j<clashMatrix.length; j++) {
				if (clashMatrix[i][j]==INFINITE)
					sb.append("0.0").append(" ");
				else if (clashMatrix[i][j]==INFINITE)
					sb.append("---").append(" ");
				else
					sb.append(clashMatrix[i][j]).append(" ");
			}
			sb.append("\n");
		}
		
		for (int i=0; i<clashMatrix.length; i++)
			sb.append("____");
		sb.append("\n");
		
		return sb.toString();
	}
}
