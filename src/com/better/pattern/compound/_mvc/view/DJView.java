package com.better.pattern.compound._mvc.view;

import com.better.pattern.compound._mvc.controller.ControllerInterface;
import com.better.pattern.compound._mvc.model.BeatModelInterface;
import com.better.pattern.compound._mvc.observer.BMPObserver;
import com.better.pattern.compound._mvc.observer.BeatObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 视图层
 * Created by zhaoyu on 2017/1/8.
 */
public class DJView implements ActionListener, BeatObserver, BMPObserver {

    BeatModelInterface model;       // model接口
    ControllerInterface controller;

    JFrame viewFrame;
    JPanel viewPanel;
    BeatBar beatBar;
    JLabel bmpOutputLabel;

    JFrame controlFrame;
    JPanel controlPanel;
    JLabel bmpLabel;
    JTextField bmpTextField;
    JButton setBmpBtn;
    JButton increaseBMPBtn;
    JButton decreaseBMPBtn;

    JMenuBar menuBar;
    JMenu menu;

    JMenuItem startMenuItem;
    JMenuItem stopMenuItem;

    public DJView(ControllerInterface controller, BeatModelInterface model) {
        this.controller = controller;
        this.model = model;
        model.registerObserver((BeatObserver) this);        // 注册观察者
        model.registerObserver((BMPObserver) this);
    }

    public void createView() {
        viewPanel = new JPanel(new GridLayout(1, 2));
        viewFrame = new JFrame("View");
        viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewFrame.setSize(new Dimension(100, 80));

        bmpOutputLabel = new JLabel("offLine", SwingConstants.CENTER);
        beatBar = new BeatBar();
        beatBar.setValue(0);

        JPanel bmpPanel = new JPanel(new GridLayout(2, 1));
        bmpPanel.add(beatBar);
        bmpPanel.add(bmpOutputLabel);

        viewPanel.add(bmpPanel);

        viewFrame.getContentPane().add(viewPanel, BorderLayout.CENTER);
        viewFrame.pack();
        viewFrame.setVisible(true);
    }

    public void createControls() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        controlFrame = new JFrame("Control");
        controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlFrame.setSize(new Dimension(100, 80));

        controlPanel = new JPanel(new GridLayout(1, 2));

        menuBar = new JMenuBar();
        menu = new JMenu("DJ Controller");
        startMenuItem = new JMenuItem("Start");
        stopMenuItem = new JMenuItem("Stop");
        menu.add(startMenuItem);
        menu.add(stopMenuItem);

        startMenuItem.addActionListener(e -> {
            controller.start();
        });

        stopMenuItem.addActionListener(e -> {
            controller.stop();
        });

        JMenuItem exit = new JMenuItem("Quit");
        exit.addActionListener(e -> System.exit(0));

        menu.add(exit);
        menuBar.add(menu);
        controlFrame.setJMenuBar(menuBar);

        bmpTextField = new JTextField(2);
        bmpLabel = new JLabel("Enter BMP: ", SwingConstants.RIGHT);
        setBmpBtn = new JButton("Set");
        setBmpBtn.setSize(new Dimension(10, 40));
        increaseBMPBtn = new JButton(">>");
        decreaseBMPBtn = new JButton("<<");
        setBmpBtn.addActionListener(this);
        increaseBMPBtn.addActionListener(this);
        decreaseBMPBtn.addActionListener(this);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(decreaseBMPBtn);
        buttonPanel.add(increaseBMPBtn);

        JPanel enterPanel = new JPanel(new GridLayout(1, 2));
        enterPanel.add(bmpLabel);
        enterPanel.add(bmpTextField);

        JPanel insidecontrolPanel = new JPanel(new GridLayout(3, 1));
        insidecontrolPanel.add(enterPanel);
        insidecontrolPanel.add(setBmpBtn);
        insidecontrolPanel.add(buttonPanel);

        controlPanel.add(insidecontrolPanel);

        bmpLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        bmpOutputLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        controlFrame.getRootPane().setDefaultButton(setBmpBtn);
        controlFrame.getContentPane().add(controlPanel, BorderLayout.CENTER);

        controlFrame.pack();
        controlFrame.setVisible(true);
    }

    public void enableStopMenuItem() {
        stopMenuItem.setEnabled(true);
    }

    public void disableStopMenuItem() {
        stopMenuItem.setEnabled(false);
    }


    public void enableStartMenuItem() {
        startMenuItem.setEnabled(true);
    }

    public void disableStartMenuItem() {
        startMenuItem.setEnabled(false);
    }


    @Override
    public void updateBMP() {
        int bmp = model.getBMP();
        if (bmp == 0) {
            bmpOutputLabel.setText("OffLine");
        } else {
            bmpOutputLabel.setText("Current BMP: " + model.getBMP());
        }
    }

    @Override
    public void updateBeat() {
        beatBar.setValue(100);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == setBmpBtn) {
            int bmp = Integer.valueOf(bmpTextField.getText());
            controller.setBMP(bmp);
        } else if(e.getSource() == increaseBMPBtn) {
            controller.increaseBMP();
        } else if(e.getSource() == decreaseBMPBtn) {
            controller.descreaseBMP();
        }
    }
}
