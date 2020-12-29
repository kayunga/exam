package GE_HH.examTimetablingProblem.components;

/**
        * Representation of a Period. Each Period has a day, a time, a length in
        * minutes, and a weight.
 */

public class Period implements Comparable<Period>{

    //Period variables
    private int id;
    private int iIndex = -1;
    private String pDay;
    private String pTime;
    private int pLength;
    private int pWeight;
    private int pAvailableRoomspace;
    private int iDay, iTime;
    private Period iPrev, iNext;


    //Constructors
    public Period(String day, String time, int length, int weight) {
        pDay = day;
        pTime = time;
        pLength = length;
        pWeight = weight;
    }

    public Period(int pid, String day, String time, int length, int weight) {
       id=pid;
        pDay = day;
        pTime = time;
        pLength = length;
        pWeight = weight;
    }
    //get period id
    public int getpId() {
        return id;
    }

    // Period index (among all periods of the problem)
    public int getIndex() {
        return iIndex;
    }


   //get time as a string
    public String getpTime() {
        return pTime;
    }

    // Time index
    public int getTime() {
        return iTime;
    }


   //get day as a string
    public String getpDay() {
        return pDay;
    }

    // Day index
    public int getDay() {
        return iDay;
    }

    //get length of period in minutes
    public int getpLength() {
        return pLength;
    }

   // get the period weight (penalty for use)
    public int getpWeight() {
        return pWeight;
    }

    // string representation of a period
    public String getName()
    {
        return "P"+getpId();
    }

    /** Previous period */
    public Period getPrevPeriod() { return iPrev; }
    /** Next period */
    public Period getNextPeriod() { return iNext; }


    // Set period index, day index, and time index
    public void setIndex(int index, int day, int time) {
        iIndex = index;
        iDay = day;
        iTime = time;
    }

    /** Set previous period */
    public void setPrev(Period prev) { iPrev = prev;}

    /** Set next period */
    public void setNext(Period next) { iNext = next;}

    /** String representation */
    public String toString() { return getpDay()+" "+getpTime(); }

    /** Hash code -- period index */
    public int hashCode() { return iIndex; }

    /** Compare two periods for equality */
    public boolean equals(Object o) {
        if (o==null || !(o instanceof Period)) return false;
        return getIndex()==((Period)o).getIndex();
    }

    /** Compare two periods on index */
    public int compareTo(Period p) {
        return Double.compare(getIndex(), p.getIndex());
    }


    public int getpAvailableRoomspace() {
        return pAvailableRoomspace;
    }

    public void setpAvalaibleRoomspace(int pRoomspace) {
        this.pAvailableRoomspace = pRoomspace;
    }
}
