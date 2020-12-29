package GE_HH.examTimetablingProblem.ProblemDomain;

import GE_HH.examTimetablingProblem.Utilities.ConstructiveHeuristics;
import GE_HH.examTimetablingProblem.Utilities.UtilityFunctions;
import GE_HH.examTimetablingProblem.components.Exam;
import GE_HH.examTimetablingProblem.components.Period;
import GE_HH.examTimetablingProblem.components.Room;
import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPIndividual;
import ec.gp.GPProblem;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleProblemForm;
import ec.util.Parameter;

import java.io.File;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class TimetableProblem extends GPProblem implements SimpleProblemForm {

    //file containing data
    // File filename = new File("E:\\School\\ResearchProject\\src\\GE_HH\\examTimetablingProblem\\datasets\\exam_comp_set81.exam");
    public static final String P_FILENAME = "tt-filename";

    //Components
    public String currentExam, currentRoom,currentPeriod,currentExamPlacement;
    private List<Exam> exams = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private List<Period> periods = new ArrayList<>();
    private int currentSchedule;

    //create a dataFunction object to access data functions
    UtilityFunctions tst = new UtilityFunctions();


    //Initial vehicle PeriodMgt using Constructive Heuristics
    private List<ExamPlacement> examPlacements = new CopyOnWriteArrayList<>();


    public void setup(final EvolutionState state,
                      final Parameter base) {

        // very important, remember this
        super.setup(state, base);

        // Get our filename
        File filename = state.parameters.getFile(base.push(P_FILENAME), null);
        if (filename == null)  //
            state.output.fatal("Filename must be provided", base.push(P_FILENAME));
        //get our timetable object
        // Get a Timetable object with all the available information.
        try {


            //load the datafile
            tst.loadfile(filename);

            //Create initial timetable using constructive heuristics
            ConstructiveHeuristics h = new ConstructiveHeuristics();
            h.setDf(tst);

            //Select heuristics to use. In this case we use the largest enrolment
            //h.largestEnrolment();

           // state.output.println("Initial vehicle Placements ",0);
            //get our initial timetable
            examPlacements = h.getInitialSolution();
            for (int n = 0; n < examPlacements.size(); n++) {
                getExams().add(examPlacements.get(n).getExam());
                getRooms().add(examPlacements.get(n).getRoom());
                getPeriods().add(examPlacements.get(n).getPeriod());

            //    state.output.println("("+getExams().get(n)+", "+getPeriods().get(n)+", "+getRooms().get(n)+" )",1);
            }


        } catch (Exception e) {
            state.output.fatal("Error in Timetable ProblemInstance setup, while loading from file " + filename +
                    "\nFrom parameter " + base.push(P_FILENAME) + "\nError:\n" + e);
        }
    }



        @Override
    public void evaluate(EvolutionState evolutionState, Individual individual, int i, int i1) {

             if (!individual.evaluated)  // don't bother reevaluating
             {
                 TimetableData input = (TimetableData) (this.input);
                 double fitness = 0.0;
                 int hits = 0;
                 double tempresult1 = 0, tempresult2 = 0, result, sresult = 0;
                 HardConstraints hConstraints = new HardConstraints();
                 SoftConstraints sConstraints = new SoftConstraints();
                 hConstraints.setDf(tst);
                 sConstraints.setDf(tst);
                 List<ExamPlacement> newPlacements = new ArrayList<ExamPlacement>();
                 List<String> tempExams = new ArrayList<>();
                 HashSet<Exam> uniqueExams = new HashSet<>();

                 for (int n = 0; n < getExamPlacements().size(); n++) {
                     currentExam = getExams().get(n).getName();
                     currentRoom = getRooms().get(n).getName();
                     currentPeriod = getPeriods().get(n).getName();
                     newPlacements.add(new ExamPlacement(tst.getExamByName(currentExam), tst.getRoomByName(currentRoom), tst.getPeriodByName(currentPeriod)));
                     currentExamPlacement = newPlacements.get(n).getName();

                     ((GPIndividual) individual).trees[0].child.eval(
                             evolutionState, i1, input, stack, ((GPIndividual) individual), this);


                     tempExams.add(newPlacements.get(n).getExam().getName());
                     uniqueExams.add(newPlacements.get(n).getExam());

                 }

                     tempresult1 = hConstraints.calculateConstraintViolations(newPlacements);

                     result = tempresult1;//+tempresult2;
                     if (result <= 1.0) {
                         hits++;
                     }
                     sresult = sConstraints.calculateConstraintViolations(newPlacements);
                     fitness = result + sresult;


                     // hits=(int)fitness;

                     // the fitness better be KozaFitness!
                     KozaFitness f = ((KozaFitness) individual.fitness);
                     f.setStandardizedFitness(evolutionState, fitness);
                     f.hits = hits;
                     individual.evaluated = true;
                 }

    }
    /**
     * Get exam Room
     *
     * @return exam
     */
    public String getExamRoom(String exam) {

        ExamPlacement temp =getExamPlacementByExam(exam);

        return temp.getRoom().getName();
    }
    /**
     * Get a random exam
     *
     * @return exam
     */
    public String getAnotherExam(String exam) {

        ExamPlacement temp =getExamPlacementByExam(exam);

        String AssignedExam =exam;

        if(temp!=null) {
            for (int i = 0; i < getExams().size(); i++) {
                if ((!temp.getExam().getName().equals(getExams().get(i).getName())) && (temp.getRoom().getCapacity() <=getExams().get(i).getNumStudents())) {
                    AssignedExam = getExams().get(i).getName();
                }
            }
        }
        return AssignedExam;
    }

      /**
     * Get a random period
     *
     * @return period
     */
    public String getAnotherPeriod(String period) {


        ExamPlacement temp =getExamPlacementByPeriod(period);

        String AssignedPeriod =period;

        if(temp!=null)
        {

            for (int i = 0; i < getPeriods().size(); i++) {
                if (!temp.getPeriod().getName().equals(getPeriods().get(i).getName())) {
                    AssignedPeriod = getPeriods().get(i).getName();
                }
            }
        }

        return AssignedPeriod;
    }

    /**
     * Get a random room
     *
     * @return room
     */
    public String getAnotherRoom(String room) {

        ExamPlacement temp =getExamPlacementByRoom(room);

        String AssignedRoom =room;

        if(temp!=null)
        {
            for (int i = 0; i < getRooms().size(); i++) {
                if ((!temp.getRoom().getName().equals(getRooms().get(i).getName())) && (temp.getExam().getNumStudents() <= getRooms().get(i).getCapacity())) {
                    AssignedRoom = getRooms().get(i).getName();
                }
            }
        }
        return AssignedRoom;
    }

    public List<ExamPlacement> getExamPlacements() {
        return examPlacements;
    }

    //generate random number between 1 and 10
    public int generateRandomNumber()
    {
        Random rn = new Random();
        int min=0;
        int max=10;

      int num = rn.nextInt(max - min + 1) + min;
      return num;

    }

    //Shuffling an arraylist

    public void shuffleExamList(int size)
    {
        if(size < getExams().size()) {

            List newList = getExams().subList(0, size);

            Collections.shuffle(newList);
        }
        else
        {
            List newList = getExams().subList(0, getExams().size());

            Collections.shuffle(newList);
        }




    }
    public void shuffleRoomList(int size)
    {
        if(size < getRooms().size()) {

            List newList = getRooms().subList(0, size);

            Collections.shuffle(newList);
        }
        else
        {
            List newList = getRooms().subList(0, getRooms().size());

            Collections.shuffle(newList);
        }




    }
    public void shufflePeriodList(int size)
    {
        if(size < getPeriods().size()) {
            List newList = getPeriods().subList(0, size);

            Collections.shuffle(newList);
        }
        else
        {
            List newList = getPeriods().subList(0, getPeriods().size());

            Collections.shuffle(newList);
        }



    }


    public String returnFirst(String x, String y) {
        return x;
    }

    public List<Exam> getExams() {

              return exams;
    }

    public List<Room> getRooms() {

        return rooms;
    }

    public List<Period> getPeriods() {

        return periods;
    }

    //getting the number of different elements between two exam lists
    public  int getNumDifferentElementsInLists(List<String> one, List<String> two){

        int size=one.size();

        Set<String> elist1 = new HashSet<>(one);
        Set<String> elist2 = new HashSet<>(two);

        Set<String> commonElements = new HashSet<>(elist1);
        commonElements.retainAll(elist2);

        return size-commonElements.size();
    }

    public void describe(
            final EvolutionState state,
            final Individual ind,
            final int subpopulation,
            final int threadnum,
            final int log)
    {
        state.output.println("\n\nBest Individual's Timetable\n=====================", log);
        TimetableData input = (TimetableData) (this.input);
        HardConstraints hConstraints = new HardConstraints();
        hConstraints.setDf(tst);
        List<ExamPlacement> newPlacements = new ArrayList<ExamPlacement>();
        List<String> tempExams = new ArrayList<>();
        HashSet<Exam> uniqueExams = new HashSet<>();

        // evaluate the individual
        for (int n = 0; n < getExamPlacements().size(); n++) {
            currentExam = getExams().get(n).getName();
            currentRoom = getRooms().get(n).getName();
            currentPeriod = getPeriods().get(n).getName();
            newPlacements.add(new ExamPlacement(tst.getExamByName(currentExam), tst.getRoomByName(currentRoom), tst.getPeriodByName(currentPeriod)));
            currentExamPlacement=newPlacements.get(n).getName();

            ((GPIndividual) ind).trees[0].child.eval(
                    state, threadnum, input, stack, ((GPIndividual) ind), this);

            uniqueExams.add(newPlacements.get(n).getExam());


        }

        ExamPlacement[] arr = newPlacements.toArray(new ExamPlacement[newPlacements.size()]);

        state.output.println("Assigned Exams :"+uniqueExams.size(), log);
        // print out the Timetable
        state.output.println("Timetable", log);

        Arrays.sort(arr);


        for(int i=0;i<arr.length;i++)
        {
            state.output.print(arr[i].getExam().getName()+","+ arr[i].getPeriod().getpId()+","+arr[i].getRoom().getRoomId()+"\r\n",log);
         //  state.output.print(arr[i].getPeriod().getpId()+","+arr[i].getRoom().getRoomId()+"\r\n",log);

        }

    }

    public boolean isInteger( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }

    public int getCurrentSchedule() {
        return currentSchedule;
    }

    public void setCurrentSchedule(int currentSchedule) {
        this.currentSchedule = currentSchedule;
    }

    //Returns a exam placement by exam name
    public ExamPlacement getExamPlacementByExam(String name) {

        ExamPlacement  r=null;
        for (ExamPlacement  rm : getExamPlacements())
        {
            if (rm.getExam().getName().equals(name))
            {
                r= rm; //gotcha!
            }
        }
        return r;
    }
    //Returns a exam placement by exam name
    public ExamPlacement getExamPlacementByRoom(String name) {

        ExamPlacement  r=null;
        for (ExamPlacement  rm : getExamPlacements())
        {
            if (rm.getRoom().getName().equals(name))
            {
                r= rm; //gotcha!
            }
        }
        return r;
    }
    //Returns a exam placement by exam name
    public ExamPlacement getExamPlacementByPeriod(String name) {

        ExamPlacement  r=null;
        for (ExamPlacement  rm : getExamPlacements())
        {
            if (rm.getPeriod().getName().equals(name))
            {
                r= rm; //gotcha!
            }
        }
        return r;
    }
}
