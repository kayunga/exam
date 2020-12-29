package GE_HH.examTimetablingProblem.ProblemDomain;


import GE_HH.examTimetablingProblem.components.Exam;


public class ExamConflicts implements Comparable<ExamConflicts> {

    private Exam exam;
    private int numConflicts;

    /**
     * Initialize placement
     *
     */
    public ExamConflicts(Exam e, int conflict ){
        this.setExam(e);
        this.setNumConflicts(conflict);
            }

    public ExamConflicts(){
           }



    // string representation of a period capacity
    public String getName() {
        return "["+getExam().getName()+" : "+ " Cost: "+getNumConflicts()+"]";
    }


    @Override
    public int compareTo(ExamConflicts o) {

        int compareID = o.getNumConflicts();

        //ascending order
        return this.getNumConflicts() - compareID;

        //descending order
        //return compareQuantity - this.quantity;
    }


    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public int getNumConflicts() {
        return numConflicts;
    }

    public void setNumConflicts(int numConflicts) {
        this.numConflicts = numConflicts;
    }
}
