/***********************************************************************
 * 
 *     Copyright: 2011, BAINA Technologies Co. Ltd.
 *     Classname: OptionMenuDesignPanel.java
 *     Author:    yuewang
 *     Description:    TODO
 *     History:
 *         1.  Date:   下午03:36:41
 *             Author:    yuewang
 *             Modifycation:    create the class.       
 *
 ***********************************************************************/

package org.lichsword.android.ui.designPanels;

import java.awt.SystemColor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;

import org.lichsword.android.manager.IconCachedManager;
import org.lichsword.android.manager.OptionMenuManager;
import org.lichsword.android.model.activity.ButtonInfo;
import org.lichsword.android.model.activity.OptionMenuModel;
import org.lichsword.android.model.activity.OptionMenuModel.OptionMenuItem;
import org.lichsword.android.ui.view.advance.DragFileHandler;
import org.lichsword.util.FileUtil;
import org.lichsword.util.TipUtil;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * @author yuewang
 * 
 */
public class OptionMenuDesignPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8853186521744999048L;

	private OptionMenuManager mOptionMenuManager = OptionMenuManager
			.getInstance();

	/**
	 * 
	 */
	public OptionMenuDesignPanel() {
		super();
		setLocation(-134, -206);
		initContentView();
	}

	private JPanel mOptionMenuItemDesignPanel;
	private JLabel mMenuIconLabel;
	private JTextField mMenuItemKeywordTextField;
	private JButton mOptionMenuItemConfirmButton;
	private JTextField mMenuXMLFileKeywordTextField;
	private JCheckBox mAutoOpenDirCheckBox;
	private JLabel mMenuXMLFileDescLabel;
	private JLabel mMenuItemDescLabel;
	private JLabel mTipLabel;

	private void initContentView() {
		setToolTipText("Drag right menu icon to here with your mouse...");
		setLayout(null);
		{
			mOptionMenuItemDesignPanel = new JPanel();
			mOptionMenuItemDesignPanel.setBackground(SystemColor.info);
			mOptionMenuItemDesignPanel.setBounds(20, 29, 160, 160);
			add(mOptionMenuItemDesignPanel);
			mOptionMenuItemDesignPanel.setLayout(null);
			{
				mMenuIconLabel = new JLabel("Drag Menu Icon");
				mMenuIconLabel.setBounds(10, 11, 140, 138);
				mOptionMenuItemDesignPanel.add(mMenuIconLabel);
				mMenuIconLabel.setBackground(SystemColor.info);
				mMenuIconLabel.setHorizontalAlignment(SwingConstants.CENTER);
				mMenuIconLabel.setTransferHandler(mDefaultPicDragFileHandler);
			}
		}

		mMenuItemKeywordTextField = new JTextField();
		mMenuItemKeywordTextField.setBounds(190, 138, 159, 27);
		add(mMenuItemKeywordTextField);
		mMenuItemKeywordTextField.setColumns(10);
		mMenuItemKeywordTextField.addKeyListener(mMenuItemKeywordKeyListener);

		JLabel OptionMenuItemKeywordLabel = new JLabel("keyword: (menu item)");
		OptionMenuItemKeywordLabel.setBounds(190, 117, 137, 14);
		add(OptionMenuItemKeywordLabel);

		mOptionMenuItemConfirmButton = new JButton("Confirm");
		mOptionMenuItemConfirmButton.setBounds(393, 133, 120, 36);
		add(mOptionMenuItemConfirmButton);

		mMenuXMLFileKeywordTextField = new JTextField();
		mMenuXMLFileKeywordTextField.setBounds(190, 54, 159, 27);
		add(mMenuXMLFileKeywordTextField);
		mMenuXMLFileKeywordTextField.setColumns(10);
		mMenuXMLFileKeywordTextField
				.addKeyListener(mMenuXMLFileKeywordKeyListener);

		JLabel lblMenuXmlFile = new JLabel("keyword: (menu xml file)");
		lblMenuXmlFile.setBounds(190, 29, 159, 14);
		add(lblMenuXmlFile);

		mAutoOpenDirCheckBox = new JCheckBox("Auto open folder");
		mAutoOpenDirCheckBox.setSelected(true);
		mAutoOpenDirCheckBox.setBounds(393, 56, 137, 23);
		add(mAutoOpenDirCheckBox);

		mMenuXMLFileDescLabel = new JLabel("N/A");
		mMenuXMLFileDescLabel.setBounds(190, 92, 103, 14);
		add(mMenuXMLFileDescLabel);

		mMenuItemDescLabel = new JLabel("N/A");
		mMenuItemDescLabel.setBounds(190, 176, 103, 14);
		add(mMenuItemDescLabel);

		mTipLabel = new JLabel("New label");
		mTipLabel.setForeground(Color.RED);
		mTipLabel.setBounds(20, 279, 573, 27);
		add(mTipLabel);
	}

	private DragFileHandler mDefaultPicDragFileHandler = new DragFileHandler() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
			return super.canImport(comp, transferFlavors);
		}

		@Override
		public boolean importData(JComponent comp, Transferable t) {
			try {

				List dragFileList = (List) t
						.getTransferData(DataFlavor.javaFileListFlavor);
				String filePath = loadDragFiles(dragFileList);
				dragFileEventHappened(filePath);
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return super.importData(comp, t);
		}

	};

	// TODO 这一片还有点混乱
	private OptionMenuModel mCurrentOptionMenu = new OptionMenuModel();
	private OptionMenuItem mCurrentOptionMenuItem = mCurrentOptionMenu.new OptionMenuItem();

	// TODO 这一片也还有点混乱
	private boolean dragFileEventHappened(String filePath) {
		if (!FileUtil.isSupportPicType(filePath)) {
			System.out
					.println("Not belong supported picture type[9.png/png/jpg]...ERROR");
			return false;
		}// end if

		// cached icon
		IconCachedManager.getInstance().refreshOptionMenuItem(
				mCurrentOptionMenuItem, filePath);
		// cached icon info
		System.out.println("default pic : " + filePath);
		mMenuIconLabel.setIcon(IconCachedManager.getInstance()
				.getIcon(filePath));
		return true;
	}

	private String loadDragFiles(List dragFileList) {
		String filePath = null;
		for (Iterator iter = dragFileList.iterator(); iter.hasNext();) {
			File file = (File) iter.next();
			filePath = file.getPath();
			break;
		}
		return filePath;
	}

	private KeyListener mMenuXMLFileKeywordKeyListener = new KeyListener() {

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
			System.out.println("You typed " + e.getKeyChar());
			char ch = e.getKeyChar();
			if (FileUtil.isCharSafe(ch)) {
				hideTip();
			} else {
				System.out
						.println("Illegal character input: "
								+ ch
								+ "[Only support ：1.number(0~9) 2.low char(a~z) 3.underline(_)]");
				showTip(TipUtil.ERROR_CONTAIN_ILLEGAL_CHAR);
			}
			refreshXMLFileNameDesc(mMenuXMLFileKeywordTextField.getText());
		}

	};

	private KeyListener mMenuItemKeywordKeyListener = new KeyListener() {

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
			System.out.println("You typed " + e.getKeyChar());
			char ch = e.getKeyChar();
			if (FileUtil.isCharSafe(ch)) {
				hideTip();
			} else {
				System.out
						.println("Illegal character input: "
								+ ch
								+ "[Only support ：1.number(0~9) 2.low char(a~z) 3.underline(_)]");
				showTip(TipUtil.ERROR_CONTAIN_ILLEGAL_CHAR);
			}
			refreshXMLFileNameDesc(mMenuItemKeywordTextField.getText());
		}

	};

	private void showTip(String tipMsg) {
		mTipLabel.setText(tipMsg);
	}

	private void hideTip() {
		mTipLabel.setText("");
	}

	private void refreshXMLFileNameDesc(String keyword) {
		mOptionMenuManager.setCurrentMenuXMLFileKeyword(keyword);
		String xmlFileName = mOptionMenuManager.getCurrentMenuXMLFileName();
		mMenuXMLFileDescLabel.setText(xmlFileName);
	}

	// TODO
	private void refreshMenuItemDesc(String keyword) {
		// TODO
		// mOptionMenuManager.setCurrentMenuXMLFileName(keyword);
		// String menuItemDesc = mOptionMenuManager.getCurrentMenuXMLFileName()
		// mMenuItemDescLabel.setText(menuItemDesc);
	}
}
