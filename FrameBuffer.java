import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

/**
 * FrameBuffer - COMP2240A3
 * Abstract implementation of a buffer of frames for a process.
 *
 * @author Cody Lewis (c3283349)
 */

public abstract class FrameBuffer {
    protected Map<Integer, Integer> loadedPages;
    protected int totalFrames;
    protected ArrayList<Integer> buffer;

    /**
     * Input constructor
     *
     * @param totalFrames Total amount of frames that can be used for the process
     */
    public FrameBuffer(int totalFrames) {
        loadedPages = new HashMap<>();
        this.totalFrames = totalFrames;
        buffer = new ArrayList<>(totalFrames);
        for (int i = 0; i < totalFrames; ++i) {
            buffer.add(0);
        }
    }

    /**
     * Replace a page according to the policy
     *
     * @param newPage The page the needs to be loaded
     */
    public abstract void replace(Integer newPage);

    /**
     * Use a page
     */
    public void use(int page) {}

    /**
     * Check whether a page is loaded
     */
    public boolean isLoaded(Integer pageid) {
        return loadedPages.containsKey(pageid);
    }
}
