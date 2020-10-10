package http.two.upload;

public class Chunk {
    int start;
    int end;
    int chunkSize;

    public Chunk() {}

    public Chunk(int start, int end, int chunkSize) {
        this.start = start;
        this.end = end;
        this.chunkSize = chunkSize;
    }

    public int getRemaining() {
        return end - start;
    }
}
