/******************************************************************************
 *
 * Copyright 2012 Lichsword Studio, All right reserved.
 *
 * File name   : SqliteDBContext.java
 * Create time : 2012-10-28
 * Author      : lichsword
 * Description : TODO
 *
 *****************************************************************************/
package org.lichsword.java.jdbc.sqlite;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.DefaultTableModel;

public class SqliteDBContext {

	private String path;
	private Connection conn;
	private Statement stat;

	public SqliteDBContext() {
		super();
	}

	private final String DB_PACK = "org.sqlite.JDBC";
	private final String DB_URL_HEAD = "jdbc:sqlite:";

	public void openDatabse(String path) {
		this.path = path;

		if (null == conn) {
			try {
				Class.forName(DB_PACK);
				conn = DriverManager.getConnection(DB_URL_HEAD + path);
				setStat(conn.createStatement());
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			// log has open
		}
	}

	public void closeDatabase() {
		if (null != conn) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}// end if
	}

	private final String STAT_QUERY_ALL_TABLE = "select name from sqlite_master where type = 'table' and name <> 'sqlite_sequence'";

	public ArrayList<String> listTables() {
		ArrayList<String> result = new ArrayList<String>();
		ResultSet rs;
		try {
			rs = getStat().executeQuery(STAT_QUERY_ALL_TABLE);
			while (rs.next()) {
				String tableName = rs.getString("name");
				// System.out.println(tableName); // for debug
				result.add(tableName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setStat(Statement stat) {
		this.stat = stat;
	}

	public Statement getStat() {
		return stat;
	}

	private final int INDEX_COLUMN_NAME = 4;
	private final int INDEX_COLUMN_TYPE_NAME = 6;

	public ArrayList<SqliteColumn> listColumns(String tableName) {
		ArrayList<SqliteColumn> result = new ArrayList<SqliteColumn>();

		if (null != conn) {
			DatabaseMetaData metadata;
			try {
				metadata = conn.getMetaData();
				ResultSet rs = metadata.getColumns(null, null, tableName, null);
				while (rs.next()) {
					SqliteColumn column = new SqliteColumn();
					column.setName(rs.getString(INDEX_COLUMN_NAME));
					column.setTypeName(rs.getString(INDEX_COLUMN_TYPE_NAME));

					System.out.println(rs.getString(INDEX_COLUMN_NAME));
					System.out.println(rs.getString(INDEX_COLUMN_TYPE_NAME));

					result.add(column);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}// end if
		return result;
	}

	private final String STAT_QUERY_ALL_HEAD = "select * from ";

	public ResultSet queryAll(String tableName) {
		if (null == stat) {
			try {
				stat = conn.createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}// end if
		ResultSet resultSet = null;
		if (null != stat) {
			try {
				resultSet = getStat().executeQuery(
						STAT_QUERY_ALL_HEAD + tableName);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultSet;
	}

	private HashMap<String, DefaultTableModel> cacheTableModel = new HashMap<String, DefaultTableModel>();

	public DefaultTableModel getCachedTableModel(String tableName) {
		DefaultTableModel result = cacheTableModel.get(tableName);

		if (null != cacheTableModel) {
			result = cacheTableModel.get(tableName);
			if (null != result) {
				return result;
			} else {
				return null;
			}
		} else {
			throw new NullPointerException("cacheTableModel is null");
		}
	}

	public void saveTableModel(String tableName, DefaultTableModel tableModel) {
		if (null != cacheTableModel) {
			cacheTableModel.put(tableName, tableModel);
		} else {
			throw new NullPointerException("cacheTableModel is null");
		}
	}

}
