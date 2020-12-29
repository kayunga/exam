package GE_HH.examTimetablingProblem.ProblemDomain;


import GE_HH.examTimetablingProblem.components.Exam;
import GE_HH.examTimetablingProblem.components.Period;
import GE_HH.examTimetablingProblem.components.Room;

/**
 * A simple class abstraction -- basically a container for exam placement(exam IDs, room IDs, period IDs)
 */

public class ExamPlacement implements Comparable<ExamPlacement> {

    private Exam exam;
    private Period period;
    private Room room;

    /**
     * Initialize placement
     *
     */
    public ExamPlacement(Exam ex, Room rm, Period p){
        this.setExam(ex);
        this.setRoom(rm);
        this.setPeriod(p);

    }

    public ExamPlacement(){
           }

    /** get Assigned exam */
    public Exam getExam() {
        return exam;
    }


    /** get Assigned period */
    public Period getPeriod() {
        return period;
    }

    /** get Assigned room */
    public Room getRoom() {
        return room;
    }

    // string representation of a examplacement
    public String getName() {
        return "["+getExam().getName()+" "+getRoom().getName()+ " "+getPeriod().getName()+"]";
    }

    // string representation of a examplacement
    public String toString() {
        return "["+getExam().getName()+" "+getPeriod().getName()+"]";
    }



    @Override
    public int compareTo(ExamPlacement o) {

        int compareID = o.getExam().getId();

        //ascending order
        return this.getExam().getId() - compareID;

        //descending order
        //return compareQuantity - this.quantity;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
