package GE_HH.vehicleRoutingProblem.Run;

import GE_HH.vehicleRoutingProblem.Utilities.ClarkeWright;
import GE_HH.vehicleRoutingProblem.Utilities.ProblemInstance;
import GE_HH.vehicleRoutingProblem.Utilities.Solution;

import java.io.File;

public class testRun {

    public static void main(String[] args) throws Exception {

        File filename = new File("C:\\Users\\George\\IdeaProjects\\ResearchProject\\src\\GE_HH\\vehicleRoutingProblem\\datasets\\Christofides-Mingozzi-Toth_1979\\vrpnc1.txt");


        ProblemInstance problem =new ProblemInstance();
        problem.loadChristofidesDataset(filename);


      //  System.out.println("Depot: "+problem.getDepot().getLocation());


        //ProblemInstance problem = ds.loadProblemInstance(filename);
       // Route=problem.


        ClarkeWright cw= new ClarkeWright(problem);
       // cw.vCapacity= problem.getVehicleCapacity();


        System.out.println("\n Clarke Wright Heuristic Solver");
        System.out.println("..................................................");
        Solution initial=cw.generateSingleCustomerRoute();
        System.out.println(initial.getRoutes());
        System.out.println("Initial Solution Cost :"+initial.getSolutionCost());
        System.out.println("\nClarke Wright Savings (Descending order))");
        System.out.println(cw.findAllPairs(cw.generateSingleCustomerRoute()));
        Solution sol2= cw.applyClarkeWrightHeuristic();
        System.out.println("\nSolution After Applying Clarke Wright Heuristic");
        for(int i=0;i<sol2.getRoutes().size();i++)
        {
            System.out.println("Vehicle ("+(i+1)+") :"+sol2.getRoutes().get(i)+ " Route Demand:"+sol2.getRoutes().get(i).getTotalRouteDemand()+" Route cost:"+sol2.getRoutes().get(i).getRouteCost(problem.getProblemInstnceCostMatrix())+" Route Duration :"+sol2.getRoutes().get(i).getRouteDuration());
        }
       // System.out.println(sol2.toString(problem.getProblemInstnceCostMatrix(),problem));
        System.out.println("Clarke Wright Heuristic Solution Cost :"+sol2.getSolutionCost());




    }





}
