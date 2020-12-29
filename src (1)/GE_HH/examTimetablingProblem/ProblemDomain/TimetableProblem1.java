package GE_HH.examTimetablingProblem.ProblemDomain;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TimetableProblem1 extends GPProblem implements SimpleProblemForm {

    //file containing data
    // File filename = new File("E:\\School\\ResearchProject\\src\\GE_HH\\examTimetablingProblem\\datasets\\exam_comp_set81.exam");
    public static final String P_FILENAME = "tt-filename";

    //Components
    public String currentExam, currentRoom,currentPeriod;
    private List<Exam> exams = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private List<Period> periods = new ArrayList<>();

    //create a dataFunction object to access data functions
    UtilityFunctions tst = new UtilityFunctions();


    HardConstraints hConstraints = new HardConstraints();


    //Initial vehicle PeriodMgt using Constructive Heuristics
    private List<ExamPlacement> examPlacements = new ArrayList<ExamPlacement>();


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

            hConstraints .setDf(tst);


            //get our components
            exams=tst.getExams();
            rooms=tst.getRooms();
            periods=tst.getPeriods();


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
                 double fitness;
                 //int hits;
              List<ExamPlacement> newPlacements = new ArrayList<ExamPlacement>();

                 for (int n = 0; n <exams.size(); n++) {
                     currentExam = getExams().get(n).getName();
                     currentRoom =getRandomRoom(rooms);
                     currentPeriod = getRandomPeriod(periods) ;

                     newPlacements.add(new ExamPlacement(tst.getExamByName(currentExam),tst.getRoomByName(currentRoom),tst.getPeriodByName(currentPeriod)));

                     ((GPIndividual) individual).trees[0].child.eval(
                             evolutionState, i1, input, stack, ((GPIndividual) individual), this);




                 }

                 fitness = hConstraints.calculateConstraintViolations(newPlacements);
                // hits=(int)fitness;

                 // the fitness better be KozaFitness!
                 KozaFitness f = ((KozaFitness) individual.fitness);
                 f.setStandardizedFitness(evolutionState, fitness);
                // f.hits = hits;
                    individual.evaluated = true;
             }
    }

    /**
     * Get a random exam
     *
     * @return exam
     */
    public String getRandomExam() {
        return getExams().get((new Random()).nextInt(getExams().size())).getName();
    }
    /**
     * Get a random period
     *
     * @return period
     */
    public String getRandomPeriod() {

        return getPeriods().get((new Random()).nextInt(getPeriods().size())).getName();

    }

    /**
     * Get a random room
     *
     * @return room
     */
    public String getRandomRoom() {
        return getRooms().get((new Random()).nextInt(getRooms().size())).getName();
    }

    /**
     * Get a random exam
     *
     * @return exam
     */
    public String getRandomExam(List<Exam> exs) {
        return exs.get((new Random()).nextInt(exs.size())).getName();
    }
    /**
     * Get a random period
     *
     * @return period
     */
    public String getRandomPeriod(List<Period> pds) {

        return pds.get((new Random()).nextInt(pds.size())).getName();

    }

    /**
     * Get a random room
     *
     * @return room
     */
    public String getRandomRoom(List<Room> rms) {
        return rms.get((new Random()).nextInt(rms.size())).getName();
    }


    public List<ExamPlacement> getExamPlacements() {
        return examPlacements;
    }

    //generate random number between 1 and 10
    public int generateRandomNumber()
    {
        Random rn = new Random();
        int min=0;
        int max=5;

      int num = rn.nextInt(max - min + 1) + min;
      return num;

    }

    //Shuffling an arraylist

    public void shuffleExamList()
    {
        List newList=getExams().subList(0, getExams().size());

        Collections.shuffle(newList);


    }
    public void shuffleRoomList()
    {
        List newList=getRooms().subList(0, getRooms().size());

        Collections.shuffle(newList);


    }
    public void shufflePeriodList()
    {
        List newList=getPeriods().subList(0, getPeriods().size());

        Collections.shuffle(newList);


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
}
