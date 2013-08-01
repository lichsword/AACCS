package org.lichsword.android.ui.designPanels.json;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
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
    private JTextArea textArea;

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
        tabbedPane.addTab("Text", new TextModePanel());
        tabbedPane.addTab("Tree", new TreeModePanel());
        tabbedPane.addTab("Code", new CodeModePanel());
        tabbedPane.setBounds(26, 45, 483, 284);
        add(tabbedPane);
        tabbedPane.setSelectedIndex(0);
    }

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
            textArea.setText(result);
            super.run();
        }

    }
}
