/***********************************************************************
 * 
 *     Copyright: 2011, BAINA Technologies Co. Ltd.
 *     Classname: DatabaseDesignPanel.java
 *     Author:    yuewang
 *     Description:    TODO
 *     History:
 *         1.  Date:   下午03:46:53
 *             Author:    yuewang
 *             Modifycation:    create the class.       
 *
 ***********************************************************************/

package org.lichsword.android.ui.designPanels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * @author yuewang
 * 
 */
public class DatabaseDesignPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8278125897889815884L;

	/**
	 * 
	 */
	public DatabaseDesignPanel() {
		super();
		initContentView();
	}
	
	private void initContentView() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		setSize(1000, 700);
	}
}
