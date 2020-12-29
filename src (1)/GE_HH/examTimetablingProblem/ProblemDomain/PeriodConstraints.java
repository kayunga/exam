package GE_HH.examTimetablingProblem.ProblemDomain;

public class PeriodConstraints {

    private int A;
    private int B;
    private String constraintType;


    public void setAFTERConstraint(int a, int b)
    {
        this.constraintType="AFTER";
        this.A=a;
        this.B=b;

    }
    public void setEXAM_COINCIDENCEConstraint(int a, int b)
    {
        this.constraintType="EXAM_COINCIDENCE";
        this.A=a;
        this.B=b;

    }
    public void setEXCLUSIONConstraint(int a, int b)
    {
        this.constraintType="EXCLUSION";
        this.A=a;
        this.B=b;

    }


    public int getA() {
        return A;
    }

    public int getB() {
        return B;
    }

    public String getConstraintType() {
        return constraintType;
    }

    /** Name of Constraint is (vehicle A, Type, vehicle B) */
    public String getName()
    {
        return "E"+getA()+", "+getConstraintType()+" , E"+getB();
    }
}
