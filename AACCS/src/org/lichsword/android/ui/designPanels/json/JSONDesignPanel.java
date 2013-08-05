package org.lichsword.android.ui.designPanels.json;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.lichsword.android.ui.designPanels.json.TreeModePanel.IconNode;
import org.lichsword.service.json.JsonClientService;

/**
 * <p>
 * JSONDesignPanel is the data center, the TextModePanel, CodeModePanel,
 * TreeModePanel are the branches of center, to divider difference UI work.
 * </p>
 * 
 * @author lichsword
 * 
 */
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
            if (currentModelPanel instanceof CodeModePanel) {
                CodeModePanel codeModePanel = (CodeModePanel) currentModelPanel;
                /* get root node from tree */
                IconNode rootNode = treeModePanel.getRootNode();
                codeModePanel.setData(rootNode);
            }// end if
        }
    }

    private void initContentView() {
        setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        add(panel, BorderLayout.PAGE_START);
        /* add URL text field && RUN button into panel */
        panel.setLayout(new BorderLayout(0, 0));
        textField = new JTextField();
        textField.setColumns(10);
        textField
                .setText("http://localhost/project/sharefile/android_version.json");
        panel.add(textField, BorderLayout.CENTER);

        JButton btnRun = new JButton();
        btnRun.setText("Run");
        btnRun.addActionListener(mActionListener);
        panel.add(btnRun, BorderLayout.EAST);

        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);

        tabbedPane.addChangeListener(new TabChangeListener());
        textModePanel = new TextModePanel();
        tabbedPane.addTab("Text", textModePanel);
        treeModePanel = new TreeModePanel();
        tabbedPane.addTab("Tree", treeModePanel);/* second tab */
        codeModePanel = new CodeModePanel();
        tabbedPane.addTab("Code", codeModePanel);

        tabbedPane.setSelectedIndex(1);
        currentModelPanel = (AbstractModelPanel) tabbedPane
                .getSelectedComponent();
    }
}
