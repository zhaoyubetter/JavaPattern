package tools.img.ui

import java.awt.EventQueue
import javax.swing.JFrame

/**
 * 主窗口
 */

fun main(args: Array<String>) {
    EventQueue.invokeLater(::createAndShowGUI)
}

private fun createAndShowGUI() {
    val frame = MainFrame("图片压缩转换小工具")
    frame.isVisible = true
}

class MainFrame(title: String) : JFrame() {
    init {
        createUI(title)
    }

    private fun createUI(title: String) {
        setTitle(title)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setSize(800, 600)
        setLocationRelativeTo(null)
    }

}