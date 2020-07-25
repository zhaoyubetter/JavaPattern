package http.two;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

/**
 * by better
 */
public final class BufferedChannelReader {
    private static int defaultSize = 4096;
    private ByteBuffer byteBuffer;
    private ReadableByteChannel channel;

    public void test() {
    }

    public BufferedChannelReader(ReadableByteChannel channel) {
        this(channel, defaultSize, null);
    }

    public BufferedChannelReader(ReadableByteChannel channel, int bufferSize) {
        this(channel, bufferSize, null);
    }

    public BufferedChannelReader(ReadableByteChannel channel, int bufferSize, byte[] remainByte) {
        this.channel = channel;
        byteBuffer = ByteBuffer.allocate(bufferSize);
        if (remainByte != null && remainByte.length > bufferSize) {
            byteBuffer = ByteBuffer.allocate(remainByte.length);
            byteBuffer.put(remainByte);
        }
        // default set buffer is full
        byteBuffer.position(byteBuffer.limit());
    }

    /**
     * read a byte
     *
     * @throws IOException
     */
    public byte read() throws IOException {
        ensureOpen();
        // has already read complete.
        if (!byteBuffer.hasRemaining()) {
            byteBuffer.clear();
            channel.read(byteBuffer);
            byteBuffer.flip();
        }
        return byteBuffer.get();
    }

    public String readLine() throws IOException {
        ensureOpen();
        StringBuilder remainStr = new StringBuilder();
        String line;
        if (!byteBuffer.hasRemaining()) {
            byteBuffer.clear();
            channel.read(byteBuffer);
            byteBuffer.flip();
        }

        while ((byteBuffer.hasRemaining()) && null != (line = readByteBuffer(byteBuffer))) {
            if ('\n' == line.charAt(line.length() - 1)) {
                // End with line feeds, just return the line.
                if(remainStr.length() > 0) {
                    return remainStr.toString() + line;
                } else {
                    return line;
                }
            }
            // Line not end with line feeds, maybe the byteBuffer is all read out.
            if (!byteBuffer.hasRemaining()) {
                // new line must contains remainStr
                remainStr.append(line);
                byteBuffer.clear();
                // Continue to read
                if(channel.read(byteBuffer) < 0) {
                    // if channel has not byte, then return remainStr
                    // set buffer is full, as init's status
                    byteBuffer.position(byteBuffer.limit());
                    return remainStr.toString();
                }
                byteBuffer.flip();
            }
        }


        return null;
    }

    private String readByteBuffer(ByteBuffer byteBuffer) throws EOFException {
        StringBuilder sb = new StringBuilder(80);
        while (byteBuffer.hasRemaining()) {
            char c = (char) byteBuffer.get();
            sb.append(c);
            if (c == -1) {
                throw new EOFException();
            } else if (c == '\n') {
                break;
            }
        }
        return sb.toString();
    }

    private void ensureOpen() throws IOException {
        if (channel == null)
            throw new IOException("Stream closed");
    }
}
