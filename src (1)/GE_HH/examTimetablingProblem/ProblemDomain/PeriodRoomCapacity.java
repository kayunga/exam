package GE_HH.examTimetablingProblem.ProblemDomain;


import GE_HH.examTimetablingProblem.components.Period;
import GE_HH.examTimetablingProblem.components.Room;




public class PeriodRoomCapacity implements Comparable<PeriodRoomCapacity> {

    private Period period;
    private Room room;
    private int pCapacity;

    /**
     * Initialize placement
     *
     */
    public PeriodRoomCapacity(Period p, Room rm, int capacity ){
        this.setRoom(rm);
        this.setPeriod(p);
        this.setpCapacity(capacity);

    }

    public PeriodRoomCapacity(){
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
        return "["+getPeriod().getName()+" : "+getRoom().getName()+ " : Remaining Capacity: "+getpCapacity()+"]";
    }


    @Override
    public int compareTo(PeriodRoomCapacity o) {

        int compareID = o.getpCapacity();

        //ascending order
        return this.getpCapacity() - compareID;

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
