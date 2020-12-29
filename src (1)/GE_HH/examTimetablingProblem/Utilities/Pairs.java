package GE_HH.examTimetablingProblem.Utilities;

import java.util.Comparator;

public class Pairs<A extends Comparable<A>,B extends Comparable<B>> implements Comparable<Pairs<A,B>> {
	
	private A first;
    private B second;
    private Comparator<Pairs<A,B>> comparator;
    
    public Pairs() {}
    
    public Pairs(A first, B second) {
		this.first = first;
		this.second = second;
	}
    
    public Pairs(A first, B second, Comparator<Pairs<A,B>> comparator) {
		this.first = first;
		this.second = second;
		this.comparator = comparator;
	}

	public A getFirst() {
		return first;
	}
	
	public void setFirst(A first) {
		this.first = first;
	}
	
	public B getSecond() {
		return second;
	}
	
	public void setSecond(B second) {
		this.second = second;
	}
	
	public void set(A first, B second) {
		this.setFirst(first);
		this.setSecond(second);
	}

	@Override
	public int compareTo(Pairs<A, B> o) {
		if (this.comparator==null) {
			if (this.getFirst().compareTo(o.getFirst()) == this.getSecond().compareTo(o.getSecond()))
				return this.getSecond().compareTo(o.getSecond());
			else
				return this.getFirst().compareTo(o.getFirst());
		}

		return comparator.compare(this, o);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
        Pairs<A,B> other = (Pairs<A,B>) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		}
		else
			if (!first.equals(other.first))
				return false;
		if (second == null) {
			if (other.second != null)
				return false;
		}
		else
			if (!second.equals(other.second))
				return false;
		return true;
	}

	@Override
	public String toString() {
		return "(" + first + ", " + second + ")";
	}
}
