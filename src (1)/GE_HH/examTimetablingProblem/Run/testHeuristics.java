package GE_HH.examTimetablingProblem.Run;

import GE_HH.examTimetablingProblem.ProblemDomain.ExamPlacement;
import GE_HH.examTimetablingProblem.ProblemDomain.HardConstraints;
import GE_HH.examTimetablingProblem.ProblemDomain.SoftConstraints;
import GE_HH.examTimetablingProblem.Utilities.ConstructiveHeuristics;
import GE_HH.examTimetablingProblem.Utilities.UtilityFunctions;
import GE_HH.examTimetablingProblem.components.Exam;
import GE_HH.examTimetablingProblem.components.Period;
import GE_HH.examTimetablingProblem.components.Room;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class testHeuristics {

    private  List<ExamPlacement> examPlacements = new CopyOnWriteArrayList<>();
    private  List<Exam> exams = new CopyOnWriteArrayList<>();
    private  List<Period> periods = new CopyOnWriteArrayList<>();
    private  List<Room> rooms = new CopyOnWriteArrayList<>();


    public  void runStart() throws Exception {

         File filename = new File("C:\\Users\\George\\IdeaProjects\\ResearchProject\\src\\GE_HH\\examTimetablingProblem\\datasets\\exam_comp_set81.exam");

        UtilityFunctions tst = new UtilityFunctions();

        HardConstraints hConstraints = new HardConstraints();
        SoftConstraints sConstraints = new SoftConstraints();

        tst.loadfile(filename);

        ConstructiveHeuristics h = new ConstructiveHeuristics();

        h.setDf(tst);

        h.largestEnrolment(tst.getExams());

        // state.output.println("Initial vehicle Placements ",0);
        //get our initial timetable
        hConstraints .setDf(tst);
        sConstraints.setDf(tst);

        examPlacements=h.getInitialSolution();

        for(int i=0; i<getExamPlacements().size();i++)
        {
            getExams().add(getExamPlacements().get(i).getExam());
            getPeriods().add(getExamPlacements().get(i).getPeriod());
            getRooms().add(getExamPlacements().get(i).getRoom());
           // System.out.println(getExamPlacements().get(i).getName());
              System.out.println(getExams().get(i).getName()+":"+getRooms().get(i).getName()+":"+getPeriods().get(i).getName());

        }
        System.out.println("\nBefore Applying Perturbative Heuristics");
        System.out.println("..........................................................");
        System.out.println("Hard Constraint Violations:"+hConstraints.calculateConstraintViolations(getExamPlacements()));
        System.out.println("Soft Constraint violations:"+sConstraints.calculateConstraintViolations(getExamPlacements()));
        System.out.println("Total Constraint violations Penalty: "+(sConstraints.calculateConstraintViolations(getExamPlacements())+hConstraints.calculateConstraintViolations(getExamPlacements())));

        double cost=hConstraints.calculateConstraintViolations(getExamPlacements());
        double cost2=0;

        List<ExamPlacement> temp;
        do {

            //temp = MoveOneExamAtRandomToTimeslot();
            //temp = SwapTimeslotsForTwoRandomExams();
            //temp = SwapAllExamsForTwoTimeslots();
            //temp = ExchangeThreeExamsTimeslots();
            // temp = SwapRoomsForAnyTwoRandomExams();
            //temp = MoveTwoRandomExamsToTimeslots();
            temp = MoveOneExamAtRandomToRoom();

            cost2 = hConstraints.calculateConstraintViolations(temp);
            if(cost2<cost)
            {
                System.out.println("\n..........................................................");
                System.out.println("After Applying Perturbative Heuristics");
                System.out.println("..........................................................");
                System.out.println("Hard Constraint Violations:" + hConstraints.calculateConstraintViolations(temp));
                System.out.println("Soft Constraint violations:" + sConstraints.calculateConstraintViolations(temp));
                System.out.println("Total Constraint violations Penalty: " + (sConstraints.calculateConstraintViolations(temp) + hConstraints.calculateConstraintViolations(temp)));
                cost=cost2;
            }

        }
        while((cost2 != 0));


        MoveOneExamAtRandomToTimeslot();
        //SwapTimeslotsForTwoRandomExams();
        //SwapAllExamsForTwoTimeslots();
        //ExchangeThreeExamsTimeslots();
        //SwapRoomsForAnyTwoRandomExams();
        //MoveTwoRandomExamsToTimeslots();
        //MoveOneExamAtRandomToRoom();



    }

    public static void main(String[] args) throws Exception {
        testHeuristics t =new testHeuristics();
        t.runStart();

    }


    /**
     * Get a random exam
     *
     * @return exam
     */
    public  Exam getRandomExam() {
        return  getExams().get((new Random()).nextInt( getExams().size()));
    }

    /**
     * Get a random Period
     *
     * @return period
     */
    public  Period getRandomPeriod() {
        return  getPeriods().get((new Random()).nextInt( getPeriods().size()));
    }

    /**
     * Get a random Room
     *
     * @return room
     */
    public  Room getRandomRoom() {
        return  getRooms().get((new Random()).nextInt( getRooms().size()));
    }


    //Move a random exam to any timeslot
    public List<ExamPlacement> MoveOneExamAtRandomToTimeslot()
    {
        Exam tempEx=getRandomExam();
        Period randPeriod=getRandomPeriod();

        ExamPlacement exPl =getExamPlacementByExam(tempEx);

      //  System.out.println("Old PeriodMgt :"+exPl .getName());

       if(tempEx.getLength()<=randPeriod.getpLength() && randPeriod.getIndex()!=exPl.getPeriod().getIndex())
       {
           getExamPlacementByExam(tempEx).setPeriod(randPeriod);

           getExamPlacements().set(getExamPlacements().indexOf( getExamPlacementByExam(tempEx)), getExamPlacementByExam(tempEx));

       }


        return getExamPlacements();
    }

    //Move a random exam to any Room
    public List<ExamPlacement> MoveOneExamAtRandomToRoom()
    {
        Exam tempEx=getRandomExam();
        Room randRoom=getRandomRoom();

        ExamPlacement exPl =getExamPlacementByExam(tempEx);

        if(tempEx.getNumStudents()<=randRoom.getCapacity() && randRoom.getRoomId()!=exPl.getRoom().getRoomId())
        {
            getExamPlacementByExam(tempEx).setRoom(randRoom);
            getExamPlacements().set(getExamPlacements().indexOf( getExamPlacementByExam(tempEx)), getExamPlacementByExam(tempEx));

        }



        return getExamPlacements();
    }

    //Swapping two timeslots
    public List<ExamPlacement> SwapTimeslotsForTwoRandomExams()
    {
        Exam tempEx=getRandomExam();
        Exam tempEx2=getRandomExam();
        ExamPlacement exPl =getExamPlacementByExam(tempEx);
        ExamPlacement exP2 =getExamPlacementByExam(tempEx2);

        if(tempEx.getId()!=tempEx2.getId() && exPl.getPeriod().getpLength()<=exP2.getPeriod().getpLength() )
        {
            Period r1=exPl.getPeriod();
            Period r2=exP2.getPeriod();
            getExamPlacementByExam(tempEx).setPeriod(r2);
            getExamPlacementByExam(tempEx2).setPeriod(r1);
            getExamPlacements().set(getExamPlacements().indexOf( getExamPlacementByExam(tempEx)), getExamPlacementByExam(tempEx));
            getExamPlacements().set(getExamPlacements().indexOf( getExamPlacementByExam(tempEx2)), getExamPlacementByExam(tempEx2));

        }


        return getExamPlacements();
    }

    //Swapping all exams on two timeslots
    public List<ExamPlacement> SwapAllExamsForTwoTimeslots()
    {
        Period randPeriod1=getRandomPeriod();
        Period randPeriod2=getRandomPeriod();

        List<Exam> tempList1=getListExamPlacementsByPeriod(randPeriod1);
        List<Exam> tempList2 =getListExamPlacementsByPeriod(randPeriod2);

        swapList(tempList1,tempList2);

        for(int k=0;k<tempList1.size();k++)
        {
            getExamPlacementByExam(tempList1.get(k)).setPeriod(randPeriod1);
           // System.out.println(getExamPlacementByExam(tempList1.get(k)).getName());
            getExamPlacements().set(getExamPlacements().indexOf(getExamPlacementByExam(tempList1.get(k))),getExamPlacementByExam(tempList1.get(k)));
        }


        for(int k=0;k<tempList2.size();k++)
        {
            getExamPlacementByExam(tempList2.get(k)).setPeriod(randPeriod2);
           // System.out.println(getExamPlacementByExam(tempList2.get(k)).getName());
            getExamPlacements().set(getExamPlacements().indexOf(getExamPlacementByExam(tempList2.get(k))),getExamPlacementByExam(tempList2.get(k)));
        }

        return getExamPlacements();

    }
    //moving two exams to different timeslots
    public List<ExamPlacement> MoveTwoRandomExamsToTimeslots()
    {
        Exam tempEx=getRandomExam();
        Exam tempEx2=getRandomExam();
        Period tempP=getRandomPeriod();
        Period tempP2=getRandomPeriod();

        ExamPlacement exPl =getExamPlacementByExam(tempEx);
        ExamPlacement exP2 =getExamPlacementByExam(tempEx2);

            if((tempEx.getId()!=tempEx2.getId()) && (exPl.getPeriod().getIndex()!= tempP.getIndex() && exP2.getPeriod().getIndex()!=tempP2.getIndex() )&& (tempP.getIndex()!=tempP2.getIndex())&&(tempEx.getLength()<=tempP.getpLength() && tempEx2.getLength()<=tempP2.getpLength()))
        {
            getExamPlacementByExam(tempEx).setPeriod(tempP);
            getExamPlacementByExam(tempEx2).setPeriod(tempP2);

            getExamPlacements().set(getExamPlacements().indexOf( getExamPlacementByExam(tempEx)), getExamPlacementByExam(tempEx));
            getExamPlacements().set(getExamPlacements().indexOf( getExamPlacementByExam(tempEx2)), getExamPlacementByExam(tempEx2));

        }


        return getExamPlacements();
    }


    //Swapping rooms for two exams
    public List<ExamPlacement> SwapRoomsForAnyTwoRandomExams()
    {
        Exam tempEx=getRandomExam();
        Exam tempEx2=getRandomExam();
        ExamPlacement exPl =getExamPlacementByExam(tempEx);
        ExamPlacement exP2 =getExamPlacementByExam(tempEx2);


        if(tempEx.getId()!=tempEx2.getId() && (tempEx.getNumStudents()>=exP2.getRoom().getCapacity() || tempEx2.getNumStudents()>=exPl.getRoom().getCapacity()))
        {
            Room r1=exPl.getRoom();
            Room r2=exP2.getRoom();
            getExamPlacementByExam(tempEx).setRoom(r2);
            getExamPlacementByExam(tempEx2).setRoom(r1);

            getExamPlacements().set(getExamPlacements().indexOf( getExamPlacementByExam(tempEx)), getExamPlacementByExam(tempEx));
            getExamPlacements().set(getExamPlacements().indexOf( getExamPlacementByExam(tempEx2)), getExamPlacementByExam(tempEx2));

        }
   /*
        for(int k=0;k<getExamPlacements().size();k++)
        {
            System.out.println(getExamPlacements().get(k).getName());

        }*/
        return getExamPlacements();
    }

    //Exchanging timeslots for 3 exams
    public List<ExamPlacement> ExchangeThreeExamsTimeslots()
    {
        Exam tempEx=getRandomExam();
        Exam tempEx2=getRandomExam();
        Exam tempEx3=getRandomExam();

        ExamPlacement exPl =getExamPlacementByExam(tempEx);
        ExamPlacement exP2 =getExamPlacementByExam(tempEx2);
        ExamPlacement exP3 =getExamPlacementByExam(tempEx3);


        List<Period> tempList= new CopyOnWriteArrayList<>();
        tempList.add(exPl.getPeriod());
        tempList.add(exP2.getPeriod());
        tempList.add(exP3.getPeriod());

        if(!(tempEx.getId()== tempEx2.getId()) && !(tempEx.getId()==tempEx3.getId()))
        {
            Collections.shuffle(tempList);

            getExamPlacementByExam(tempEx).setPeriod(tempList.get(0));
            getExamPlacementByExam(tempEx2).setPeriod(tempList.get(1));
            getExamPlacementByExam(tempEx3).setPeriod(tempList.get(2));

            getExamPlacements().set(getExamPlacements().indexOf( getExamPlacementByExam(tempEx)), getExamPlacementByExam(tempEx));
            getExamPlacements().set(getExamPlacements().indexOf( getExamPlacementByExam(tempEx2)), getExamPlacementByExam(tempEx2));
            getExamPlacements().set(getExamPlacements().indexOf( getExamPlacementByExam(tempEx3)), getExamPlacementByExam(tempEx3));


        }

        return getExamPlacements();
    }



    public List<ExamPlacement> getExamPlacements() {
        return examPlacements;
    }

    //Returns a list of Exams
    public List<Exam> getListExamPlacementsByPeriod(Period p) {

        List<Exam> tempList= new CopyOnWriteArrayList<>();
        for (int i=0; i<getExamPlacements().size();i++)
        {
            if (getExamPlacements().get(i).getPeriod().getIndex()==p.getIndex())
            {
                tempList.add(getExamPlacements().get(i).getExam());
            }
        }
        return  tempList;
    }

    public  void swapList(List<Exam> list1, List<Exam> list2){
        List<Exam> tmpList = new CopyOnWriteArrayList<>(list1);
        list1.clear();
        list1.addAll(list2);
        list2.clear();
        list2.addAll(tmpList);
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    //Returns a exam placement by exam name
    public ExamPlacement getExamPlacementByExam(Exam ex) {

        ExamPlacement  r=null;
        for (ExamPlacement  rm : getExamPlacements())
        {
            if (rm.getExam().getName().equals(ex.getName()))
            {
                r= rm; //gotcha!
            }
        }
        return r;
    }
}

