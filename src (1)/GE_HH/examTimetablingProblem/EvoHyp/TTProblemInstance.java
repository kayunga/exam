/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GE_HH.examTimetablingProblem.EvoHyp;

import GE_HH.examTimetablingProblem.ProblemDomain.ExamPlacement;
import GE_HH.examTimetablingProblem.ProblemDomain.HardConstraints;
import GE_HH.examTimetablingProblem.ProblemDomain.SoftConstraints;
import GE_HH.examTimetablingProblem.Utilities.ConstructiveHeuristics;
import GE_HH.examTimetablingProblem.Utilities.UtilityFunctions;
import GE_HH.examTimetablingProblem.components.Exam;
import GE_HH.examTimetablingProblem.components.Period;
import GE_HH.examTimetablingProblem.components.Room;
import initialsoln.InitialSoln;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author GM
 */
public class TTProblemInstance extends InitialSoln {
    private String heuCom;          // String storing the chromosome (heuristic combination)
    private UtilityFunctions tst; //Some utility functions
    private List<Exam> exams; //List of exams to be scheduled
    private List<ExamPlacement> examPlacements ; //List of Exam Placements for the candidate solution
    private ConstructiveHeuristics cHeuristics; //constructive heuristics for building the candidate solution
    private double hCost; // cost of hard constraints violations for the candidate solution
    private double sCost; // cost of soft constraints violations for the candidate solution
    private double ObjVal;

    
    /**
     * Constructor initializing the timetable components

     */
    TTProblemInstance(UtilityFunctions utils) {
        this.tst = utils;
        this.cHeuristics = new ConstructiveHeuristics();
        this.cHeuristics.setDf(tst);
        this.exams=this.tst.getExams();
        this.examPlacements= new CopyOnWriteArrayList<>();
        this.sCost=0.0;
        this.hCost=0.0;
        this.ObjVal=0.0;

    }
    

    /**
     * The objective value is the total cost of the hard and soft constraints violations. For a feasible timetable the hard constraints cost must be zero.
     *
     * @return : the objective value for this problem instance
     */
    public double objValue () {

        HardConstraints hConstraints = new HardConstraints();
        hConstraints.setDf(tst);
        SoftConstraints sConstraints = new SoftConstraints();
        sConstraints.setDf(tst);
        this.hCost=hConstraints.calculateConstraintViolations(getExamPlacements());
        this.sCost=sConstraints.calculateConstraintViolations(getExamPlacements());

        return this.hCost;
    }
    
    /**
     * @return : the number of exams scheduled
     */
    public int getNumExamsScheduled ()
    {
         return this.getExamPlacements().size();
    }
    
    /**
     * @return true if there are still items to be allocated, false otherwise
     */
    public boolean examsNotScheduled()
    {
        return !(this.getExamPlacements().size()==this.tst.getExams().size());
    }

    /**
     * String representation of this Exam time tabling Problem instance (for printing)
     * 
     * @return this problem instance as a String
     */
    @Override
    public String toString () {
        String str = "";
        for (int i = 0; i < this.getExamPlacements().size(); i++) {
            str += this.getExamPlacements().get(i).getExam().getName() + ":\t";
            str += this.getExamPlacements().get(i).getRoom().getName() + ":\t";
            str += this.getExamPlacements().get(i).getPeriod().getName()  + "\n";
        }
        return str;
    }
    
    /**
     * The fitness of this candidate solution.  The objective value is taken to be the fitness.
     * 
     * @return the fitness (objective) value of this candidate solution
     */
    @Override
    public double getFitness() {
        return this.objValue();
    }

    /**
     * Compares the performance of this candidate solution with that of "other".
     * 
     * @param other the candidate solution to be compared with this
     * @return 1 if this is fitter than "other", -1 if "other" is fitter than this, and 0 if the fitness is equal
     */
    @Override
    public int fitter (InitialSoln other) {
        if (this.getFitness() < other.getFitness()) {
            return 1;
        } else if (this.getFitness() > other.getFitness()) {
            return -1;
        } else {
            return 0;
        }
    }
    
    /**
     * A List object containing the scheduled exams, representing this candidate solution.
     * @return a list object containing the scheduled exams
     */
    @Override
    public Object getSoln() {
        return this.getExamPlacements();
    }

    /**
     * Set the heuristic combination that will build this candidate solution.
     * 
     * @param heuCom string representing the heuristic combination used to make this candidate solution
     */
    @Override
    public void setHeuCom(String heuCom) {
        this.heuCom = heuCom;
    }

    /**
     * The heuristic combination to build this candidate solution.
     * 
     * @return a string representing the heuristic combination used to make this candidate solution
     */
    @Override
    public String getHeuCom() {
        return this.heuCom;
    }


    
    /////////////////////////////////////////
    //  Low-Level Construction Heuristics  //
    /////////////////////////////////////////
    
    /**
     * Schedule exams using the Largest Degree Constructive Heuristic
     */
    public void LargestDegree()
    {

           }
    
    /**
     * Schedule exams using the Largest Weighted Degree Constructive Heuristic
     */
    public void LargestWeightedDegree() {

      }

    /**
     * Schedule exams using the Largest Enrolment Degree Constructive Heuristic
     */
    public void LargestEnrolmentDegree() {

    }

    /**
     * Schedule exams using the Largest Enrolment Degree Constructive Heuristic
     */
    public void LeastSaturationDegree() {

    }

     public UtilityFunctions getTst() {
        return tst;
    }

    public List<ExamPlacement> getExamPlacements() {
        return examPlacements;
    }

    public double getObjVal() {
        return this.ObjVal;
    }

    public ConstructiveHeuristics getcHeuristics() {
        return cHeuristics;
    }

    public void setcHeuristics(ConstructiveHeuristics cHeuristics) {
        this.cHeuristics = cHeuristics;
    }

    public double gethCost() {
        return hCost;
    }


    public double getsCost() {
        return sCost;
    }


    public List<Exam> getExams() {
        return exams;
    }


}
