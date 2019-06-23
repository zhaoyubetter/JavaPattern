package tools.img.ui

import java.awt.*
import javax.swing.*


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

        // 分为3大块
        val borderLayout = BorderLayout()             // 布局管理器
        val mainPanel = JPanel(borderLayout)

        // insertLast to mainPanel
        mainPanel.add(getTopPanel(), BorderLayout.NORTH)
        mainPanel.add(getCenterPanel(), BorderLayout.CENTER)
        mainPanel.add(getBottomPanel(), BorderLayout.SOUTH)

        this.add(mainPanel)
    }

    private inline fun getTopPanel(): JPanel {

        return JPanel().apply {
            layout = GridLayout(2, 1, 0, 0)
            add(JPanel().apply {
                layout = FlowLayout(FlowLayout.LEFT)
                add(JLabel("来源目录："))
                add(JTextField(42))
                add(JButton("..."))
                add(JCheckBox("含子"))
            })

            add(JPanel().apply {
                layout = FlowLayout(FlowLayout.LEFT)
                add(JLabel("目标目录："))
                add(JTextField(42))
                add(JButton("..."))
            })
        }
    }

    private inline fun getCenterPanel(): JPanel {
        val centerPanel = JPanel()
        centerPanel.background = Color.CYAN
        return centerPanel
    }

    private inline fun getBottomPanel(): JPanel {
        val bottomPanel = JPanel()
        bottomPanel.background = Color.yellow
        return bottomPanel
    }
}