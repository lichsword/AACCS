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

                IconNode item = (IconNode) rootNode.getNextNode();
                codeOfObject(sb, rootNode, rootNode);
                // generateCodeByClassNode(sb, rootNode);
                String text = sb.toString();
                textArea.setText(text);
            }// end if
            super.run();
        }

        void codeOfObject(StringBuilder sb, IconNode node, IconNode rootNode) {
            IconNode item = node;
            int maxDeep = rootNode.getDepth();
            switch (item.type) {
            case INT:
                codeOfTab(sb, node.getDepth(), maxDeep);
                sb.append("private int " + item.key + ";\n\n");
                item = (IconNode) item.getNextNode();
                if (null != item) {
                    codeOfObject(sb, item, rootNode);
                }// end if
                break;
            case STRING:
                codeOfTab(sb, node.getDepth(), maxDeep);
                sb.append("private String " + item.key + ";\n\n");
                item = (IconNode) item.getNextNode();
                if (null != item) {
                    codeOfObject(sb, item, rootNode);
                }// end if
                break;
            case OBJECT:
                String tabString = codeOfTab(sb, node.getDepth(), maxDeep);
                String UpperFirstString = UpperFirstChar(node.key);
                if (!item.isRoot()) {// not root, means is an member variable.
                    sb.append("private " + UpperFirstString + ' '
                            + getMemberVariable(node.key) + " = null;\n\n");
                }// end if
                sb.append(tabString);
                sb.append("class " + UpperFirstString + " {\n\n");
                sb.append(tabString);
                sb.append(codeOfConstruct(UpperFirstString));
                item = (IconNode) item.getNextNode();
                if (null != item) {
                    codeOfObject(sb, item, rootNode);
                }// end if
                codeOfTab(sb, node.getDepth(), maxDeep);
                sb.append("}\n\n");
                break;
            case ARRAY:
                // TODO
                break;
            default:
                break;
            }
        }

        /**
         * Add tab code to StringBuilder.
         * 
         * @param sb
         * @param deep
         *            tab count
         */
        String codeOfTab(StringBuilder sb, int deep, int maxDeep) {
            StringBuilder inner = new StringBuilder();
            for (int i = deep; i < maxDeep; i++) {
                inner.append("    ");
            }// end for
            String result = inner.toString();
            sb.append(result);
            return result;
        }

        String codeOfConstruct(String className) {
            String result = String.format(
                    "public %s(String jsonString){\n\n}\n\n", className);
            return result;
        }

        String UpperFirstChar(String text) {
            StringBuilder sb = new StringBuilder();
            sb.append(text);
            sb.setCharAt(0, Character.toUpperCase(text.charAt(0)));
            return sb.toString();
        }

        String getMemberVariable(String text) {
            return 'm' + UpperFirstChar(text);
        }
    }
}