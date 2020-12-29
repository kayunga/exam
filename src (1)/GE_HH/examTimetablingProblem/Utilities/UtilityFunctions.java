package GE_HH.examTimetablingProblem.Utilities;

import GE_HH.examTimetablingProblem.ProblemDomain.ExamPlacement;
import GE_HH.examTimetablingProblem.ProblemDomain.PeriodConstraints;
import GE_HH.examTimetablingProblem.ProblemDomain.RoomConstraints;
import GE_HH.examTimetablingProblem.components.Exam;
import GE_HH.examTimetablingProblem.components.Period;
import GE_HH.examTimetablingProblem.components.Room;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;


public class UtilityFunctions {


    //components
    private List<Exam> Exams = new CopyOnWriteArrayList<Exam>();
    private List<Period> Periods = new CopyOnWriteArrayList<Period>();
    private List<Room> Rooms = new CopyOnWriteArrayList<Room>();
    private List<Student> Students = new CopyOnWriteArrayList<Student>();
    private final HashMap<String, String> studentExamMapping = new HashMap<String, String>();

    //Other information
    private int TwoInARow = 0;
    private int TwoInADay = 0;
    private int PeriodSpread = 0;
    private int NonMixedDurations = 0;
    private Integer[] FrontLoad = new Integer[] {0, 0, 0};

    //List of constraints
    private List<PeriodConstraints> PeriodConstraints = new ArrayList<>();
    private List<RoomConstraints> RoomConstraints = new ArrayList<>();

    private ClashMatrix problemInstanceClashMatrix;



    // input file loader function
    public boolean loadfile(File file) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line = null;
        String info = null;
        int count = 0;
        Hashtable<Integer, Student> students = new Hashtable<Integer, Student>();
        //Hashtable<Integer, String> exams = new Hashtable<Integer, String>();

     //   System.out.println(" Dataset Information");
     //   System.out.println("----------------------");
        while ((line = in.readLine()) != null) {
            if (line.startsWith("[") && line.endsWith("]")) {
                info = line.substring(1, line.length() - 1);
                if (info.indexOf(':') >= 0) {
                    count = Integer.parseInt(info.substring(info.indexOf(':') + 1));
                    info = info.substring(0, info.indexOf(':'));
                } else {
                    count = 0;
                }
                if (count == 0) continue;
            }
            if (line.trim().length() == 0) continue;
            if ("Exams".equals(info)) {
         //       System.out.println(" # of " + info + " :" + count);
                for (int i = 0; i < count; i++) {
                    line = in.readLine();
                    StringTokenizer stk = new StringTokenizer(line, ", ");
                    int length = Integer.parseInt(stk.nextToken());
                    Exam exam = new Exam(new Integer(i), length);
                    while (stk.hasMoreTokens()) {
                        int studentId = Integer.parseInt(stk.nextToken());
                        Student student = new Student(new Integer(studentId));
                        students.put(new Integer(studentId), student);
                        exam.getiStudents().add(student.getName());
                        getStudentExamMapping().put(exam.getName(), student.toString());
                    }
                    getExams().add(exam);

                }
            }
            if ("Periods".equals(info)) {
          //      System.out.println(" # of " + info + " :" + count);
                for (int i = 0; i < count; i++) {
                    line = in.readLine();
                    StringTokenizer stk = new StringTokenizer(line, ", ");
                    addPeriod(i,
                            stk.nextToken(),
                            stk.nextToken(),
                            Integer.parseInt(stk.nextToken()),
                            Integer.parseInt(stk.nextToken()));
                }
            }
            if ("Rooms".equals(info)) {
       //         System.out.println(" # of " + info + " :" + count);
                for (int i = 0; i < count; i++) {
                    line = in.readLine();
                    StringTokenizer stk = new StringTokenizer(line, ", ");
                    Room room = new Room(i,
                            Integer.parseInt(stk.nextToken()),
                            Integer.parseInt(stk.nextToken()));
                    getRooms().add(room);

                }
            }

            if ("PeriodHardConstraints".equals(info)) {
                StringTokenizer stk = new StringTokenizer(line, ", ");
                int ex1 = Integer.valueOf(stk.nextToken());
                String constraint = stk.nextToken();
                int ex2 = Integer.valueOf(stk.nextToken());
                if (ex1==(ex2)) {
                    System.out.println("Constraint "+constraint+" posted between "+ex1+" and "+ex2+".");
                } else {
                    PeriodConstraints rc = new PeriodConstraints();
                    if ("AFTER".equals(constraint)) {
                        rc.setAFTERConstraint(ex1, ex2);
                        getPeriodConstraints().add(rc);
                    } else if ("EXAM_COINCIDENCE".equals(constraint)) {
                        rc.setEXAM_COINCIDENCEConstraint(ex1, ex2);
                        getPeriodConstraints().add(rc);
                    } else if ("EXCLUSION".equals(constraint)) {
                        rc.setEXCLUSIONConstraint(ex1, ex2);
                        getPeriodConstraints().add(rc);
                    }
                }
            }

            if ("RoomHardConstraints".equals(info)) {
                StringTokenizer stk = new StringTokenizer(line, ", ");
                Exam ex1 = getExams().get(Integer.valueOf(stk.nextToken()));
                RoomConstraints rc = new RoomConstraints();
                String constraint = stk.nextToken();
                if ("ROOM_EXCLUSIVE".equals(constraint))
                   rc.setROOM_EXCLUSIVEConstraint(ex1.getId());
               getRoomConstraints().add(rc);
                }

            if ("InstitutionalWeightings".equals(info)) {
                StringTokenizer stk = new StringTokenizer(line, ", ");
                String constraint = stk.nextToken();
                if ("TWOINAROW".equals(constraint)) {
                    TwoInARow = Integer.parseInt(stk.nextToken());
                } else if ("TWOINADAY".equals(constraint)) {
                    TwoInADay = Integer.parseInt(stk.nextToken());
                } else if ("PERIODSPREAD".equals(constraint)) {
                    PeriodSpread = Integer.parseInt(stk.nextToken());
                } else if ("NONMIXEDDURATIONS".equals(constraint)) {
                    NonMixedDurations = Integer.parseInt(stk.nextToken());
                } else if ("FRONTLOAD".equals(constraint)) {
                    getFrontLoad()[0] = Integer.parseInt(stk.nextToken());
                    getFrontLoad()[1] = Integer.parseInt(stk.nextToken());
                    getFrontLoad()[2] = Integer.parseInt(stk.nextToken());
                }
            }



        }

        return true;
    }


    public Multimap<String, Integer> groupPeriodsbyDate(HashMap<Integer, Period> period) {
        HashMap<Integer, String> groupedPeriods = new HashMap<Integer, String>();

        for (int i = 0; i < this.getPeriods().size(); i++) {
            groupedPeriods.put(period.get(i).getpId(), period.get(i).getpDay());

        }

        Multimap<String, Integer> multiMap = HashMultimap.create();
        for (Entry<Integer, String> entry : groupedPeriods.entrySet()) {
            multiMap.put(entry.getValue(), entry.getKey());
        }
        return multiMap;
    }




    /**
     * Add a period
     */
    protected void addPeriod(int id,String day, String time, int length, int weight) {
        Period lastPeriod = (this.Periods.isEmpty() ? null : this.Periods.get(this.Periods.size() - 1));
        Period p = new Period(id, day, time, length, weight);
        if (lastPeriod == null)
            p.setIndex(this.Periods.size(), 0, 0);
        else if (lastPeriod.getpDay().equals(day)) {
            p.setIndex(this.Periods.size(), lastPeriod.getDay(), lastPeriod.getTime() + 1);
        } else
            p.setIndex(this.Periods.size(), lastPeriod.getDay() + 1, 0);
        if (lastPeriod != null) {
            lastPeriod.setNext(p);
            p.setPrev(lastPeriod);
        }
        this.Periods.add(p);
    }


    public HashMap<String, String> getStudentExamMapping() {
        return studentExamMapping;
    }

    public List<Exam> getExams() {
        return Exams;
    }

    public List<Period> getPeriods() {
        return Periods;
    }

    public List<Room> getRooms() {
        return Rooms;
    }

    public void getStudentExams(HashMap<String, String> examStudent) {

        Multimap<String, String> multiMap = HashMultimap.create();
        for (Entry<String, String> entry : examStudent.entrySet()) {
            multiMap.put(entry.getValue(), entry.getKey());
        }
        System.out.println();

        for (Entry<String, Collection<String>> entry : multiMap.asMap().entrySet()) {
            System.out.println(entry.getKey() + " takes the following exams : "
                    + entry.getValue());


        }
        System.out.println("   Number of Students: " + multiMap.asMap().entrySet().size());
    }

    public List<Exam> getExamsBySize() {

        getExams().sort(Comparator.comparingInt(Exam::getNumStudents).reversed());

        return getExams();

    }

    public List<Room> getRoomsBySize() {

        getRooms().sort(Comparator.comparingInt(Room::getCapacity).reversed());

        return getRooms();

    }

    public void printExamList(List<Exam> list) {
        for (Exam elem : list) {
            System.out.println("vehicle:" + elem.getName() + " Size:" + elem.getNumStudents());
        }
    }

    public void printRoomList(List<Room> list) {
        for (Room elem : list) {
            System.out.println("vehicle:" + elem.getName() + " Size:" + elem.getCapacity());
        }
    }



    /**
     * Get a random exam
     *
     * @return exam
     */
    public Exam getRandomExam() {



        return getExams().get((new Random()).nextInt(getExams().size()));
    }

    /**
     * Get a random period
     *
     * @return period
     */
    public Period getRandomPeriod( int size) {

        List<Period> p=new ArrayList<>();
        for(int i=0;i<getPeriods().size();i++)
        {
           if(getPeriods().get(i).getpLength() >= size)
           {
               p.add(getPeriods().get(i));
           }
        }

       return p.get((new Random()).nextInt(p.size()));


    }

    /**
     * Get a random room
     *
     * @return room
     */
    public Room getRandomRoom(int size) {

        List<Room> r=new ArrayList<>();
        for(int i=0;i<getRooms().size();i++)
        {
            if(getRooms().get(i).getCapacity() >= size)
            {
                r.add(getRooms().get(i));
            }
        }
        return r.get((new Random()).nextInt(r.size()));
    }

    /**
     * check if there are common students in two student lists
     *
     * @return boolean
     */
    public Boolean containsSameElements(List<Student> sl, List<Student> s2) {
        return Collections.disjoint(sl, s2);

    }

    /**
     * check if there are common students in two student lists
     *
     * @return boolean
     */
    public Boolean containsSameStudents(List<String> sl, List<String> s2) {
        return Collections.disjoint(sl, s2);

    }

    /**
     * Return number of students taking two exams
     *
     * @return int
     */
    public int getNumStudentTakingBothExams(List<String> s1, List<String> s2) {
        int count = 0;
        for (String s : s2) {
            if (s1.contains(s))
                count++;
        }
        return count;

    }

      //Returns a exam given its name
    public Exam getExamByName(String name) {

       Exam r=null;
        for (Exam rm : getExams())
        {
            if (rm.getName().equals(name))
            {
                r= rm; //gotcha!
            }
        }
        return r;
    }



    //Returns a room given its name
    public Room getRoomByName(String name) {

        Room r=null;
        for (Room rm : getRooms())
        {
            if (rm.getName().equals(name))
            {
               r= rm; //gotcha!
            }
        }
          return r;
    }

    //Returns a period given its name
    public Period getPeriodByName(String name) {

        Period r=null;
        for (Period rm : getPeriods())
        {
            if (rm.getName().equals(name))
            {
                r= rm; //gotcha!
            }
        }
        return r;
    }

    //Returns a list of room constraints given the type
    public List<RoomConstraints> getRoomConstraintsByType(String type) {

        List<RoomConstraints> rm = this.getRoomConstraints();
        List<RoomConstraints> tempRm= new CopyOnWriteArrayList<>();
        for (int i=0; i<rm.size();i++)
        {
            if (rm.get(i).getConstraintType().contains(type))
            {
                tempRm.add(rm.get(i));
            }
        }
        return tempRm;
    }
    //Returns a list of period constraints given the type
    public List<PeriodConstraints> getPeriodConstraintsByType(String type) {

       List<PeriodConstraints> rm = this.getPeriodConstraints();
       List<PeriodConstraints> temprm= new CopyOnWriteArrayList<>();
        for (int i=0; i<getPeriodConstraints().size();i++)
        {
            if (rm.get(i).getConstraintType().equals(type))
            {
                         temprm.add(rm.get(i));
            }
        }
        return  temprm;
    }


    public int getTwoInARow() {
        return TwoInARow;
    }

    public int getTwoInADay() {
        return TwoInADay;
    }

    public int getPeriodSpread() {
        return PeriodSpread;
    }

    public int getNonMixedDurations() {
        return NonMixedDurations;
    }

    public Integer[] getFrontLoad() {
        return FrontLoad;
    }

    public List<GE_HH.examTimetablingProblem.ProblemDomain.PeriodConstraints> getPeriodConstraints() {
        return PeriodConstraints;
    }

    public List<GE_HH.examTimetablingProblem.ProblemDomain.RoomConstraints> getRoomConstraints() {
        return RoomConstraints;
    }

    public PeriodConstraints getPeriodConstraintsByExam(int examA) {

        PeriodConstraints pc =null ;
        for (int i=0; i<getPeriodConstraints().size();i++)
        {
            if (getPeriodConstraints().get(i).getA()==(examA))
            {
                pc=getPeriodConstraints().get(i);
            }
        }
            return pc;
    }

    public List<PeriodConstraints> getPeriodConstraintsListByExam(String constraintType) {

        List<PeriodConstraints> pc=new CopyOnWriteArrayList<>();
        for (int i=0; i<getPeriodConstraints().size();i++)
        {
            if (getPeriodConstraints().get(i).getConstraintType().equals(constraintType))
            {
               pc.add(getPeriodConstraints().get(i));
            }
        }
        return pc;
    }


    public PeriodConstraints getPeriodConstraintsByExamAndType(int examA, String constraintType) {

        PeriodConstraints pc =null ;
        for (int i=0; i<getPeriodConstraints().size();i++)
        {
            if ((getPeriodConstraints().get(i).getA()==examA)&&(getPeriodConstraints().get(i).getConstraintType().equals(constraintType)))
            {
                pc=getPeriodConstraints().get(i);
            }
        }
        return pc;
    }

    public List<PeriodConstraints> getPeriodConstraintsListByExamAndType(List<PeriodConstraints>pc, int examA, String constraintType) {

        for (int i=0; i<getPeriodConstraints().size();i++)
        {
            if ((getPeriodConstraints().get(i).getA()==examA)&&(getPeriodConstraints().get(i).getConstraintType().equals(constraintType)))
            {
                pc.add(getPeriodConstraints().get(i));
            }
        }
        return pc;
    }

    public ClashMatrix getProblemInstnceClashMatrix() {
        return problemInstanceClashMatrix;
    }

    public ClashMatrix calculateClashMatrix(List<Exam> exams)
    {
        ClashMatrix costMatrix = new ClashMatrix(exams.size());

        double tmp;

        for (int i=0; i<exams.size()-1; i++)
            for (int j=i+1; j<exams.size(); j++) {
                tmp = getNumStudentTakingBothExams(exams.get(i).getiStudents(), exams.get(j).getiStudents());
                costMatrix.setCost(i, j, tmp);
            }

        return costMatrix;
    }

    public HashMap<Exam, Integer> getExamClashDegrees(ClashMatrix cmatrix)
    {

        HashMap<Exam, Integer> emap = new HashMap<>();

        double tmp;
        int clashes=0;

        for (int i=0; i<cmatrix.size(); i++) {
            for (int j = 0; j < cmatrix.size(); j++) {
                tmp = cmatrix.getCost(i,j);
               if (tmp != 0.0)
               {
                    clashes++;
                }
            }
            Exam tempExam=getExamByName("E"+i);
            emap.put(tempExam, clashes);
            clashes=0;
        }
        return emap;
    }


    public boolean checkExamClashes(List<ExamPlacement> ep, Period p, Exam e, ClashMatrix cmatrix)
    {
        boolean status=false;

        double tmp = 0;

            for (int i = 0; i < ep.size(); i++)
            {
                  if(ep.get(i).getPeriod().getName().equals(p.getName()))
                  {
                      tmp += cmatrix.getCost(ep.get(i).getExam().getId(), e.getId());
                            }
            }


        if (tmp > 0.0)
        {
            status =true;
        }
       // System.out.println();
            return status;
    }


    public void printExamDegrees(HashMap<Exam, Integer> hmap)
    {
        System.out.println(entriesSortedByValues(hmap));

    }

    static <K,V extends Comparable<? super V>>
    List<Entry<K, V>> entriesSortedByValues(Map<K,V> map) {

        List<Entry<K,V>> sortedEntries = new ArrayList<>(map.entrySet());

        Collections.sort(sortedEntries,
                (e1, e2) -> e2.getValue().compareTo(e1.getValue())
        );

        return sortedEntries;
    }

    public List<Exam> getExamsByDegrees(HashMap<Exam, Integer> hmap)
    {
        List<Exam> tempList=new ArrayList<>();

          for(int i=0;i<entriesSortedByValues(hmap).size();i++)
        {
            tempList.add(entriesSortedByValues(hmap).get(i).getKey());
        }
        return tempList;
    }

    public HashMap<Exam, Integer> getExamWeightedClashDegrees(ClashMatrix cmatrix)
    {

        HashMap<Exam, Integer> emap = new HashMap<>();

        double tmp=0;
        int clashes=0;

        for (int i=0; i<cmatrix.size(); i++) {
            for (int j = 0; j < cmatrix.size(); j++) {
                tmp += cmatrix.getCost(i,j);
                if (tmp != 0.0)
                {
                    clashes= (int) tmp;
                }
            }
            Exam tempExam=getExamByName("E"+i);
            emap.put(tempExam, clashes);
            tmp=0;
        }
        return emap;
    }

    public HashMap<Exam, Integer> getExamLargestEnrolmentDegrees(List<Exam> tempExams)
    {
        HashMap<Exam, Integer> emap = new HashMap<>();
        tempExams.sort(Comparator.comparingInt(Exam::getNumStudents).reversed());

        for (int i=0; i<tempExams.size(); i++)
        {
          emap.put(tempExams.get(i), tempExams.get(i).getNumStudents());

        }
        return emap;
    }

}
