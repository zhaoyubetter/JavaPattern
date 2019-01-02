package tools.android.apkanalyze;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

import brut.androlib.AndrolibException;
import brut.androlib.res.decoder.ResStreamDecoder;
import tools.extend.ExtendsKt;

/**
 * 参考微信
 */
public class XmlPullResourceRefDecoder implements ResStreamDecoder {

    private final Set<String> resourceRefSet = new HashSet<>();
    private final XmlPullParser mParser;

    public XmlPullResourceRefDecoder(XmlPullParser parser) {
        this.mParser = parser;
    }

    @Override
    public void decode(InputStream inputStream, OutputStream outputStream) throws AndrolibException {
        try {
            mParser.setInput(inputStream, null);
            int token = mParser.next();
            while (token != XmlPullParser.END_DOCUMENT) {
                switch (token) {
                    case XmlPullParser.START_TAG:
                        handleElement();
                        break;
                    case XmlPullParser.TEXT:
                        handleContent();
                        break;
                    default:
                        break;
                }
                token = mParser.next();
            }
//            Log.i(TAG, "find resource references: %s", resourceRefSet);
            inputStream.close();
        } catch (XmlPullParserException var7) {
            throw new AndrolibException("Could not decode XML," + var7.getMessage(), var7);
        } catch (IOException e) {
            throw new AndrolibException("Parse xml error," + e.getMessage(), e);
        }
    }

    private void handleElement() {
        String tagName = mParser.getName();
        int pointIndex = tagName.lastIndexOf('.');
        if (pointIndex >= 0) {
            tagName = tagName.substring(pointIndex + 1);
        }

        if (ExtendsKt.isNotNullOrBlank(tagName)) {
            for (int i = 0; i < mParser.getAttributeCount(); i++) {
                String value = mParser.getAttributeValue(i);
                //Log.d(TAG, "attribute %s, %s", name, value);
                if (ExtendsKt.isNotNullOrBlank(value)) {
                    if (value.startsWith("@")) {
                        int index = value.indexOf('/');
                        if (index > 1) {
                            String type = value.substring(1, index);
                            resourceRefSet.add(ApkConstants.R_PREFIX + type + "." + value.substring(index + 1).replace('.', '_'));
                        }
                    } else if (value.startsWith("?")) {
                        int index = value.indexOf('/');
                        if (index > 1) {
                            resourceRefSet.add(ApkConstants.R_ATTR_PREFIX + "." + value.substring(index + 1).replace('.', '_'));
                        }
                    }
                }
            }
        }
    }

    private void handleContent() {
        String text = mParser.getText();
        if (ExtendsKt.isNotNullOrBlank(text)) {
            if (text.startsWith("@")) {
                int index = text.indexOf('/');
                if (index > 1) {
                    String type = text.substring(1, index);
                    resourceRefSet.add(ApkConstants.R_PREFIX + type + "." + text.substring(index + 1).replace('.', '_'));
                }
            } else if (text.startsWith("?")) {
                int index = text.indexOf('/');
                if (index > 1) {
                    resourceRefSet.add(ApkConstants.R_ATTR_PREFIX + "." + text.substring(index + 1).replace('.', '_'));
                }
            }
        }
    }


    public Set<String> getResourceRefSet() {
        return resourceRefSet;
    }
}
