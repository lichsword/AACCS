/******************************************************************************
 *
 * Copyright 2012 Lichsword Studio, All right reserved.
 *
 * File name   : Launcher.java
 * Create time : 2012-10-11
 * Author      : lichsword
 * Description : TODO
 *
 *****************************************************************************/
package org.lichsword.java.console;

import org.lichsword.java.util.HttpUtil;

public class Launcher {

	public static void main(String[] args) {
		String str = HttpUtil.getSourceCode("http://www.playdota.com/heroes");
		System.out.println("code = " + str);
	}
}
