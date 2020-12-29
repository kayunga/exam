package GE_HH.examTimetablingProblem.Run;

import GE_HH.examTimetablingProblem.ProblemDomain.HardConstraints;
import GE_HH.examTimetablingProblem.Utilities.ConstructiveHeuristics;
import GE_HH.examTimetablingProblem.Utilities.UtilityFunctions;
import GE_HH.examTimetablingProblem.components.Exam;
import GE_HH.examTimetablingProblem.components.Room;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class testHardConstraints {


    public static void main(String[] args) throws Exception {
        File filename = new File("C:\\Users\\George\\IdeaProjects\\ResearchProject\\src\\GE_HH\\examTimetablingProblem\\datasets\\exam_comp_set81.exam");

        UtilityFunctions tst=new UtilityFunctions();

        HardConstraints hConstraints = new HardConstraints();

        tst.loadfile(filename);

        ConstructiveHeuristics h = new ConstructiveHeuristics();

        h.setDf(tst);
        h.initializePeriodRooms();
        h.largestEnrolment(tst.getExams());
       // h.largestDegree();
        //h.largestWeightDegree();
        // h.saturationDegree();

      // System.out.println(tst.getTwoInARow());
      //  System.out.println(tst.getTwoInADay());
      //  System.out.println(tst.getPeriodConstraintsByType("AFTER"));


        hConstraints.setDf(tst);

               for(int i=0; i<h.getInitialSolution().size();i++)
        {

            System.out.println(h.getInitialSolution().get(i).getName());
          //  System.out.println("Period:"+h.getHeuristicPlacements().get(i).getName()+" Period index:"+h.getHeuristicPlacements().get(i).getPeriod().getIndex()+" Next Period:"+h.getHeuristicPlacements().get(i).getPeriod().getNextPeriod());

        }

        System.out.println("Assigned Exams "+h.getInitialSolution().size());
        System.out.println("\nTotal Constraint Violations:"+hConstraints.calculateConstraintViolations(h.getInitialSolution()));


        List<Exam> ex= h.getExamsByPeriod(h.getInitialSolution(), tst.getPeriods().get(1));
        System.out.println("\nPeriod " + tst.getPeriods().get(1).getName());
        for(int i=0;i<ex.size();i++)
        {
            System.out.println("Exams " + ex.get(i).getName()+ " size "+ex.get(i).getNumStudents());
        }

        List<Room> rm= h.getRoomsByPeriod(h.getInitialSolution(), tst.getPeriods().get(1));
        System.out.println("Rooms " + rm);
        for(int i=0;i<rm.size();i++)
        {
            System.out.println(rm.get(i).getName()+ " Available Space "+ h.getPeriodCapacity(tst.getPeriods().get(1),rm.get(i)));
        }

    }

    public static void printExamPlacements(HashMap<String, String> placements){
        for (Object obj : placements.entrySet()) {
            Map.Entry<String, String> entry = (Map.Entry) obj;
            System.out.print("vehicle: " + entry.getKey());
            System.out.println(", Period: " + entry.getValue());
        }
    }

}
