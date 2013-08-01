package org.lichsword.android.manager;

public interface BuildProcessListener {
	public void buildBegin(String msg);

	public void buildProcess(String msg, int color);

	public void buildEnd(String msg);
}
