package GE_HH.examTimetablingProblem.ProblemDomain;
import ec.gp.GPData;

/*
 * VrpData.java - our return type will be a string
 *
 * @author George Mweshi
 *
 */

public class TimetableData1 extends GPData
{
    // The number of components
    public static final int numComponents = 4;

    // An array of 3 Strings for Timetable data
    public String[] x;

    public TimetableData1()
    {
        x = new String[numComponents];
    }

    public Object clone()
    {
        TimetableData1 dat = (TimetableData1)(super.clone());
        dat.x = new String[numComponents];
        System.arraycopy(x,0,dat.x,0,numComponents);
        return dat;
    }


    public void copyTo(final GPData gpd)
    {
        TimetableData1 md = ((TimetableData1)gpd);
        for(int i=0;i<numComponents;i++)
            md.x[i] = x[i];
    }
}