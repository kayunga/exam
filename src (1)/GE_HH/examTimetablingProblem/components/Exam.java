package GE_HH.examTimetablingProblem.components;

/*
 * Representation of an vehicle (variable).
 */

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Exam{

    //vehicle variables
    private int id; //vehicle id
    private int length; // duration of exam in minutes
    private List<String> iStudents = new CopyOnWriteArrayList<String>(); //list of students taking the exam


    // Constructor
    public Exam(int eid, int elength) {
        id = eid;
        length = elength;
    }

    //get exam id
    public int getId() {
        return id;
    }

    //get exam length in minutes
     public int getLength() {
        return length;
    }

    // Get list of students taking the exam
    public List<String> getiStudents() {
        return iStudents;
    }

    //get size of exam i.e. number of students taking the exam
    public int getNumStudents()
    {
        return getiStudents().size();

    }

     /** Name of vehicle is (E + id) */
    public String getName()
    {
        return "E"+getId();
    }

    public String toString()
    {
        return "E"+getId();
    }



}
