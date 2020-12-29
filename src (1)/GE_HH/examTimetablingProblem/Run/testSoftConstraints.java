package GE_HH.examTimetablingProblem.Run;

import GE_HH.examTimetablingProblem.ProblemDomain.HardConstraints;
import GE_HH.examTimetablingProblem.ProblemDomain.PeriodConstraints;
import GE_HH.examTimetablingProblem.ProblemDomain.SoftConstraints;
import GE_HH.examTimetablingProblem.Utilities.ConstructiveHeuristics;
import GE_HH.examTimetablingProblem.Utilities.Student;
import GE_HH.examTimetablingProblem.Utilities.UtilityFunctions;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class testSoftConstraints {


    public static void main(String args[]) throws Exception {





        File filename = new File("C:\\Users\\George\\IdeaProjects\\ResearchProject\\src\\GE_HH\\examTimetablingProblem\\datasets\\exam_comp_set4.exam");

        UtilityFunctions tst=new UtilityFunctions();

        tst.loadfile(filename);

       // System.out.println(tst.containsSameElements(s1,s2));

        SoftConstraints sConstraints = new SoftConstraints();
        ConstructiveHeuristics h = new ConstructiveHeuristics();


        h.setDf(tst);

        h.largestEnrolment(tst.getExams());

               for(int i=0; i<h.getInitialSolution().size();i++)
        {

            System.out.println(h.getInitialSolution().get(i).getName());
            //  System.out.println("Period:"+h.getHeuristicPlacements().get(i).getName()+" Period index:"+h.getHeuristicPlacements().get(i).getPeriod().getIndex()+" Next Period:"+h.getHeuristicPlacements().get(i).getPeriod().getNextPeriod());

        }

         sConstraints.setDf(tst);
            //   System.out.println("Final penalty: "+sConstraints.getAllPeriodsBySpread(h.getHeuristicPlacements(),4));


   //     System.out.println("Current Period: "+h.getHeuristicPlacements().get(0).getPeriod().getName());
    //    System.out.println("Previous Period: "+h.getHeuristicPlacements().get(0).getPeriod().getPrevPeriod());
  //      System.out.println("Next Period: "+h.getHeuristicPlacements().get(0).getPeriod().getNextPeriod());
    //    System.out.println("Total Students: "+tst.getNumStudentTakingBothExams(h.getHeuristicPlacements().get(5).getExam().getiStudents(),h.getHeuristicPlacements().get(6).getExam().getiStudents()));
       System.out.println("Total Soft Constraint violations Penalty:  "+sConstraints.calculateConstraintViolations(h.getInitialSolution()));









    }
}

