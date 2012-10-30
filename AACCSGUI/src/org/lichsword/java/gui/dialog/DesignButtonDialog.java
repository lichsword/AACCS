/******************************************************************************
 *
 * Copyright 2012 Lichsword Studio, All right reserved.
 *
 * File name   : DesignButtonDialog.java
 * Create time : 2012-10-30
 * Author      : lichsword
 * Description : TODO
 *
 *****************************************************************************/
package org.lichsword.java.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;

import org.lichsword.java.tool.design.android.manager.ConfigManager;
import org.lichsword.java.tool.design.android.ui.designPanels.ButtonDesignPanel;

public class DesignButtonDialog extends JDialog {

	private static final long serialVersionUID = -2870305570524488571L;

	public DesignButtonDialog(boolean modal) {
		// getContentPane().setSize(new Dimension(631, 512));
		setBounds(100, 100, 613, 512);
		setResizable(false);
		setAlwaysOnTop(true);
		setModal(true);

		initContentView();
		fillContentView();
	}

	private void initContentView() {
		ButtonDesignPanel buttonDesignPanel = new ButtonDesignPanel();
		getContentPane().add(buttonDesignPanel, BorderLayout.CENTER);
	}

	private void fillContentView() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();

		int width = screenSize.width;
		int height = screenSize.height;

		int defaultX = (width - getSize().width) / 2;
		int defaultY = (height - getSize().height) / 2;

		ConfigManager manager = ConfigManager.getInstance();

		setBounds(manager.getWindowX(defaultX), manager.getWindowY(defaultY),
				manager.getWindowWidth(ConfigManager.DEFAULT_WINDOW_WIDTH),
				manager.getWindowHeight(ConfigManager.DEFAULT_WINDOW_HEIGHT));
	}

}
