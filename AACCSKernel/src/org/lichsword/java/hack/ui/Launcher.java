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
package org.lichsword.java.hack.ui;

import java.util.ArrayList;

import org.lichsword.java.jdbc.sqlite.SqliteColumn;
import org.lichsword.java.jdbc.sqlite.SqliteDBContext;
import org.lichsword.java.jdbc.sqlite.SqliteTable;

public class Launcher {

	public static void main(String[] args) {
		// String str =
		// HttpUtil.getSourceCode("http://www.playdota.com/heroes");
		// System.out.println("code = " + str);

		Thread thread = new Thread() {

			@Override
			public void run() {
				super.run();
				SqliteDBContext sqliteDBContext = new SqliteDBContext();
				sqliteDBContext.openDatabse("dota.db");
				ArrayList<String> tableNames = sqliteDBContext.listTables();
				ArrayList<SqliteTable> tables = new ArrayList<SqliteTable>();
				for (String tableName : tableNames) {
					SqliteTable table = new SqliteTable(sqliteDBContext,
							tableName);
					table.refreshValues();
					tables.add(table);
				}

				SqliteTable table = tables.get(1);
				ArrayList<SqliteColumn> columns = table.getColumns();
				for (SqliteColumn column : columns) {
					System.out.println(column.getName());
				}
			}

		};
		thread.start();
	}
}
