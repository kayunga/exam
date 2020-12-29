package GE_HH.examTimetablingProblem.ecjFunctions;


import GE_HH.examTimetablingProblem.ProblemDomain.TimetableData;
import GE_HH.examTimetablingProblem.ProblemDomain.TimetableProblem;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;


public class Row extends GPNode
{

    public String toString() { return "row"; }

       public int expectedChildren() { return 0; }



    @Override
    public void eval(EvolutionState evolutionState, int i, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem) {

        TimetableData rd=(TimetableData) gpData;
        rd.x= ((TimetableProblem)problem).currentExamPlacement;

    }
}