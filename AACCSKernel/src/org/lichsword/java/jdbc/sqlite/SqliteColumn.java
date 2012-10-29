/******************************************************************************
 *
 * Copyright 2012 Lichsword Studio, All right reserved.
 *
 * File name   : SqliteColumn.java
 * Create time : 2012-10-29
 * Author      : lichsword
 * Description : TODO
 *
 *****************************************************************************/
package org.lichsword.java.jdbc.sqlite;

public class SqliteColumn {

	private String name;
	private String typeName;
	
	public static final String TYPE_NAME_TEXT = "TEXT";
	public static final String TYPE_NAME_INTEGER = "INTEGER";
	

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

}
