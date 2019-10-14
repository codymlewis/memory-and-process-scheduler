import java.util.ArrayList;

/**
 * Clock - COMP2240A3
 * Clock memory management policy
 *
 * @author Cody Lewis (c3283349)
 */

public class Clock extends FrameBuffer {
    private ArrayList<Boolean> useFlags;
    private int current;

    /**
     * Input constructor
     *
     * @param totalFrames Total amount of frames that can be used for the process
     */
    public Clock(int totalFrames) {
        super(totalFrames);
        useFlags = new ArrayList<>(totalFrames);
        for (int i = 0; i < totalFrames; ++i) {
            useFlags.add(false);
        }
        current = 0;
    }

    /**
     * Replace a page according to the policy
     *
     * @param newPage The page the needs to be loaded
     */
    @Override
    public void replace(Integer newPage) {
        while (useFlags.get(current)) {
            useFlags.set(current, false);
            incrementCurrent();
        }
        loadedPages.remove(buffer.get(current));
        buffer.set(current, newPage);
        loadedPages.put(buffer.get(current), current);
        useFlags.set(current, true);
        incrementCurrent();
    }

    /**
     * Increment the frame that that this current points to
     */
    private void incrementCurrent() {
        current = (current + 1) % totalFrames;
    }
}
