package GE_HH.vehicleRoutingProblem.Utilities;

import GE_HH.vehicleRoutingProblem.components.Customer;
import GE_HH.vehicleRoutingProblem.components.Route;
import GE_HH.vehicleRoutingProblem.components.Vehicle;
import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClarkeWright {

    private ProblemInstance problem;
    public List<Route> joinedRoutes = new CopyOnWriteArrayList<>();
    public List<RouteSavings> RouteSavings = new CopyOnWriteArrayList<>();


    public ClarkeWright(ProblemInstance prob)
    {
       this.problem=prob;
    }


    public Solution applyClarkeWrightHeuristic(){

        Solution tempSolution;
        tempSolution =generateSingleCustomerRoute();
        List<RouteSavings> rSavings = findAllPairs(tempSolution);
        while(rSavings.size() > 0){
            for(int i = 0; i < rSavings.size();i++){
                RouteSavings saving = rSavings.get(i);
                Route a = saving.getR1();
                Route b = saving.getR2();
                if(!joinedRoutes.contains(a) && !joinedRoutes.contains(b)){
                    joinTwoRoutes(a, b,tempSolution);
                    break;
                }
                else if(!joinedRoutes.contains(a)){
                    for(Route r: tempSolution.getRoutes()){
                        if(r.getCurrentCustomer() == b.getCurrentCustomer()) joinTwoRoutes(a, r,tempSolution);
                        break;
                    }
                }
                else if(!joinedRoutes.contains(b)){
                    for(Route r: tempSolution.getRoutes()){
                        if(r.getCurrentCustomer() == a.getCurrentCustomer()) joinTwoRoutes(r, b,tempSolution);
                        break;
                    }
                }
            }
            joinedRoutes.clear();
            RouteSavings.clear();
            findAllPairs(tempSolution);
        }

      //  randomizeCustomers(tempSolution);
//        Validate.isTrue(tempSolution.isValid(), "the solution is invalid");

        return tempSolution;
    }

  /*  public void randomizeCustomers(Solution sol)
    {
        for(Route r: sol.getRoutes()){
            List<Customer> best = r.getCustomers();
            ArrayList<Customer> mix = new ArrayList<>();
            mix.addAll(best);
            Route ok = new Route(this.problem.getDepot());
            for(int i = 0; i <10000; i++) {
                mix.sort(new RandomSort());
                ok.setCustomers(mix);
                if (ok.getRouteCost(problem.getProblemInstnceCostMatrix()) < r.getRouteCost(problem.getProblemInstnceCostMatrix())) {
                    List<Customer> t = new ArrayList<>();
                    for (Customer c : mix) t.add(c);
                    r.setCustomers(t);
                }
            }
        }
    }
    */

    private void joinTwoRoutes(Route a, Route b, Solution sol){
        if(verifyJoin(a, b)){

            if(a.getCustomers().get(a.getCustomers().size()-1).getCustomerID()==0 && b.getCustomers().get(0).getCustomerID()==0)
            {
                a.removeFromRoute(a.getCustomers().size()-1);
                b.removeFromRoute(0);
            }
          //   System.out.println("a :"+a);
          //  System.out.println("b :"+b);
            a.addRoute(b);
            joinedRoutes.add(a);
            joinedRoutes.add(b);
            sol.getRoutes().remove(b);

        }
    }


    private boolean verifyJoin(Route r1, Route r2) {
        Boolean result = true;
        if(r1.getRouteDuration()> this.problem.getMaxRouteTime())
            return false;
        if(r2.getRouteDuration()> this.problem.getMaxRouteTime())
            return false;

        int total = 0;
        for(Customer c: r1.getCustomers()) total += c.getCustomerDemand();
        for(Customer c: r2.getCustomers()) total += c.getCustomerDemand();
        if (total > this.problem.getVehicleCapacity()) {
            result = false;
        }


        return result;
    }


    public double calculatePairSavings(Route a, Route b){
        Customer cus1 = a.getCurrentCustomer();
        Customer cus2 = b.getCurrentCustomer();
        double bridge = this.problem.getProblemInstnceCostMatrix().getCost(cus1.getCustomerID(),cus2.getCustomerID());
        double sav1 = this.problem.getProblemInstnceCostMatrix().getCost(cus1.getCustomerID(),this.problem.getDepot().getCustomerID());
        double sav2 = this.problem.getProblemInstnceCostMatrix().getCost(cus2.getCustomerID(),this.problem.getDepot().getCustomerID());
        return sav1 + sav2 - bridge;
    }

    public List<RouteSavings>  findAllPairs(Solution sol){
        for(int j = 0; j < sol.getRoutes().size(); j++){
            for( int i = j + 1; i < sol.getRoutes().size(); i++ ){
                Route a = sol.getRoutes().get(j);
                Route b = sol.getRoutes().get(i);
                double sav = calculatePairSavings(a, b);
                double sav2 = calculatePairSavings(b, a);
                if(sav > sav2){
                    if(sav > 1 && verifyJoin(a, b)) RouteSavings.add( new RouteSavings(sav, a, b) );
                }
                else if(sav2 > 1 && verifyJoin(b, a)) RouteSavings.add( new RouteSavings(sav, b, a) );
            }
        }
         RouteSavings.sort(new RouteSavingsSorter());
        return RouteSavings;
    }

      public Solution generateSingleCustomerRoute(){
        Solution tempSolution=new Solution(this.problem);
         for(Customer c:this.problem.getCustomers()){
            Route route=new Route(this.problem.getDepot(),this.problem.getDropTime());
            route.addCustomer(c);
            route.addCustomer(this.problem.getDepot());
            tempSolution.getRoutes().add(route);
        }
         Validate.isTrue(tempSolution.isValid(), "the given solution is invalid");
        // System.out.println(" routes :"+tempSolution.getRoutes());


        return tempSolution;
    }


}
