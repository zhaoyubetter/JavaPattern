package http.two;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * by better
 */
public final class BufferedChannelReader {
    private static int defaultSize = 512;
    private ByteBuffer byteBuffer;
    private SocketChannel channel;

    public void test() {
    }

    public BufferedChannelReader(SocketChannel channel) {
        this(channel, defaultSize, null);
    }

    public BufferedChannelReader(SocketChannel channel, int bufferSize) {
        this(channel, bufferSize, null);
    }

    public BufferedChannelReader(SocketChannel channel, int bufferSize, byte[] remainByte) {
        this.channel = channel;
        byteBuffer = ByteBuffer.allocate(bufferSize);
        if (remainByte != null && remainByte.length > bufferSize) {
            byteBuffer = ByteBuffer.allocate(remainByte.length);
            byteBuffer.put(remainByte);
        }
        // default set buffer is full
        byteBuffer.position(byteBuffer.limit());
    }

    public ByteBuffer getBuffer() {
        return byteBuffer;
    }

    public SocketChannel getChannel() {
        return this.channel;
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

    public int readBytes() throws IOException {
        ensureOpen();
        byteBuffer.clear();
        int size = channel.read(byteBuffer);
        byteBuffer.flip();
        return size;
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
                if (remainStr.length() > 0) {
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
                if (channel.read(byteBuffer) < 0) {
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

    /*
        /r/n/r/n

        7\r\n
        Mozilla\r\n
        9\r\n
        Developer\r\n
        7\r\n
        Network\r\n
        0\r\n
        \r\n
     */
    // use for chunk, header must contain Transfer-Encoding



    public boolean hasNextChunk() throws IOException {
        if (byteBuffer.remaining() == 0) {
            readBytes();
        } else {
            byteBuffer = byteBuffer.slice(); // 切割
        }
        return byteBuffer.remaining() > 5;      // 这里不能这么判断
    }

    public int readChunkSize() throws IOException {
        String a = readLine();
        int size = byteBuffer.getInt();
        // skip a line
        if (byteBuffer.remaining() >= 2) {
            byte[] line = new byte[2];
            byteBuffer.get(line);
        }
        return size;
    }

    public byte[] readChunkData(final int size) throws IOException {
        byte[] bytes = new byte[size];
        final int limit = byteBuffer.limit();
        int targetRemainSize = size;
        if (byteBuffer.remaining() > 0) {
            byteBuffer = byteBuffer.slice();    // slice
            byteBuffer.get(bytes, 0, limit < size ? limit : size);
            targetRemainSize -= limit < size ? limit : size;
        }
        while (targetRemainSize > 0) {
            int readSize = readBytes();
            byte[] temp;
            if (readSize > targetRemainSize) {
                temp = new byte[targetRemainSize];
            } else {
                temp = new byte[byteBuffer.remaining()];
            }
            System.arraycopy(temp, 0, bytes, size - targetRemainSize, temp.length);
            targetRemainSize -= temp.length;
        }
        return bytes;
    }
    // use for chunk

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
