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


public class Period extends GPNode
{

    public String toString() { return "period"; }

    public int expectedChildren() { return 0; }



    @Override
    public void eval(EvolutionState evolutionState, int i, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem) {

        TimetableData rd=(TimetableData) gpData;
        rd.x = ((TimetableProblem)problem).currentPeriod;



    }
}