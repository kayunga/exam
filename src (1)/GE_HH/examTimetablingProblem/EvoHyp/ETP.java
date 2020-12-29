/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GE_HH.examTimetablingProblem.EvoHyp;

import GE_HH.examTimetablingProblem.Utilities.UtilityFunctions;
import problemdomain.ProblemDomain;

import java.io.File;

/**
 *
 * @author GM
 */
public class ETP extends ProblemDomain {
    private String fName;
    private int evalLim;
    private UtilityFunctions ds;


    ETP(String fileName) throws Exception {
        this.fName = fileName;
        this.evalLim = -1;
        ds = new UtilityFunctions();
        ds.loadfile( new File(fileName));
    }
    
    ETP(String fileName, int evalLim) throws Exception {
        this.fName = fileName;
        this.evalLim = -1;
        ds = new UtilityFunctions();
        ds.loadfile(new File(fileName));
    }
    
    public String getFileName () {
        return this.fName;
    }
    
    public void setFileName (String fileName) throws Exception {
        this.fName = fileName;
        ds = new UtilityFunctions();
        ds.loadfile(new File(fileName));
    }
    
    public void setEvalLim (int evalLim) {
        this.evalLim = evalLim;
    }
    
    public int getEvalLim () {
        return this.evalLim;
    }
    
    @Override
    public TTProblemInstance evaluate(String string) {

           TTProblemInstance ps = new TTProblemInstance(this.ds);

            ps.setHeuCom(string);

            int nRun;

            if (this.evalLim == -1) {

                nRun = Integer.MIN_VALUE;
            } else {
                nRun = 0;
            }

            while ((nRun <= this.evalLim) && (ps.examsNotScheduled())) {
                for (int i = 0; i < string.length(); i++) {
                    switch ( string.charAt(i) ) {
                    case 'L':
                      ps.LargestDegree();
                        break;
                    case 'E':
                       ps.LargestEnrolmentDegree();
                        break;
                    case 'W':
                         ps.LargestWeightedDegree();
                        break;

                    case 'S':
                            ps.LeastSaturationDegree();
                            break;
                    default:
                            System.out.println("Invalid heuristic letter \'" + string.charAt(i) + "\' at index " + (i));
                            break;
                    }
                }
                nRun++;
            }


        return ps;
    }
    
}
