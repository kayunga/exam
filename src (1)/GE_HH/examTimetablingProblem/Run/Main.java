package GE_HH.examTimetablingProblem.Run;

import java.util.*;

public class Main {
  public static void main(String[] args) {

    System.out.println(getRandomNumberInRange());

    /*
    Set<String> a = new HashSet<>();
    a.add("a");
    a.add("a2");
    a.add("common");

    Set<String> b = new HashSet<>();
    b.add("b");
    b.add("common");
    b.add("b2");



    Set<String> inAOnly = new HashSet<>(a);
    inAOnly.removeAll(b);
    System.out.println("A Only: " + inAOnly );

    Set<String> inBOnly = new HashSet<>(b);
    inBOnly .removeAll(a);
    System.out.println("B Only: " + inBOnly );

    Set<String> common = new HashSet<>(a);
    common.retainAll(b);
    System.out.println("Common: " + common);
    System.out.println("Common size: " + common.size());






    List<String> aList = new ArrayList<String>();
    List<String> asList = new ArrayList<String>();

    aList.add("1");
    aList.add("2");
    aList.add("3");
    aList.add("4");
    aList.add("5");

    ListIterator<String> listIterator = aList.listIterator();
    System.out.println("Previous: " + listIterator.previousIndex());
    System.out.println("Next: " + listIterator.nextIndex());

    // advance current position by one using next method
    listIterator.next();
    System.out.println("Previous: " + listIterator.previousIndex());
    System.out.println("Next: " + listIterator.nextIndex());

    System.out.print("Before Shuffle :");
    for(int i=0; i< aList.size();i++)
    {

      System.out.print(aList.get(i));
    }

    System.out.println();

    //asList=aList.subList(Math.max(aList.size() - 1, 0), aList.size());
    List newList=  aList.subList(0, aList.size());
    Collections.shuffle(newList);

    System.out.print("After Shuffle :");
    for(int i=0; i< aList.size();i++)
    {

        System.out.print(aList.get(i));
    }
    System.out.println();
*/





  }

  private static int getRandomNumberInRange() {

    if (0 >= 5) {
      throw new IllegalArgumentException("Num of vehicles must be greater than 0");
    }

    Random r = new Random();
    return r.nextInt((5 - 0)) ;
  }

}
