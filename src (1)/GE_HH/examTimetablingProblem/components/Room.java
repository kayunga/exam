package GE_HH.examTimetablingProblem.components;

import java.util.Comparator;

/**
 * Representation of a room - used to store the room capacity and compare against the student Group's size.
 * */

public class Room {

    //Room variables
    private int roomId;
    private int capacity; //room capacity
    private int rPenalty; //for using this room
    private int usedSpace; // used space after exam assignments
    private boolean roomExclusive; //determines whether room is exclusive or not

    // Constructor
    public Room(int id, int size, int penalty) {
         roomId = id;
         setCapacity(size);
         usedSpace=0;
         rPenalty = penalty;
    }

    public static Comparator<Room> getCompareRoomCapacity() {
        return compareRoomCapacity;
    }

    public static void setCompareRoomCapacity(Comparator<Room> compareRoomCapacity) {
        Room.compareRoomCapacity = compareRoomCapacity;
    }

    //get room id
    public int getRoomId() {
        return roomId;
    }

   // get room capacity
    public int getCapacity() {
        return capacity;
    }

    //get penalty for using this room
    public int getrPenalty() {
        return rPenalty;
    }

    // string representation of a room name
    public String getName()
    {
        return "R"+getRoomId();
    }

    // String representation for a room
       public String toString()
    {

        return getName()+"["+getCapacity()+","+getrPenalty()+"]";
    }

    // is room exclusive
    public boolean isRoomExclusive() {
        return roomExclusive;
    }

    // set room status
    public void setRoomExclusive(boolean roomExclusive) {
        this.roomExclusive = roomExclusive;
    }

   // Return available space in the given period
    public int getAvailableSpace() {
        return getCapacity() - getUsedSpace();
    }

     private static Comparator<Room> compareRoomCapacity = (r1, r2) -> {

        int ro1 = r1.getCapacity();
        int ro2 = r2.getCapacity();

       /*For ascending order*/
        return ro1-ro2;

   /*For descending order*/
        //rollno2-rollno1;
    };

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getUsedSpace() {
        return usedSpace;
    }

    public void setUsedSpace(int usedSpace) {
        this.usedSpace = usedSpace;
    }
}
