/***********************************************************************
 * 
 *     Copyright: 2011, BAINA Technologies Co. Ltd.
 *     Classname: ActivityDesignPanel.java
 *     Author:    yuewang
 *     Description:    TODO
 *     History:
 *         1.  Date:   下午03:44:21
 *             Author:    yuewang
 *             Modifycation:    create the class.       
 *
 ***********************************************************************/

package org.lichsword.java.tool.design.android.ui.designPanels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.lichsword.java.tool.design.android.ui.view.ActivityViewWidget;

/**
 * @author yuewang
 * 
 */
public class ActivityDesignPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -258377810949990955L;

	/**
	 * 
	 */
	public ActivityDesignPanel() {
		super();
		initContentView();
	}

	private void initContentView() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));

		ActivityViewWidget phoneWidget = new ActivityViewWidget();
		add(phoneWidget);
		phoneWidget.setLayout(null);
	}
}
