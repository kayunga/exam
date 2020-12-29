package GE_HH.examTimetablingProblem.ProblemDomain;


import GE_HH.examTimetablingProblem.components.Period;
import GE_HH.examTimetablingProblem.components.Room;




public class PeriodCapacity implements Comparable<PeriodCapacity> {

    private Period period;
    private Room room;
    private int pCapacity;

    /**
     * Initialize placement
     *
     */
    public PeriodCapacity(Period p, Room rm, int capacity ){
        this.setRoom(rm);
        this.setPeriod(p);
        this.setpCapacity(capacity);

    }

    public PeriodCapacity(){
           }


    /** get Assigned period */
    public Period getPeriod() {
        return period;
    }

    /** get Assigned room */
    public Room getRoom() {
        return room;
    }

    // string representation of a period capacity
    public String getName() {
        return "["+getRoom().getName()+ " "+getPeriod().getName()+"]";
    }


    @Override
    public int compareTo(PeriodCapacity o) {

        int compareID = o.pCapacity;

        //ascending order
        return this.pCapacity - compareID;

        //descending order
        //return compareQuantity - this.quantity;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getpCapacity() {
        return pCapacity;
    }

    public void setpCapacity(int pCapacity) {
        this.pCapacity = pCapacity;
    }
}
