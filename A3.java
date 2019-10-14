import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;

public class A3 {
    public static void main(String args[]) {
        if (args.length < 3) {
            System.err.println("Error: Not enough arguments supplied.");
            System.err.println("Usage: java A3 <F> <Q> <INPUT_FILES>");
            System.exit(1);
        }
        try {
            A3 runner = new A3();
            System.exit(
                runner.run(
                    Integer.parseInt(args[0]),
                    Integer.parseInt(args[1]),
                    new ArrayList<String>(Arrays.asList(Arrays.copyOfRange(args, 2, args.length)))
                )
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int run(int frames, int quantum, ArrayList<String> processFiles) throws Exception {
        int framesPerProcess = (int) Math.floor(frames / processFiles.size());
        runWithPolicy(framesPerProcess, quantum, processFiles, "LRU");
        System.out.println("------------------------------------------------------------");
        System.out.println();
        runWithPolicy(framesPerProcess, quantum, processFiles, "Clock");
        return 0;
    }

    public void runWithPolicy(int framesPerProcess, int quantum,
            ArrayList<String> processFiles, String fbname) throws Exception {
        LinkedList<Process> processes = new LinkedList<Process>();
        for(int i = 0; i < processFiles.size(); ++i) {
            processes.add(new Process(i + 1, processFiles.get(i), framesPerProcess, fbname));
        }
        Scheduler scheduler = new Scheduler(processes, quantum);
        scheduler.run();
        System.out.format("%s - Fixed\n", fbname);
        System.out.println(scheduler.summary());
    }
}
