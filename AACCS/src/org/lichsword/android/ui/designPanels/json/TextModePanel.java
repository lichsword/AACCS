package org.lichsword.android.ui.designPanels.json;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class TextModePanel extends AbstractModelPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 7442860224527572676L;

    private JTextArea textArea;

    public TextModePanel() {
        super();
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);
    
        initContentView();
    }

    @Override
    public void setData(String jsonString) {
        textArea.setText(jsonString);
    }

    private void initContentView() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout(0, 0));
        // TODO
        /* scroll pane contain an TextArea */
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setSize(483, 246);
        scrollPane.setLocation(0, 0);
        add(scrollPane);
    }
}
