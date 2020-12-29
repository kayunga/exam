/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package GE_HH.vehicleRoutingProblem.ecjFunctions;

import GE_HH.examTimetablingProblem.ProblemDomain.TimetableData1;
import GE_HH.vehicleRoutingProblem.ProblemDomain.VrpData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

import java.util.Random;

/* 
 * Move.java
 * 

 */

/**
 * @author George Mweshi

 */

public class Ephemeral extends GPNode
    {
       VrpData rd = new VrpData();

        public Ephemeral()
        {
            Random rn = new Random();
            int min=0;
            int max=5;

           int num = rn.nextInt(max - min + 1) + min;
           rd.x=Integer.toString(num);

        }

        public String toString() {
            return rd != null ?
                String.valueOf(rd.x) : "n";
        }

        public int expectedChildren() { return 0; }

        @Override
        public void eval(final EvolutionState state,
                         final int thread,
                         final GPData input,
                         final ADFStack stack,
                         final GPIndividual individual,
                         final Problem problem) {
            ((VrpData)(input)).x = rd.x;
        }


        }






