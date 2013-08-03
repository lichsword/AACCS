package org.lichsword.android.ui.designPanels.json;

import java.awt.BorderLayout;
import java.awt.ScrollPane;

import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.lichsword.android.ui.designPanels.json.TreeModePanel.IconNode;

public class CodeModePanel extends AbstractModelPanel {
    /**
     * 
     */
    private static final long serialVersionUID = -4085578857452410528L;

    private JTextArea textArea;

    @SuppressWarnings("unused")
    private String jsonString = null;

    private IconNode rootNode;

    public CodeModePanel() {
        super();
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        initContentView();
    }

    @Override
    public void setData(String jsonString) {
        this.jsonString = jsonString;
        // TODO
    }

    public void setData(IconNode rootNode) {
        this.rootNode = rootNode;
        CodeCreateThread thread = new CodeCreateThread();
        thread.start();
    }

    private void initContentView() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout(0, 0));

        ScrollPane scrollPane = new ScrollPane();
        add(scrollPane, BorderLayout.CENTER);

        textArea = new JTextArea();
        scrollPane.add(textArea);
    }

    class CodeCreateThread extends Thread {

        @Override
        public void run() {
            if (null != rootNode) {
                String text = translateCode(rootNode);
                textArea.setText(text);
            }// end if
            super.run();
        }

        String translateCode(IconNode rootNode) {
            StringBuilder sb = new StringBuilder();

            if (null != rootNode) {
                sb.append("root node is null.");
            }// end if

            sb.append(rootNode.toString());

            // TODO

            return sb.toString();
        }

    }
}