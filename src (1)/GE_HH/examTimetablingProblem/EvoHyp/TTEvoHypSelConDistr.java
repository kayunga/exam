/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GE_HH.examTimetablingProblem.EvoHyp;

import GE_HH.examTimetablingProblem.Utilities.UtilityFunctions;
import distrgenalg.*;

import java.io.File;
import java.util.concurrent.Callable;


public class TTEvoHypSelConDistr implements Callable {
    /**
     * If the number of cores for the GA is not specified, the value defaults to 1
     */
    private long seed;
    private String fName;
    private UtilityFunctions ds;
    private ETP etp;
    private int evalLim;
    private int hillClimbLim;
    private int cores;
    private String paramFile;
    private boolean print;
    private boolean useHC;
    
    TTEvoHypSelConDistr(String  fName, String paramFile) throws Exception {
        seed = System.nanoTime();
        this.cores = 1;
        this.fName = fName;
        this.ds = new UtilityFunctions();
        this.ds.loadfile( new File(this.fName));
        this.etp = new ETP(this.fName, -1);
        this.evalLim = -1;
        this.hillClimbLim = 1;
        this.paramFile = paramFile;
        this.print = true;
    }
    
    TTEvoHypSelConDistr(String fName, String paramFile, int evalLim) throws Exception {
        seed = System.nanoTime();
        this.cores = 1;
        this.fName = fName;
        this.ds = new UtilityFunctions();
        this.ds.loadfile( new File(this.fName));
        this.evalLim = evalLim;
        this.hillClimbLim = 1;
        this.etp = new ETP(this.fName, -1);
        this.paramFile = paramFile;
        this.print = true;
    }
    
    TTEvoHypSelConDistr(int cores, String fName, String paramFile) throws Exception {
        seed = System.nanoTime();
        this.cores = cores;
        this.fName = fName;
        this.ds = new UtilityFunctions();
        this.ds.loadfile( new File(this.fName));
        this.etp = new ETP(this.fName, -1);
        this.evalLim = -1;
        this.hillClimbLim = 1;
        this.paramFile = paramFile;
        this.print = true;
    }
    
    TTEvoHypSelConDistr(int cores, String fName, String paramFile, int evalLim) throws Exception {
        seed = System.nanoTime();
        this.cores = cores;
        this.fName = fName;
        this.ds = new UtilityFunctions();
        this.ds.loadfile( new File(this.fName));
        this.evalLim = evalLim;
        this.hillClimbLim = 1;
        this.etp = new ETP(this.fName, this.evalLim);
        this.paramFile = paramFile;
        this.print = true;
    }
    
    TTEvoHypSelConDistr(String fName, String paramFile, boolean print) throws Exception {
        seed = System.nanoTime();
        this.cores = 1;
        this.fName = fName;
        this.ds = new UtilityFunctions();
        this.ds.loadfile( new File(this.fName));
        this.etp = new ETP(this.fName, -1);
        this.evalLim = -1;
        this.hillClimbLim = 1;
        this.paramFile = paramFile;
        this.print = print;
    }
    
    TTEvoHypSelConDistr(String fName, String paramFile, boolean print, int evalLim) throws Exception {
        seed = System.nanoTime();
        this.cores = 1;
        this.fName = fName;
        this.ds = new UtilityFunctions();
        this.ds.loadfile( new File(this.fName));
        this.evalLim = evalLim;
        this.hillClimbLim = 1;
        this.etp = new ETP(this.fName, this.evalLim);
        this.paramFile = paramFile;
        this.print = print;
    }
    
    TTEvoHypSelConDistr(int cores, String fName, String paramFile, boolean print) throws Exception {
        seed = System.nanoTime();
        this.cores = cores;
        this.fName = fName;
        this.ds = new UtilityFunctions();
        this.ds.loadfile( new File(this.fName));
        this.etp = new ETP(this.fName, -1);
        this.evalLim = -1;
        this.hillClimbLim = 1;
        this.paramFile = paramFile;
        this.print = print;
    }
    
    TTEvoHypSelConDistr(int cores, String fName, String paramFile, boolean print, int hillClimbLim) throws Exception {
        seed = System.nanoTime();
        this.cores = cores;
        this.fName = fName;
        this.ds = new UtilityFunctions();
        this.ds.loadfile( new File(this.fName));
        this.evalLim = -1;
        this.hillClimbLim = hillClimbLim;
        this.etp = new ETP(this.fName, this.evalLim);
        this.paramFile = paramFile;
        this.print = print;
    }
    
    TTEvoHypSelConDistr(int cores, String fName, String paramFile, boolean print, boolean useHC, int hillClimbLim) throws Exception {
        seed = System.nanoTime();
        this.cores = cores;
        this.fName = fName;
        this.ds = new UtilityFunctions();
        this.ds.loadfile( new File(this.fName));
        this.evalLim = -1;
        this.hillClimbLim = hillClimbLim;
        this.etp= new ETP(this.fName, this.evalLim);
        this.paramFile = paramFile;
        this.print = print;
        this.useHC = useHC;
    }
    
    @Override
    public Output call() throws Exception {
        int len = this.ds.getExams().size();
        Output res = new Output();
        res.setDatName(this.fName.toString());
        res.setSeed(this.seed);
        res.setUseHC(this.useHC);
        
        /*
        0 -> Next-Fit Heuristic
        1 -> Best-Fit Heuristic
        
        N/n -> Next-Fit Heuristic
        B/b -> Best-Fit Heuristic
        F/f -> First-Fit Heuristic
        W/w -> Worst-Fit Heuristic
        */
        DistrGenAlg ga = new DistrGenAlg(this.seed, "LEWS", this.cores);
        ga.setParameters(this.paramFile);
        ga.setNoOfCores(this.cores);
        ga.setPrint(this.print);
        ga.setProblem(this.etp);
        ga.setHillClimbAttempts(this.hillClimbLim);
        ga.setUseHillClimbing(this.useHC);
        
        TTProblemInstance is = (TTProblemInstance) ga.evolve();
        
        res.setObjVal(is.getFitness());
        res.setFitness(is.objValue());
        
        String heus = is.getHeuCom();
        
        if (heus.length() > len) {
            res.setProg(heus.substring(0, len));
        } else if (heus.length() == len) {
            res.setProg(is.getHeuCom());
        } else {
            int reps = (int) Math.ceil((1.0 * len) / heus.length());
            String tmp = "";
            for (int i = 0; i < reps; i++) {
                tmp += heus;
            }
            res.setProg(tmp.substring(0, len));
        }
        
//        if ( (is.getHeuCom()).length() <= len ) {
//            res.setProg(is.getHeuCom());
//        } else {
//            res.setProg( (is.getHeuCom()).substring(0, len) );
//        } 
       
        return res;
    }
    
}
