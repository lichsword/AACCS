/******************************************************************************
 *
 * Copyright 2012 Lichsword Studio, All right reserved.
 *
 * File name   : RegularDesignDialog.java
 * Create time : 2012-10-30
 * Author      : lichsword
 * Description : TODO
 *
 *****************************************************************************/
package org.lichsword.java.gui.dialog;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class RegularDesignDialog extends JDialog {

	private static final long serialVersionUID = -7816780213848417002L;

	public RegularDesignDialog(boolean modal) {
		setModal(modal);

		initContentView();
		fillContentView();
	}

	private void initContentView() {
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.WEST);
		// TODO
	}

	private void fillContentView() {
		// TODO
	}
}
