package com.better.pattern.observe_2.client

import com.better.pattern.observe_2.before.CarDisplay
import com.better.pattern.observe_2.before.PhoneDisplay
import com.better.pattern.observe_2.before.Wheel
import groovy.swing.SwingBuilder

import javax.swing.BorderFactory
import javax.swing.JFrame
import javax.swing.WindowConstants
import java.awt.BorderLayout
import java.awt.Color

/**
 * Created by zhaoyu on 2016/12/12.
 */
class MainFrame extends JFrame {

    def messageTextArea
    def frame

    def wheel       // 事件源
    def car         // 2个监听
    def phone
    def audio

    def initWheel() {

        car = new CarDisplay()
        phone = new PhoneDisplay()

        // audio = new AudioDisplay()

        wheel = new Wheel(car, phone)
        //wheel.setAudio(audio)

    }

    MainFrame() {
        initWheel();

        def swingBuilder = new SwingBuilder()
        swingBuilder.edt {
            frame = swingBuilder.frame(title: '观察者模式测试', size: [800, 600], visible: true, locationRelativeTo: null, background: Color.WHITE, defaultCloseOperation: WindowConstants.EXIT_ON_CLOSE) {
                panel(layout: new BorderLayout(0, 10)) {
                    swingBuilder.scrollPane(constraints: BorderLayout.CENTER, border: BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10), "")) {
                        messageTextArea = textArea(rows: 6, columns: 20)
                    }
                    swingBuilder.panel(constraints: BorderLayout.SOUTH, layout: new BorderLayout()) {
                        swingBuilder.panel(constraints: BorderLayout.EAST) {
                            button(text: "Clean", actionPerformed: { messageTextArea.setText(null) })
                            button(text: "增加压力", actionPerformed: { addPressure() })
                            button(text: "增加磨损", actionPerformed: { addFret() })

                        }
                    }
                }
            }
        }
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
        String msg1 = car.getMsg()
        String msg2 = phone.getMsg()
        String msg3 = audio ? audio.getMsg()  : ""

        messageTextArea.setText("$oldValue\n$msg1\n$msg2\n$msg3\n");
    }
}
