/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package GE_HH.vehicleRoutingProblem.ecjFunctions;

import GE_HH.vehicleRoutingProblem.ProblemDomain.VrpData;
import GE_HH.vehicleRoutingProblem.components.Customer;
import GE_HH.vehicleRoutingProblem.components.Route;
import GE_HH.vehicleRoutingProblem.components.Vehicle;
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

public class Shuffle extends GPNode
    {
    public String toString() { return "shuffle"; }

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

            Customer tempCustomer;
            Route tempRoute;
            Vehicle tempVehicle;

            VrpData tdata = (VrpData) input;
         //   VrpProblem rd = (VrpProblem) problem;

            children[0].eval(state, thread, input, stack, individual, problem);
        //    tempCustomer=tdata.customer;
         //   tempRoute=tdata.route;
          //  tempVehicle=tdata.vehicle;
/*
            if (rd.getCurrentCustomer().equals(tempCustomer))
            {
                children[1].eval(state, thread, input, stack, individual, problem);
                int num=rd.getRandomNumberofCustomers();
                GE_HH.vehicleRoutingProblem.components.Customer p= rd.getRandomCustomer(num);
                tdata.customer=p;

            }
            if(rd.getCurrentRoute().equals(tempRoute))
            {
                children[1].eval(state, thread, input, stack, individual, problem);
                int num=rd.getRandomNumberofVehicles();
                GE_HH.vehicleRoutingProblem.components.Route p= rd.getRandomRoute(num);
                tdata.route=p;

            }
            if(rd.getCurrentVehicle().equals(tempVehicle))
            {
                children[1].eval(state, thread, input, stack, individual, problem);
                int num=rd.getRandomNumberofVehicles();
                GE_HH.vehicleRoutingProblem.components.Vehicle p= rd.getRandomVehicle(num);
                tdata.vehicle=p;

            }
*/

        }


    }



