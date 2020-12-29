package GE_HH.examTimetablingProblem.ProblemDomain;
import ec.gp.GPData;

/*
 * VrpData.java - our return type will be a string
 *
 * @author George Mweshi
 *
 */

public class TimetableData extends GPData
{
    // return value
    public String x;



    public void copyTo(final GPData gpd)
    {

        ((TimetableData)gpd).x = x;


    }
}