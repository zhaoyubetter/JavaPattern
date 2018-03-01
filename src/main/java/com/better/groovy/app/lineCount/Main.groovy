package com.better.groovy.app.lineCount

import javax.swing.*;
import groovy.swing.SwingBuilder

import java.awt.BorderLayout
import java.awt.Color
import java.awt.FlowLayout

/**
 * Created by zhaoyu on 2017/5/6.
 */
class Main {

    static class Run implements Observer {
        def pathTextField
        def frame
        def messageTextArea
        def startBtn, stopBtn

        // 避免积压的runnable一直执行，造成错误
        def isFinish = false
        def sb = new SwingBuilder()

        Observable observable


        public Run(Observable o) {
            this.observable = o;
            this.observable.addObserver(this)
        }

        // 上
        def topPanel = {
            sb.panel(constraints: BorderLayout.NORTH, layout: new FlowLayout(FlowLayout.LEFT)) {
                label(text: '路径：')
                pathTextField = textField(columns: 24, text: '/Users/zhaoyu/AndroidStudioProjects/MyApplication/app/src/main/java')
                button(text: "...", actionPerformed: {
                    showFileChooser()
                })
            }
        }

        // 中
        def centerPanel = {
            sb.scrollPane(constraints: BorderLayout.CENTER,
                    border: BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(4, 4, 4, 0))) {
                messageTextArea = textArea(rows: 10)
            }
        }

        // 下
        def bottomPanel = {
            sb.panel(constraints: BorderLayout.SOUTH, layout: new BorderLayout()) {
                sb.panel(constraints: BorderLayout.EAST) {
                    startBtn = button(text: "开始", actionPerformed: { doWork() })
                    stopBtn = button(text: "结束", actionPerformed: { stopWork() })
                }
            }
        }


        def run() {
            frame = sb.frame(title: '统计代码行数', visible: true, locationRelativeTo: null, background: Color.WHITE,
                    size: [480, 320],
                    defaultCloseOperation: WindowConstants.EXIT_ON_CLOSE) {
                panel(layout: new BorderLayout()) {
                    topPanel()
                    centerPanel()
                    bottomPanel()
                }
            }

            frame.pack()
            frame.setVisible(true)
        }

        def showFileChooser() {
            def fileChooser = new JFileChooser()
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES)
            fileChooser.setDialogTitle("选择文件或文件夹");
            int returnVal = fileChooser.showOpenDialog(frame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                pathTextField.setText(fileChooser.getSelectedFile().getPath())
            }
        }


        def doWork() {
            isFinish = false
            def path = new File(pathTextField.getText())
            if (path.exists()) {
                messageTextArea.setText('')
                new Thread(new Runnable() {
                    @Override
                    void run() {
                        observable.start(path)
                    }
                }).start()
                startBtn.setEnabled(false)
            }
        }

        def stopWork() {
            observable.stop()
            startBtn.setEnabled(true)
        }

        @Override
        void update(Observable o, Object arg) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                void run() {
                    if (isFinish) {
                        return
                    }
                    if (observable.isStop) {
                        messageTextArea.setText(messageTextArea.getText() + " \n\n行数：${observable.codeLine}\n 注释：${observable.explainLine}\n 空行：${observable.spaceLine}")
                        stopWork()
                        isFinish = true
                    } else {
                        messageTextArea.setText(messageTextArea.getText() + "\n" + arg)
                    }
                }
            })
        }
    }


    public static void main(String[] args) {
        CodeLineCountRegex2 tool = new CodeLineCountRegex2()
        new Run(tool).run()
    }
}