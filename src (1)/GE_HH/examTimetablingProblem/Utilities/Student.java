package GE_HH.examTimetablingProblem.Utilities;

import GE_HH.examTimetablingProblem.components.Exam;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Representation of a student.
 * */

public class Student {

    private int sid;
    private List<Exam> exams = new CopyOnWriteArrayList<Exam>(); //list of exams taking the student



    /**
     * Constructor
     * @param id student unique id
     */
    public Student(int id) {
            sid = id;
    }

    public int getSid() {
        return sid;
    }

    /** String representation */
    public String getName()
    {
        return "S"+getSid();
    }

    // Get list of exams taking the student
    public List<Exam> getExams() {
        return exams;
    }
}
