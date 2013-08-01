/***********************************************************************
 * 
 *     Copyright: 2011, BAINA Technologies Co. Ltd.
 *     Classname: StateBarModel.java
 *     Author:    yuewang
 *     Description:    TODO
 *     History:
 *         1.  Date:   下午05:43:24
 *             Author:    yuewang
 *             Modifycation:    create the class.       
 *
 ***********************************************************************/

package org.lichsword.android.model.activity;

/**
 * @author yuewang
 * 
 */
public class StateBarModel {

	public StateBarModel() {
		super();
		imagePath = "./image/phone/milestone/device_state_bar.png";
		isVisible = true;
	}

	public StateBarModel(String imagePath, boolean visible) {
		this.setImagePath(imagePath);
		isVisible = visible;
	}

	private String imagePath = null;

	private boolean isVisible = true;

	/**
	 * 480
	 */
	private final int DEFALUT_STATE_WIDTH = 480;
	/**
	 * 40
	 */
	private final int DEFALUT_STATE_HEIGHT = 38;

	private int width = DEFALUT_STATE_WIDTH;
	private int height = DEFALUT_STATE_HEIGHT;

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getImagePath() {
		return imagePath;
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
