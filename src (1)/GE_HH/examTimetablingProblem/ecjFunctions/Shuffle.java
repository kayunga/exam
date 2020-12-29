/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package GE_HH.examTimetablingProblem.ecjFunctions;

import GE_HH.examTimetablingProblem.ProblemDomain.TimetableData;
import GE_HH.examTimetablingProblem.ProblemDomain.TimetableData1;
import GE_HH.examTimetablingProblem.ProblemDomain.TimetableProblem;
import GE_HH.examTimetablingProblem.ProblemDomain.TimetableProblem1;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

import java.util.Arrays;
import java.util.List;

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


            TimetableData tdata =(TimetableData) input;
            TimetableProblem rd = (TimetableProblem) problem;

            int num=rd.generateRandomNumber();

            children[0].eval(state,thread,input,stack,individual,problem);
            String temp =tdata.x;


            if (rd.currentExam.equals(temp)) {
                rd.shuffleExamList(num);
                children[1].eval(state,thread,input,stack,individual,problem);


            }
            if (rd.currentRoom.equals(temp)) {
                rd.shuffleRoomList(num);
                children[1].eval(state,thread,input,stack,individual,problem);


            }
            if (rd.currentPeriod.equals(temp)) {

                rd.shufflePeriodList(num);
                children[1].eval(state,thread,input,stack,individual,problem);
            }


        }


    }



