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
                StringBuilder sb = new StringBuilder();
                generateCodeByClassNode(sb, rootNode);
                String text = sb.toString();
                textArea.setText(text);
            }// end if
            super.run();
        }

        void generateCodeByClassNode(StringBuilder sb, IconNode node) {

            switch (node.type) {
            case OBJECT:
                sb.append("class " + node.key + " {\n");
                IconNode next = (IconNode) node.getNextNode();
                generateCodeByClassNode(sb, next);
                break;
            case ARRAY:
                sb.append("    private Arraylist<" + node.key + ">" + node.key
                        + "List = null;");
                break;
            case INT:
                sb.append("    private int " + node.key + ";");
                break;
            case STRING:
                sb.append("    private String " + node.key + ";");
                break;
            default:
                break;
            }
            sb.append("}");

        }

    }
}