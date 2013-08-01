package org.lichsword.android.ui.designPanels.json;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.lichsword.service.json.JsonClientService;

public class JSONDesignPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 3028317657813419800L;

    public JSONDesignPanel() {
        super();
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        initContentView();
    }

    private JTextField textField;

    private TextModePanel textModePanel;
    private TreeModePanel treeModePanel;
    private CodeModePanel codeModePanel;

    private final ButtonActionListener mActionListener = new ButtonActionListener();

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
            textModePanel.setText(result);
            super.run();
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

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        textModePanel = new TextModePanel();
        tabbedPane.addTab("Text", textModePanel);
        treeModePanel = new TreeModePanel();
        tabbedPane.addTab("Tree", treeModePanel);
        codeModePanel = new CodeModePanel();
        tabbedPane.addTab("Code", codeModePanel);
        tabbedPane.setBounds(26, 45, 483, 284);
        add(tabbedPane);
        tabbedPane.setSelectedIndex(0);
    }
}
