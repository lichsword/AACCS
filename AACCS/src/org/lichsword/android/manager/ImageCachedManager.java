/***********************************************************************
 * 
 *     Copyright: 2011, BAINA Technologies Co. Ltd.
 *     Classname: ImageCachedManager.java
 *     Author:    yuewang
 *     Description:    TODO
 *     History:
 *         1.  Date:   下午04:29:59
 *             Author:    yuewang
 *             Modifycation:    create the class.       
 *
 ***********************************************************************/

package org.lichsword.android.manager;

import java.awt.Image;
import java.util.HashMap;

import javax.swing.ImageIcon;

/**
 * @author yuewang
 * 
 */
public class ImageCachedManager {

	private static ImageCachedManager sInstance;

	private ImageCachedManager() {
		super();
	}

	public static ImageCachedManager getInstance() {
		if (null == sInstance) {
			sInstance = new ImageCachedManager();
		}// end if
		return sInstance;
	}

	private HashMap<Integer, ImageIcon> mCachedImageIcons = new HashMap<Integer, ImageIcon>();

	private boolean hasCached(String imagePath) {
		return mCachedImageIcons.containsKey(imagePath.hashCode());
	}

	private boolean addImageIcon(String imagePath) {
		String temp = new String(imagePath);
		temp = temp.replace('\\', '/');
		ImageIcon imageIcon = new ImageIcon(temp);
		mCachedImageIcons.put(imagePath.hashCode(), imageIcon);
		return true;
	}

	private ImageIcon getImageIcon(String imagePath) {
		return mCachedImageIcons.get(imagePath.hashCode());
	}

	public boolean addImage(String imagePath) {
		return addImageIcon(imagePath);
	}

	public void removeImage(String iamgePath) {
		if (null == iamgePath) {
			return;
		}// end if
		mCachedImageIcons.remove(iamgePath.hashCode());
	}

	public Image getImage(String imagePath) {
		Image result = null;
		if (!hasCached(imagePath)) {
			addImage(imagePath);
		}// end if
		ImageIcon imageIcon = getImageIcon(imagePath);
		if (null != imageIcon) {
			result = imageIcon.getImage();
		}// end if
		return result;
	}
}
