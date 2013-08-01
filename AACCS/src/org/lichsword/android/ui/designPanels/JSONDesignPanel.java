package org.lichsword.android.ui.designPanels;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

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

    private void initContentView() {
        JTextField textField;
        textField = new JTextField();
        textField.setBounds(16, 6, 412, 28);
        add(textField);
        textField.setColumns(10);

        JButton btnRun = new JButton("Run");
        btnRun.setBounds(440, 7, 69, 29);
        add(btnRun);

        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setSize(483, 276);
        scrollPane.setLocation(26, 46);
        add(scrollPane);
    }

}
