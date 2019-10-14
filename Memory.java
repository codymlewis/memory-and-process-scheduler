import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Memory {
    private class Page {
        int id;

        public Page(int id) {
            this.id = id;
        }
    }

    ArrayList<Page> frames;
    int maxFrames;
    Map<Integer, Integer> pageMap;
    FrameBuffer fbuffer;

    public Memory(int numberFrames) {
        maxFrames = numberFrames;
        frames = new ArrayList<>(numberFrames);
        pageMap = new HashMap<>();
    }
}
