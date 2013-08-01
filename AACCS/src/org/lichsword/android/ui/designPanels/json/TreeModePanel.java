package org.lichsword.android.ui.designPanels.json;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lichsword.android.manager.ImageCachedManager;
import org.lichsword.android.ui.designPanels.json.TreeModePanel.JSONNodeFactory.eJSONIcon;
import org.lichsword.util.LogHelper;
import org.lichsword.util.TextUtils;

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
        initContentData();
    }

    private JTree tree;
    private DefaultMutableTreeNode rootNode;
    private String jsonString;

    public void setText(String text) {
        this.jsonString = text;
        ParseTextAsTreeThread thread = new ParseTextAsTreeThread();
        thread.start();
    }

    private void initContentView() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout(0, 0));

        /* scroll pane contain an TextArea */
        rootNode = JSONNodeFactory.getInstance().createNode(eJSONIcon.OBJECT,
                "root");
        tree = new JTree(rootNode);
        tree.setCellRenderer(new IconCellRender());/* set cell render */
        tree.setEditable(false);/* can't edit */
        tree.setRootVisible(true);/* root node visible false */
        tree.setToggleClickCount(2);/* toggle node need only 2 time click */
        DefaultTreeCellRenderer cellRenderer = (DefaultTreeCellRenderer) tree
                .getCellRenderer();
        ImageIcon icon = ImageCachedManager.getInstance().getImageIconRes(
                "icon_array.gif");
        cellRenderer.setOpenIcon(icon);
        JScrollPane scrollPane = new JScrollPane(tree,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setSize(483, 246);
        scrollPane.setLocation(0, 0);

        add(scrollPane);
    }

    private void initContentData() {
        // TODO Auto-generated method stub
        JSONNodeFactory factory = JSONNodeFactory.getInstance();
        IconNode root1 = factory.createNode(eJSONIcon.ARRAY, "node 1");
        IconNode root2 = factory.createNode(eJSONIcon.OBJECT, "node 2");
        IconNode root3 = factory.createNode(eJSONIcon.INT, "node 3");
        IconNode root4 = factory.createNode(eJSONIcon.STRING, "node 4");
        root1.add(root2);
        root2.add(root3);
        root2.add(root4);
        rootNode.add(root1);
        /* must invoke repaint, otherwise the UI is dirty data. */
        // tree.repaint();
    }

    private boolean isJsonArray(String text) {
        try {
            new JSONArray(text);
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isJsonObject(String text) {
        try {
            new JSONObject(text);
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    class IconCellRender extends DefaultTreeCellRenderer {

        private static final long serialVersionUID = 1L;

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                boolean sel, boolean expanded, boolean leaf, int row,
                boolean hasFocus) {
            /* must invoke super() at first */
            super.getTreeCellRendererComponent(tree, value, sel, expanded,
                    leaf, row, hasFocus);
            IconNode node = (IconNode) value;
            Icon icon = node.icon;
            String text = node.text;

            setIcon(icon);
            setText(text);

            /* must return self */
            return this;
        }

    }

    private class ParseTextAsTreeThread extends Thread {

        @Override
        public void run() {
            try {

                if (!TextUtils.isEmpty(jsonString)) {
                    if (isJsonArray(jsonString)) {
                        JSONArray array = new JSONArray(jsonString);

                    } else if (isJsonObject(jsonString)) {
                        JSONObject object = new JSONObject(jsonString);
                    } else {
                        LogHelper.e("illegal json");
                    }
                } else {
                    // log
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
            super.run();
        }
    }

    static class JSONNodeFactory {

        private final ImageIcon arrayIcon;/* mark JSON array */
        private final ImageIcon objectIcon;/* mark JSON object */
        private final ImageIcon stringIcon;/* mark string value */
        private final ImageIcon integerIcon;/* mark integer value */

        static JSONNodeFactory sInstance;

        private JSONNodeFactory() {
            // initial icon.
            ImageCachedManager manager = ImageCachedManager.getInstance();
            arrayIcon = manager.getImageIconRes("icon_array.gif");
            objectIcon = manager.getImageIconRes("icon_object.gif");
            stringIcon = manager.getImageIconRes("icon_blue.gif");
            integerIcon = manager.getImageIconRes("icon_green.gif");
        }

        static JSONNodeFactory getInstance() {
            if (null == sInstance) {
                sInstance = new JSONNodeFactory();
            }// end if
            return sInstance;
        }

        static enum eJSONIcon {
            ARRAY, OBJECT, INT, STRING
        }

        IconNode createNode(eJSONIcon icon, String text) {
            IconNode result = null;
            switch (icon) {
            case ARRAY:
                result = new IconNode(arrayIcon, text);
                break;
            case OBJECT:
                result = new IconNode(objectIcon, text);
                break;
            case INT:
                result = new IconNode(integerIcon, text);
                break;
            case STRING:
                result = new IconNode(stringIcon, text);
                break;
            default:
                break;
            }
            return result;
        }

    }

    static class IconNode extends DefaultMutableTreeNode {

        private static final long serialVersionUID = 1L;

        Icon icon;
        String text;

        IconNode() {
            // do nothing
        }

        IconNode(Icon icon, String text) {
            this.icon = icon;
            this.text = text;
        }

        IconNode(ImageIcon imageIcon, String text) {
            this.icon = imageIcon;
            this.text = text;
        }

    }
}
