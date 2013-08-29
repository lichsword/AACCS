package org.lichsword.android.ui.designPanels.game;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class TLBBWG extends JPanel {

    private static final long serialVersionUID = -1873038449772500279L;

    private JButton mStartButton;
    private Timer mTimer = null;

    public TLBBWG() {
        super();
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        initContentView();
    }

    Robot mRobot = null;

    private void initContentView() {
        try {
            mRobot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        mTimer = new Timer(200, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("do...");
                mRobot.keyPress(KeyEvent.VK_A);
                mRobot.keyPress(KeyEvent.VK_A);
                mRobot.keyPress(KeyEvent.VK_A);
                mRobot.keyPress(KeyEvent.VK_A);
            }
        });
        mStartButton = new JButton("开始");
        mStartButton.setBounds(48, 62, 117, 29);
        add(mStartButton);
        mStartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (mTimer.isRunning()) {
                    mStartButton.setText("开始");
                    mTimer.stop();
                } else {
                    mStartButton.setText("停止");
                    mTimer.start();
                }
            }
        });
    }

}
