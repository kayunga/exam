package GE_HH.examTimetablingProblem.ProblemDomain;

public class RoomConstraints {

    private int A;
    private String constraintType;


    public void setROOM_EXCLUSIVEConstraint(int a)
    {
        this.constraintType="ROOM_EXCLUSIVE";
        this.A=a;

    }


    public int getA() {
        return A;
    }

    public String getConstraintType() {
        return constraintType;
    }

    /** Name of Constraint is (vehicle + Type) */
    public String getName()
    {
        return "R"+getA()+", "+getConstraintType();
    }

}
