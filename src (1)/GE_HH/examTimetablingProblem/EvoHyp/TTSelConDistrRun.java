/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GE_HH.examTimetablingProblem.EvoHyp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Oct 11, 2016
 * @author Derrick Beckedahl
 *
 * Edited by George Mweshi Nov 28, 2018
 * 
 * This program runs a Selection-Constructive Hyper-Heuristic for the Examination timetabling Problem.
 */
public class TTSelConDistrRun {
    /**
     * @param args the command line arguments
     *      
     *      args[0] -&gt; file containing list of input files
     *      args[1] -&gt; file containing parameters
     *      args[2] -&gt; number of runs to perform for each file
     *      args[3] -&gt; number of cores/threads to use for the runs
     *      args[4] -&gt; number of cores/threads to use for EvoHyp
     *      args[5] -&gt; parameter to determine whether or not to print after each generation (primarily for parameter tuning)
     *                      0 -&gt; don't output after each generation
     *                      1 -&gt; output after each generation
     *      args[6] -&gt; parameter to determine whether or not to use hill-climbing
     *                      0 -&gt; don't use hill-climbing
     *                      1 -&gt; use hill-climbing (if using hill-climbing number of attempts can be specified with args[7])
     *      args[7] -&gt; (optional) number of iterations when using hill-climbing (defaults to 1 if specified value &lt; 1)
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // Output usage of the program if no command-line arguments are passed
        if (!((args.length == 7) || (args.length == 8))) {
            System.out.println();
            System.out.println("This program runs a Selection-Constructive Hyper-Heuristic for the Exam Timetabling problem (ETP).");
            System.out.println("The command line arguments are as follows:\n");
            System.out.println("\targs[0] -> file containing list of input files");
            System.out.println("\targs[1] -> file containing parameters");
            System.out.println("\targs[2] -> number of runs to perform for each file");
            System.out.println("\targs[3] -> number of cores/threads to use for the runs");
            System.out.println("\targs[4] -> number of cores/threads to use for EvoHyp");
            System.out.println("\targs[5] -> parameter to determine whether or not to print after each generation (primarily for parameter tuning)");
            System.out.println("\t\t\t\u00BB 0 -> don't output after each generation");
            System.out.println("\t\t\t\u00BB 1 -> output after each generation");
            System.out.println("\targs[6] -> parameter to determine whether or not to use hill-climbing");
            System.out.println("\t\t\t\u00BB 0 -> don't use hill-climbing");
            System.out.println("\t\t\t\u00BB 1 -> use hill-climbing (if using hill-climbing number of attempts can be specified with args[7])");
            System.out.println("\targs[7] -> (optional) number of iterations when using hill-climbing (defaults to 1 if specified value < 1)");
            System.out.println();
            System.exit(0);
        }

        boolean print = false;

        switch (Integer.parseInt(args[5])) {
            case 0:
                print = false;
                break;
            case 1:
                print = true;
                break;
            default:
                System.out.println("Error with specified generation output parameter value.  Run this program without arguments for more information.");
                System.out.println("Generation output parameter value given:\t" + Integer.parseInt(args[5]));
                System.exit(1);
                break;
        }

        boolean useHC = false;

        switch (Integer.parseInt(args[6])) {
            case 0:
                useHC = false;
                break;
            case 1:
                useHC = true;
                break;
            default:
                System.out.println("Error with specified hill-climbing use parameter value.  Run this program without arguments for more information.");
                System.out.println("Hill-Climbing use parameter value given:\t" + Integer.parseInt(args[6]));
                System.exit(1);
                break;
        }

        int runs = Integer.parseInt(args[2]);
        int cores = Integer.parseInt(args[3]);
        int evoCores = Integer.parseInt(args[4]);

        int hillClimbLim = 1;
        if (args.length == 8) {
            hillClimbLim = Integer.parseInt(args[7]);
        }
        if (hillClimbLim < 1) {
            hillClimbLim = 1;
        }

        FileReader fr = new FileReader(args[0]);
        String str = "";
        while (fr.ready()) {
            str += (char) (fr.read());
        }
        fr.close();

        String[] strArr = (str.trim()).split("\n");
        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = strArr[i].trim();
        }

        Object Results[][] = new Object[strArr.length][runs];

        //Run all the threads simultaneously on the cores and store the output from
        //each run in the Results array.
        try {
            /*
             * Distribute the algorithm over multiple cores (Each run is performed on a separate core)
             */
            ExecutorService es = Executors.newFixedThreadPool(cores);

            for (int files = 0; files < strArr.length; files++) {
                for (int r = 0; r < runs; r++) {
                    Results[files][r] = es.submit(new TTEvoHypSelConDistr(evoCores, strArr[files], args[1], print, useHC, hillClimbLim));
                }
            }

            es.shutdown();

            // Initialise Default Values
            Output[] bestRun = new Output[strArr.length];
            double[] avgObjVal = new double[strArr.length];
            double[] objValStdDev = new double[strArr.length];
            double[] avgFit = new double[strArr.length];
            double[] fitStdDev = new double[strArr.length];
            for (int i = 0; i < bestRun.length; i++) {
                bestRun[i] = null;
                avgObjVal[i] = 0.0;
                objValStdDev[i] = 0.0;
                avgFit[i] = 0.0;
                fitStdDev[i] = 0.0;
            }

            /*
             * Determine the best individual across all runs, according to objective value. In the case of ties in the objective
             * value, the best is then determined according to the fitness value. In the case where both the objective value and
             * the fitness value are tied, the more recent run is taken to be the best.
             */
            for (int files = 0; files < strArr.length; files++) {
                Output[] allRuns = new Output[runs];

                for (int r = 0; r < runs; r++) {
                    Future<Output> f = (Future<Output>) Results[files][r];
                    Output tmp = f.get();
                    allRuns[r] = tmp;
                    double objVal = tmp.getObjVal();
                    double fitVal = tmp.getFitness();

                    avgObjVal[files] += objVal;
                    avgFit[files] += fitVal;

                    if (r == 0) {
                        bestRun[files] = tmp;
                    } else if (objVal < bestRun[files].getObjVal()) {
                        bestRun[files] = tmp;
                    } else if (objVal == bestRun[files].getObjVal()) {
                        if (fitVal <= (bestRun[files].getFitness())) {
                            bestRun[files] = tmp;
                        }
                    }
                }

                /*
                 * Calculate the average values and the standard deviation
                 */
                avgObjVal[files] /= runs;
                avgFit[files] /= runs;
                for (int r = 0; r < runs; r++) {
                    objValStdDev[files] += Math.pow((allRuns[r].getObjVal() - avgObjVal[files]), 2.0);
                    fitStdDev[files] += Math.pow((allRuns[r].getFitness() - avgFit[files]), 2.0);
                }
                objValStdDev[files] /= runs;
                objValStdDev[files] = Math.sqrt(objValStdDev[files]);
                fitStdDev[files] /= runs;
                fitStdDev[files] = Math.sqrt(fitStdDev[files]);
            }

            /*
             * Calculate the average values and the standard deviation
             */
            System.out.println("\n###############################\n");

            String pm = "+/-";

            for (int files = 0; files < strArr.length; files++) {
                if (files != 0) {
                    System.out.println("\t-----\n");
                }
                System.out.println("File:\t\t" + strArr[files].substring(strArr[files].lastIndexOf("/") + 1, strArr[files].lastIndexOf(".")));
                System.out.println("Average Objective Value:\t\t" + avgObjVal[files] + "\t" + pm + "\t" + objValStdDev[files]);
                System.out.println("Best Objective Value:\t\t\t" + bestRun[files].getObjVal());
                System.out.println("Average Fitness:\t\t\t" + avgFit[files] + "\t" + pm + "\t" + fitStdDev[files]);
                System.out.println("Best Fitness:\t\t\t" + bestRun[files].getFitness());
                System.out.println("Best Heuristic Combination:\t\t" + bestRun[files].getProg());
                System.out.println("Random Generator Seed (for best):\t" + bestRun[files].getSeed());
                System.out.println();
            }

        } catch (Exception e) {
            System.out.println("Problem");
            System.out.println("Exception:\t" + e);
            System.out.println("Cause:\t\t" + e.getCause());
            System.out.println("Stack Trace:");
            e.printStackTrace();
        }
    }
}
