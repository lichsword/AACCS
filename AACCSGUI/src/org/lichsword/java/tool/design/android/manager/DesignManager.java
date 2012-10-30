/***********************************************************************
 * 
 *     Copyright: 2011, BAINA Technologies Co. Ltd.
 *     Classname: DesignManager.java
 *     Author:    yuewang
 *     Description:    TODO
 *     History:
 *         1.  Date:   下午10:43:10
 *             Author:    yuewang
 *             Modifycation:    create the class.       
 *
 ***********************************************************************/

package org.lichsword.java.tool.design.android.manager;

import java.util.ArrayList;

import org.lichsword.java.tool.design.android.model.activity.ActivityModel;
import org.lichsword.java.tool.design.android.model.application.ApplicationModel;
import org.lichsword.java.tool.design.android.model.service.ServiceModel;

/**
 * @author yuewang
 * 
 */
public class DesignManager {
	private static DesignManager sInstance;

	private DesignManager() {
		super();
	}

	public static DesignManager getInstance() {
		if (null == sInstance) {
			sInstance = new DesignManager();
		}// end if
		return sInstance;
	}

	/**
	 * In common, we has more than 1 activity.
	 */
	private ArrayList<ActivityModel> mActivityModelList = new ArrayList<ActivityModel>();

	/**
	 * In common, we only has 1 application(default application).
	 */
	private ArrayList<ApplicationModel> mApplicationModelList = new ArrayList<ApplicationModel>();

	/**
	 * In common, we only has 1 OR 2 service.
	 */
	private ArrayList<ServiceModel> mServiceModelList = new ArrayList<ServiceModel>();

	
	
}
