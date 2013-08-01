/***********************************************************************
 * 
 *     Copyright: 2011, BAINA Technologies Co. Ltd.
 *     Classname: ActivityModel.java
 *     Author:    yuewang
 *     Description:    TODO
 *     History:
 *         1.  Date:   下午10:21:56
 *             Author:    yuewang
 *             Modifycation:    create the class.       
 *
 ***********************************************************************/

package org.lichsword.android.model.activity;

import java.awt.Graphics;
import java.awt.Image;

import org.lichsword.android.manager.ImageCachedManager;

/**
 * @author yuewang
 * 
 */
public class ActivityModel {

	ImageCachedManager mImageResourceManager = ImageCachedManager.getInstance();

	public StateBarModel mStateBarModel = null;

	public TitleBarModel mTitleBarModel = null;

	public OptionMenuModel mOptionMenuModel = null;

	/**
	 * 
	 */
	public ActivityModel() {
		super();
		mStateBarModel = new StateBarModel();
		mTitleBarModel = new TitleBarModel();
		mOptionMenuModel = new OptionMenuModel();
	}

	private Image mTempImage = null;

	public boolean paint(Graphics graphics) {
		boolean result = true;
		int tempHeight = 0;
		// draw state bar
		if (mStateBarModel.isVisible()) {
			mTempImage = mImageResourceManager.getImage(mStateBarModel
					.getImagePath());
			graphics.drawImage(mTempImage, 0, tempHeight,
					mStateBarModel.getWidth(), mStateBarModel.getHeight(),
					null, null);
			tempHeight += mStateBarModel.getHeight();
		}// end if
			// draw application bar
		if (mTitleBarModel.isVisible()) {
			mTempImage = mImageResourceManager.getImage(mTitleBarModel
					.getImagePath());
			graphics.drawImage(mTempImage, 0, tempHeight,
					mTitleBarModel.getWidth(), mTitleBarModel.getHeight(),
					null, null);
			tempHeight += mTitleBarModel.getHeight();
		}// end if
		return result;
	}
}
