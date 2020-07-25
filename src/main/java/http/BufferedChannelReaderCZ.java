package http;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

/**
 * by cz
 */
public class BufferedChannelReaderCZ {
    private static int defaultCharBufferSize = 1024;
    private ByteBuffer byteBuffer;
    private ReadableByteChannel socketChannel;

    public BufferedChannelReaderCZ(ReadableByteChannel socketChannel) {
        this(socketChannel, defaultCharBufferSize);
    }

    public BufferedChannelReaderCZ(ReadableByteChannel socketChannel, int bufferSize) {
        this.socketChannel = socketChannel;
        this.byteBuffer = ByteBuffer.allocate(bufferSize);
        this.byteBuffer.position(byteBuffer.limit());
    }

    /**
     * Checks to make sure that the stream has not been closed
     */
    private void ensureOpen() throws IOException {
        if (socketChannel == null)
            throw new IOException("Stream closed");
    }

    public ByteBuffer getBuffer() {
        return byteBuffer;
    }

    public byte read() throws IOException {
        ensureOpen();
        if (!byteBuffer.hasRemaining()) {     // has already read complete.
            socketChannel.read(byteBuffer);
            byteBuffer.flip();
        }
        return byteBuffer.get();
    }

    /**
     * Read file channel by a internal buffer
     *
     * @return
     * @throws IOException
     */
    public String readLine() throws IOException {
        ensureOpen();
        String line;
        String remainString = null;
        //First time when we read the line.
        if (!byteBuffer.hasRemaining()) {
            byteBuffer.clear();
            socketChannel.read(byteBuffer);
            byteBuffer.flip();
        }
        while (byteBuffer.hasRemaining() && null != (line = readByteBuffer(byteBuffer))) {
            boolean skipRemainString = false;
            if (!byteBuffer.hasRemaining()) {
                //We use all the buffer, trying to read more byte to the buffer.
                //Here May be the line ends with line feeds. So we better skip the remain string.
                skipRemainString = true;
                if (null != remainString) {
                    remainString += line;
                } else {
                    remainString = line;
                }
                byteBuffer.clear();
                //Continue to read
                if (0 > socketChannel.read(byteBuffer)) {
                    byteBuffer.position(byteBuffer.limit());
                    return line;
                }
                byteBuffer.flip();
            }
            if ('\n' == line.charAt(line.length() - 1)) {
                //End with line feeds, just return the line.
                if (!skipRemainString && null != remainString) {
                    return remainString + line;
                } else {
                    return line;
                }
            }
        }
        return null;
    }

    private String readByteBuffer(ByteBuffer byteBuffer) throws EOFException {
        StringBuilder result = new StringBuilder(80);
        while (byteBuffer.hasRemaining()) {
            char c = (char) byteBuffer.get();
            result.append(c);
            if (c == -1) {
                throw new EOFException();
            } else if (c == '\n') {
                break;
            }
        }
        return result.toString();
    }

}
