/***********************************************************************
 * 
 *     Copyright: 2011, BAINA Technologies Co. Ltd.
 *     Classname: Launcher.java
 *     Author:    yuewang
 *     Description:    TODO
 *     History:
 *         1.  Date:   下午02:55:42
 *             Author:    yuewang
 *             Modifycation:    create the class.       
 *
 ***********************************************************************/

package org.lichsword.android.ui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import org.lichsword.android.manager.AppVersionManager;
import org.lichsword.android.manager.ConfigManager;
import org.lichsword.android.ui.designPanels.ActivityDesignPanel;
import org.lichsword.android.ui.designPanels.ButtonDesignPanel;
import org.lichsword.android.ui.designPanels.DatabaseDesignPanel;
import org.lichsword.android.ui.designPanels.FlowSheetDesignPanel;
import org.lichsword.android.ui.designPanels.OptionMenuDesignPanel;
import org.lichsword.android.ui.designPanels.json.JSONDesignPanel;

/**
 * @author yuewang
 * 
 */
public class Launcher extends JFrame {
    /**
	 * 
	 */
    private static final long serialVersionUID = -2561591521749339934L;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Launcher frame = new Launcher();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Launcher() {
        initFrameParam();
        initContentView();
    }

    private void initFrameParam() {
        setTitle(AppVersionManager.getInstance().getAppName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 1000);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();

        int width = screenSize.width;
        int height = screenSize.height;

        int defaultX = (width - getSize().width) / 2;
        int defaultY = (height - getSize().height) / 2;

        ConfigManager manager = ConfigManager.getInstance();

        setBounds(manager.getWindowX(defaultX), manager.getWindowY(defaultY),
                manager.getWindowWidth(ConfigManager.DEFAULT_WINDOW_WIDTH),
                manager.getWindowHeight(ConfigManager.DEFAULT_WINDOW_HEIGHT));

        setResizable(true);

        addWindowListener(mWindowListener);
    }

    private final WindowListener mWindowListener = new WindowListener() {

        @Override
        public void windowOpened(WindowEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void windowIconified(WindowEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void windowDeiconified(WindowEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void windowDeactivated(WindowEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void windowClosing(WindowEvent e) {
            writeWindowConfigParam();
        }

        @Override
        public void windowClosed(WindowEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void windowActivated(WindowEvent e) {
            // TODO Auto-generated method stub

        }
    };

    private void writeWindowConfigParam() {
        Rectangle rect = Launcher.this.getBounds();
        ConfigManager manager = ConfigManager.getInstance();
        manager.setWindowX(rect.x);
        manager.setWindowY(rect.y);
        manager.setWindowWidth(rect.width);
        manager.setWindowHeight(rect.height);
        manager.setWindowTabIndex(mTabs.getSelectedIndex());
    }

    private JTabbedPane mTabs;

    private void initContentView() {
        mTabs = new JTabbedPane(JTabbedPane.TOP);
        getContentPane().add(mTabs);
        initChildTab();
    }

    /**
     * "Database"
     */
    private final String TAB_NAME_DATABASE = "Database";
    /**
     * "Option Menu"
     */
    private final String TAB_NAME_OPTION_MENU = "Option Menu";

    private final String TAB_NAME_BUTTON = "  Button  ";

    private final String TAB_NAME_JSON = "  JSON    ";

    private final String TAB_NAME_FLOW_SHEET = "Flow Sheet";

    private void initChildTab() {
        mTabs.addTab(TAB_NAME_UI, new ActivityDesignPanel());
        mTabs.addTab(TAB_NAME_DATABASE, new DatabaseDesignPanel());
        mTabs.addTab(TAB_NAME_OPTION_MENU, new OptionMenuDesignPanel());
        mTabs.addTab(TAB_NAME_BUTTON, new ButtonDesignPanel());
        mTabs.addTab(TAB_NAME_JSON, new JSONDesignPanel());
        mTabs.addTab(TAB_NAME_FLOW_SHEET, new FlowSheetDesignPanel());

        int index = ConfigManager.getInstance().getWindowTabIndex(0);
        if (index >= mTabs.getTabCount()) {
            index = 0;
        }// end if
        mTabs.setSelectedIndex(index);
    }

    private final String TAB_NAME_UI = "   UI   ";

}
