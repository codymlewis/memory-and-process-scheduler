import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;

/**
 * Scheduler - COMP2240A3
 * The round robin Scheduler
 *
 * @author Cody Lewis (c3283349)
 */

public class Scheduler {
    private class ScheduledProcess implements Comparable<ScheduledProcess> {
        private Process process;
        private int slicePoint;
        private int turnaroundTime;

        public ScheduledProcess(Process process, int slicePoint) {
            this.process = process;
            this.slicePoint = slicePoint;
            turnaroundTime = 0;
        }

        public Process getProcess() {
            return process;
        }

        public void setSlice(int slicePoint) {
            this.slicePoint = slicePoint;
        }

        public int getCurSlice() {
            return slicePoint;
        }

        public void decrementSlice() {
            slicePoint--;
        }

        public void finish(int time) {
            turnaroundTime = time;
        }

        public String summary() {
            return String.format(
                "%d\t%s\t%d\t%d\t%s",
                process.getPid(),
                process.getName(),
                turnaroundTime,
                process.getNumberFaults(),
                process.getFaultTimes()
            );
        }

        @Override
        public int compareTo(ScheduledProcess other) {
            return process.getPid().compareTo(other.getProcess().getPid());
        }

        public String toString() {
            return String.format("{process: %s, slice: %d}", process.toString(), slicePoint);
        }
    }

    private LinkedList<ScheduledProcess> processes;
    private int quantum;
    private int time;
    private LinkedList<ScheduledProcess> finishedProcesses;

    public Scheduler(LinkedList<Process> processes, int quantum) {
        this.processes = new LinkedList<>();
        finishedProcesses = new LinkedList<>();
        processes.stream().forEach(p -> this.processes.add(new ScheduledProcess(p, quantum)));
        this.quantum = quantum;
        time = 0;
    }

    public void run() {
        while (!processes.isEmpty()) {
            Iterator<ScheduledProcess> it = processes.iterator();
            ScheduledProcess current = null;
            while (it.hasNext()) {
                current = it.next();
                if (current.getProcess().isReady(time)) {
                    break;
                } else {
                    current = null;
                }
            }
            if (current == null) {
                time++;
            } else {
                while (!current.getProcess().isFinished() &&
                        current.getProcess().isReady(time) &&
                        current.getCurSlice() > 0) {
                    current.getProcess().run();
                    time++;
                    current.decrementSlice();
                }
                if (current.getProcess().isFinished() ||
                        current.getProcess().isReady(time) ||
                        current.getCurSlice() == 0) {
                    it.remove();
                    if (!current.getProcess().isFinished()) {
                        current.setSlice(quantum);
                        processes.add(current);
                    } else {
                        current.finish(time);
                        finishedProcesses.add(current);
                    }
                }
            }
        }
    }

    public String summary() {
        String result = "PID\tProcess Name\tTurnaround Time\t# Faults\tFault Times\n";
        Collections.sort(finishedProcesses);
        for (ScheduledProcess finishedProcess : finishedProcesses) {
            result += finishedProcess.summary() + "\n";
        }
        return result;
    }
}
