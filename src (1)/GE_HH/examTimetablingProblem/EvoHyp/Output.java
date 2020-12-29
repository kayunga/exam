/*
 * Defines the output of the class which will store the output for each run.This 
 * could be the fitness and chromosome, etc.  Essentially it is all the results/
 * information that you want to get from a run.
 */
package GE_HH.examTimetablingProblem.EvoHyp;

/**
 *
 * Nelishia Pillay 6 April 2016
 */
 
public class Output {

    //Data elements
    private double objVal;         // Stores the objective value of the problem
    private double fitness;     // Stores the fitness value of the problem
    private String prog;        // Stores the heuristic combination
    private long seed;          // Store the seed of a run
    private String datName;     // Store the data file of a run
    private boolean useHC;        // Store the evaluation iteration limit

    // Set Methods
    public void setUseHC(boolean useHC) {
        this.useHC = useHC;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public void setDatName(String datName) {
        this.datName = datName;
    }

    public void setObjVal(double objVal) {
        this.objVal = objVal;
    }

    public void setProg(String prog) {
        this.prog = prog;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    // Get Methods
    public boolean getUseHC() {
        return this.useHC;
    }

    public String getDatName() {
        return this.datName;
    }

    public long getSeed() {
        return this.seed;
    }

    public double getObjVal() {
        return this.objVal;
    }

    public String getProg() {
        return prog;
    }

    public double getFitness() {
        return this.fitness;
    }
}
