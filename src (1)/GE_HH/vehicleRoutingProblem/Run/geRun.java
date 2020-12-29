package GE_HH.vehicleRoutingProblem.Run;

import ec.Evolve;

public class geRun {


    public static void main(String[] args) throws Exception {

            String pathToFiles = "C:/Users/George/IdeaProjects/ResearchProject/src/GE_HH/vehicleRoutingProblem/Results/";
            int numberOfJobs = 2;

          // String statisticType = "ec.gp.koza.KozaShortStatistics";
            String[] runConfig = new String[] {
                    Evolve.A_FILE, "src/GE_HH/vehicleRoutingProblem/grammar/rp.params",
// "-p", ("stat="+statisticType),
                    "-p", ("stat.file=$"+pathToFiles+"out.stat"),
                    "-p", ("jobs="+numberOfJobs)
            };
            Evolve.main(runConfig);






    }



}
