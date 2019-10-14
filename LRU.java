import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;

public class LRU extends FrameBuffer {
    private class LRUPage implements Comparable<LRUPage> {
        Integer time;
        int id;

        public LRUPage(int id, int time) {
            this.id = id;
            this.time = time;
        }

        public void use(int time) {
            this.time = time;
        }

        public int getId() {
            return id;
        }

        public Integer getTime() {
            return time;
        }

        public int compareTo(LRUPage other) {
            return time.compareTo(other.getTime());
        }

        public String toString() {
            return String.format("{id: %d, time: %d}", id, time);
        }
    }

    private ArrayList<LRUPage> lrubuffer;
    private int counter; // Counts time relativistically


    public LRU(int totalFrames) {
        super(totalFrames);
        lrubuffer = new ArrayList<>();
        counter = 0;
    }

    @Override
    public void replace(Integer newPage) {
        int replaceFrameNum;
        if (lrubuffer.size() == totalFrames) {
            replaceFrameNum = loadedPages.get(Collections.min(lrubuffer).getId());
            loadedPages.remove(buffer.get(replaceFrameNum));
            lrubuffer.set(replaceFrameNum, new LRUPage(newPage, counter));
        } else {
            replaceFrameNum = lrubuffer.size();
            lrubuffer.add(new LRUPage(newPage, counter));
        }
        buffer.set(replaceFrameNum, newPage);
        loadedPages.put(buffer.get(replaceFrameNum), replaceFrameNum);
    }

    @Override
    public void use(int page) {
        counter++;
        lrubuffer.get(loadedPages.get(page)).use(counter);
    }
}
