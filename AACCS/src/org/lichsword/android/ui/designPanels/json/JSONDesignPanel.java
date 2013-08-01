package org.lichsword.android.ui.designPanels.json;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.lichsword.service.json.JsonClientService;

public class JSONDesignPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 3028317657813419800L;

    private JTabbedPane tabbedPane;

    private JTextField textField;

    private AbstractModelPanel currentModelPanel;

    private TextModePanel textModePanel;
    private TreeModePanel treeModePanel;
    private CodeModePanel codeModePanel;

    private final ButtonActionListener mActionListener = new ButtonActionListener();

    public JSONDesignPanel() {
        super();
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        initContentView();
    }

    private class ButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DownloadJsonThread thread = new DownloadJsonThread(
                    textField.getText());
            thread.run();
        }

    }

    private class DownloadJsonThread extends Thread {

        private final String url;

        DownloadJsonThread(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            String result = JsonClientService.getInstance().retrieveJsonString(
                    url);
            currentModelPanel.setData(result);
            super.run();
        }

    }

    class TabChangeListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
            currentModelPanel = (AbstractModelPanel) tabbedPane
                    .getSelectedComponent();
        }
    }

    private void initContentView() {
        textField = new JTextField();
        textField.setBounds(16, 6, 412, 28);
        add(textField);
        textField.setColumns(10);

        JButton btnRun = new JButton("Run");
        btnRun.setBounds(440, 7, 69, 29);
        btnRun.addActionListener(mActionListener);
        add(btnRun);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.addChangeListener(new TabChangeListener());
        textModePanel = new TextModePanel();
        tabbedPane.addTab("Text", textModePanel);/* first tab */
        treeModePanel = new TreeModePanel();
        tabbedPane.addTab("Tree", treeModePanel);/* second tab */
        codeModePanel = new CodeModePanel();
        tabbedPane.addTab("Code", codeModePanel);/* third tab */
        tabbedPane.setBounds(26, 45, 483, 284);
        add(tabbedPane);
        tabbedPane.setSelectedIndex(1);
        currentModelPanel = (AbstractModelPanel) tabbedPane
                .getSelectedComponent();
    }
}
