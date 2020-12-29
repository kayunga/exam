package GE_HH.examTimetablingProblem.Run;

import GE_HH.examTimetablingProblem.Utilities.ClashMatrix;
import GE_HH.examTimetablingProblem.Utilities.ConstructiveHeuristics;
import GE_HH.examTimetablingProblem.Utilities.UtilityFunctions;
import GE_HH.examTimetablingProblem.components.Exam;

import java.io.File;
import java.util.List;
import java.util.Map;

public class testing {


    public static void main(String[] args) throws Exception {
        File filename = new File("C:\\Users\\George\\IdeaProjects\\ResearchProject\\src\\GE_HH\\examTimetablingProblem\\datasets\\exam_comp_set81.exam");

        UtilityFunctions tst=new UtilityFunctions();
        tst.loadfile(filename);

      //  System.out.println(tst.getExams());
      //  ClashMatrix cm= tst.calculateClashMatrix(tst.getExams());
      //  System.out.println(cm);
       // cm.toString();
       // tst.printExamClashesDegree(tst.getExamClashDegrees(cm));
       // tst.printExamClashesDegree(tst.getExamWeightedClashDegrees(cm));
       // System.out.println(tst.getExamsByClashesDegree(tst.getExamWeightedClashDegrees(cm)).get(0).getNumStudents());
       // System.out.println(tst.getRoomByStudentSize(tst.getRooms(),tst.getExamsByClashesDegree(tst.getExamClashDegrees(cm)).get(0).getNumStudents()));
       // System.out.println();
       // System.out.println(tst.getExamsByClashesDegree(tst.getExamWeightedClashDegrees(cm)).get(1));
       // System.out.println(tst.getExamsByClashesDegree(tst.getExamWeightedClashDegrees(cm)).get(1).getNumStudents());
       // System.out.println(tst.getRoomByStudentSize(tst.getRooms(),tst.getExamsByClashesDegree(tst.getExamClashDegrees(cm)).get(1).getNumStudents()));

       ConstructiveHeuristics h = new ConstructiveHeuristics();

        h.setDf(tst);
        h.initializePeriodRooms();
        h.constructInitialSolution(tst.getExams());
       // System.out.println(h.getOrderedMapLists(h.SaturationDegree(tst.getExams())));
      //  System.out.println(h.getExamsByDegrees(h.largestEnrolment(tst.getExams())));
      //  System.out.println(h.getExamsByDegrees(h.largestDegree(tst.getExams())));
      //  System.out.println(h.getExamsByDegrees(h.largestWeightDegree(tst.getExams())));
       // System.out.println(h.getExamsByDegrees(h.SaturationDegree(tst.getExams())));



       // tst.printExamClashesDegree(h.getExamSaturationDegree());
       //System.out.println(cm.getCost(18,6));
      //  System.out.println("Total Saturation Exams "+h.getSaturationDegree().size());

     /*   List<ExamConflicts> ec= h.get.SaturationDegree();
        System.out.println("Total Periods: "+tst.getPeriods().size());

        for(int i=0;i<ec.size();i++)
        {
            System.out.println(ec.get(i).getName());
        }
        System.out.println();

        System.out.println("Total Exams "+ec.size());
        */



    }



}
