package tools.img;

import com.luciad.imageio.webp.WebPReadParam;
import com.luciad.imageio.webp.WebPWriteParam;

import javax.imageio.*;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;


public class ImageWebTest {

    /** The "lossy" compression taskType. */
    private static final String LOSSY_COMPRESSION_TYPE = "Lossy";
    /** The "lossless" compression taskType. */
    private static final String LOSSLESS_COMPRESSION_TYPE = "Lossless";

    public static void main(String[] args) throws IOException {
        encoding();
//        decoding();
//        encoding2();
    }

    // base encoding
    private static void encoding() throws IOException {
        // 默认无损压缩 压缩度：0.75
        // Obtain an image to encode from somewhere
        BufferedImage image = ImageIO.read(new File("/Users/zhaoyu1/1webpTest/idea_remote_debug_tiny.png"));
        // Encode it as webp using default settings
        ImageIO.write(image, "webp", new File("/Users/zhaoyu1/1webpTest/idea_remote_debug_tiny.webp"));
    }

    private static void encoding2() throws IOException {
        final String extension = "png";
        BufferedImage image = ImageIO.read(new File("/Users/zhaoyu1/1webpTest/idea_remote_debug_bak.png"));

        // get writer
        final ImageWriter imgWriter = ImageIO.getImageWritersByFormatName(extension).next();
        final ImageWriteParam imgWriteParams = new WebPWriteParam(null);
        imgWriteParams.setCompressionType(LOSSLESS_COMPRESSION_TYPE);  // 压缩格式
        // 0~1
        imgWriteParams.setCompressionQuality(0.75f);  // 系数
        final File file = new File("/Users/zhaoyu1/1webpTest/idea_remote_debug_bak__.png");
        final ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(file);
        try {
            imgWriter.setOutput(imageOutputStream);
            imgWriter.write(null, new IIOImage(image, null, null), imgWriteParams);
        } finally {
            try {
                imageOutputStream.close();
            } catch (final IOException e) {
            }
        }
    }


    private static void decoding() throws IOException {
        // === 1.  default settings as follows
        BufferedImage image2 = ImageIO.read(new File("/Users/zhaoyu1/__output2.webp"));

        // === 2. custom settings
// Obtain a WebP ImageReader instance
        ImageReader reader = ImageIO.getImageReadersByMIMEType("image/webp").next();

// Configure decoding parameters
        WebPReadParam readParam = new WebPReadParam();
        readParam.setBypassFiltering(true);

// Configure the input on the ImageReader
        reader.setInput(new FileImageInputStream(new File("/Users/zhaoyu1/__output2.webp")));

// Decode the image
        BufferedImage image = reader.read(0, readParam);
    }
}
