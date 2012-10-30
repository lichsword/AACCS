/***********************************************************************
 * 
 *     Copyright: 2011, BAINA Technologies Co. Ltd.
 *     Classname: TitleBarModel.java
 *     Author:    yuewang
 *     Description:    TODO
 *     History:
 *         1.  Date:   下午10:27:46
 *             Author:    yuewang
 *             Modifycation:    create the class.       
 *
 ***********************************************************************/

package org.lichsword.java.tool.design.android.model.activity;

/**
 * @author yuewang
 * 
 */
public class TitleBarModel {

	public TitleBarModel() {
		super();
		this.imagePath = "./image/phone/milestone/device_application_title.png";
		this.visible = true;
	}

	public TitleBarModel(String imagePath, boolean visible) {
		this.setImagePath(imagePath);
		this.visible = visible;
	}

	private String imagePath = null;

	private boolean visible = true;
	/**
	 * 480
	 */
	private final int DEFAULT_TITLE_BAR_WIDTH = 480;
	/**
	 * 38
	 */
	private final int DEFAULT_TITLE_BAR_HEIGHT = 38;
	
	private int width = DEFAULT_TITLE_BAR_WIDTH;
	
	private int height = DEFAULT_TITLE_BAR_HEIGHT;

	public void setVisible(boolean isVisible) {
		this.visible = isVisible;
	}

	public boolean isVisible() {
		return visible;
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
