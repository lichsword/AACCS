package org.lichsword.android.ui.designPanels.json;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Enumeration;
import java.util.Iterator;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lichsword.android.manager.ImageCachedManager;
import org.lichsword.android.ui.designPanels.json.TreeModePanel.IconNode.eJSONType;
import org.lichsword.util.TextUtils;

public class TreeModePanel extends AbstractModelPanel {
    /**
     * 
     */
    private static final long serialVersionUID = -4085578857452410528L;

    private JTree tree;
    private IconNode rootNode;
    private String jsonString;

    public TreeModePanel() {
        super();
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        initContentView();
        initContentData();
    }

    class ParseJSONAsTreeThread extends Thread {

        @Override
        public void run() {
            super.run();

            IconNode rootNode = TreeModePanel.this.rootNode;
            rootNode.removeAllChildren();
            parseJSONAsTree(rootNode, "JSON", jsonString);
            tree.setRootVisible(true);
            // TODO
            TreeModePanel.this.expandAll(tree, new TreePath(rootNode), true);
        }

        JSONNodeFactory factory = JSONNodeFactory.getInstance();

        private void parseJSONAsTree(final IconNode parentNode,
                final String childKeyString, final Object childValueObject) {
            try {
                if (childValueObject instanceof Integer) {
                    IconNode node = factory.createNode(eJSONType.INT,
                            childKeyString, "" + childValueObject);
                    parentNode.add(node);
                } else if (childValueObject instanceof String) {
                    String valueString = (String) childValueObject;
                    if (isJsonArray(valueString)) {
                        IconNode node = factory.createNode(eJSONType.ARRAY,
                                childKeyString, "");
                        parentNode.add(node);
                        // handler child
                        JSONArray array = new JSONArray(valueString);
                        if (null != array) {
                            // LogHelper.d("array: " + array);
                            for (int i = 0; i < array.length(); i++) {
                                parseJSONAsTree(node, "" + i,
                                        array.getString(i));
                            }// end for
                        }
                    } else if (isJsonObject(valueString)) {
                        IconNode node = factory.createNode(eJSONType.OBJECT,
                                childKeyString, "");
                        parentNode.add(node);
                        // handler child
                        JSONObject object = new JSONObject(valueString);
                        if (null != object) {
                            // LogHelper.d("object: " + object);
                            @SuppressWarnings("unchecked")
                            Iterator<String> keys = object.keys();
                            while (keys.hasNext()) {
                                String key = keys.next().toString();
                                Object value = object.get(key);
                                // LogHelper
                                // .d("key: " + key + ", value: " + value);
                                parseJSONAsTree(node, key, value);
                            }
                        }
                    } else {
                        // LogHelper.e("illegal json: ");
                        IconNode node = factory.createNode(eJSONType.STRING,
                                childKeyString, valueString);
                        parentNode.add(node);
                    }
                } else if (childValueObject instanceof JSONArray) {
                    IconNode node = factory.createNode(eJSONType.ARRAY,
                            childKeyString, "");
                    parentNode.add(node);
                    // handler child
                    JSONArray array = (JSONArray) childValueObject;
                    if (null != array) {
                        // LogHelper.d("array: " + array);
                        for (int i = 0; i < array.length(); i++) {
                            parseJSONAsTree(node, "" + i, array.getString(i));
                        }// end for
                    }
                } else if (childValueObject instanceof JSONObject) {
                    IconNode node = factory.createNode(eJSONType.OBJECT,
                            childKeyString, "");
                    parentNode.add(node);
                    // handler child
                    JSONObject object = (JSONObject) childValueObject;
                    if (null != object) {
                        // LogHelper.d("object: " + object);
                        @SuppressWarnings("unchecked")
                        Iterator<String> keys = object.keys();
                        while (keys.hasNext()) {
                            String key = keys.next().toString();
                            Object value = object.get(key);
                            // LogHelper.d("key: " + key + ", value: " + value);
                            parseJSONAsTree(node, key, value);
                        }
                    }
                } else {
                    // log
                }

            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    /***
     * Tree render
     * 
     * @author lichsword
     * 
     */
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
            String text = node.toString();

            setIcon(icon);
            setText(text);

            /* must return self */
            return this;
        }

    }

    public static class JSONNodeFactory {

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

        IconNode createNode(eJSONType type, String key, String value) {
            IconNode result = new IconNode(type, key, value);
            return result;
        }

    }

    static class IconNode extends DefaultMutableTreeNode {

        private static final long serialVersionUID = 1L;

        public static enum eJSONType {
            ARRAY, OBJECT, INT, STRING
        }

        eJSONType type;
        Icon icon;
        String key;
        String value;

        IconNode() {
            // do nothing
        }

        IconNode(eJSONType type, String key, String value) {
            this.type = type;
            JSONNodeFactory factory = JSONNodeFactory.getInstance();
            switch (type) {
            case ARRAY:
                icon = factory.arrayIcon;
                break;
            case INT:
                icon = factory.integerIcon;
                break;
            case STRING:
                icon = factory.stringIcon;
                break;
            case OBJECT:
                icon = factory.objectIcon;
                break;
            default:
                break;
            }
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            if (TextUtils.isEmpty(value)) {
                return key;
            } else {
                return key + ":" + value;
            }
        }

    }

    @Override
    public void setData(String jsonString) {
        this.jsonString = jsonString;
        ParseJSONAsTreeThread thread = new ParseJSONAsTreeThread();
        thread.start();
    }

    /**
     * 
     * @return null if not exist root.
     */
    public IconNode getRootNode() {
        return rootNode;
    }

    private void initContentView() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout(0, 0));

        /* scroll pane contain an TextArea */
        rootNode = JSONNodeFactory.getInstance().createNode(eJSONType.OBJECT,
                "ROOT", "");
        tree = new JTree(rootNode);
        tree.setCellRenderer(new IconCellRender());/* set cell render */
        tree.setEditable(false);/* can't edit */
        tree.setRootVisible(false);/* root node visible false */
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
        // do nothing
    }

    private boolean isJsonArray(String text) {
        try {
            new JSONArray(text);
            return true;
        } catch (JSONException e) {
            // e.printStackTrace();
            return false;
        }
    }

    private boolean isJsonObject(String text) {
        try {
            new JSONObject(text);
            return true;
        } catch (JSONException e) {
            // e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("rawtypes")
    private void expandAll(JTree tree, TreePath parent, boolean expand) {
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
            for (Enumeration e = node.children(); e.hasMoreElements();) {
                TreeNode n = (TreeNode) e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree, path, expand);
            }
        }// end if

        if (expand) {
            tree.expandPath(parent);
        } else {
            tree.collapsePath(parent);
        }
    }
}
