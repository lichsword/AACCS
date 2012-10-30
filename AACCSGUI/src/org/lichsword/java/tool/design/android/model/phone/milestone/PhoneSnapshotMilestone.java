package org.lichsword.java.tool.design.android.model.phone.milestone;

import java.awt.Image;

import org.lichsword.java.tool.design.android.model.phone.PhoneSnapshot;

/**
 * @author yuewang
 * 
 */
public class PhoneSnapshotMilestone extends PhoneSnapshot {

	/**
	 * 854
	 */
	private final int SCREEN_HEIGHT = 854;
	/**
	 * 480
	 */
	private final int SCREEN_WIDTH = 480;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lichsword.java.tool.design.android.model.phone.PhoneSnapshot#getPhoneScreenHeight()
	 */
	@Override
	public int getPhoneScreenHeight() {
		return SCREEN_HEIGHT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lichsword.java.tool.design.android.model.phone.PhoneSnapshot#getPhoneScreenWidth()
	 */
	@Override
	public int getPhoneScreenWidth() {
		return SCREEN_WIDTH;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lichsword.java.tool.design.android.model.phone.PhoneSnapshot#getPhoneSkinImage()
	 */
	@Override
	public Image getPhoneSkinImage() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lichsword.java.tool.design.android.model.phone.PhoneSnapshot#hasHardKeyboard()
	 */
	@Override
	public boolean hasHardKeyboard() {
		// TODO Auto-generated method stub
		return false;
	}

}
