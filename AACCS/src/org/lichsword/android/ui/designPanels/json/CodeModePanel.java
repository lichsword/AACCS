package org.lichsword.android.ui.designPanels.json;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class CodeModePanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = -4085578857452410528L;

    public CodeModePanel() {
        super();
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        initContentView();
    }

    private JTextArea textArea;

    private void initContentView() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout(0, 0));
    }
}
