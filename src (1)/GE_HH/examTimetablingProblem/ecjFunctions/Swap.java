/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package GE_HH.examTimetablingProblem.ecjFunctions;

import GE_HH.examTimetablingProblem.ProblemDomain.*;
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
            TimetableData tdata=(TimetableData) input;
        TimetableProblem rd = (TimetableProblem) problem;

          children[0].eval(state,thread,input,stack,individual,problem);
          String temp=tdata.x;

             if (rd.currentExam.equals(temp)) {

            children[1].eval(state,thread,input,stack,individual,problem);
            String exam = rd.getAnotherExam(temp);
            tdata.x=exam;
                   }
            if (rd.currentRoom.equals(temp)) {
                children[1].eval(state,thread,input,stack,individual,problem);
                String room = rd.getAnotherRoom(temp);
                tdata.x=room;

            }
            if (rd.currentPeriod.equals(temp)) {
                children[1].eval(state,thread,input,stack,individual,problem);
                String period= rd.getAnotherPeriod(temp);
                tdata.x=period;
                        }

            if (rd.currentExamPlacement.equals(temp)) {
            children[1].eval(state,thread,input,stack,individual,problem);
                String period =rd.getAnotherPeriod(temp);
                String room =rd.getAnotherRoom(temp);
                String exam=rd.getAnotherExam(temp);
                tdata.x=exam+" "+room+" "+period;
            }



        }


    }



