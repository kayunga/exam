package GE_HH.vehicleRoutingProblem.ProblemDomain;

import GE_HH.vehicleRoutingProblem.Utilities.*;
import GE_HH.vehicleRoutingProblem.components.Customer;
import GE_HH.vehicleRoutingProblem.components.Route;
import GE_HH.vehicleRoutingProblem.components.Vehicle;
import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPIndividual;
import ec.gp.GPProblem;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleProblemForm;
import ec.util.Parameter;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class VrpProblem extends GPProblem implements SimpleProblemForm {

    //file containing data
    // File filename = new File("E:\\School\\ResearchProject\\src\\GE_HH\\examTimetablingProblem\\datasets\\exam_comp_set81.exam");
    public static final String P_FILENAME = "vrp-filename";

    public Customer currentCustomer;
    public Route currentRoute;
    public Vehicle currentVehicle;
    private ProblemInstance problem =new ProblemInstance();
    private Solution initialSolution, clarkeWrightSolution;
    public static double GAIN_DELTA = 0.000_001;
    private CostMatrix costMatrix;
    private ClarkeWright cw;

    public void setup(final EvolutionState state,
                      final Parameter base) {

        // very important, remember this
        super.setup(state, base);

        // Get our filename
        File filename = state.parameters.getFile(base.push(P_FILENAME), null);
        if (filename == null)  //
            state.output.fatal("Filename must be provided", base.push(P_FILENAME));
        //get our timetable object
        // Get a Timetable object with all the available information.
        try {


            //load the datafile
            problem.loadChristofidesDataset(filename);

            //Create initial timetable using constructive heuristics
            cw= new ClarkeWright(problem);


            //get initial solution
            initialSolution=cw.generateSingleCustomerRoute();

            clarkeWrightSolution= cw.applyClarkeWrightHeuristic();


        } catch (Exception e) {
            state.output.fatal("Error in Timetable ProblemInstance setup, while loading from file " + filename +
                    "\nFrom parameter " + base.push(P_FILENAME) + "\nError:\n" + e);
        }
    }



    @Override
    public void evaluate(EvolutionState evolutionState, Individual individual, int i, int i1) {


        if (!individual.evaluated)  // don't bother reevaluating
        {

            VrpData input = (VrpData) (this.input);
            double fitness = 0.0;
            int hits = 0;


            Solution newSolution=new Solution(this.problem);
            List<Route> newRoute=new CopyOnWriteArrayList<>();
            List<Customer> newCustomers=new CopyOnWriteArrayList<>();

            for (int n = 0; n < clarkeWrightSolution.getRoutes().size(); n++)
            {
                for( int j=0; j<clarkeWrightSolution.getRoutes().get(n).size();j++)
                {
                    currentCustomer =clarkeWrightSolution.getRoutes().get(n).getCustomers().get(j);
                    currentRoute=clarkeWrightSolution.getRoutes().get(n);

                    ((GPIndividual) individual).trees[0].child.eval(
                            evolutionState, i1, input, stack, ((GPIndividual) individual), this);

                    //  newCustomers.add(currentCustomer);
                     // newRoute.add(currentRoute);
                    newSolution.getUniqueCustomer().add(currentCustomer);
                    newSolution.getRoutes().add(currentRoute);

                }
            }

            fitness=newSolution.getSolutionCost();

            // the fitness better be KozaFitness!
            KozaFitness f = ((KozaFitness) individual.fitness);
            f.setStandardizedFitness(evolutionState, fitness);
            f.hits = hits;
            individual.evaluated = true;

        }


        }


    public double calculateSwapGain(Route route, int i, int j) {
        int n1 = route.getCustomers().get(i - 1).getCustomerID();
        int n1Next = route.getCustomers().get(i).getCustomerID();
        int n2 = route.getCustomers().get(j).getCustomerID();
        int n2Next = route.getCustomers().get(j + 1).getCustomerID();

        double cost = costMatrix.getCost(n1, n1Next) + costMatrix.getCost(n2, n2Next) -
                costMatrix.getCost(n1, n2) - costMatrix.getCost(n1Next, n2Next);

        return cost > GAIN_DELTA ? cost : 0.;
    }

    public boolean twoOptImprovement(Route r) {

        double gain;
        double bestGain = 0;
        Pairs<Integer, Integer> bestSwap = new Pairs<>();

        for (int i = 1; i < r.size() - 2; i++) {
            for (int k = i + 1; k < r.size() - 1; k++) {    // for each customer pair (i,j)

                gain = calculateSwapGain(r, i, k);

                if (gain > bestGain) {
                    bestSwap.set(i, k);        // save indexes and swap at the end
                    bestGain = gain;            // update best gain
                }
            }
        }

        if (bestGain > 0) {
            r.swap(bestSwap.getFirst(), bestSwap.getSecond());
            return true;
        } else {
            return false;
        }


    }

    public boolean twoOptSwap(Solution solution) {

        boolean improv = false;

        for (Route v : solution.getRoutes()) {
            improv = improv || this.twoOptImprovement(v);
        }

        return improv;    // return true if some route were optimized
    }

    public double calculateMoveGain(Route routeA, Route routeB, int j, int i) {
        int n1 = routeB.getCustomers().get(i).getCustomerID();
        int n1Prev = routeB.getCustomers().get(i - 1).getCustomerID();
        int n1Next = routeB.getCustomers().get(i + 1).getCustomerID();
        int n2 = routeA.getCustomers().get(j).getCustomerID();
        int n2Next = routeA.getCustomers().get(j + 1).getCustomerID();

        // this variable is for the case in which removing n1 cause an invalid route 0 ==> 0 (that have infinite cost).
        // In this case, not consider the route
        double n1Prev_n1Next_cost = n1Prev == n1Next ? 0 : costMatrix.getCost(n1Prev, n1Next);

        // this variable is for the case in which n1 is moved in the route 0 ==> 0 (that have infinite cost).
        // In this case, not consider the route
        double n2_n2Next_cost = n2 == n2Next ? 0 : costMatrix.getCost(n2, n2Next);

        double cost = n2_n2Next_cost + costMatrix.getCost(n1Prev, n1) + costMatrix.getCost(n1, n1Next) -
                costMatrix.getCost(n2, n1) - costMatrix.getCost(n1, n2Next) - n1Prev_n1Next_cost;

        return cost > GAIN_DELTA ? cost : 0.;
    }

    private void move(Route routeA, Route routeB, int j, int i) {

        if (routeA == routeB)
            routeA.moveNode(i, j);
        else {
            routeA.getCustomers().add(j + 1, routeB.getCustomers().get(i));
            routeB.getCustomers().remove(i);
        }
    }

    private boolean relocateCustomer(List<Route> aList) {

        double gain;
        double bestGain = 0;

        Route bestSourceVehicle = null;
        Route bestDestVehicle = null;
        Pairs<Integer, Integer> bestRelocate = new Pairs<>();

        for (Route b : aList) {
            for (Route a : aList) {

                        double remainingCapacity = this.problem.getVehicleCapacity() - a.getTotalRouteDemand();

                for (int i = 1; i < b.size() - 1; i++) {    // iterate on customers (no depots!)

                    if (b.getTotalRouteDemand() < remainingCapacity) {    // customer i can fit into route a

                       for (int j = 0; j < a.size() - 1; j++) {

                            gain = calculateMoveGain(a, b, j, i);

                           if (gain > bestGain) {

                               bestSourceVehicle = b;
                               bestDestVehicle = a;
                               bestRelocate.set(i, j);        // save indexes and move at the end

                               bestGain = gain;            // update best gain
                            }
                        }
                    }
                }
            }
        }

        if (bestGain > 0 && bestDestVehicle != null && bestSourceVehicle != null) {        // move and return

            move(bestDestVehicle, bestSourceVehicle,
                    bestRelocate.getSecond(), bestRelocate.getFirst());
            return true;
        } else {

            return false;
        }
    }

    private boolean relocateCustomer (List<Route> aList, Route b) {

        double gain;
        double bestGain = 0;

        Route bestVehicle = null;
        Pairs<Integer,Integer> bestRelocate = new Pairs<>();


        for (Route a : aList) {

            double remainingCapacity = this.problem.getVehicleCapacity() - a.getTotalRouteDemand();

            for (int i=1; i< b.size()-1; i++) {	// iterate on customers (no depots!)

                if (b.getTotalRouteDemand()<remainingCapacity) {	// customer i can fit into route a
                    /*
                     * for each possible position in route a
                     * find a position that decrement the amount cost of the two routes
                     */
                    for (int j=0; j< a.size()-1; j++) {

                        gain = calculateMoveGain(a, b, j, i);

                        if (gain > bestGain) {

                            bestVehicle = a;
                            bestRelocate.set(i,j);		// save indexes and move at the end

                            bestGain = gain;			// update best gain
                        }
                    }
                }
            }
        }

        if (bestGain>0 && bestVehicle!=null) {		// move and return
            move(bestVehicle, b, bestRelocate.getSecond(), bestRelocate.getFirst());
            return true;
        }
        else {
              return false;
        }

    }


    public void addVoidRoute(Solution solution) {
        Route v = new Route(problem.getDepot(),problem.getDropTime());
        solution.getRoutes().add(v);
    }

    private void cleanSolution(Solution solution) {
        Iterator<Route> it = solution.getRoutes().iterator();

        while (it.hasNext()) {
            Route v = it.next();

            if (v.getTotalRouteDemand()==0) {
                it.remove();
            }
        }
    }

}