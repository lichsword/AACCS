/******************************************************************************
 *
 * Copyright 2012 Lichsword Studio, All right reserved.
 *
 * File name   : WinLauncher.java
 * Create time : 2012-10-27
 * Author      : lichsword
 * Description : TODO
 *
 *****************************************************************************/
package org.lichsword.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.lichsword.java.gui.dialog.DesignButtonDialog;
import org.lichsword.java.gui.dialog.GetWebsiteSourceDialog;
import org.lichsword.java.gui.dialog.GetWebsiteSourceDialog.Listener;
import org.lichsword.java.jdbc.sqlite.SqliteColumn;
import org.lichsword.java.jdbc.sqlite.SqliteDBContext;
import org.lichsword.java.jdbc.sqlite.SqliteTable;

public class WinLauncher extends JFrame implements ActionListener {

	private static final long serialVersionUID = 6215327146471051272L;

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				WinLauncher frame = new WinLauncher();
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Constructor
	 */
	public WinLauncher() throws HeadlessException {
		initFrameParam();
		initContentView();
	}

	private void initFrameParam() {
		setTitle("AACCS-GUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 772, 600);
	}

	private final String MENU_NAME_FILE = "File";
	private final String MENU_NAME_TOOL = "Tool";
	private final String MENU_ITEM_NAME_GET_WEBSITE_SOURCE = "Get site code";
	private final String MENU_ITEM_NAME_REGULAR_TEST = "Regular Test";
	private final String MENU_ITEM_NAME_BUTTON_DESIGN = "Design Button";

	private final String MENU_ITEM_NAME_OPEN = "Open";
	private final String MENU_ITEM_NAME_EXIT = "Exit";

	//
	private JPanel panelDatabase;
	private JTree treeDatabase;

	//

	private void initContentView() {
		initMenutBar();
		initTreeLayout();
		initTableLayout();
	}

	private void initMenutBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		//
		JMenu menuFile = new JMenu(MENU_NAME_FILE);
		menuFile.setIcon(new ImageIcon(WinLauncher.class
				.getResource("/org/lichsword/java/res/icon/database.png")));
		menuFile.setMnemonic(KeyEvent.VK_F);
		menuBar.add(menuFile);
		//
		JMenuItem menuItemOpen = new JMenuItem(MENU_ITEM_NAME_OPEN);
		menuItemOpen.setMnemonic(KeyEvent.VK_O);
		menuItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
				InputEvent.CTRL_DOWN_MASK));
		menuItemOpen.addActionListener(this);
		menuItemOpen.setIcon(new ImageIcon(WinLauncher.class
				.getResource("/org/lichsword/java/res/icon/fldr_obj.gif")));
		menuFile.add(menuItemOpen);
		//
		JMenuItem menuItemExit = new JMenuItem(MENU_ITEM_NAME_EXIT);
		menuFile.add(menuItemExit);

		JMenu menuTool = new JMenu(MENU_NAME_TOOL);
		menuTool.setIcon(new ImageIcon(WinLauncher.class
				.getResource("/org/lichsword/java/res/icon/tool.png")));
		menuTool.setMnemonic(KeyEvent.VK_F4);
		menuBar.add(menuTool);
		//
		JMenuItem menuItemGetWebsiteSource = new JMenuItem(
				MENU_ITEM_NAME_GET_WEBSITE_SOURCE);
		menuItemGetWebsiteSource.setMnemonic(KeyEvent.VK_G);
		menuItemGetWebsiteSource.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_F2, 0));
		menuItemGetWebsiteSource.setIcon(new ImageIcon(WinLauncher.class
				.getResource("/org/lichsword/java/res/icon/lprio_tsk.gif")));
		menuItemGetWebsiteSource.addActionListener(this); // 添加侦听
		menuTool.add(menuItemGetWebsiteSource);
		//
		JMenuItem menuItemRegularTest = new JMenuItem(
				MENU_ITEM_NAME_REGULAR_TEST);
		menuItemRegularTest.setMnemonic(KeyEvent.VK_R);
		menuItemRegularTest.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_F3, 0));
		menuItemRegularTest.setIcon(new ImageIcon(WinLauncher.class
				.getResource("/org/lichsword/java/res/icon/read_obj.gif")));
		menuTool.add(menuItemRegularTest);
		menuTool.addSeparator();
		//
		JMenuItem menuItemDesignButton = new JMenuItem(
				MENU_ITEM_NAME_BUTTON_DESIGN);
		menuItemDesignButton.addActionListener(this); // 添加侦听
		menuItemDesignButton.setMnemonic(KeyEvent.VK_D);
		menuItemDesignButton.setIcon(new ImageIcon(WinLauncher.class
				.getResource("/org/lichsword/java/res/icon/design.png")));
		menuTool.add(menuItemDesignButton);
	}

	//
	private JPanel panelTableDetail;
	private JTable table;

	private void initTreeLayout() {
		JPanel panelContent = new JPanel();
		GridBagLayout gbl_panelContent = new GridBagLayout();
		gbl_panelContent.columnWidths = new int[] { 656, 0 };
		gbl_panelContent.rowHeights = new int[] { 26, 500, 15, 0 };
		gbl_panelContent.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelContent.rowWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		panelContent.setLayout(gbl_panelContent);

		panelDatabase = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelDatabase.getLayout();
		flowLayout.setAlignOnBaseline(true);
		flowLayout.setAlignment(FlowLayout.LEFT);
		panelDatabase.setVisible(false);
		panelDatabase.setBorder(new TitledBorder(null, "",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDatabase.setForeground(new Color(230, 230, 250));
		panelDatabase.setBackground(new Color(230, 230, 250));

		treeDatabase = new JTree();
		treeDatabase.setBackground(new Color(230, 230, 250));
		treeDatabase.setForeground(new Color(230, 230, 250));
		treeDatabase.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				doTreeOnMouseClicked(me);
			}
		});
		getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

		panelDatabase.add(treeDatabase);
		GridBagConstraints gbc_panelDatabase = new GridBagConstraints();
		gbc_panelDatabase.fill = GridBagConstraints.BOTH;
		gbc_panelDatabase.insets = new Insets(0, 0, 0, 5);
		gbc_panelDatabase.gridx = 0;
		gbc_panelDatabase.gridy = 0;
		getContentPane().add(panelDatabase);
	}

	private void initTableLayout() {
		JPanel panelTable = new JPanel();
		GridBagConstraints gbc_panelTable = new GridBagConstraints();
		gbc_panelTable.anchor = GridBagConstraints.NORTH;
		gbc_panelTable.fill = GridBagConstraints.BOTH;
		gbc_panelTable.gridx = 1;
		gbc_panelTable.gridy = 0;
		getContentPane().add(panelTable);
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		// table.addMouseListener(tableOnMouseListener);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				tableOnMouseListener(me);
			}
		});
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		JTableHeader jth = table.getTableHeader();
		panelTable.setLayout(new BorderLayout(0, 0));
		panelTableDetail = new JPanel();
		panelTable.add(panelTableDetail);
		panelTableDetail.setLayout(new BorderLayout(0, 0));
		panelTableDetail.add(jth, BorderLayout.NORTH);
		panelTableDetail.add(table, BorderLayout.CENTER);

		VSB = new JScrollBar();
		VSB.addAdjustmentListener(sbAdjustmentValueChangeListener);
		// TODO temp note
		// panelTable.add(VSB, BorderLayout.EAST);

		HSB = new JScrollBar();
		HSB.setOrientation(JScrollBar.HORIZONTAL);
		HSB.addAdjustmentListener(sbAdjustmentValueChangeListener);
		// TODO temp note
		// panelTable.add(HSB, BorderLayout.SOUTH);
	}

	private JScrollBar VSB;
	private JScrollBar HSB;

	private SBAdjustmentValueChangeListener sbAdjustmentValueChangeListener = new SBAdjustmentValueChangeListener();

	int x;
	int y;

	private class SBAdjustmentValueChangeListener implements AdjustmentListener {

		@Override
		public void adjustmentValueChanged(AdjustmentEvent e) {
			JScrollBar sb = (JScrollBar) e.getSource();
			if (sb.getOrientation() == JScrollBar.HORIZONTAL) {
				x = sb.getValue();
			} else {
				y = sb.getValue();
			}
			panelTableDetail.setLocation(-x, -y);
		}
	}

	private void tableOnMouseListener(MouseEvent me) {
		System.out.println("double click");
		int rowIndex = table.getSelectedRow();
		int columnIndex = table.getSelectedColumn();
		System.out.println("row = " + rowIndex + "column = " + columnIndex);
		if (0 == rowIndex) {
			// special row
			// TODO
		}
	}

	void doTreeOnMouseClicked(MouseEvent me) {
		TreePath tp = treeDatabase.getPathForLocation(me.getX(), me.getY());
		if (null != tp) {
			TreeNode node = (TreeNode) tp.getLastPathComponent();
			/*
			 * only leaf is table, the branch is databaes name
			 */
			if (null != node && node.isLeaf()) {
				// System.out.println(tp.toString());
				String selectedTableName = node.toString();
				System.out.println(selectedTableName);// test

				SqliteTable table = new SqliteTable(currentSqliteDB,
						selectedTableName);
				table.refreshValues();

				DefaultTableModel tableModel = currentSqliteDB
						.getCachedTableModel(selectedTableName);

				if (null == tableModel) {
					tableModel = new DefaultTableModel();
					currentSqliteDB.saveTableModel(selectedTableName,
							tableModel);
				}// end if

				this.table.setModel(tableModel);
				HSB.setMinimum(0);
				HSB.setMaximum(this.table.getWidth());

				int rowcount = tableModel.getRowCount();
				if (0 == rowcount) {
					// first load
					if (null != currentSqliteDB) {
						ArrayList<SqliteColumn> columns = currentSqliteDB
								.listColumns(selectedTableName);
						String[][] ValueStore = table.getValues();
						for (SqliteColumn column : columns) {
							tableModel.addColumn(column.getName());
						}

						String[] Filters = new String[columns.size()];
						for (int i = 0; i < Filters.length; i++) {
							Filters[i] = "*";
						}
						tableModel.addRow(Filters);

						for (String[] strings : ValueStore) {
							tableModel.addRow(strings);
						}
					}
				}// end if
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String name = e.getActionCommand();
		if (e.getSource() instanceof JMenuItem) {
			OnMenuClickEvent(name);
		} else {
			// TODO
		}
	}

	private final String DB_PATH = "dota.db"; // test

	private SqliteDBContext currentSqliteDB;

	private void OnMenuClickEvent(String name) {
		if (name.equals(MENU_ITEM_NAME_OPEN)) {
			Thread thread = new Thread() {

				@Override
				public void run() {
					SqliteDBContext sqliteDB = new SqliteDBContext();
					sqliteDB.openDatabse(DB_PATH);
					ArrayList<String> tableNames = sqliteDB.listTables();

					//
					DefaultMutableTreeNode root = new DefaultMutableTreeNode(
							DB_PATH);
					DefaultTreeModel model = new DefaultTreeModel(root);
					treeDatabase.setModel(model);

					for (int i = 0; i < tableNames.size(); i++) {
						System.out.println(tableNames.get(i));
						DefaultMutableTreeNode tableTree = new DefaultMutableTreeNode(
								tableNames.get(i));
						model.insertNodeInto(tableTree, root, i);
					}
					treeDatabase.setVisible(true);
					panelDatabase.setVisible(true);
					//
					currentSqliteDB = sqliteDB;
					super.run();
				}

			};
			thread.start();

		} else if (name.equals(MENU_ITEM_NAME_GET_WEBSITE_SOURCE)) {
			boolean modal = true;// 模态对话框
			GetWebsiteSourceDialog dialog = new GetWebsiteSourceDialog(modal);
			dialog.setListener(new GetWebsiteSourceListener());
			dialog.show();
		} else if (name.equals(MENU_ITEM_NAME_BUTTON_DESIGN)) {
			boolean modal = true;// 模态对话框
			DesignButtonDialog dialog = new DesignButtonDialog(modal);
			dialog.show();
		}
	}

	private class GetWebsiteSourceListener implements Listener {

		@Override
		public void finishGetWebsiteCode(boolean success, String result) {
			if (success) {
				System.out.println(result);
			}

		}
	}

}
