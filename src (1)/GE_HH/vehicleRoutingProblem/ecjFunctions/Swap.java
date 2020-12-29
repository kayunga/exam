/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package GE_HH.vehicleRoutingProblem.ecjFunctions;

import GE_HH.vehicleRoutingProblem.ProblemDomain.VrpData;
import GE_HH.vehicleRoutingProblem.components.Route;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

/* 
 * Move.java
 * 

 */

/**
 * @author George Mweshi

 */

public class Swap extends GPNode
    {
    public String toString() { return "swap"; }

    /*
      public void checkConstraints(final EvolutionState state,
      final int tree,
      final GPIndividual typicalIndividual,
      final Parameter individualBase)
      {
      super.checkConstraints(state,tree,typicalIndividual,individualBase);
      if (children.length!=0)
      state.output.error("Incorrect number of children for node " + 
      toStringForError() + " at " +
      individualBase);
      }
    */
    public int expectedChildren() { return 2; }

    public void eval(final EvolutionState state,
        final int thread,
        final GPData input,
        final ADFStack stack,
        final GPIndividual individual,
        final Problem problem)
        {
    /*        Route tempRoute;

            VrpData tdata = (VrpData) input;
            VrpProblem rd = (VrpProblem) problem;

            children[0].eval(state, thread, input, stack, individual, problem);
            tempRoute=tdata.route;

            if(rd.getCurrentRoute().equals(tempRoute))
            {
                children[1].eval(state, thread, input, stack, individual, problem);
                GE_HH.vehicleRoutingProblem.components.Route p= rd.twoOptSwap(tempRoute);
                tdata.route=p;

            }
*/
        }


    }



