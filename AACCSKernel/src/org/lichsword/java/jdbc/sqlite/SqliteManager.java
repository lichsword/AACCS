/******************************************************************************
 *
 * Copyright 2012 Lichsword Studio, All right reserved.
 *
 * File name   : SqliteManager.java
 * Create time : 2012-10-29
 * Author      : lichsword
 * Description : TODO
 *
 *****************************************************************************/
package org.lichsword.java.jdbc.sqlite;

public class SqliteManager {

	public SqliteManager() {
		super();
	}

	private static SqliteManager sInstance;

	public static SqliteManager getInstance() {
		if (null == sInstance) {
			sInstance = new SqliteManager();
		}// end if
		return sInstance;
	}

	private SqliteTable currentTable;

	public void setCurrentTable(SqliteTable currentTable) {
		this.currentTable = currentTable;
	}

	public SqliteTable getCurrentTable() {
		return currentTable;
	}

}
