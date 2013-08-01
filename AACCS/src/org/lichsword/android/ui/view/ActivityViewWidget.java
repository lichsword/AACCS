/***********************************************************************
 * 
 *     Copyright: 2011, BAINA Technologies Co. Ltd.
 *     Classname: ActivityViewWidget.java
 *     Author:    yuewang
 *     Description:    TODO
 *     History:
 *         1.  Date:   下午02:49:42
 *             Author:    yuewang
 *             Modifycation:    create the class.       
 *
 ***********************************************************************/

package org.lichsword.android.ui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;

import javax.crypto.Mac;
import javax.naming.InitialContext;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.lichsword.android.manager.ImageCachedManager;
import org.lichsword.android.manager.PhoneStyleManager;
import org.lichsword.android.model.activity.ActivityModel;

/**
 * @author yuewang
 * 
 */
public class ActivityViewWidget extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9048352884740233012L;

	ImageCachedManager mImageResourceManager = ImageCachedManager.getInstance();

	ActivityModel mActivityModel = null;

	/**
	 * 480
	 */
	private final int DEFAULT_ACTIVITY_WIDGET_WIDTH = 480;
	/**
	 * 854
	 */
	private final int DEFAULT_ACTIVITY_WIDGET_HEIGHT = 854;

	private int width = DEFAULT_ACTIVITY_WIDGET_WIDTH + 1;

	private int height = DEFAULT_ACTIVITY_WIDGET_HEIGHT + 1;

	/**
	 * 
	 */
	public ActivityViewWidget() {
		super();
		init();
		setSize(width, height);
	}

	private void init() {
		mActivityModel = new ActivityModel();
	}

	@Override
	public void paint(Graphics graphics) {
		graphics.setColor(new Color(255, 0, 0));
		graphics.draw3DRect(0, 0, 480, 854, true);
		graphics.setColor(new Color(255, 0, 0));
		graphics.drawLine(0, 0, 480, 855);
		graphics.drawLine(0, 854, 480, 0);
		mActivityModel.paint(graphics);
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getWidth() {
		return width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

}
