package com.better.pattern.observe_2.after

import groovy.swing.SwingBuilder

import javax.swing.*
import java.awt.*

/**
 * Created by zhaoyu on 2016/12/12.
 */
class MainFrame extends JFrame {

    def messageTextArea
    def frame

    def wheel       // 事件源
    def car         // 2个监听
    def phone

    def initWheel() {
        // 1.创建主题
        wheel = new Wheel()

        // 2.创建观察者
        car = new CarDisplay(wheel)
        phone = new PhoneDisplay(wheel)

        // 省略注册
    }

    MainFrame() {
        initWheel();

        def swingBuilder = new SwingBuilder()
        swingBuilder.edt {
            frame = swingBuilder.frame(title: '观察者模式测试三', size: [800, 600], visible: true, locationRelativeTo: null, background: Color.WHITE, defaultCloseOperation: WindowConstants.EXIT_ON_CLOSE) {
                panel(layout: new BorderLayout(0, 10)) {
                    swingBuilder.scrollPane(constraints: BorderLayout.CENTER, border: BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10), "")) {
                        messageTextArea = textArea(rows: 6, columns: 20)
                    }
                    swingBuilder.panel(constraints: BorderLayout.SOUTH, layout: new BorderLayout()) {
                        swingBuilder.panel(constraints: BorderLayout.EAST) {
                            button(text: "Clean", actionPerformed: { messageTextArea.setText(null) })
                            button(text: "增加压力", actionPerformed: { addPressure() })
                            button(text: "增加磨损", actionPerformed: { addFret() })
                            button(text: '移除【手机】', actionPerformed: {removePhone()})
                            button(text: '添加【手机】', actionPerformed: {addPhone()})
                        }
                    }
                }
            }
        }
    }

    def addPhone() {
        // 添加观察者
        wheel.addObserver(phone)
    }

    def removePhone() {
        // 移除观察者
        wheel.removeObserver(phone)
    }

    def addPressure() {
        wheel.setPressure(wheel.getPressure() + 1);
        showMsg()
    }

    def addFret() {
        wheel.setFret(wheel.getFret() + 2);
        showMsg()
    }

    def showMsg() {
        def oldValue = messageTextArea.getText()
        messageTextArea.setText("$oldValue\n" + wheel.getMsg());
    }
}
