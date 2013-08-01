package org.lichsword.android.ui.designPanels;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class JSONDesignPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 3028317657813419800L;
    private final JTextField textField;

    public JSONDesignPanel() {
        super();
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        textField = new JTextField();
        textField.setBounds(16, 6, 345, 28);
        add(textField);
        textField.setColumns(10);

        JTextArea textArea = new JTextArea();
        textArea.setBounds(16, 46, 415, 236);
        add(textArea);

        JButton btnRun = new JButton("Run");
        btnRun.setBounds(362, 7, 69, 29);
        add(btnRun);
        initContentView();
    }

    private void initContentView() {
        // TODO
    }
}
