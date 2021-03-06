package GE_HH.vehicleRoutingProblem.ecjFunctions;


import GE_HH.vehicleRoutingProblem.ProblemDomain.VrpData;
import GE_HH.vehicleRoutingProblem.ProblemDomain.VrpProblem;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;


public class Vehicle extends GPNode
{

    public String toString() { return "vehicle"; }

    public int expectedChildren() { return 0; }



    @Override
    public void eval(EvolutionState evolutionState, int i, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem) {

        VrpData rd=(VrpData) gpData;
        rd.x= ((VrpProblem) problem).currentVehicle.toString();

    }
}