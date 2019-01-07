package tools.android.apkanalyze

import brut.androlib.res.decoder.AXmlResourceParser
import com.google.gson.JsonArray
import org.xmlpull.v1.XmlPullParser
import java.io.File
import java.io.FileInputStream
import com.google.gson.JsonObject
import tools.extend.isNotNullOrBlank
import java.util.*


/**
 * 参考微信 Matrix-ApkChecker
 */
class ManifestParser(manifestFile: File) {

    private val ROOTTAG = "manifest"

    private val resourceParser: AXmlResourceParser
    private val manifestFile: File
    private var isParseStarted = false
    private val jsonStack = Stack<JsonObject>()
    private lateinit var result: JsonObject

    init {
        this.resourceParser = ApkResourceDecoder.createAXmlParser()
        this.manifestFile = manifestFile
    }

    fun parse(): JsonObject {
        resourceParser.open(FileInputStream(manifestFile))
        var token = resourceParser.nextToken()
        while (token != XmlPullParser.END_DOCUMENT) {
            token = resourceParser.next().apply {
                when (this) {
                    XmlPullParser.START_TAG -> handleStartElement()
                    XmlPullParser.TEXT -> handleElementContent()
                    XmlPullParser.END_TAG -> handleEndElement()
                }
            }
        }

        return result
    }

    private inline fun handleStartElement() {
        val name = resourceParser.name
        if (name == ROOTTAG) {
            isParseStarted = true
        }
        if (isParseStarted) {
            val jsonObject = JsonObject()
            for (i in 0 until resourceParser.attributeCount) {
                if ((resourceParser.getAttributePrefix(i).isNotNullOrBlank())) {
                    jsonObject.addProperty(resourceParser.getAttributePrefix(i) + ":" +
                            resourceParser.getAttributeName(i), resourceParser.getAttributeValue(i))
                } else {
                    jsonObject.addProperty(resourceParser.getAttributeName(i), resourceParser.getAttributeValue(i))
                }
            }
            jsonStack.push(jsonObject)
        }
    }

    private fun handleElementContent() {
    }

    private fun handleEndElement() {
        val name = resourceParser.name
        val jsonObject = jsonStack.pop()

        if (jsonStack.isEmpty()) {                                      //root element
            result = jsonObject   // result
        } else {
            val preObject = jsonStack.peek()
            if (preObject.has(name)) {
                preObject.getAsJsonArray(name).apply {
                    add(jsonObject)
                }
            } else {
                JsonArray().apply {
                    this.add(jsonObject)
                    preObject.add(name, this)
                }
            }
        }
    }
}