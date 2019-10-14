import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Process - COMP2240A3
 * A process class
 *
 * @author Cody Lewis (c3283349)
 */

public class Process {
    // The Integer specifies the page request
    private Queue<Integer> instructions;
    private String name;
    private int pid;
    private FrameBuffer fbuffer;
    private int timeBlockedTo;
    private LinkedList<Integer> faultTimes;

    public Process(int pid, String filename, int maxFrames, String fbname) throws FileNotFoundException {
        name = filename;
        this.pid = pid;
        faultTimes = new LinkedList<>();
        instructions = readInstructionFile(filename);
        fbuffer = FBFactory(fbname, maxFrames);
        timeBlockedTo = -1;
    }

    private Queue<Integer> readInstructionFile(String filename) throws FileNotFoundException {
        Scanner fstream = new Scanner(new File(filename));
        Queue<Integer> result = new LinkedList<Integer>();
        // TODO: Add some errors for incorrect file format
        String line = fstream.nextLine();
        while (!(line = fstream.nextLine()).equals("end")) {
            result.offer(Integer.parseInt(line));
        }
        return result;
    }

    private FrameBuffer FBFactory(String fbname, int maxFrames) {
        switch(fbname) {
            case "Clock":
                return new Clock(maxFrames);
            default:
                return new LRU(maxFrames);
        }
    }

    public boolean isReady(int time) {
        int nextInstruction = instructions.peek();
        if (!fbuffer.isLoaded(nextInstruction)) {
            fbuffer.replace(nextInstruction);
            timeBlockedTo = time + 5;
            faultTimes.add(time);
        }
        return timeBlockedTo < time;
    }

    public int run() {
        int page = instructions.poll();
        fbuffer.use(page);
        return 1;
    }

    public boolean isFinished() {
        return instructions.isEmpty();
    }

    public String getName() {
        return name;
    }

    public Integer getPid() {
        return pid;
    }

    public int getNumberFaults() {
        return faultTimes.size();
    }

    public String getFaultTimes() {
        return faultTimes.toString().replace("[", "{").replace("]", "}");
    }

    public String toString() {
        return String.format("{pid: %d, instructions_left: %d}", pid, instructions.size());
    }
}
