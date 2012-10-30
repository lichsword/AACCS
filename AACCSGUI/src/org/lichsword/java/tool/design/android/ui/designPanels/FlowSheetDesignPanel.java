/***********************************************************************
 * 
 *     Copyright: 2011, BAINA Technologies Co. Ltd.
 *     Classname: FlowSheetDesignPanel.java
 *     Author:    yuewang
 *     Description:    TODO
 *     History:
 *         1.  Date:   下午05:16:46
 *             Author:    yuewang
 *             Modifycation:    create the class.       
 *
 ***********************************************************************/

package org.lichsword.java.tool.design.android.ui.designPanels;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.lichsword.java.tool.design.util.ColorUtil;

/**
 * @author yuewang
 * 
 */
public class FlowSheetDesignPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6077383886540913181L;

	/**
	 * 
	 */
	public FlowSheetDesignPanel() {
		super();
		setSize(800, 600);
	}

	private final int CELL_WIDTH = 10;
	private final int CELL_HEIHGT = 10;

	private final int CELL_START_X = 0;
	private final int CELL_START_Y = 0;

	
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub

		int panelWidth = getWidth();
		int panelHeight = getHeight();

		final int startX = CELL_START_X;
		final int startY = CELL_START_Y;

		final int endX = panelWidth;
		final int endY = panelHeight;

		// clear bg
		g.setColor(new Color(CololrUtils.CANVAS_BG));
		g.drawRect(0, 0, panelWidth, panelHeight);

		// draw all vertical lines
		int curX = startX;
		while (curX <= endX) {
			g.setColor(new Color(CololrUtils.CANVAS_VERTICAL_LINE));

			g.drawLine(curX, startY, curX, endY);

			curX += CELL_WIDTH;
		}

		// draw all horizontal lines
		int curY = startY;
		while (curY < endY) {
			g.setColor(new Color(CololrUtils.CANVAS_HORIZONTAL_LINE));

			g.drawLine(startX, curY, endX, curY);

			curY += CELL_HEIHGT;
		}

		// super.paint(g);
	}

	private class CololrUtils {
		public static final int CANVAS_BG = 0xffffff;
		public static final int CANVAS_VERTICAL_LINE = 0xbcbcbc;
		public static final int CANVAS_HORIZONTAL_LINE = 0xbebebe;
		public static final int CANVAS_CELL_FRAME = 0x353a3e;
	}

}
