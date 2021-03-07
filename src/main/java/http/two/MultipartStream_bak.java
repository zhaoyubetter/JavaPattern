//package http.two;
//
//import org.apache.commons.fileupload.FileUploadBase;
//import org.apache.commons.fileupload.util.Closeable;
//import org.apache.commons.fileupload.util.Streams;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.nio.channels.SocketChannel;
//
//class MultipartStream_bak {
//
//    /**
//     * The Carriage Return ASCII character value.
//     */
//    public static final byte CR = 0x0D;
//
//    /**
//     * The Line Feed ASCII character value.
//     */
//    public static final byte LF = 0x0A;
//
//    ///----------------
//    /**
//     * The dash (-) ASCII character value.
//     */
//    public static final byte DASH = 0x2D;
//
//    /**
//     * A byte sequence that that follows a delimiter of the last
//     * encapsulation in the stream (<code>--</code>).
//     */
//    protected static final byte[] STREAM_TERMINATOR = {DASH, DASH};
//
//    /**
//     * A byte sequence that that follows a delimiter that will be
//     * followed by an encapsulation (<code>CRLF</code>).
//     */
//    protected static final byte[] FIELD_SEPARATOR = {CR, LF};
//
//    /**
//     * A byte sequence that precedes a boundary (<code>CRLF--</code>).
//     */
//    protected static final byte[] BOUNDARY_PREFIX = {CR, LF, DASH, DASH};
//
//    /**
//     * A byte sequence that marks the end of <code>header-part</code>
//     * (<code>CRLFCRLF</code>).
//     */
//    protected static final byte[] HEADER_SEPARATOR = {CR, LF, CR, LF};
//
//
//    final InputStream input;
//    final int bufSize;
//    int boundaryLength;
//    final byte[] buffer;
//
//    /**
//     * The byte sequence that partitions the stream.
//     */
//    final byte[] boundary;
//    /**
//     * The table for Knuth-Morris-Pratt search algorithm.
//     * KMP 算法 next 数组
//     */
//    private final int[] boundaryTable;
//    /**
//     * The amount of data, in bytes, that must be kept in the buffer in order
//     * to detect delimiters reliably.
//     */
//    private final int keepRegion;
//
//    /**
//     * The index of first valid character in the buffer.
//     * <br>
//     * 0 <= head < bufSize
//     */
//    private int head;
//
//    /**
//     * The index of last valid character in the buffer + 1.
//     * <br>
//     * 0 <= tail <= bufSize
//     */
//    private int tail;
//
//
//    public MultipartStream_bak(InputStream in, byte[] boundary) {
//        this.boundaryLength = boundary.length + BOUNDARY_PREFIX.length;
//        this.input = in;
//        this.bufSize = Math.max(512, boundaryLength);
//        this.buffer = new byte[this.bufSize];
//
//        this.boundary = new byte[this.boundaryLength];
//        this.boundaryTable = new int[this.boundaryLength + 1];
//        this.keepRegion = this.boundary.length;
//
//        System.arraycopy(BOUNDARY_PREFIX, 0, boundary, 0, BOUNDARY_PREFIX.length);
//        System.arraycopy(boundary, 0, this.boundary, BOUNDARY_PREFIX.length, boundary.length);
//
//        computeBoundaryTable();
//
//        head = 0;
//        tail = 0;
//    }
//
//    /**
//     * 跳过序文
//     * Finds the beginning of the first <code>encapsulation</code>.
//     *
//     * @return
//     * @throws IOException
//     */
//    public boolean skipPreamble() throws IOException {
//        // First delimiter may be not preceeded with a CRLF.
//        System.arraycopy(boundary, 2, boundary, 0, boundary.length - 2);
//        boundaryLength = boundary.length - 2;
//        computeBoundaryTable();
//        try {
//            // Discard all data up to the delimiter.
//            discardBodyData();
//
//            // Read boundary - if succeeded, the stream contains an
//            // encapsulation.
//            return readBoundary();
//        } catch (IllegalArgumentException e) {
//            return false;
//        } finally {
//            // Restore delimiter.
//            System.arraycopy(boundary, 0, boundary, 2, boundary.length - 2);
//            boundaryLength = boundary.length;
//            boundary[0] = CR;
//            boundary[1] = LF;
//            computeBoundaryTable();
//        }
//    }
//
//
//    /**
//     * Skips a <code>boundary</code> token, and checks whether more
//     * <code>encapsulations</code> are contained in the stream.
//     *
//     * @return <code>true</code> if there are more encapsulations in
//     *         this stream; <code>false</code> otherwise.
//     *
//     * @throws FileUploadBase.FileUploadIOException if the bytes read from the stream exceeded the size limits
//     */
//    public boolean readBoundary()
//            throws FileUploadBase.FileUploadIOException {
//        byte[] marker = new byte[2];
//        boolean nextChunk = false;
//
//        head += boundaryLength;
//        try {
//            marker[0] = readByte();
//            if (marker[0] == LF) {
//                // Work around IE5 Mac bug with input type=image.
//                // Because the boundary delimiter, not including the trailing
//                // CRLF, must not appear within any file (RFC 2046, section
//                // 5.1.1), we know the missing CR is due to a buggy browser
//                // rather than a file containing something similar to a
//                // boundary.
//                return true;
//            }
//
//            marker[1] = readByte();
//            if (arrayequals(marker, STREAM_TERMINATOR, 2)) {        // --
//                nextChunk = false;
//            } else if (arrayequals(marker, FIELD_SEPARATOR, 2)) {   // \r\n
//                nextChunk = true;
//            } else {
//                throw new IllegalArgumentException(
//                        "Unexpected characters follow a boundary");
//            }
//        } catch (FileUploadBase.FileUploadIOException e) {
//            // wraps a SizeException, re-throw as it will be unwrapped later
//            throw e;
//        } catch (IOException e) {
//            throw new IllegalArgumentException("Stream ended unexpectedly");
//        }
//        return nextChunk;
//    }
//
//    /**
//     * <p>Reads the <code>header-part</code> of the current
//     * <code>encapsulation</code>.
//     *
//     * <p>Headers are returned verbatim to the input stream, including the
//     * trailing <code>CRLF</code> marker. Parsing is left to the
//     * application.
//     *
//     *
//     * @return The <code>header-part</code> of the current encapsulation.
//     *
//     * @throws FileUploadBase.FileUploadIOException if the bytes read from the stream exceeded the size limits.
//     */
//    public String readHeaders() throws IOException {
//        int i = 0;
//        byte b;
//        // to support multi-byte characters
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        int size = 0;
//        while (i < HEADER_SEPARATOR.length) {   // \r\n\r\n
//            try {
//                b = readByte();
//            } catch (FileUploadBase.FileUploadIOException e) {
//                // wraps a SizeException, re-throw as it will be unwrapped later
//                throw e;
//            } catch (IOException e) {
//                throw new org.apache.commons.fileupload.MultipartStream.MalformedStreamException("Stream ended unexpectedly");
//            }
//            // header is too large
////            if (++size > HEADER_PART_SIZE_MAX) {
////                throw new org.apache.commons.fileupload.MultipartStream.MalformedStreamException(
////                        format("Header section has more than %s bytes (maybe it is not properly terminated)",
////                                Integer.valueOf(HEADER_PART_SIZE_MAX)));
////            }
//            if (b == HEADER_SEPARATOR[i]) {
//                i++;
//            } else {
//                i = 0;
//            }
//            baos.write(b);
//        }
//
//        String headers = null;
////        if (headerEncoding != null) {
////            try {
////                headers = baos.toString(headerEncoding);
////            } catch (UnsupportedEncodingException e) {
////                // Fall back to platform default if specified encoding is not
////                // supported.
////                headers = baos.toString();
////            }
////        } else {
////            headers = baos.toString();
////        }
//
//        headers = baos.toString("utf-8");
//        return headers;
//    }
//
//    /**
//     * Compares <code>count</code> first bytes in the arrays
//     * <code>a</code> and <code>b</code>.
//     *
//     * @param a     The first array to compare.
//     * @param b     The second array to compare.
//     * @param count How many bytes should be compared.
//     *
//     * @return <code>true</code> if <code>count</code> first bytes in arrays
//     *         <code>a</code> and <code>b</code> are equal.
//     */
//    public static boolean arrayequals(byte[] a,
//                                      byte[] b,
//                                      int count) {
//        for (int i = 0; i < count; i++) {
//            if (a[i] != b[i]) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//
//    /**
//     * Reads a byte from the <code>buffer</code>, and refills it as
//     * necessary.
//     *
//     * @return The next byte from the input stream.
//     *
//     * @throws IOException if there is no more data available.
//     */
//    public byte readByte() throws IOException {
//        // Buffer depleted ?
//        if (head == tail) {
//            head = 0;
//            // Refill.
//            tail = input.read(buffer, head, bufSize);
//            if (tail == -1) {
//                // No more data available.
//                throw new IOException("No more data is available");
//            }
////            if (notifier != null) {
////                notifier.noteBytesRead(tail);
////            }
//        }
//        return buffer[head++];
//    }
//
//
//    /**
//     * <p> Reads <code>body-data</code> from the current
//     * <code>encapsulation</code> and discards it.
//     *
//     * <p>Use this method to skip encapsulations you don't need or don't
//     * understand.
//     *
//     * @return The amount of data discarded.
//     * @throws IOException if an i/o error occurs.
//     */
//    public int discardBodyData() throws IOException {
//        return readBodyData(null);
//    }
//
//    /**
//     * <p>Reads <code>body-data</code> from the current
//     * <code>encapsulation</code> and writes its contents into the
//     * output <code>Stream</code>.
//     *
//     * <p>Arbitrary large amounts of data can be processed by this
//     * method using a constant size buffer. (see {@link
//     *
//     * @param output The <code>Stream</code> to write data into. May
//     *               be null, in which case this method is equivalent
//     *               to {@link #discardBodyData()}.
//     * @return the amount of data written.
//     * @throws IOException if an i/o error occurs.
//     */
//    public int readBodyData(OutputStream output) throws IOException {
//        return (int) Streams.copy(newInputStream(), output, false); // N.B. Streams.copy closes the input stream
//    }
//
//    /**
//     *
//     */
//    ItemInputStream newInputStream() {
//        return new ItemInputStream();
//    }
//
//
//    /**
//     * Compute the table used for Knuth-Morris-Pratt search algorithm.
//     * 计算next数组
//     */
//    private void computeBoundaryTable() {
//        int position = 2;
//        int candidate = 0;
//
//        boundaryTable[0] = -1;
//        boundaryTable[1] = 0;
//
//        // begin at 3
//        while (position <= boundaryLength) {
//            if (boundary[position - 1] == boundary[candidate]) {
//                boundaryTable[position] = candidate + 1;
//                candidate++;
//                position++;
//            } else if (candidate > 0) {
//                candidate = boundaryTable[candidate];
//            } else {
//                boundaryTable[position] = 0;
//                position++;
//            }
//        }
//    }
//
//    /**
//     * Searches for the <code>boundary</code> in the <code>buffer</code>
//     * region delimited by <code>head</code> and <code>tail</code>.
//     * <p>
//     * 这里就是 KMP 算法了
//     *
//     * @return The position of the boundary found, counting from the
//     * beginning of the <code>buffer</code>, or <code>-1</code> if
//     * not found.
//     */
//    protected int findSeparator() {
//
//        int bufferPos = this.head;  // i
//        int tablePos = 0;           // j
//
//        while (bufferPos < this.tail) {
//            while (tablePos >= 0 && buffer[bufferPos] != boundary[tablePos]) {
//                tablePos = boundaryTable[tablePos];
//            }
//            bufferPos++;
//            tablePos++;
//            // find the boundary
//            if (tablePos == boundaryLength) {
//                return bufferPos - boundaryLength;
//            }
//        }
//        return -1;
//    }
//
//    /**
//     * An {@link InputStream} for reading an items contents.
//     */
//    public class ItemInputStream extends InputStream implements Closeable {
//
//        /**
//         * Offset when converting negative bytes to integers.
//         */
//        private static final int BYTE_POSITIVE_OFFSET = 256;
//
//
//        /**
//         * The number of bytes, which have been read so far.
//         */
//        private long total;
//
//        /**
//         * The number of bytes, which must be hold, because
//         * they might be a part of the boundary.
//         */
//        private int pad;
//
//        /**
//         * The current offset in the buffer.
//         */
//        private int pos;
//
//        /**
//         * Whether the stream is already closed.
//         */
//        private boolean closed;
//
//        ItemInputStream() {
//            findSeparator();
//        }
//
//        /**
//         * Called for finding the separator.
//         */
//        private void findSeparator() {
//            pos = MultipartStream_bak.this.findSeparator();
//            // not found
//            if (pos == -1) {
//                if (tail - head > keepRegion) {
//                    pad = keepRegion;  // \r\n--boundary
//                } else {
//                    pad = tail - head;
//                }
//            }
//        }
//
//        /**
//         * Returns the number of bytes, which have been read
//         * by the stream.
//         *
//         * @return Number of bytes, which have been read so far.
//         */
//        public long getBytesRead() {
//            return total;
//        }
//
//        /**
//         * Returns the number of bytes, which are currently
//         * available, without blocking.
//         *
//         * @throws IOException An I/O error occurs.
//         * @return Number of bytes in the buffer.
//         */
//        @Override
//        public int available() throws IOException {
//            if (pos == -1) {    // no boundary
//                return tail - head - pad;
//            }
//            return pos - head;
//        }
//
//        /**
//         * Returns the next byte in the stream.
//         *
//         * @return The next byte in the stream, as a non-negative
//         *   integer, or -1 for EOF.
//         * @throws IOException An I/O error occurred.
//         */
//        @Override
//        public int read() throws IOException {
//            if (closed) {
//                throw new IllegalAccessError("");
//            }
//            if (available() == 0 && makeAvailable() == 0) {
//                return -1;
//            }
//            ++total;
//            int b = buffer[head++];
//            if (b >= 0) {
//                return b;
//            }
//            return b + BYTE_POSITIVE_OFFSET;
//        }
//
//        /**
//         * Reads bytes into the given buffer.
//         *
//         * @param b The destination buffer, where to write to.
//         * @param off Offset of the first byte in the buffer.
//         * @param len Maximum number of bytes to read.
//         * @return Number of bytes, which have been actually read,
//         *   or -1 for EOF.
//         * @throws IOException An I/O error occurred.
//         */
//        @Override
//        public int read(byte[] b, int off, int len) throws IOException {
//            if (closed) {
//                throw new IllegalAccessError();
//            }
//            if (len == 0) {
//                return 0;
//            }
//            int res = available();
//            if (res == 0) {
//                res = makeAvailable();
//                if (res == 0) {
//                    return -1;
//                }
//            }
//            res = Math.min(res, len);
//            System.arraycopy(buffer, head, b, off, res);
//            head += res;
//            total += res;
//            return res;
//        }
//
//
//        /**
//         * Attempts to read more data.
//         *
//         * @return Number of available bytes
//         * @throws IOException An I/O error occurred.
//         */
//        private int makeAvailable() throws IOException {
//            if (pos != -1) {
//                return 0;
//            }
//
//            // Move the data to the beginning of the buffer.
//            total += tail - head - pad;
//            System.arraycopy(buffer, tail - pad, buffer, 0, pad);
//
//            // Refill buffer with new data.
//            head = 0;
//            tail = pad;
//
//            for (;;) {
//                int bytesRead = input.read(buffer, tail, bufSize - tail);
//                if (bytesRead == -1) {
//                    // The last pad amount is left in the buffer.
//                    // Boundary can't be in there so signal an error
//                    // condition.
//                    final String msg = "Stream ended unexpectedly";
//                    throw new IOException(msg);
//                }
////                if (notifier != null) {
////                    notifier.noteBytesRead(bytesRead);
////                }
//                tail += bytesRead;
//
//                findSeparator();
//                int av = available();
//
//                if (av > 0 || pos != -1) {
//                    return av;
//                }
//            }
//        }
//
//        /**
//         * Closes the input stream.
//         *
//         * @throws IOException An I/O error occurred.
//         */
//        @Override
//        public void close() throws IOException {
//            close(false);
//        }
//
//        /**
//         * Closes the input stream.
//         *
//         * @param pCloseUnderlying Whether to close the underlying stream
//         *   (hard close)
//         * @throws IOException An I/O error occurred.
//         */
//        public void close(boolean pCloseUnderlying) throws IOException {
//            if (closed) {
//                return;
//            }
//            if (pCloseUnderlying) {
//                closed = true;
//                input.close();
//            } else {
//                for (;;) {
//                    int av = available();
//                    if (av == 0) {
//                        av = makeAvailable();
//                        if (av == 0) {
//                            break;
//                        }
//                    }
//                    skip(av);
//                }
//            }
//            closed = true;
//        }
//
//        /**
//         * Skips the given number of bytes.
//         *
//         * @param bytes Number of bytes to skip.
//         * @return The number of bytes, which have actually been
//         *   skipped.
//         * @throws IOException An I/O error occurred.
//         */
//        @Override
//        public long skip(long bytes) throws IOException {
//            if (closed) {
//                throw new IOException();
//            }
//            int av = available();
//            if (av == 0) {
//                av = makeAvailable();
//                if (av == 0) {
//                    return 0;
//                }
//            }
//            long res = Math.min(av, bytes);
//            head += res;
//            return res;
//        }
//
//        /**
//         * Returns, whether the stream is closed.
//         *
//         * @return True, if the stream is closed, otherwise false.
//         */
//        public boolean isClosed() {
//            return closed;
//        }
//    }
//}