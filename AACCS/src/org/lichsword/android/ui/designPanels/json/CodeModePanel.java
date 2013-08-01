package org.lichsword.android.ui.designPanels.json;

import java.awt.BorderLayout;

import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class CodeModePanel extends AbstractModelPanel {
    /**
     * 
     */
    private static final long serialVersionUID = -4085578857452410528L;

    private JTextArea textArea;

    public CodeModePanel() {
        super();
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        initContentView();
    }

    @Override
    public void setData(String jsonString) {
        // TODO Auto-generated method stub
    
    }

    private void initContentView() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout(0, 0));
    }
}
