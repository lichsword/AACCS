package org.lichsword.android.ui.designPanels.json;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;

public class TreeModePanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = -4085578857452410528L;

    public TreeModePanel() {
        super();
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        initContentView();
    }

    private JTextArea textArea;

    private void initContentView() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout(0, 0));

        /* scroll pane contain an TextArea */
        DefaultMutableTreeNode root1 = new DefaultMutableTreeNode("node 1");
        DefaultMutableTreeNode root2 = new DefaultMutableTreeNode("node 2");
        DefaultMutableTreeNode root3 = new DefaultMutableTreeNode("node 3");
        root1.add(root2);
        root2.add(root3);
        JTree tree = new JTree(root1);
        JScrollPane scrollPane = new JScrollPane(tree,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setSize(483, 246);
        scrollPane.setLocation(0, 0);
        add(scrollPane);
    }
}
