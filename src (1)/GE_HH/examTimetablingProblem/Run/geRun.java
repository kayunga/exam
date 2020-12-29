package GE_HH.examTimetablingProblem.Run;

import ec.Evolve;

public class geRun {


    public static void main(String[] args) throws Exception {
System.out.println("test");
            String pathToFiles = "grammar/";
            int numberOfJobs = 5;

          // String statisticType = "ec.gp.koza.KozaShortStatistics";
            String[] runConfig = new String[] {
                    Evolve.A_FILE, "grammar/tt.params",
                    "-p", ("stat.gather-full="+true),
                    "-p", ("stat.file=$"+pathToFiles+"out.stat"),
                    "-p", ("jobs="+numberOfJobs)
            };
            Evolve.main(runConfig);






    }



}
