package GE_HH.examTimetablingProblem.Utilities;

import GE_HH.examTimetablingProblem.ProblemDomain.ExamConflicts;
import GE_HH.examTimetablingProblem.ProblemDomain.ExamPlacement;
import GE_HH.examTimetablingProblem.ProblemDomain.HardConstraints;
import GE_HH.examTimetablingProblem.ProblemDomain.PeriodRoomCapacity;
import GE_HH.examTimetablingProblem.components.Exam;
import GE_HH.examTimetablingProblem.components.Period;
import GE_HH.examTimetablingProblem.components.Room;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConstructiveHeuristics {

    private UtilityFunctions df;


    private List<ExamPlacement> initialSolution = new CopyOnWriteArrayList<>();
    private List<PeriodRoomCapacity> periodCapacity = new CopyOnWriteArrayList<>();

    private HashMap<String, String> ExamPlacement = new HashMap<>();
    private HashMap<Exam, Period> ExamPeriod = new HashMap<>();
    private HashMap<Exam, Integer> ExamSD = new HashMap<>();
    private int[][] periodRooomCapacity;



    public HashMap<Exam, Integer>  largestEnrolment(List<Exam> exams)
    {
      //  List<Exam> firstExam = getDf().getExamsByDegrees(getDf().getExamLargestEnrolmentDegrees(exams));
       // System.out.println("First Exam "+firstExam.get(0));
        return getDf().getExamLargestEnrolmentDegrees(exams);

    }


    public  HashMap<Exam, Integer>  largestDegree(List<Exam> exams)
    {
        //  System.out.println(tst.getExams());
        ClashMatrix cm= getDf().calculateClashMatrix(exams);
      //  List<Exam> firstExam = getDf().getExamsByDegrees(getDf().getExamClashDegrees(cm));
        //System.out.println("First Exam "+firstExam.get(0));

        return getDf().getExamClashDegrees(cm);

    }


    public HashMap<Exam, Integer>   largestWeightDegree(List<Exam> exams)
    {

        //  System.out.println(tst.getExams());
        ClashMatrix cm= getDf().calculateClashMatrix(exams);
      //  List<Exam> firstExam = getDf().getExamsByDegrees(getDf().getExamWeightedClashDegrees(cm));
      //  System.out.println("First Exam "+firstExam.get(0));

        return getDf().getExamWeightedClashDegrees(cm);

    }

    public HashMap<Exam, Integer>  SaturationDegree(List<Exam> exams)
    {
        HashMap<Exam, Integer> ExamSaturationDegree = new HashMap<>();

        ClashMatrix cm= getDf().calculateClashMatrix(exams);

        Collections.shuffle(exams);

        List<Period> periods= getDf().getPeriods();
        int degree=periods.size();

        for(int i=0;i<periods.size();i++)
        {

            for(Exam elem : exams)
            {

                if (! ( getExamPlacement().containsKey(elem.getName())) )
                {
                    Room room1 = getRoomByStudentSize(elem.getNumStudents());

                    boolean sd = getDf().checkExamClashes(getInitialSolution(), periods.get(i), elem, cm);
                   // System.out.println(" Clashes btn "+Arrays.toString(getHeuristicPlacements().toArray())+ " and " +elem.getName()+ " = "+sd);
                    if(sd==false)
                    {int tempDegree=degree-i;
                    ExamSaturationDegree.put(elem,tempDegree);
                    assignExams(elem, room1, periods.get(i));
                    getExamPlacement().put(elem.getName(),periods.get(i).getName());
                    }
                }

            }
          //  System.out.println(" Cost "+hc.calculateConstraintViolations(getHeuristicPlacements()));
          //  System.out.println("\n");

         //  System.out.println("Exam Placements "+Arrays.toString(getHeuristicPlacements().toArray()));
        }

        List<Exam> tempExams= getExamsFromExamPlacement(getInitialSolution());
        HashSet eSet = new HashSet<>(getDf().getExams());
        eSet.removeAll(tempExams);

        Iterator value = eSet.iterator();
        while (value.hasNext())
        {
            ExamSaturationDegree.put(getDf().getExamByName(value.next().toString()),0);

        }
      //  List<Exam> firstExam = getDf().getExamsByDegrees(ExamSaturationDegree);

       // System.out.println("First Exam "+firstExam.get(ExamSaturationDegree.size()-1));

        return ExamSaturationDegree;
    }


    public List<ExamConflicts>  getExamsByTimeslotConflicts()
    {
        HashSet<Exam> Exams = new HashSet<>();
        List<ExamConflicts> ec=new ArrayList<>();
        int difficulty=0;
        List<Exam> exams= getDf().getExams();
        Collections.shuffle(exams);
        System.out.println("Total Exams :"+exams.size()+"\n");
        List<Room> rooms= getDf().getRooms();
        List<Period> periods= getDf().getPeriods();


            for (Exam elem : exams) {
                Room room = getRoomByStudentSize(elem.getNumStudents());
                Period period = getDf().getRandomPeriod(elem.getLength());
                if (!Exams.contains(elem))
                {
                    for(int i=0; i<periods.size(); i++)
                    {
                        if (periods.get(i).getName().equals(period.getName())) {
                        assignExams(elem, room, period);

                        int conflict = getSlotAssigmentConflict(getInitialSolution(), periods.get(i));
                        if (conflict > 0) {
                            difficulty++;
                        }
                        ExamConflicts tempEc = new ExamConflicts(elem, difficulty);
                        ec.add(tempEc);
                        Exams.add(elem);
                        System.out.print("Period "+period.getName()+  " TimeslotConflicts: "+tempEc.getName());

                    }

                }
            }
            System.out.println();
        }

                return ec;
           }

    public UtilityFunctions getDf() {
        return df;
    }

    public void setDf(UtilityFunctions df) {
        this.df = df;
    }

    public List<ExamPlacement>  getInitialSolution() {
        return initialSolution ;
    }


    public List<Room> getRoomsByPeriod(List<ExamPlacement> ep, Period p)
    {
        List<Room> ex=new ArrayList<>();

        for(int i=0;i<ep.size();i++)
        {
            if(ep.get(i).getPeriod().getName().equals(p.getName()))
            {
                ex.add(ep.get(i).getRoom());
            }

        }

        return new ArrayList<>(new LinkedHashSet<>(ex));
    }

    public List<Exam> getExamsByRoom(List<ExamPlacement> ep, Room r)
    {
        List<Exam> ex=new ArrayList<>();

        for(int i=0;i<ep.size();i++)
        {
            if(ep.get(i).getRoom().getName().equals(r.getName()))
            {
                ex.add(ep.get(i).getExam());
            }

        }

        return new ArrayList<>(new LinkedHashSet<>(ex));
    }

    public List<Exam> getExamsByPeriod(List<ExamPlacement> ep, Period p)
    {
        List<Exam> ex=new ArrayList<>();

        for(int i=0;i<ep.size();i++)
        {
            if(ep.get(i).getPeriod().getName().equals(p.getName()))
            {
                ex.add(ep.get(i).getExam());
            }

        }

        return new ArrayList<>(new LinkedHashSet<>(ex));
    }



    public int getRoomSpaceByPeriod(List<ExamPlacement> ep, Room r, Period p)
    {
        int usedSpace=0;
        int availableSpace=0;

        if(ep.equals(null))
        {
            availableSpace=r.getCapacity();
        }
        else {

            List<Room> listOfRooms = getRoomsByPeriod(ep, p);

            for (int i = 0; i < listOfRooms.size(); i++) {
                if (listOfRooms.get(i).getName().equals(r.getName())) {
                    List<Exam> ex = getExamsByRoom(ep, r);
                    usedSpace = getUsedSpace(ex);
                      System.out.println(" Used Space "+usedSpace);
                    int tempSpace = r.getCapacity() - usedSpace;

                    if (tempSpace > 0) {
                        availableSpace = tempSpace;
                    }

                }

            }
        }

        return availableSpace;
    }

    public int getUsedSpace(List<Exam> ex)
    {
        int used=0;
        for(int i=0; i<ex.size();i++)
        {
            used+=ex.get(i).getNumStudents();
        }
        return used;
    }

    public List<PeriodRoomCapacity> getPeriodCapacity() {
        return periodCapacity;
    }

    public int  getPeriodCapacity(Period p, Room rm)
    {
        int cap=0;
        int availableSpace=rm.getCapacity();

        List<PeriodRoomCapacity> tempCap=getPeriodCapacity();

         for(int i=0;i<tempCap.size();i++)
               {
                   if(tempCap.get(i).getPeriod().getName().equals(p.getName()) && (tempCap.get(i).getRoom().getName().equals(rm.getName())))
                   {
                       cap+=tempCap.get(i).getpCapacity();
                   }

                   int tmpAvailableSpace=rm.getCapacity()-cap;

                   if(tmpAvailableSpace >= 0)
                   {
                       availableSpace=tmpAvailableSpace;
                   }

                }

            return availableSpace;
    }


    public Period  getPeriodByRoomCapacity(Exam e,Room rm, int capacity)
    {
        Period p=getDf().getRandomPeriod(e.getLength());

        List<PeriodRoomCapacity> tempCap=getPeriodCapacity();
        List<Period> periods =getDf().getPeriods();

        if(tempCap.equals(null) )
        {
            return p;
        }
        else
        {
            for(int i=0;i<periods.size();i++)
            {
                List<Room> rms= getRoomsFromPeriodRoomCapacity( tempCap, periods.get(i));

                for(int j=0;j<rms.size();j++)
                {

                    int aCapacity = getPeriodCapacity(periods.get(i), rms.get(j));
                 //   System.out.println("capacity "+aCapacity);

                    if (capacity <= aCapacity) {
                        p = periods.get(i);
                        break;
                    }
                }
               // System.out.println();
            }

       }
       return p;
    }


    public Room getRoomByStudentSize(int studentsize) {


        List<Room> rms = new ArrayList<>();
        int idx = 0;

        outerloop:
        for(int i=0;i<getPeriodRooomCapacity().length;i++)
        {
            for (int j = 0; j < getPeriodRooomCapacity()[0].length; j++)
            {
                Room tempRoom = new Room(j,getPeriodRooomCapacity()[i][j],getDf().getRoomByName("R"+j).getrPenalty());
                rms.add(tempRoom);

            }
            // System.out.println();
            Collections.sort(rms, Room.getCompareRoomCapacity());
            //System.out.println(" Rooms "+rms);

            for (int c = 0; c < rms.size(); c++)
            {
                if (rms.get(c).getCapacity()>=studentsize)
                {
                    idx = c;
                    break outerloop;
                }
            }
        }


        return rms.get(idx);
    }

    public Room checkRoomAvailabilty(Room rm, Period p, int capacity) {

        int cap = getPeriodCapacity(p,rm);
       // System.out.println(" Capacity "+cap);

        if(cap>=capacity)
        {
            return rm;
        }
        else
        {
            List<Room> tempRooms=getDf().getRooms();
            Collections.sort(tempRooms, Room.getCompareRoomCapacity());
            return tempRooms.get(tempRooms.indexOf(rm)+1);
        }


        }




    public int getSlotAssigmentConflict(List<ExamPlacement> ep, Period p)
    {

       HardConstraints hc=new HardConstraints();
       hc.setDf(getDf());
       int cost=0;
       int rm=0;
        for(int i=0;i<ep .size();i++)
        {
            if(ep.get(i).getPeriod().getName().equals(p.getName()))
            {
                   cost= hc.calculateConstraintViolations(ep);

            }

               rm=cost;

        }
        return rm;
    }

    public void assignExams(Exam e, Room r, Period p)
    {
        getExamPlacement().put(e.getName(), p.getName());
        getInitialSolution().add(new ExamPlacement(e, r, p));
        periodCapacity.add(new PeriodRoomCapacity(p, r, e.getNumStudents()));
        UpdatePeriodRooms(e, p, r);
      //  printPeriodRoomPlacement(getPeriodRooomCapacity());

    }

    public List<Exam> getExamsFromExamPlacement( List <ExamPlacement> ep)
    {
        List<Exam> ex=new ArrayList<>();

        for(int i=0;i<ep.size();i++)
        {
            ex.add(ep.get(i).getExam());

        }
        return new ArrayList<>(new LinkedHashSet<>(ex));

    }
    public List<Room> getRoomsFromPeriodRoomCapacity( List <PeriodRoomCapacity> ep, Period p)
    {
        List<Room> ex=new ArrayList<>();

        for(int i=0;i<ep.size();i++)
        {
            if(ep.get(i).getPeriod().getName().equals(p.getName()))
            {
                ex.add(ep.get(i).getRoom());
            }

        }
        return new ArrayList<>(new LinkedHashSet<>(ex));

    }
    public boolean checkIfPeriodAssigned( List <ExamPlacement> ep, Period p)
    {
        boolean assigned =false;

           for(int i=0;i<ep.size();i++)
        {
            if(ep.get(i).getPeriod().getName().equals(p.getName()))
            assigned=true;

        }
        return assigned;

    }



    /**
     * Get a random exam
     *
     * @return exam
     */
    public Exam getRandomExam(List<Exam> exams) {
       return exams.get((new Random()).nextInt(exams.size()));
    }

    public HashMap<String, String> getExamPlacement() {
        return ExamPlacement;
    }

    public boolean checkPeriodRoomSpace(Period p, int capacity)
    {
        boolean status=false;
        int size=0;
        int usedspace=0;
        List<PeriodRoomCapacity> tempCap=getPeriodCapacity();

        for(int i=0;i<tempCap.size();i++)
        {
            if(tempCap.get(i).getPeriod().getName().equals(p.getName()) )
            {
                size+=tempCap.get(i).getRoom().getCapacity();
                usedspace+=tempCap.get(i).getpCapacity();
            }

        }

        if( capacity > (size-usedspace))
        {
            status=true;
        }

        return status;
    }


    public int[][] getPeriodRooomCapacity() {
        return periodRooomCapacity;
    }

    public void setPeriodRooomCapacity(int[][] periodRooomCapacity) {
        this.periodRooomCapacity = periodRooomCapacity;
    }


    public void initializePeriodRooms()
    {
        List<Room> tempRooms=getDf().getRooms();
      //  Collections.sort(tempRooms, Room.getCompareRoomCapacity());

        int[][] temp= new int[getDf().getPeriods().size()][tempRooms.size()];

        for(int i=0;i<getDf().getPeriods().size();i++)
        {
           for(int j=0;j<tempRooms.size();j++)
            {

               temp[i][j] = tempRooms.get(j).getCapacity();

            }
            // System.out.println();
        }
           setPeriodRooomCapacity(temp);
    }

    public void UpdatePeriodRooms(Exam e, Period p, Room r)
    {
     //  printPeriodRoomPlacement(getPeriodRooomCapacity());
        outerloop:
        for(int i=0;i<getPeriodRooomCapacity().length;i++)
        {
            for (int j = 0; j < getPeriodRooomCapacity()[0].length; j++)
                {
                    if ((p.getpId()==i) && (r.getRoomId()==j) && getPeriodRooomCapacity()[i][j] >=e.getNumStudents())
                    {
                        getPeriodRooomCapacity()[i][j] =getPeriodRooomCapacity()[i][j] - e.getNumStudents();
                     //   System.out.println(" Exam :"+e.getName()+ " NumStudents "+e.getNumStudents());
                      //  System.out.println(" Room :"+r.getName()+ " Room Capacity "+r.getCapacity());
                      //  System.out.println(" Period :"+p.getName());
                        break outerloop;
                    }
                }
                // System.out.println();
            }
        setPeriodRooomCapacity(getPeriodRooomCapacity());
     }

     public Period findSuitablePeriod(Exam e)
    {
        int pID =0;
        outerloop:
        for(int i=0;i<getPeriodRooomCapacity().length;i++)
        {
            for (int j = 0; j < getPeriodRooomCapacity()[0].length; j++)
            {
                if (getPeriodRooomCapacity()[i][j] >= e.getNumStudents() && getDf().getPeriodByName("P"+i).getpLength() >=e.getLength())
                {
                    int tempPID=i;
                    pID=tempPID;
                    break outerloop;
                }
            }
            // System.out.println();
        }

        return getDf().getPeriodByName("P"+pID);

    }

    public Period findSuitableRandomPeriod(Exam e)
    {
      //  printPeriodRoomPlacement(getPeriodRooomCapacity());
         List<Integer> rpID=new CopyOnWriteArrayList<>();

        for(int i=0;i<getPeriodRooomCapacity().length;i++)
        {
            for (int j = 0; j < getPeriodRooomCapacity()[0].length; j++)
            {
                if (getPeriodRooomCapacity()[i][j] >= e.getNumStudents() && getDf().getPeriodByName("P"+i).getpLength() >=e.getLength())
                {
                     rpID.add(i);

                }
            }
            // System.out.println();
        }

        int pID=rpID.get((new Random()).nextInt(rpID.size()));

        return getDf().getPeriodByName("P"+pID);

    }
    public Room findSuitableRoom(Exam e, Period p)
    {
        int rID =0;
        outerloop:
        for(int i=0;i<getPeriodRooomCapacity().length;i++)
        {
            for (int j = 0; j < getPeriodRooomCapacity()[0].length; j++)
            {
                if ((i==p.getpId())&& getPeriodRooomCapacity()[i][j] >= e.getNumStudents())
                {
                    int tempPID=i;
                    rID=tempPID;
                    break outerloop;
                }
            }
            // System.out.println();
        }

        return getDf().getRoomByName("R"+rID);

    }

     public void printPeriodRoomPlacement(int[][] ps)
     {

         for(int i=0;i<ps.length;i++)
         {
             for(int j=0;j<ps[0].length;j++)
             {
                 System.out.print(" "+ps[i][j]);
             }
             System.out.println();
         }
         System.out.println();
     }


    public HashMap<Exam, Integer> getExamSD() {
        return ExamSD;
    }

    public boolean checkExamClashes(HashMap<Exam, Period> hmap,Period p, Exam e, ClashMatrix cmatrix)
    {
        boolean status=false;

        double clashes=0;

        for (Map.Entry<Exam, Period> entry : hmap.entrySet())
        {
           if (Objects.equals(p, entry.getValue()))
           {
            clashes += cmatrix.getCost(entry.getKey().getId(), e.getId());
             }


        }
        if(clashes > 0.0)
        {
            status=true;
        }


        return status;
    }

    public HashMap<Exam, Period> getExamPeriod() {
        return ExamPeriod;
    }

 public List<ExamPlacement> constructInitialSolution(List<Exam> exams)
 {

     HashMap<Exam, Integer> examsBySD = SaturationDegree(exams);
     System.out.println(examsBySD);
     List<Exam> ExamScheduleOrder= new CopyOnWriteArrayList<>();

         for(int j=0;j<getDf().getPeriods().size();j++)
         {
           //Get the list of Exams whose value matches with given value.
             List<Exam> listOfExamBySD = getAllKeysForValue(examsBySD, j);
             System.out.println("Period "+j+" Exams "+listOfExamBySD);

             if(listOfExamBySD.size() > 1)
             {
                 HashMap<Exam, Integer> examsByLD = largestDegree(listOfExamBySD);
                 System.out.println(examsByLD);

                // printExamDegrees(examsByLD);
             }
             else
             {
                 ExamScheduleOrder.add(listOfExamBySD.get(0));
                 System.out.println(listOfExamBySD.get(0));
             }
         }


     return getInitialSolution();
 }

    /*
        * Get the all the keys associated with given Value V from map
        */
    static <K, V> List<K> getAllKeysForValue(Map<K, V> mapOfWords, V value)
    {
        List<K> listOfKeys = null;

        //Check if Map contains the given value
        if(mapOfWords.containsValue(value))
        {
            // Create an Empty List
            listOfKeys = new ArrayList<>();

            // Iterate over each entry of map using entrySet
            for (Map.Entry<K, V> entry : mapOfWords.entrySet())
            {
                // Check if value matches with given value
                if (entry.getValue().equals(value))
                {
                    // Store the key from entry to the list
                    listOfKeys.add(entry.getKey());
                }
            }
        }
        // Return the list of keys whose value matches with given value.
        return listOfKeys;
    }

    public Map<Integer, List<Exam>> getOrderedMapLists(HashMap<Exam, Integer> exams)
    {

        Map<Integer, List<Exam>> segList = new HashMap<>();
        Iterator<Map.Entry<Exam, Integer>> i = exams.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry<Exam, Integer> next = i.next();
            if (segList.get(next.getValue()) != null) {
                List<Exam> ex= segList.get(next.getValue());
                ex.add(next.getKey());
                segList.put(next.getValue(), ex);
            }else{
                List<Exam> ex= new ArrayList<>();
                ex.add(next.getKey());
                segList.put(next.getValue(), ex);

            }
        }
        return segList;
    }
    static <K,V extends Comparable<? super V>>
    List<Map.Entry<K, V>> entriesSortedByValues(Map<K,V> map) {

        List<Map.Entry<K,V>> sortedEntries = new ArrayList<>(map.entrySet());

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

    public void printExamDegrees(HashMap<Exam, Integer> hmap)
    {
        System.out.println(entriesSortedByValues(hmap));

    }
}
